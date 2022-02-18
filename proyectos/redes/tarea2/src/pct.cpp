#include "pct.h"


using namespace std;

Estado estadoActual = INIT;

Rol rol;

int globalPctSocket;
//struct sockaddr_in globalDireccionSocket;

unsigned char globalPuertoOrigen;
unsigned char globalPuertoDestino;

struct in_addr globalIPOrigen;
struct in_addr globalIPDestino;

pthread_t threadLeoBufferYEnvio;
pthread_t threadRecibirPaqueteControl;
pthread_t threadLeoDatosYEnvioBuffer;
pthread_t threadTimerFin;
pthread_t kalThread;

pthread_mutex_t mutex_buffer = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t mutex_estadoActual = PTHREAD_MUTEX_INITIALIZER;

buffer* buffer;

int saltaTimerFin;
int llegoFin;
int MAX_INTENTOS = 3;


////////AUXILIARES////////////

void* timerFin(void * param){

	usleep((int)finTimeOut);
	saltaTimerFin = 1;

	return 0;
}

void envioKAL(){
	int cantKeepALive = 0;
	//TODO verificar
	int lenDatagrama = sizeof(struct iphdr) + sizeof(struct pct_header) +10; // mas 10 para indicar que es KAL
	char datagrama [lenDatagrama];
	memset(datagrama, 0, lenDatagrama);
	crearPaqueteKAL(datagrama,cantKeepALive);
	enviarPaquete(datagrama,10);
}

void* timerKal(void * param){

	usleep((int)kalTimeOut);

	log("pct.cpp - timerKal: Paso el primer usleep, mando el kal",LOGGER_INFO);

	envioKAL();

	usleep((int)kalTimeOut);

	pthread_mutex_lock(&mutex_estadoActual);
	log("pct.cpp - timerKal: Paso el segundo usleep, me desconecto",LOGGER_INFO);
		estadoActual = DESCONECTADO;
	pthread_mutex_unlock(&mutex_estadoActual);

	llegoFin = 1;

	return 0;
}

void* timerKalReceptor(void * param){

	usleep((int)kalTimeOut);

	log("pct.cpp - timerKal: Paso el primer usleep, mando el kal",LOGGER_INFO);

	envioKAL();

	usleep((int)kalTimeOut);

	pthread_mutex_lock(&mutex_estadoActual);
	log("pct.cpp - timerKal: Paso el segundo usleep, me desconecto",LOGGER_INFO);
		estadoActual = DESCONECTADO;
	pthread_mutex_unlock(&mutex_estadoActual);


	return 0;
}

void * leeDatosYEnviaBuffer (void * param);

