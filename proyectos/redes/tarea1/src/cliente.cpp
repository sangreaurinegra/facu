#include "cliente.h"




char* obtenerIP(char* request){

	struct addrinfo *direccionInfo;
	char * nombreHost = getHost(request);

	//log("buscarEnWeb hostName obtenido *"+string(nombreHost)+"*", LOGGER_DEBUG);

	int error = getaddrinfo(nombreHost, NULL, NULL, &direccionInfo);

	if (error != 0)	{
		log("obtenerIP No se pudo obtener la direccion ", LOGGER_INFO);
		return 0;
	}
	struct sockaddr_in  *direccionIPV4 = (struct sockaddr_in *) direccionInfo->ai_addr;
	char * ip = inet_ntoa(direccionIPV4->sin_addr);
	log("ip reconocida "+string(ip), LOGGER_DEBUG);
	if(direccionInfo!=0)
		freeaddrinfo(direccionInfo);

	if (nombreHost != NULL) {delete[] nombreHost;}
	return ip;
}

int mandarReponseDesdeCacheANavegador(char * resLectura, int largoResponse, int socketCliente) {
	int Datos_EscritosAux = 0;
	int largodatosescritos = 0;
	int Datos_Escritos = 0;

	largodatosescritos = largoResponse;


	while (largodatosescritos > 0)
	{
		//log("EscribirEnSocket: Escribo en el socket del navegador lo que mando el servidor web ",LOGGER_TRACE);
		Datos_Escritos = 0;
		Datos_Escritos = EscribirEnSocketServWeb(socketCliente, resLectura + Datos_EscritosAux, largodatosescritos);

		Datos_EscritosAux = Datos_Escritos;
		largodatosescritos = largodatosescritos - Datos_EscritosAux;

	}

	log("OBJETO QUE MANDO DESDE LA CACHE"+string(resLectura), LOGGER_DEBUG);

	delete[] resLectura;

	return 1;

}

const char* buscarEnWeb(char* request, int largoRequest, int socketCliente, int max_object_size, int& bytesLeidos){

	char* res = NULL;
	char* response;
	int largoResponse = 0;
	int flagSalir = 0;
	char * resLecura =  NULL;
	char* ip = obtenerIP(request);
	int largodatosescritos = 0;
	int datosEscritosServWeb = 0;
	char* resError = NULL;
	int Datos_EscritosAux;
	int socketServidorWeb;
	int datosLeidosServWeb;

	if(ip!=0){

		log("buscarEnWeb AperturaSocketComoCliente: Abro la conexion con el servidor ",LOGGER_TRACE);
		socketServidorWeb = AperturaSocketComoCliente(ip, 80);

		datosEscritosServWeb = EscribirEnSocketServWeb(socketServidorWeb, request, largoRequest);
		delete[] request;
		log("buscarEnWeb LeerDelSocket: Leo el mensaje que envia el servidor web ",LOGGER_TRACE);

		response = new char[BUFFER_LECTURA_CLIENTE+1];
		datosLeidosServWeb = 0;
		int dataSize = BUFFER_LECTURA_CLIENTE;
		datosLeidosServWeb = LeerDelSocketServWeb (socketServidorWeb, response, dataSize);

		largoResponse = 0;
		int largototalactual;
		char* resaux = NULL;


		res = new char[datosLeidosServWeb + 1];
		res[0] = '\0';
		memcpy(res,response,datosLeidosServWeb);
		res[datosLeidosServWeb]= '\0';
		largoResponse = datosLeidosServWeb;

		if (largoResponse > max_object_size)
		{
			flagSalir = 1;
		}

		while (datosLeidosServWeb > 0 && flagSalir == 0){

			log("EscribirEnSocket: Leo del socket del servidor webb",LOGGER_TRACE);

			response = new char[BUFFER_LECTURA_CLIENTE+1];
			dataSize = BUFFER_LECTURA_CLIENTE;
			datosLeidosServWeb = 0;
			datosLeidosServWeb = LeerDelSocketServWeb (socketServidorWeb, response, dataSize);



			if (datosLeidosServWeb > 0 )
			{

				largototalactual = largoResponse + datosLeidosServWeb;
				resaux = new char[largototalactual + 1];
				memcpy(resaux,res,largoResponse);
				resaux[largoResponse + 1] = '\0';
				memcpy(resaux + largoResponse,response,datosLeidosServWeb);
				resaux[largototalactual] = '\0';

				delete[] res;

				res = new char[largototalactual + 1];
				res[0] = '\0';

				memcpy(res,resaux,largoResponse + datosLeidosServWeb);
				res[largototalactual] = '\0';
				delete[] resaux;
				largoResponse = largoResponse + datosLeidosServWeb;

				if (largoResponse > max_object_size)
				{
					flagSalir = 1;
				}

			}
		}

		if (flagSalir == 0)
		{
			resLecura = new char[largoResponse + 1];
			resLecura[0] = '\0';
			memcpy(resLecura,res,largoResponse);
			resLecura[largoResponse] = '\0';
			Datos_EscritosAux = 0;
			largodatosescritos = largoResponse;

			while (largodatosescritos > 0 && datosEscritosServWeb != -1)
			{
				//log("EscribirEnSocket: Escribo en el socket del navegador lo que mando el servidor web ",LOGGER_TRACE);
				datosEscritosServWeb = 0;
				datosEscritosServWeb = EscribirEnSocketServWeb(socketCliente, resLecura + Datos_EscritosAux, largodatosescritos);

				Datos_EscritosAux = datosEscritosServWeb;
				largodatosescritos = largodatosescritos - Datos_EscritosAux;

			}
		}else
		{
			char errorHeader[] = "HTTP/1.0 403 Forbidden\r\n\r\n";
			char errorBody[] = "<html><head><title>403 Forbidden</title></head><body><h1>ERROR 403 - Forbidden</h1><p><h4>Objeto demasiado grande para tranferir...</h4></p></body></html>";
			resError = new char[strlen(errorHeader)+strlen(errorBody)+1];
			resError[0] = '\0';
			strcat(resError,errorHeader);
			strcat(resError,errorBody);
			largodatosescritos = strlen(errorHeader)+strlen(errorBody);
		}

		delete[] res;
		delete[] response;

		close (socketServidorWeb);

	}else
	{
		flagSalir = 1;
		char errorHeader[] = "HTTP/1.0 502 Bad Gateway\r\n\r\n";
		char errorBody[] = "<html><head><title>502 Bad Gateway</title></head><body><h1>ERROR 502 - Bad Gateway</h1><p><h4>No se pudo obtener la IP del servidor...</h4></p></body></html>";
		char* resError = new char[strlen(errorHeader)+strlen(errorBody)+1];
		resError[0] = '\0';
		strcat(resError,errorHeader);
		strcat(resError,errorBody);
		largodatosescritos = strlen(errorHeader)+strlen(errorBody);
	}

	if (flagSalir == 1)
	{
		Datos_EscritosAux = 0;
		while (largodatosescritos > 0 && datosEscritosServWeb != -1)
		{
			datosEscritosServWeb = 0;
			datosEscritosServWeb = EscribirEnSocketServWeb(socketCliente, resError + Datos_EscritosAux, largodatosescritos);

			Datos_EscritosAux = datosEscritosServWeb;
			largodatosescritos = largodatosescritos - Datos_EscritosAux;

		}
	}

	bytesLeidos = largoResponse;
	return resLecura;
}







