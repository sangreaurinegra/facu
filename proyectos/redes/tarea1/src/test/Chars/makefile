CXXFLAGS =	-O2 -g -Wall -fmessage-length=0

OBJS =		testChar.o ../../logger.o

LIBS =

TARGET =	testChar

$(TARGET):	$(OBJS)
	$(CXX) -o $(TARGET) $(OBJS) $(LIBS)

all:	$(TARGET)

clean:
	rm -f $(OBJS) $(TARGET)


