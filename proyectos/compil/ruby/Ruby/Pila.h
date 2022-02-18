#ifndef PILA_H
#define PILA_H
#include <iostream>
#include "ListaVariables.h"



using namespace std;

class Pila {
    public:
        std::vector<ListaVariables*> pilaVars;

        Pila();
        virtual ~Pila();
        void abreScope() {
            ListaVariables *lista = new ListaVariables();
            pilaVars.push_back(lista);
        }
        void cierraScope(){
            int topePila = pilaVars.size();
            delete pilaVars[topePila-1];
            pilaVars.pop_back();
        }
        void agregarVarPila(string id, int v){ // buscar antes si existe en toda la pila para remplazarla
            bool existe = false;
            int posPila = pilaVars.size();  //Inicializo posicion en el tope (scope actual)
            int scActual = pilaVars.size();  //Inicializo posicion en el tope (scope actual)
            bool igual;
            int pos;
            while (!existe and posPila > 0) {
                pilaVars.at(posPila-1)->buscarVarScopeActual(id, 0, &igual, &pos);      //0-tipoInt
                if (pos == -1) //No encontre en scope posPila-1, sigo buscando mas abajo
                    posPila --;
                else {
                    existe = true;
                    if (igual) {    //Encontre y es del mismo tipo
                        pilaVars.at(posPila-1)->agregarVariable(id,v); //Solo actualizo valor
                    }
                    else {  //Encontre pero es de distinto tipo
                        pilaVars.at(scActual-1)->agregarVariable(id,v); //Agrego en el scope actual.
                    }
                }
            }
            if (!existe and posPila == 0) {
                pilaVars.at(scActual-1)->agregarVariable(id,v); //Agrego en el scope actual.
            }
        }
        void agregarVarPila(string id, float v){
            bool existe = false;
            int posPila = pilaVars.size();  //Inicializo posicion en el tope (scope actual)
            int scActual = pilaVars.size();  //Inicializo posicion en el tope (scope actual)
            bool igual;
            int pos;
            while (!existe and posPila > 0) {
                pilaVars.at(posPila-1)->buscarVarScopeActual(id, 1, &igual, &pos);      //1-tipoReal
                if (pos == -1) //No encontre en scope posPila-1, sigo buscando mas abajo
                    posPila --;
                else {
                    existe = true;
                    if (igual) {    //Encontre y es del mismo tipo
                        pilaVars.at(posPila-1)->agregarVariable(id,v); //Solo actualizo valor
                    }
                    else {  //Encontre pero es de distinto tipo
                        pilaVars.at(scActual-1)->agregarVariable(id,v); //Agrego en el scope actual.
                    }
                }
            }
            if (!existe and posPila == 0) {
                pilaVars.at(scActual-1)->agregarVariable(id,v); //Agrego en el scope actual.
            }
        }
        void agregarVarPila(string id, string v){
            bool existe = false;
            int posPila = pilaVars.size();  //Inicializo posicion en el tope (scope actual)
            int scActual = pilaVars.size();  //Inicializo posicion en el tope (scope actual)
            bool igual;
            int pos;
            while (!existe and posPila > 0) {
                pilaVars.at(posPila-1)->buscarVarScopeActual(id, 2, &igual, &pos);      //2-tipoString
                if (pos == -1) //No encontre en scope posPila-1, sigo buscando mas abajo
                    posPila --;
                else {
                    existe = true;
                    if (igual) {    //Encontre y es del mismo tipo
                        pilaVars.at(posPila-1)->agregarVariable(id,v); //Solo actualizo valor
                    }
                    else {  //Encontre pero es de distinto tipo
                        pilaVars.at(scActual-1)->agregarVariable(id,v); //Agrego en el scope actual.
                    }
                }
            }
            if (!existe and posPila == 0) {
                pilaVars.at(scActual-1)->agregarVariable(id,v); //Agrego en el scope actual.
                //cout << "agrego \n";
            }
        }
        void agregarVarPila(string id, bool v){
            bool existe = false;
            int posPila = pilaVars.size();  //Inicializo posicion en el tope (scope actual)
            int scActual = pilaVars.size();  //Inicializo posicion en el tope (scope actual)
            bool igual;
            int pos;
            while (!existe and posPila > 0) {
                pilaVars.at(posPila-1)->buscarVarScopeActual(id, 3, &igual, &pos);      //3-tipoBool
                if (pos == -1) //No encontre en scope posPila-1, sigo buscando mas abajo
                    posPila --;
                else {
                    existe = true;
                    if (igual) {    //Encontre y es del mismo tipo
                        pilaVars.at(posPila-1)->agregarVariable(id,v); //Solo actualizo valor
                    }
                    else {  //Encontre pero es de distinto tipo
                        pilaVars.at(scActual-1)->agregarVariable(id,v); //Agrego en el scope actual.
                    }
                }
            }
            if (!existe and posPila == 0) {
                pilaVars.at(scActual-1)->agregarVariable(id,v); //Agrego en el scope actual.
            }
        }

