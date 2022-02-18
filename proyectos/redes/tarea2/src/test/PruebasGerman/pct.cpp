#include "pct.h"


using namespace std;

Estado estadoActual = INIT;


int globalPctSocket;
//struct sockaddr_in globalDireccionSocket;

unsigned char globalPuertoOrigen;
unsigned char globalPuertoDestino;

struct in_addr globalIPOrigen;
struct in_addr globalIPDestino;

pthread_t threadLeoBufferYEnvio;
pthread_t threadRecibirPaqueteControl;
pthread_t threadLeoDatosYEnvioBuffer;

pthread_mutex_t mutex_buffer = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t mutex_estadoActual = PTHREAD_MUTEX_INITIALIZER;

buffer* buffer;



////////AUXILIARES////////////

void * leeDatosYEnviaBuffer (void * param);

void * leeBufferYEnviaDatos (void * param)
{
	char* datosPaquete = NULL;
	char* datosPaqueteEnvio = NULL;
	int resultadoLecturaBuffer = 0;
	int resultadoEnviaDatosGBN = 0;
	int iterar = 1;

	log("pct.cpp - leeBufferYEnviaDatos: >>> ENTRE Y VOY A ITERAR",LOGGER_TRACE);

	while(iterar == 1)
	{
		pthread_mutex_lock(&mutex_buffer);
		if (not (isEmptyBuffer(buffer)))
		{
			log("pct.cpp - leeBufferYEnviaDatos: El buffer no estaba vacio, voy a leer del buffer",LOGGER_TRACE);

			datosPaquete = new char[MAX_PCT_DATA_SIZE];
			datosPaquete[0] = '\0';

			//leo del buffer lo maximo que puedo
			resultadoLecturaBuffer = readBuffer(datosPaquete, MAX_PCT_DATA_SIZE, buffer);
			pthread_mutex_unlock(&mutex_buffer);

			log("pct.cpp - leeBufferYEnviaDatos: Lei del buffer "+ intToStr(resultadoLecturaBuffer) + " bytes",LOGGER_TRACE);

			//Redefino el char* para que efectivamente contenga unicamente los bytes leidos y NO mas.
			datosPaqueteEnvio = new char[resultadoLecturaBuffer];
			memcpy(datosPaqueteEnvio,datosPaquete,resultadoLecturaBuffer);

			log("pct.cpp - leeBufferYEnviaDatos: Voy a enviar al cliente con rdtEnviar esa cantidad de bytes.",LOGGER_TRACE);
			log("pct.cpp - leeBufferYEnviaDatos: Lo que voy a enviar: \n " + string(datosPaqueteEnvio),LOGGER_TRACE);

			//Intento enviar EL paquete con los datos leidos del buffer al receptor
			resultadoEnviaDatosGBN = rdtEnviar(datosPaqueteEnvio, resultadoLecturaBuffer);

			log("pct.cpp - leeBufferYEnviaDatos:>>>>> Envie " + intToStr(resultadoEnviaDatosGBN) + "bytes" ,LOGGER_TRACE);

			switch (resultadoEnviaDatosGBN)
			{
				//Si intento enviar un paquete fuera de la ventana espero a que se corra y me permite enviar
				case -1:
					log("pct.cpp - leeBufferYEnviaDatos: Salgo de la rutina por recibir -2 de rdtEnviar (KeepAlive no llego)",LOGGER_TRACE);
					while (resultadoEnviaDatosGBN == -1)
					{
						usleep(3000);// 3 milisegundos, el parametro es en microsegundos (1 millon de micros = 1 seg))
						resultadoEnviaDatosGBN = rdtEnviar(datosPaqueteEnvio, resultadoLecturaBuffer);
					}
					break;
				//Si se perdio la conexion con el receptor detengo el envio de datos al mismo
				case -2:
					log("pct.cpp - leeBufferYEnviaDatos: Salgo de la rutina por recibir -2 de rdtEnviar (KeepAlive no llego)",LOGGER_TRACE);
					delete [] datosPaquete;
					datosPaquete = NULL;
					delete [] datosPaqueteEnvio;
					datosPaqueteEnvio = NULL;
					iterar = 0;
					break;
				//Si se enviaron los datos exitosamente libero memoria
				case 0:
					log("pct.cpp - leeBufferYEnviaDatos:exitoso)",LOGGER_TRACE);

					delete [] datosPaquete;
					datosPaquete = NULL;
					delete [] datosPaqueteEnvio;
					datosPaqueteEnvio = NULL;
					break;
			}

			pthread_mutex_lock(&mutex_buffer);
		}else
		{
			//log("pct.cpp - leeBufferYEnviaDatos: BUFFER VACIO",LOGGER_TRACE);
		}
		pthread_mutex_unlock(&mutex_buffer);
	}

	return (void *) 0;


}

