//Grupo 19 

#ifndef VALOR_H
#define VALOR_H

#include "tipo.h"

class Contexto;

class Valor
{
public:
	virtual ~Valor();
	
	virtual void imprimir() const = 0;
	virtual Tipo* getTipo() const = 0;
	virtual Valor* copiar() const = 0;
};

class ValorInteger : public Valor
{
public:
	ValorInteger(int valor);
	~ValorInteger();
	
	void imprimir() const;
	Tipo* getTipo() const;
	Valor *copiar() const;
	
	int getValorInteger() const;
private:
	int valor;
};

class ValorBool : public Valor
{
public:
	ValorBool(bool valor);
	~ValorBool();
	
	void imprimir() const;
	Tipo* getTipo() const;
	Valor *copiar() const;
	
	bool getValorBool() const;
private:
	bool valor;
};

class ValorReal : public Valor
{
public:
	ValorReal(float valor);
	~ValorReal();
	
	void imprimir() const;
	Tipo* getTipo() const;
	Valor *copiar() const;
	
	float getValorReal() const;
private:
	float valor;
};

class ValorString : public Valor
{
public:
	ValorString(string valor);
	~ValorString();
	
	void imprimir() const;
	Tipo* getTipo() const;
	Valor *copiar() const;
	
	string getValorString() const;
private:
	string valor;
};


class ValorNIL : public Valor
{
public:
	ValorNIL(int valor);
	~ValorNIL();
	
	void imprimir() const;
	Tipo* getTipo() const;
	Valor *copiar() const;
	
	int getValorNIL() const;
private:
	int valor;
};


/*typedef struct ElemTable {
	Valor* clave;
	Valor* valor;
} ElemTable;
typedef ElemTable* elementsTable[];
*/

typedef struct ElemTable {
	Valor* clave;
	Valor* valor;
} ElemTable;
	
typedef struct elementsTable {
	ElemTable* elem;
	elementsTable* sig;
    int cantElem;
} elementsTable;

class ValorTable : public Valor
{
public:
	ValorTable();
	~ValorTable();
	
	void imprimir() const;
	Tipo* getTipo() const;
	Valor* copiar() const;

    int largoTabla();	
	elementsTable* getValorTableConClave() const;
    elementsTable* getValorTableSinClave() const;
    void insertElemConClave(ElemTable* elem);
    void insertElemSinClave(ElemTable* elem);
    bool insertElemSinClave(ElemTable* elem, Valor* posicion);
    bool sort();
    bool remove(Valor* posicion, Valor* &valorResult);
    void setValor(Valor* clave, Valor* valor);
	Valor* getValor(Valor* clave);

	elementsTable* tableConClave;
    elementsTable* tableSinClave;
};

#endif


