/*
Author:	Alan Macek
Date:	May 30, 2001

Terms of use: Please send me an email <al@alanmacek.com> if you are using this
		file or modifying it to let me know how it works for you.
*/

#include <iostream>
#include "parserClasses.h"
#include "values.h"

//Helper Functions
static int  EvaluateToInt  (Expression *exp, Environment *env);
static bool EvaluateToBool (Expression *exp, Environment *env);

//********  Implementation of 'Expression' base class *********

Expression::Expression(string text)
: expText(text) {}
Expression::~Expression()
{}

//********       Implementation of 'IntExpression' *********
#define Inherited Expression

IntExpression::IntExpression(int value_, string text)
: Inherited(text), value(value_) {}
IntExpression::~IntExpression() {}
Value* IntExpression::evaluate(Environment *env)
{ 
#ifdef EVAL_DEBUG
	cout << "Evaluating Int Expression\n";
#endif
	return new IntValue(value);
}

Type* IntExpression::typeCheck(Environment *env)
{
#ifdef TYPE_DEBUG
	cout << "Typing Int Expression\n";
#endif
	return new IntType;
}

//********       Implementation of 'BoolExpression' *********

BoolExpression::BoolExpression(bool value_, string text) 
: Inherited(text), value(value_) {}
BoolExpression::~BoolExpression() {}
Value* BoolExpression::evaluate(Environment *env)
{
#ifdef EVAL_DEBUG
	cout << "Evaluating Bool Expression\n";
#endif
	return new BoolValue(value);
}
Type* BoolExpression::typeCheck(Environment *env)
{
#ifdef TYPE_DEBUG
	cout << "Typing Bool Expression\n";
#endif
	return new BoolType;
}

//********       Implementation of 'IfExpression' *********

IfExpression::IfExpression (Expression *if_,
							Expression *then_,
							Expression *else_,
							string text)
: Inherited(text), ifExp(if_), thenExp(then_), elseExp(else_)
{}

IfExpression::~IfExpression()
{
	delete ifExp;
	delete thenExp;
	delete elseExp;
}
	
Value* IfExpression::evaluate(Environment *env)
{
#ifdef EVAL_DEBUG
	cout << "Evaluating If Expression\n";
#endif
	if (EvaluateToBool(ifExp, env) == true)
		return thenExp->evaluate(env);
	return elseExp->evaluate(env);
}

Type* IfExpression::typeCheck(Environment *env)
{
#ifdef TYPE_DEBUG
	cout << "Typing If Expression\n";
#endif
	Type *ifExpType = ifExp->typeCheck(env);
	if (!ifExpType->compare(new BoolType))
	{
		cout << "In '" << expText << "' expression, test condition must be of 'bool' type\n";
		return 0;
	}
	Type* thenType = thenExp->typeCheck(env);
	if (!thenType->compare(elseExp->typeCheck(env)))
	{
		cout << "Branches of condition '" << expText << "' must be of same type.\n";
		return 0;
	}
	return thenType;
}

//********       Implementation of 'LetExpression' *********

LetExpression::LetExpression (string id_,
							  Expression *letExp_,
							  Expression *bodyExp_,
							  string text)
: Inherited(text), id(id_), letExp(letExp_), bodyExp(bodyExp_)
{}

LetExpression::~LetExpression()
{
	delete letExp;
	delete bodyExp;
}

Value* LetExpression::evaluate(Environment *env)
{
#ifdef EVAL_DEBUG
	cout << "Evaluating Let Expression\n";
#endif
	Variable v = Variable(id, letExp->evaluate(env));
	Environment *e = Environment::ExtendEnv(env, 1, &v);
	return bodyExp->evaluate(e);
}

Type* LetExpression::typeCheck(Environment *env)
{
#ifdef TYPE_DEBUG
	cout << "Typing Let Expression\n";
#endif
	Variable v = Variable(id, letExp->typeCheck(env));
	Environment *e = Environment::ExtendEnv(env, 1, &v);
	return bodyExp->typeCheck(e);
}	

//********       Implementation of 'VarExpression' *********

VarExpression::VarExpression (string id_, string text)
: Inherited(text), id(id_)
{}

