#ifndef MANEJADORPILA_H
#define MANEJADORPILA_H
#include "Pila.h"
#include "TipoValor.h"
#include <vector>


class ManejadorPila{

    private:
        static ManejadorPila* instancia;
        vector<Pila*> ListaPilas;
        vector<VarPila*> VarsGlobales;
        ManejadorPila();

    public:
        static ManejadorPila* getInstancia();
        Pila* crearPilaNueva();
        void agregarPilaCreada(Pila* p);
        void agregarPila();
        void  borrarPila();
        Pila* obtenerPilaActual();
        void agragarVariableGlobal(VarPila* var);
        TipoValor* obtenerVariableGlobal(string id);
        void imprimirVarsGlobales();
        virtual ~ManejadorPila();


};


#endif // MANEJADORPILA_H

