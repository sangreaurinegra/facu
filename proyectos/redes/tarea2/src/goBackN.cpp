#include "goBackN.h"


/*
 Automatic Repeat Request (ARQ) ,Solicitud Automatica de repeticion
Son protocolos fiables basados en retrasmisiones

Se requieren fundamentalmente 3 capacidades adicionales para gestionar la presencia de errores.

- Deteccion de errores
	bits adicionales para soportar tecnicas de deteccion (y correccion) de errores
- Realimentacion de receptor
	el receptor envia ACK cuando recive un paquete de manera correcta
- Retransmicion
	un paquete recivido con errores en el receptor debe de ser retrasmitido por el emisor
*/


/*
Go back N
El Emisor puede trasmitir paquetes sin tener que esperar que sean reconocidos , pero esta restringido a N paquetres no reconocidos en el canal.
Le ventana (limite en el emisor) existe como tal por el control de flujo , entr otros ...
*/
//Emisor
int base = 0; //numero de secuencia del paquete no reconicido mas antiguo
int sigNumSec = 0; // siguiente numero de secuencia del siguiente paquete a enviar

//Receptor
int numSecEsperado = 0;

struct nodoGbn{
    char* datagrama;
    size_t lenDatos;
};

nodoGbn *pqtsEnv[MAX_GBN_SIZE];

nodoGbn * pqtEnvACK = NULL;

pthread_t timerThread; // timer de la ventana


pthread_mutex_t mutex_emisor = PTHREAD_MUTEX_INITIALIZER;

pthread_mutex_t mutex_timer = PTHREAD_MUTEX_INITIALIZER;

pthread_mutex_t mutex_receptor = PTHREAD_MUTEX_INITIALIZER;


// los paquetes de [0,base-1] corresponden a los paquetes enviados y reconocidos
// [base,sigNumSec-1] corresponden a paquetes enviados pero no reconocidos hasta el momento
// [sigNumSec,base+GBN_WINDOW-1] se pueden emplear para el envio de nuevos paquetes
// los numeros de secuencia mayores a base+GBN_WINDOW no muede ser utilizado hasta que se reconocido el paquete coon secuencia base


// Numero de secuencias estan determinados por la cantidad d ebits en el campo nro de secuencia del cabezal
// siendo k dicha cantidad de bits los numeros de secuencia estan en el rango [0,2^k-1] por lo que hay que utilizar aritmetica modulo 2^k
// e nuestro caso el numero de secuencia esta determinado por los bits de unsigned char , es decir 2^8 = 256 (tambien lo aclara la letra)


void inicioEmisor(){
	pthread_mutex_lock(&mutex_emisor);
	base = 0 ;
	sigNumSec = 0 ;
	for (int i = 0; i < MAX_GBN_SIZE; ++i) {
		pqtsEnv[i]=NULL;
	}
	pthread_mutex_unlock(&mutex_emisor);
}

void destruirEmisor(){
	pthread_mutex_lock(&mutex_emisor);
	base = 0 ;
	sigNumSec = 0 ;
	for (int i = 0; i < MAX_GBN_SIZE; ++i) {
		if(pqtsEnv[i]!=NULL){
			delete pqtsEnv[i];
			pqtsEnv[i]=NULL;
		}
	}
	pthread_mutex_unlock(&mutex_emisor);
}

void sigNumSecPlusPlus(){
	sigNumSec++;
	if(sigNumSec == MAX_GBN_SIZE){
		sigNumSec = 0;
	}
	//log("sigNumSecPlusPlus "+intToStr(sigNumSec),LOGGER_TRACE);
}

void borrarPaquete(int i){
	if(pqtsEnv[i]!=NULL){
		if(pqtsEnv[i]->datagrama!=NULL){
			delete pqtsEnv[i]->datagrama;
			pqtsEnv[i]->datagrama = NULL;
		}
		delete pqtsEnv[i];
		pqtsEnv[i] = NULL;
	}
}

