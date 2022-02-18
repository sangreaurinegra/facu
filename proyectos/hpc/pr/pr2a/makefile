FLAGS = -lcrypt $(CFLAGS) $(EFLAGS) $(HFLAGS) 

OBJECTS= pr2a.o 
EXE= pr2a

default: $(EXE)

pr2a: $(OBJECTS)
	g++ -o $(EXE) $(OBJECTS) $(FLAGS) 

pr2a.o: pr2a.cpp
	g++ -c pr2a.cpp $(FLAGS)


clean:
	rm *.o 
