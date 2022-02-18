/*
Author:	Alan Macek
Date:	May 30, 2001

Terms of use: Please send me an email <al@alanmacek.com> if you are using this
		file or modifying it to let me know how it works for you.
*/

#include <iostream>
#include "types.h"

bool Type::compare(Type *t) const
{
	if (t == 0) return false;
	return (getBasicType() == t->getBasicType());
}

string IntType::print() const
{
	return "int";
}

Type* IntType::copy() const
{
	return new IntType();
}

string BoolType::print() const
{
	return "bool";
}

Type* BoolType::copy() const
{
	return new BoolType();
}

ClosureType::ClosureType (Type *rType_, int nArgs_)
: nArgs(nArgs_), rType(rType_)
{
	argTypes = new Type*[nArgs];
	for (int i = 0; i < nArgs; ++i)
		argTypes[i] = 0;
}

ClosureType::~ClosureType()
{
	delete rType;
	rType = 0;
	for (int i = 0; i < nArgs; ++i)
		delete argTypes[i];
	delete [] argTypes;
}

Type* ClosureType::copy() const
{
	ClosureType* c = new ClosureType (rType->copy(), nArgs);
	for (int i = 0; i < nArgs; ++i)
		c->setNthArgType(i, getNthArgType(i)->copy());
	return c;
}

void ClosureType::setNthArgType(int i, Type *type)
{
	if (i >= nArgs) { cout << "ERROR setting nTh args"; }
	if (argTypes[i] != 0) { cout << "setNthArg - type already set"; }
	argTypes[i] = type;
}

Type* ClosureType::getNthArgType(int i) const
{
	return argTypes[i];
}

string ClosureType::print() const
{
	string r = "(";
	for (int i = 0; i < nArgs; ++i)
	{
		if (i > 0)
			r = r + " * ";
		r = r + argTypes[i]->print();
	}
	r = r + " -> ";
	r = r + rType->print();
	r = r + ")";
	return r;
}

bool ClosureType::compare(Type *t) const
{
	if (t == 0) return false;
	//Check that both are closure types
	if (getBasicType() != t->getBasicType()) return false;
	
	ClosureType *c = (ClosureType*)t;
	//Set the return type
	c->rType = rType->copy();

	//Check that equal number of arguments
	if (nArgs != c->nArgs) return false;

	//Check that all the arguments are the same
	for (int i = 0; i < nArgs; ++i)
		if (!argTypes[i]->compare(c->argTypes[i])) return false;
	return true;
}
