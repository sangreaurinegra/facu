#include <cstdlib>
#include <iostream>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <cstring>
#include <crypt.h>

using namespace std;

int letraIni = 97;
int letraFin = 122;

//tiempo ejecucion
//1373775084 -
//1373776410
// = 1326 Seg

int tamArr(char arr[]){
 int tam=0;
 while (arr[tam]!='\0'){
      tam++;      
 }
 return tam;
}


void solucionSerial(char* a, const char* valorCifradoBuscado){
	char *valorCifrado = NULL;
	
	//cout<<"Valor cifrado Buscado"<<valorCifradoBuscado<<"\n";
	//char algo;
	//cin>>algo;
	
     for(int i=letraIni-1 ; i<=letraFin ; i++){
             for(int j=letraIni-1 ; j<=letraFin ; j++){
                     for(int k=letraIni-1 ; k<=letraFin ; k++){
                             for(int m=letraIni-1 ; m<=letraFin ; m++){
                                     for(int n=letraIni-1 ; n<=letraFin ; n++){
                                             for(int o=letraIni ; o<=letraFin ; o++){
                                                     if(i>=letraIni)a[5]=i;
                                                     if(j>=letraIni)a[4]=j;
                                                     if(k>=letraIni)a[3]=k;
                                                     if(m>=letraIni)a[2]=m;
                                                     if(n>=letraIni)a[1]=n;
                                                     a[0]=o;
                                                     valorCifrado = crypt(a, "13");
                                                     //cout<<a<<" - "<<valorCifrado<<"\n";
                                                     if(strncmp(valorCifrado, valorCifradoBuscado ,13) == 0){
														 cout<<"EL Valor es: "<<a<<"\n";
														 return;
													 }
                                             }
                                     }
                             }
                     }
             }
     }     
}

int main(int argc, char *argv[])
{
    int i = 0;
    int j = 0;
    int k = 0;
    cout<<time(NULL)<<"\n";
    cout<<"Procesando .... \n";
    
    int tamArray = 7; 
    char a[tamArray];
    //inicializo Array
    for(int i=0 ; i<tamArray ; i++){
      a[i]='\0';
    }
    string valorCifradoBuscado = "13rfeUmpQl2s6"; // prueva abc "13MqkfKmyTWBw";
 
	//cout<<crypt("abc", "13")<<"\n";
 
    solucionSerial(a,valorCifradoBuscado.c_str());   

    cout<<time(NULL)<<"\n";

    return EXIT_SUCCESS;
}