int rdtEnviar(char* datos, size_t lenDatos){
	pthread_mutex_lock(&mutex_emisor);
//	string strLog = "Entro en rdtEnviar Datos "+ string(datos);
//	log(strLog+intToStr(lenDatos),LOGGER_INFO);
	int ret = -1;

	int topeVentana = (base+GBN_WINDOW)%MAX_GBN_SIZE;
	bool desborda = base+GBN_WINDOW >= MAX_GBN_SIZE;// la ventana comieza a circular

	if(((!desborda)&&(sigNumSec < topeVentana) && (sigNumSec >= base)) ||
			((desborda) && (sigNumSec >= base) && (sigNumSec < MAX_GBN_SIZE))
			||  ((desborda) && (sigNumSec < base) && (sigNumSec < topeVentana))) { // si el siguiente numero esta dentro de la ventana

//		string strLog = "Ventana ["+intToStr(base);
//		strLog = strLog + ".." ;
//		strLog = strLog +intToStr(topeVentana) ;
//		strLog = strLog + "]";
//		log(strLog,LOGGER_TRACE);

		if(pqtsEnv[sigNumSec]!=NULL){ // si hay algo lo elimino antes de setear el nuevo
			borrarPaquete(sigNumSec);
		}
		int lenDatagrama = sizeof(struct iphdr) + sizeof(struct pct_header) + lenDatos;
		char datagrama [lenDatagrama];
		memset(datagrama, 0, lenDatagrama);
		crearPaqueteDatos(sigNumSec,datos,lenDatos, datagrama);

		//cout<<"rdtEnviar Creo Datagrama ";
		//imprimirPaquete(datagrama);
		//cout<<" lendatos "<<lenDatos<<" en sigNumSec "<<sigNumSec<<" \n";

		pqtsEnv[sigNumSec] = new nodoGbn;
		pqtsEnv[sigNumSec]->lenDatos = lenDatos;

		pqtsEnv[sigNumSec]->datagrama = new char[lenDatagrama];
		memcpy(pqtsEnv[sigNumSec]->datagrama,datagrama,lenDatagrama);

//		imprimirGBN(LOGGER_INFO); //

		udtEnviarEmisor(pqtsEnv[sigNumSec]);

		if(base == sigNumSec){ // o sea es el primero a enviar de la ventana
			iniciarTemporizador();
		}
		sigNumSecPlusPlus();
		ret = 0;
	}else{
//		log("Rechazo Datos ",LOGGER_INFO);
		ret = -1; // rechazar datos
	}

	pthread_mutex_unlock(&mutex_emisor);
	return ret;

}

void finTemporizador(){ // time out reenvio paquetes
	pthread_mutex_lock(&mutex_emisor);
//	string srtLog = "**************** finTemporizador base "+intToStr(base);
//	srtLog = srtLog + " sigNumSec " ;
//	srtLog = srtLog + intToStr(sigNumSec);
//	log(srtLog,LOGGER_INFO);

	for(int i = base; i < sigNumSec; i++){
	//	log("**************** finTemporizador i "+intToStr(i),LOGGER_INFO);
		if(pqtsEnv[i] != NULL){
			udtEnviarEmisor(pqtsEnv[i]); // [base .. sigNumSec-1]
		}
	}
	iniciarTemporizador();
	pthread_mutex_unlock(&mutex_emisor);
}


void rdtRecibirEmisor(int numACK){
	pthread_mutex_lock(&mutex_emisor);
//	log("**************** rdtRecibirEmisor int numACK"+numACK,LOGGER_TRACE);
	int nuevaBase = numACK+1;

	// Verifico que este dentro de la ventana

	int topeVentana = (base+GBN_WINDOW)%MAX_GBN_SIZE;
	bool desborda = base+GBN_WINDOW >= MAX_GBN_SIZE;// la ventana comieza a circular

	if(((!desborda)&&(numACK < topeVentana) && (numACK >= base)) ||
			((desborda) && (numACK >= base) && (numACK < MAX_GBN_SIZE))
			||  ((desborda) && (numACK < base) && (numACK < topeVentana))) { // si el siguiente numero esta dentro de la ventana

		// manejo de casos de movimieto de ventana para eliminacion de memoria
			if(nuevaBase>=base){
				for (int i = base; i < nuevaBase; i++) {
					borrarPaquete(i);
				}
			}else{ // desborda
				for (int i = base; i < MAX_GBN_SIZE; i++) { // parte superior
					borrarPaquete(i);
				}
				for (int i = 0; i < nuevaBase; i++) { // parte inferior
					borrarPaquete(i);
				}
			}
			base = nuevaBase;
			if(base == sigNumSec){ //check signumsec
				detenerTemporizador();
			}else{
				iniciarTemporizador();
			}
	}
	pthread_mutex_unlock(&mutex_emisor);
}

void* timer(void * timeOut){
	usleep((int)timeOut);
	finTemporizador();
	return 0;
}

void iniciarTemporizador(){
	//log("INICIO TEMPORIZADOR",LOGGER_TRACE);
	pthread_mutex_lock(&mutex_timer);
	pthread_create(&timerThread,NULL,timer,(void *)TIMEOUT_GBN);
	pthread_mutex_unlock(&mutex_timer);
}

void detenerTemporizador(){
	//log("DETENER TEMPORIZADOR",LOGGER_TRACE);
	pthread_mutex_lock(&mutex_timer);
	pthread_cancel(timerThread);
	pthread_mutex_unlock(&mutex_timer);
}