VarExpression::~VarExpression()
{}
	
Value* VarExpression::evaluate(Environment *env)
{
#ifdef EVAL_DEBUG
	cout << "Evaluating Var Expression\n";
#endif
//	env->printEnv();
	Variable v(id);
	env->lookupVariable(&v);
	return v.getValue();
}

Type* VarExpression::typeCheck(Environment *env)
{
#ifdef TYPE_DEBUG
	cout << "Typing Var Expression\n";
#endif
//	env->printEnv();
	Variable v(id);
	env->lookupVariable(&v);
	Type* t = v.getType();
	return t;
}

//********       Implementation of 'PrimExpression' *********

PrimExpression::PrimExpression (Prim p_, ExpsList list_, string text)
: Inherited(text), p(p_), list(list_)
{}

PrimExpression::~PrimExpression()
{
	delete list.exps;
}

Value* PrimExpression::evaluate(Environment *env)
{
#ifdef EVAL_DEBUG
	cout << "Evaluating Prim Expression\n";
#endif
	switch (p)
	{
	case Add:
		return new IntValue( EvaluateToInt((*list.exps)[0], env)
								+ EvaluateToInt((*list.exps)[1], env));
	case Subtract:
		return new IntValue( EvaluateToInt((*list.exps)[0], env)
								- EvaluateToInt((*list.exps)[1], env));
	case Multiply:
		return new IntValue( EvaluateToInt((*list.exps)[0], env)
								* EvaluateToInt((*list.exps)[1], env));
	case Equal:
		return new BoolValue( EvaluateToInt((*list.exps)[0], env)
								== EvaluateToInt((*list.exps)[1], env));
	default:
		break;
	}
	cout << "Primitive Not Supported\n";
	return 0;
}

Type* PrimExpression::typeCheck (Environment *env)
{
#ifdef TYPE_DEBUG
	cout << "Typing Prim Expression\n";
#endif
	ClosureType *primType = 0;
	switch (p)
	{
	case Add:
	case Subtract:
	case Multiply:
		primType = new ClosureType(new IntType, 2);
		primType->setNthArgType(0, new IntType);
		primType->setNthArgType(1, new IntType);
		break;
	case Equal:
		primType = new ClosureType(new BoolType, 2);
		primType->setNthArgType(0, new IntType);
		primType->setNthArgType(1, new IntType);
		break;
	default:
		cout << "Primitive Not Supported\n";
		return 0;
	}
	
	if ((int)list.exps->size() != primType->getNArgs())
	{
		cout << "Wrong number of arguments given in '" << expText << "'\n";
		cout << "Expected " << primType->getNArgs() << " given " << list.exps->size() << endl;
		delete primType;
		return 0;
	}
	for (unsigned int i = 0; i < list.exps->size(); ++i)
	{
		Type *givenType = (*list.exps)[i]->typeCheck(env);
		if (!givenType->compare(primType->getNthArgType(i)))
		{
			cout << "Argument number " << i+1 << " differs in type\n";
			cout << "Given '" << givenType->print() << "' wanted '" << primType->getNthArgType(i)->print() << "' in ";
			cout << "'" << expText << "'\n";
			delete primType;
			return 0;
		}
	}
	return primType->getReturnType();
}

//********       Implementation of 'ProcExpression' *********

ProcExpression::ProcExpression(ArgsList list_, Expression *body_, string text)
: Inherited(text), list(list_), body(body_)
{}

ProcExpression::~ProcExpression()
{
//	delete list.args;
//	delete body;
}

Value* ProcExpression::evaluate(Environment *env)
{
#ifdef EVAL_DEBUG
	cout << "Evaluating Proc Expression\n";
#endif
	return new ClosureValue(list, body, env);
}

Type* ProcExpression::typeCheck(Environment *env)
{
#ifdef TYPE_DEBUG
	cout << "Typing Proc Expression\n";
#endif
	vector<Variable> vars;
	for (unsigned int i = 0; i < list.args->size(); ++i)
		vars.push_back(Variable((*list.args)[i], (*list.types)[i]));
	
	Environment *nenv = Environment::ExtendEnv(env, vars);
//	nenv->printEnv();
	Type* rType = body->typeCheck(nenv);
	ClosureType* closure = new ClosureType(rType,
										   list.args->size());
	for (unsigned int j = 0; j < vars.size(); ++j)
		closure->setNthArgType(j, vars[j].getType());
	return closure;
}

