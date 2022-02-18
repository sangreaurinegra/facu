#include"stringLib.h"

// fuente de codigo http://stackoverflow.com/questions/236129/splitting-a-string-in-c

vector<string> &split(const string &s, char delim, vector<string> &elems) {
    stringstream ss(s);
    string item;
    while(getline(ss, item, delim)) {
        elems.push_back(item);
    }
    return elems;
}


vector<string> split(const string &s, char delim) {
    vector<string> elems;
    return split(s, delim, elems);
}

// fuente http://www.cplusplus.com/forum/beginner/7777/
string intToStr(int number){
   stringstream ss;
   ss << number;
   return ss.str();
}

// fuente http://www.cplusplus.com/forum/articles/9645/
int strToint(string strNumber){
	int ret=-1;
	stringstream convert(strNumber);
	if ( !(convert >> ret) )
		ret = -1;
	return ret;

}


uint64_t strTouInt64_t(string strNumber){
	uint64_t ret=-1;
	stringstream convert(strNumber);
	if ( !(convert >> ret) )
		ret = -1;
	return ret;

}



string int64_tToStr(uint64_t number){
   stringstream ss;
   ss << number;
   return ss.str();
}


