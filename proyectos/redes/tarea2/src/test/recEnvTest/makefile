all: enviaTest recibeTest


recibeTest: recibeTest.cpp ../../pct.o ../../logger.o ../../stringLib.o ../../pctUtils.o ../../buffer.o ../../goBackN.o
	g++ -pthread -o recibeTest recibeTest.cpp ../../logger.o ../../pct.o  ../../stringLib.o ../../pctUtils.o ../../buffer.o ../../goBackN.o -g

enviaTest: enviaTest.cpp ../../pct.o ../../logger.o ../../stringLib.o ../../pctUtils.o ../../buffer.o ../../goBackN.o
	g++ -pthread -o enviaTest enviaTest.cpp ../../logger.o ../../pct.o ../../stringLib.o ../../pctUtils.o ../../buffer.o ../../goBackN.o -g
	 
 
clean:
	rm -f ../../*.o
	rm -f *.o
	rm -f enviaTest
	rm -f recibeTest


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