void * leeBufferYEnviaDatos (void * param)
{
	char* datosPaquete = NULL;
	char* datosPaqueteEnvio = NULL;
	int resultadoLecturaBuffer = 0;
	int resultadoEnviaDatosGBN = 0;


	//log("pct.cpp - leeBufferYEnviaDatos: >>> ENTRE Y VOY A ITERAR",LOGGER_TRACE);

	pthread_mutex_lock(&mutex_estadoActual);

	while ((llegoFin == 0)||(estadoActual == CONECTADO))
	{

		pthread_mutex_unlock(&mutex_estadoActual);

		pthread_mutex_lock(&mutex_buffer);
		if (not (isEmptyBuffer(buffer)))
		{
			//log("pct.cpp - leeBufferYEnviaDatos: El buffer no estaba vacio, voy a leer del buffer",LOGGER_TRACE);

			datosPaquete = new char[MAX_PCT_DATA_SIZE];
			datosPaquete[0] = '\0';

			//leo del buffer lo maximo que puedo
			resultadoLecturaBuffer = readBuffer(datosPaquete, MAX_PCT_DATA_SIZE, buffer);
			pthread_mutex_unlock(&mutex_buffer);

			//log("pct.cpp - leeBufferYEnviaDatos: Lei del buffer "+ intToStr(resultadoLecturaBuffer) + " bytes",LOGGER_TRACE);

			//Redefino el char* para que efectivamente contenga unicamente los bytes leidos y NO mas.
			datosPaqueteEnvio = new char[resultadoLecturaBuffer];
			memcpy(datosPaqueteEnvio,datosPaquete,resultadoLecturaBuffer);

			//log("pct.cpp - leeBufferYEnviaDatos: Voy a enviar al cliente con rdtEnviar esa cantidad de bytes.",LOGGER_INFO);
			//log("pct.cpp - leeBufferYEnviaDatos: Lo que voy a enviar: \n " + string(datosPaqueteEnvio),LOGGER_TRACE);

			//Intento enviar EL paquete con los datos leidos del buffer al receptor
			resultadoEnviaDatosGBN = rdtEnviar(datosPaqueteEnvio, resultadoLecturaBuffer);

			//log("pct.cpp - leeBufferYEnviaDatos:>>>>> Envie " + intToStr(resultadoEnviaDatosGBN) + "bytes" ,LOGGER_TRACE);

			switch (resultadoEnviaDatosGBN)
			{
				//Si intento enviar un paquete fuera de la ventana espero a que se corra y me permite enviar
				case -1:
					log("pct.cpp - leeBufferYEnviaDatos: Vuelvo a intentar dado que retorne -1 del rdtEnviar ",LOGGER_INFO);
					while (resultadoEnviaDatosGBN == -1)
					{
						usleep(3000);// 3 milisegundos, el parametro es en microsegundos (1 millon de micros = 1 seg))
						resultadoEnviaDatosGBN = rdtEnviar(datosPaqueteEnvio, resultadoLecturaBuffer);
					}
					log("pct.cpp - leeBufferYEnviaDatos: El rdtEnviar retorno <> -1, retorno: "+intToStr(resultadoEnviaDatosGBN),LOGGER_INFO);
					break;
				//Si se perdio la conexion con el receptor detengo el envio de datos al mismo
				case -2:
					log("pct.cpp - leeBufferYEnviaDatos: Salgo de la rutina por recibir -2 de rdtEnviar (KeepAlive no llego)",LOGGER_INFO);
					delete [] datosPaquete;
					datosPaquete = NULL;
					delete [] datosPaqueteEnvio;
					datosPaqueteEnvio = NULL;

					pthread_mutex_lock(&mutex_estadoActual);
						estadoActual = DESCONECTADO;
					pthread_mutex_unlock(&mutex_estadoActual);

					llegoFin = 1;
					break;
				//Si se enviaron los datos exitosamente libero memoria
				case 0:
					//log("pct.cpp - leeBufferYEnviaDatos:exitoso",LOGGER_INFO);

					delete [] datosPaquete;
					datosPaquete = NULL;
					delete [] datosPaqueteEnvio;
					datosPaqueteEnvio = NULL;
					break;
			}

			pthread_mutex_lock(&mutex_buffer);
		}
		pthread_mutex_unlock(&mutex_buffer);

		pthread_mutex_lock(&mutex_estadoActual);
	}

	pthread_mutex_unlock(&mutex_estadoActual);

	return (void *) 0;


}

