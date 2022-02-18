/*
 * parserLib.cpp
 *
 *  Created on: Sep 5, 2012
 *      Author: dell
 */

#include "parserLib.h"
#include "timeLib.h"
#include <string.h>

bool  isPrefix(const char * pref, const char * cadena){
	if	((strlen(pref) <= strlen(cadena)) &&
		(strncmp(cadena,pref,strlen(pref))==0)) // comparo los primeros caracteres
		return true;
	else
		return false;
}


bool isAuthorization(const char * cadena) {

	char* headers = regexp(cadena,"^(.+\r\n)\r\n");
	char *response = regexp(headers,"\r\nAuthorization: ([[:alnum:]]+)");
	if (headers != NULL ) {delete[] headers;}
	bool res = (response != NULL);
    if (response != NULL) {delete[] response;};
	return res;
}


bool isPragmaNoCache(const char * cadena) {

	char* headers = regexp(cadena,"^(.+\r\n)\r\n");

	char *response = regexp(headers,"\r\nPragma: ([[:print:]]+)\r\n");
	if (headers != NULL ) {delete[] headers;}
	bool res = false;
    if (response == NULL) {
    	res = false;
    } else {
        //== 0 si son iguales asi que retorna true si son iguales
    	res = (strcmp( response, "no-cache" ) == 0);
    }
    if (response != NULL) {delete[] response;};
    return res;
}


bool isExpires(const char * cadena) {

	char* headers = regexp(cadena,"^(.+\r\n)\r\n");

	char *response = regexp(headers,"\r\nExpires: ([[:print:]]+)\r\n");
	if (headers != NULL ) {delete[] headers;}
	bool res = (response != NULL);
	if (response != NULL) {delete[] response;};
	return res;
}

bool isGet(const char * cadena) {

	char *response = regexp(cadena,"^(GET)");
	bool res = response != NULL;
    if (response != NULL) {delete[] response;};
	return res;
}

bool isPost(const char * cadena) {

	char *response = regexp(cadena,"^(POST)");
	bool res = response != NULL;
    if (response != NULL) {delete[] response;};
	return res;
}


bool isHttp10 (const char * cadena) {
	char *response = regexp(cadena,"(HTTP/1.0)\r\n");
	bool res = (response != NULL);
    if (response != NULL) {delete[] response;};

	return res;
}


bool isProtocol(const char * cadena) {

	char *response = regexp(cadena,"^.* (http://)");
	bool res = (response != NULL);
    if (response != NULL) {delete[] response;};
	return res;
}


char*  getContentLength (const char * cadena) {

	char* headers = regexp(cadena,"^(.+\r\n)\r\n");

	char* tamanio = regexp(headers,"\r\nContent-Length: ([0-9]+)\r\n");
	if (headers != NULL ) {delete[] headers;}
	return tamanio;
}



char*  getHeader (const char * cadena){
	return regexp(cadena,"^(.+\r\n)\r\n");
}



char *  getBody ( const char * cadena) {
	return regexp(cadena,"\r\n\r\n(.+)$");
}


char * getHost (const char * cadena) {

	char* headers = regexp(cadena,"^(.+\r\n)\r\n");

	char* elhost = regexp(headers,"Host: ([[:print:]]+)\r\n");
	if (headers != NULL ) {delete[] headers;}
	return elhost;
}


