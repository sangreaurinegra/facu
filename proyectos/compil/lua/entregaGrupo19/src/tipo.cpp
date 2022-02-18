//Grupo 19
#include "tipo.h"

bool Tipo::comparar(Tipo *t) const
{
	if (t == 0) return false;
	return (getTipoBasico() == t->getTipoBasico());
}

/*****	TIPO INT  *******/
string TipoInt::imprimir() const
{
	return "entero";
}

Tipo* TipoInt::copiar() const
{
	return new TipoInt();
}

/*****	TIPO BOOL  *******/
string TipoBool::imprimir() const
{
	return "booleano";
}

Tipo* TipoBool::copiar() const
{
	return new TipoBool();
}

/*****	TIPO REAL  *******/
string TipoReal::imprimir() const
{
	return "real";
}

Tipo* TipoReal::copiar() const
{
	return new TipoReal();
}

/*****	TIPO STRING  *******/
string TipoString::imprimir() const
{
	return "string";
}

Tipo* TipoString::copiar() const
{
	return new TipoString();
}

/*****	TIPO NIL  *******/
string TipoNIL::imprimir() const
{
	return "nil";
}

Tipo* TipoNIL::copiar() const
{
	return new TipoNIL();
}

/*****	TIPO TABLE  *******/
string TipoTable::imprimir() const
{
	return "table";
}

Tipo* TipoTable::copiar() const
{
	return new TipoTable();
}

