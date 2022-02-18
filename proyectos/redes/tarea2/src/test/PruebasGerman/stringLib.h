#ifndef STRINGLIB_H_
#define STRINGLIB_H_
#include"logger.h"
#include <vector>
#include <sstream>
#include <string.h>
#include <stdint.h>

vector<string> split(const string &s, char delim);

string intToStr(int number);

int strToint(string strNumber);

uint64_t strTouInt64_t(string strNumber);

string int64_tToStr(uint64_t number);

#endif /* STRINGLIB_H_ */