void * recepcionPaqueteControl (void * param)
{

	int  numRes = 0;
	int iterar = 1;
	size_t largoDatosALeer = 0;
	char* datosPaquete = NULL;
	unsigned char tipoPaquete;

	while (iterar == 1)
	{

		log("pct.cpp - recepcionPaqueteControl: Llamo a la funcion  recibirPaquete de Utils",LOGGER_TRACE);
		//LLamo a la funcion de pctUtils que da soporte a la recepcion de ack's de parte del receptor
		//la cual retorna el nro de ack que corresponde. (Dentro de la funcion hay una espera implementada que debe ser mayor a la de GBN)
		datosPaquete = new char[MAX_PCT_DATA_SIZE];
		datosPaquete[0] = '\0';
		numRes = recibirPaquete(datosPaquete, largoDatosALeer, tipoPaquete);

		log("pct.cpp - recepcionPaqueteControl: recibirPaquete retorno "+ intToStr(numRes),LOGGER_TRACE);

		delete [] datosPaquete;
		datosPaquete = NULL;

		//Si es un ACK
		if ((numRes != -1) && (tipoPaquete == ACK_FLAG))
		{
			//Llamo a la funcion que da soporte a la recepcion de acks del GBN, de forma de correr la ventans si corresponde
			rdtRecibirEmisor(numRes);
		}
		//Si es un paquete de control KeepAlive
		else if ((numRes != -1) && (tipoPaquete == 0))
		{
			//Hacer lo que corresponda - Hay que ver donde se hace soporte a este tema
		}
		//Si es un paquete de FIN-ACK
		else if ((numRes != -1) && (tipoPaquete == (ACK_FLAG + FIN_FLAG)))
		{
			//Quiere decir que se cierra la conexion, hay que setear iterar = 0 ?
			iterar = 0;
		}
		//Si es un paquete de FIN quiere decir que el receptor desea cerrar la conexion, antes de responder un fin-ack debo
		//asegurarme de que envie todo lo que tengo en el buffer
		else if ((numRes != -1) && (tipoPaquete == FIN_FLAG))
		{
			//Hay que controlar que el buffer este vacio y que haya llegado el ack para el ultimo paquete enviado
			//Lo del buffer se sabe a este nivel, para lo del ack se necesita soporte de GBN
		}
	}

	return (void *) 0;

}

int crearPCT(struct in_addr localIPaddr) {
	setLogLevel(LOGGER_INFO);
	log("crearPCT",LOGGER_TRACE);
	if (estadoActual != INIT) {
		log("Error crearPCT: El estado es distinto a INIT",LOGGER_INFO);
		return -1;
	}

	globalPctSocket = socket(AF_INET, SOCK_RAW, IPPROTO_RAW);

	if (globalPctSocket < 0) {
		log("Error crearPCT: creando Socket",LOGGER_INFO);
		return -1;
	}
	log("crearPCT  socket creado numero: "+ intToStr(globalPctSocket),LOGGER_TRACE);

	estadoActual = DESCONECTADO;
	globalIPOrigen = localIPaddr;

	char  str[20];
	inet_ntop(AF_INET, &(globalIPOrigen.s_addr), str, INET_ADDRSTRLEN);

	log("crearPCT  globalIPOrigen: "+ string(str),LOGGER_TRACE);

	//Creo el Buffer correspondiente de escritura o lectura dependiendo si es el emisor o receptor
	buffer = createBuffer(TAM_BUFFER);

	return globalPctSocket;
}

