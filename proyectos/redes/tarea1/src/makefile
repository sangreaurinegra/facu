CXXFLAGS =	-O2 -g -Wall -fmessage-length=0 -g

OBJS =	proxyServer.o socketLib.o logger.o admin.o cliente.o cache.o stringLib.o parserLib.o timeLib.o  cacheHandler.o

LIBS =  -lpthread

TARGET =	proxyServer

$(TARGET):	$(OBJS)
	$(CXX) -o $(TARGET) $(OBJS) $(LIBS)

all:	$(TARGET)

clean:
	rm -f $(OBJS) $(TARGET)


