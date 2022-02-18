#include "nodo.h"


ListaFunciones::ListaFunciones(){}


ListaFunciones* ListaFunciones::instancia = NULL;

ListaFunciones* ListaFunciones::getInstancia(){
    if (instancia == NULL)
      instancia = new ListaFunciones();
    return instancia;
}

int ListaFunciones::agragarFuncion(Statement* f){

    int i = 0;
    int res;
    int tope = funciones.size();
    bool encontre = false;
    FuncionDeclaracion* f1 = dynamic_cast<FuncionDeclaracion*>(f);
    FuncionDeclaracionConParametros* f2 = dynamic_cast<FuncionDeclaracionConParametros*>(f);

    while (!encontre && i<tope){

        FuncionDeclaracion* fun1 = dynamic_cast<FuncionDeclaracion*>(funciones.at(i));
        FuncionDeclaracionConParametros* fun2 = dynamic_cast<FuncionDeclaracionConParametros*>(funciones.at(i));

        if ((f1!=NULL) && (fun1!=NULL)){
           if (f1->nombre.str == fun1->nombre.str){
                res = 0;
                encontre=true;
            }else{
                i++;
            }
        }else if ((f2!=NULL) && (fun2!=NULL)){
            if (f2->nombre.str == fun2->nombre.str){
                res = 0;
                encontre=true;
            }else{
                i++;
            }
        }else if ((f1!=NULL) && (fun2!=NULL)){
            if (f1->nombre.str == fun2->nombre.str){
                res = 0;
                encontre=true;
            }else{
                i++;
            }
        }else if ((f2!=NULL) && (fun1!=NULL)){
            if (f2->nombre.str == fun1->nombre.str){
                res = 0;
                encontre=true;
            }else{
                i++;
            }
        }

    }
    if (!encontre){
        funciones.push_back(f);
        res = 1;
    }
    return res;

}


Statement* ListaFunciones::buscarFuncion(string id){

    int i = 0;
    Statement* res = NULL;
    int tope = funciones.size();
    bool encontre = false;
    while (!encontre && i<tope){

        FuncionDeclaracion* fun1 = dynamic_cast<FuncionDeclaracion*>(funciones.at(i));
        FuncionDeclaracionConParametros* fun2 = dynamic_cast<FuncionDeclaracionConParametros*>(funciones.at(i));
        FuncionDeclaracionClase* fun3 = dynamic_cast<FuncionDeclaracionClase*>(funciones.at(i));
        FuncionDeclaracionClaseConParametros* fun4 = dynamic_cast<FuncionDeclaracionClaseConParametros*>(funciones.at(i));

        if (fun1!=NULL){

            if (id == fun1->nombre.str){
                res = funciones.at(i);
                encontre=true;
            }else{
                i++;
            }
        }else if (fun2!=NULL){

            if (id == fun2->nombre.str){
                res = funciones.at(i);
                encontre=true;
            }else{
                i++;
            }
        }else if (fun3!=NULL){

            if (id == fun3->nombre.str){
                res = funciones.at(i);
                encontre=true;
            }else{
                i++;
            }
        }else if (fun4!=NULL){

            if (id == fun4->nombre.str){
                res = funciones.at(i);
                encontre=true;
            }else{
                i++;
            }
        }

    }
    return res;
}


void ListaFunciones::imprimirFunciones(){
    int i;
    cout << "---Lista Funciones ----\n";
    for (i=0;i<funciones.size();i++){

        FuncionDeclaracion* fun1 = dynamic_cast<FuncionDeclaracion*>(funciones.at(i));
        FuncionDeclaracionConParametros* fun2 = dynamic_cast<FuncionDeclaracionConParametros*>(funciones.at(i));
        FuncionDeclaracionClaseConParametros* fun3 = dynamic_cast<FuncionDeclaracionClaseConParametros*>(funciones.at(i));

        if (fun1!=NULL)
            cout<<"Funcion:" << fun1->nombre.str << "\n";
        else if (fun2!=NULL)
            cout<<"Funcion:" << fun2->nombre.str << "\n";
        else if (fun3!=NULL)
            cout<<"Funcion:" << fun3->nombre.str << "\n";
    }
}


ListaFunciones::~ListaFunciones(){}
