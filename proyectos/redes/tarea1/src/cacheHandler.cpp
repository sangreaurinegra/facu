#include "cacheHandler.h"
#include "parserLib.h"

#include <iostream>
using namespace std;

cache* refCache;

void initCacheHandler(cache* &laCache) {
	if (laCache == NULL) {
		laCache = initCache();

	}
	refCache = laCache;
}

char* consultarCache(char* request,  int &tamanioResponseBytes) {

	 if ( !isGet(request) && !isPost(request) ){
	 	 // RETORNO EL MENSAJE DE ERROR CORRESPONDIENTE INDICANDO QUE ES UN METODO NO VALIDO
		log("FAULT consultarCache: No se ejecuto metodo valido",LOGGER_DEBUG);

		char* respuesta = new char[512];

		// Armo el encabezado de la respuesta
		// aca no estoy seguro si retornar "200 OK" o "405 Method Not Allowed"
		if (isHttp10(request))
			strcpy(respuesta,"HTTP/1.0 405 Method Not Allowed\r\n\r\n");
		else
			strcpy(respuesta,"HTTP/1.1 405 Method Not Allowed\r\n\r\n");

		// Concateno el body de la respuesta
		strcat(respuesta,"<html><head><title>405 Method Not Allowed</title></head><body><h1>ERROR 405 - Method Not Allowed</h1><p><h4>Se realizo una peticion http con un metodo no permitido...</h4></p></body></html>");

		respuesta[strlen(respuesta)] = '\0';
		return respuesta;

	 }
	 else if (isPragmaNoCache(request) ){
		// DEBO IR A BUSCAR AL OBJETO AL SERVIDOR WEB SIN IMPORTAR SI LO TENGO O NO EN EL CACHE
		// LUEGO CUANDO INVOQUEN A cachear(req, resp), SI CORRESPONDE, ACTUALIZO ESA ENTRADA DEL CACHE
		// O LO INSERTO COMO OBJETO NUEVO. LUEGO LO ENVIO AL NAVEGADOR
		return NULL;

	 } else if (isAuthorization(request) ) {
	 	// DEBO IR A BUSCAR EL OBJETO AL SERVIDOR WEB Y RETORNARLO AL NAVEGADOR
		// SIN CACHEARLO
		return NULL;
	 }
	 else if (isGet(request)) {

		int modifiedSince = getModifiedSince(request);
	 	char* URL_solicitada = getUrl(request);

	 	nodoObjeto* obj = buscarObjetoCache(URL_solicitada,getToday(),refCache);
	 	if (URL_solicitada != NULL ) {delete URL_solicitada;}

	 	if (modifiedSince > 0){ // es una fecha valida: GET Conditional

	 		// encontre al objeto en el cache
	 		if (obj != NULL){

	 			int lastModifiedCache = getUltimaModificacionObj(obj);
	 			if (modifiedSince > lastModifiedCache){ // el objeto ha sido modificado
		 			return getResponse(obj);
	 			}
	 			else{ // el objeto no ha sido modificado

	 				// SE RETORNA UN 304 (not modified) Y NO SE INCLUYE AL OBJETO EN LA RESPUESTA
	 				char* respuesta = new char[256];

					// Armo la respuesta sin body
					if (isHttp10(request))
						strcpy(respuesta,"HTTP/1.0 304 Not Modified\r\n\r\n");
					else
						strcpy(respuesta,"HTTP/1.1 304 Not Modified\r\n\r\n");

					respuesta[strlen(respuesta)] = '\0';
					return respuesta;
	 			}
	 		}

	 		// No encontre al objeto en el cache
	 		else{
	 			return NULL;
	 		}
	 	}

	 	// modifiedSince no es valida o es NULL, por lo que se resuelve como un GET comun
	 	else{
	 		if (obj != NULL){ 	// encontre al objeto en el cache
	 			char * response = getResponse(obj);
	 			tamanioResponseBytes = getTamanioObj(obj);
	 			return response;
	 		}
	 		else{ 				// No encontre al objeto en el cache
	 			return NULL;
	 		}
	 	}
	 }
	 else  { // el request es un POST
		 return NULL;
	 }
}



int cachear (char* request, char* response, int tamanioResponseBytes) {

	int FDate = getDate(response);

	int FToday = getToday();

	int Fexpira;
	if ( !isExpires(response) )
		Fexpira = 0; // indica que nunca vence
	else
		Fexpira = getExpires(response); // si es -1 es una fecha invalida

	if (isAuthorization(request) || 					// Se requiere autenticacion en el servidor
		(Fexpira == -1) || 								// fecha de expiracion invalida
		((FDate != -1) && (Fexpira != 0) && (Fexpira <= FDate)) ||		// fecha de expiracion valida <= fecha Date valida
		((Fexpira != 0) && (Fexpira <= FToday)) ||							// fecha de expiracion valida  <= fecha Actual valida
		(tamanioResponseBytes > getTamanioPermitidoMaxCache(refCache)) || // El tamanio del objeto es mas grande que el permitido
		(strcmp(getStateCode(response),"200")!=0) ||	// No es un codigo de estado OK
		 (isPragmaNoCache(response))
		)
	   {
		log("CACHEAR: LO RETORNO AL NAVEGADOR SIN CACHERLO \n",LOGGER_DEBUG);
		// LO RETORNO AL NAVEGADOR SIN CACHEARLO
		return -1;
	   }

	else {
		// VAMOS A CACHEAR O A ACTUALIZAR LA CACHE
		// DUDA1: Modificamos la primera línea del response donde se indica que versión de HTTP se está utilizando, y cambiar el HTTP/1.1 por HTTP/1.0. ??
		// DUDA2: Eliminar todos los headers del response que sean exclusivos de HTTP/1.1 y por lo tanto no existan en HTTP/1.0 ??

		if (!isHttp10(response))
			cambioResponseHttp11A10(response);

		// Obtengo identificador = Url
		char* url = getUrl(request);

		// Obtengo lastModifiedParaElCache
		int lastModifiedParaElCache;
		int FlastModified = getLastModified(response);

		if ((FlastModified == -1) && (FDate != -1)) {
			lastModifiedParaElCache = FDate;
		}
		else if ((FlastModified == -1) && (FDate == -1)){
			lastModifiedParaElCache = FToday;
		}
		else {
			lastModifiedParaElCache = FlastModified;
		}

		// VAMOS A ACTUALIZAR UN OBJETO, SI EXISTE, EN LA CACHE
		// SI NO LO ENCONTRAMOS, LO INSERTAMOS COMO UNO NUEVO
		if (isPragmaNoCache(request)) {
			if (actualizarObjetoCache(url,response,tamanioResponseBytes,Fexpira,lastModifiedParaElCache,refCache) == -1){
				insertarObjetoCache(url,response,tamanioResponseBytes,Fexpira,lastModifiedParaElCache,refCache);
			}
		}
		// VAMOS A CACHEAR UN OBJETO NUEVO EN LA CACHE
		else {
			insertarObjetoCache(url,response,tamanioResponseBytes,Fexpira,lastModifiedParaElCache,refCache);

		}

		return 1;
	}
}


