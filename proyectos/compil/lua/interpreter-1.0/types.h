/*
Author:	Alan Macek
Date:	May 30, 2001

Terms of use: Please send me an email <al@alanmacek.com> if you are using this
		file or modifying it to let me know how it works for you.
*/

#ifndef TYPES_H
#define TYPES_H

#include <string>
#include <vector>
using namespace std;

class Expression;

// Used for runtime class typing
enum BasicType
{
	IntTypeClass,
	BoolTypeClass,
	ClosureTypeClass,
	ERROR
};

class Type
{
public:
	virtual ~Type() {}
	virtual string print() const = 0;

	virtual bool compare(Type *t) const;	
	virtual Type* copy() const = 0;
	virtual BasicType getBasicType() const = 0;
};

class IntType : public Type
{
public:
	IntType() {}
	~IntType() {}
	string print() const;
	Type* copy() const;
	BasicType getBasicType() const { return IntTypeClass; }
};

class BoolType : public Type
{
public:
	BoolType() {}
	~BoolType() {}
	string print() const;
	Type* copy() const;
	BasicType getBasicType() const { return BoolTypeClass; }
};

class ClosureType : public Type
{
public:
	ClosureType(Type *rType, int nArgs);
	~ClosureType();
	void setNthArgType(int i, Type *type);
	Type* getNthArgType(int i) const;
	Type* getReturnType() const { return rType; }
	int getNArgs() const { return nArgs; }
	string print() const;

	bool compare(Type *t) const;	
	Type* copy() const;
protected:
	BasicType getBasicType() const { return ClosureTypeClass; }
private:
	int nArgs;
	Type *rType;
	Type **argTypes;
};

struct ExpsList
{
	vector<Expression*> *exps;
	string *listText;
};

struct ArgsList
{
	vector<Type*> *types;
	vector<string> *args;
	string *listText;
};

#endif
