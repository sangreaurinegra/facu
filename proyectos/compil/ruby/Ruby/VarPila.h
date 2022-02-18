#ifndef VARPILA_H
#define VARPILA_H
#include <iostream>
#include "string.h"
#include "TipoValor.h"



using namespace std;

/*Usamos valores entero=0, real=1, string=2, booleano=3, arreglo=4 */

struct valorVar{
    int entero;
    double real;
    string str;
    bool booleano;
    TArreglo* arreglo;
    TObjeto* objeto;
};

class VarPila {
     private:
        string id;
        int tipo;
        valorVar valor;

     public:
        VarPila();
        virtual ~VarPila();

        void setId(string s){
            id = s;
        }
        void setTipo(int t){
            tipo = t;
        }
        void setValor(int v){
            valor.entero = v;
        }
        void setValor(float v){
            valor.real = v;
        }
        void setValor(string v){
            valor.str = v;
            //cout << "Variable de valor: "<< valor.str ;
        }
        void setValor(bool v){
            valor.booleano = v;
        }
        void setValor(TArreglo* v){
            valor.arreglo = v;
        }
        void setValor(TObjeto* v){
            valor.objeto = v;
        }
        string getId(){
            return id;
        }
        int getTipoVar(){
            return tipo;
        }
        valorVar getValor(){
            return valor;
        }
};

#endif // VARPILA_H
