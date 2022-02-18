%{

#include <stdio.h>
#include <math.h>
#include <vector>
#include <string>
#include "nodo.h"
using namespace std;

Block * program;

extern int yylex();
extern int yylineno;
extern int yytext;

void yyerror (char *s);

%}

%union

{
   class Statement* stmt;
   class Block* bloqueP;
   class Expresion* expr;
   class Accesor *acc;
   class DefArgs* lArgs;
   class Variable* izq;
   class String* stringId;
   class ExpresionStatement* nExprStat;
   class CallArgs* lArgsCall;
   class Identificador* NIdent;
   class ListaExpresiones* ListaExp;
   class ListaAccesores *ListaAcc;
   class CallFunctionConParametros * callFun;
   class BloqueFuncion* bloqueFun;
   class Operador* oper;
   class BloqueClase* bloqueClass;
   class FuncionDeclaracionClase* funClass;



   float real;
   int entero;
   char str[1000];
   int token;
   char* ident;

}

//%error-verbose
%start AnalisisSintactico

%token <token> Token_if
%token <token> Token_else
%token <token> Token_while
%token <token> Token_case
%token <token> Token_when
%token <token> Token_Mas
%token <token> Token_Menos
%token <token> Token_Multiplicacion
%token <token> Token_SALTO
%token <token> Token_Division
%token <token> Token_Mod
%token <token> Token_Comparacion
%token <token> Token_end
%token <token> Token_Mayor
%token <token> Token_Mayor_Igual
%token <token> Token_Menor
%token <token> Token_Igual_Igual
%token <token> Token_Triple_igual
%token <token> Token_then
%token <token> Token_Potencia
%token <token> Token_Exclamacion
%token <token> Token_Or_Booleano
%token <token> Token_And_Booleano
%token <token> Token_and
%token <token> Token_Asignacion
%token <token> Token_or
%token <token> Token_And_Bool_Asignacion
%token <token> Token_RECTO_RIGHT
%token <token> Token_RECTO_LEFT
%token <token> Token_Distinto
%token <token> Token_Or_Binario_Asignacion
%token <token> Token_Punto
%token <token> Token_ID
%token <token> Token_not
%token <token> Token_Mult_Asignacion
%token <token> Token_Mas_Asignacion
%token <token> Token_Mod_Asignacion
%token <token> Token_Menos_Asignacion
%token <token> Token_And_Binario_Asignacion
%token <token> Token_ShiftDer_Asignacion
%token <token> Token_Div_Asignacion
%token <token> Token_Potencia_Asignacion
%token <token> Token_Or_Bool_Asignacion

%token <token> Token_Xor_Asignacion
%token <token> <str>Token_STRING_SO
%token <token> Token_ShiftIzq_Asignacion
%token <token> Token_Arroba
%token <token> Token_Pesos
%token <token> Token_PTOCOMA
%token <token> Token_elsif
%token <token> <real>T_NUM
%token <token> Token_Menor_Igual
%token <token> <str>Token_STRING

%token <token> Token_def
%token <token> Token_PARENT_LEFT
%token <token> Token_PARENT_RIGHT
%token <token> Token_COMA
%token <token> Token_do
%token <token> Token_Pipe
%token <token> Token_class
%token <token> Token_LLAVE_LEFT
%token <token> Token_LLAVE_RIGHT
%token <token> Token_return
%token <token> Token_nil
%token <token> Token_DOSPUNTOS
%token <token> Token_puts
%token <token> Token_gets
%token <token> Token_to_s
%token <token> Token_new
%token <token> Token_respond_to
%token <token> Token_instance_of
%token <token> Token_attr_reader
%token <token> Token_attr_writer
%token <token> Token_attr_accessor
%token <token>  Token_Pregunta
%token <token> Token_Complemento
%token <token>  Token_each
%token <token> Token_true
%token <token> Token_false
%token <token> Token_DOUBLE
%token <token>  Token_INTEGER


