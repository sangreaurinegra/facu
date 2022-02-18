#include "pct.h"
#include "logger.h"
#include "stringLib.h"
#include <unistd.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/ip.h>
#include <arpa/inet.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>


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

/*ENCABEZADO DE FUNCIONES*/
int recibirPaqueteControl (unsigned char flag);
int enviarPaqueteControl (unsigned char flag);
int enviarPaqueteDatos(char *buf, size_t len);
int recibirPaqueteDatos(char *buf, size_t len);
int IP2asc(u_int32_t IP,char * result);


int crearPCT(struct in_addr localIPaddr) {
	log("crearPCT",LOGGER_TRACE);
	if (estadoActual != INIT) {
		log("Error crearPCT: El estado es distinto a INIT",LOGGER_INFO);
		return -1;
	}

	globalPctSocket = socket(AF_INET, SOCK_RAW, IPPROTO_RAW);

	if (globalPctSocket < 0) {
		log("Error crearPCT: creando Socket",LOGGER_INFO);
		return -1;
	}
	log("crearPCT  socket creado numero: "+ intToStr(globalPctSocket),LOGGER_TRACE);

	estadoActual = DESCONECTADO;
	globalIPOrigen = localIPaddr;

	char  str[20];
	inet_ntop(AF_INET, &(globalIPOrigen.s_addr), str, INET_ADDRSTRLEN);

	log("crearPCT  globalIPOrigen: "+ string(str),LOGGER_TRACE);

	return globalPctSocket;
}

int aceptarPCT(unsigned char localPCTport){

	globalPuertoOrigen = localPCTport;

	if (estadoActual != DESCONECTADO) {
		log("Error aceptarPCT: El estado es distinto a DESCONECTADO",LOGGER_INFO);
		return -1;
	}

	estadoActual = ESPERANDO_CONEXION;

	while (estadoActual == ESPERANDO_CONEXION) {
		log("aceptarPCT esperando SYN",LOGGER_DEBUG);

		//Espero un SYN
    	if (recibirPaqueteControl( SYN_FLAG ) == -1) {
    		estadoActual = DESCONECTADO;
    		return -1;
    	}
		log("aceptarPCT enviando SYN ACK",LOGGER_DEBUG);

    	//Ya recibi un SYN, envio un SYN ACK
    	if (enviarPaqueteControl( SYN_FLAG + ACK_FLAG) == -1) {
    		estadoActual == DESCONECTADO;
    		return -1;
    	}


    	estadoActual = RECIBI_SYN;
		log("aceptarPCT recibiendo ACK",LOGGER_DEBUG);

    	if (recibirPaqueteControl( ACK_FLAG ) == -1) {
    		estadoActual = DESCONECTADO;
    		return -1;
    	}
		log("aceptarPCT conexion lograda",LOGGER_DEBUG);

    	//numeroSecuencia = 0;
    	//numeroACK = 0;
    	estadoActual = LOGRE_CONEXION;

	}

	return 1;
}

int conectarPCT(unsigned char localPCTport,unsigned char peerPCTPport, struct in_addr peerIPaddr){
	log("conectarPCT primera linea ",LOGGER_TRACE);

	if (estadoActual != DESCONECTADO) {
		log("Error conectarPCT: El estado es distinto a DESCONECTADO",LOGGER_INFO);
		return -1;
	}

    globalDireccionSocket.sin_addr = peerIPaddr;
    //globalDireccionSocket.sin_port = htons(peerPCTPport); al pedo?
    globalDireccionSocket.sin_family = AF_INET;

    globalPuertoOrigen = localPCTport;
    globalPuertoDestino = peerPCTPport;

    while (estadoActual == DESCONECTADO) {
    	log("conectarPTC envio SYN ",LOGGER_TRACE);

    	if( enviarPaqueteControl( SYN_FLAG ) == -1) {
    		estadoActual = DESCONECTADO;
    		return -1;
    	}

    	estadoActual = ESPERANDO_SYN_ACK;
    	log("conectarPTC espero SYN_ACK ",LOGGER_TRACE);

    	if (recibirPaqueteControl(SYN_FLAG + ACK_FLAG) == -1) {
    		estadoActual = DESCONECTADO;
    		return -1;
    	}

    	log("conectarPTC envio ACK ",LOGGER_TRACE);

    	//Ya recibi un SYN ACK, envio el ACK
    	if (enviarPaqueteControl( ACK_FLAG) == -1) {
    		estadoActual == DESCONECTADO;
    		return -1;
    	}

    	//numeroSecuencia = 0;
    	//numeroACK = 1;

    	//Aca hago un nuevo hilo para enviar datos?
    	log("conectarPTC CONECTADO ",LOGGER_TRACE);

    	estadoActual = CONECTADO;


    }
	return 1;
}


