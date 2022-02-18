/*
Author:	Alan Macek
Date:	May 30, 2001

Terms of use: Please send me an email <al@alanmacek.com> if you are using this
		file or modifying it to let me know how it works for you.
*/

#include <iostream>
#include <stdarg.h>
#include "environment.h"

Environment* Environment::EmptyEnv()
{
	return new Environment();
}

Environment* Environment::ExtendEnv(Environment* env, int nVars, ...)
{
	Environment *nenv = new Environment();
	nenv->nextEnv = env;
	
	va_list ap;
	va_start(ap, nVars);

	while (nVars > 0)
	{
		Variable *v = va_arg(ap, Variable*);
		nenv->vars.push_back(*v);
		nVars--;
	}
	
	return nenv;
}

Environment* Environment::ExtendEnv(Environment* env,
									vector<Variable> newVars)
{
	Environment *nenv = new Environment();
	nenv->nextEnv = env;
	
	nenv->vars = newVars;
	return nenv;
}

bool Environment::getVariable (Variable *&v)
{
	if (v == 0) return false;
	
	int match = -1;
	for (int i = 0; i < (int)vars.size(); ++i)
		if (vars[i].getName() == v->getName())
			match = i;
		
	if (match == -1)
	{
		if (nextEnv == 0)
		{
			cout << "Variable " << v->getName() << " not in environment\n";
			return false;
		}
		return nextEnv->lookupVariable(v);
	}
	delete v;
	v = &vars[match];
	return true;
}

bool Environment::lookupVariable (Variable *v)
{
	Variable *actualV = new Variable(v->getName());
	if (!getVariable(actualV))
		return false;

//	cout << "lookup found match\n";
	Value *val = actualV->getValue();
	Type *t = actualV->getType();
	if (val != 0)
		val = val->copy();
	if (t != 0)
		t = t->copy();
//	cout << "type is " << t->print() << endl;
		
	v->setValue (val);
	v->setType (t);
//	cout << "finished lookup\n";
	return true;
}

Environment::Environment()
: nextEnv(0), vars()
{}

void Environment::printEnv(int tabLevel) const
{
	string tabs;
	for (int j = 0; j < tabLevel; ++j)
		tabs += "\t";

	if (tabLevel == 0)
		cout << "Environment Dump:\n";

	cout << tabs << "\tNumber of vars is " << vars.size() << endl;
	for (unsigned int i = 0; i < vars.size(); ++i)
	{
		const Variable *it = &vars[i];
		cout << tabs << "\t" << it->getName() << " - ";
		if (it->getValue() != 0)
			it->getValue()->print();
		else if (it->getType() != 0)
			cout << it->getType()->print();
		else
			cout << "NONE";
		cout << endl;
	}
	if (nextEnv != 0)
		nextEnv->printEnv(tabLevel + 1);
}
