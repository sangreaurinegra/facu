/*
Author:	Alan Macek
Date:	May 30, 2001

Terms of use: Please send me an email <al@alanmacek.com> if you are using this
		file or modifying it to let me know how it works for you.
*/

#include <iostream>
#include "environment.h"
#include "parserClasses.h"
#include "types.h"

extern int yydebug;
int yyparse();

Expression* resultExp;

int main()
{
// Uncommenting the following line enables the debugging information in
// the parser.
//	yydebug=1;
	int res = yyparse();
	if (res == 1)
	{
		cout << "Syntax Error -> failure\n";
		return 0;
	}
	
	if (resultExp == 0)
	{
		cout << "ERRROR!\n";
		return 0;
	}

	cout << "\nType Checking...\n";
	Type *resType = resultExp->typeCheck(Environment::EmptyEnv());
	if (resType == 0)
	{
		cout << "\tType checking failed\n";
		return 0;
	}
	cout << "\tType is " << resType->print() << endl;
	
	cout << "Evaluating...\n";
	Value *resVal = resultExp->evaluate(Environment::EmptyEnv());
//	cout << "Finished Evaluating\n";
	cout << "\tResult is ";
	resVal->print();
	cout << endl;
//	printf("Result is %d\n", );
	delete resultExp;
	resultExp = 0;
	
	return 0;
}
