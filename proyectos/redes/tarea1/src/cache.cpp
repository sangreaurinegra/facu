#include "cache.h"
#include <iostream> // la uso para el showCache

using namespace std;


struct nodoObjeto{
    char* id;				// identificador del objeto
    char* objeto;			// encabezado + contendio
    int tamanio;			// tamanio del mensaje http (encabezado + objeto)
	int vencimiento;		// "Expires" del objeto. Un valor 0 indica que nunca vence.
	int ultimaModificacion;	// "Last-Modified" del objeto.
    nodoObjeto* next;
};

struct cache{
	int capacidad;				// indica la cantidad maxima de objetos a almacenar
	int tamanio_permitido_max;	// indica el mayor tamanio de objeto cacheable
	int cant_objetos;			// indica la cantidad de objetos almacenados
	uint64_t memoria_utilizada;			// indica la cantidad de memoria utilizada

	int hitsEnCache;			// indica la cantidad de objetos cacheados entregados
    nodoObjeto* objetos_front;	// apunta al primer objeto de la lista
    nodoObjeto* objetos_rear;	// apunta al ultimo objeto de la lista
};


cache* initCache(){
	cache* c = new cache;
	c->capacidad = 200;				// por defecto son 200 objetos
	c->cant_objetos = 0;			// la cache no contiene objetos aun
	c->memoria_utilizada = 0;
	c->tamanio_permitido_max = 102400;	// por defecto son 100 KB = 102400
	c->hitsEnCache = 0;				// cantidad de objetos cacheados entregados
	c->objetos_front = NULL;
	c->objetos_rear = NULL;
	return c; 
}


char* getIdObj(nodoObjeto* nObj){
	if (nObj!=NULL){
		char* url = new char[20];
		strcpy(url,nObj->id);
		return url;
	}
	else
		return NULL;
}


char* getResponse(nodoObjeto* nObj){
	if (nObj!=NULL){
		char* paquete = new char[nObj->tamanio+1];
		memcpy(paquete,nObj->objeto,nObj->tamanio);
		paquete[nObj->tamanio] = '\0';
		return paquete;
	}
	else
		return NULL;
}


int getTamanioObj(nodoObjeto* nObj){
	if (nObj!=NULL)
		return nObj->tamanio;
	else
		return -1;
}


int getVencimientoObj(nodoObjeto* nObj){
	if (nObj!=NULL)
		return nObj->vencimiento;
	else
		return -1;
}


int getUltimaModificacionObj(nodoObjeto* nObj){
	if (nObj!=NULL)
		return nObj->ultimaModificacion;
	else
		return -1;
}


int getCapacidadCache (cache* c){
	return c->capacidad;
}


int getCantidadObjCache (cache* c){
	return c->cant_objetos;
}

uint64_t getMemoriaUtilizada (cache *c) {
	return c->memoria_utilizada;
}

int getTamanioPermitidoMaxCache (cache* c){
	return c->tamanio_permitido_max;
}


int getHitsCache (cache* c){
	return c->hitsEnCache;
}


int setCapacidadCache (int cap, cache* c){
	if (c->cant_objetos>cap)
		return -1;
	else{
		c->capacidad = cap;
		return 0;
	}
}


void setTamanioPermitidoMaxCache (int tam, cache* c){
	c->tamanio_permitido_max = tam;
}


int esVacioCache (cache* c){
	return (c->cant_objetos == 0);
}


int esLlenoCache (cache* c){
	return (c->cant_objetos == c->capacidad);
}

int insertarObjetoCache (const char* ID, const char* obj, int tam, int expires, int lastModified, cache* &c){

	if (c->tamanio_permitido_max < tam){
		return -1;
	}else{
		if (c->cant_objetos == c->capacidad){
			eliminarPrimeroCache(c);
		}

		nodoObjeto* nuevo = new nodoObjeto;

		nuevo->id = new char[strlen(ID)+1];
		strcpy(nuevo->id, ID);
		nuevo->id[strlen(ID)] = '\0';

		nuevo->objeto = new char[tam+1];
		memcpy(nuevo->objeto,obj,tam);
		nuevo->objeto[tam] = '\0';

		nuevo->tamanio = tam;
		nuevo->vencimiento = expires;
		nuevo->ultimaModificacion = lastModified;
		nuevo->next = NULL;
		if (c->cant_objetos == 0)
			c->objetos_front = nuevo;
		else
			c->objetos_rear->next = nuevo;
		c->objetos_rear = nuevo;
		c->cant_objetos = c->cant_objetos + 1;
		c->memoria_utilizada = c->memoria_utilizada +nuevo->tamanio;


		return 0;
	}
}

int actualizarObjetoCache (const char* URL, const char* obj, int tam, int expires, int lastModified, cache* &c){
	nodoObjeto* iter = c->objetos_front;
	while ((iter != NULL) && (strcmp(URL,iter->id)!=0)){
		iter = iter->next;
	}
	if (iter != NULL){ // lo encontre en el nodo iter

		// actualizo el nodo
		delete[] iter->objeto;

		iter->objeto = new char[tam+1];
		memcpy(iter->objeto,obj,tam);
		iter->objeto[tam] = '\0';

		iter->tamanio = tam;
		iter->vencimiento = expires;
		iter->ultimaModificacion = lastModified;

		return 0;
	}
	else // no lo encontre
		return -1;
}

