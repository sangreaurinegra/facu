#include "nodo.h"


ListaClases::ListaClases(){}



ListaClases* ListaClases::instancia = NULL;

ListaClases* ListaClases::getInstancia(){
    if (instancia == NULL)
      instancia = new ListaClases();
    return instancia;
}

int ListaClases::agragarClase(ClassStatment* clase){

    int i = 0;
    int res;
    int tope = clases.size();
    bool encontre = false;


    while (!encontre && i<tope){

           if (clase->nombre.str == clases.at(i)->nombre.str){
                res = 0;
                encontre=true;
            }else
                i++;


    }

    if (!encontre){
        clases.push_back(clase);
        res = 1;
    }
    return res;

}

ClassStatment* ListaClases::buscarClase(string id){

    int i = 0;
    ClassStatment* res = NULL;
    int tope = clases.size();
    bool encontre = false;
    while (!encontre && i<tope){

            if (id == clases.at(i)->nombre.str){
                res = clases.at(i);
                encontre=true;
            }else
                i++;

    }

    return res;
}

/*vector<NVariable*> obtenerAtributos (ClassStatment* clase){

    int i;
    for(i=0;i<clase.funciones->)

}*/


void ListaClases::imprimirClases(){
    int i;
    int j;
    cout << "---Lista Clases ----\n";
    for (i=0;i<clases.size();i++){
            cout<<"clase:" << clases.at(i)->nombre.str << "\n";
           // for (j=0;j<clases.at(i)->funciones->size())
    }

}


ListaClases::~ListaClases(){}

