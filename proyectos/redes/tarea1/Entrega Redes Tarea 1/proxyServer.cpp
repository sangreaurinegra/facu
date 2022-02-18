#include <pthread.h>

#include "admin.h"
#include "cliente.h"
#include "cacheHandler.h"


#define PUERTO_DEFECTO_ADMIN 6666
int PUERTO_DEFECTO_CLIENTE = 5555;
string IP_SERVIDOR = "127.0.0.1";


fd_set fdSet;
int socketAdmin;
int socketCliente;

pthread_t threadAdmin;
pthread_t threadCliente;


uint64_t max_object_size = 104857600; // en bytes , pordefecto 10 megas valor maximo de ulong int 64 -1 = 18446744073709551615;
uint64_t max_cached_object_size = 102400 ;// en bytes por defecto 100kb ,determina el mayor tamaño de objeto cacheable
uint64_t max_object_count = 200; // determina la cantidad máxima de objetos a almacenar en RAM


uint64_t cantidadEntregados = 0;
uint64_t cantidadRequest = 0;

pthread_mutex_t mutex_max_object_size = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t mutex_cantidadEntregados = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t mutex_cantidadRequest = PTHREAD_MUTEX_INITIALIZER;

pthread_mutex_t mutex_cache = PTHREAD_MUTEX_INITIALIZER;


cache* laCache = 0;


void cantidadEntregadosPlusPlus(){
	pthread_mutex_lock(&mutex_cantidadEntregados);
	cantidadEntregados++;
	pthread_mutex_unlock(&mutex_cantidadEntregados);
}

void cantidadRequestPlusPlus(){
	pthread_mutex_lock(&mutex_cantidadRequest);
	cantidadRequest++;
	pthread_mutex_unlock(&mutex_cantidadRequest);
}


string ejecutarComando(string comando, int socket, int & salir){
	string ret = "Ok";
	int intCom = getComando(comando);

	switch (intCom) {
		case show_run:
			showRun(socket, cantidadEntregados, cantidadRequest,max_object_size, laCache);
			break;
		case purge:
			pthread_mutex_lock(&mutex_cache);
			ejecutarPurge(socket, laCache);
			pthread_mutex_unlock(&mutex_cache);
			break;
		case set_max_object_size:
			if(MAX_VALOR_SOPORTADO >  getValor(comando)){
				pthread_mutex_lock(&mutex_max_object_size);
				setMaxObjectSize(socket, max_object_size,getValor(comando));
				pthread_mutex_unlock(&mutex_max_object_size);
			}else{
				log("Valor del comando set_max_object_size no reconocido" , LOGGER_INFO);
			}
			break;
		case set_max_cached_object_size:
			if(MAX_VALOR_SOPORTADO >  getValor(comando)){
				pthread_mutex_lock(&mutex_max_object_size);
				setMaxCachedObjectSize(socket, max_cached_object_size,getValor(comando),laCache);
				pthread_mutex_unlock(&mutex_max_object_size);
			}else{
				log("Valor del comando max_cached_object_size no reconocido" , LOGGER_INFO);
			}
			break;
		case set_max_object_count:
			if(MAX_VALOR_SOPORTADO >  getValor(comando)){
				pthread_mutex_lock(&mutex_max_object_size);
				setMaxObjectCount(socket, max_object_count,getValor(comando),laCache);
				pthread_mutex_unlock(&mutex_max_object_size);
			}else{
				log("Valor del comando max_cached_object_size no reconocido" , LOGGER_INFO);
			}

			break;
		case quit:
		    log("ejecutarComando switch (intCom) quit" , LOGGER_DEBUG);
			ejecutarQuit(socket, salir);
			break;
		default:
			 log("ejecutarComando switch Comando no reconocido *****"+comando+"**********" , LOGGER_DEBUG);
			ret = "Comando no reconocido";
		break;
	}
	return ret;

}


void* adminHandler(void * param){
	log("Entro en admin handler ... ", LOGGER_DEBUG);
	string ret = "";
	string comando = "";
	char* entrada = 0;
	int socketAdmin = (int) param;

	int salir=0;
	while(salir==0){
		entrada = new char[BUFFER_LECTURA_ADMIN];
		bzero(entrada,BUFFER_LECTURA_ADMIN);
		log("Voy a socket del administrador ... ", LOGGER_DEBUG);
		int error = LeerDelSocketServWeb(socketAdmin, entrada, BUFFER_LECTURA_ADMIN);
		log("Lei socket del administrador ... ", LOGGER_DEBUG);
		if(error < 0){
			log("No se pudo leer el socket del administrador" , LOGGER_INFO);
		}else{
			comando = string(entrada);
			comando = comando.substr(0,comando.find("\r"));// limpio caracteres extras \r\n
			log("Se leyo del socket del administrador "+comando, LOGGER_DEBUG);
			ret = ejecutarComando(comando,socketAdmin,salir);

		}
		delete entrada;
	}
	log("Fin adminHandler"+comando , LOGGER_DEBUG);
	salir = 0;
	return (void *)ret.c_str();
}

