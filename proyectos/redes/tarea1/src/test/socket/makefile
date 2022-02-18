CXXFLAGS =	-O2 -g -Wall -fmessage-length=0

OBJS =		testSocketLibWeb1.o ../../logger.o ../../socketLib.o

LIBS =

TARGET =	testSocketLibWeb1

$(TARGET):	$(OBJS)
	$(CXX) -o $(TARGET) $(OBJS) $(LIBS)

all:	$(TARGET)

clean:
	rm -f $(OBJS) $(TARGET)


