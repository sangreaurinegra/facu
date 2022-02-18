FLAGS = -lcrypt $(CFLAGS) $(EFLAGS) $(HFLAGS) 

OBJECTS= pr2.o 
EXE= pr2

default: $(EXE)

pr2: $(OBJECTS)
	gcc -o $(EXE) $(OBJECTS) $(FLAGS) 

pr2.o: pr2.c
	gcc $(FLAGS) -c pr2.c


clean:
	rm *.o 