void * recepcionPaqueteControl (void * param)
{

	int  numRes = 0;
	int iterar = 1;
	size_t largoDatosALeer = 0;
	char* datosPaquete = NULL;
	unsigned char tipoPaquete;
	int resEnvio = 0;
	int termineMandar = 0;

	pthread_mutex_lock(&mutex_estadoActual);

	while (estadoActual == CONECTADO)
	{
		pthread_mutex_unlock(&mutex_estadoActual);

		pthread_create(&kalThread,NULL,timerKal,NULL);

		//log("pct.cpp - recepcionPaqueteControl: Llamo a la funcion  recibirPaquete de Utils",LOGGER_INFO);
		//LLamo a la funcion de pctUtils que da soporte a la recepcion de ack's de parte del receptor
		//la cual retorna el nro de ack que corresponde. (Dentro de la funcion hay una espera implementada que debe ser mayor a la de GBN)
		datosPaquete = new char[MAX_PCT_DATA_SIZE];
		datosPaquete[0] = '\0';
		numRes = recibirPaquete(datosPaquete, largoDatosALeer, tipoPaquete);

		//log("pct.cpp - recepcionPaqueteControl: recibirPaquete retorno "+ intToStr(numRes),LOGGER_TRACE);


		//Si es un ACK
		if ((numRes != -1) && (tipoPaquete == ACK_FLAG))
		{
			pthread_mutex_lock(&mutex_estadoActual);
				pthread_cancel(kalThread);
			pthread_mutex_unlock(&mutex_estadoActual);


			if (datosPaquete[0] == '\0') {
				//log("pct.cpp - recepcionPaqueteControl: recibi un ACK diabolico: "+intToStr(numRes),LOGGER_INFO);
			}else
			{
				log("pct.cpp - recepcionPaqueteControl: recibi un ACK, con nro ack: "+intToStr(numRes),LOGGER_INFO);
				//Llamo a la funcion que da soporte a la recepcion de acks del GBN, de forma de correr la ventans si corresponde
				rdtRecibirEmisor(numRes);
			}
		}
		//Si es un paquete de control KeepAlive
		else if ((numRes != -1) && (tipoPaquete == 0))
		{
			pthread_mutex_lock(&mutex_estadoActual);
				pthread_cancel(kalThread);
			pthread_mutex_unlock(&mutex_estadoActual);
			//Hacer lo que corresponda - Hay que ver donde se hace soporte a este tema
			log ("pct.cpp - leeDatosYEnviaBuffer > Recibi un KAL ",LOGGER_INFO);
			//reciboKeepALiveGBN();
		}
		//Si es un paquete de FIN-ACK
		else if ((numRes != -1) && (tipoPaquete == (FIN_FLAG + ACK_FLAG)))
		{
			pthread_mutex_lock(&mutex_estadoActual);
				pthread_cancel(kalThread);
			pthread_mutex_unlock(&mutex_estadoActual);

			//Quiere decir que se cierra la conexion, hay que setear iterar = 0 ?
			pthread_mutex_lock(&mutex_estadoActual);
				log("pct.cpp - recepcionPaqueteControl: recibi un FIN-ACK, marco la desconexion",LOGGER_INFO);
				estadoActual = DESCONECTADO;
			pthread_mutex_unlock(&mutex_estadoActual);

			llegoFin = 1;
		}
		//Si es un paquete de FIN quiere decir que el receptor desea cerrar la conexion, antes de responder un fin-ack debo
		//asegurarme de que envie todo lo que tengo en el buffer
		else if ((numRes != -1) && (tipoPaquete == FIN_FLAG))
		{
			pthread_mutex_lock(&mutex_estadoActual);
				pthread_cancel(kalThread);
			pthread_mutex_unlock(&mutex_estadoActual);

			pthread_mutex_lock(&mutex_estadoActual);
				estadoActual = DESCONECTADO;
			pthread_mutex_unlock(&mutex_estadoActual);

			//Hay que controlar que el buffer este vacio y que haya llegado el ack para el ultimo paquete enviado
			pthread_mutex_lock(&mutex_buffer);
			//Invocar funcion de GBN que indica que todos los acks han sido enviados
			termineMandar = esVacioGBN();
			while (not ((isEmptyBuffer(buffer)) && (termineMandar == 1)))
			{
				pthread_mutex_unlock(&mutex_buffer);
				usleep(30000);// 30 milisegundos, el parametro es en microsegundos (1 millon de micros = 1 seg))
				pthread_mutex_lock(&mutex_buffer);
				termineMandar = esVacioGBN();
			}

			pthread_mutex_unlock(&mutex_buffer);

			llegoFin = 1;

			resEnvio = enviarPaqueteControlDirecto (FIN_FLAG + ACK_FLAG);

			log("pct.cpp - recepcionPaqueteControl: Recibi un FIN, me desconecte y envie un FIN-ACK, retorno de enviarPaqueteControlDirecto: "+ intToStr(resEnvio),LOGGER_INFO);

		}

		delete [] datosPaquete;
		datosPaquete = NULL;

		pthread_mutex_lock(&mutex_estadoActual);
	}
	pthread_mutex_unlock(&mutex_estadoActual);

	return (void *) 0;

}

int crearPCT(struct in_addr localIPaddr) {
	setLogLevel(LOGGER_INFO);
	//log("crearPCT",LOGGER_TRACE);
	if (estadoActual != INIT) {
		log("Error crearPCT: El estado es distinto a INIT",LOGGER_INFO);
		return -1;
	}

	globalPctSocket = socket(AF_INET, SOCK_RAW, IPPROTO_RAW);

	if (globalPctSocket < 0) {
		log("Error crearPCT: creando Socket",LOGGER_INFO);
		return -1;
	}
	//log("crearPCT  socket creado numero: "+ intToStr(globalPctSocket),LOGGER_TRACE);

	estadoActual = DESCONECTADO;
	globalIPOrigen = localIPaddr;

	char  str[20];
	inet_ntop(AF_INET, &(globalIPOrigen.s_addr), str, INET_ADDRSTRLEN);

	//log("crearPCT  globalIPOrigen: "+ string(str),LOGGER_TRACE);

	//Creo el Buffer correspondiente de escritura o lectura dependiendo si es el emisor o receptor
	buffer = createBuffer(TAM_BUFFER);

	llegoFin = 0;

	return globalPctSocket;
}

