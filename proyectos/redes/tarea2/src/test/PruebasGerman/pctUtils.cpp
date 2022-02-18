#include "pctUtils.h"

using namespace std;

/**
 * Crea un paquete de datos con numero de secuencia,numero de ack y datos. Devuelve el paquete creado.
 */

int crearPaquete(int numSec, int numACK, unsigned char tipoPaquete, char* datos, size_t lenDatos , char* datagrama) {
	log("pcutils.cpp --- llamo a crearPaquete",LOGGER_DEBUG);



	//Armo IP
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
	ip->daddr = globalIPDestino.s_addr;

	char charIP[20];

	IP2asc(ntohl(ip->saddr),&charIP[0]);
	log("pcutils.cpp - crearPaquete --- IP origen: "+string(charIP),LOGGER_DEBUG);

	IP2asc(ntohl(ip->daddr),&charIP[0]);
	log("pcutils.cpp - crearPaquete ---	IP destino: "+string(charIP),LOGGER_DEBUG);



	//Armo Header pct
    struct pct_header * pct = (struct pct_header*) (datagrama + sizeof(struct iphdr));
    pct->srcPort = globalPuertoOrigen;
    pct->destPort = globalPuertoDestino;
    pct->flags_pct = tipoPaquete;
    pct->nro_SEC_pct = numSec;
    pct->nro_ACK_pct = numACK;

	//memcpy (copiaDatagrama,datagrama,sizeof(struct iphdr) + sizeof(struct pct_header) + len);
    //char *datos = (char*)buf;

	int i = sizeof(struct pct_header)+sizeof(struct iphdr);
	log("pcutils.cpp - crearPaquete --- tamano de los cabezales juntos = "+intToStr(i),LOGGER_DEBUG);

	log("pcutils.cpp - crearPaquete --- Agrego los datos al paquete que  ya tiene los cabezales si es un paquete de datos",LOGGER_DEBUG);
	int j = 0;
	while (i < (sizeof(struct iphdr) +sizeof(struct pct_header))+lenDatos) {
		datagrama[i] = datos[j];
		i++;
		j++;
	}

	if (tipoPaquete == DATA_FLAG) {
		log("pcutils.cpp - crearPaquete ---EL PAQUETE CREADO ES DE DATOS "+intToStr(i),LOGGER_DEBUG);
		log("pcutils.cpp - crearPaquete --- strlen(datos) "+intToStr(strlen(datos)),LOGGER_DEBUG);
		log("pcutils.cpp - crearPaquete --- strlen(datagrama) "+intToStr(strlen(datagrama)),LOGGER_DEBUG);
	}


	//datagrama[i] = '\0';

	log("pcutils.cpp - crearPaquete --- IMPRIMO EL PAQUETE CREADO: ",LOGGER_DEBUG);
	imprimirPaquete(datagrama,LOGGER_DEBUG);
	log("pcutils.cpp - crearPaquete --- termina crearPaquete",LOGGER_DEBUG);

	return 1;


}

int crearPaqueteACK(int numACK, char* datagrama){
	log("pcutils.cpp ---- crearPaqueteACK",LOGGER_DEBUG);
	char buf[10];
	sprintf(buf,"ACK-%d", numACK);
	return crearPaquete(0, numACK, ACK_FLAG, buf, 10 ,datagrama);
}

int crearPaqueteKAL(char* datagrama){
	log("pcutils.cpp --- crearPaqueteKAL",LOGGER_DEBUG);
	return crearPaquete(0, 0, 0, NULL, 0 ,datagrama);
}

int crearPaqueteDatos(int numSec,char* datos, size_t lenDatos , char* datagrama){
	log("pcutils.cpp --- crearPaqueteDatos",LOGGER_DEBUG);
	return crearPaquete(numSec, 0, DATA_FLAG, datos, lenDatos ,datagrama);
}

/**
 * LE pasas un paquete y te imprime la informacion como IP, PTC y DATOS.
 */
