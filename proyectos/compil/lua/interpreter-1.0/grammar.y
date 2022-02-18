%{
/*
Author:	Alan Macek
Date:	May 30, 2001

Terms of use: Please send me an email <al@alanmacek.com> if you are using this
		file or modifying it to let me know how it works for you.
*/

#include "parserClasses.h"
#include <string>
#include <vector>
int yylex();
int yyerror(char *s);
extern Expression* resultExp;
#define YYERROR_VERBOSE
%}

%union {
	int	val;
	Expression *expr;
	char* id;
	PrimExpression::Prim prim;
	ExpsList exprs;
	ArgsList args;
	Type* varType;
	vector<Type*> *types;
}

%token_table

%token <val> NUM
%token <id> IDENTIFIER
%type <expr> exp
%type <prim> primitive
%type <exprs> listOfExps
%type <args> listOfArgs 
%type <varType> varType
%type <types> listOfTypes
%token IF "if"
%token THEN "then"
%token ELSE "else"
%token LET "let"
%token IN "in"
%token PROC "proc"
%token CALL "call"
%token EQ "="
%token OPENPARA "("
%token CLOSEPARA ")"
%token COMMA ","
%token NOT "!"
%token ADD "+"
%token SUBTACT "-"
%token MULTIPLY "*"
%token TRUE "true"
%token FALSE "false"
%token INTTYPE "int"
%token BOOLTYPE "bool"
%token SET "set"
%token BEGINSTATE "begin"
%token ENDSTATE "end"
%token ARROW "->"

%%
input: 	/* empty line */
	| input line
;

line:	'\n'
	| exp		{ resultExp = $1;
			      YYACCEPT }
;

exp:	NUM		{ $$ = new IntExpression($1, @1.text); }
		| TRUE	{ $$ = new BoolExpression(true, @1.text); }
		| FALSE { $$ = new BoolExpression(false, @1.text); }
		| IDENTIFIER
				{ $$ = new VarExpression($1, @1.text); }
		| IF exp THEN exp ELSE exp
				{ string text = (string)@1.text + " " + (string)@2.text + " "
								+ (string)@3.text + " " + (string)@4.text + " "
								+ (string)@5.text + " " + (string)@6.text;
				  $$ = new IfExpression($<expr>2, $<expr>4, $<expr>6, text); }
		| LET IDENTIFIER EQ exp IN exp
				{ string text = (string)@1.text + " " + (string)@2.text + " " 
								+ (string)@3.text + " " + (string)@4.text + " "
								+ (string)@5.text + " " + (string)@6.text;
				  $$ = new LetExpression($2, $<expr>4, $<expr>6, text); }
		| primitive "(" listOfExps ")"
				{ string text = (string)@1.text + " " + (string)@2.text + " "
								+ *($3.listText) + " " + (string)@4.text;
				  $$ = new PrimExpression($1, $3, text); }
		| PROC "(" listOfArgs ")" exp
				{ string text = (string)@1.text + " " + (string)@2.text + " "
								+ *($3.listText) + " " + (string)@4.text + " "
								+ (string)@5.text;
				  $$ = new ProcExpression($3, $5, text); }
		| CALL exp "(" listOfExps ")"
				{ string text = (string)@1.text + " " + (string)@2.text + " "
								+ (string)@3.text + " " + *($4.listText) + " "
								+ (string)@5.text;
				  $$ = new CallExpression($<expr>2, $4, text); }
		| SET IDENTIFIER "=" exp
				{ string text = (string)@1.text + " " + (string)@2.text + " "
								+ (string)@3.text + " " +
								(string)@4.text;
				  $$ = new SetVarExpression($2, $<expr>4, text); }
		| BEGINSTATE listOfExps ENDSTATE
				{ string text = (string)@1.text + " " + *($2.listText) + " "
								+ (string)@3.text;
				  $$ = new BeginExpression($2, text); }
;

listOfExps: exp	{ ExpsList l;
				  l.exps = new vector<Expression*>;
				  l.exps->push_back($<expr>1);
				  l.listText = new string(@1.text);
				  $$ = l;
				 }
		| listOfExps "," exp
				{ ExpsList l = $1;
				  l.exps->push_back($<expr>3);
				  *(l.listText) += ", " + (string)@3.text;
				  $$ = l; }
;

listOfArgs:	varType IDENTIFIER { ArgsList a;
								 a.types = new vector<Type*>;
								 a.types->push_back($1);
								 a.args = new vector<string>;
								 a.args->push_back($2);
								 a.listText = new string($1->print() + " " + (string)@2.text);
								 $$ = a;
							   }
			| listOfArgs "," varType IDENTIFIER
					  	{ ArgsList a = $1;
					  	  a.types->push_back($3);
						  a.args->push_back($4);
						  *(a.listText) += ", " + $3->print() + " " + (string)@4.text;
						  $$ = a;
						}
;

primitive:	"+"	{ $$ = PrimExpression::Add; }
			| "*" { $$ = PrimExpression::Multiply; }
			| "-" { $$ = PrimExpression::Subtract; }
			| "=" { $$ = PrimExpression::Equal; }
;

varType:	INTTYPE	{ $$ = new IntType; }
			| BOOLTYPE { $$ = new BoolType; }
			| "[" listOfTypes "-" varType "]"
				{ ClosureType *c = new ClosureType ( $4, $2->size());
				  vector<Type*>::iterator it = $2->begin();
				  for (int i = 0; i < (int)$2->size(); ++i)
				  	{
				  	   c->setNthArgType(i, *it);
				  	   it++;
				  	}
				  $$ = c;
				 }
;
listOfTypes: varType { vector<Type*> *l = new vector<Type*>;
				  l->push_back($1);
				  $$ = l;
				 }
		| listOfTypes "*" varType
				{ $1->push_back($3);
				  $$ = $1; }
;
%%

int yyerror (char *s)  /* Called by yyparse on error */
{
  printf ("%s\n", s);
  return 0;
}

int lookupTokens(char *s)
{
	for (int i = 0; i < YYNTOKENS; i++)
	{
//		printf("Match attempt between '%s' and '%s' (item %d)\n", s, yytname[i],i);
		if (yytname[i] != 0
			&& yytname[i][0] == '"'
			&& strncmp (yytname[i] + 1, s, strlen (s)) == 0
#ifdef EXTRA_BACK_SLASH
			&& yytname[i][strlen (s) + 1] == '\\'
			&& yytname[i][strlen (s) + 2] == '"'
			&& yytname[i][strlen (s) + 3] == 0)
#else
			&& yytname[i][strlen (s) + 1] == '"'
			&& yytname[i][strlen (s) + 2] == 0)
#endif			
		{
//			printf("Matched '%s' to '%s' which has value %d\n", s, yytname[i], i); 
			return yytoknum[i];
		}
	}
	return 0;
}
	