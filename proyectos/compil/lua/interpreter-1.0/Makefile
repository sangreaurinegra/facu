# Makefile for Interpreter
# Written by Alan Macek (May 30, 2001)

# Flags for Bison - allow debugging
BFLAGS=-DYYDEBUG=1

# Common Flags - turn on warnings
#CFLAGS=-Wall

# Extra Flags - turn on debugging code in type checker and/or evaluator
#EFLAGS=-DEVAL_TYPE
#EFLAGS=-DEVAL_EVAL

# Hack Flags - allow for differences between some computers
#HFLAGS=-DEXTRA_BACK_SLASH

FLAGS = $(CFLAGS) $(EFLAGS) $(HFLAGS)

OBJECTS= main.o grammar.o lexical.o parserClasses.o environment.o \
	variable.o values.o types.o
EXE= main

default: $(EXE)

main: $(OBJECTS)
	g++ $(FLAGS) -o main $(OBJECTS)

main.o: main.cpp parserClasses.h environment.h
	g++ $(FLAGS) -c main.cpp

parserClasses.o: parserClasses.cpp parserClasses.h environment.h
	g++ $(FLAGS) -c parserClasses.cpp

environment.o: environment.cpp environment.h variable.h
	g++ $(FLAGS) -c environment.cpp

values.o: values.cpp values.h types.h
	g++ $(FLAGS) -c values.cpp

variable.o: variable.cpp variable.h values.h
	g++ $(FLAGS) -c variable.cpp

grammar.o: grammar.cpp
	g++ $(FLAGS) $(BFLAGS) -c grammar.cpp

lexical.o: lexical.cpp
	g++ $(FLAGS) -c lexical.cpp

types.o: types.cpp types.h
	g++ $(FLAGS) -c types.cpp

#Generate Scanner
lexical.cpp: lexical.y grammar.cpp
	flex -I -olexical.cpp lexical.y

#Generate Parser
grammar.cpp: grammar.y
	bison -d -o grammar.c -t grammar.y
	#Hack to force the .cpp extension rather than .c for the grammar file
	mv grammar.c grammar.cpp

clean:
	rm *.o grammar.cpp grammar.h lexical.cpp main