int aceptarPCT(unsigned char localPCTport){

	globalPuertoOrigen = localPCTport;


	if (estadoActual != DESCONECTADO) {
		log("Error aceptarPCT: El estado es distinto a DESCONECTADO",LOGGER_INFO);
		return -1;
	}

	estadoActual = ESPERANDO_CONEXION;

	log("aceptarPCT esperando SYN",LOGGER_DEBUG);

	//Espero un SYN
	if (recibirPaqueteControl( SYN_FLAG ) == -1) {
		estadoActual = DESCONECTADO;
		return -1;
	}
	log("aceptarPCT enviando SYN ACK",LOGGER_DEBUG);

	//Ya recibi un SYN, envio un SYN ACK
	if (enviarPaqueteControlDirecto( SYN_FLAG + ACK_FLAG) == -1) {
		estadoActual == DESCONECTADO;
		return -1;
	}


	estadoActual = RECIBI_SYN;
	log("aceptarPCT recibiendo ACK",LOGGER_DEBUG);

	if (recibirPaqueteControl( ACK_FLAG ) == -1) {
		estadoActual = DESCONECTADO;
		return -1;
	}
	log("aceptarPCT conexion lograda",LOGGER_DEBUG);

	estadoActual = CONECTADO;

	log("pct.cpp - conectarPCT:>>>>>>>>> Tiro el hilo leeDatosYEnviaBuffer",LOGGER_TRACE);
	pthread_create(&threadLeoDatosYEnvioBuffer, NULL, leeDatosYEnviaBuffer, NULL);


	return 0;
}

int conectarPCT(unsigned char localPCTport,unsigned char peerPCTPport, struct in_addr peerIPaddr){
	log("conectarPCT primera linea ",LOGGER_TRACE);

	if (estadoActual != DESCONECTADO) {
		log("Error conectarPCT: El estado es distinto a DESCONECTADO",LOGGER_INFO);
		return -1;
	}


    globalIPDestino = peerIPaddr;

    globalPuertoOrigen = localPCTport;
    globalPuertoDestino = peerPCTPport;

    if (estadoActual == DESCONECTADO) {
    	log("conectarPTC envio SYN ",LOGGER_TRACE);

    	if( enviarPaqueteControlDirecto( SYN_FLAG ) == -1) {
        	log("ERROR enviarPaquete, Desconectando ",LOGGER_TRACE);

    		estadoActual = DESCONECTADO;
    		return -1;
    	}

    	estadoActual = ESPERANDO_SYN_ACK;
    	log("conectarPTC espero SYN_ACK ",LOGGER_TRACE);

    	if (recibirPaqueteControl(SYN_FLAG + ACK_FLAG) == -1) {
        	log("ERROR recibirPAquete, Desconectando ",LOGGER_TRACE);

    		estadoActual = DESCONECTADO;
    		return -1;
    	}

    	log("conectarPTC envio ACK ",LOGGER_TRACE);

    	//Ya recibi un SYN ACK, envio el ACK
    	if (enviarPaqueteControlDirecto( ACK_FLAG) == -1) {
    		estadoActual == DESCONECTADO;
    		return -1;
    	}


    	//Aca hago un nuevo hilo para enviar datos?
    	log("conectarPTC CONECTADO ",LOGGER_TRACE);

    	estadoActual = CONECTADO;

    	log("pct.cpp - aceptarPCT: Creo la ventana invocando a inicioEmisor",LOGGER_TRACE);
		//Inicializo la ventana del GBN
		inicioEmisor();

		log("pct.cpp - aceptarPCT:>>>>>>>>> Tiro los hilos leeBufferYEnviaDatos y recepcionPaqueteControl",LOGGER_TRACE);
		pthread_create(&threadLeoBufferYEnvio, NULL, leeBufferYEnviaDatos, NULL);
		pthread_create(&threadRecibirPaqueteControl, NULL, recepcionPaqueteControl, NULL);


    }
	return 0;
}


