/*
Author:	Alan Macek
Date:	May 30, 2001

Terms of use: Please send me an email <al@alanmacek.com> if you are using this
		file or modifying it to let me know how it works for you.
*/

#ifndef PARSERCLASSES_H
#define PARSERCLASSES_H

#include "types.h"
#include "environment.h"

class Expression;

class Expression
{
public:
	virtual ~Expression();
	virtual Value* evaluate(Environment *env) = 0;
	virtual Type* typeCheck(Environment *env) = 0;
protected:
	Expression(string text);
	string expText;
};

class IntExpression : public Expression
{
public:
	IntExpression(int value, string text);
	~IntExpression();
	
	Value* evaluate(Environment *env);
	Type* typeCheck(Environment *env);
private:
	int value;
};

class BoolExpression : public Expression
{
public:
	BoolExpression(bool value, string text);
	~BoolExpression();
	
	Value* evaluate(Environment *env);
	Type* typeCheck(Environment *env);
private:
	bool value;
};

class IfExpression : public Expression
{
public:
	IfExpression (Expression *ifExp,
				  Expression *thenExp,
				  Expression *elseExp,
				  string text);
	~IfExpression();
	
	Value* evaluate(Environment *env);
	Type* typeCheck(Environment *env);
private:
	Expression *ifExp, *thenExp, *elseExp;
};

class LetExpression : public Expression
{
public:
	LetExpression (string id,
				   Expression *letExp,
				   Expression *bodyExp,
				   string text);
	~LetExpression();
	
	Value* evaluate(Environment *env);
	Type* typeCheck(Environment *env);
private:
	string id;
	Expression *letExp, *bodyExp;
};

class VarExpression : public Expression
{
public:
	VarExpression (string id, string text);
	~VarExpression();
	
	Value* evaluate(Environment *env);
	Type* typeCheck(Environment *env);
private:
	string id;
};

class PrimExpression : public Expression
{
public:
	enum Prim {
		Add,
		Subtract,
		Multiply,
		Equal
	};

public:
	PrimExpression (Prim p, ExpsList list, string text);
	~PrimExpression();
	
	Value* evaluate(Environment *env);
	Type* typeCheck(Environment *env);
private:
	Prim p;
	ExpsList list;
};

class ProcExpression : public Expression
{
public:
	ProcExpression (ArgsList list, Expression *body, string text);
	~ProcExpression();
	
	Value* evaluate(Environment *env);
	Type* typeCheck(Environment *env);
private:
	ArgsList list;
	Expression *body;
};

class CallExpression : public Expression
{
public:
	CallExpression (Expression *proc, ExpsList list, string text);
	~CallExpression();
	
	Value* evaluate(Environment *env);
	Type* typeCheck(Environment *env);
private:
	Expression *proc;
	ExpsList list;
};

class SetVarExpression : public Expression
{
public:
	SetVarExpression (string id, Expression *rhs, string text);
	~SetVarExpression();
	
	Value* evaluate(Environment *env);
	Type* typeCheck(Environment *env);
private:
	string id;
	Expression *rhs;
};

class BeginExpression : public Expression
{
public:
	BeginExpression (ExpsList list, string text);
	~BeginExpression();
	
	Value* evaluate(Environment *env);
	Type* typeCheck(Environment *env);

private:
	ExpsList list;
};
#endif