int escribirPCT(const void *buf, size_t len){
	enviarPaqueteDatos((char*)buf, len);
}

int leerPCT(void *buf, size_t len){

	recibirPaqueteDatos( (char*)buf,len);

}

int cerrarPCT(){

	return 1;
}


/**
 *
 *  ACA VIENEN TODAS LAS FUNCIONES AUXILIARES
 *
 * */


int enviarPaqueteDatos(char *buf, size_t len){
	char datagrama [sizeof(struct iphdr) + sizeof(struct pct_header) + len];
	memset(datagrama, 0, sizeof(struct iphdr) + sizeof(struct pct_header) + len);


    /*Armo IP*/
    /* apunto  iphdr al principio del datagrama*/
    struct iphdr *ip = (struct iphdr *)datagrama;

	ip->ihl = 5;
	ip->version = 4;
	ip->tos = 0;
	ip->tot_len = sizeof datagrama;
	ip->frag_off = 0;		/* no fragment */
	ip->ttl = 64;			/* default value */
	ip->protocol = IPPROTO_RAW;	/* protocol at L4 es 255*/
	ip->check = 0;			/* not needed in iphdr */
	ip->saddr = globalIPOrigen.s_addr;
	ip->daddr = globalDireccionSocket.sin_addr.s_addr;

	//Armo Header pct
    struct pct_header * pct = (struct pct_header*) (datagrama + sizeof(struct iphdr));
    pct->srcPort = globalPuertoOrigen;
    pct->destPort = globalPuertoDestino;
    pct->flags_pct = DATA_FLAG;
    //pct->nro_SEC_pct = num;
    //pct->nro_ACK_pct = num_ack;

    //Agrego los datos al datagrama , se puede hacer mejor esta funcion usando memCpy ???
    char *datos = (char*)buf;

	int i = sizeof(struct pct_header)+sizeof(struct iphdr);
	int j = 0;
	while (i < (sizeof(struct iphdr) +sizeof(struct pct_header))+len) {
		datagrama[i] = datos[j];
		i++;
		j++;
	}

	//Para imprmir el header IP
	log("Envio este datagrama:",LOGGER_DEBUG);
	char charIP[20];
	IP2asc(ntohl(ip->saddr),&charIP[0]);
	log("	IP origen: "+string(charIP),LOGGER_DEBUG);
	IP2asc(ntohl(ip->daddr),&charIP[0]);
	log("	IP destino: "+string(charIP),LOGGER_DEBUG);
	log("	protocolo: "+intToStr(ip->protocol),LOGGER_DEBUG);
	//Para imprimir el header PCT
	log("	srcPort: "+intToStr(pct->srcPort),LOGGER_DEBUG);
	log("	flags_pct: "+intToStr(pct->flags_pct),LOGGER_DEBUG);
	log("	dest_pct: "+intToStr(pct->destPort),LOGGER_DEBUG);

	//Para imprimir el data
	//log("	data: "+string(datos),LOGGER_DEBUG);
	log("	data buf: "+string((char*)buf),LOGGER_DEBUG);

	char *cosos = (char*)(datagrama + sizeof(struct iphdr)+sizeof(struct pct_header));
	log("	cosos: "+string(cosos),LOGGER_DEBUG);



    int tamanioDatagrama = sizeof(datagrama);

    int res = -1;
    while (res < 0){

		res = sendto(globalPctSocket, (char *)datagrama, tamanioDatagrama, 0, (struct sockaddr*) & globalDireccionSocket, sizeof(globalDireccionSocket));
	    cout<< "res: sendto: "<<res<< endl;


    }


    if (res != tamanioDatagrama) return -1;

    return 1;
}


