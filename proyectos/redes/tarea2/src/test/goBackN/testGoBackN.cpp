#include "../../goBackN.h"
#include "../../stringLib.h"

int main(int argc, char *argv[]) {

	setLogLevel(LOGGER_TRACE);

	cout<<"inicio"<<"\n";
	cout.flush();

	inicioEmisor();
	cout<<"inicioEmisor"<<"\n";

	char * datos = NULL;
	int resEnviar = 0;

	for (int var = 0; var < 32; ++var) {

		string strAux = intToStr(var);

		datos = new char[strAux.size()+1];
		datos[strAux.size()] = '\0';

		memcpy(datos,strAux.c_str(), strAux.size());

		cout<<"[test] rdtEnviar "<<datos<<" "<<resEnviar<<"**********************************************************\n";

		resEnviar = rdtEnviar(datos);



		usleep(500000); // 1000000 duerme un segundo , testeado

	}


	return 0;
}