int escribirPCT(const void *buf, size_t len){

	log("pct.cpp - escribirPCT:>>>>>>>>> ENTRE A  escribirPCT",LOGGER_TRACE);

	int cantDatosEscritosBuffer = 0;

	pthread_mutex_lock(&mutex_estadoActual);

	//Si estoy desconectado retorno -1
	if (estadoActual == DESCONECTADO)
	{
		log("pct.cpp - escribirPCT:>>>>>>>>> ESTADO DESCONECTADO - ME LAS TOMO RETORNANDO -1 , NO ESCRIBO EN BUFFER!!! :(",LOGGER_TRACE);
		pthread_mutex_unlock(&mutex_estadoActual);
		return -1;
	}
	else
	{
		log("pct.cpp - escribirPCT: Voy a escribir en el buffer.",LOGGER_TRACE);
		//log("pct.cpp - escribirPCT: Lo que voy a escribir: \n." + string(buf),LOGGER_TRACE);

		//Sino retorno la cantidad escrita de datos en bytes

		pthread_mutex_unlock(&mutex_estadoActual);

		pthread_mutex_lock(&mutex_buffer);
			cantDatosEscritosBuffer = writeBuffer((char*) buf, len, buffer);
			//log("pct.cpp - escribirPCT: IMPRIMO BUFFER: \n",LOGGER_TRACE);
			//printBuffer(buffer);
			//log("\n",LOGGER_TRACE);
		pthread_mutex_unlock(&mutex_buffer);

		log("pct.cpp - escribirPCT: Retorno la cantidad de bytes entregados a la capa inferior = "+ intToStr(cantDatosEscritosBuffer),LOGGER_TRACE);
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

	log("---leerPCT",LOGGER_DEBUG);
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

			log("---leerPCT cantDatosLeidosBuffer "+intToStr(cantDatosLeidosBuffer),LOGGER_DEBUG);

			return cantDatosLeidosBuffer;

		}

}

int cerrarPCT(){


	return 1;
}

void * leeDatosYEnviaBuffer (void * param){

	log ("pct.cpp - ENTRE A leeDatosYEnviaBuffer",LOGGER_DEBUG);

	char* datosPaquete = NULL;
	int resultadoEscrituraBuffer = 0;
	int resultadoEnviaDatosGBN = 0;
	int iterar = 1;
	int esElQueEspero = 0 ;
	//Inicializo gbn receptor

	while(iterar == 1) 	{
		pthread_mutex_lock(&mutex_buffer);
		if (not (isFullBuffer(buffer))){

			pthread_mutex_unlock(&mutex_buffer);

			datosPaquete = new char[MAX_PCT_DATA_SIZE];
			datosPaquete[0] = '\0';

			size_t len = 0;
			unsigned char flag = '\0';

			log ("pct.cpp - leeDatosYEnviaBuffer > Voy a invocar a recibirPaquete del pcutils",LOGGER_DEBUG);
			int rtnRecibirPaquete = recibirPaquete(datosPaquete, len, flag);
			log ("pct.cpp - leeDatosYEnviaBuffer > el retorno de rtnRecibirPaquete: "+  intToStr(rtnRecibirPaquete),LOGGER_DEBUG);

			switch (flag) {
				case DATA_FLAG: // rtnRecibirPaquete retorna el numsec
					log ("pct.cpp - leeDatosYEnviaBuffer > Recibi un DATA_FLAG",LOGGER_DEBUG);
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
					log ("pct.cpp - leeDatosYEnviaBuffer > Recibi un ACK_FLAG",LOGGER_DEBUG);
						//como receptor no recivo ACK
					break;
				case 0: // Keep a Live
					log ("pct.cpp - leeDatosYEnviaBuffer > Recibi un 0",LOGGER_DEBUG);
					break;
				default:
					break;
			}
			pthread_mutex_lock(&mutex_buffer);
		}
		pthread_mutex_unlock(&mutex_buffer);
	}

	return (void *) 0;


}


