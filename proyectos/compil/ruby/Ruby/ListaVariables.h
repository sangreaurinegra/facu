#ifndef LISTAVARIABLES_H
#define LISTAVARIABLES_H
#include <vector>
#include "VarPila.h"
#include "TipoValor.h"


using namespace std;

class ListaVariables {
    public:
        std::vector<VarPila*> ListaVar;

        ListaVariables();
        virtual ~ListaVariables();


        //AGREGAR VARIABLE: Cuando tenemos una asignacion en un scope, verificamos si la variable:
        //verifico scope actual: - si ya esta definida y es del mismo tipo --> actualizo valor
        //                       - si ya esta definida pero es de distinto tipo --> actualizo valor y tipo (piso definicion anterior)
        //si no esta en scope actual, miro scope previos:
        //        - si ya esta definida y es del mismo tipo --> actualizo valor
        //        - si ya esta definida pero es de distinto tipo --> agrego variable en scope actual (redefino)
        void agregarVariable(string id, int v){
            bool igual;
            int pos;
            buscarVarScopeActual(id, 0, &igual, &pos);    //0-tipoInt
            if (pos > -1) {  //Encontre sigo buscando
                if (igual) {    //Encontre y es del mismo tipo
                    ListaVar.at(pos)->setValor(v); //Solo actualizo valor
                }
                else {//Encontre pero es de distinto tipo
                    ListaVar.at(pos)->setValor(v); //Solo actualizo valor
                    ListaVar.at(pos)->setTipo(0); //Solo actualizo valor
                }
            }
            else { //No la encontre => la agrego
                VarPila *valor = new VarPila();
                valor->setId(id);
                valor->setValor(v);
                valor->setTipo(0);
                ListaVar.push_back(valor);
            }
        }
        void agregarVariable(string id, float v){
            bool igual;
            int pos;
            buscarVarScopeActual(id, 1, &igual, &pos);    //1-tipoReal
            if (pos > -1) {  //Encontre sigo buscando
                if (igual)    //Encontre y es del mismo tipo
                    ListaVar.at(pos)->setValor(v); //Solo actualizo valor
                else {//Encontre pero es de distinto tipo
                    ListaVar.at(pos)->setValor(v); //Piso valor
                    ListaVar.at(pos)->setTipo(1); //Piso tipo
                }
            }
            else { //No la encontre => la agrego
                VarPila *valor = new VarPila();
                valor->setId(id);
                valor->setValor(v);
                valor->setTipo(1);  //1-tipoReal
                ListaVar.push_back(valor);
            }
        }
        void agregarVariable(string id, string v){
            bool igual;
            int pos;
            buscarVarScopeActual(id, 2, &igual, &pos);    //2-tipoStr
            if (pos > -1) {  //Encontre sigo buscando
                if (igual)     //Encontre y es del mismo tipo
                    ListaVar.at(pos)->setValor(v); //Solo actualizo valor
                else {//Encontre pero es de distinto tipo
                    ListaVar.at(pos)->setValor(v); //Piso valor
                    ListaVar.at(pos)->setTipo(2); //Piso tipo
                }
            }
            else { //No la encontre => la agrego
                VarPila *valor = new VarPila();
                valor->setId(id);
                valor->setValor(v);
                valor->setTipo(2); //2-tipoStr
                ListaVar.push_back(valor);
            }
        }
        void agregarVariable(string id, bool v){
            bool igual;
            int pos;
            buscarVarScopeActual(id, 3, &igual, &pos);    //3-tipobool
            if (pos > -1) {  //Encontre sigo buscando
                if (igual)    //Encontre y es del mismo tipo
                    ListaVar.at(pos)->setValor(v); //Solo actualizo valor
                else {//Encontre pero es de distinto tipo
                    ListaVar.at(pos)->setValor(v); //Piso valor
                    ListaVar.at(pos)->setTipo(3); //Piso tipo
                }
            }
            else { //No la encontre => la agrego
                VarPila *valor = new VarPila();
                valor->setId(id);
                valor->setValor(v);
                valor->setTipo(3); //2-tipoBool
                ListaVar.push_back(valor);
            }
        }

        void agregarVariable(string id, TArreglo* v){
            bool igual;
            int pos;
            buscarVarScopeActual(id, 4, &igual, &pos);
            if (pos > -1) {  //Encontre sigo buscando
                if (igual)    //Encontre y es del mismo tipo
                    ListaVar.at(pos)->setValor(v); //Solo actualizo valor
                else {//Encontre pero es de distinto tipo
                    ListaVar.at(pos)->setValor(v); //Piso valor
                    ListaVar.at(pos)->setTipo(4); //Piso tipo
                }
            }
            else { //No la encontre => la agrego
                VarPila *valor = new VarPila();
                valor->setId(id);
                valor->setValor(v);
                valor->setTipo(4);
                ListaVar.push_back(valor);
            }
        }

