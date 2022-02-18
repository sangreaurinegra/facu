#include "buffer.h"

struct buffer{
    int comienzo;
    int fin;
    int tamanio;
    int estado;
    unsigned char * data;
};


buffer * createBuffer(int tam){
    buffer * b = new buffer;
    b->comienzo = 0;
    b->fin = 0;
    b->tamanio = tam;
    b->estado = VACIO;
    b->data = new unsigned char [tam];
    return b;
}


bool isEmptyBuffer(buffer * b){
	return ((b!=0) && (b->estado == VACIO));
}


bool isFullBuffer(buffer * b){
	return ((b!=0) && (b->estado == LLENO));
}


int readBuffer(char* leido, int tam, buffer * b){
	if (b->estado == VACIO){
		return 0;
	}
	else {
		int cantALeer = tam;
		if (b->comienzo >= b->fin){
			int cantHastaFinDelBuf = (b->tamanio - b->comienzo);

			if (cantALeer <= cantHastaFinDelBuf){
				memcpy(leido,b->data + b->comienzo,cantALeer);
				b->comienzo = (b->comienzo + cantALeer) % b->tamanio;
			}
			else
			{
				memcpy(leido,b->data + b->comienzo,cantHastaFinDelBuf);
				b->comienzo = 0;
				if (b->comienzo == b->fin){
					b->estado = VACIO;
					return cantHastaFinDelBuf;
				}
				else {
					cantALeer = cantALeer - cantHastaFinDelBuf;
					if (cantALeer > b->fin)
						cantALeer = b->fin;
					memcpy(leido + cantHastaFinDelBuf,b->data,cantALeer);
					b->comienzo = (b->comienzo + cantALeer) % b->tamanio;
					cantALeer = cantALeer + cantHastaFinDelBuf;
				}
			}
		}
		else { // b->comienzo < b->fin
			if (cantALeer > (b->fin - b->comienzo)) // hay mas para leer que lugares escritos
				cantALeer = (b->fin - b->comienzo);
			memcpy(leido,b->data + b->comienzo,cantALeer);
			b->comienzo = (b->comienzo + cantALeer) % b->tamanio;
		}

		if (b->comienzo == b->fin)
			b->estado = VACIO;
		else
			b->estado = HAYLUGAR;
		return cantALeer;
	}
}


int writeBuffer(const char* entrada, int tam, buffer * b){
	if (b->estado != LLENO){
		int cantACopiar = tam;

		if (b->comienzo <= b->fin){
			int cantHastaFinDelBuf = (b->tamanio - b->fin);

			if (cantACopiar <= cantHastaFinDelBuf){
				memcpy(b->data + b->fin,entrada,cantACopiar);
				b->fin = (b->fin + cantACopiar) % b->tamanio;
			}
			else
			{
				memcpy(b->data + b->fin,entrada,cantHastaFinDelBuf);
				b->fin = 0;
				cantACopiar = cantACopiar - cantHastaFinDelBuf;
				if (cantACopiar > b->comienzo)
					cantACopiar = b->comienzo;
				memcpy(b->data + b->fin,entrada + cantHastaFinDelBuf,cantACopiar);
				b->fin = (b->fin + cantACopiar) % b->tamanio;
				cantACopiar = cantACopiar + cantHastaFinDelBuf;
			}
		}
		else { // b->comienzo > b->fin

			if (cantACopiar > (b->comienzo - b->fin)) // hay mas para copiar que lugares libres
				cantACopiar = (b->comienzo - b->fin);
			memcpy(b->data + b->fin,entrada,cantACopiar);
			b->fin = (b->fin + cantACopiar) % b->tamanio;
		}

		if (b->comienzo == b->fin)
			b->estado = LLENO;
		else
			b->estado = HAYLUGAR;
		log("buffer.cpp - writeBuffer: cantidad de bytes escritos que retorna = "+ intToStr(cantACopiar),LOGGER_TRACE);
		return cantACopiar;
	}
	else
		return 0;
}


void printBuffer(buffer * b){
	printf("\nBUFFER:\n");
	printf("comienzo: %d\n",b->comienzo);
	printf("fin: %d\n",b->fin);
	printf("tamanio: %d\n",b->tamanio);
	if (b->estado == VACIO)
		printf("estado: VACIO\n");
	else{
		if (b->estado == LLENO) printf("estado: LLENO\n");
		else printf("estado: HAYLUGAR\n");
		printf("data: [");
		int i = b->comienzo;
		printf("%c",b->data[i]);
		i++;
		while (i != b->fin) {
			printf("%c",b->data[i]);
			i = (i + 1) % b->tamanio;
		}
		printf("]");
	}
	printf("\n\n");
}


void destroyBuffer(buffer * b){
    delete [] b->data;
    delete b;
    b = 0;
}

