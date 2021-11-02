//Grupo 19
%{
#include <stdio.h>
#include <string.h>
#include "parser.h"
using namespace std;

extern FILE *yyin;
extern int yylex(void);
extern int yylineno;
extern char* yytext;

int yyerror(char *s);

extern NodoBloque* programa;
#define NDEBUG
#ifdef DEBUG
#define printf_debug printf
#else
#define printf_debug while(0)printf
#endif
%}

%union {
	int intNum;
	bool boolType;
	int nilType;
	float floatNum;
	char* stringType;
	class NodoExp * expType;

	class NodoLlamarFuncion *llamarFuncionType;

   	class NodoSentencia* stmt;
   	class NodoBloque* bloqueType;

    class NodoTable* tableType;
    class NodoListaTable* listaTableType;
    class NodoCampoTable* campoTableType;

	class NodoListaArgumento* listaArgumentoType;
    class NodoListaExp * listaExpType;
    class NodoListaVar * listaVarType;
        
    class NodoFuncionAsignada * funcionAsignadaType;
    
}

%start PROG

%token <intNum> INT
%token <floatNum> REAL
%token <stringType> STRING
%token <stringType> IDENTIFICADOR
%token <stringType> NOMBRE
%token <boolType> FALSE
%token <boolType> TRUE
%token <nilType> NIL


%token IF
%token THEN
%token ELSE
%token END
%token FOR
%token IN

%token PAIRS

%token FUNCION
%token LOCAL
%token DO
%token RETURN

%token PTOCOMA 		
%token COMA  		
%token PARECIZQ 	
%token PARECDER 	
%token PACURIZQ 	
%token PACURDER  	
%token LLAVEIZQ  	
%token LLAVEDER  	

%token PUNTO  		


%left MENOS MAS
%left MUL DIV MODULO
%right POTENCIA ASIGNACION

%left  AND OR NOT

%nonassoc  MENOR MAYOR MENORIGUAL MAYORIGUAL IGUAL DISTINTO

%left  CONCATENACION LARGO

%type <bloqueType>  PROG bloque lista_de_sentencias
%type <expType>    exp 
%type <stmt> sentencia ultima_sentencia  
%type <listaExpType> explist
%type <stringType>  var
%type <llamarFuncionType> llamada_a_func 
%type <listaArgumentoType> lista_de_nombres 
%type <tableType> constructor_de_tabla 
%type <listaTableType> lista_de_campos
%type <campoTableType> campo
%type <listaVarType> varlist
%type <funcionAsignadaType> func

%%

PROG: bloque    {programa = $1;
				printf_debug("S-programa \n");}		
;

bloque : lista_de_sentencias 				{}
	| lista_de_sentencias ultima_sentencia	{$1->sentencias.push_back($<stmt>2);
                                                printf_debug("S-lista_de_sentencias ultima_sentencia\n");}
	| ultima_sentencia {  $$ = new NodoBloque();  $$->sentencias.push_back($<stmt>1); 
			printf_debug("S-ultima_sentencia\n");}
	| {$$ = new NodoBloque();printf_debug("S-bloque - epsilon (programa vacio?) \n");} //epsilon 
;

lista_de_sentencias :	sentencia       {$$ = new NodoBloque();
                                         $$->sentencias.push_back($<stmt>1);
                                        printf_debug("S-lista_de_sentencias:- sentencia\n");}
		| lista_de_sentencias sentencia {$1->sentencias.push_back($<stmt>2);
						  printf_debug("S-lista_de_sentencias:lista_de_sentencias sentencia\n");}
;

