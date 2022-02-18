/*
Author:	Alan Macek
Date:	May 30, 2001

Terms of use: Please send me an email <al@alanmacek.com> if you are using this
		file or modifying it to let me know how it works for you.
*/

#ifndef ENVIRONMENT_H
#define ENVIRONMENT_H

#include <vector>
using namespace std;
#include "variable.h"

class Environment
{
public:
	static Environment* EmptyEnv();
	static Environment* ExtendEnv(Environment* env, int nVars, ...);
	static Environment* ExtendEnv(Environment* env, vector<Variable> vars);
	
	//Get copy of value/type
	bool lookupVariable(Variable *v);
	//Get actual entry in environment
	bool getVariable (Variable *&v);

	void printEnv(int tabLevel = 0) const;
private:
	Environment();
	
	Environment* nextEnv;
	vector<Variable> vars;
};

#endif