int recibirPaqueteDatos(char * buf, size_t len) {

	   struct timeval timeout;
		fd_set fdSet;

	    timeout.tv_sec = TIMEOUT * 2;
	    timeout.tv_usec = 0;


		FD_ZERO(&fdSet); //FD_ZERO nos vacía el puntero, de forma que estamos indicando que no nos interesa ningún descriptor de fichero.
		FD_SET(globalPctSocket, &fdSet);//FD_SET mete el descriptor que le pasamos en int al puntero fd_set.

		log("Select ...",LOGGER_DEBUG);

		int selectResult = -1;
		int recv_result = -1;
		char * datagrama = NULL;

		//Aca no estoy seguro ???
		while ( recv_result < 0) {
			log("select ...",LOGGER_DEBUG);

			selectResult = select(FD_SETSIZE, &fdSet, NULL, NULL, &timeout);

			if (selectResult == 0) {
				log("ERROR recibirPaqueteControl: select Time out",LOGGER_INFO);
				//return -1;
			}
			if (selectResult == -1) {
				log("ERROR recibirPaqueteControl: Error al hacer select",LOGGER_INFO);
				return -1;
			}

			socklen_t socketSize = sizeof(globalDireccionSocket);

			datagrama = new char [sizeof(struct iphdr)+sizeof(struct pct_header) + len];
			recv_result = recvfrom(globalPctSocket, datagrama, MAX_PCT_DATA_SIZE, MSG_WAITALL,(struct sockaddr *)&globalDireccionSocket, (socklen_t *)&socketSize) ;
			if (recv_result < 0 ) {
				log("ERROR recibirPaqueteControl: Error al hacer recvfrom",LOGGER_INFO);
				return -1;
			}

			if (datagrama != NULL) {
					log("Datagrama recibido",LOGGER_DEBUG);

					//Para imprmir el header IP
					char charIP[20];
					struct iphdr *ip = (struct iphdr *)datagrama;

					IP2asc(ntohl(ip->saddr),&charIP[0]);
					log("	IP origen: "+string(charIP),LOGGER_DEBUG);

					IP2asc(ntohl(ip->daddr),&charIP[0]);
					log("	IP destino: "+string(charIP),LOGGER_DEBUG);

					log("	protocolo: "+intToStr(ip->protocol),LOGGER_DEBUG);

					//Para imprimir el header PCT
					struct pct_header *pct = (struct pct_header *)(datagrama + sizeof(struct iphdr));

					log("	srcPort: "+intToStr(pct->srcPort),LOGGER_DEBUG);
					log("	flags_pct: "+intToStr(pct->flags_pct),LOGGER_DEBUG);
					log("	dest_pct: "+intToStr(pct->destPort),LOGGER_DEBUG);

					//PAra imprmir los datos
					char *datos = (char*)(datagrama + sizeof(struct iphdr)+sizeof(struct pct_header));
					log("	datos: "+string(datos),LOGGER_DEBUG);


					//Viene a mi, lo atiendo. MAnipulo el socket para responder
					if (ip->daddr == globalIPOrigen.s_addr && pct->destPort == globalPuertoOrigen && DATA_FLAG == pct->flags_pct){
						log("Este paquete es para mi",LOGGER_DEBUG);
						log("FLAGS iguales", LOGGER_DEBUG);

						globalDireccionSocket.sin_addr.s_addr = ip->saddr;
						//globalDireccionSocket.sin_port = pct->srcPort; al pedo?
						globalDireccionSocket.sin_family = AF_INET;
						globalPuertoDestino = pct->srcPort;
						//numeroACK =  (pct->nro_SEC_
						//numeroSecuencia = pct->nro_ACK;

						//asigno los datos?
						//buf = datos;
						memcpy (buf,datos,len+1);

						log("	buf: "+string((char*)buf),LOGGER_DEBUG);

						return 1;

					} else {
						recv_result = -1;
						log("Este paquete no era para mi, vuelvo a esperar por otro",LOGGER_DEBUG);
					}

		}





		}



		//return -1;


}





int enviarPaqueteControl (unsigned char flag) {

	char datagrama [sizeof(struct iphdr) + sizeof(struct pct_header)];
	memset(datagrama, 0, sizeof(struct iphdr) + sizeof(struct pct_header));


    /*Armo IP*/
    /* apunto  iphdr al principio del datagrama*/
    struct iphdr *ip = (struct iphdr *)datagrama;

	ip->ihl = 5;
	ip->version = 4;
	ip->tos = 0;
	ip->tot_len = sizeof datagrama;
	ip->frag_off = 0;		/* no fragment */
	ip->ttl = 64;			/* default value */
	ip->protocol = IPPROTO_RAW;	/* protocol at L4 es 255*/
	ip->check = 0;			/* not needed in iphdr */
	ip->saddr = globalIPOrigen.s_addr;
	ip->daddr = globalDireccionSocket.sin_addr.s_addr;

	//Armo Header pct
    struct pct_header * pct = (struct pct_header*) (datagrama + sizeof(struct iphdr));
    pct->srcPort = globalPuertoOrigen;
    pct->destPort = globalPuertoDestino;
    pct->flags_pct = flag;
    //pct->nro_SEC_pct = num;
    //pct->nro_ACK_pct = num_ack;

	//Para imprmir el header IP
	log("Envio este datagrama:",LOGGER_DEBUG);
	char charIP[20];
	IP2asc(ntohl(ip->saddr),&charIP[0]);
	log("	IP origen: "+string(charIP),LOGGER_DEBUG);
	IP2asc(ntohl(ip->daddr),&charIP[0]);
	log("	IP destino: "+string(charIP),LOGGER_DEBUG);
	log("	protocolo: "+intToStr(ip->protocol),LOGGER_DEBUG);
	//Para imprimir el header PCT
	log("	srcPort: "+intToStr(pct->srcPort),LOGGER_DEBUG);
	log("	flags_pct: "+intToStr(pct->flags_pct),LOGGER_DEBUG);
	log("	dest_pct: "+intToStr(pct->destPort),LOGGER_DEBUG);

    int tamanioDatagrama = sizeof(datagrama);

    int res = -1;
    //while (res < 0){

		res = sendto(globalPctSocket, (char *)datagrama, tamanioDatagrama, 0, (struct sockaddr*) & globalDireccionSocket, sizeof(globalDireccionSocket));


    //}

    cout<< "res: sendto: "<<res<< endl;

    if (res != tamanioDatagrama)     return -1;

    return 1;
}