//Asociatividades
%nonassoc Token_puts
%left Token_or Token_and 			//or and			Composición lógica
%left Token_not 					//not	Negación lógica
%left Token_Pregunta
%right Token_Asignacion Token_Mod_Asignacion  Token_Div_Asignacion Token_Mas_Asignacion Token_Or_Bool_Asignacion Token_And_Bool_Asignacion Token_ShiftIzq_Asignacion Token_SHIFTAUX_EXP Token_Mult_Asignacion Token_And_Binario_Asignacion Token_Or_Binario_Asignacion Token_EXPToken_EXP_ASIGN //= %= ~= /= = += |= &= >>= <<= *= &&= ||= **=	Asignación
%left Token_Or_Booleano 				//ll	Or lógico
%left Token_And_Booleano			//&&	And lógico
%nonassoc Token_Comparacion Token_Igual_Igual Token_Triple_igual Token_Distinto	//<=> == === != =~ !~	Operadores de igualdad y coincidencia de patrones
%nonassoc Token_Menor_Igual Token_Menor Token_Mayor Token_Mayor_Igual 		//<= < > >=	Operadores de comparación
%left Token_Mas Token_Menos
%left Token_Multiplicacion Token_Division Token_Mod			//* / %Multiplicación, división, módulo
%left Token_Exclamacion Token_Complemento MENOSU MASU		//! ~ + -		Not, complemento, más y menos unarios
%left Token_Potencia										//**		Exponente


%type <bloqueP> AnalisisSintactico PROG LISTA_SENTENCIAS OPT_ELSE   /*AUX_ELSE_IF*/ BLOQUE
%type <stmt> SENTENCIAS FUNCION_CLASE
%type <expr> EXP LITERAL VAR FUNCION AUX_ARREGLO
%type <acc>  ACCESOR
%type <lArgs> LISTA_ARGSDEF
%type <izq>  AUX_EXP
%type <NIdent> AUX_ID
%type <nExprStat>  AUX_RETURN
%type <lArgsCall>  LISTA_ARGUMENTOS
%type <ListaExp>  COLECCION_EXP AUX_COLECCION_EXP
%type <ListaAcc>   LISTA_ID_ACCESORES
%type <callFun> AUX_FUNCION
%type <bloqueFun> LISTA_SENTENCIAS_FUNCION
%type <oper> OP_ASGN
%type <bloqueClass> LISTA_CLASE


%%

AnalisisSintactico: PROG  						{ program = $1;}

PROG: AUX_SALTO LISTA_SENTENCIAS  AUX_SALTO		{$$ = $2; }

AUX_SALTO :          		 {}
			|Token_SALTO  	 {}

LISTA_SENTENCIAS : 										{$$ = new Block();   }
                | LISTA_SENTENCIAS	 SENTENCIAS TERM 	{ $1->statements.push_back($<stmt>2);  }

LISTA_SENTENCIAS_FUNCION : 									{$$ = new BloqueFuncion();  }
                | LISTA_SENTENCIAS_FUNCION  SENTENCIAS TERM { $1->statements.push_back($<stmt>2);  }

SENTENCIAS : EXP        														{$$ = new ExpresionStatement($1); }
			| Token_while 	EXP		TERM 	LISTA_SENTENCIAS  Token_end	 		{$$ = new WhileStatment(*$2,*$4); }
			| Token_if 	  EXP	TERM   LISTA_SENTENCIAS OPT_ELSE  Token_end		{$$ = new IfStatment(*$2,*$4,*$5); }		
			| Token_def   AUX_ID 	TERM  	LISTA_SENTENCIAS_FUNCION   AUX_RETURN	Token_end {$$ = new FuncionDeclaracion(*$2,*$4,*$5);}	
			| Token_def  AUX_ID  Token_PARENT_LEFT 	LISTA_ARGSDEF 	Token_PARENT_RIGHT 	TERM   LISTA_SENTENCIAS_FUNCION  AUX_RETURN  Token_end 	{$$ = new FuncionDeclaracionConParametros(*$2,*$4,*$7,*$8); }
			| Token_class 	AUX_ID 	TERM 	LISTA_CLASE  	Token_end         	{$$ = new ClassStatment(*$2,*$4); }
			| EXP	Token_Pregunta	EXP  Token_DOSPUNTOS EXP				  	{}	
			| BLOQUE 															{$$=$1; }

AUX_ID : Token_ID {$$ = new Identificador(yylval.ident);}

LISTA_ARGSDEF : Token_ID							{$$= new DefArgs(); $$->args.push_back(new Argumento(yylval.ident));}
			| LISTA_ARGSDEF Token_COMA Token_ID    	{$1->args.push_back(new Argumento(yylval.ident)); }


AUX_RETURN : 							{$$ = new ExpresionStatement(); }
			| Token_return EXP TERM		{$$ = new ExpresionStatement($2); }


ACCESOR : Token_attr_reader Token_DOSPUNTOS LISTA_ID_ACCESORES TERM				{$$ = new Accesor ("attr_reader",*$3);}
			 | Token_attr_writer Token_DOSPUNTOS LISTA_ID_ACCESORES TERM		{$$ = new Accesor ("attr_writer",*$3);}
			 | Token_attr_accessor Token_DOSPUNTOS LISTA_ID_ACCESORES TERM		{$$ = new Accesor ("attr_accessor",*$3);}


