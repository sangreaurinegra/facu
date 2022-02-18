FLAGS =  $(CFLAGS) $(EFLAGS) $(HFLAGS) 

OBJECTS= rsa.o 
EXE= rsa

default: $(EXE)

rsa: $(OBJECTS)
	g++ -o $(EXE) $(OBJECTS) $(FLAGS) 

rsa.o: rsa.cpp
	g++ -c rsa.cpp $(FLAGS)


clean:
	rm *.o 