sentencia : llamada_a_func {$$ = $<stmt>1;}
	| IF exp THEN bloque END        	    {$$ = new NodoIf(@1.first_line,$2,$4,new NodoBool(false),new NodoBloque());}
	| IF exp THEN bloque ELSE bloque END 	{$$ = new NodoIf(@1.first_line,$2,$4,new NodoBool(true),$6);}
    | FOR NOMBRE ASIGNACION exp COMA exp DO bloque END {$$ = new NodoFor(@1.first_line,$2, $4,$6,new NodoInteger(1),$8);}
	| FOR NOMBRE ASIGNACION exp COMA exp COMA exp DO bloque END {$$ = new NodoFor(@1.first_line,$2, $4,$6,$8,$10);}
	| FOR lista_de_nombres IN PAIRS PACURIZQ exp PACURDER DO bloque END  {$$ = new NodoForIn(@1.first_line,$2,$6,$9); }
	| LOCAL lista_de_nombres                        {$$ = new NodoVariablesLocal(@1.first_line,$2);}
	| LOCAL lista_de_nombres ASIGNACION explist     {$$ = new NodoVariablesLocal(@1.first_line,$2, $4);}
	| varlist ASIGNACION explist                    {$$ = new NodoAsignacion(@1.first_line,$1, $3);}
    | FUNCION NOMBRE PACURIZQ PACURDER bloque END   {$$ = new NodoFuncion(@1.first_line,$2, $5);}
	| FUNCION NOMBRE PACURIZQ lista_de_nombres PACURDER bloque END {$$ = new NodoFuncion(@1.first_line,$2, $6, $4);}
	| NOMBRE PARECIZQ exp PARECDER ASIGNACION exp {$$ = new NodoAsignacionTabla(@1.first_line, $1, $3, $6, ASIG_PARENTESIS);}
    | NOMBRE PUNTO NOMBRE ASIGNACION exp	   {$$ = new NodoAsignacionTabla(@1.first_line, $1, new NodoString($3), $5, ASIG_PUNTO);}
;

varlist : var {printf_debug("S-varlist : var\n");$$ = new NodoListaVar(); $$->variables.push_back($1);}
	| varlist COMA var {printf_debug("S-varlist : varlist COMA var\n"); $1->variables.push_back($3); }
;

var : NOMBRE {printf_debug("S- var NOMBRE : %s\n",$1);}
;

ultima_sentencia : RETURN   {char* ch = new char[1];ch[0]='\0' ;$$ = new NodoReturn(new NodoString(ch));}
		| RETURN exp		{$$ = new NodoReturn($2);}
;

explist : exp	{printf_debug("S-explist: exp\n");
                 $$ = new NodoListaExp(); $$->expresiones.push_back($1);
                 }
	| explist COMA exp {printf_debug("S-explist: explist COMA exp\n"); $1->expresiones.push_back($3);}
;

lista_de_nombres : NOMBRE {$$ = new NodoListaArgumento(); 
                           $$->argumentos.push_back($1);
                           printf_debug("S-lista_de_nombres : NOMBRE\n");}
		| lista_de_nombres COMA NOMBRE {printf_debug("lista_de_nombres: lista_de_nombres COMA NOMBRE\n");
                                                $1->argumentos.push_back($3);}
;