//********       Implementation of 'CallExpression' *********

CallExpression::CallExpression (Expression *proc_, ExpsList list_, string text)
: Inherited(text), proc(proc_), list(list_)
{}

CallExpression::~CallExpression()
{
//	delete list.exps;
}

Value* CallExpression::evaluate(Environment *env)
{
#ifdef EVAL_DEBUG
	cout << "Evaluating Call Expression\n";
#endif
	ClosureValue *v = (ClosureValue*)proc->evaluate(env);
	ArgsList args = v->getArgs();
	Expression *body = v->getBody();
	
	vector<Variable> vars;
	for (unsigned int i = 0; i < args.args->size(); ++i)
	{
		string vName = (*args.args)[i];
		Expression *exp = (*list.exps)[i];
		vars.push_back(Variable(vName, exp->evaluate(env)));
	}

	Environment *nenv = Environment::ExtendEnv(v->getEnv(), vars);
//	nenv->printEnv();

	Value *res = body->evaluate(nenv);
	return res;
}

Type* CallExpression::typeCheck(Environment *env)
{
#ifdef TYPE_DEBUG
	cout << "Typing Call Expression\n";
#endif
	//Procedure Given
	Type* wanted = proc->typeCheck(env);
	
	//Arguments Given
	ClosureType v = ClosureType(0, list.exps->size());
	for (int i = 0; i < (int)list.exps->size(); ++i)
		v.setNthArgType(i, (*list.exps)[i]->typeCheck(env));

	if (!wanted->compare(&v))
	{
		cout << "Type mismatch in procedure call '" << expText << "'\n";
		cout << "Wanted '" << wanted->print() << "' and given '" << v.print() << "'\n";
		return 0;
	}

	ClosureType *closureGiven = (ClosureType*)wanted;
	return closureGiven->getReturnType();
}

int EvaluateToInt (Expression *exp, Environment *env)
{
	IntValue *v = (IntValue*)exp->evaluate(env);
	int iv = v->getIntValue();
	return iv;
}

bool EvaluateToBool (Expression *exp, Environment *env)
{
	BoolValue *v = (BoolValue*)exp->evaluate(env);
	bool bv = v->getBoolValue();
	return bv;
}

//********       Implementation of 'SetVarExpression' *********

SetVarExpression::SetVarExpression (string id_,
									Expression *rhs_,
									string text)
: Inherited(text), id(id_), rhs(rhs_)
{}

SetVarExpression::~SetVarExpression()
{}

Value* SetVarExpression::evaluate (Environment *env)
{
	Variable *v = new Variable(id);
	env->getVariable(v);
	Value *rhsValue = rhs->evaluate(env);
	v->setValue(rhsValue);
	return rhsValue;
}

Type* SetVarExpression::typeCheck (Environment *env)
{
	Variable *v = new Variable(id);
	if (!env->lookupVariable(v))
		return 0;
	Type* orgType = v->getType();
	Type* newType = rhs->typeCheck(env);
	if (!orgType->compare(newType))
	{
		cout << "The new type of a variable must be the same as the original type\n";
		cout << "Type Error in '" << expText << "'\n";
		return 0;
	}
	return orgType;
}

//********       Implementation of 'BeginExpression' *********

BeginExpression::BeginExpression(ExpsList list_, string text)
: Inherited(text), list(list_)
{}

BeginExpression::~BeginExpression()
{
//	delete list.exps;
}

Value* BeginExpression::evaluate (Environment *env)
{
	Value *result = 0;
	for (unsigned int i = 0; i < list.exps->size(); ++i)
		 result = (*list.exps)[i]->evaluate(env);
		 
	return result;
}

Type* BeginExpression::typeCheck (Environment *env)
{
	Type *type = 0;
	for (unsigned int i = 0; i < list.exps->size(); ++i)
	{
		type = (*list.exps)[i]->typeCheck(env);
		if (type == 0)
			return 0;
	}
		 
	return type;
}
