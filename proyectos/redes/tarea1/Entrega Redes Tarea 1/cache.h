#ifndef CACHE_H_
#define CACHE_H_

#include "stringLib.h"
//#include "timeLib.h"

/*
NOTA 1 : Hay que modificar las fechas que por ahora las estoy tomando como int

NOTA 3 : ta faltando hacer un UpdateObjCache
*/

struct nodoObjeto;
struct cache;

cache* initCache();
// Inicializa el cache

char* getIdObj(nodoObjeto* nObj);
// Retorna una copia del id del objeto (se hace new pero no nunca el delete)
// Si nodoObjeto == NULL, retorna NULL

char* getResponse(nodoObjeto* nObj);
// Retorna una copia del mensaje http: encabezado + objeto (se hace new pero no nunca el delete)
// Si nodoObjeto == NULL, retorna NULL

int getTamanioObj(nodoObjeto* nObj);
// Retorna el tamanio del mensaje http (encabezado + objeto)
// Si nodoObjeto == NULL, retorna -1

int getVencimientoObj(nodoObjeto* nObj);
// Retorna la fecha de expiracion de objeto
// Si el objeto no expira, se retorna 0
// Si nodoObjeto == NULL, retorna -1

int getUltimaModificacionObj(nodoObjeto* nObj);
// Retorna la fecha de la ultima vez que el objeto fue modificado
// Si nodoObjeto == NULL, retorna -1

int getCapacidadCache (cache* c);
// Retorna la cantidad maxima de objetos posibles en el cache
// Precondicion: El cache debe estar inicializado.

uint64_t  getMemoriaUtilizada (cache* c);


int getCantidadObjCache (cache* c);
// Retorna la cantidad de objetos almacenados en el cache
// Precondicion: El cache debe estar inicializado.

int getTamanioPermitidoMaxCache (cache* c);
// Retorna el mayor tamanio de objeto cacheable en KB
// Precondicion: El cache debe estar inicializado.

int getHitsCache (cache* c);
// Retorna la cantidad de objetos cacheados entregados
// Precondicion: El cache debe estar inicializado.

int setCapacidadCache (int cap, cache* c);
// Setea la cantidad maxima de objetos posibles en el cache
// Si (getCantidadObjCache(c)>cap) se retorna -1 y no se setea dicho valor.
// Si (getCantidadObjCache(c)<=cap) se retorna 0 y se setea la cacapacidad del cache
// Precondicion: El cache debe estar inicializado.

void setTamanioPermitidoMaxCache (int tam, cache* c);
// Setea el mayor tamanio de objeto cacheable
// Precondicion: El cache debe estar inicializado.
// HAY QUE VER QUE HACEMOS CUANDO BAJAN EL TAMANIO Y TENIA EN MI CACHE OBEJTOS CON TAMANIO MAYOR
// UNA OPCION SERIA RECORRER TODO EL CACHE ELIMIANANDO LOS OBJETOS QUE SUPERAN ESE TAMANIO

int esVacioCache (cache* c);
// Retorna true si c no contiene ningun objeto alamcenado
// Precondicion: El cache debe estar inicializado.

int esLlenoCache (cache* c);
// Retorna true si el cache contiene la maxima cantidad de objetos posibles
// Precondicion: El cache debe estar inicializado.

int insertarObjetoCache (const char* ID, const char* obj, int tam, int expires, int lastModified, cache* &c);
// Si (getTamanioPermitidoMaxCache(c) < tam) se retorna -1 y no se permite insertar el nodoObjeto.
// Si (getTamanioPermitidoMaxCache(c) >= tam) se retorna 0 y se inserta el nodoObjeto al final de la cola.
// El nodoObjeto se identifica por 'URL', su mensaje http contiene el objeto "obj" y su tamanio es 'tam'.
// La fecha de expiracion del objeto es "expires" y su fecha de ultima modificacion es "lastModified".
// Precondicion: El cache debe estar inicializado.

int actualizarObjetoCache (const char* URL, const char* obj, int tam, int expires, int lastModified, cache* &c);
// Se busca el nodoObjeto identificado por 'URL'
// Si no existe, se retorna -1 y no se actualizan sus datos.
// Si existe, se retorna 0 y se actualizan sus datos.
// Precondicion: El cache debe estar inicializado.

nodoObjeto* buscarObjetoCache (const char* URL, int ahora, cache* &c);
// Busca el nodoObjeto identificado por URL en el cache
// Si lo encuentra y esta vencido, lo elimina del cache y retorna NULL
// Si lo encuentra y no vencio, lo mueve al final de la cola de nodoObjetos y
// retorna una copia del mismo (se hace new pero no nunca el delete)
// Si no lo encuentra, se retorna NULL
// Precondicion: El cache debe estar inicializado.

void eliminarPrimeroCache (cache* &c);
// Elimina el primer nodoObjeto de la cola de nodoObjeto y libera la memoria.
// Si no hay nodoObjetos en la cache, la operacion no tiene efecto.
// Precondicion: El cache debe estar inicializado.

void destruirCache (cache* &c);
// Libera toda la memoria reservada para el cache deshaciendo la inicializacion.
// Para volver a utilizar el cache se debe volver a ejecutar initCache()

void showCache (cache* c);
// Muestra info del cache.


#endif

