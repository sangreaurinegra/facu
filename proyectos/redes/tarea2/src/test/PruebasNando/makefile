all: enviaFile recibeFile


recibeFile: recibeFile.cc ../../pct.o ../../logger.o ../../stringLib.o ../../pctUtils.o ../../buffer.o ../../goBackN.o
	g++ -pthread -o recibeFile recibeFile.cc ../../logger.o ../../pct.o  ../../stringLib.o ../../pctUtils.o ../../buffer.o ../../goBackN.o -g

enviaFile: enviaFile.cc ../../pct.o ../../logger.o ../../stringLib.o ../../pctUtils.o ../../buffer.o ../../goBackN.o
	g++ -pthread -o enviaFile enviaFile.cc ../../logger.o ../../pct.o ../../stringLib.o ../../pctUtils.o ../../buffer.o ../../goBackN.o -g
	 
 
clean:
	rm -f *.o
	rm -f enviaFile
	rm -f recibeFile


#all: enviaFile recibeFile 
#
#
#recibeFile: recibeFile.cc pct.o logger.o stringLib.o
#	g++ -pthread -o recibeFile recibeFile.cc logger.o pct.o  stringLib.o
#
#enviaFile: enviaFile.cc pct.o logger.o stringLib.o
#	g++ -pthread -o enviaFile enviaFile.cc logger.o pct.o stringLib.o
#	
#
#clean:
#	rm -f *.o