exp :  NIL 			{$$ = new NodoNIL($1);}
       | FALSE 			{$$ = new NodoBool($1);}
       | TRUE 			{$$ = new NodoBool($1);}
       | INT 			{$$ = new NodoInteger($1);}
       | REAL 			{$$ = new NodoReal($1);}
       | STRING 		{$$ = new NodoString($1);}
       | constructor_de_tabla   {$$=$1;}
       | func                   {$$ = $1;printf_debug("S-exp: func\n");}
       | exp MAS exp 		{$$ = new NodoOperacionBinaria(@1.first_line,(EnumOperacionBinaria)mas,$1,$3);}
       | exp MENOS exp 		{$$ = new NodoOperacionBinaria(@1.first_line,(EnumOperacionBinaria)menos,$1,$3);}
       | exp MUL exp 		{$$ = new NodoOperacionBinaria(@1.first_line,(EnumOperacionBinaria)mul,$1,$3);}
       | exp DIV exp 		{$$ = new NodoOperacionBinaria(@1.first_line,(EnumOperacionBinaria)ediv,$1,$3);}
       | exp POTENCIA exp 	{$$ = new NodoOperacionBinaria(@1.first_line,(EnumOperacionBinaria)potencia,$1,$3);}
       | exp MODULO exp 	{$$ = new NodoOperacionBinaria(@1.first_line,(EnumOperacionBinaria)modulo,$1,$3);}
       | exp CONCATENACION exp  {$$ = new NodoOperacionBinaria(@1.first_line,(EnumOperacionBinaria)concatenacion,$1,$3);}
       | exp MENOR exp 		{$$ = new NodoOperacionBinaria(@1.first_line,(EnumOperacionBinaria)menor,$1,$3);}
       | exp MENORIGUAL exp     {$$ = new NodoOperacionBinaria(@1.first_line,(EnumOperacionBinaria)menorigual,$1,$3);}
       | exp MAYOR exp 		{$$ = new NodoOperacionBinaria(@1.first_line,(EnumOperacionBinaria)mayor,$1,$3);}
       | exp MAYORIGUAL exp     {$$ = new NodoOperacionBinaria(@1.first_line,(EnumOperacionBinaria)mayorigual,$1,$3);}
       | exp IGUAL exp 		{$$ = new NodoOperacionBinaria(@1.first_line,(EnumOperacionBinaria)igual,$1,$3);}
       | exp DISTINTO exp 	{$$ = new NodoOperacionBinaria(@1.first_line,(EnumOperacionBinaria)distinto,$1,$3);}
       | exp AND exp 		{$$ = new NodoOperacionBinaria(@1.first_line,(EnumOperacionBinaria)eand,$1,$3);}
       | exp OR exp 		{$$ = new NodoOperacionBinaria(@1.first_line,(EnumOperacionBinaria)eor,$1,$3);}
       | MENOS exp 		{$$ = new NodoOperacionUnaria(@1.first_line,(EnumOperacionUnaria)menosu,$2);}
       | NOT exp		{$$ = new NodoOperacionUnaria(@1.first_line,(EnumOperacionUnaria)enot,$2);}
       | LARGO exp		{$$ = new NodoOperacionUnaria(@1.first_line,(EnumOperacionUnaria)largo,$2);}
       | PACURIZQ exp PACURDER  {$$=$2;} 
       | llamada_a_func         {$$ = $<expType>1;}
       | var                    {$$ = new NodoVariable($1);} 
       | NOMBRE PARECIZQ exp PARECDER {$$ = new NodoObtenerValorTabla($1, $3);}
	   | NOMBRE PUNTO NOMBRE {$$ = new NodoObtenerValorTabla($1, new NodoString($3));}
;		  
 
llamada_a_func : NOMBRE PACURIZQ PACURDER {$$ = new NodoLlamarFuncion(@1.first_line,$1, new NodoListaExp()); 
                                                printf_debug("llamada_a_func___ nombre %s \n",$1);}
                | NOMBRE PACURIZQ explist PACURDER {$$ = new NodoLlamarFuncion(@1.first_line,$1,$3); 
                                                printf_debug("llamada_a_func___ nombre %s \n",$1);}
                | NOMBRE PUNTO NOMBRE PACURIZQ PACURDER {
                                                char* nombreFunc = $1;
                                                strcat(nombreFunc,"."); 
                                                strcat(nombreFunc,$3);
                                                $$ = new NodoLlamarFuncion(@1.first_line,nombreFunc, new NodoListaExp());}
                | NOMBRE PUNTO NOMBRE PACURIZQ explist PACURDER {
                                                char* nombreFunc = $1;
                                                strcat(nombreFunc,".");
                                                strcat(nombreFunc,$3);
                                                $$ = new NodoLlamarFuncion(@1.first_line,nombreFunc,$5);}
;
					


func : FUNCION PACURIZQ PACURDER bloque END {$$ = new NodoFuncionAsignada($4);}
       | FUNCION PACURIZQ lista_de_nombres PACURDER bloque END {$$ = new NodoFuncionAsignada($5,$3);}
;

constructor_de_tabla :  LLAVEIZQ LLAVEDER {$$ = new NodoTable(*(new NodoListaTable(@1.first_line)));}
			| LLAVEIZQ lista_de_campos LLAVEDER {$$ = new NodoTable(*$2);}
;

lista_de_campos : campo                         {$$ = new NodoListaTable(@1.first_line);
                                                $$->campos.push_back($1);}
		| lista_de_campos COMA campo    {$$->campos.push_back($3);}
;

campo : PARECIZQ exp PARECDER ASIGNACION exp {$$ = new NodoCampoTable($2,$5);}
	| NOMBRE ASIGNACION exp              {$$ = new NodoCampoTable(new NodoString($1),$3);}
	| exp                                {$$ = new NodoCampoTable(new NodoNIL(0),$1);}
;

%%

// lo llama el parse en caso de error
int yyerror (char *s)
{
	printf ("Error Sintactico en la linea %d, cerca de %s \n", yylineno, yytext);

  return 0;
}


