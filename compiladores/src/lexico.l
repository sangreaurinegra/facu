%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "sintactico.tab.h"

#define DEBUG2
 
#ifdef DEBUG
#define printf_debug printf
#else
#define printf_debug while(0)printf
#endif
 
#define YY_LOCATION yylloc.first_line = yylloc.last_line = yylineno;

int columna;
int fila = 1;
extern YYLTYPE yylloc;
%}

%option noyywrap
%option yylineno

INT	[0-9]+
REAL	{INT}("."{INT})
IDENTIFICADOR [a-zA-Z][a-zA-Z0-9_]*

WHITESPACE [ \t]
NEWLINE	[\n]


%x COMENTARIO1
%x COMENTARIO2
%x STR
%%


"if" 		{YY_LOCATION;columna =+ yyleng;printf_debug("if\n");
			return (IF);} 
"then" 		{YY_LOCATION;columna =+ yyleng;printf_debug("then\n");
			return (THEN);}
"else" 		{YY_LOCATION;columna =+ yyleng;
			return (ELSE);}
"end" 		{YY_LOCATION;columna =+ yyleng;printf_debug("end\n");
				return (END);}
"for" 		{YY_LOCATION;columna =+ yyleng;printf_debug("for\n");
				return (FOR);}
"in" 		{YY_LOCATION;columna =+ yyleng;
				return (IN);}

"function" 	{YY_LOCATION;columna =+ yyleng;printf_debug("function\n");
				return (FUNCION); }
"local" 	{YY_LOCATION;columna =+ yyleng;printf_debug("local\n");
				return (LOCAL); }
"do" 		{YY_LOCATION;columna =+ yyleng;printf_debug("do\n");
				return (DO); }
"return" 	{YY_LOCATION;columna =+ yyleng;
				return (RETURN); } 

";" 		{YY_LOCATION;columna =+ yyleng;
				return (PTOCOMA); }
"=" 		{YY_LOCATION;columna =+ yyleng;printf_debug("asignacion\n"); yylval.stringType =strdup(yytext);
				return (ASIGNACION); }
"," 		{YY_LOCATION;columna =+ yyleng;printf_debug("coma\n");
				return (COMA); }
"[" 		{YY_LOCATION;columna =+ yyleng; printf_debug("paRECizq\n");
				return (PARECIZQ); }
"]" 		{YY_LOCATION;columna =+ yyleng; printf_debug("paRECizq\n");
				return (PARECDER); }
"(" 		{YY_LOCATION;columna =+ yyleng;printf_debug("pacuizq\n");
				return (PACURIZQ); }
")" 		{YY_LOCATION;columna =+ yyleng;printf_debug("pacuder\n");
				return (PACURDER); }
"{" 		{YY_LOCATION;columna =+ yyleng;printf_debug("llaveizq\n");
				return (LLAVEIZQ); }
"}" 		{YY_LOCATION;columna =+ yyleng;printf_debug("llaveder\n");
				return (LLAVEDER); }

"nil" 		{ yylval.nilType = 0;
 		  YY_LOCATION;columna =+ yyleng; 
		  return (NIL); }
"false" 	{ yylval.boolType = false;
		  YY_LOCATION;columna =+ yyleng; 
		  return (FALSE); }
"true" 		{ yylval.boolType = true;
		  YY_LOCATION;columna =+ yyleng; 
		  return (TRUE); }

"." 		{YY_LOCATION;columna =+ yyleng;printf_debug("punto\n");
				return (PUNTO); }

"+" {YY_LOCATION;columna =+ yyleng; yylval.stringType =strdup(yytext);
		return (MAS); }
"-" {YY_LOCATION;columna =+ yyleng; yylval.stringType =strdup(yytext);
		return (MENOS); }
"*" {YY_LOCATION;columna =+ yyleng;
		return (MUL); }
"/" {YY_LOCATION;columna =+ yyleng;printf_debug("div\n");
		return (DIV); }
"^" {YY_LOCATION;columna =+ yyleng;
		return (POTENCIA); }
"%" {YY_LOCATION;columna =+ yyleng;printf_debug("modulo\n"); 
		return (MODULO); }
".." {YY_LOCATION;columna =+ yyleng; 
		return (CONCATENACION); }
"<" {YY_LOCATION;columna =+ yyleng; 
		return (MENOR); }
">" {YY_LOCATION;columna =+ yyleng; 
		return (MAYOR); }
"<=" {YY_LOCATION;columna =+ yyleng; 
		return (MENORIGUAL); }
">=" {YY_LOCATION;columna =+ yyleng; 
		return (MAYORIGUAL); }
"==" {YY_LOCATION;columna =+ yyleng;printf_debug("igual\n"); 
		return (IGUAL); }
"~=" {YY_LOCATION;columna =+ yyleng;printf_debug("distinto\n"); 
		return (DISTINTO); }
"and" {YY_LOCATION;columna =+ yyleng; 
		return (AND); }
"or" {YY_LOCATION;columna =+ yyleng; 
		return (OR); }
"not" {YY_LOCATION;columna =+ yyleng; 
		return (NOT); }
"#" {YY_LOCATION;columna =+ yyleng; 
		return (LARGO); }

"pairs" {YY_LOCATION;columna =+ yyleng; 
		return (PAIRS); }

{INT}+		{ yylval.intNum = atoi(yytext);
                  YY_LOCATION;
		 printf_debug("nombre\n"); printf_debug("int\n");
		 return INT; }

{REAL}+		{ yylval.floatNum = atof(yytext);
		 printf_debug("real\n");
                YY_LOCATION;
		 return REAL; }

"\""	        BEGIN(STR);
<STR>[^\"]*	{yylval.stringType = strdup(yytext);  
		return STRING;}
<STR>"\""	{BEGIN(INITIAL);}


{WHITESPACE}+	{ columna =+ yyleng; }
{NEWLINE}+	{ printf_debug("new line\n");   
		  columna = 0; 
		  fila++; }

{IDENTIFICADOR}+ {	yylval.stringType = strdup(yytext);printf_debug("nombre: %s\n", yytext);
                        YY_LOCATION;
			return NOMBRE;}


"--[[--"                {BEGIN(COMENTARIO1);}
<COMENTARIO1>{NEWLINE}  {columna = 0; fila++;}
<COMENTARIO1>.      
<COMENTARIO1>"--]]--"   {BEGIN(INITIAL);}

"--"         		    {BEGIN(COMENTARIO2);}
<COMENTARIO2>. 	    
<COMENTARIO2>{NEWLINE}  {BEGIN(INITIAL);columna = 0; fila++;}     
     
     
[ \t\n]+       {} /* quitamos espacios en blanco y saltos de linea */				
				
. { printf("Error lexico en linea %d columna %d cerca de '%s' \n ",yylineno,columna,yytext);return(yytext[0]);} 


