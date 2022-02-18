/*
Author:	Alan Macek
Date:	May 30, 2001

Terms of use: Please send me an email <al@alanmacek.com> if you are using this
		file or modifying it to let me know how it works for you.
*/

#ifndef VARIABLE_H
#define VARIABLE_H

#include <string>
using namespace std;
#include "values.h"

class Variable
{
public:
	Variable() : name(""), value(0), type(0) {}
	Variable(string id) : name(id), value(0), type(0) {}
	Variable(string id, Value *v) : name(id), value(v), type(0) {}
	Variable(string id, Type *t) : name(id), value(0), type(t) {}
	~Variable() { /*delete value;*/ }
	
	bool operator< (const Variable &v) const {return name < v.name;}
	bool operator==(const Variable &v) const {return name ==v.name;}

	Value* getValue() const {return value;}
	void setValue(Value* v) {value = v;}
	
	string getName() const {return name;}

	Type* getType() const {return type;}
	void setType(Type* t) {type = t;}

private:
	string name;
	Value *value;
	Type *type;
};

#endif