int aceptarPCT(unsigned char localPCTport){

	rol = RECEPTOR;

	globalPuertoOrigen = localPCTport;


	if (estadoActual != DESCONECTADO) {
		log("Error aceptarPCT: El estado es distinto a DESCONECTADO",LOGGER_INFO);
		return -1;
	}

	estadoActual = ESPERANDO_CONEXION;

	log("aceptarPCT esperando SYN",LOGGER_INFO);

	int intentos = 0;
	while (recibirPaqueteControl( SYN_FLAG ) == -1 && intentos <= MAX_INTENTOS) {

		usleep(1000000);
		intentos = intentos +1;
		log("Re Espero SYN ",LOGGER_INFO);

	}
	if (intentos> MAX_INTENTOS) {
		log("aceptarPCT se llego al numero maximo de intentos y espera para el ACK",LOGGER_INFO);

		estadoActual = DESCONECTADO;
		return -1;
	}


	log("aceptarPCT enviando SYN ACK",LOGGER_INFO);

	estadoActual = RECIBI_SYN;

	//Ya recibi un SYN, envio un SYN ACK
	enviarPaqueteControlDirecto( SYN_FLAG + ACK_FLAG);
	log("aceptarPCT esperando ACK",LOGGER_INFO);

	intentos = 0;
	while (recibirPaqueteControl( ACK_FLAG ) == -1 && intentos <= MAX_INTENTOS) {

		usleep(1000000);
		intentos = intentos +1;

		//Mientras no reciba un ACK, envio otra vez el SYN ACK
		log("Reenvio SYN ACK",LOGGER_INFO);

		enviarPaqueteControlDirecto( SYN_FLAG + ACK_FLAG);

	}
	if (intentos> MAX_INTENTOS) {
		log("aceptarPCT se llego al numero maximo de intentos y espera para el ACK",LOGGER_INFO);

		estadoActual = DESCONECTADO;
		return -1;
	}

	log("aceptarPCT conexion lograda",LOGGER_INFO);

	estadoActual = CONECTADO;


	//log("pct.cpp - leeDatosYEnviaBuffer: Creo la ventana invocando a inicioReceptor",LOGGER_TRACE);
	//Inicializo gbn receptor
	inicioReceptor();

	//log("pct.cpp - conectarPCT:>>>>>>>>> Tiro el hilo leeDatosYEnviaBuffer",LOGGER_TRACE);
	pthread_create(&threadLeoDatosYEnvioBuffer, NULL, leeDatosYEnviaBuffer, NULL);


	return 0;
}

int conectarPCT(unsigned char localPCTport,unsigned char peerPCTPport, struct in_addr peerIPaddr){
	log("conectarPCT primera linea ",LOGGER_INFO);

	rol = EMISOR;

	if (estadoActual != DESCONECTADO) {
		log("Error conectarPCT: El estado es distinto a DESCONECTADO",LOGGER_INFO);
		return -1;
	}

	if (localPCTport == peerPCTPport) {
		log("Error conectarPCT: los puertos son iguales",LOGGER_INFO);
		return -1;

	}
    globalIPDestino = peerIPaddr;

    globalPuertoOrigen = localPCTport;
    globalPuertoDestino = peerPCTPport;

	log("conectarPTC envio SYN ",LOGGER_INFO);

	enviarPaqueteControlDirecto( SYN_FLAG);

	estadoActual = ESPERANDO_SYN_ACK;
	log("conectarPTC espero SYN_ACK ",LOGGER_INFO);


	int intentos = 0;
	while (recibirPaqueteControl( SYN_FLAG + ACK_FLAG ) == -1 && intentos <= MAX_INTENTOS) {

		usleep(1000000);
		intentos = intentos +1;

		//Mientras no reciba un SYN ACK, envio otra vez el SYN
		log("Reenvio SYN ",LOGGER_INFO);

		enviarPaqueteControlDirecto( SYN_FLAG );

	}
	if (intentos> MAX_INTENTOS) {
		log("se llego al numero maximo de intentos y espera para el SYN ACK",LOGGER_INFO);

		estadoActual = DESCONECTADO;
		return -1;
	}


	log("conectarPTC envio ACK ",LOGGER_INFO);

	//Ya recibi un SYN ACK, envio el ACK
	if (enviarPaqueteControlDirecto( ACK_FLAG) == -1) {
		estadoActual == DESCONECTADO;
		return -1;
	}


	log("conectarPTC CONECTADO ",LOGGER_INFO);

	estadoActual = CONECTADO;


	//log("pct.cpp - aceptarPCT : Creo la ventana invocando a inicioEmisor",LOGGER_TRACE);
	//Inicializo la ventana del GBN
	inicioEmisor();


	//log("pct.cpp - aceptarPCT:>>>>>>>>> Tiro los hilos leeBufferYEnviaDatos y recepcionPaqueteControl",LOGGER_TRACE);
	pthread_create(&threadLeoBufferYEnvio, NULL, leeBufferYEnviaDatos, NULL);
	pthread_create(&threadRecibirPaqueteControl, NULL, recepcionPaqueteControl, NULL);


	return 0;
}


