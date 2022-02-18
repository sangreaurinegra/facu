#define PUERTO_DATOS 5553
#define PUERTO_DEFECTO_ADMIN 6666
#define IP_SERVIDOR "127.0.0.1"
#define MAXLEN 1024


#include "../../socketLib.h"
#include "../../stringLib.h"
#include <string.h>
#include <stdio.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdlib.h>
#include <iostream>
using namespace std;


char* obtenerHost(char* package){
	string auxiliar = string(package);
	int host = auxiliar.find("Host:");
	int ind2 = auxiliar.find("User-Agent:");
	auxiliar = auxiliar.substr(host + 6,ind2 - host -8);
	char* result = strdup(auxiliar.c_str());
	return result;
}


int main(int argc, char **argv)
{
	/*
	* Descriptores de socket servidor y de socket con el cliente
	*/
	int Socket_Servidor;
	int Socket_Cliente;
	int socket_Servidor_web;
	//char Cadena[100];

	/*
	* Se abre el socket servidor, con el servicio "cpp_java" dado de
	* alta en /etc/services.
	*/

	setLogLevel(LOGGER_TRACE);

	log("AperturaSocketEnServidor: Se abre el socket servidor, con el servicio ",LOGGER_TRACE);

	Socket_Servidor = AperturaSocketComoServidor (PUERTO_DATOS);
	if (Socket_Servidor == -1)
	{
		printf ("No se puede abrir socket servidor\n");
		exit (-1);
	}



	while(true)
	{
		/*
		* Se espera un cliente que quiera conectarse
		*/

		log("AceptarConexionComoServidor: Se espera un cliente que quiera conectarse ",LOGGER_TRACE);

		Socket_Cliente = AceptarConexionComoServidor (Socket_Servidor);
		if (Socket_Servidor == -1)
		{
			printf ("No se puede abrir socket de cliente\n");
			exit (-1);
		}

		log("Cliente se conecto ",LOGGER_TRACE);

		/*
		* Se lee la informacion del cliente, suponiendo que va a enviar
		* 5 caracteres.
		*/

		log("LeerDelSocket: Leo desde el sochet del cliente ",LOGGER_TRACE);

		//char* Cadena = (char*)malloc(MAXLEN);
		char* Cadena = new char[MAXLEN];
		int data_size = MAXLEN;
		int DatosLeidos;


		DatosLeidos = LeerDelSocket (Socket_Cliente, Cadena, data_size);


		//printf("Recibido del servidor (%d bytes): %s\n", DatosLeidos, Cadena);

		/*
		* Se escribe en pantalla la informacion que se ha recibido del
		* cliente
		*/

		/*int largo = strlen(Cadena);

		cout << "largo de los datos leidos del Cliente" << largo << "\n" ;*/

		//string res = string(Cadena);

		//printf ("DatosLeidos : %s\n",res.substr(0,largo));

		//printf ("Soy Servior, he recibido : %s\n", lala);

		printf ("Soy Servior, he recibido del Cliente :\n %s\n", Cadena);

		/*string res = string(Cadena);
		res = res.substr(0,largo);
		printf ("Soy Servior, he recibido (recortado) : %s\n", Cadena);*/


		//La funcion gerpeername sirve para determinar la ip del cliente conectado a determinado socket
		/*sockaddr_in peeraddr;
		unsigned int size = sizeof(peeraddr);

		getpeername(Socket_Servidor, (struct sockaddr *)&peeraddr, &size);

		struct sockaddr_in *s = (struct sockaddr_in *)&peeraddr;

		char* IP = inet_ntoa(peeraddr.sin_addr);
		cout << "Accepted connection from " << IP << "\r\n";*/

		//Ip del sitio de la facultad para realiar pruebas 164.73.44.13
		//Ahora debemos abrir un socket para conectarnos con el servidor web para perdirle el recurso
		//que se nos solicito desde el cliente (Navegador en este caso)

		///////////////////////////////////////////
		char* hostName = new char[strlen(Cadena)];
		memcpy(hostName, Cadena,strlen(Cadena));
		hostName = obtenerHost(hostName);
		printf("EL hostName ES %s\n", hostName);


		struct addrinfo *result;
		int error;
		struct addrinfo *res;

		error = getaddrinfo(hostName, NULL, NULL, &result);

		//printf("getaddrinfo llamado %s\n");

		if (error != 0)
		{
			fprintf(stderr, "error in getaddrinfo: %s\n", gai_strerror(error));
			return EXIT_FAILURE;
		}

		//printf("Paso manejo de error %s\n");

		struct sockaddr_in  *sockaddr_ipv4;
		int i = 1;

		//printf("Paso declaracion de variables %s\n");

		sockaddr_ipv4 = (struct sockaddr_in *) result->ai_addr;
		char * IP = inet_ntoa(sockaddr_ipv4->sin_addr);
		printf("\tIPv4 address %s\n",IP );

		//printf("Paso inicializacion de variables %s\n");


		/*for(res=result; res != NULL ;res=res->ai_next) {

			printf("getaddrinfo response %d\n", i++);
			printf("\tFamily: ");
			switch (res->ai_family) {
				case AF_UNSPEC:
					printf("Unspecified\n");
					break;
				case AF_INET:
					printf("AF_INET (IPv4)\n");
					sockaddr_ipv4 = (struct sockaddr_in *) res->ai_addr;
					printf("\tIPv4 address %s\n",
						inet_ntoa(sockaddr_ipv4->sin_addr) );
					break;*/
				/*case AF_INET6:
					printf("AF_INET6 (IPv6)\n");
					// the InetNtop function is available on Windows Vista and later
					// sockaddr_ipv6 = (struct sockaddr_in6 *) res->ai_addr;
					// printf("\tIPv6 address %s\n",
					//    InetNtop(AF_INET6, &sockaddr_ipv6->sin6_addr, ipstringbuffer, 46) );

					// We use WSAAddressToString since it is supported on Windows XP and later
					sockaddr_ip = (LPSOCKADDR) res->ai_addr;
					// The buffer length is changed by each call to WSAAddresstoString
					// So we need to set it for each iteration through the loop for safety
					ipbufferlength = 46;
					iRetval = WSAAddressToString(sockaddr_ip, (DWORD) res->ai_addrlen, NULL,
						ipstringbuffer, &ipbufferlength );
					if (iRetval)
						printf("WSAAddressToString failed with %u\n", WSAGetLastError() );
					else
						printf("\tIPv6 address %s\n", ipstringbuffer);
					break;*/
				/*case AF_NETBIOS:
					printf("AF_NETBIOS (NetBIOS)\n");
					break;*/
				/*default:
					printf("Other %ld\n", res->ai_family);
					break;
		        }
			}*/


		/*for (res = result; res != NULL; res = res->ai_next)
		{
			char hostname[NI_MAXHOST] = "";

			error = getnameinfo(res->ai_addr, res->ai_addrlen, hostname, NI_MAXHOST, NULL, 0, 0);
			if (error != 0)
			{
				fprintf(stderr, "error in getnameinfo: %s\n", gai_strerror(error));
				continue;
			}
			if (*hostname != '\0')
				printf("hostname: %s\n", hostname);
		}*/

		freeaddrinfo(result);

		//////////////////////////////////////////

		log("AperturaSocketComoCliente: Abro la conexion con el servidor ",LOGGER_TRACE);

		socket_Servidor_web = AperturaSocketComoCliente(IP, 80);

		log("EscribirEnSocket: Le mando el mensaje al servidor web ",LOGGER_TRACE);

		int largodatosescritos = strlen(Cadena);
		int Datos_Escritos = EscribirEnSocket (socket_Servidor_web, Cadena, largodatosescritos);

		delete(Cadena);

		log("LeerDelSocket: Leo el mensaje que envia el servidor web ",LOGGER_TRACE);

		data_size = MAXLEN;
		char* Cadena1 = new char[MAXLEN];
		DatosLeidos = 0;
		DatosLeidos = LeerDelSocket (socket_Servidor_web, Cadena1, data_size);

		printf ("Soy Servior, he recibido del servidor web :\n %s\n", Cadena1);

		log("EscribirEnSocket: Escribo en el socket del navegador lo que mando el servidor web ",LOGGER_TRACE);

		largodatosescritos = strlen(Cadena1);
		Datos_Escritos = 0;
		Datos_Escritos = EscribirEnSocket (Socket_Cliente, Cadena1, largodatosescritos);


		log("Termine de mandar el mje del servidor al navegador, ahora vuelvo a empezar ",LOGGER_TRACE);

		delete(Cadena1);

		/*data_size = MAXLEN;
		Cadena1 = new char[MAXLEN];
		DatosLeidos = 0;
		DatosLeidos = LeerDelSocket (socket_Servidor_web, Cadena1, data_size);

		printf ("Soy Servior, he recibido del servidor web :\n %s\n", Cadena1);


		while(strlen(Cadena1) != 0){

			log("EscribirEnSocket: Escribo en el socket del navegador lo que mando el servidor web ",LOGGER_TRACE);

			largodatosescritos = strlen(Cadena1);
			Datos_Escritos = 0;
			Datos_Escritos = EscribirEnSocket (Socket_Cliente, Cadena1, largodatosescritos);
			delete(Cadena1);

			printf ("Soy Servior, he recibido del servidor web :\n %s\n", Cadena1);

			data_size = MAXLEN;
			Cadena1 = new char[MAXLEN];
			DatosLeidos = 0;
			DatosLeidos = LeerDelSocket (socket_Servidor_web, Cadena1, data_size);

			printf ("Soy Servior, he recibido del servidor web :\n %s\n", Cadena1);
		}*/

		/*
		* Se prepara una cadena de texto para enviar al cliente. La longitud
		* de la cadena es 5 letras + \0 al final de la cadena = 6 caracteres
		*/
		// strcpy (Cadena, "Adios");
		// Escribe_Socket (Socket_Cliente, Cadena, 6);




		/*
		* Se cierran los sockets
		*/
		close (socket_Servidor_web);
		close (Socket_Cliente);
	}

	close (Socket_Servidor);
}
