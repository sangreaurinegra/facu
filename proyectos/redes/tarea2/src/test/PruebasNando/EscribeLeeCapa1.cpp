#include "EscribeLeeCapa1.h"
#include "../../logger.h"
#include "../../stringLib.h"
#include "../../buffer.h"


using namespace std;

enum Estado {
   INIT, DESCONECTADO, CONECTADO, ESPERANDO_SYN_ACK, ESPERANDO_CONEXION, LOGRE_CONEXION, RECIBI_SYN
};

Estado estadoActual = INIT;
int globalPctSocket;
struct sockaddr_in globalDireccionSocket;

unsigned char globalPuertoOrigen;
unsigned char globalPuertoDestino;

struct in_addr globalIPOrigen;



int escribirPCT(const void *buf, size_t len){

	if (estadoActual == DESCONECTADO)
	{
		return -1;
	}
	else
	{
		buffer * b = createBuffer(1024);
		writeBuffer((char*) buf, len, b);
	}


	return 0;
}

int leerPCT(void *buf, size_t len){

	/*recibirPaqueteDatos( (char*)buf,len);*/

	return 0;
}


