#include "logger.h"

int logLevel = 0; // 0 no log , 1 info, 2 debug, 3 trace. 
// ie if(logLevel>=INFO) se imprime si es info o debug o trace


string loggerLevelToStr(int mylogLevel){
 switch (mylogLevel) {
  case LOGGER_NO_LOG : 
   return "No Log";
  case LOGGER_INFO : 
   return "Info";
  case LOGGER_DEBUG : 
   return "Debug";
  case LOGGER_TRACE : 
   return "Trace";
 }
 return "";
}

void log(string msg, int logLevelMsg){//imprime el mensaje si logLevelMsg<=logLevel
 if(logLevelMsg<=logLevel){
  cout <<"["+loggerLevelToStr(logLevelMsg)+"] "+msg<<endl;
   cout.flush();
 }
}

void setLogLevel(int newLogLevel){
 logLevel = newLogLevel;
}






