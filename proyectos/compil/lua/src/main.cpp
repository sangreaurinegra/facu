
#include <iostream>
#include "contexto.h"
#include "parser.h"
#include "tipo.h"
#include <stdio.h>

extern FILE *yyin;
extern int yydebug;
int yyparse();

NodoBloque* programa;

int main(int argc, char **argv)
{
    try{
        if (argc>1)
            yyin = fopen(argv[1],"rt");
        else
            yyin = fopen("entrada.txt", "rt");

        if (yyin == NULL) {
		    cout << "FALLA: Archivo no encontrado\n";
            return 0;
        }

        int res = yyparse();
        if (res == 1) {
	        return 0;
        }


        if (programa == 0) {
	        cout << "ERROR!\n";
	        return 0;
        }

       Valor *resVal = programa->evaluar(Contexto::contextoVacio());

        if (resVal != NULL) {
                cerr << "> ";
                resVal->imprimir();
        }else {
            //cerr << "null...\n";
        }
                    cerr << endl;
        return 0;
    }catch( char * str ) {
            cout << str << '\n';
    }
}