void* clienteHandler(void * param){

	log("Inicio clienteHandler", LOGGER_DEBUG);
	const char* response = NULL;
	char* request = 0;
	int socketCliente = (int) param;
	int tamanioResponseBytes = 0;

	request = new char[BUFFER_LECTURA_CLIENTE + 1];
	//bzero(request,BUFFER_LECTURA_CLIENTE);
	//log("Voy a leer del cliente ... ", LOGGER_DEBUG);0
	log("Conectado cliente" +string(intToStr(socketCliente)), LOGGER_INFO);

	int datosLeidos = LeerDelSocketServWeb(socketCliente, request, BUFFER_LECTURA_CLIENTE);
	log("request__________" +string(request), LOGGER_INFO);

	if(datosLeidos < 0 ){
		log("No se pudo leer el socket del cliente" , LOGGER_INFO);
	}else{

		cantidadRequestPlusPlus(); // aumento la variable de request en 1

		log("* Consulto en CACHE request*\n\n"+string(request), LOGGER_DEBUG);
		if ((request != NULL) && (strlen(request) > 0)){
			pthread_mutex_lock(&mutex_cache);

			response = consultarCache(request, tamanioResponseBytes);
			pthread_mutex_unlock(&mutex_cache);

		}

		if(response!=NULL ){
			// si se encuentra en la cache retorno
			log("SI se encontro en CACHE", LOGGER_DEBUG);
			cantidadEntregadosPlusPlus();

			char* ptrResponse = new char[tamanioResponseBytes + 1];
			memcpy(ptrResponse,response, tamanioResponseBytes);
			ptrResponse[tamanioResponseBytes] = '\0';


			log("\n\n******************** REQUEST DE LA RESPONSE Q MANDO"+string(request),LOGGER_DEBUG);
			log("\n\n******************** ptrResponse"+string(ptrResponse),LOGGER_DEBUG);

			mandarReponseDesdeCacheANavegador(ptrResponse, tamanioResponseBytes, socketCliente);

			// si no se encuentra en la cache
		}else{
			log("NO se encontro en CACHE", LOGGER_DEBUG);
			// voy a buscar a web

			int bytesLeidos = 0;
			char* ptrRequest = new char[datosLeidos + 1];
			memcpy(ptrRequest,request, datosLeidos);
			ptrRequest[datosLeidos] = '\0';

			int max_object_size_param;

			pthread_mutex_lock(&mutex_max_object_size);
			max_object_size_param= max_object_size;
			pthread_mutex_unlock(&mutex_max_object_size);

			response = buscarEnWeb(ptrRequest, datosLeidos, socketCliente, max_object_size_param, bytesLeidos);
			log("bytesLeidos:"+intToStr(bytesLeidos), LOGGER_DEBUG);


			if (response != NULL)
			{
				pthread_mutex_lock(&mutex_cache);

					char* ptrResponse = new char[bytesLeidos + 1];
					ptrResponse[0] = '\0';
					memcpy(ptrResponse,response,bytesLeidos);
					ptrResponse[bytesLeidos] = '\0';


					log("VAMOS A CACHEAR\n", LOGGER_DEBUG);
					int resCache = cachear(request,ptrResponse,bytesLeidos);
					if (resCache) {
						log("YA CACHEO\n", LOGGER_DEBUG);

					} else {
						log("NO CACHEO\n", LOGGER_DEBUG);
					}
				pthread_mutex_unlock(&mutex_cache);
			}

		}

		 //ret = "<head><meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\" /><title>Respuesta de proxy</title></head><body>Toma pa vos</body></html>";
	}

	close(socketCliente);

	log("Fin clienteHandler", LOGGER_DEBUG);

	return (void *)response;
}