LISTA_ID_ACCESORES : Token_ID													{$$= new ListaAccesores(); $$->args.push_back(new Argumento(yylval.ident)); }
					|LISTA_ID_ACCESORES  Token_COMA Token_DOSPUNTOS  Token_ID 	{$1->args.push_back(new Argumento(yylval.ident)); }

LISTA_CLASE:									{$$ = new BloqueClase();}
			| LISTA_CLASE FUNCION_CLASE TERM 	{$1->statements.push_back($<stmt>2);}
			| LISTA_CLASE ACCESOR  				{$1->statements.push_back($<stmt>2);}

FUNCION_CLASE :  Token_def   AUX_ID 	TERM  	LISTA_SENTENCIAS_FUNCION   AUX_RETURN	Token_end {$$ = new FuncionDeclaracionClase(*$2,*$4,*$5);}
			|  Token_def  AUX_ID  Token_PARENT_LEFT  LISTA_ARGSDEF 	Token_PARENT_RIGHT 	TERM   LISTA_SENTENCIAS_FUNCION  AUX_RETURN  Token_end 	{$$ = new FuncionDeclaracionClaseConParametros(*$2,*$4,*$7,*$8); }



BLOQUE	: Token_do				TERM	LISTA_SENTENCIAS	Token_end     	  {$$ =$3}
		| Token_LLAVE_LEFT		TERM	LISTA_SENTENCIAS 	Token_LLAVE_RIGHT {$$ =$3}



OPT_ELSE :  											{$$ = new Block(); }
			| Token_else  TERM 	LISTA_SENTENCIAS 		{$$ = $3; }



EXP		: AUX_EXP OP_ASGN EXP											{$$ = new Asignacion(*$1,*$2, *$3); }
		| EXP Token_Mas EXP												{$$ = new OperacionBinaria(*$1,"1",*$3); }
		| EXP Token_Menos EXP											{$$ = new OperacionBinaria(*$1,"2",*$3); }
		| EXP Token_Multiplicacion EXP									{$$ = new OperacionBinaria(*$1,"3",*$3); }
		| EXP Token_Division EXP										{$$ = new OperacionBinaria(*$1,"4",*$3); }
		| EXP Token_Mod EXP												{$$ = new OperacionBinaria(*$1,"5",*$3); }
		| EXP Token_Potencia EXP										{$$ = new OperacionBinaria(*$1,"6",*$3); }
		| Token_Mas EXP	%prec MASU										{$$ = new OperadorUnario("7",*$2); }
		| Token_Menos EXP %prec MENOSU									{$$ = new OperadorUnario("8",*$2); }
		| EXP Token_Comparacion EXP										{$$ = new OperacionBinaria(*$1,"9",*$3); }
		| EXP Token_Mayor EXP											{$$ = new OperacionBinaria(*$1,"10",*$3); }
		| EXP Token_Mayor_Igual EXP										{$$ = new OperacionBinaria(*$1,"11",*$3); }
		| EXP Token_Menor EXP        									{$$ = new OperacionBinaria(*$1,"12",*$3); }
		| EXP Token_Menor_Igual EXP										{$$ = new OperacionBinaria(*$1,"13",*$3); }
		| EXP Token_Igual_Igual EXP										{$$ = new OperacionBinaria(*$1,"14",*$3); }
		| EXP Token_Triple_igual EXP									{$$ = new OperacionBinaria(*$1,"15",*$3); }
		| EXP Token_Distinto EXP										{$$ = new OperacionBinaria(*$1,"16",*$3); }
		| Token_Exclamacion EXP											{$$ = new OperadorUnario("17",*$2);  }
		| Token_Complemento EXP											{$$ = new OperadorUnario("18",*$2);  }
		| EXP Token_And_Booleano EXP									{$$ = new OperacionBinaria(*$1,"19",*$3); }		
		| EXP Token_Or_Booleano EXP										{$$ = new OperacionBinaria(*$1,"20",*$3); }
		| EXP Token_and EXP												{$$ = new OperacionBinaria(*$1,"21",*$3); }
		| EXP Token_or EXP												{$$ = new OperacionBinaria(*$1,"22",*$3); }
		| Token_not EXP													{$$ = new OperadorUnario("23",*$2); }
		| AUX_ARREGLO													{$$ =$1; }
		| Token_RECTO_LEFT   AUX_COLECCION_EXP  Token_RECTO_RIGHT 		{$$ = new ArregloDec(*$2);}
		| FUNCION	 													{$$=$1; }
	
		| VAR															{$$=$1;}
		| LITERAL  														{$$=$1; }
		| Token_PARENT_LEFT EXP Token_PARENT_RIGHT 						{$$=$2;}
		| VAR Token_Punto Token_to_s									{}

