#include "ManejadorPila.h"

ManejadorPila::ManejadorPila(){}


ManejadorPila* ManejadorPila::instancia = NULL;


ManejadorPila* ManejadorPila::getInstancia(){
    if (instancia == NULL)
      instancia = new ManejadorPila();
    return instancia;
}

void ManejadorPila::agregarPila(){
    Pila* p = new Pila();
    ListaPilas.push_back(p);
}

void ManejadorPila::borrarPila(){
    ListaPilas.pop_back();
}

Pila* ManejadorPila::obtenerPilaActual(){
    return ListaPilas.at(ListaPilas.size()-1);
}

void ManejadorPila::agragarVariableGlobal(VarPila* var){
    VarsGlobales.push_back(var);
}

Pila* ManejadorPila::crearPilaNueva(){
    Pila* p = new Pila();
    return p;
}

void ManejadorPila::agregarPilaCreada(Pila* p){
    ListaPilas.push_back(p);
}

TipoValor* ManejadorPila::obtenerVariableGlobal(string id){

        int encontre = 0;
        int j = VarsGlobales.size(); //itero en el scope
        TipoValor* tipoVariable = NULL;
        while ((encontre == 0) and (j > 0)) {
            if (VarsGlobales.at(j-1)->getId() == id) {   //si ya esta la variable, voy a devolver la posicion y me fijo si es del mismo tipo
               encontre = 1;
                //Miramos de que tipo es la variable que esta guardada
                if (VarsGlobales.at(j-1)->getTipoVar() == 0)             //0-tipoInt
                    tipoVariable = new TInteger(VarsGlobales.at(j-1)->getValor().entero);
                else if (VarsGlobales.at(j-1)->getTipoVar() == 1)        //Tipo real
                        tipoVariable = new TReal(VarsGlobales.at(j-1)->getValor().real);
                    else if (VarsGlobales.at(j-1)->getTipoVar() == 2)          //Tipo String
                                tipoVariable = new TString(VarsGlobales.at(j-1)->getValor().str);
                            else if (VarsGlobales.at(j-1)->getTipoVar() == 3)             //Tipo Booleano
                                    tipoVariable = new TBool(VarsGlobales.at(j-1)->getValor().booleano);
                                else if (VarsGlobales.at(j-1)->getTipoVar() == 4)             //Tipo arreglo
                                    tipoVariable = VarsGlobales.at(j-1)->getValor().arreglo;
            }
            else
                j--;
        }
        return tipoVariable;

}

void ManejadorPila::imprimirVarsGlobales(){
    int i;
    cout << "----- Variables Globales -----\n";
    for (i=0;i<VarsGlobales.size();i++){
        if (VarsGlobales.at(i)->getTipoVar() == 0)
            cout << "Var: " << VarsGlobales.at(i)->getId() << " - " << "valor: " << VarsGlobales.at(i)->getValor().entero << "\n";
        else if (VarsGlobales.at(i)->getTipoVar() == 1)
            cout << "Var: " << VarsGlobales.at(i)->getId() << " - " << "valor: " << VarsGlobales.at(i)->getValor().real << "\n";
        else if (VarsGlobales.at(i)->getTipoVar() == 2)
            cout << "Var: " << VarsGlobales.at(i)->getId() << " - " << "valor: " << VarsGlobales.at(i)->getValor().str << "\n";
        else if (VarsGlobales.at(i)->getTipoVar() == 3)
            cout << "Var: " << VarsGlobales.at(i)->getId() << " - " << "valor: " << VarsGlobales.at(i)->getValor().booleano << "\n";
        else if (VarsGlobales.at(i)->getTipoVar() == 4){
            cout << "Var: " << VarsGlobales.at(i)->getId() << " - " << "arreglo: " ;
            VarsGlobales.at(i)->getValor().arreglo->imprimir();
        }
    }
}

ManejadorPila::~ManejadorPila()
{
    //dtor
}
