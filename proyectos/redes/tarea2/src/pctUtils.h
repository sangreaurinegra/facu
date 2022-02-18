#ifndef PCTUTILS_H_
#define PCTUTILS_H_


#include <unistd.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/ip.h>
#include <arpa/inet.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>

#ifndef PCT_H_
	#include "pct.h"
#endif


#include "logger.h"
#include "stringLib.h"

enum Estado {
   INIT, DESCONECTADO, CONECTADO, ESPERANDO_SYN_ACK, ESPERANDO_CONEXION, RECIBI_SYN
};

enum Rol {
   EMISOR, RECEPTOR
};

extern Estado estadoActual;
extern int globalPctSocket;
//extern struct sockaddr_in globalDireccionSocket;

extern unsigned char globalPuertoOrigen;
extern unsigned char globalPuertoDestino;

extern struct in_addr globalIPOrigen;
extern struct in_addr globalIPDestino;

/*ENCABEZADO DE FUNCIONES*/
int recibirPaqueteControl (unsigned char flag);
int enviarPaqueteControlDirecto (unsigned char flag);
int recibirPaquete(char * datosPaquete, size_t & lenDatos, unsigned char & flag);



int recibirPaqueteDatos(char *buf, size_t lenDatos);
int enviarPaqueteDatos(char *paquete, size_t lenDatos);

int enviarPaquete(char *paquete, size_t lenDatos);
int enviarPaqueteControl(char *paquete, size_t lenDatos);


int crearPaqueteDatos(int numSec,char* datos, size_t lenDatos , char* datagrama);
int crearPaqueteACK(int numACK, char* datagrama);
int crearPaqueteKAL(char* datagrama,int numKAL);

int crearPaquete(int numSec, int numACK,unsigned char tipoPaquete, char* datos, size_t lenDatos , char* datagrama);

int IP2asc(u_int32_t IP,char * result);
void imprimirPaquete(char* paquete, int LoggerLevel);



#endif /* PCTUTILS_H_ */


