
#include "pct.h"
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
#define MAX_BUF_SIZE	1024

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
	
    // abro archivo a transmitir
    //char* texto[1000];
    //for (int i=0; i<2048; i++)
    //	texto[i]=i%256;

    char *texto = "Hola este es un mensaje de prueba";
    printf("Envio Archivo de largo %d \n",MAX_BUF_SIZE);



    // comienzo proceso de envio
    int enviado  = 0;
    int buffSize = 0;
    cantInt      = 0;
    printf("Envio Archivo de largo %d \n",MAX_BUF_SIZE);
    while (enviado < MAX_BUF_SIZE) {
		buffSize = MAX_BUF_SIZE;
		if ( (MAX_BUF_SIZE - enviado) < MAX_BUF_SIZE ){
			buffSize = MAX_BUF_SIZE - enviado;
		}
		result = escribirPCT(&texto[enviado], buffSize);
			if ( result == -1 ){
				printf("Error al enviar datos\n");
				sleep(3);
			result = 0;
			}
		if(result >= 0){
				usleep(500);
				printf("envie %d bytes de datos\n",result);
			enviado = enviado + result;
			}
		if ( cantInt > 50 ){
				enviado = MAX_BUF_SIZE;
		}
		sleep(5);
		cantInt++;
    }
    printf("Archivo enviado\n");

    // cierro conexion
    cerrarPCT();
    printf("Cerrando sesion PCT\n");
    sleep(5);
    printf("Envio Finalizado\n");
    
}


