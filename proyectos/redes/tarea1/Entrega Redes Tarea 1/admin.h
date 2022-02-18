#ifndef ADMIN_H_
#define ADMIN_H_
#include "logger.h"
#include "stringLib.h"
#include "socketLib.h"

#include "cacheHandler.h"

#define BUFFER_LECTURA_ADMIN 100

const uint64_t MAX_VALOR_SOPORTADO = 99999999;

const int noComm = -1;

//show run - muestra los valores actuales y datos estadísticos
//			como memoria total utilizada, cantidad de objetos,
//			cantidad de requests atendidos y cantidad de objetos cacheados entregados.
const int show_run = 1;

//purge	- borra todos los objetos cacheados en memoria.
const int purge = 2;

//set max_object_size XXX - determina el mayor tamaño de objeto transferible por
//							este proxy, expresado en KB (por defecto, su valor será de 10MB)
const int set_max_object_size = 3;

//set max_cached_object_size YYY - determina el mayor tamaño de objeto cacheable por
//								 este proxy, expresado en KB (por defecto, su valor será de 100KB)
const int set_max_cached_object_size = 4;

//set max_object_count ZZZ - 	determina la cantidad máxima de objetos a almacenar
//							en RAM (por defecto, su valor será de 200 objetos)
const int set_max_object_count = 5;

//quit - cierra la sesión de administración
const int quit = 6;

// retorna el entero correspondiente al comando (lo codifica)
int getComando(string comando);

// retorna el valor del parametro ingresado en el comando
uint64_t getValor(string valor);

// operaciones
string showRun(int socket, uint64_t cantidadEntregados,uint64_t cantidadRequest,uint64_t max_object_size, cache* &ptrCache);
void ejecutarPurge(int socket, cache* &ptrCache);

void setMaxObjectSize(int socket, uint64_t & max_object_size , uint64_t newValor);

void setMaxCachedObjectSize(int socket, uint64_t & max_object_size , uint64_t newValor, cache* &ptrCache);

void setMaxObjectCount(int socket, uint64_t & max_object_size , uint64_t newValor, cache* &ptrCache);

void ejecutarQuit(int socket, int & salir);



#endif /* ADMIN_H_ */
