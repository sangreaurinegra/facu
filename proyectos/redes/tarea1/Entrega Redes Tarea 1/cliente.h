#ifndef CLIENTE_H_
#define CLIENTE_H_

#include "logger.h"
#include "stringLib.h"
#include "socketLib.h"
#include "parserLib.h"

#include <string.h>
#include <stdio.h>
#include <netinet/in.h>
#include <arpa/inet.h>


#define BUFFER_LECTURA_CLIENTE 1500


const char* buscarEnWeb(char* request, int largoRequest ,int socketCliente, int max_object_size, int& bytesLeidos);
int mandarReponseDesdeCacheANavegador(char * resLecura, int largoResponse, int socketCliente);

#endif
