CXXFLAGS =	-O2 -g -Wall -fmessage-length=0

OBJS =		testCache.o  ../../timeLib.o ../../parserLib.o ../../cache.o ../../cacheHandler.o

LIBS =

TARGET =	testCache

$(TARGET):	$(OBJS)
	$(CXX) -o $(TARGET) $(OBJS) $(LIBS)

all:	$(TARGET)

clean:
	rm -f $(OBJS) $(TARGET)


