#define PUERTO_DATOS 5561
#define PUERTO_DEFECTO_ADMIN 6666
#define IP_SERVIDOR "127.0.0.1"
#define MAXLEN 1024


#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
using namespace std;


/*char* obtenerHost(char* package){

	string auxiliar = string(package);
	int host = auxiliar.find("Host:");
	int ind2 = auxiliar.find("User-Agent:");
	auxiliar = auxiliar.substr(host + 6,ind2 - host -8);
	char* result = strdup(auxiliar.c_str());
	return result;
}*/


int main(int argc, char **argv)
{
	int largototalactual;
	char* resaux;
	char* res;
	char* response;
	int index;
	string auxiliar;

	char errorHeader[] = "HTTP/1.0 502 Bad Gateway\r\n\r\n";
	char errorBody[] = "<html><head><title>502 Bad Gateway</title></head><body><h1>ERROR 502 - Bad Gateway</h1><p><h4>No se pudo obtener la IP del servidor...</h4></p></body></html>";
	char* resLecura = new char[strlen(errorHeader)+strlen(errorBody)+2];
	resLecura[0] = '\0';
	strcat(resLecura,errorHeader);
	strcat(resLecura,errorBody);

	cout << "resLecura: " <<resLecura;


	/*response = new char[50];
	res = new char[50];

	strcpy(response,"lalala lala");
	strcpy(res,"12346");

	cout << "response: " << response << "   largo: " << strlen(response) << "\n";
	cout << "res: " << res << "   largo: " << strlen(res) << "\n";

	largototalactual = strlen(response) + strlen(res);
	cout << "largototalactual: " << largototalactual  << "\n";

	resaux = new char[largototalactual + 1];
	strcat(resaux,res);
	cout << "resaux 1: " << resaux  << "   largo: " << strlen(resaux) << "\n";
	strcat(resaux,response);
	cout << "resaux 2: " << res  << "   largo: " << strlen(resaux) << "\n";

	delete[] res;

	res = new char[largototalactual + 1];
	strcpy(res,resaux);
	cout << "res: " << res  << "   largo: " << strlen(res) << "\n";
	delete[] resaux;*/


}
