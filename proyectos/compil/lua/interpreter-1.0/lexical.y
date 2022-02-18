/* Scanner for simple language */

%{
/*
Author:	Alan Macek
Date:	May 30, 2001

Terms of use: Please send me an email <al@alanmacek.com> if you are using this
		file or modifying it to let me know how it works for you.
*/

#include "parserClasses.h"
#include "grammar.h"

int lookupTokens(char *s);
int colNum;
int rowNum;
extern YYLTYPE yylloc;
%}

%option always-interactive
%option noyywrap

DIGIT	[0-9]
SINGLECHAR [!+*\-=\(\),\[\]]
WHITESPACE [ \t]
NEWLINE	[\n\r]
ANYCHAR [^0-9!+*\-=\(\),\[\] \t\n]
%%

{DIGIT}+		{ /* printf("Parsed Digit with value %d\n", atoi(yytext)); */
					yylval.val = atoi(yytext);
					yylloc.text = strdup(yytext);
					yylloc.first_line = yylloc.last_line = rowNum;
					yylloc.first_column = colNum;
					yylloc.last_column = colNum + yyleng;
					colNum =+ yyleng;
		  			return NUM; }
{WHITESPACE}+	{ 	colNum =+ yyleng;  }
{NEWLINE}+		{   colNum = 0;
					rowNum++;  }
{SINGLECHAR}	|
{ANYCHAR}+ 		{
					yylloc.text = strdup(yytext);
					yylloc.first_line = yylloc.last_line = rowNum;
					yylloc.first_column = colNum;
					yylloc.last_column = colNum + yyleng;
					colNum =+ yyleng;
					int t = lookupTokens(yytext);
					if (t != 0)
						return t;
       				yylval.id = strdup(yytext);
       				/* printf("Found Identifier '%s'\n", yylval.id); */
       				return IDENTIFIER;
       			}

%%
