#include "../../timeLib.h"
#include "../../parserLib.h"
#include "../../cache.h"
#include "../../cacheHandler.h"

#include <assert.h>
#include <iostream>
using namespace std;

void showObjEcontrado(nodoObjeto* nObj){
	if (nObj == NULL)
		cout << "No se encontro al objeto." << endl;
	else{
		cout << "Se encontro al objeto: ";
		cout << "id=" <<getIdObj(nObj)<< " obj=" <<getResponse(nObj)<< " tam=" <<getTamanioObj(nObj);
		cout << " venc=" <<getVencimientoObj(nObj)<< " Umodif=" <<getUltimaModificacionObj(nObj)<< endl;
	}
}

int main()
{
	cout << "Test1" << endl;
	cache* c = initCache();
	showCache(c);

	int seteoCap = setCapacidadCache(5,c);
	assert(seteoCap == 0);

	assert(getCapacidadCache(c) == 5);
	assert(getCantidadObjCache(c) == 0);
	assert(getTamanioPermitidoMaxCache(c) == 102400);
	assert(getHitsCache(c) == 0);
	assert(esVacioCache(c) == 1);
	assert(esLlenoCache(c) == 0);
	cout << ">> Fin Test1" << endl << endl;

	cout << "Test2" << endl;
	insertarObjetoCache("url1","objeto1",10,2013,1900,c);
	insertarObjetoCache("url2","objeto2",20,2014,1900,c);
	insertarObjetoCache("url3","objeto3",30,2010,1900,c);
	insertarObjetoCache("url4","objeto4",40,1990,1900,c);
	assert(getCapacidadCache(c) == 5);
	assert(getCantidadObjCache(c) == 4);
	assert(getTamanioPermitidoMaxCache(c) == 102400);
	assert(getHitsCache(c) == 0);
	assert(esVacioCache(c) == 0);
	assert(esLlenoCache(c) == 0);
	insertarObjetoCache("url5","objeto5",50,2016,1900,c);
	assert(getCantidadObjCache(c) == 5);
	assert(esVacioCache(c) == 0);
	assert(esLlenoCache(c) == 1);
	cout << endl << "Inserte 5..." << endl;
	showCache(c);
	eliminarPrimeroCache(c);
	eliminarPrimeroCache(c);
	eliminarPrimeroCache(c);
	cout << endl << "Elimine los 3 primeros..." << endl;
	showCache(c);
	assert(getCantidadObjCache(c) == 2);
	assert(esVacioCache(c) == 0);
	assert(esLlenoCache(c) == 0);
	eliminarPrimeroCache(c);
	eliminarPrimeroCache(c);
	cout << endl << "Elimine los otros 2..." << endl;
	showCache(c);
	assert(getCantidadObjCache(c) == 0);
	assert(esVacioCache(c) == 1);
	assert(esLlenoCache(c) == 0);
	cout << ">> Fin Test2" << endl << endl;

	cout << "Test3" << endl;
	insertarObjetoCache("url1","objeto1",10,2013,1900,c);
	insertarObjetoCache("url2","objeto2",20,2014,1900,c);
	insertarObjetoCache("url3","objeto3",30,2010,1900,c);
	insertarObjetoCache("url4","objeto4",40,1990,1900,c);
	insertarObjetoCache("url5","objeto5",50,2016,1900,c);
	assert(getCapacidadCache(c) == 5);
	assert(getCantidadObjCache(c) == 5);
	assert(getTamanioPermitidoMaxCache(c) == 102400);
	assert(getHitsCache(c) == 0);
	assert(esVacioCache(c) == 0);
	assert(esLlenoCache(c) == 1);
	cout << endl << "Inserte 5..." << endl;
	showCache(c);
	insertarObjetoCache("url6","objeto6",60,2013,1900,c);
	cout << endl << "Inserte otro..." << endl;
	showCache(c);
	insertarObjetoCache("url7","objeto7",70,2009,1900,c);
	cout << endl << "Inserte otro..." << endl;
	showCache(c);
	assert(getCapacidadCache(c) == 5);
	assert(getCantidadObjCache(c) == 5);
	assert(getTamanioPermitidoMaxCache(c) == 102400);
	assert(getHitsCache(c) == 0);
	assert(esVacioCache(c) == 0);
	assert(esLlenoCache(c) == 1);
	cout << ">> Fin Test3" << endl << endl;

	cout << "Test4" << endl;
	destruirCache(c);
	c = initCache();
	showCache(c);

	seteoCap = setCapacidadCache(5,c);
	assert(seteoCap == 0);

	assert(getCapacidadCache(c) == 5);
	assert(getCantidadObjCache(c) == 0);
	assert(getTamanioPermitidoMaxCache(c) == 102400);
	assert(getHitsCache(c) == 0);
	assert(esVacioCache(c) == 1);
	assert(esLlenoCache(c) == 0);
	cout << ">> Fin Test4" << endl << endl;


	cout << "Test5" << endl;

	cout << "* hay 1 objeto, es el buscado y no esta vencido" << endl;
	insertarObjetoCache("url1","objeto1",10,2013,1900,c);
	nodoObjeto* obj = buscarObjetoCache("url1",2012,c);
	assert(strcmp(getIdObj(obj),"url1")==0);
	assert(strcmp(getResponse(obj),"objeto1")==0);
	assert(getTamanioObj(obj) == 10);
	assert(getVencimientoObj(obj) == 2013);
	assert(getUltimaModificacionObj(obj) == 1900);
	showObjEcontrado(obj);
	assert(getCapacidadCache(c) == 5);
	assert(getCantidadObjCache(c) == 1);
	assert(getHitsCache(c) == 1);
	assert(esVacioCache(c) == 0);
	assert(esLlenoCache(c) == 0);

	cout << "* hay 1 objeto, no es el buscado" << endl;
	obj = buscarObjetoCache("url2",2012,c);
	showObjEcontrado(obj);
	assert(getCapacidadCache(c) == 5);
	assert(getCantidadObjCache(c) == 1);
	assert(getHitsCache(c) == 1);
	assert(esVacioCache(c) == 0);
	assert(esLlenoCache(c) == 0);

	cout << "* hay 1 objeto, es el buscado y esta vencido" << endl;
	obj = buscarObjetoCache("url1",2014,c);
	showObjEcontrado(obj);
	assert(getCapacidadCache(c) == 5);
	assert(getCantidadObjCache(c) == 0);
	assert(getHitsCache(c) == 1);
	assert(esVacioCache(c) == 1);
	assert(esLlenoCache(c) == 0);

	cout << "* no hay objetos" << endl;
	obj = buscarObjetoCache("url1",2012,c);
	showObjEcontrado(obj);
	assert(getCapacidadCache(c) == 5);
	assert(getCantidadObjCache(c) == 0);
	assert(getHitsCache(c) == 1);
	assert(esVacioCache(c) == 1);
	assert(esLlenoCache(c) == 0);

	cout << "* hay 5 objetos, busco el 3 y no esta vencido" << endl;
	insertarObjetoCache("url1","objeto1",10,2013,1900,c);
	insertarObjetoCache("url2","objeto2",20,2014,1900,c);
	insertarObjetoCache("url3","objeto3",30,2015,1900,c);
	insertarObjetoCache("url4","objeto4",40,2000,1900,c);
	insertarObjetoCache("url5","objeto5",50,2013,1900,c);
	obj = buscarObjetoCache("url3",2012,c);
	assert(strcmp(getIdObj(obj),"url3")==0);
	assert(strcmp(getResponse(obj),"objeto3")==0);
	assert(getTamanioObj(obj) == 30);
	assert(getVencimientoObj(obj) == 2015);
	assert(getUltimaModificacionObj(obj) == 1900);
	showObjEcontrado(obj);
	assert(getCapacidadCache(c) == 5);
	assert(getCapacidadCache(c) == 5);
	assert(getCantidadObjCache(c) == 5);
	assert(getHitsCache(c) == 2);
	assert(esVacioCache(c) == 0);
	assert(esLlenoCache(c) == 1);
	showCache(c);

	cout << "* hay 5 objetos, busco el 1 y no esta vencido" << endl;
	obj = buscarObjetoCache("url1",2012,c);
	assert(strcmp(getIdObj(obj),"url1")==0);
	assert(strcmp(getResponse(obj),"objeto1")==0);
	assert(getTamanioObj(obj) == 10);
	assert(getVencimientoObj(obj) == 2013);
	assert(getUltimaModificacionObj(obj) == 1900);
	showObjEcontrado(obj);
	assert(getCapacidadCache(c) == 5);
	assert(getCantidadObjCache(c) == 5);
	assert(getHitsCache(c) == 3);
	assert(esVacioCache(c) == 0);
	assert(esLlenoCache(c) == 1);
	showCache(c);

	cout << "* hay 5 objetos, busco el primero y esta vencido" << endl;
	obj = buscarObjetoCache("url2",2020,c);
	showObjEcontrado(obj);
	assert(getCapacidadCache(c) == 5);
	assert(getCantidadObjCache(c) == 4);
	assert(getHitsCache(c) == 3);
	assert(esVacioCache(c) == 0);
	assert(esLlenoCache(c) == 0);
	showCache(c);

	cout << "* hay 4 objetos, busco el ultimo y no esta vencido" << endl;
	obj = buscarObjetoCache("url1",2012,c);
	assert(strcmp(getIdObj(obj),"url1")==0);
	assert(strcmp(getResponse(obj),"objeto1")==0);
	assert(getTamanioObj(obj) == 10);
	assert(getVencimientoObj(obj) == 2013);
	assert(getUltimaModificacionObj(obj) == 1900);
	showObjEcontrado(obj);
	assert(getCapacidadCache(c) == 5);
	assert(getCantidadObjCache(c) == 4);
	assert(getHitsCache(c) == 4);
	assert(esVacioCache(c) == 0);
	assert(esLlenoCache(c) == 0);
	showCache(c);

	cout << "* hay 4 objetos, busco el segundo y no esta vencido" << endl;
	obj = buscarObjetoCache("url5",2012,c);
	assert(strcmp(getIdObj(obj),"url5")==0);
	assert(strcmp(getResponse(obj),"objeto5")==0);
	assert(getTamanioObj(obj) == 50);
	assert(getVencimientoObj(obj) == 2013);
	assert(getUltimaModificacionObj(obj) == 1900);
	showObjEcontrado(obj);
	assert(getCapacidadCache(c) == 5);
	assert(getCantidadObjCache(c) == 4);
	assert(getHitsCache(c) == 5);
	assert(esVacioCache(c) == 0);
	assert(esLlenoCache(c) == 0);
	showCache(c);

	cout << "* hay 4 objetos, busco el ultimo y esta vencido" << endl;
	obj = buscarObjetoCache("url5",2014,c);
	showObjEcontrado(obj);
	assert(getCapacidadCache(c) == 5);
	assert(getCantidadObjCache(c) == 3);
	assert(getHitsCache(c) == 5);
	assert(esVacioCache(c) == 0);
	assert(esLlenoCache(c) == 0);
	showCache(c);

	cout << "* hay 3 objetos, busco el segundo y esta vencido" << endl;
	obj = buscarObjetoCache("url3",2020,c);
	showObjEcontrado(obj);
	assert(getCapacidadCache(c) == 5);
	assert(getCantidadObjCache(c) == 2);
	assert(getHitsCache(c) == 5);
	assert(esVacioCache(c) == 0);
	assert(esLlenoCache(c) == 0);
	showCache(c);

	cout << "* hay 2 objetos, busco el primero y no esta vencido" << endl;
	obj = buscarObjetoCache("url4",1900,c);
	assert(strcmp(getIdObj(obj),"url4")==0);
	assert(strcmp(getResponse(obj),"objeto4")==0);
	assert(getTamanioObj(obj) == 40);
	assert(getVencimientoObj(obj) == 2000);
	assert(getUltimaModificacionObj(obj) == 1900);
	showObjEcontrado(obj);
	assert(getCapacidadCache(c) == 5);
	assert(getCantidadObjCache(c) == 2);
	assert(getHitsCache(c) == 6);
	assert(esVacioCache(c) == 0);
	assert(esLlenoCache(c) == 0);
	showCache(c);

	cout << "* hay 2 objetos, busco el primero y esta vencido" << endl;
	obj = buscarObjetoCache("url1",2020,c);
	showObjEcontrado(obj);
	assert(getCapacidadCache(c) == 5);
	assert(getCantidadObjCache(c) == 1);
	assert(getHitsCache(c) == 6);
	assert(esVacioCache(c) == 0);
	assert(esLlenoCache(c) == 0);
	showCache(c);

	cout << "* hay 1 objeto, lo busco y esta vencido" << endl;
	obj = buscarObjetoCache("url4",2012,c);
	showObjEcontrado(obj);
	assert(getCapacidadCache(c) == 5);
	assert(getCantidadObjCache(c) == 0);
	assert(getHitsCache(c) == 6);
	assert(esVacioCache(c) == 1);
	assert(esLlenoCache(c) == 0);
	showCache(c);
	cout << ">> Fin Test5" << endl << endl;

	cout << "Test6" << endl;

	seteoCap = setCapacidadCache(10,c);
	assert(seteoCap == 0);
	assert(getCapacidadCache(c) == 10);

	setTamanioPermitidoMaxCache(220,c);
	assert(getTamanioPermitidoMaxCache(c) == 220);

	cout << "* hay 10 objetos, busco uno que no esta" << endl;
	insertarObjetoCache("url1","objeto1",10,2013,1901,c);
	insertarObjetoCache("url2","objeto2",20,2014,1902,c);
	insertarObjetoCache("url3","objeto3",30,2015,1903,c);
	insertarObjetoCache("url4","objeto4",40,2000,1904,c);
	insertarObjetoCache("url5","objeto5",50,2015,1905,c);
	insertarObjetoCache("url6","objeto6",60,2015,1906,c);
	insertarObjetoCache("url7","objeto7",70,2015,1907,c);
	insertarObjetoCache("url8","objeto8",80,2015,1908,c);
	insertarObjetoCache("url9","objeto9",90,2015,1909,c);
	insertarObjetoCache("url10","objeto10",100,2015,1910,c);
	obj = buscarObjetoCache("url11",2012,c);
	showObjEcontrado(obj);
	showCache(c);
	assert(getCantidadObjCache(c) == 10);
	assert(getHitsCache(c) == 6);
	assert(esVacioCache(c) == 0);
	assert(esLlenoCache(c) == 1);

	cout << "* hay 10 objetos, busco el octavo y no esta vencido" << endl;
	obj = buscarObjetoCache("url8",1990,c);
	assert(strcmp(getIdObj(obj),"url8")==0);
	assert(strcmp(getResponse(obj),"objeto8")==0);
	assert(getTamanioObj(obj) == 80);
	assert(getVencimientoObj(obj) == 2015);
	assert(getUltimaModificacionObj(obj) == 1908);
	showObjEcontrado(obj);
	showCache(c);
	assert(getCantidadObjCache(c) == 10);
	assert(getHitsCache(c) == 7);
	assert(esVacioCache(c) == 0);
	assert(esLlenoCache(c) == 1);

	cout << "* hay 10 objetos, busco el cuarto y no esta vencido" << endl;
	obj = buscarObjetoCache("url4",1990,c);
	assert(strcmp(getIdObj(obj),"url4")==0);
	assert(strcmp(getResponse(obj),"objeto4")==0);
	assert(getTamanioObj(obj) == 40);
	assert(getVencimientoObj(obj) == 2000);
	assert(getUltimaModificacionObj(obj) == 1904);
	showObjEcontrado(obj);
	showCache(c);
	assert(getCantidadObjCache(c) == 10);
	assert(getHitsCache(c) == 8);
	assert(esVacioCache(c) == 0);
	assert(esLlenoCache(c) == 1);

	cout << "* hay 10 objetos, busco el primero y no esta vencido" << endl;
	obj = buscarObjetoCache("url1",1990,c);
	assert(strcmp(getIdObj(obj),"url1")==0);
	assert(strcmp(getResponse(obj),"objeto1")==0);
	assert(getTamanioObj(obj) == 10);
	assert(getVencimientoObj(obj) == 2013);
	assert(getUltimaModificacionObj(obj) == 1901);
	showObjEcontrado(obj);
	showCache(c);
	assert(getCantidadObjCache(c) == 10);
	assert(getHitsCache(c) == 9);
	assert(esVacioCache(c) == 0);
	assert(esLlenoCache(c) == 1);

	cout << ">> Fin Test6" << endl << endl;

	cout << "Test7" << endl;

	cout << "* actualizo el obj 'url1'" << endl;
	int upd = actualizarObjetoCache("url1","objeto0001",15,3013,2222,c);
	assert(upd == 0);
	showCache(c);
	obj = buscarObjetoCache("url1",1990,c);
	assert(strcmp(getIdObj(obj),"url1")==0);
	assert(strcmp(getResponse(obj),"objeto0001")==0);
	assert(getTamanioObj(obj) == 15);
	assert(getVencimientoObj(obj) == 3013);
	assert(getUltimaModificacionObj(obj) == 2222);
	showObjEcontrado(obj);

	cout << "* actualizo el obj 'url5'" << endl;
	upd = actualizarObjetoCache("url5","objeto0005",55,3015,5555,c);
	assert(upd == 0);
	showCache(c);

	cout << "* intento actualizar un obj que no existe: 'url20'" << endl;
	upd = actualizarObjetoCache("url20","objeto0020",28,3028,2828,c);
	assert(upd == -1);
	showCache(c);

	cout << "* actualizo el obj 'url2'" << endl;
	upd = actualizarObjetoCache("url2","objeto0002",22,3012,1111,c);
	assert(upd == 0);
	showCache(c);

	cout << "*[CACHE HANDLER] inserto una polenta request y response. Pruebo cachear" << endl;

	char * request = "GET http://www-redes.fing.edu.uy/index_files/webserver_content_data/suyfas.jpg HTTP/1.1\r\nHost: www-redes.fing.edu.uy\r\nProxy-Connection: keep-alive\r\nUser-Agent: Mozilla/5.0 (X11; Linux i686) AppleWebKit/535.19 (KHTML, like Gecko) Ubuntu/12.04 Chromium/18.0.1025.168 Chrome/18.0.1025.168 Safari/535.19\r\nAccept: */*\r\nReferer: http://www-redes.fing.edu.uy/index_files/webserver_content.html\r\nAccept-Encoding: gzip,deflate,sdch\r\nAccept-Language: en-US,en;q=0.8\r\nAccept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.3\r\n\r\n";

	char * response = "HTTP/1.1 200 OK\r\nDate: Fri, 14 Sep 2012 00:53:42 GMT\r\nServer: Apache/2.2.17 (Ubuntu)\r\nLast-Modified: Tue, 21 Aug 2012 21:41:05 GMT\r\nETag: 1bf667-128a-4c7cd7bc4d36c\r\nAccept-Ranges: bytes\r\nContent-Length: 4746\r\nContent-Type: image/jpeg\r\n\r\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXxx\r\n\r\n";

	//cache *laCache;
	initCacheHandler(c);
	setTamanioPermitidoMaxCache(220000,c);

	cachear(request, response, strlen(response));
	showCache(c);
	char* respuestaCache = consultarCache(request);
	cout<<"respuestaCahce__________"<<respuestaCache<<endl;
	return 0;
}