void imprimirPaquete(char* paquete, int miLoggerLevel) {


	log("pcutils.cpp --- ENTRO A imprimirPaquete",miLoggerLevel);

	//imprmir el header IP
	char charIP[20];
	struct iphdr *ip = (struct iphdr *)paquete;

	IP2asc(ntohl(ip->saddr),&charIP[0]);
	log("pcutils.cpp - imprimirPaquete > IP origen: "+string(charIP),miLoggerLevel);

	IP2asc(ntohl(ip->daddr),&charIP[0]);
	log("pcutils.cpp - imprimirPaquete > IP destino: "+string(charIP),miLoggerLevel);

	log("pcutils.cpp - imprimirPaquete > protocolo: "+intToStr(ip->protocol),miLoggerLevel);

	//Para imprimir el header PCT
	struct pct_header *pct = (struct pct_header *)(paquete + sizeof(struct iphdr));

	log("pcutils.cpp - imprimirPaquete > srcPort: "+intToStr(pct->srcPort),miLoggerLevel);
	log("pcutils.cpp - imprimirPaquete > dest_pct: "+intToStr(pct->destPort),miLoggerLevel);

	string strFlag="nada";

	switch (pct->flags_pct) {
		case DATA_FLAG:
			strFlag="DATA_FLAG";
			break;
		case SYN_FLAG:
			strFlag="SYN_FLAG";
		break;
		case ACK_FLAG:
			strFlag="ACK_FLAG";
		break;
		case FIN_FLAG:
			strFlag="FIN_FLAG";
		break;
		default:
			strFlag="el switch no encaro";
			break;
	}

	log("pcutils.cpp - imprimirPaquete > flags_pct: "+strFlag,miLoggerLevel);

	log("pcutils.cpp - imprimirPaquete > nro_ACK_pct: "+intToStr(pct->nro_ACK_pct),miLoggerLevel);
	log("pcutils.cpp - imprimirPaquete > nro_SEC_pct: "+intToStr(pct->nro_SEC_pct),miLoggerLevel);

	//PAra imprmir los datos
	char *datos = (char*)(paquete + sizeof(struct iphdr)+sizeof(struct pct_header));
	//char mostrarDatos[20];
	//memcpy(mostrarDatos, datos, 20);
	log("pcutils.cpp - imprimirPaquete > datos: "+string(datos),miLoggerLevel);


}

int enviarPaqueteDatos(char* datagrama, size_t len){
	log("pcutils.cpp --- ENTRO A enviarPaqueteDatos",LOGGER_DEBUG);
	return enviarPaquete(datagrama, len);
}

/**
 * Envia un paquete a destino
 */
int enviarPaquete(char* datagrama, size_t len){

	log("pcutils.cpp --- ENTRO A enviarPaquete",LOGGER_TRACE);
	//imprimirPaquete(datagrama);

	//Hago una copia porque no quiero enviar un puntero, sino da error el sendTo
	char copiaDatagrama[sizeof(struct iphdr) + sizeof(struct pct_header) + len];
	memcpy (copiaDatagrama,datagrama,sizeof(struct iphdr) + sizeof(struct pct_header) + len);

    int tamanioDatagrama = sizeof(copiaDatagrama);

    //Direccion de socket a enviar
    struct sockaddr_in direccionSocketDestino;
    direccionSocketDestino.sin_addr = globalIPDestino;
    direccionSocketDestino.sin_family = AF_INET;

    imprimirPaquete(datagrama, LOGGER_TRACE);

    //imprimirPaquete(copiaDatagrama);
    int res = -1;
    while (res < 0){
    	log("pcutils.cpp - enviarPaquete: Invoco al sendto con tamanioDatagrama: "+intToStr(tamanioDatagrama),LOGGER_DEBUG);
		res = sendto(globalPctSocket, copiaDatagrama, tamanioDatagrama, 0, (struct sockaddr*) & direccionSocketDestino, sizeof(direccionSocketDestino));
		log("pcutils.cpp - enviarPaquete: cantidad enviados: "+intToStr(res),LOGGER_DEBUG);
	    //cout<< " sendto: "<<res<< endl;
    }

    log("pcutils.cpp - enviarPaquete: cantidad enviados: "+intToStr(res),LOGGER_TRACE);

    imprimirPaquete(copiaDatagrama,LOGGER_TRACE);

    if (res != tamanioDatagrama) return -1;

    return 1;
}