AUX_EXP	: VAR												{$$=$<izq>1;}
		| VAR   Token_RECTO_LEFT   EXP   Token_RECTO_RIGHT	{$$ = new Variable(new ArregloPosicion($1,*$3)); }
		| VAR   Token_Punto   Token_ID						{$$ = new Variable(new AtributoAsig(*$1,yylval.ident));}

AUX_ARREGLO : VAR Token_RECTO_LEFT   EXP  Token_RECTO_RIGHT  {$$ = new ArregloPosicion($1,*$3); }

AUX_COLECCION_EXP : 				 {$$ = new ListaExpresiones();}
					| COLECCION_EXP  {$$ = $1}

COLECCION_EXP : EXP										{$$ = new ListaExpresiones(); $$->listaExp.push_back($1); }
				| COLECCION_EXP Token_COMA EXP   	  	{$1->listaExp.push_back($3); }

FUNCION: 	AUX_ID  Token_PARENT_LEFT LISTA_ARGUMENTOS Token_PARENT_RIGHT 		{$$ = new CallFunctionConParametros(*$1,$3);}
			| AUX_ID  Token_PARENT_LEFT  Token_PARENT_RIGHT 					{$$ = new CallFunctionConParametros(*$1,NULL);}
			| Token_gets                              							{$$ = new CallFunctionSinParametros("gets");}
			| Token_puts	EXP                     							{$$ = new Puts("puts", *$2);} 
			| Token_puts	Token_STRING_SO										{$$ = new PutsCmd("puts", *(new String(yylval.str)));}
			| VAR 	Token_Punto 	AUX_FUNCION									{$$ = new CallFunctionClass(*$<izq>1,*$3);}


LISTA_ARGUMENTOS: EXP								  {$$ = new CallArgs();$$->args.push_back($1);}
				| LISTA_ARGUMENTOS Token_COMA EXP     {$1->args.push_back($3); }



AUX_FUNCION : 	AUX_ID 															{$$ = new CallFunctionConParametros(*$1,NULL);}
				| AUX_ID  Token_PARENT_LEFT LISTA_ARGUMENTOS Token_PARENT_RIGHT {$$ = new CallFunctionConParametros(*$1,$3);}
				| Token_respond_to 			Token_PARENT_LEFT		Token_STRING		Token_PARENT_RIGHT {}
				| Token_instance_of			Token_ID
				{/*$$ = new CallFunctionConParametros(*$1,$3);printf("AUX_FUNCION 2\n");*/}
				| Token_class				Token_Punto				Token_to_s  {}
				| Token_each  				BLOQUE  							{}


OP_ASGN	 :  Token_Asignacion  			{$$ = new Operador("0"); }
		| Token_Mas_Asignacion 			{$$ = new Operador("1"); }
		| Token_Menos_Asignacion 	    {$$ = new Operador("2");}
		| Token_Mult_Asignacion  		{$$ = new Operador("3"); }
		| Token_Div_Asignacion  		{$$ = new Operador("4"); }
		| Token_Mod_Asignacion  		{$$ = new Operador("5"); }
		| Token_Potencia_Asignacion  	{$$ = new Operador("6");}
		| Token_And_Bool_Asignacion     {$$ = new Operador("7"); }
		| Token_Or_Bool_Asignacion      {$$ = new Operador("8");}
	
		


TERM	: Token_PTOCOMA			{}
		| Token_SALTO			{}

VAR		: Token_ID					{$$ = new Variable(new Identificador(yylval.ident));}
		| Token_Pesos Token_ID      {$$ = new Variable(new IdentificadorGlobal(yylval.ident)); }
		| Token_Arroba   Token_ID   {$$ = new Variable(new Atributo(yylval.ident)); }


LITERAL		: Token_DOUBLE			{$$ = new Float(yylval.real); }
			| Token_INTEGER			{$$ = new Entero(yylval.entero); }
			| Token_STRING      	{$$ = new String(yylval.str); }
			| Token_nil				{$$ = new Boolean(false);}
			| Token_false			{$$ = new Boolean(false);}
			| Token_true			{$$ = new Boolean(true);}

%%

void yyerror(char *s)
{

	int numL =  yylineno;
	char aux [999];
	char msjError[999]="";
	char *  linea = itoa (numL, aux, 10);
	strcat (msjError,"ERROR Sintactico en linea ");
	strcat (msjError,linea);
	strcat (msjError," : No se esperaba ");
	strcat (msjError,(char*)yytext);
	throw msjError;

}


