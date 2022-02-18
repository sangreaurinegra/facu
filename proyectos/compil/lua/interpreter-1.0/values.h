/*
Author:	Alan Macek
Date:	May 30, 2001

Terms of use: Please send me an email <al@alanmacek.com> if you are using this
		file or modifying it to let me know how it works for you.
*/

#ifndef VALUES_H
#define VALUES_H

#ifdef WIN32
	#include <ostream>
#else
	#include <ostream.h>
#endif
#include "types.h"
class Expression;
class Environment;

class Value
{
public:
	virtual ~Value();
	
	virtual void print() const = 0;
	virtual Type* getType() const = 0;
	virtual Value* copy() const = 0;
};

class IntValue : public Value
{
public:
	IntValue(int v);
	~IntValue();
	
	void print() const;
	Type* getType() const;
	Value* copy() const;
	
	int getIntValue() const;
private:
	int v;
};

class BoolValue : public Value
{
public:
	BoolValue (bool v);
	~BoolValue();
	
	void print() const;
	Type* getType() const;
	Value* copy() const;
	
	bool getBoolValue() const;
private:
	bool v;
};

class ClosureValue : public Value
{
public:
	ClosureValue (ArgsList argList, Expression *body, Environment *env);
	~ClosureValue();
	
	void print() const;
	Type* getType() const;
	Value* copy() const;
	
	ArgsList getArgs() const;
	Expression* getBody() const;
	Environment* getEnv() const;
private:
	ArgsList argsList;
	Expression *body;
	Environment *env;
};
#endif