//Recibe cualquier tipo de paquete, retorna el tipo en flag. Si recibe ACK devuelve numero de ACK
//Si es un paquete de datos lo devuelve en datosPAquete y el tamanio en lenDatos. Retorna el numero de secuencia.
int recibirPaquete(char * datosPaquetes, size_t & lenDatos, unsigned char & flag) {
	log("pcutils.cpp --- recibirPaquete",LOGGER_DEBUG);

 		    struct timeval timeout;
			fd_set fdSet;

		    timeout.tv_sec = TIMEOUT * 2;
		    timeout.tv_usec = 0;

		    lenDatos = -1;

			FD_ZERO(&fdSet); //FD_ZERO nos vacía el puntero, de forma que estamos indicando que no nos interesa ningún descriptor de fichero.
			FD_SET(globalPctSocket, &fdSet);//FD_SET mete el descriptor que le pasamos en int al puntero fd_set.

			int selectResult = -1;
			int recv_result = -1;
			char * datagrama = NULL;

			//Espera por paquetes que sean para mi
			while ( recv_result < 0) {

				selectResult = select(FD_SETSIZE, &fdSet, NULL, NULL, &timeout);

				if (selectResult == 0) {
					log("pcutils.cpp - recibirPaquete ERROR recibirPaqueteControl: select Time out",LOGGER_INFO);
					//return -1;
				}
				if (selectResult == -1) {
					log("pcutils.cpp - recibirPaquete ERROR recibirPaqueteControl: Error al hacer select",LOGGER_INFO);
					return -1;
				}

			    struct sockaddr_in direccionSocketOrigen;
			    direccionSocketOrigen.sin_addr = globalIPOrigen;
			    direccionSocketOrigen.sin_family = AF_INET;

				socklen_t socketSize = sizeof(direccionSocketOrigen);

				int tamanioDatagrama = sizeof(struct iphdr)+sizeof(struct pct_header) + MAX_PCT_DATA_SIZE;

				datagrama = new char [tamanioDatagrama];
				recv_result = recvfrom(globalPctSocket, datagrama, tamanioDatagrama, MSG_WAITALL,(struct sockaddr *)&direccionSocketOrigen, (socklen_t *)&socketSize) ;

				if (recv_result < 0 ) {
					log("pcutils.cpp - recibirPaquete ERROR recibirPaqueteControl: Error al hacer recvfrom",LOGGER_INFO);
					return -1;
				}

				if (datagrama != NULL) {
						log("pcutils.cpp - recibirPaquete > Datagrama recibido",LOGGER_DEBUG);

						//Function auxiliar para mostrar los datos de el paquete. (se puede eliminar)
						imprimirPaquete(datagrama,LOGGER_TRACE);

						//Desarmo el paquete en ip, pct y datos
						struct iphdr *ip = (struct iphdr *)datagrama;
						struct pct_header *pct = (struct pct_header *)(datagrama + sizeof(struct iphdr));
						char *datos = (char*)(datagrama + sizeof(struct iphdr)+sizeof(struct pct_header));

						//Viene a mi, lo atiendo. MAnipulo el socket para responder
						if (ip->daddr == globalIPOrigen.s_addr && pct->destPort == globalPuertoOrigen){
							log("pcutils.cpp - recibirPaquete - Este paquete es para mi",LOGGER_DEBUG);

							flag = pct->flags_pct;

							if (flag == DATA_FLAG) { // Es un paquete de datos devuelvo los datos, cambio el tamanio de los datos
								lenDatos = recv_result - (sizeof(struct iphdr)+sizeof(struct pct_header));
								log("pcutils.cpp - recibirPaquete > lenDatos______: "+intToStr(lenDatos),LOGGER_DEBUG);

								memcpy (datosPaquetes,datos,lenDatos); // el +1 es al pedo???

								log("pcutils.cpp - recibirPaquete > datosPaquetes que devuelvo: "+string((char*)datosPaquetes),LOGGER_DEBUG);
								return pct->nro_SEC_pct;
							}
							else if (flag == ACK_FLAG){
								return pct->nro_ACK_pct;
							}
							else if (flag == 0) { // Es un keep alive
								return 1;
							}
							//Otras flags validas
							else if (flag == FIN_FLAG ||flag == SYN_FLAG || flag == (SYN_FLAG + ACK_FLAG) || flag ==(FIN_FLAG+ ACK_FLAG)) {
								return 1;
							}

							return -1; //La flag tiene cualquier cosa. ERROR

						} else {
							recv_result = -1;
							log("pcutils.cpp - recibirPaquete > Este paquete no era para mi, vuelvo a esperar por otro",LOGGER_DEBUG);
						}

				}

			}

}