        void agregarVarPila(string id, TArreglo* v){
            bool existe = false;
            int posPila = pilaVars.size();  //Inicializo posicion en el tope (scope actual)
            int scActual = pilaVars.size();  //Inicializo posicion en el tope (scope actual)
            bool igual;
            int pos;
            while (!existe and posPila > 0) {
                pilaVars.at(posPila-1)->buscarVarScopeActual(id, 4, &igual, &pos);
                if (pos == -1) //No encontre en scope posPila-1, sigo buscando mas abajo
                    posPila --;
                else {
                    existe = true;
                    if (igual) {    //Encontre y es del mismo tipo
                        pilaVars.at(posPila-1)->agregarVariable(id,v); //Solo actualizo valor
                    }
                    else {  //Encontre pero es de distinto tipo
                        pilaVars.at(scActual-1)->agregarVariable(id,v); //Agrego en el scope actual.
                    }
                }
            }
            if (!existe and posPila == 0) {
                pilaVars.at(scActual-1)->agregarVariable(id,v); //Agrego en el scope actual.
            }
        }

        void agregarVarPila(string id, TObjeto* v){
            bool existe = false;
            int posPila = pilaVars.size();  //Inicializo posicion en el tope (scope actual)
            int scActual = pilaVars.size();  //Inicializo posicion en el tope (scope actual)
            bool igual;
            int pos;
            while (!existe and posPila > 0) {
                pilaVars.at(posPila-1)->buscarVarScopeActual(id, 5, &igual, &pos);
                if (pos == -1) //No encontre en scope posPila-1, sigo buscando mas abajo
                    posPila --;
                else {
                    existe = true;
                    if (igual) {    //Encontre y es del mismo tipo
                        pilaVars.at(posPila-1)->agregarVariable(id,v); //Solo actualizo valor
                    }
                    else {  //Encontre pero es de distinto tipo
                        pilaVars.at(scActual-1)->agregarVariable(id,v); //Agrego en el scope actual.
                    }
                }
            }
            if (!existe and posPila == 0) {
                pilaVars.at(scActual-1)->agregarVariable(id,v); //Agrego en el scope actual.
            }
        }

        //Retorna 1 si encontro la variable y una copia en variable, sino la encuentra retorna 0.
        TipoValor* buscarVariable(string id) {
            TipoValor* variable = NULL;
            int existe = 0;
            int posPila = pilaVars.size();
            while (existe == 0 and posPila > 0) {   //Busco en toda la pila comenzando por el actual
                variable = pilaVars.at(posPila-1)->devuelveVariable(id);
                if (variable != NULL) {
                    existe = 1;
                }
                posPila --;
            }
            return variable;
        }
        //Para Debug
        void imprimirStack(){
            cout << "IMPRIMIR STACK \n";
            int i,tamanio;
            tamanio = pilaVars.size();
            for (i=0; i < tamanio; i++)
                pilaVars.at(i)->imprimirLista();
            cout << "FIN STACK \n";
        }

};

#endif // PILA_H
