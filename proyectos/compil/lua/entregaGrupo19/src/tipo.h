// Grupo 19
#ifndef TIPO_H
#define TIPO_H
#include <iostream>
#include <string>
#include <vector>
using namespace std;

class Expression;

// Utilizado apra el tipeo en tiempo de ejecucion
enum TipoBasico
{
	IntTipoClase,
	NILTipoClase,
	BoolTipoClase,
	RealTipoClase,
	StringTipoClase,
	TableTipoClase,
	ERROR
};

class Tipo
{
public:
	virtual ~Tipo() {}
	virtual string imprimir() const = 0;

	virtual bool comparar(Tipo *t) const;	
	virtual Tipo* copiar() const = 0;
	virtual TipoBasico getTipoBasico() const = 0;
};

class TipoInt : public Tipo
{
public:
	TipoInt() {}
	~TipoInt() {}
	string imprimir() const;
	Tipo* copiar() const;
	TipoBasico getTipoBasico() const { return IntTipoClase; }
};

class TipoBool : public Tipo
{
public:
	TipoBool() {}
	~TipoBool() {}
	string imprimir() const;
	Tipo* copiar() const;
	TipoBasico getTipoBasico() const { return BoolTipoClase; }
};


class TipoReal : public Tipo
{
public:
	TipoReal() {}
	~TipoReal() {}
	string imprimir() const;
	Tipo* copiar() const;
	TipoBasico getTipoBasico() const { return RealTipoClase; }
};

class TipoString : public Tipo
{
public:
	TipoString() {}
	~TipoString() {}
	string imprimir() const;
	Tipo* copiar() const;
	TipoBasico getTipoBasico() const { return StringTipoClase; }
};

class TipoNIL : public Tipo
{
public:
	TipoNIL() {}
	~TipoNIL() {}
	string imprimir() const;
	Tipo* copiar() const;
	TipoBasico getTipoBasico() const { return NILTipoClase; }
};

class TipoTable : public Tipo
{
public:
	TipoTable() {}
	~TipoTable() {}
	string imprimir() const;
	Tipo* copiar() const;
	TipoBasico getTipoBasico() const { return TableTipoClase; }
};

struct ListaArgumento
{
	vector<Tipo*> *tipos;
	vector<string> *argumentos;

};
#endif
