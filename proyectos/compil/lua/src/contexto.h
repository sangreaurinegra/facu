//Grupo 19
#ifndef CONTEXTO_H
#define CONTEXTO_H

#include <vector>
using namespace std;
#include <iostream>
#include <stdarg.h>
#include <string.h>
#include "parser.h"
#include "variable.h"
#include "scope.h"

class NodoFuncion;

class Contexto{
public:
	static Contexto* contextoVacio();
	static Contexto* extenderContexto(Contexto* contexto, int nVars, ...);
	static Contexto* extenderContexto(Contexto* contexto, vector<Variable> vars);
	
	//copia de  valor o tipo
	bool lookupVariable(Variable *v);
	
	//dar la variable actual del contexto
	bool getVariable (Variable *&v);

	void printContexto(int tabLevel = 0) const;

    void agregarFuncion(NodoFuncion* funcion);
    NodoFuncion* buscarFuncion(char* id);
    void agregarVariable(Variable * variable);
		
	void nuevoScope(); // pushea un scope
	void agregarVariableAScopeActual(Variable * variable);
	void eliminarScope(); // elimina el ultimo scope pusheado

private:
	Contexto();
    vector<NodoFuncion*> funciones;	
	//Contexto* nextCont;
	vector<Variable> variables;
	vector<Scope> scopes;
};

#endif
