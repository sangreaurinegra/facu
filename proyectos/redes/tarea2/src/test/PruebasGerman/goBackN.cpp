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

pthread_t keepALiveThread; // timer que maneja el comienzo de envio de keep a live

int cantKeepALive = 0;

// los paquetes de [0,base-1] corresponden a los paquetes enviados y reconocidos
// [base,sigNumSec-1] corresponden a paquetes enviados pero no reconocidos hasta el momento
// [sigNumSec,base+GBN_WINDOW-1] se pueden emplear para el envio de nuevos paquetes
// los numeros de secuencia mayores a base+GBN_WINDOW no muede ser utilizado hasta que se reconocido el paquete coon secuencia base


// Numero de secuencias estan determinados por la cantidad d ebits en el campo nro de secuencia del cabezal
// siendo k dicha cantidad de bits los numeros de secuencia estan en el rango [0,2^k-1] por lo que hay que utilizar aritmetica modulo 2^k
// e nuestro caso el numero de secuencia esta determinado por los bits de unsigned char , es decir 2^8 = 256 (tambien lo aclara la letra)


void inicioEmisor(){
	base = 0 ;
	sigNumSec = 0 ;
	for (int i = 0; i < MAX_GBN_SIZE; ++i) {
		pqtsEnv[i]=NULL;
	}
}

void destruirEmisor(){
	base = 0 ;
	sigNumSec = 0 ;
	for (int i = 0; i < MAX_GBN_SIZE; ++i) {
		if(pqtsEnv[i]!=NULL){
			delete pqtsEnv[i];
			pqtsEnv[i]=NULL;
		}
	}
}

void sigNumSecPlusPlus(){
	sigNumSec++;
	if(sigNumSec == MAX_GBN_SIZE){
		sigNumSec = 0;
	}
	log("sigNumSecPlusPlus "+intToStr(sigNumSec),LOGGER_TRACE);
}

int rdtEnviar(char* datos, size_t lenDatos){

	string strLog = "Entro en rdtEnviar Datos "+ string(datos);
	log(strLog+intToStr(lenDatos),LOGGER_INFO);

	if(cantKeepALive > 1){
		return -2;
	}


	int topeVentana = (base+GBN_WINDOW)%MAX_GBN_SIZE;
	bool desborda = base+GBN_WINDOW >= MAX_GBN_SIZE;// la ventana comieza a circular

	if((sigNumSec < topeVentana) ||
			((desborda) && (sigNumSec >= base) && (sigNumSec < MAX_GBN_SIZE))
			||  ((desborda) && (sigNumSec < base) && (sigNumSec < MAX_GBN_SIZE))) { // si el siguiente numero esta dentro de la ventana

		string strLog = "Ventana ["+intToStr(base);
		strLog = strLog + ".." ;
		strLog = strLog +intToStr(topeVentana) ;
		strLog = strLog + "]";
		log(strLog,LOGGER_TRACE);

		if(pqtsEnv[sigNumSec]!=NULL){ // si hay algo lo elimino antes de setear el nuevo
			delete pqtsEnv[sigNumSec];
			pqtsEnv[sigNumSec] = NULL;
		}

		char datagrama [sizeof(struct iphdr) + sizeof(struct pct_header) + lenDatos];
		memset(datagrama, 0, sizeof(struct iphdr) + sizeof(struct pct_header) + lenDatos);

		crearPaqueteDatos(sigNumSec,datos,lenDatos, datagrama);

		cout<<"rdtEnviar Creo Datagrama ";

		//imprimirPaquete(datagrama);

		cout<<" lendatos "<<lenDatos<<" en sigNumSec "<<sigNumSec<<" \n";

		pqtsEnv[sigNumSec] = new nodoGbn;
		pqtsEnv[sigNumSec]->datagrama = datagrama;
		pqtsEnv[sigNumSec]->lenDatos = lenDatos;

		imprimirGBN(LOGGER_INFO); //TODO eliminar

		udtEnviarEmisor(pqtsEnv[sigNumSec]);

		if(base == sigNumSec){ // o sea es el primero a enviar de la ventana
			iniciarTemporizador();
		}
		sigNumSecPlusPlus();
		return 0;
	}else{
		return -1; // rechazar datos
	}
}

void finTemporizador(){ // time out reenvio paquetes
	log("**************** finTemporizador"+intToStr(sigNumSec),LOGGER_TRACE);
	iniciarTemporizador();
	for(int i = base; i < sigNumSec; i++){

		log("**************** finTemporizador i "+intToStr(i),LOGGER_TRACE);

		udtEnviarEmisor(pqtsEnv[i]); // [base .. sigNumSec-1]
	}
}

