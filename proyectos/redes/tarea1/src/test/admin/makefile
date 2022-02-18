CXXFLAGS =	-O2 -g -Wall -fmessage-length=0

OBJS =		testAdmin.o ../../logger.o ../../admin.o ../../stringLib.o ../../socketLib.o

LIBS =

TARGET =	testAdmin

$(TARGET):	$(OBJS)
	$(CXX) -o $(TARGET) $(OBJS) $(LIBS)

all:	$(TARGET)

clean:
	rm -f $(OBJS) $(TARGET)


