CXXFLAGS =	-O2 -g -Wall -fmessage-length=0

OBJS =		testParser.o  ../../parserLib.o   ../../timeLib.o

LIBS =

TARGET =	testParser

$(TARGET):	$(OBJS)
	$(CXX) -o $(TARGET) $(OBJS) $(LIBS)

all:	$(TARGET)

clean:
	rm -f $(OBJS) $(TARGET)