        void agregarVariable(string id, TObjeto* v){
            bool igual;
            int pos;
            buscarVarScopeActual(id, 5, &igual, &pos);
            if (pos > -1) {  //Encontre sigo buscando
                if (igual)    //Encontre y es del mismo tipo
                    ListaVar.at(pos)->setValor(v); //Solo actualizo valor
                else {//Encontre pero es de distinto tipo
                    ListaVar.at(pos)->setValor(v); //Piso valor
                    ListaVar.at(pos)->setTipo(4); //Piso tipo
                }
            }
            else { //No la encontre => la agrego
                VarPila *valor = new VarPila();
                valor->setId(id);
                valor->setValor(v);
                valor->setTipo(5);
                ListaVar.push_back(valor);
            }
        }

        //Busca id en scope
        TipoValor* devuelveVariable(string id) {
            int encontre = 0;
            int j = ListaVar.size(); //itero en el scope
            TipoValor* tipoVariable = NULL;
            while ((encontre == 0) and (j > 0)) {
                //if (id.compare(ListaVar.at(j-1)->getId()) == 0){
                if (ListaVar.at(j-1)->getId() == id) {   //si ya esta la variable, voy a devolver la posicion y me fijo si es del mismo tipo
                   encontre = 1;
                    //Miramos de que tipo es la variable que esta guardada
                    if (ListaVar.at(j-1)->getTipoVar() == 0)             //0-tipoInt
                        tipoVariable = new TInteger(ListaVar.at(j-1)->getValor().entero);
                    else if (ListaVar.at(j-1)->getTipoVar() == 1)        //Tipo real
                            tipoVariable = new TReal(ListaVar.at(j-1)->getValor().real);
                    else if (ListaVar.at(j-1)->getTipoVar() == 2)          //Tipo String
                                tipoVariable = new TString(ListaVar.at(j-1)->getValor().str);
                    else if (ListaVar.at(j-1)->getTipoVar() == 3)             //Tipo Booleano
                            tipoVariable = new TBool(ListaVar.at(j-1)->getValor().booleano);
                    else if (ListaVar.at(j-1)->getTipoVar() == 4)             //Tipo Arreglo
                        tipoVariable = ListaVar.at(j-1)->getValor().arreglo;
                    else if (ListaVar.at(j-1)->getTipoVar() == 5)             //Tipo Objeto
                        tipoVariable = ListaVar.at(j-1)->getValor().arreglo;

                }else
                    j--;
            }
            return tipoVariable;
        }

        //Busca variable en scope actual
        int buscarVarScopeActual(string id, int tipo, bool *igual, int *pos){
            bool encontre = false;
            *pos = -1;
            *igual = false;
            int j = 0; //itero en el scope
            int topeScope = ListaVar.size(); //Tamaño de la vector usado en scope
            while (!encontre and j < topeScope) {
               if (ListaVar.at(j)->getId() == id) {    //si ya esta la variable, voy a devolver la posicion y me fijo si es del mismo tipo
                    encontre = true;
                    if (ListaVar.at(j)->getTipoVar() == tipo)    //si es del mismo tipo
                        *igual = true;  //retorno la posicion en el scope
                    else
                        *igual = false; //si es de distinto tipo
                }
                else
                    j++;
            }
            if (encontre)
                *pos = j;
            return 0;
        }

        void imprimirLista() {
            int i,tamanio;
            tamanio = ListaVar.size();
            for (i=0; i < tamanio; i++) {
                if (ListaVar.at(i)->getTipoVar() == 0)
                    cout << "Var: " << ListaVar.at(i)->getId() << " - " << "valor: " << ListaVar.at(i)->getValor().entero << "\n";
                else if (ListaVar.at(i)->getTipoVar() == 1)
                    cout << "Var: " << ListaVar.at(i)->getId() << " - " << "valor: " << ListaVar.at(i)->getValor().real << "\n";
                else if (ListaVar.at(i)->getTipoVar() == 2)
                    cout << "Var: " << ListaVar.at(i)->getId() << " - " << "valor: " << ListaVar.at(i)->getValor().str << "\n";
                else if (ListaVar.at(i)->getTipoVar() == 3)
                    cout << "Var: " << ListaVar.at(i)->getId() << " - " << "valor: " << ListaVar.at(i)->getValor().booleano << "\n";
                else if (ListaVar.at(i)->getTipoVar() == 4){
                    cout << "Var: " << ListaVar.at(i)->getId() << " - " << "arreglo: " ;
                    ListaVar.at(i)->getValor().arreglo->imprimir();
                }
            }

        }

};

#endif // LISTAVARIABLES_H