char * getUrl (const char * cadena) {
	if (isGet(cadena) && isHttp10(cadena)) {
		if (isProtocol(cadena))
			return regexp(cadena,"GET http://([[:print:]]+) HTTP");
		else
			return regexp(cadena,"GET ([[:print:]]+) HTTP");

	}
	else if (isPost(cadena) && isHttp10(cadena)){
		if (isProtocol(cadena))
			return regexp(cadena,"POST http://([[:print:]]+) HTTP");
		else
			return regexp(cadena,"POST ([[:print:]]+) HTTP");

	}
	else if (isGet(cadena) && !isHttp10(cadena)){
		char * host = getHost(cadena);
		if (host == NULL) return NULL;
		char * url;
		if (isProtocol(cadena)) //es la ruta absoluta
			return regexp(cadena,"GET http://([[:print:]]+) HTTP");
		else {
			url = regexp(cadena,"GET ([[:print:]]+) HTTP");

			if (url == NULL) return NULL;

			if (!isPrefix(host,url)) {
				char* result;
				result = new char[strlen(host)+strlen(url)+1];
				strcpy(result, host);
				strcat(result, url);
				if (host != NULL) {delete[] host;};
				if (url != NULL) {delete[] url;};
				return result;
			}
			else
				return url;
		}
	}
	else if (isPost(cadena) && !isHttp10(cadena)){
		char * host = getHost(cadena);
		if (host == NULL) return NULL;
		char * url;
		if (isProtocol(cadena)) //es la ruta absoluta
			return regexp(cadena,"POST http://([[:print:]]+) HTTP");
		else {
			url = regexp(cadena,"POST ([[:print:]]+) HTTP");

			if (url == NULL) return NULL;

			if (!isPrefix(host,url)) {
				char* result;
				result = new char[strlen(host)+strlen(url)+1];
				strcpy(result, host);
				strcat(result, url);

				if (host != NULL) {delete[] host;};
				if (url != NULL) {delete[] url;};

				return result;
			}
			else
				return url;
		}
	}
	return NULL;
}


char*  getStateCode(char * cadena) {
	char* index = strstr(cadena,"HTTP/1.");
	if(index!=NULL){
		char*  cod = new char [4];
		cod[0] = index[9];
		cod[1] = index[10];
		cod[2] = index[11];
		cod[3] = 0;
		return cod;
	}
	else
		return NULL;
}


/*
 * Devuelvo un int que representa la Date en segundos.
 * Si no se encuentra la fecha se devuelve  -1.
 */
int getModifiedSince(const char * cadena) {

	char* headers = regexp(cadena,"^(.+\r\n)\r\n");

	const char * date = regexp(headers,"\r\nIf-Modified-Since: ([[:print:]]+)\r\n");
	if (headers != NULL ) {delete[] headers;}
	int res = stringToTime(date);
    if (date != NULL) {delete[] date;};
	return res;

}

/*
 * Devuelvo un int que representa la Date en segundos.
 * Si no se encuentra la fecha se devuelve  -1.
 */
int getLastModified(const char * cadena) {

	char* headers = regexp(cadena,"^(.+\r\n)\r\n");

	const char * date = regexp(headers,"\r\nLast-Modified: ([[:print:]]+)\r\n");
	if (headers != NULL ) {delete[] headers;}
	int res = stringToTime(date);
    if (date != NULL) {delete[] date;};
	return res;
}

/*
 * Devuelvo un int que representa la Date en segundos.
 * Si no se encuentra la fecha se devuelve  -1.
 */
int getDate(const char * cadena) {

	char* headers = regexp(cadena,"^(.+\r\n)\r\n");

	const char * date = regexp(headers,"\r\nDate: ([[:print:]]+)\r\n");
	if (headers != NULL ) {delete[] headers;}
	int res = stringToTime(date);
    if (date != NULL) {delete[] date;};
	return res;
}

/*
 * Devuelvo un int que representa la Date en segundos.
 * Si no se encuentra la fecha se devuelve  -1.
 */
int getExpires(const char * cadena) {

	char* headers = regexp(cadena,"^(.+\r\n)\r\n");

	const char * date = regexp(headers,"\r\nExpires: ([[:print:]]+)\r\n");
	if (headers != NULL ) {delete[] headers;}
	int res = stringToTime(date);
    if (date != NULL) {delete[] date;};
	return res;
}


void cambioResponseHttp11A10(char* cadena) {
	char* index = strstr(cadena,"HTTP/1.1");
    if(index!=NULL)
    	index[7]='0';
}


char *regexp (const char *string, const char *patrn) {
        int i, w=0, len;
        char *word = NULL;
        regex_t rgT;
        regmatch_t match[2];

        if (string == NULL) return NULL;

        regcomp(&rgT,patrn,REG_EXTENDED);

        if ((regexec(&rgT,string,2,match,0)) == 0) {

                int begin = (int)match[1].rm_so;
                int end = (int)match[1].rm_eo;
                len = end-begin;

                word=new char [len+1];
                for (i=begin; i<end; i++) {
                        word[w] = string[i];
                        w++; }
                word[w]=0;
        }
        regfree(&rgT);
        return word;
}