int recibirPaqueteControl (unsigned char flag) {

    struct timeval timeout;
	fd_set fdSet;

    timeout.tv_sec = TIMEOUT * 2;
    timeout.tv_usec = 0;


	FD_ZERO(&fdSet); //FD_ZERO nos vacía el puntero, de forma que estamos indicando que no nos interesa ningún descriptor de fichero.
	FD_SET(globalPctSocket, &fdSet);//FD_SET mete el descriptor que le pasamos en int al puntero fd_set.

	log("Select ...",LOGGER_DEBUG);

	int selectResult = -1;
	int recv_result = -1;
	char * datagrama = NULL;

	//Aca no estoy seguro ???
	while ( recv_result < 0) {
		log("select ...",LOGGER_DEBUG);

		selectResult = select(FD_SETSIZE, &fdSet, NULL, NULL, &timeout);

		if (selectResult == 0) {
			log("ERROR recibirPaqueteControl: select Time out",LOGGER_INFO);
			//return -1;
		}
		if (selectResult == -1) {
			log("ERROR recibirPaqueteControl: Error al hacer select",LOGGER_INFO);
			return -1;
		}

		socklen_t socketSize = sizeof(globalDireccionSocket);

		datagrama = new char [MAX_PCT_DATA_SIZE];
		recv_result = recvfrom(globalPctSocket, datagrama, MAX_PCT_DATA_SIZE, MSG_WAITALL,(struct sockaddr *)&globalDireccionSocket, (socklen_t *)&socketSize) ;
		if (recv_result < 0 ) {
			log("ERROR recibirPaqueteControl: Error al hacer recvfrom",LOGGER_INFO);
			return -1;
		}

		if (datagrama != NULL) {
				log("Datagrama recibido",LOGGER_DEBUG);

				//Para imprmir el header IP
				char charIP[20];
				struct iphdr *ip = (struct iphdr *)datagrama;

				IP2asc(ntohl(ip->saddr),&charIP[0]);
				log("	IP origen: "+string(charIP),LOGGER_DEBUG);

				IP2asc(ntohl(ip->daddr),&charIP[0]);
				log("	IP destino: "+string(charIP),LOGGER_DEBUG);

				log("	protocolo: "+intToStr(ip->protocol),LOGGER_DEBUG);

				//Para imprimir el header PCT
				struct pct_header *pct = (struct pct_header *)(datagrama + sizeof(struct iphdr));

				log("	srcPort: "+intToStr(pct->srcPort),LOGGER_DEBUG);
				log("	flags_pct: "+intToStr(pct->flags_pct),LOGGER_DEBUG);
				log("	dest_pct: "+intToStr(pct->destPort),LOGGER_DEBUG);

				//Viene a mi, lo atiendo. MAnipulo el socket para responder
				if (ip->daddr == globalIPOrigen.s_addr && pct->destPort == globalPuertoOrigen && flag == pct->flags_pct){
					log("Este paquete es para mi",LOGGER_DEBUG);
					log("FLAGS iguales", LOGGER_DEBUG);

					globalDireccionSocket.sin_addr.s_addr = ip->saddr;
					//globalDireccionSocket.sin_port = pct->srcPort; al pedo?
					globalDireccionSocket.sin_family = AF_INET;
					globalPuertoDestino = pct->srcPort;
					//numeroACK =  (pct->nro_SEC_
					//numeroSecuencia = pct->nro_ACK;
					return 1;

				} else {
					recv_result = -1;
					log("Este paquete no era para mi, vuelvo a esperar por otro",LOGGER_DEBUG);
				}

	}





	}



	//return -1;
}



int IP2asc(u_int32_t IP,char * result){
	int aux;
	aux = sprintf(result, "%u.%u.%u.%u", (IP / 16777216),((IP / 65536) % 256),((IP / 256) % 256),(IP % 256));
	return aux;
}
