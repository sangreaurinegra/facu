/*
 * chacheHandler.h
 *
 *  Created on: Sep 8, 2012
 *      Author: dell
 */

#ifndef CHACHEHANDLER_H_
#define CHACHEHANDLER_H_
#include "cache.h"

void initCacheHandler(cache* &laCache);

char* consultarCache(char* request, int &tamanioResponseBytes);
// Consulta si se encuentra un objeto en el cache
// En caso que sea un GET Condicional y se encuentre el objeto,
// se retorna el objeto o se retorna el mensaje "304 Not Modified"
// En caso que sea un GET y se encuentre el objeto, se lo retorna
// En otro caso se retorna NULL

int cachear(char* request, char* response, int tamanioResponseBytes);
// Si corresponde se cachea el objeto y se retorna 1
// Si no corresponde no se cachea y se retorna 0


#endif /* CHACHEHANDLER_H_ */