int enviarPaqueteControlDirecto (unsigned char flag) {

	log ("pcutils.cpp - ENTRE A enviarPaqueteControlDirecto",LOGGER_INFO);

	char datagrama [sizeof(struct iphdr) + sizeof(struct pct_header)];
	memset(datagrama, 0, sizeof(struct iphdr) + sizeof(struct pct_header));

	crearPaquete(0, 0, flag, NULL, 0 ,datagrama);

	int res = enviarPaquete(datagrama,0);
	log ("pcutils.cpp - enviarPaqueteControlDirecto > Retorno de enviarPaquete: "+intToStr(res),LOGGER_INFO);

	return res;

}

int recibirPaqueteControl (unsigned char flag) {

    struct timeval timeout;
	fd_set fdSet;

    timeout.tv_sec = TIMEOUT * 2;
    timeout.tv_usec = 0;


	FD_ZERO(&fdSet); //FD_ZERO nos vacía el puntero, de forma que estamos indicando que no nos interesa ningún descriptor de fichero.
	FD_SET(globalPctSocket, &fdSet);//FD_SET mete el descriptor que le pasamos en int al puntero fd_set.


	int selectResult = -1;
	int recv_result = -1;
	char * datagrama = NULL;

	while ( recv_result < 0) {
		log("pcutils.cpp - recibirPaqueteControl > Select ...",LOGGER_DEBUG);

		selectResult = select(FD_SETSIZE, &fdSet, NULL, NULL, &timeout);

		if (selectResult == 0) {
			log("pcutils.cpp - ERROR recibirPaqueteControl: select Time out",LOGGER_INFO);
			return -1;
		}
		if (selectResult == -1) {
			log("pcutils.cpp - ERROR recibirPaqueteControl: Error al hacer select",LOGGER_INFO);
			return -1;
		}

	    struct sockaddr_in direccionSocketOrigen;
	    direccionSocketOrigen.sin_addr = globalIPOrigen;
	    direccionSocketOrigen.sin_family = AF_INET;

		socklen_t socketSize = sizeof(direccionSocketOrigen);

		int tamanioDatagrama = sizeof(struct iphdr) + sizeof(struct pct_header);
		datagrama = new char [tamanioDatagrama];
		recv_result = recvfrom(globalPctSocket, datagrama, tamanioDatagrama, MSG_WAITALL,(struct sockaddr *)&direccionSocketOrigen, (socklen_t *)&socketSize) ;
		if (recv_result < 0 ) {
			log("pcutils.cpp - ERROR recibirPaqueteControl: Error al hacer recvfrom",LOGGER_INFO);
			return -1;
		}

		if (datagrama != NULL) {
			log("pcutils.cpp - recibirPaqueteControl > Datagrama recibido",LOGGER_DEBUG);
				imprimirPaquete(datagrama,LOGGER_TRACE);

				struct iphdr *ip = (struct iphdr *)datagrama;
				struct pct_header *pct = (struct pct_header *)(datagrama + sizeof(struct iphdr));

				//Viene a mi, lo atiendo. MAnipulo el socket para responder
				if (ip->daddr == globalIPOrigen.s_addr && pct->destPort == globalPuertoOrigen && flag == pct->flags_pct){
					log("pcutils.cpp - recibirPaqueteControl > Este paquete es para mi",LOGGER_DEBUG);
					log("pcutils.cpp - recibirPaqueteControl > FLAGS iguales", LOGGER_DEBUG);

					//Recibi un paquete, seteo la ip  y port de destino con la direccion ip source y port source del paquete entrante.

					globalIPDestino.s_addr = ip->saddr;
					globalPuertoDestino = pct->srcPort;

					return 1;

				} else {
					recv_result = -1;
					log("pcutils.cpp - recibirPaqueteControl > Este paquete no era para mi, vuelvo a esperar por otro",LOGGER_DEBUG);
				}

		}


	}



	return -1;
}



int IP2asc(u_int32_t IP,char * result){
	int aux;
	aux = sprintf(result, "%u.%u.%u.%u", (IP / 16777216),((IP / 65536) % 256),((IP / 256) % 256),(IP % 256));
	return aux;
}
