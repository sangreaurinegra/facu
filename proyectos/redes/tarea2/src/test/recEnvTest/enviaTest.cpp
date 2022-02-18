#include "../../pct.h"
#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <ctype.h>
#include <string.h>
#include <iostream>

using namespace std;

#define MAX_INTENTOS	10
#define MAX_BUF_SIZE	30

int main( int argc, const char* argv[] )
{
    int IdPCT,result,cantEnvio;
    int count,cantInt,puerto, puertoOrig,puertoDest, tam_archivo;
    unsigned char puertoO,puertoD;

    
    struct in_addr dirOrig;
    struct in_addr dirDest;



    // cargo variables de la conexion
    puertoOrig=1;
    puertoO = (unsigned char) puertoOrig;
    puertoDest=3;
    puertoD = (unsigned char) puertoDest;


    // configuro direccion del equipo local
    inet_aton("127.0.0.2", &dirOrig);
    IdPCT = crearPCT(dirOrig);
    if ( IdPCT  == -1 ){
        printf( "Error al crear el Socket\n");
        exit(-1);
    }
    printf( "Conexion creada\n");

    // Configuro direccion remota y conecto
    inet_aton("127.0.0.1", &dirDest);
    result = conectarPCT(puertoO,puertoD,dirDest);
    if ( result == -1 ) {
        printf( "Error al conectar Socket\n");
        exit(-1);
    }
    printf( "Equipo conectado\n");


    char *texto = "Hola este es un mensaje";
	result = escribirPCT(&texto[0], strlen(texto));

	cout <<"enviaTest result___"<<result<<endl;

    printf("Archivo enviado \n");

    cerrarPCT();
    sleep(5);

    printf("Envio Finalizado\n");
    
}


