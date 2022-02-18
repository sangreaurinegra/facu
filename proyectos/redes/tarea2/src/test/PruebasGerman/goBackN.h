#ifndef goBackN_H
#define goBackN_H

#include <pthread.h>
#include "logger.h"
#include "stringLib.h"
#include "pctUtils.h"

#define MAX_GBN_SIZE	26//256


#define TIMEOUT_GBN	1000000 //1000000 es un segundo

#define TIMEOUT_KAL	5000000 // time out para el comienzo del Keep A Live 5 seg

struct nodoGbn;


void inicioEmisor();

void destruirEmisor();

int rdtEnviar(char* datos, size_t lenDatos);

void iniciarTemporizador();

void detenerTemporizador();

// rutina de atencion al timer
void finTemporizador();

// el emisor recibe paquetes ack del receptor
void rdtRecibirEmisor(int numACK);


// Receptor

void inicioReceptor();

void porDefecto();

//void rdtRecibirReceptor(int numeroSec, char* packet, size_t lenDatos);
int rdtRecibirReceptor(int numeroSec);

//auxiliares
void udtEnviarReceptor(nodoGbn* ack);

void udtEnviarEmisor(nodoGbn* nodo);

// entrega los datos a la capa superior
void entregarDatos(char* datos, size_t lenDatos);

// para test
void imprimirGBN(int loggerLevel);

#endif