int escribirPCT(const void *buf, size_t len){

	//log("pct.cpp - escribirPCT:>>>>>>>>> ENTRE A  escribirPCT",LOGGER_TRACE);

	int cantDatosEscritosBuffer = 0;

	pthread_mutex_lock(&mutex_estadoActual);

	//Si estoy desconectado retorno -1
	if (estadoActual == DESCONECTADO)
	{
		//log("pct.cpp - escribirPCT:>>>>>>>>> ESTADO DESCONECTADO - ME LAS TOMO RETORNANDO -1 , NO ESCRIBO EN BUFFER!!! :(",LOGGER_TRACE);
		pthread_mutex_unlock(&mutex_estadoActual);
		return -1;
	}
	else
	{
		//log("pct.cpp - escribirPCT: Voy a escribir en el buffer.",LOGGER_TRACE);
		//log("pct.cpp - escribirPCT: Lo que voy a escribir: \n." + string(buf),LOGGER_TRACE);

		//Sino retorno la cantidad escrita de datos en bytes

		pthread_mutex_unlock(&mutex_estadoActual);

		pthread_mutex_lock(&mutex_buffer);
			cantDatosEscritosBuffer = writeBuffer((char*) buf, len, buffer);
			//log("pct.cpp - escribirPCT: IMPRIMO BUFFER: \n",LOGGER_TRACE);
			//printBuffer(buffer);
			//log("\n",LOGGER_TRACE);
		pthread_mutex_unlock(&mutex_buffer);

		//log("pct.cpp - escribirPCT: Retorno la cantidad de bytes entregados a la capa inferior = "+ intToStr(cantDatosEscritosBuffer),LOGGER_TRACE);
		return cantDatosEscritosBuffer;

	}



	/*char datagrama [sizeof(struct iphdr) + sizeof(struct pct_header) + len];
	memset(datagrama, 0, sizeof(struct iphdr) + sizeof(struct pct_header) + len);

	crearPaqueteDatos(0, (char*)buf,len, datagrama);

	log("adentro de escribirPTC______",LOGGER_INFO);
	imprimirPaquete(datagrama);
	enviarPaqueteDatos(datagrama,len);*/
}

int leerPCT(void *buf, size_t len){

	//log("---leerPCT",LOGGER_DEBUG);
	int cantDatosLeidosBuffer = 0;


	pthread_mutex_lock(&mutex_estadoActual);

		//Si estoy desconectado retorno -1
		if (estadoActual == DESCONECTADO)
		{
			pthread_mutex_unlock(&mutex_estadoActual);
			return -1;
		}
		else
		{

			//Sino Leo del buffer

			pthread_mutex_unlock(&mutex_estadoActual);

			pthread_mutex_lock(&mutex_buffer);
				cantDatosLeidosBuffer = readBuffer((char*) buf, len, buffer);
			pthread_mutex_unlock(&mutex_buffer);

			//log("---leerPCT cantDatosLeidosBuffer "+intToStr(cantDatosLeidosBuffer),LOGGER_DEBUG);

			return cantDatosLeidosBuffer;

		}

}

