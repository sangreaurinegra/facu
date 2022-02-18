#include "socketLib.h"
#include <netinet/in.h>
#include <arpa/inet.h>


#define MAX_PETICIONES SOMAXCONN


/*Se encarga de abrir la conexion en determinado socket,
asociar el socket al proceso y quedar a la escucha de
peticiones de conexion a dicho socket.
En caso de error retorna -1*/
int AperturaSocketComoServidor (int Puerto,string ip)
{
	int IDSocket;
	struct sockaddr_in DireccionSocket;
	int ResConexion;
	int ResListen;

	//Pido un descriptor de Socket
	IDSocket = socket (AF_INET, SOCK_STREAM, 0);
	if (IDSocket == -1)
	{
		log("SocketLib:AperturaSocketEnServidor: ERROR al obtener el Descriptor del Socket ",LOGGER_TRACE);
		return -1; //Error al obtener el descriptor
	}	
	
	DireccionSocket.sin_family = AF_INET;
	DireccionSocket.sin_port = htons(Puerto);
	DireccionSocket.sin_addr.s_addr = inet_addr(ip.c_str());//INADDR_ANY;
	
	//Realizo el bind para asociar el socket al proceso
	ResConexion = bind (IDSocket, (struct sockaddr *)&DireccionSocket, sizeof (DireccionSocket));
	if (ResConexion == -1)
	{
		log("SocketLib:AperturaSocketEnServidor: ERROR al hacer el bind del Socket ",LOGGER_TRACE);
		close (IDSocket);
		return -1;
	}
	
	//Su tuve exito en asociar el proceso al socket realizo el listen para quedar escuchando conexiones
	ResListen = listen(IDSocket, MAX_PETICIONES);
	
	if (ResListen == -1)
	{
	log("SocketLib:AperturaSocketEnServidor: ERROR al hacer el listen del Socket ",LOGGER_TRACE);
	close (IDSocket);
	return -1;
	}
	
	//Si se realizaron todas las operaciones con exito y el proceso quedo escuchando conexiones en el puerto indicado
	//se devuelve el descriptor del socket
	
	return IDSocket;
	
}

//Funcion que establece la conexion de un cliente con determinado socket, para ello se pasan como
//parametros el puerto y el host que identifican el servicio en el servidor, se retorna -1 en caso de error
//o el descriptor del socket en otro caso
int AperturaSocketComoCliente (char *IPServidor, int Puerto)
{
	int IDSocket;
	struct sockaddr_in DireccionSocket;
	int ResConexion;

	//Pido un descriptor de Socket
	IDSocket = socket (AF_INET, SOCK_STREAM, 0);
	if (IDSocket == -1)
	{
		log("SocketLib:AperturaSocketEnCliente: ERROR al obtener el Descriptor del Socket ",LOGGER_TRACE);
		return -1; //Error al obtener el descriptor
	}	
	
	DireccionSocket.sin_family = AF_INET;
	DireccionSocket.sin_port = htons(Puerto);
	DireccionSocket.sin_addr.s_addr =inet_addr(IPServidor);
	
	//Realizo el connect para establecer la conexion al socket
	ResConexion = connect (IDSocket, (struct sockaddr *)&DireccionSocket, sizeof (DireccionSocket));
	if (ResConexion == -1)
	{
		log("SocketLib:AperturaSocketEnCliente: ERROR al hacer el connect al socket ",LOGGER_TRACE);
		close (IDSocket);
		return -1;
	}
	
	//Si se realizaron todas las operaciones con exito y el se establecio la conexion con el socket
	//se devuelve el descriptor del mismo
	
	return IDSocket;
	
}

//Funcion que se encarga de aceptar las conexiones pendientes al socket indicado por el
//descriptor pasado como parametro, en caso de error retorna -1
int AceptarConexionComoServidor (int IDSocket)
{
	int IDSocketCliente;
	struct sockaddr AddrCliente;
	socklen_t LongitudCliente;
	
	//Llamamos a la funcion accept de forma de aceptar las peticiones de conexion de los clientes 
	//que estan esperando por las mismas, de lo contrario la llamda el bloqueante
	LongitudCliente = sizeof(AddrCliente);
	IDSocketCliente = accept(IDSocket, &AddrCliente, &LongitudCliente);
	if (IDSocketCliente == -1)
	{
		log("SocketLib:AceptarConexionEnServidor: ERROR al hacer el accept",LOGGER_DEBUG);
		return -1;
	}
	
	//Si se realizo el accept con exito se devuelve el descriptor de socket del cliente.
	
	return IDSocketCliente;
}

