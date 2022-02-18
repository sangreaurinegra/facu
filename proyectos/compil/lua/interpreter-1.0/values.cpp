/*
Author:	Alan Macek
Date:	May 30, 2001

Terms of use: Please send me an email <al@alanmacek.com> if you are using this
		file or modifying it to let me know how it works for you.
*/

#include <iostream>
#include "values.h"
#include "environment.h"
#include "parserClasses.h"

Value::~Value()
{}

IntValue::IntValue(int v_) : v(v_) {}
IntValue::~IntValue() {}
void IntValue::print() const {	cout << v; }
Type* IntValue::getType() const { return new IntType; }
Value* IntValue::copy() const { return new IntValue(v); }
int IntValue::getIntValue() const {	return v; }

BoolValue::BoolValue(bool v_) : v(v_) {}
BoolValue::~BoolValue() {}
void BoolValue::print() const
{
	if (v)
		cout << "true";
	else
		cout << "false";
}
Type* BoolValue::getType() const { return new BoolType; }
Value* BoolValue::copy() const { return new BoolValue(v); }
bool BoolValue::getBoolValue() const { return v; }

ClosureValue::ClosureValue (ArgsList argsList_,
							Expression *body_, 
							Environment *env_)
: argsList(argsList_), body(body_), env(env_)
{}

ClosureValue::~ClosureValue()
{
//	delete env;
//	delete body;
//	delete argsList.args;
}

void ClosureValue::print() const
{
	cout << "ERROR - Closures can not be printed\n";
}

Value* ClosureValue::copy() const
{
//	cout << "Copying Closure Value\n";
	return new ClosureValue (argsList, body, env);
}

Type* ClosureValue::getType() const
{
	ClosureType *type = new ClosureType(new IntType, argsList.args->size());
	return type;
}
ArgsList ClosureValue::getArgs() const { return argsList; }
Expression* ClosureValue::getBody() const { return body; }
Environment* ClosureValue::getEnv() const {return env; }