void init(){
	log("init ...",LOGGER_DEBUG);
	log("AperturaSocketComoServidor ",LOGGER_DEBUG);
	socketAdmin = AperturaSocketComoServidor(PUERTO_DEFECTO_ADMIN,IP_SERVIDOR);
	if(socketAdmin == -1){
		log("No se pudo abrir Socket Servidor Admin ",LOGGER_INFO);
		exit(-1);
	}
	log("AperturaSocketComoServidor ",LOGGER_DEBUG);
	socketCliente = AperturaSocketComoServidor(PUERTO_DEFECTO_CLIENTE,IP_SERVIDOR);
	if(socketCliente == -1){
		log("No se pudo abrir Socket Servidor Browser ",LOGGER_INFO);
		exit(-1);

	}

	log("Creo CacheHandler ",LOGGER_DEBUG);
	initCacheHandler(laCache);

}

int main(int argc, char *argv[]) {

setLogLevel(LOGGER_TRACE);
//log("Inicio proxyServer en "+intToStr(PUERTO_DEFECTO_CLIENTE) ,LOGGER_INFO);

if (argc == 3){ // 2 parametros
	IP_SERVIDOR = string(argv[1]);
	PUERTO_DEFECTO_CLIENTE = atoi(argv[2]);
	string salida = "Se reconocieron los parametros "+ IP_SERVIDOR;
	salida = salida +":";
	salida = salida +intToStr(PUERTO_DEFECTO_CLIENTE);
	log(salida,LOGGER_DEBUG);
}
else{
	if(argc == 2){ // 1 parametro
		IP_SERVIDOR = string(argv[1]);
		log("Se reconocio el parametro "+IP_SERVIDOR,LOGGER_DEBUG);
	}
}


init();

//Inicializacion de estructuras
//Carga de valores configurables (puertos y host)
//Apertura de Sockets del servidor (los que quedan escuchando...)

 while(true){
	    log("Inicio iteracion cargo FD_SET",LOGGER_DEBUG);

	    //Carga de estructura con los sockets paramentro de entrada para el select (pq se carga dentro del while ??)
		FD_ZERO(&fdSet); //FD_ZERO nos vacía el puntero, de forma que estamos indicando que no nos interesa ningún descriptor de fichero.
		FD_SET(socketAdmin, &fdSet);//FD_SET mete el descriptor que le pasamos en int al puntero fd_set.
		FD_SET(socketCliente, &fdSet);//De esta forma estamos indicando que tenemos interes en ese descriptor.

		log("Select ...",LOGGER_DEBUG);
		select(FD_SETSIZE, &fdSet, NULL, NULL, NULL);//llamada al select

		//Si se detecta peticion de consola administrativa
		if (FD_ISSET(socketAdmin,&fdSet)){// FD_ISSET nos indica si ha habido algo en el descriptor int dentro de fd_set
			log("Admin ingreso ...",LOGGER_INFO);

			int socketAdministradorAceptado = AceptarConexionComoServidor(socketAdmin);

			if(socketAdministradorAceptado < 0){
				log("Error al aceptar administrador",LOGGER_INFO);
			}else {
				log("Admin conectado.",LOGGER_INFO);

				try {
					//Creo hilo con la rutina de atencion a las consolas administrativas
					pthread_create(&threadAdmin, NULL, adminHandler,(void *)socketAdministradorAceptado);

				}catch (exception& e) {
					log( "ERROR TRY : pthread_create adminHandler "+string(e.what()),LOGGER_INFO);
				}
			}
		}
		if (FD_ISSET(socketCliente, &fdSet)){ //Si se detecta peticion de navegador

			log("Browser ingreso ...",LOGGER_INFO);

			int socketClienteAceptado = AceptarConexionComoServidor(socketCliente);

			if(socketClienteAceptado < 0){
				log("Error al aceptar cliente",LOGGER_INFO);
			}else {
				log("Browser conectado.",LOGGER_INFO);

				try {
					//Creo hilo con la rutina de atencion del browser
					pthread_create(&threadCliente, NULL, clienteHandler,(void *)socketClienteAceptado);
					//log("Browser desconectado."+string(intToStr(socketClienteAceptado)),LOGGER_INFO);

				}catch (exception& e) {
			        log( "ERROR TRY : pthread_create clienteHandler "+string(e.what()),LOGGER_INFO);
				}

			}

		}

 }

 pthread_join(threadAdmin, NULL);
 pthread_join(threadCliente, NULL);

//log("Fin proxyServer",LOGGER_INFO);// si bien no es necesario llegar aca puede estar bueno dar soporte de apagado por consola , que de de baja todo etc antes de finalizar

return 0;

}