int LeerDelSocket (int IDSocket, char*DatosALeer, int largoBuffer)
{
	int CantDatosLeidosTotales = 0;
	int CantDatosLeidosInvocacion = 0;

	//Se verifica que los parametros de entrada sean validos
	if (IDSocket == -1)
	{
		log("SocketLib:LeerDelSocket: ERROR en el parametro IDSocket valor  - 1",LOGGER_DEBUG);
		return -1;
	}
	if (DatosALeer == NULL)
	{
		log("SocketLib:LeerDelSocket: ERROR en el parametro DatosALeer valor  NULL",LOGGER_DEBUG);
		return -1;
	}
	if (largoBuffer <= 0)
	{
		log("SocketLib:LeerDelSocket: ERROR en el parametro LargoDeDatos valor  0",LOGGER_DEBUG);
		return -1;
	}

	//Si todos los parametros son aceptables se pasa a realizar la lectura del mensaje

	//Realizamos las invocaciones que sean necesarias de forma de leer todo lo indicado por LargoDeDatos
	while (CantDatosLeidosTotales < largoBuffer)
	{
		log("SocketLib:LeerDelSocket: ENTRE AL WHILE",LOGGER_TRACE);

		//Invocamos el read con los parametros, el descriptor de socket del cual queremos leer, la pocision del buffer donde queremos dejar los
		//caracteres leidos y la cantidad que caracteres que resan leer
		CantDatosLeidosInvocacion = read(IDSocket, DatosALeer + CantDatosLeidosTotales, largoBuffer - CantDatosLeidosTotales);

		//Retorna 0 significa que el socket se cerro o se termino la lectura de datos exitosamente
		if (CantDatosLeidosInvocacion == 0)
			return CantDatosLeidosTotales;
		//Devuelve -1 en caso de error
		if (CantDatosLeidosInvocacion == -1)
		{
			switch (errno)
				{
					case EINTR:
						log("SocketLib:LeerDelSocket: ERROR EINTR en la funcion, se vuelve a intentar en 100 microsegundos",LOGGER_TRACE);
					case EAGAIN:
						log("SocketLib:LeerDelSocket: ERROR EAGAIN en la funcion, se vuelve a intentar en 100 microsegundos",LOGGER_TRACE);
						usleep (100);
						break;
					default:
						log("SocketLib:LeerDelSocket: ERROR en la funcion read retorna -1",LOGGER_DEBUG);
						return -1;
				}
		}
		else
		{
			//En caso de ser un valor > 0, indica la cantidad de datos leidos, actualizo el largo de caracteres leidos hasta el momento
			if (CantDatosLeidosInvocacion > 0)
				CantDatosLeidosTotales = CantDatosLeidosTotales + CantDatosLeidosInvocacion;
		}
	}

	return CantDatosLeidosTotales;

}

int LeerDelSocketServWeb (int IDSocket, char*DatosALeer, int largoBuffer)
{
	int CantDatosLeidosInvocacion = 0;

	//Se verifica que los parametros de entrada sean validos
	if (IDSocket == -1)
	{
		log("SocketLib:LeerDelSocket: ERROR en el parametro IDSocket valor  - 1",LOGGER_DEBUG);
		return -1;
	}
	if (DatosALeer == NULL)
	{
		log("SocketLib:LeerDelSocket: ERROR en el parametro DatosALeer valor  NULL",LOGGER_DEBUG);
		return -1;
	}
	if (largoBuffer <= 0)
	{
		log("SocketLib:LeerDelSocket: ERROR en el parametro LargoDeDatos valor  0",LOGGER_DEBUG);
		return -1;
	}

	//Si todos los parametros son aceptables se pasa a realizar la lectura del mensaje
	//Realizamos las invocaciones que sean necesarias de forma de leer todo lo indicado por LargoDeDatos

	CantDatosLeidosInvocacion = read(IDSocket, DatosALeer, largoBuffer);


	//Retorna 0 significa que el socket se cerro o se termino la lectura de datos exitosamente
	if (CantDatosLeidosInvocacion < 0)
	{
		return -1;
	}
	else
	{
		DatosALeer[CantDatosLeidosInvocacion] = '\0';
		return CantDatosLeidosInvocacion;
	}
}


