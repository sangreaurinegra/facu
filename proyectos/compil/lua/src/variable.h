//Grupo 19
#ifndef VARIABLE_H
#define VARIABLE_H

#include <string>
using namespace std;
#include "valor.h"

class Variable
{
public:
	Variable() : nombre(""), valor(0), tipo(0) {}
	Variable(string id) : nombre(id), valor(0), tipo(0) {}
	Variable(string id, Valor *v) : nombre(id), valor(v), tipo(0) {}
	Variable(string id, Tipo *t) : nombre(id), valor(0), tipo(t) {}
	~Variable() { /*delete valor;*/ }
	
	bool operator< (const Variable &v) const {return nombre < v.nombre;}
	bool operator==(const Variable &v) const {return nombre ==v.nombre;}

	Valor* getValor() const {return valor;}
	void setValor(Valor* v) {valor = v;}
	
	string getNombre() const {return nombre;}

	Tipo* getTipo() const {return tipo;}
	void setTipo(Tipo* t) {tipo = t;}

private:
	string nombre;
	Valor *valor;
	Tipo *tipo;
};

#endif
