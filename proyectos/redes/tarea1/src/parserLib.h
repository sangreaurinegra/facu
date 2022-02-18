/*
 * parserLib.h
 *
 *  Created on: Sep 5, 2012
 *      Author: dell
 */

#ifndef PARSERLIB_H_
#define PARSERLIB_H_
#include <sys/types.h>
#include <regex.h>
#include <stdio.h>
#include <time.h>

#include "stringLib.h"
#include "timeLib.h"

#include "logger.h"

bool  isPrefix(const char * pref, const char * cadena) ;
bool  isAuthorization(const char * cadena) ;
bool  isPragmaNoCache(const char * cadena) ;
bool  isExpires(const char * cadena) ;
bool  isGet(const char * cadena) ;
bool  isPost(const char * cadena) ;
bool  isHttp10 (const char * cadena) ;
bool  isProtocol(const char * cadena) ;
char* getContentLength (const char * cadena) ;
char* getHeader (const char * cadena);
char* getBody (const char * cadena);
char* getHost (const char * cadena);
char* getUrl (const char * cadena);
char* getStateCode(char * cadena);
int   getModifiedSince(const char * cadena) ;
int   getLastModified(const char * cadena);
int   getDate(const char * cadena) ;
int   getExpires(const char * cadena) ;
void  cambioResponseHttp11A10 (char* cadena);
char* regexp (const char *string, const char *patrn);


#endif /* PARSERLIB_H_ */