int cerrarPCT(){

	int termineMandar = 0;
	int resEnvio = 0;
	int cantIntentos = 1;

	if (rol == EMISOR)
	{
		log("pct.cpp --- cerrarPCT > El que cierra es el EMISOR ",LOGGER_INFO);

		pthread_mutex_lock(&mutex_estadoActual);

		if (estadoActual == CONECTADO)
		{
			pthread_mutex_unlock(&mutex_estadoActual);

			log("pct.cpp --- cerrarPCT > Cuando cierra esta CONECTADO ",LOGGER_INFO);

			pthread_mutex_lock(&mutex_buffer);
			//Invocar funcion de GBN que indica que todos los acks han sido recibidos
			termineMandar = esVacioGBN();
			int isemptybuf = 0;
			while ((not ((isEmptyBuffer(buffer)) && (termineMandar == 1))) && (estadoActual == CONECTADO))
			{
				pthread_mutex_unlock(&mutex_buffer);
				if (isEmptyBuffer(buffer)) {isemptybuf = 1;}
				else {isemptybuf = 0;}
				//log("pct.cpp --- cerrarPCT > Estoy dentro del while. termineMandar = "+intToStr(termineMandar) + "   isemptybuf = " + intToStr(isemptybuf),LOGGER_INFO);
				usleep(30000);// 30 milisegundos, el parametro es en microsegundos (1 millon de micros = 1 seg))
				pthread_mutex_lock(&mutex_buffer);
				termineMandar = esVacioGBN();
			}

			pthread_mutex_unlock(&mutex_buffer);

			log("pct.cpp --- cerrarPCT > Se vacio el buffer, envio el FIN ",LOGGER_INFO);

			resEnvio = enviarPaqueteControlDirecto (FIN_FLAG);


			if (resEnvio == 1)
			{
				log("pct.cpp --- cerrarPCT > Se envio exitosamente el FIN, espero el FIN-ACK ",LOGGER_INFO);

				saltaTimerFin = 0;
				pthread_create(&threadTimerFin, NULL, timerFin, NULL);

				pthread_mutex_lock(&mutex_estadoActual);
				while ((estadoActual != DESCONECTADO)&&(cantIntentos <= cantIntentosFin))
				{
					pthread_mutex_unlock(&mutex_estadoActual);
					//Me quedo esperando el fin-ack, reintento si no llega FinAck
					if (saltaTimerFin == 1)
					{
						cantIntentos = cantIntentos + 1;
						log("pct.cpp --- cerrarPCT > Se vacio el buffer, envio el FIN ",LOGGER_INFO);
						resEnvio = enviarPaqueteControlDirecto (FIN_FLAG);
						if (resEnvio == 1)
						{
							log("pct.cpp --- cerrarPCT > SALTO TIMER DEL FIN,  cantIntentos = "+intToStr(cantIntentos),LOGGER_INFO);
							saltaTimerFin = 0;
							pthread_create(&threadTimerFin, NULL, timerFin, NULL);
						}
						else
						{
							log("pct.cpp --- cerrarPCT > Error al enviar el FIN.No espero fin,ack. Libero recursos solamente.",LOGGER_INFO);
							destroyBuffer(buffer);
							destruirEmisor();
							return -1;
						}
					}
					pthread_mutex_lock(&mutex_estadoActual);
				}
				pthread_mutex_unlock(&mutex_estadoActual);

				/*pthread_mutex_lock(&mutex_estadoActual);
				while ((estadoActual != DESCONECTADO) && (saltaTimerFin == 0))
				{
					pthread_mutex_unlock(&mutex_estadoActual);
					//Me quedo esperando el fin-ack
					pthread_mutex_lock(&mutex_estadoActual);
				}
				pthread_mutex_unlock(&mutex_estadoActual);*/

				if (cantIntentos > cantIntentosFin)//(saltaTimerFin == 1)
				{
					log("pct.cpp --- cerrarPCT > Salto timer de espera del FIN-ACK, libero recursos. ",LOGGER_INFO);
					destroyBuffer(buffer);
					destruirEmisor();
					return -1;
				}
				else if (estadoActual == DESCONECTADO)
				{
					log("pct.cpp --- cerrarPCT > No salto el timer, salio pq el estado es DESCONECTADO.",LOGGER_INFO);
					destroyBuffer(buffer);
					destruirEmisor();
					return 0;
				}


				saltaTimerFin = 0;
			}
			else
			{
				log("pct.cpp --- cerrarPCT > Error al enviar el FIN.No espero fin,ack. Libero recursos solamente.",LOGGER_INFO);
				destroyBuffer(buffer);
				destruirEmisor();
				return -1;
			}

		}else
		{
			pthread_mutex_unlock(&mutex_estadoActual);

			log("pct.cpp --- cerrarPCT > Cuando cierra esta DESCONECTADO. Solo libero recursos.",LOGGER_INFO);

			destroyBuffer(buffer);
			destruirEmisor();
			return 0;
		}

	}
	else if (rol == RECEPTOR)
	{
		log("pct.cpp --- cerrarPCT > El que cierra es el RECEPTOR ",LOGGER_INFO);


		pthread_mutex_lock(&mutex_estadoActual);

		if (estadoActual == CONECTADO)
		{
			pthread_mutex_unlock(&mutex_estadoActual);

			log("pct.cpp --- cerrarPCT > Cuando cierra esta CONECTADO ",LOGGER_INFO);

			/*pthread_mutex_lock(&mutex_buffer);

			while (not (isEmptyBuffer(buffer)))
			{
				pthread_mutex_unlock(&mutex_buffer);
				log("pct.cpp --- cerrarPCT > Buffer no vacio ",LOGGER_INFO);
				usleep(30000);// 30 milisegundos, el parametro es en microsegundos (1 millon de micros = 1 seg))
				pthread_mutex_lock(&mutex_buffer);
			}

			pthread_mutex_unlock(&mutex_buffer);*/

			log("pct.cpp --- cerrarPCT > Envio el FIN ",LOGGER_INFO);

			resEnvio = enviarPaqueteControlDirecto (FIN_FLAG);

			if (resEnvio == 1)
			{
				log("pct.cpp --- cerrarPCT > Se envio exitosamente el FIN, espero el FIN-ACK ",LOGGER_INFO);

				saltaTimerFin = 0;
				pthread_create(&threadTimerFin, NULL, timerFin, NULL);


				pthread_mutex_lock(&mutex_estadoActual);
				while ((estadoActual != DESCONECTADO)&&(cantIntentos <= cantIntentosFin))
				{
					//log("pct.cpp --- cerrarPCT > Estoy en while (estadoActual != DESCONECTADO)&&(cantIntentos <= cantIntentosFin)"+intToStr(cantIntentos),LOGGER_INFO);
					pthread_mutex_unlock(&mutex_estadoActual);
					//Me quedo esperando el fin-ack, reintento si no llega FinAck
					if (saltaTimerFin == 1)
					{
						cantIntentos = cantIntentos + 1;
						log("pct.cpp --- cerrarPCT > Se vacio el buffer, envio el FIN ",LOGGER_INFO);
						resEnvio = enviarPaqueteControlDirecto (FIN_FLAG);
						if (resEnvio == 1)
						{
							log("pct.cpp --- cerrarPCT > SALTO TIMER DEL FIN,  cantIntentos = "+intToStr(cantIntentos),LOGGER_INFO);
							saltaTimerFin = 0;
							pthread_create(&threadTimerFin, NULL, timerFin, NULL);
						}
						else
						{
							log("pct.cpp --- cerrarPCT > Error al enviar el FIN.No espero fin,ack. Libero recursos solamente.",LOGGER_INFO);
							destroyBuffer(buffer);
							destruirEmisor();
							return -1;
						}
					}
					pthread_mutex_lock(&mutex_estadoActual);
				}
				pthread_mutex_unlock(&mutex_estadoActual);

				/*pthread_mutex_lock(&mutex_estadoActual);
				while ((estadoActual != DESCONECTADO) && (saltaTimerFin == 0))
				{
					pthread_mutex_unlock(&mutex_estadoActual);
					//Me quedo esperando el fin-ack
					pthread_mutex_lock(&mutex_estadoActual);
				}
				pthread_mutex_unlock(&mutex_estadoActual);*/

				if (cantIntentos > cantIntentosFin)//(saltaTimerFin == 1)
				{
					log("pct.cpp --- cerrarPCT > Salto timer de espera del FIN-ACK, libero recursos. ",LOGGER_INFO);
					destroyBuffer(buffer);
					destruirEmisor();
					return -1;
				}
				else if (estadoActual == DESCONECTADO)
				{
					log("pct.cpp --- cerrarPCT > No salto el timer, salio pq el estado es DESCONECTADO.",LOGGER_INFO);
					destroyBuffer(buffer);
					destruirEmisor();
					return 0;
				}


				saltaTimerFin = 0;
			}
			else
			{
				log("pct.cpp --- cerrarPCT > Error al enviar el FIN.No espero fin,ack. Libero recursos solamente.",LOGGER_INFO);
				destroyBuffer(buffer);
				destruirEmisor();
				return -1;
			}


		}else
		{
			pthread_mutex_unlock(&mutex_estadoActual);

			log("pct.cpp --- cerrarPCT > Cuando cierra esta DESCONECTADO. Solo libero recursos.",LOGGER_INFO);

			destroyBuffer(buffer);
			destruirEmisor();
			return 0;
		}

	}else
	{
		log("pct.cpp --- cerrarPCT Rol no inicializado",LOGGER_INFO);
		return -1;
	}
}

