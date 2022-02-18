#include "../../buffer.h"

#include <assert.h>
#include <iostream>
using namespace std;

int main()
{
    cout << "***  Test1  ***" << endl << endl;
    buffer * b = createBuffer(30);

    const char * msj1 = "este ";			 	// 5 bytes
    const char * msj2  = "es un mensaje ";	// 14 bytes
    const char * msj3  = "de prueba";			// 9 bytes
    cout << "Texto: " << msj1 << msj2 << msj3 << endl;

    int escrito = 0;
    escrito = writeBuffer(msj1,5,b);
    escrito = writeBuffer(msj2,14,b);
    escrito = writeBuffer(msj3,9,b);    
    assert(escrito != -1);
    printBuffer(b);	// 28 bytes

    cout << "***  Test2  ***" << endl << endl;
    int cantLeido = 0;
    char * leido = new char [128];
    cantLeido = readBuffer(leido,8,b);
    leido[cantLeido] = '\0';
    assert(cantLeido != 0);
    cout << "se leyeron " << cantLeido << " bytes" << endl;
    cout << "LEIDO: " << leido  << endl;
    printBuffer(b);

    escrito = writeBuffer("abcdefghijklmnopqrstuvxyz",25,b);
    assert(escrito != 0);
    if (escrito == 0)
    	cout << "***  ERROR: intente escribir en el buffer lleno" << endl;
    else{
    	cout << "se escribieron " << escrito << " bytes" << endl;
    	printBuffer(b);
    }


	cout << "***  Test3  ***" << endl << endl;
    cantLeido = readBuffer(leido,20,b);
    leido[cantLeido] = '\0';
    if (cantLeido == 0)
    	cout << "***  ERROR: no se leyo nada" << endl;
    else {
    	cout << "se leyeron " << cantLeido << " bytes" << endl;
    	cout << "LEIDO: " << leido  << endl;
    }
    printBuffer(b);


	cout << "***  Test4  ***" << endl << endl;
	escrito = writeBuffer("xyzxyzxyzxyzxyzxyzxyzxyzxyzxyz",30,b);
    if (escrito == 0)
    	cout << "***  ERROR: intente escribir en el buffer lleno" << endl;
    else{
    	cout << "se escribieron " << escrito << " bytes" << endl;
    	printBuffer(b);
    }


	cout << "***  Test5  ***" << endl << endl;
	escrito = writeBuffer("aaaaaaaaaa",10,b);
    if (escrito != 0)
    	cout << "***  ERROR: se escribi en un buffer lleno" << endl;
    else{
    	cout << "se escribieron " << escrito << " bytes" << endl;
    	printBuffer(b);
    }


	cout << "***  Test6  ***" << endl << endl;
    cantLeido = readBuffer(leido,2,b);
    leido[cantLeido] = '\0';
    if (cantLeido == 0)
    	cout << "***  ERROR: no se leyo nada" << endl;
    else {
    	cout << "se leyeron " << cantLeido << " bytes" << endl;
    	cout << "LEIDO: " << leido  << endl;
    }
    printBuffer(b);


    cout << "***  Test7  ***" << endl << endl;
    cantLeido = readBuffer(leido,8,b);
    leido[cantLeido] = '\0';
    if (cantLeido == 0)
    	cout << "***  ERROR: no se leyo nada" << endl;
    else {
    	cout << "se leyeron " << cantLeido << " bytes" << endl;
    	cout << "LEIDO: " << leido  << endl;
    }
    printBuffer(b);


    delete [] leido;
	return 0;
}