// Receptor
void inicioReceptor(){
	pthread_mutex_lock(&mutex_receptor);
	numSecEsperado = 0;
	int lenDatagrama = sizeof(struct iphdr) + sizeof(struct pct_header) +10;
	char datagrama [lenDatagrama];
	memset(datagrama, 0, lenDatagrama);
	crearPaqueteACK(0,datagrama);
	pqtEnvACK = NULL;
//	pqtEnvACK = new nodoGbn;
//	//pqtEnvACK->datagrama = datagrama;
//	pqtEnvACK->datagrama = new char[lenDatagrama];
//	memcpy(pqtEnvACK->datagrama,datagrama,lenDatagrama);
//	pqtEnvACK->lenDatos = 0;

	pthread_mutex_unlock(&mutex_receptor);
}


void numSecEsperadoPlusPlus(){
	numSecEsperado++;
	if(numSecEsperado == MAX_GBN_SIZE){
		numSecEsperado = 0;
	}
//	log("numSecEsperadoPlusPlus "+intToStr(numSecEsperado),LOGGER_TRACE);
}

int rdtRecibirReceptor(int numeroSec){//, char* datos, size_t lenDatos){ // recibe el paquete el receptor
	pthread_mutex_lock(&mutex_receptor);
	//cout<<"rdtRecibirReceptor numeroSec "<<numeroSec<<" *\n";
	if(numeroSec == numSecEsperado){ // si es el esperado respondo ACK correspondiente
		//extract(packet,datos);
		//entregarDatos(datos,lenDatos);
		int lenDatagrama = sizeof(struct iphdr) + sizeof(struct pct_header) +10;
		char datagrama [lenDatagrama];
		memset(datagrama, 0, lenDatagrama);
		crearPaqueteACK(numSecEsperado,datagrama);

		//cout<<"rdtRecibirReceptor creo ACK con numeroSec "<<numeroSec;
		//imprimirPaquete(datagrama);

		if(pqtEnvACK != NULL){
			delete pqtEnvACK;
			pqtEnvACK = NULL;
		}
		pqtEnvACK = new nodoGbn;

		pqtEnvACK->lenDatos = 10;

		//pqtEnvACK->datagrama = datagrama;
		pqtEnvACK->datagrama = new char[lenDatagrama];
		memcpy(pqtEnvACK->datagrama,datagrama,lenDatagrama);

		udtEnviarReceptor(pqtEnvACK);

		numSecEsperadoPlusPlus();

		pthread_mutex_unlock(&mutex_receptor);
		return 1;
	}else{

		udtEnviarReceptor(pqtEnvACK); // se envia el ultimo ACK correspondiente al ultimo paquete recibido correctamente

		pthread_mutex_unlock(&mutex_receptor);
		return 0;
	}
	pthread_mutex_unlock(&mutex_receptor);
}


// Auxiliares

void udtEnviarEmisor(nodoGbn* nodo){
//	cout<<"udtEnviarEmisor data "<<nodo->datagrama<<" lenDatos "<< nodo->lenDatos<<" *\n";
//	cout.flush();
	enviarPaqueteDatos(nodo->datagrama, nodo->lenDatos);
}

void udtEnviarReceptor(nodoGbn* ack){
//	cout<<"udtEnviarReceptor "<<ack->datagrama<<"*\n";
//	cout.flush();
	if(ack != NULL){
		enviarPaquete(ack->datagrama, ack->lenDatos);
	}
}

// retrona 1 si el GBN esta vacio o sea no quedan paquetes por enviar , 0 en otro caso
int esVacioGBN(){
	pthread_mutex_lock(&mutex_emisor);
	for (int i = 0; i < MAX_GBN_SIZE; ++i) {
		if(pqtsEnv[i]!=NULL){
			pthread_mutex_unlock(&mutex_emisor);
			return 0;
		}
	}
	pthread_mutex_unlock(&mutex_emisor);
	return 1;
}


//void entregarDatos(char* datos, size_t lenDatos){
// escribir en buffer
//}

void imprimirGBN(int loggerLevel){
	cout<<"GBN->";
	for (int i = 0; i < MAX_GBN_SIZE; ++i) {
		if(i==base){
			cout<< "[";
		}
		if(pqtsEnv[i] != NULL){
			cout<<"lendatos "<< pqtsEnv[i]->lenDatos<<" datagrama \n";

			imprimirPaquete(pqtsEnv[i]->datagrama, loggerLevel);

		}else{
			cout<< "NULL";
		}
		int topeVentana = (base+GBN_WINDOW)%MAX_GBN_SIZE;
		if(topeVentana==i){
			cout<< "]";
		}
		if(i<MAX_GBN_SIZE-1){
			cout<< ",";
		}
	}
	cout<<"<-GBN\n";
}

