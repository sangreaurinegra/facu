#ifndef LOGGER_H
#define LOGGER_H

#include <iostream>
using std::string;
using namespace std;


// libreria para el manejo de log del sistema

const int LOGGER_NO_LOG = 0;
const int LOGGER_INFO = 1;
const int LOGGER_DEBUG = 2;
const int LOGGER_TRACE = 3;

void log(string msg, int logLevelMsg);//imprime el mensaje si logLevelMsg<=logLevel

void setLogLevel(int newLogLevel);

#endif
