all: transmisor receptor


receptor: receptor.cc ../../pct.o ../../logger.o ../../stringLib.o ../../pctUtils.o ../../buffer.o ../../goBackN.o
	g++ -pthread -o receptor receptor.cc ../../logger.o ../../pct.o  ../../stringLib.o ../../pctUtils.o ../../buffer.o ../../goBackN.o -g

transmisor: transmisor.cc ../../pct.o ../../logger.o ../../stringLib.o ../../pctUtils.o ../../buffer.o ../../goBackN.o
	g++ -pthread -o transmisor transmisor.cc ../../logger.o ../../pct.o ../../stringLib.o ../../pctUtils.o ../../buffer.o ../../goBackN.o -g
	 
 
clean:
	rm -f *.o
	rm -f ../../*.o
	rm -f transmisor
	rm -f receptor
