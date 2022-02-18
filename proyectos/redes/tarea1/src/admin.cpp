#include "admin.h"


// retorna el entero correspondiente al comando (lo codifica)
int getComando(string comando){
	log("getComando Llego comando en ->"+comando, LOGGER_TRACE);
	vector<string> spComando = split(comando,' ');
	int tamanio = spComando.size();
	log("getComando Tamanio -> "+intToStr(tamanio), LOGGER_TRACE);

	switch (tamanio) {
		case 1:// purge, quit
			if(spComando.at(0).compare("purge")==0){
				log("getComando reconozco -> purge", LOGGER_TRACE);
				return purge;
			}
			else if(spComando.at(0).compare("quit")==0){
				log("getComando reconozco -> quit", LOGGER_TRACE);
				return quit;
			}
			break;
		case 2:// show run
			if((spComando.at(0).compare("show")==0)&&(spComando.at(1).compare("run")==0)){
				log("getComando reconozco -> show", LOGGER_TRACE);
				return show_run;
			}
			break;
		case 3: // set max_object_size, max_cached_object_size, max_object_count
			if(spComando.at(0).compare("set")==0){
				if(spComando.at(1).compare("max_object_size")==0){
					log("getComando reconozco -> max_object_size", LOGGER_TRACE);
					return set_max_object_size;
				}else if(spComando.at(1).compare("max_cached_object_size")==0){
					log("getComando reconozco -> max_cached_object_size", LOGGER_TRACE);
					return set_max_cached_object_size;
				}else if(spComando.at(1).compare("max_object_count")==0){
					log("getComando reconozco -> max_object_count", LOGGER_TRACE);
					return set_max_object_count;
				}
				return noComm;
			}
			break;
		default:
			return noComm;
		break;
	}

	return noComm;
}

// retorna el valor del parametro ingresado en el comando
uint64_t getValor(string comando){
	log("getValor Llego comando en ->"+comando, LOGGER_TRACE);
	vector<string> spComando = split(comando,' ');
	int tamanio = spComando.size();
	log("getValor Tamanio -> "+intToStr(tamanio), LOGGER_TRACE);
	int intComando = getComando(comando);
	if(intComando == set_max_cached_object_size ||
			intComando == set_max_object_count|| intComando == set_max_object_size){
		log("getValor Comando con valor" , LOGGER_TRACE);
		int tam = spComando.at(2).length();
		if(tam>0 && tam<11){ // 11 por max de ulong int
			return strTouInt64_t(spComando.at(2));
		}
	}
	return MAX_VALOR_SOPORTADO;
}



string showRun(int socket, uint64_t cantidadEntregados,uint64_t cantidadRequest,uint64_t max_object_size, cache* &ptrCache){
	//printf("laCache.getCapacidadCache33 %d",getCapacidadCache(ptrCache));
	//printf("laCache.getCantidadObjCache %d",getCantidadObjCache(ptrCache));

	log("Ejecutando showRun" , LOGGER_DEBUG);
	string cantidadObjetos = intToStr(getCantidadObjCache(ptrCache));
	string cantidadMaxObjetosCache = intToStr(getCapacidadCache(ptrCache));
	string cantidadHitsCache = intToStr(getHitsCache(ptrCache));



	string ret = "memoria total utilizada: "+int64_tToStr(getMemoriaUtilizada(ptrCache)/1024);
	ret += "\n cantidad de objetos en Cache:  " + cantidadObjetos;
	ret += "\n Capacidad maxima de objetos en la Cache:  " + cantidadMaxObjetosCache;
	ret += "\n Cantidad de hits en la cache:  " + cantidadHitsCache;

	ret += "\n cantidad de requests atendidos: " + int64_tToStr(cantidadRequest);
	ret += "\n cantidad de objetos cacheados entregados:" +  int64_tToStr(cantidadEntregados);
	ret += "\n Max Object Size:" +  int64_tToStr(max_object_size/1024);

	ret += "\n";

	int error = EscribirEnSocket (socket, ret.c_str(),ret.length());
	if(error < 0){
		log("Error al escribir en Administrador" , LOGGER_INFO);
	}
	return ret;
}

void ejecutarPurge(int socket, cache* &ptrCache){
	log("Ejecutando Purge" , LOGGER_DEBUG);
	destruirCache(ptrCache);

	initCacheHandler(ptrCache);

	string ret = "Purge ejecutado con exito\n";
	int error = EscribirEnSocket (socket, ret.c_str(),ret.length());
	if(error < 0){
		log("Error al escribir en Administrador" , LOGGER_INFO);
	}
}

void setMaxObjectSize(int socket, uint64_t & max_object_size , uint64_t newValor){
	log("Ejecutando setMaxObjectSize" , LOGGER_DEBUG);
	string ret;

	if(newValor < MAX_VALOR_SOPORTADO){
		max_object_size=newValor*1024;
		ret = "Set Max Object size ejecutado con exito\n";

	}else{
		log("max_object_size Valor no valido" , LOGGER_INFO);
		ret = "max_object_size Valor no valido\n";

	}

	int error = EscribirEnSocket (socket, ret.c_str(),ret.length());
	if(error < 0){
		log("Error al escribir en Administrador" , LOGGER_INFO);
	}
}

void setMaxCachedObjectSize(int socket, uint64_t & max_cached_object_size , uint64_t newValor, cache* &ptrCache){
	string ret;

	log("Ejecutando setMaxCachedObjectSize" , LOGGER_DEBUG);

	if(newValor < MAX_VALOR_SOPORTADO){
		max_cached_object_size=newValor*1024;
		setTamanioPermitidoMaxCache(max_cached_object_size, ptrCache);
		ret = "set Max Cached object size ejecutado con exito\n";

	}else{
		ret = "max_cached_object_size Valor no valido\n";

		log("max_cached_object_size Valor no valido" , LOGGER_INFO);
	}

	int error = EscribirEnSocket (socket, ret.c_str(),ret.length());
	if(error < 0){
		log("Error al escribir en Administrador" , LOGGER_INFO);
	}
}

void setMaxObjectCount(int socket, uint64_t & max_object_count , uint64_t newValor, cache* &ptrCache){
	string ret;

	log("Ejecutando setMaxObjectCount" , LOGGER_DEBUG);
	if(newValor < MAX_VALOR_SOPORTADO){
		if (setCapacidadCache(newValor, ptrCache) == 0) {
			max_object_count=newValor;
			ret = "set Max object count ejecutado con exito\n";
		} else {
			ret = "set Max object count error: El valor es menor a la cantidad de objetos actual. Sugerencia : realice un purge\n";

		}

	}else{
		log("max_object_count Valor no valido" , LOGGER_INFO);
		ret = "max_object_count Valor no valido\n";

	}

	int error = EscribirEnSocket (socket, ret.c_str(),ret.length());
	if(error < 0){
		log("Error al escribir en Administrador" , LOGGER_INFO);
	}
}

void ejecutarQuit(int socket, int & salir){
	log("Ejecutando Quit" , LOGGER_DEBUG);
	string chau = "Chau\n";
	int error = EscribirEnSocket (socket, chau.c_str(),chau.length());
	if(error < 0){
		log("Error al escribir en Administrador" , LOGGER_INFO);
	}
	salir = 1;
	close(socket);
}




