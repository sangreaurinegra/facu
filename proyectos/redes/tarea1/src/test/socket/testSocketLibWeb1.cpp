#define PUERTO_DATOS 5558
#define PUERTO_DEFECTO_ADMIN 6666
#define IP_SERVIDOR "127.0.0.1"
#define MAXLEN 1500


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
		char* Cadena = new char[MAXLEN + 1];
		int data_size = MAXLEN;
		int DatosLeidos;


		//DatosLeidos = LeerDelSocket (Socket_Cliente, Cadena, data_size);
		DatosLeidos = LeerDelSocketServWeb(Socket_Cliente, Cadena, data_size);

		printf ("Soy Servior, he recibido del Cliente :\n %s\n", Cadena);

		/////////////////////////////////////////// Obtengo la IP del Host

		char* hostName = new char[strlen(Cadena) + 1];
		memcpy(hostName, Cadena,strlen(Cadena));
		hostName = obtenerHost(hostName);
		printf("EL hostName ES %s\n", hostName);


		struct addrinfo *result;
		int error;
		//struct addrinfo *res;

		error = getaddrinfo(hostName, NULL, NULL, &result);

		if (error != 0)
		{
			fprintf(stderr, "error in getaddrinfo: %s\n", gai_strerror(error));
			return EXIT_FAILURE;
		}

		struct sockaddr_in  *sockaddr_ipv4;
		int i = 1;

		sockaddr_ipv4 = (struct sockaddr_in *) result->ai_addr;
		char * IP = inet_ntoa(sockaddr_ipv4->sin_addr);
		printf("\tIPv4 address %s\n",IP );

		freeaddrinfo(result);

		//////////////////////////////////////////

		log("AperturaSocketComoCliente: Abro la conexion con el servidor \n\n",LOGGER_TRACE);

		socket_Servidor_web = AperturaSocketComoCliente(IP, 80);

		//log("EscribirEnSocket: Le mando el mensaje al servidor web ",LOGGER_TRACE);

		int largodatosescritos = strlen(Cadena);
		int Datos_Escritos = EscribirEnSocketServWeb(socket_Servidor_web, Cadena, DatosLeidos);

		delete[] Cadena;

		//log("LeerDelSocket: Leo el mensaje que envia el servidor web ",LOGGER_TRACE);

		data_size = MAXLEN;
		char* Cadena1 = new char[MAXLEN + 1];
		DatosLeidos = 0;
		DatosLeidos = LeerDelSocketServWeb(socket_Servidor_web, Cadena1, data_size);


		printf ("Soy Servior, he recibido del servidor web :\n %s\n\n\n", Cadena1);
		cout << "Bytes de lo que lei : " << DatosLeidos << "\n\n";

		int largoResponse = 0;
		int largototalactual;
		char* resaux = NULL;
		char* res;
		char* auxi;

		res = new char[DatosLeidos + 1];
		res[0] = '\0';
		//cout << "LARGO DE RES: " << strlen(res) << "\n";
		//cout << "LARGO DE Cadena1: " << strlen(Cadena1) << "\n";
		//strcpy(res,Cadena1);
		memcpy(res,Cadena1,DatosLeidos);
		res[DatosLeidos]= '\0';
		largoResponse = DatosLeidos;

		while (DatosLeidos != 0)
		{
			/*largototalactual = DatosLeidos;
			//resaux = new char[largototalactual + 1];
			resaux = new char[largototalactual + 1];
			resaux[0] = '\0';
			//strcpy(resaux,Cadena1);
			memcpy(resaux,Cadena1,largototalactual);
			//memmove(resaux,Cadena1,DatosLeidos);
			resaux[largototalactual + 1] = '\0';*/

			/*log("EscribirEnSocket: Escribo en el socket del navegador lo que mando el servidor web \n\n",LOGGER_TRACE);
			largodatosescritos = DatosLeidos;
			Datos_Escritos = 0;
			//Datos_Escritos = EscribirEnSocketServWeb(Socket_Cliente, Cadena1, largodatosescritos);
			Datos_Escritos = EscribirEnSocketServWeb(Socket_Cliente, resaux, largodatosescritos);
			cout << "Bytes de lo que escribi : " << Datos_Escritos << "\n\n";*/

			//delete[] resaux;
			//delete[] Cadena1;

			log("EscribirEnSocket: Leo del socket del servidor web ",LOGGER_TRACE);

			//Concateno lo leido al acumulado del response
			data_size = MAXLEN;
			Cadena1 = new char[MAXLEN + 1];
			DatosLeidos = 0;
			DatosLeidos = LeerDelSocketServWeb (socket_Servidor_web, Cadena1, data_size);
			cout << "Lei : " << Cadena1 << "\n";
			cout << "Largo de lo que lei: " << strlen(Cadena1) << "\n";
			cout << "Bytes de lo que lei : " << DatosLeidos << "\n\n";

			//printf ("Soy Servior, he recibido del servidor web :\n %s\n", Cadena1);

			/*cout << "LARGO DE RES: " << strlen(res) << "\n";
			cout << "LARGO DE Cadena1: " << strlen(Cadena1) << "\n";*/
			if (DatosLeidos > 0)
			{

				largototalactual = largoResponse + DatosLeidos;
				cout << "largototalactual: " << largototalactual  << "\n";
				resaux = new char[largototalactual + 1];
				//resaux[0] = '\0';
				//strcat(resaux,res);
				memcpy(resaux,res,largoResponse);
				resaux[largoResponse + 1] = '\0';
				cout << "resaux 1: " << "   largo: " << strlen(resaux) << "\n";
				cout << "resaux 1: \n " << resaux << "\n\n";
				/*auxi = new char[DatosLeidos + 1];
				memcpy(auxi,Cadena1,DatosLeidos);
				cout << "AUXI : \n " << auxi << "\n\n";
				strcat(resaux,auxi);*/
				memcpy(resaux + largoResponse,Cadena1,DatosLeidos);
				cout << "resaux 2: " << "   largo: " << strlen(resaux) << "\n";
				cout << "resaux 2: \n " << resaux << "\n\n";
				resaux[largototalactual] = '\0';
				cout << "resaux 3: " << "   largo: " << strlen(resaux) << "\n";

				delete[] res;

				res = new char[largototalactual + 1];
				res[0] = '\0';
				//strcpy(res,resaux);
				cout << "1111111111111111: " << "\n";
				memcpy(res,resaux,largoResponse + DatosLeidos);
				cout << "2222222222222222: " << "\n";
				res[largototalactual] = '\0';
				//cout << "res: " << "   largo: " << strlen(res) << "\n";
				cout << "3333333333333333: " << "\n";
				delete[] resaux;
				cout << "4444444444444444: " << "\n";

				largoResponse = largoResponse + DatosLeidos;
			}

		}


		cout << "\n\nlargoResponse (bytes leidos): " << largoResponse << "\n";
		cout << "Largo de res (cantidad de caracteres): " << strlen(res) << "\n";
		cout << "Size de res (cantidad de bytes): " << sizeof(res) << "\n";
		printf ("*********************************************\n\n\nSoy Servior, EL ACUMULADO DEL RESPONSE ES :\n %s\n**********************************************\n\n\n", res);


		char * resLecura = new char[largoResponse + 1];
		resLecura[0] = '\0';
		//strcpy(resLecura,res);
		memcpy(resLecura,res,largoResponse);
		resLecura[largoResponse] = '\0';
		cout << "Largo de COPIA res (cantidad de caracteres): " << strlen(resLecura) << "\n";
		cout << "Size de COPIA res (cantidad de bytes): " << sizeof(resLecura) << "\n";
		printf ("*********************************************\n\n\nSoy Servior, COPIA ACUMULADO DEL RESPONSE ES :\n %s\n**********************************************\n\n\n", resLecura);
		int Datos_EscritosAux = 0;
		largodatosescritos = largoResponse;
		cout << "largodatosescritos: " << largodatosescritos << "\n";


		while (largodatosescritos != 0)
		{
			//log("EscribirEnSocket: Escribo en el socket del navegador lo que mando el servidor web ",LOGGER_TRACE);
			Datos_Escritos = 0;
			Datos_Escritos = EscribirEnSocketServWeb(Socket_Cliente, resLecura + Datos_EscritosAux, largodatosescritos);
			cout << "Datos_Escritos: " << Datos_Escritos << "\n";

			Datos_EscritosAux = Datos_Escritos;
			largodatosescritos = largodatosescritos - Datos_EscritosAux;
			cout << "largodatosescritos while: " << largodatosescritos << "\n";

		}

		delete[] resLecura;
		delete[] res;
		delete[] Cadena1;

		/*
		* Se cierran los sockets
		*/
		close (socket_Servidor_web);
		close (Socket_Cliente);

		log("Termine de mandar el mje del servidor al navegador, ahora vuelvo a empezar ",LOGGER_TRACE);

	}

	close (Socket_Servidor);
}
