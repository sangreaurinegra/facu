#ifndef BUFFER_H_
#define BUFFER_H_

#include <string.h>
#include <stdio.h>
#include "stringLib.h"


#define VACIO		1	/* El buffer esta vacio */
#define LLENO		2	/* El buffer esta lleno */
#define HAYLUGAR	3	/* El buffer tiene lugar */

struct buffer;

buffer * createBuffer(int tam);
// Crea e inicializa el buffer con un tamanio maximo 'tam'

bool isEmptyBuffer(buffer * b);
// Precond: el buffer debe haber sido creado
// Retorna true si el buffer esta vacio, false en caso contrario

bool isFullBuffer(buffer * b);
// Precond: el buffer debe haber sido creado
// Retorna true si el buffer esta lleno, false en caso contrario

int readBuffer(char* leido, int tam, buffer * b);
// Precond: el buffer debe haber sido creado
// Lee como maximo 'tam' bytes del buffer y se guardan en 'leido'
// Retorna la cantidad efectiva leida

int writeBuffer(const char* entrada, int tam, buffer * b);
// Precond: el buffer debe haber sido creado
// Escribe como maximo los primeros 'tam' bytes de 'entrada' en el buffer
// Retorna la cantidad efectiva escrita (si el bufer esta lleno retorna 0)

void printBuffer(buffer * b);
// Imprime el contenido del buffer

void destroyBuffer(buffer * b);
// Libera la memoria del buffer


#endif
