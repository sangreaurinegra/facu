//Grupo 19
#ifndef SCOPE_H
#define SCOPE_H

#include "variable.h"

class Scope{
public:

static Scope* scopeVacio();

void agregarVariable(Variable * variable);
Variable* getVariable(string nombre);

private:

vector<Variable> variables;

};

#endif