void rdtRecibirEmisor(int numACK){
	log("**************** rdtRecibirEmisor int numACK"+numACK,LOGGER_TRACE);
	int nuevaBase = numACK+1;

	// manejo de casos de movimieto de ventana para eliminacion de memoria
	if(nuevaBase>=base){
		for (int i = base; i < nuevaBase; i++) {
			delete pqtsEnv[i];
			pqtsEnv[i] = NULL;
		}

	}else{ // desborda
		for (int i = base; i < MAX_GBN_SIZE; i++) { // parte superior
			delete pqtsEnv[i];
			pqtsEnv[i] = NULL;
		}
		for (int i = 0; i < nuevaBase; i++) { // parte inferior
			delete pqtsEnv[i];
			pqtsEnv[i] = NULL;
		}

	}

	// antes de los for
	//	if((nuevaBase!=base)&&(pqtsEnv[base]!=NULL)){// no desbordo
	//		delete [] pqtsEnv[base];
	//		pqtsEnv[base] = NULL;
	//	}

	base = nuevaBase;
	if(base == sigNumSec){
		detenerTemporizador();
	}else{
		iniciarTemporizador();
	}
}

void* timer(void * timeOut){
	usleep((int)timeOut);
	finTemporizador();
	return 0;
}

void iniciarTemporizador(){
	pthread_create(&timerThread,NULL,timer,(void *)TIMEOUT_GBN);
}

void detenerTemporizador(){
	pthread_cancel(timerThread);
}

void envioKeepALive(){
	//cantKeepALive++;
	//TODO
}


void* keepALiveTimer(void * timeOut){
	usleep((int)timeOut);
	envioKeepALive();
	return 0;
}

void estoyVivo(){
	cantKeepALive = 0;
	pthread_cancel(keepALiveThread);
	pthread_create(&keepALiveThread,NULL,keepALiveTimer,(void *)TIMEOUT_KAL);
}

void reciboKeepALive(){
	estoyVivo();
}

// Receptor
void inicioReceptor(){
	numSecEsperado = 1;
	char datagrama [sizeof(struct iphdr) + sizeof(struct pct_header) +10];
	memset(datagrama, 0, sizeof(struct iphdr) + sizeof(struct pct_header) +10);
	crearPaqueteACK(0,datagrama);
	pqtEnvACK = new nodoGbn;
	pqtEnvACK->datagrama = datagrama;
	pqtEnvACK->lenDatos = 0;
}


void numSecEsperadoPlusPlus(){
	numSecEsperado++;
	if(numSecEsperado == MAX_GBN_SIZE){
		numSecEsperado = 0;
	}
	log("numSecEsperadoPlusPlus "+intToStr(numSecEsperado),LOGGER_TRACE);
}

int rdtRecibirReceptor(int numeroSec){//, char* datos, size_t lenDatos){ // recibe el paquete el receptor
	cout<<"rdtRecibirReceptor numeroSec "<<numeroSec<<" *\n";
	if(numeroSec == numSecEsperado){ // si es el esperado respondo ACK correspondiente
		//char * datos = NULL;
		//extract(packet,datos);
		//entregarDatos(datos,lenDatos);

		char datagrama [sizeof(struct iphdr) + sizeof(struct pct_header) +10];
		memset(datagrama, 0, sizeof(struct iphdr) + sizeof(struct pct_header) +10);
		crearPaqueteACK(numSecEsperado,datagrama);

		cout<<"rdtRecibirReceptor creo ACK con numeroSec "<<numeroSec;
		//imprimirPaquete(datagrama);

		if(pqtEnvACK != NULL){
			delete pqtEnvACK;
			pqtEnvACK = NULL;
		}
		pqtEnvACK = new nodoGbn;
		pqtEnvACK->datagrama = datagrama;
		pqtEnvACK->lenDatos = 10;

		udtEnviarReceptor(pqtEnvACK);

		numSecEsperadoPlusPlus();
		return 1;
	}else{ // TODO DECISION DE DISENIO respondo ack con el ultimo numero de paquete recibido correctamente
		udtEnviarReceptor(pqtEnvACK); // se envia el ultimo ACK correspondiente al ultimo paquete recibido correctamente
		return 0;
	}

}


// Auxiliares

void udtEnviarEmisor(nodoGbn* nodo){
	cout<<"udtEnviarEmisor data "<<nodo->datagrama<<" lenDatos "<< nodo->lenDatos<<" *\n";
	enviarPaqueteDatos(nodo->datagrama, nodo->lenDatos);
}

void udtEnviarReceptor(nodoGbn* ack){
	cout<<"udtEnviarReceptor "<<ack->datagrama<<"*\n";
	enviarPaquete(ack->datagrama, ack->lenDatos);
}


void entregarDatos(char* datos, size_t lenDatos){
//TODO escribir en buffer
}

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

