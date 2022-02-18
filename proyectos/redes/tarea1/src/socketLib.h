#ifndef SOCKETLIB_H
#define SOCKETLIB_H

#include <sys/types.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <unistd.h>
#include <errno.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netdb.h>
#include <stdlib.h>

#include "stringLib.h"
#include "logger.h"


int AperturaSocketComoServidor (int Puerto,string ip);
int AperturaSocketComoCliente (char* IPServidor, int Puerto);
int AceptarConexionComoServidor (int IDSocket);
int LeerDelSocket (int IDSocket, char* DatosALeer, int largoBuffer);
int LeerDelSocketServWeb (int IDSocket, char* DatosALeer, int largoBuffer);
int EscribirEnSocket (int IDSocket,const char* DatosAEscribir, int largoBuffer);
int EscribirEnSocketServWeb (int IDSocket,const char*DatosAEscribir, int largoBuffer);

#endif