void * leeDatosYEnviaBuffer (void * param){

	//log ("pct.cpp - ENTRE A leeDatosYEnviaBuffer",LOGGER_DEBUG);

	char* datosPaquete = NULL;
	int resultadoEscrituraBuffer = 0;
	int resultadoEnviaDatosGBN = 0;
	int iterar = 1;
	int esElQueEspero = 0 ;
	int resEnvio = 0;

	pthread_mutex_lock(&mutex_estadoActual);

	while (estadoActual == CONECTADO)
	{
		pthread_mutex_unlock(&mutex_estadoActual);

		pthread_mutex_lock(&mutex_buffer);
		if (not (isFullBuffer(buffer))){

			pthread_mutex_unlock(&mutex_buffer);

			datosPaquete = new char[MAX_PCT_DATA_SIZE];
			datosPaquete[0] = '\0';

			size_t len = 0;
			unsigned char flag = '\0';

			pthread_create(&kalThread,NULL,timerKalReceptor,NULL);

			log ("pct.cpp - leeDatosYEnviaBuffer > Voy a invocar a recibirPaquete del pcutils",LOGGER_DEBUG);
			int rtnRecibirPaquete = recibirPaquete(datosPaquete, len, flag);
			log ("pct.cpp - leeDatosYEnviaBuffer > el retorno de rtnRecibirPaquete: "+  intToStr(rtnRecibirPaquete),LOGGER_DEBUG);

			switch (flag) {
				case DATA_FLAG: // rtnRecibirPaquete retorna el numsec

					pthread_mutex_lock(&mutex_estadoActual);
						pthread_cancel(kalThread);
					pthread_mutex_unlock(&mutex_estadoActual);

					log ("pct.cpp - leeDatosYEnviaBuffer > Recibi un DATA_FLAG con nro de sec: "+intToStr(rtnRecibirPaquete),LOGGER_INFO);
					log ("pct.cpp - leeDatosYEnviaBuffer > Voy a invocar a rdtRecibirReceptor del GBN",LOGGER_DEBUG);
					esElQueEspero = rdtRecibirReceptor(rtnRecibirPaquete);
					if(esElQueEspero > 0){
						log ("pct.cpp - leeDatosYEnviaBuffer > Es el paquete que esperaba, lo voy a cargar en el buffer de lectura con writeBuffer",LOGGER_DEBUG);
						pthread_mutex_lock(&mutex_buffer);
						resultadoEscrituraBuffer = writeBuffer(datosPaquete,len,buffer);
									log ("pct.cpp - leeDatosYEnviaBuffer > El writeBuffer escribio bytes: "+ intToStr(resultadoEscrituraBuffer),LOGGER_DEBUG);
									if (resultadoEscrituraBuffer<len){ // falta por escribir datos
										int datosPorEscribir = len - resultadoEscrituraBuffer;
										while(datosPorEscribir>0){
											datosPorEscribir = writeBuffer(datosPaquete+resultadoEscrituraBuffer,datosPorEscribir,buffer);
											datosPorEscribir = len - resultadoEscrituraBuffer;
										}
									}
						pthread_mutex_unlock(&mutex_buffer);
					}
					break;
				case ACK_FLAG:

					pthread_mutex_lock(&mutex_estadoActual);
						pthread_cancel(kalThread);
					pthread_mutex_unlock(&mutex_estadoActual);

					log ("pct.cpp - leeDatosYEnviaBuffer > Recibi un ACK_FLAG con nro de ack: "+intToStr(rtnRecibirPaquete),LOGGER_INFO);
						//como receptor no recivo ACK
					break;
				case FIN_FLAG:

					pthread_mutex_lock(&mutex_estadoActual);
						pthread_cancel(kalThread);
					pthread_mutex_unlock(&mutex_estadoActual);

					log ("pct.cpp - leeDatosYEnviaBuffer > Recibi un FIN_FLAG",LOGGER_INFO);

					pthread_mutex_lock(&mutex_buffer);

					while (not (isEmptyBuffer(buffer)))
					{
						log ("pct.cpp - leeDatosYEnviaBuffer > BUFFER NO VACIO",LOGGER_INFO);
						pthread_mutex_unlock(&mutex_buffer);
						usleep(30000);// 30 milisegundos, el parametro es en microsegundos (1 millon de micros = 1 seg))
						pthread_mutex_lock(&mutex_buffer);
					}

					pthread_mutex_unlock(&mutex_buffer);

					log ("pct.cpp - leeDatosYEnviaBuffer > BUFFER VACIO",LOGGER_INFO);

					pthread_mutex_lock(&mutex_estadoActual);
						log ("pct.cpp - leeDatosYEnviaBuffer > Marco estadoActual = DESCONECTADO",LOGGER_INFO);
						estadoActual = DESCONECTADO;
					pthread_mutex_unlock(&mutex_estadoActual);

					resEnvio = enviarPaqueteControlDirecto (FIN_FLAG + ACK_FLAG);

					log ("pct.cpp - leeDatosYEnviaBuffer > Envie FIN-ACK, enviarPaqueteControlDirecto retorno "+ intToStr(resEnvio),LOGGER_INFO);

					break;
				case FIN_FLAG + ACK_FLAG:

					pthread_mutex_lock(&mutex_estadoActual);
						pthread_cancel(kalThread);
					pthread_mutex_unlock(&mutex_estadoActual);

					log ("pct.cpp - leeDatosYEnviaBuffer > Recibi un FIN-ACK",LOGGER_DEBUG);
					pthread_mutex_lock(&mutex_estadoActual);
						estadoActual = DESCONECTADO;
					pthread_mutex_unlock(&mutex_estadoActual);
					break;
				case 0: // Keep a Live

					pthread_mutex_lock(&mutex_estadoActual);
						pthread_cancel(kalThread);
					pthread_mutex_unlock(&mutex_estadoActual);

					log ("pct.cpp - leeDatosYEnviaBuffer > Recibi un KAL ",LOGGER_INFO);
					//reciboKeepALiveGBN();
					break;
				default:
					break;
			}
			pthread_mutex_lock(&mutex_buffer);
		}
		pthread_mutex_unlock(&mutex_buffer);

		pthread_mutex_lock(&mutex_estadoActual);
	}

	pthread_mutex_unlock(&mutex_estadoActual);

	return (void *) 0;


}


