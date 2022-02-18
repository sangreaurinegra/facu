#include "pct.h"
#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <ctype.h>
#include <string.h>
#include <stddef.h>
#include <netdb.h>
#include <stddef.h>
#include <iostream>

using namespace std;

#define MAX_INTENTOS	1000
#define MAX_FILE_SIZE	1024

int main( int argc, const char* argv[] )
{
    int IdPCT,result,i;
    int cantInt,puerto, puertoOrig,puertoDest, tam_archivo;
    char puertoO,puertoD;
    
    struct in_addr direccion;



    // cargo variables para conexion
    puertoO = (char) 3;
    char buf[MAX_FILE_SIZE];
    inet_aton("127.0.0.1", &direccion);
    IdPCT = crearPCT(direccion);
    if ( IdPCT  == -1 ){
        printf( "Error al crear el Socket\n");
        exit(-1);
    }
    printf( "Esperando Conexion\n");

    result = aceptarPCT(puertoO);
    if ( result == -1 ) {
        printf( "Error al aceptar conexion\n");
        exit(-1);
    }
    printf( "Equipo conectado\n");
    sleep(10);


    // Espero llegada de datos
    // voy grabando lo recibido
    cantInt = 0;
    int leido = 0;
    result = 0;
    while (result >= 0) {
			result = leerPCT(buf, MAX_FILE_SIZE);
			printf("Recibidos %d bytes\n",result);


	         buf[MAX_FILE_SIZE]='\0';

	         cout << "lEIDO:" << buf << "." << endl;
	         buf[0]='\0';
    }
    sleep(10);

    // cierro conexion
    cerrarPCT();
    printf("Cerrando sesion PCT\n");
    sleep(5);
    printf("Recepcion Finalizada\n");

    
}
