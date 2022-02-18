#include <iostream>
#include <cstdio>
#include "nodo.h"
#include "ManejadorPila.h"

using namespace std;


extern int yyparse();
extern FILE* yyin;


extern Block* program;

int main(int argc, char **argv)
{
    try{

        if (argc>1)
            yyin = fopen(argv[1],"rt");
        else
            yyin = stdin;

        yyparse();

        //program->b();

        ManejadorPila* mp = ManejadorPila::getInstancia();
        mp->agregarPila();

        program->ejecutar();

        /*mp->obtenerPilaActual()->imprimirStack();
        mp->imprimirVarsGlobales();
        ListaFunciones* lf = ListaFunciones::getInstancia();
        lf->imprimirFunciones();
        ListaClases* lc = ListaClases::getInstancia();
        lc->imprimirClases();*/


    }catch (char* error){
        cout <<error << "\n";
    }catch (string caught){
        cout << caught << "\n";
    }


    return 0;
}