int EscribirEnSocket (int IDSocket,const char*DatosAEscribir, int largoBuffer)
{
	int CantDatosEscritosTotales = 0;
	int CantDatosEscritosInvocacion = 0;

	//Se verifica que los parametros de entrada sean validos
	if (IDSocket == -1)
	{
		log("SocketLib:EscribirEnSocket: ERROR en el parametro IDSocket valor  - 1",LOGGER_DEBUG);
		return -1;
	}
	if (DatosAEscribir == NULL)
	{
		log("SocketLib:EscribirEnSocket: ERROR en el parametro DatosAEscribir valor  NULL",LOGGER_DEBUG);
		return -1;
	}
	if (largoBuffer <= 0)
	{
		log("SocketLib:EscribirEnSocket: ERROR en el parametro LargoDeDatos valor  0",LOGGER_DEBUG);
		return -1;
	}

	//Si todos los parametros son aceptables se pasa a realizar la lectura del mensaje

	//Realizamos las invocaciones que sean necesarias de forma de leer todo lo indicado por LargoDeDatos
	while (CantDatosEscritosTotales < largoBuffer)
	{
		//Invocamos el write con los parametros, el descriptor de socket del cual queremos leer, la pocision del buffer donde queremos dejar los
		//caracteres leidos y la cantidad que caracteres que resan leer
		CantDatosEscritosInvocacion = write(IDSocket, DatosAEscribir + CantDatosEscritosTotales, largoBuffer - CantDatosEscritosTotales);

		//Retorna 0 significa que el socket se cerro o se termino la lectura de datos exitosamente
		if (CantDatosEscritosInvocacion == 0)
			return CantDatosEscritosTotales;
		//Devuelve -1 en caso de error
		if (CantDatosEscritosInvocacion == -1)
		{
			log("SocketLib:EscribirEnSocket: ERROR en la funcion write retorna -1",LOGGER_DEBUG);
			return -1;
		}
		else
		{
			//En caso de ser un valor > 0, indica la cantidad de datos leidos, actualizo el largo de caracteres leidos hasta el momento
			if (CantDatosEscritosInvocacion > 0)
				CantDatosEscritosTotales = CantDatosEscritosTotales + CantDatosEscritosInvocacion;
		}
	}

	return CantDatosEscritosTotales;
}


int EscribirEnSocketServWeb (int IDSocket,const char*DatosAEscribir, int largoBuffer)
{
	int CantDatosEscritosInvocacion = 0;

	//Se verifica que los parametros de entrada sean validos
	if (IDSocket == -1)
	{
		log("SocketLib:EscribirEnSocket: ERROR en el parametro IDSocket valor  - 1",LOGGER_DEBUG);
		return -1;
	}
	if (DatosAEscribir == NULL)
	{
		log("SocketLib:EscribirEnSocket: ERROR en el parametro DatosAEscribir valor  NULL",LOGGER_DEBUG);
		return -1;
	}
	if (largoBuffer <= 0)
	{
		log("SocketLib:EscribirEnSocket: ERROR en el parametro LargoDeDatos valor  0",LOGGER_DEBUG);
		return -1;
	}

	//Si todos los parametros son aceptables se pasa a realizar la lectura del mensaje

	//Invocamos el write con los parametros, el descriptor de socket del cual queremos leer, la pocision del buffer donde queremos dejar los
	//caracteres leidos y la cantidad que caracteres que resan leer

	CantDatosEscritosInvocacion = write(IDSocket, DatosAEscribir, largoBuffer);


	if (CantDatosEscritosInvocacion < 0)
	{
		return -1;
	}
	else
	{
		return CantDatosEscritosInvocacion;
	}

}