nodoObjeto* buscarObjetoCache (const char* URL, int ahora, cache* &c){
	nodoObjeto* obj = NULL;
	nodoObjeto* iter = c->objetos_front;
	log("Buscando esta URL en la cache"+string(URL),LOGGER_DEBUG);


	if (iter != NULL){

		// si el cache tiene un solo objeto y es el buscado
		if ((iter->next == NULL) && (strcmp(URL,iter->id)==0)){
			obj = iter;

			if ((obj->vencimiento!=0) && (obj->vencimiento <= ahora)){ // esta vencido
				delete[] obj;
				c->cant_objetos = 0;
				c->memoria_utilizada =0;
				c->objetos_front = NULL;
				c->objetos_rear = NULL;
				obj = NULL;
			}
		}

		// si el cache tiene mas de un objeto
		else{

			// si es el primer objeto
			if (strcmp(URL,iter->id)==0){
				obj = iter;
				c->objetos_front = obj->next;

				if ((obj->vencimiento!=0) && (obj->vencimiento <= ahora)){ // esta vencido
					delete[] obj;
					c->cant_objetos--;
					c->memoria_utilizada = c->memoria_utilizada - obj->tamanio;
					obj = NULL;
				}

				else{ // No esta vencido
					// muevo obj al final de la cola de objetos
					c->objetos_rear->next = obj;
					c->objetos_rear = obj;
					obj->next = NULL;
				}
			}

			// como no es el primer objeto, sigo buscando
			else{
				while ((iter->next != NULL) && (strcmp(URL,iter->next->id)!=0))
					iter = iter->next;

				if (iter->next != NULL){ // lo encontre en el nodo iter->next
					obj = iter->next;

					// esta vencido
					if ((obj->vencimiento!=0) && (obj->vencimiento <= ahora)){
						if (obj->next == NULL){ // es el ultimo objetos de la cola
							c->objetos_rear = iter;
							iter->next = NULL;
						}
						else{ // el objeto tiene otro objeto adelante de el
							iter->next = obj->next;
						}
						c->memoria_utilizada = c->memoria_utilizada - obj->tamanio;

						delete[] obj;
						c->cant_objetos--;
						obj = NULL;
					}

					// No esta vencido
					else{
						// muevo obj al final de la cola de objetos
						c->objetos_rear->next = obj;
						c->objetos_rear = obj;
						iter->next = obj->next;
						obj->next = NULL;
					}
				}
			}
		}
	}
	if (obj){
		// Lo encontre, por lo tanto lo copio
		log("Lo encontre en la cache\n",LOGGER_DEBUG);

		c->hitsEnCache++;
		nodoObjeto* nuevo = new nodoObjeto;
		nuevo->id = new char[strlen(obj->id)+1];
		nuevo->objeto = new char[obj->tamanio+1];
		strcpy(nuevo->id,obj->id);
		memcpy(nuevo->objeto,obj->objeto,obj->tamanio);
		nuevo->objeto[obj->tamanio]='\0';
		nuevo->tamanio = obj->tamanio;
		nuevo->vencimiento = obj->vencimiento;
		nuevo->ultimaModificacion = obj->ultimaModificacion;
		nuevo->next = NULL;

		return nuevo;
	}
	else
		// No lo encontre
		return obj;
}


void eliminarPrimeroCache (cache* &c){
	if (c->cant_objetos != 0){
		nodoObjeto* head = c->objetos_front;
		c->objetos_front = head->next;
		c->cant_objetos--;
		c->memoria_utilizada = c->memoria_utilizada - head->tamanio;
		delete[] head;
	}
	if (c->cant_objetos == 0){
		c->objetos_front = NULL;
		c->objetos_rear = NULL;
	}
}

void destruirCache (cache* &c){
	nodoObjeto* borro;
	while (c->cant_objetos != 0){
		borro = c->objetos_front;
		c->objetos_front = borro->next;
		c->cant_objetos = c->cant_objetos - 1;
		c->memoria_utilizada = c->memoria_utilizada - borro->tamanio;
		delete[] borro;
	}
	delete[] c;
	c = NULL;
}

void showCache (cache* c){
	nodoObjeto* iter = c->objetos_front;
	if (iter==NULL){
		cout << "El cache esta vacio." << endl;
	}
	else
		while (iter != NULL){
			//cout << "id=" << iter->id << " obj=" << iter->objeto << " tam=" << iter->tamanio << " venc=" << iter->vencimiento << " Umodif=" << iter->ultimaModificacion << endl;
			cout << "iter=" << iter <<endl;

			cout << "id=" << iter->id <<endl;
			cout << "obj=" << iter->objeto <<endl;

			iter = iter->next;
		}
}


