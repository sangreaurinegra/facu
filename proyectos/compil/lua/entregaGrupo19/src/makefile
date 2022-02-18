FLAGS = $(CFLAGS) $(EFLAGS) $(HFLAGS)

OBJECTS= main.o sintactico.o lexico.o parser.o contexto.o \
	variable.o valor.o tipo.o scope.o
EXE= main

default: $(EXE)

main: $(OBJECTS)
	g++ $(FLAGS) -o main $(OBJECTS)

main.o: main.cpp parser.h contexto.h
	g++ $(FLAGS) -c main.cpp

parser.o: parser.cpp parser.h contexto.h
	g++ $(FLAGS) -c parser.cpp

contexto.o: contexto.cpp contexto.h variable.h scope.h
	g++ $(FLAGS) -c contexto.cpp

valor.o: valor.cpp valor.h tipo.h
	g++ $(FLAGS) -c valor.cpp

variable.o: variable.cpp variable.h valor.h
	g++ $(FLAGS) -c variable.cpp

sintactico.o: sintactico.cpp
	g++ $(FLAGS) $(BFLAGS) -c sintactico.cpp 

lexico.o: lexico.cpp
	g++ $(FLAGS) -c lexico.cpp 

tipo.o: tipo.cpp tipo.h
	g++ $(FLAGS) -c tipo.cpp
	
scope.o: scope.cpp scope.h
	g++ $(FLAGS) -c scope.cpp

#Lexico y Sintactico
lexico.cpp: lexico.l sintactico.cpp
	flex -I -olexico.cpp lexico.l 

sintactico.cpp: sintactico.y
	bison -d -v -o sintactico.tab.c -t sintactico.y
	#cambio la extension a cpp
	mv sintactico.tab.c sintactico.cpp

clean:
	#rm *.o sintactico.cpp sintactico.h lexico.cpp main
	rm *.o sintactico.cpp lexico.cpp main
