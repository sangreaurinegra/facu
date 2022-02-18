all: enviaFile recibeFile

recibeFile: recibeFile.cc pct.o pctUtils.o goBackN.o logger.o stringLib.o buffer.o
	g++ -pthread -o recibeFile recibeFile.cc pct.o pctUtils.o goBackN.o logger.o stringLib.o buffer.o

enviaFile: enviaFile.cc pct.o pctUtils.o goBackN.o logger.o stringLib.o buffer.o
	g++ -pthread -o enviaFile enviaFile.cc pct.o pctUtils.o goBackN.o logger.o stringLib.o buffer.o
	

clean:
	rm -f *.o
	rm -f enviaFile
	rm -f recibeFile

