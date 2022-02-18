#define PUERTO_DATOS 5555
#define PUERTO_DEFECTO_ADMIN 9999
#define IP_SERVIDOR "127.0.0.1"
#define MAXLEN 1024


#include "../../socketLib.h"
#include "../../stringLib.h"
#include <string.h>
#include <stdio.h>
#include <string>
#include <stdlib.h>
#include <iostream>
using namespace std;


int main(int argc, char **argv)
{
	/*
	* Descriptores de socket servidor y de socket con el cliente
	*/
	int Socket_Servidor;
	int Socket_Cliente;
	//char Cadena[100];

	/*
	* Se abre el socket servidor, con el servicio "cpp_java" dado de
	* alta en /etc/services.
	*/

	setLogLevel(LOGGER_TRACE);

	Socket_Servidor = AperturaSocketComoServidor (PUERTO_DEFECTO_ADMIN);
	if (Socket_Servidor == -1)
	{
		printf ("No se puede abrir socket servidor\n");
		exit (-1);
	}

	/*
	* Se espera un cliente que quiera conectarse
	*/
	Socket_Cliente = AceptarConexionComoServidor (Socket_Servidor);
	if (Socket_Servidor == -1)
	{
		printf ("No se puede abrir socket de cliente\n");
		exit (-1);
	}

	/*
	* Se lee la informacion del cliente, suponiendo que va a enviar 
	* 5 caracteres.
	*/

	//char* Cadena = (char*)malloc(MAXLEN);
	char* Cadena = new char[MAXLEN];
	int data_size = MAXLEN;
	LeerDelSocketServWeb(Socket_Cliente, Cadena, data_size);


	//printf("Recibido del servidor (%d bytes): %s\n", DatosLeidos, Cadena);

	/*
	* Se escribe en pantalla la informacion que se ha recibido del
	* cliente
	*/

	int largo = strlen(Cadena);

	cout << "largo de los datos leidos " << largo << "\n" ;

	//string res = string(Cadena);

	//printf ("DatosLeidos : %s\n",res.substr(0,largo));

	//printf ("Soy Servior, he recibido : %s\n", lala);

	printf ("Soy Servior, he recibido : %s\n", Cadena);

	string res = string(Cadena);
	res = res.substr(0,largo);
	printf ("Soy Servior, he recibido (recortado) : %s\n", Cadena);

	delete(Cadena);

	/*
	* Se prepara una cadena de texto para enviar al cliente. La longitud
	* de la cadena es 5 letras + \0 al final de la cadena = 6 caracteres
	*/
	// strcpy (Cadena, "Adios");
	// Escribe_Socket (Socket_Cliente, Cadena, 6);

	/*
	* Se cierran los sockets
	*/
	close (Socket_Cliente);
	close (Socket_Servidor);
}
