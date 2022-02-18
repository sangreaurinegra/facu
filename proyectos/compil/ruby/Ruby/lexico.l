%{
   #include <stdio.h>
	#include "nodo.h"
   #include "gram.hpp"
%}
%option noyywrap
%option yylineno

%x comentario
%x comment
%x EString
%x EString2
%x EString3
%x salto

DIGITO [0-9]
ID [a-z_][a-zA-Z0-9_]*
TAB [ \t]+
%{
char string_buf[1000];
char *string_buf_ptr;

%}

%%

[0-9]+\.[0-9]*         {yylval.real = atof(yytext);return Token_DOUBLE;}
[0-9]+                 {yylval.entero = atoi(yytext);return Token_INTEGER;}
{TAB}		{/*omitirlo*/} //NO ANDAAAAAA
[\n]+		{return(Token_SALTO);}

"=begin"         BEGIN(comment);
<comment>\n
<comment>[^\n]*
<comment>\n
<comment>"=end\n"       BEGIN(INITIAL);

"#"			BEGIN(comentario);
<comentario>[^\n]*
<comentario>\n		BEGIN(INITIAL);{return(Token_SALTO);}


\"				{string_buf_ptr = string_buf;
				BEGIN(EString);}

<EString>\" 	{BEGIN(INITIAL);
				*string_buf_ptr = '\0';
				int i=0;
				while ( string_buf[i] != '\0' ){
						yylval.str[i] = string_buf[i];
						i++;
				}
				yylval.str[i] = string_buf[i]; //asigno \0
				return(Token_STRING);
				}

<EString>\\\n	 {*string_buf_ptr++ = ' ';}

<EString>\\n	 	{*string_buf_ptr++ = '\n';}

<EString>\\\'	 {*string_buf_ptr++ = '\'';}

<EString>[^\\\n\"]+ 	{char *yptr = yytext;
						while ( *yptr )
							*string_buf_ptr++ = *yptr++;
						}

\'				{string_buf_ptr = string_buf;
				BEGIN(EString2);}

<EString2>\' 	{BEGIN(INITIAL);
				*string_buf_ptr = '\0';
				int i=0;
				while ( string_buf[i] != '\0' ){
						yylval.str[i] = string_buf[i];
						i++;
				}
				yylval.str[i] = string_buf[i]; //asigno \0
				return(Token_STRING);
				}

<EString2>\\\n	 {*string_buf_ptr++ = ' ';}
<EString2>\\n	 	{*string_buf_ptr++ = '\n';}

<EString2>\\\'	 {*string_buf_ptr++ = '\'';}

<EString2>[^\\\n\']+ 	{char *yptr = yytext;
						while ( *yptr )
							*string_buf_ptr++ = *yptr++;
						}

\`				{string_buf_ptr = string_buf;
				BEGIN(EString3);}

<EString3>\` 	{BEGIN(INITIAL);
				*string_buf_ptr = '\0';
				int i=0;
				while ( string_buf[i] != '\0' ){
						yylval.str[i] = string_buf[i];
						i++;
				}
				yylval.str[i] = string_buf[i]; //asigno \0
				return(Token_STRING_SO);
				}

<EString3>[^\`]		{char *yptr = yytext;
					while ( *yptr )
						*string_buf_ptr++ = *yptr++;
					}


"\\\n"			{printf("escape");}


":"				{return(Token_DOSPUNTOS);}
"nil"			{return(Token_nil);}
"case"    		{return(Token_case);}
"when"    		{return(Token_when);}
"class"    		{return(Token_class);}
"def"   		{return(Token_def);}
"do"			{return(Token_do);}
"end"			{return(Token_end);}
"["				{return(Token_RECTO_LEFT);}
"]"				{return(Token_RECTO_RIGHT);}
"!"				{return(Token_Exclamacion);}
"~"				{return(Token_Complemento);}	
"+"				{return(Token_Mas);}
"/"				{return(Token_Division);}
"*"				{return(Token_Multiplicacion);}
"%"				{return(Token_Mod);}
"-"				{return(Token_Menos);}
">="			{return(Token_Mayor_Igual);}
"<="			{return(Token_Menor_Igual);}
">"				{return(Token_Mayor);}
"<"				{return(Token_Menor);}
"==="			{return(Token_Triple_igual);}
"<=>"			{return(Token_Comparacion);}
"!="			{return(Token_Distinto);}	
"=="			{return(Token_Igual_Igual);}	
"("				{return(Token_PARENT_LEFT);}
")"				{return(Token_PARENT_RIGHT);}
"?"				{return(Token_Pregunta);}	
"&&"			{return(Token_And_Booleano);}
"||"			{return(Token_Or_Booleano);}
"return"		{return(Token_return);}	
"**"			{return(Token_Potencia);}
"="				{return(Token_Asignacion);}
"%="			{return(Token_Mod_Asignacion);}
"/="			{return(Token_Div_Asignacion);}
"+="			{return(Token_Mas_Asignacion);}
"&="			{return(Token_And_Binario_Asignacion);}
"|="			{return(Token_Or_Binario_Asignacion);}
"<<="			{return(Token_ShiftIzq_Asignacion);}
">>="			{return(Token_ShiftDer_Asignacion);}
"&&="			{return(Token_And_Bool_Asignacion);}
"||="			{return(Token_Or_Bool_Asignacion);}
"*="			{return(Token_Mult_Asignacion);}
"**="			{return(Token_Potencia_Asignacion);}
"^="			{return(Token_Xor_Asignacion);}	
"-="			{return(Token_Menos_Asignacion);}
"while"			{return(Token_while);}	
"{"				{return(Token_LLAVE_LEFT);}
"}"				{return(Token_LLAVE_RIGHT);}	
"if"			{return(Token_if);}
"then" 			{return(Token_then);}
"elsif"			{return(Token_elsif);}
"else"			{return(Token_else);}
"not"			{return(Token_not);}
"and"			{return(Token_and);}
"."				{return(Token_Punto);}
";"				{return(Token_PTOCOMA);}
"|"				{return(Token_Pipe);}
"each"			{return(Token_each);}
"@"				{return(Token_Arroba);}
"$"				{return(Token_Pesos);}
"or"			{return(Token_or);}
"puts"			{return(Token_puts);}
"to_s"			{return(Token_to_s);}
"attr_writer"	{return(Token_attr_writer);}
"attr_accessor"	{return(Token_attr_accessor);}
"attr_reader"	{return(Token_attr_reader);}
"gets"			{return(Token_gets);}
"instance_of?"	{return(Token_instance_of);}
"respond_to?"	{return(Token_respond_to);}
"false"			{return(Token_false);}
"true"			{return(Token_true);}
","				{return(Token_COMA);}


{ID}  			{yylval.ident = yytext; /*printf("LEXICO %s\n",yylval.ident);*/return(Token_ID);}

.		{  int numL =  yylineno;  char aux [999]; char msjError[999]="";  char *  linea = itoa (numL, aux, 10);
                    strcat (msjError,"ERROR Lexico en la linea ");
                    strcat (msjError,linea);
					strcat (msjError," : simbolo no esperado ");
					strcat (msjError,yytext);
                    throw msjError;}


%%
