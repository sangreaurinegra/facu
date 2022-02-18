#include "../../parserLib.h"
#include <assert.h>

int main(int argc, char *argv[]) {


    printf("\n\n****** isAuthorization **********\n\n");
    bool auth;
    auth = isAuthorization("\r\nAuthorization: algo\r\n otras cosas\r\n\r\n");
    if (auth) printf("autorizado\n");
    else printf("No existe el header Authorization\n");
    assert(auth == 1);

    printf("\n\n****** isAuthorization (no hay) **********\n\n");
    auth = isAuthorization("empieza el header \r\n\r\n\r\nAuthorization: algo es del body");
    if (auth) printf("autorizado\n");
    else printf("No existe el header Authorization\n");
    assert(auth == 0);

    printf("\n\n****** isAuthorization (no hay tampoco) **********\n\n");
    auth = isAuthorization("GET w.a.com HTTP/1.0\r\nHost: Authorization: passw\r\n\r\naca empieza el body\r\nAuthorization: algo es del body");
    if (auth) printf("autorizado\n");
    else printf("No existe el header Authorization\n");
    assert(auth == 0);



    printf("\n\n****** isPragmaNoCache - test1 **********\n\n");
    bool pragma;
    pragma = isPragmaNoCache("\r\nHost: www.a.c\r\nPragma: no-cache\r\n\r\n");
    if (pragma) printf("pragma");
    else printf("no esta pragma");
    assert(pragma == 1);

    printf("\n\n****** isPragmaNoCache - test2 **********\n\n");
    pragma = isPragmaNoCache("\r\nAuthorization: Pragma: no-cache\r\n\r\n");
    if (pragma) printf("pragma");
    else printf("no esta pragma");
    assert(pragma == 0);

    printf("\n\n****** isPragmaNoCache - test3 **********\n\n");
    pragma = isPragmaNoCache("HTTP/1.1 200 OK\r\n\r\nPragma: no-cache\r\n");
    if (pragma) printf("pragma");
    else printf("no esta pragma");
    assert(pragma == 0);




    printf("\n\n****** isExpires - test1 **********\n\n");
    bool expire;
    expire = isExpires("\r\nExpires: algo\r\n\r\n aca empieza el body\r\n");
    if (expire) printf("expire");
    else printf("no esta expire");
    assert(expire == 1);

    printf("\n\n****** isExpires - test2 **********\n\n");
    expire = isExpires("\r\nAuthorization: Expires\r\n\r\n");
    if (expire) printf("expire");
    else printf("no esta expire");
    assert(expire == 0);

    printf("\n\n****** isExpires - test3 **********\n\n");
    expire = isExpires("HTTP/1.1 200 OK\r\n\r\nExpires: 1234\r\n");
    if (expire) printf("expire");
    else printf("no esta expire");
    assert(expire == 0);



    printf("\n\n****** isGet - test1 **********\n\n");
    bool esget;
    esget = isGet("GET http://www.example.com/index.html HTTP/1.0\r\n");
    if (esget) printf("es GET");
    else printf("no es GET");
    assert(esget == 1);

    printf("\n\n****** isGet - test2 **********\n\n");
    esget = isGet("POST www.GET.com/index.html HTTP/1.0\r\nGET \r\n");
    if (esget) printf("es GET");
    else printf("no es GET");
    assert(esget == 0);



    printf("\n\n****** isPost - test1 **********\n\n");
    bool espost;
    espost = isPost("POST http://www.example.com/index.html HTTP/1.0\r\n");
    if (espost) printf("es POST");
    else printf("no es POST");
    assert(espost == 1);

    printf("\n\n****** isGet - test2 **********\n\n");
    espost = isPost("GET www.POST.com/index.html HTTP/1.0\r\nPOST");
    if (espost) printf("es POST");
    else printf("no es POST");
    assert(espost == 0);



    printf("\n\n****** isHttp10 - test1 **********\n\n");
    bool is10;
    is10 = isHttp10("GET http://www.example.com/index.html HTTP/1.0\r\n");
    if (is10) printf("es HTTP/1.0");
    else printf("es HTTP/1.1");
    assert(is10 == 1);

    printf("\n\n****** isHttp10 - test2 **********\n\n");
    is10 = isHttp10("HTTP/1.1 200 OK\r\n");
    if (is10) printf("es HTTP/1.0");
    else printf("es HTTP/1.1");
    assert(is10 == 0);




    printf("\n\n****** getContentLength - test1 **********\n\n");
    const char *match;
    match = getContentLength("\r\nHost: www.a.c\r\nContent-Length: 111\r\nContent-Type: text/plain\r\n\r\n");
    if (match) printf("Content-Length: %s\n",match);
    else printf("no esta Content-Length");
    assert(match != 0);

    printf("\n\n****** getContentLength - test2 **********\n\n");
    match = getContentLength("\r\nHost: www.a.c\r\nContent-Length: 222\r\n\r\n");
    if (match) printf("Content-Length: %s\n",match);
    else printf("no esta Content-Length");
    assert(match != 0);

    printf("\n\n****** getContentLength - test3 **********\n\n");
    match = getContentLength("\r\nHost: www.a.c\r\n\r\nContent-Length: 333\r\n");
    if (match) printf("Content-Length: %s\n",match);
    else printf("no esta Content-Length");
    assert(match == 0);



    printf("\n\n****** getHeader - test1 **********\n\n");
    const char *head;
    head = getHeader("este es el header \r\n\r\n primera linea del body: algo \r\n segunda linea del body \r\n");
    if (head) printf("%s\n",head);
    else printf("no encontro el header");
    assert(head != 0);

    printf("\n\n****** getHeader - test2 **********\n\n");
    head = getHeader("GET www.servidor.com/index.html HTTP/1.1\r\nHost: www.servidor.com\r\nOtroHeader: abcdefghijk\r\n\r\nAca empieza el body: algo \r\n segunda linea del body \r\n");
    if (head) printf("%s\n",head);
    else printf("no encontro el header");
    assert(head != 0);




    printf("\n\n****** getBody - test1 **********\n\n");
    const char * body;
    body = getBody("este es el header \r\n\r\n primera linea del body: algo \r\n segunda linea del body \r\n");
    if (body) printf("%s\n",body);
    else printf("no encontro el body");
    assert(body != 0);



    printf("\n\n****** getHost - test1 **********\n\n");
    const char *  host = getHost("\r\nHost: http://pepelepu.com/asieslacosa.html\r\n\r\n");
    if (host) printf("%s\n",host);
    else printf("no encontro el host");
    assert(host != 0);

    printf("\n\n****** getHost - test2 **********\n\n");
    host = getHost("\r\nContent-Length: 222\r\nHost: www.tarambana.com\r\n\r\n");
    if (host) printf("%s\n",host);
    else printf("no encontro el host");
    assert(host != 0);

    printf("\n\n****** getHost - test3 **********\n\n");
    host = getHost("\r\nContent-Length: 222\r\n\r\nHost: www.eselbody.uy\r\n");
    if (host) printf("%s\n",host);
    else printf("no encontro el host");
    assert(host == 0);




    printf("\n\n****** getStateCode - test1 **********\n\n");
    char * resp = new char [1024];
    strcpy(resp,"HTTP/1.1 205 Reset Content \r\nHost: www.coso.com\r\n");
    char * code = getStateCode(resp);
    if (code) printf("StateCode: %s\n",code);
    else printf("no encontro el state code");
    assert(code != 0);
    delete code;
    delete [] resp;



    printf("\n\n****** getModifiedSince - test1 **********\n\n");
    int  modifiedSince = getModifiedSince("\r\nHost: www.a.c\r\nIf-Modified-Since: Wed, 01 Sep 2004 13:24:52 GMT\r\n\r\n");
    if (modifiedSince != -1) printf("If-Modified-Since: %d\n",modifiedSince);
    else printf("no esta If-Modified-Since");
    assert(modifiedSince != -1);

    printf("\n\n****** getModifiedSince - test2 **********\n\n");
    modifiedSince = getModifiedSince("\r\nHost: www.a.c\r\n\r\nIf-Modified-Since: Wed, 09 Sep 2004 13:24:52 GMT\r\n");
    if (modifiedSince != -1) printf("If-Modified-Since: %d\n",modifiedSince);
    else printf("no esta If-Modified-Since");
    assert(modifiedSince == -1);

    printf("\n\n****** getModifiedSince - test3 **********\n\n");
    modifiedSince = getModifiedSince("\r\nHost: www.a.c\r\nIf-Modified-Since: Wed,10Sep2004 13:24:52 GMT\r\n\r\n");
    if (modifiedSince != -1) printf(">> ERROR << If-Modified-Since: %d\n",modifiedSince);
    else printf("no If-Modified-Since");
    //assert(modifiedSince == -1);




    printf("\n\n****** getLastModified - test1 **********\n\n");
    int  Lastmodified = getLastModified("\r\nHost: www.a.c\r\nLast-Modified: Wed, 01 Sep 2004 13:24:52 GMT\r\n\r\n");
    if (Lastmodified != -1) printf("Last-Modified: %d\n",Lastmodified);
    else printf("no esta Last-Modified");
    assert(Lastmodified != -1);

    printf("\n\n****** getLastModified - test2 **********\n\n");
    Lastmodified = getLastModified("\r\nHost: www.a.c\r\n\r\nLast-Modified: Wed, 09 Sep 2004 13:24:52 GMT\r\n");
    if (Lastmodified != -1) printf("Last-Modified: %d\n",Lastmodified);
    else printf("no esta Last-Modified");
    assert(Lastmodified == -1);

    printf("\n\n****** getLastModified - test3 **********\n\n");
    Lastmodified = getLastModified("\r\nHost: www.a.c\r\nLast-Modified: Wed,10Sep2004 13:24:52 GMT\r\n\r\n");
    if (Lastmodified != -1) printf(">> ERROR << Last-Modified:: %d\n",Lastmodified);
    else printf("no Last-Modified");
    //assert(Lastmodified == -1);



    printf("\n\n****** getDate - test1 **********\n\n");
    int  dia = getDate("\r\nHost: www.a.c\r\nDate: Wed, 01 Sep 2004 13:24:52 GMT\r\n\r\n");
    if (dia != -1) printf("Date: %d\n",dia);
    else printf("no esta Date");
    assert(dia != -1);

    printf("\n\n****** getDate - test2 **********\n\n");
    dia = getDate("\r\nHost: www.a.c\r\n\r\nDate: Wed, 09 Sep 2004 13:24:52 GMT\r\n");
    if (dia != -1) printf("Date: %d\n",dia);
    else printf("no esta Date");
    assert(dia == -1);

    printf("\n\n****** getDate - test3 **********\n\n");
    dia = getDate("\r\nHost: www.a.c\r\nDate: Wed,10Sep2004 13:24:52 GMT\r\n\r\n");
    if (dia != -1) printf(">> ERROR << Date:: %d\n",dia);
    else printf("no esta Date");
    //assert(dia == -1);




    printf("\n\n****** getExpires - test1 **********\n\n");
    int  vence = getExpires("\r\nHost: www.a.c\r\nExpires: Wed, 01 Sep 2004 13:24:52 GMT\r\n\r\n");
    if (vence != -1) printf("Expires: %d\n",vence);
    else printf("no esta Expires");
    assert(vence != -1);

    printf("\n\n****** getExpires - test2 **********\n\n");
    vence = getExpires("\r\nHost: www.a.c\r\n\r\nExpires: Wed, 09 Sep 2004 13:24:52 GMT\r\n");
    if (vence != -1) printf("Expires: %d\n",vence);
    else printf("no esta Expires");
    assert(vence == -1);

    printf("\n\n****** getExpires - test3 **********\n\n");
    vence = getExpires("\r\nHost: www.a.c\r\nExpires: Wed,10Sep2004 13:24:52 GMT\r\n\r\n");
    if (vence != -1) printf(">> ERROR << Expires: %d\n",vence);
    else printf("no esta Expires");
    //assert(dia == -1);


    printf("\n\n****** modifyURL 1.1 a 1.0 **********\n\n");
    char * r = new char [1024];
    strcpy(r,"HTTP/1.1 205 Reset Content \r\nHost: www.coso.com\r\n");
    printf("Response antes: %s\n",r);
    cambioResponseHttp11A10(r);
    printf("Response despues: %s\n",r);



    printf("\n\n****** isProtocol - test1 **********\n\n");
    bool isP;
    isP = isProtocol("GET http://www.proxy.com/index.html HTTP/1.0\r\n");
    if (isP) printf("vino con http://\n");
    else printf("no vino con http://\n");
    assert(isP == 1);

    printf("\n\n****** isProtocol - test2 **********\n\n");
    isP = isProtocol("POST www.proxy2.com/index.html HTTP/1.0\r\n");
    if (isP) printf("vino con http://\n");
    else printf("no vino con http://\n");
    assert(isP == 0);



    printf("\n\n****** isPrefix - test1 **********\n\n");
    bool pre = isPrefix("aca empieza","aca empieza la cadena");
	if (pre) printf("es prefijo");
	else printf("no es prefijo");
	assert(pre == 1);

    printf("\n\n****** isPrefix - test2 **********\n\n");
    pre = isPrefix(" www","www.putoelqlee.com/index.html");
	if (pre) printf("es prefijo");
	else printf("no es prefijo");
	assert(pre == 0);

    printf("\n\n****** isPrefix - test3 **********\n\n");
    pre = isPrefix("www.putoelqlee","www.putoelqlee.com/index.html");
	if (pre) printf("es prefijo");
	else printf("no es prefijo");
	assert(pre == 1);

    printf("\n\n****** isPrefix - test4 **********\n\n");
    pre = isPrefix("www.putoelqlee.com","www.putoelqlee.com/a.h");
	if (pre) printf("es prefijo");
	else printf("no es prefijo");
	assert(pre == 1);



    printf("\n\n****** getURL - test1 **********\n\n");
    const char * url;
    url = getUrl("GET http://www.example.com/index.html HTTP/1.0\r\n");
    if (url) printf("%s\n",url);
    else printf("no encontro la URL");
    assert(url != 0);

    printf("\n\n****** getURL - test2 **********\n\n");
    url = getUrl("GET www.example2.com/index.html HTTP/1.0\r\n");
    if (url) printf("%s\n",url);
    else printf("no encontro la URL");
    assert(url != 0);

    printf("\n\n****** getURL - test3 **********\n\n");
    char * req = new char [1024];
    strcpy(req,"GET http://www.example3.com/index.html HTTP/1.1\r\n");
    strcat(req,"Host: www.example3.com\r\n\r\n");
    url = getUrl(req);
    if (url) printf("%s\n",url);
    else printf("no encontro la URL");
    assert(url != 0);

    printf("\n\n****** getURL - test4 **********\n\n");
    strcpy(req,"GET www.example4.com/index.html HTTP/1.1\r\n");
    strcat(req,"Host: www.example4.com\r\n\r\n");
    url = getUrl(req);
    if (url) printf("%s\n",url);
    else printf("no encontro la URL");
    assert(url != 0);

    printf("\n\n****** getURL - test5 **********\n\n");
    strcpy(req,"POST www.example5.com/index.html HTTP/1.1\r\n");
    strcat(req,"Host: www.example5.com\r\n\r\n");
    url = getUrl(req);
    if (url) printf("%s\n",url);
    else printf("no encontro la URL");
    assert(url != 0);

    printf("\n\n****** getURL - test6 **********\n\n");
    req = new char [1024];
    strcpy(req,"POST http://www.example6.com/index.html HTTP/1.1\r\n");
    strcat(req,"Host: www.example6.com\r\n\r\n");
    url = getUrl(req);
    if (url) printf("%s\n",url);
    else printf("no encontro la URL");
    assert(url != 0);


 /*

    printf("\n\n****** getContentLength (existe) **********\n\n");
    const char *match = getContentLength("Content-Length: 374\r\nContent-Type: text/plain\r\n\r\nAuthorization: Content-Length: 11\r\n");
    if (match != NULL) {
        printf("content Size %s\n",match);
    }

    printf("\n\n****** getContentLength (no existe) **********\n\n");
    match = getContentLength("\r\nContent-Type: text/plain\r\n\r\nel Content-Length: 111\r\n");
    if (match != NULL) {
        printf("content Size %s\n",match);
    }
    else
        printf("no existe el header Content-Length\n");



    printf("\n\n****** isGet **********\n\n");
    if (isGet("GET http://www.example.com/index.html HTTP/1.0")) {
        printf("es GET \n");
    }

    printf("\n\n****** isPost **********\n\n");
    if (isPost("POST http://www.example.com/index.html HTTP/1.0")) {
        printf("es POST \n");
    }

    printf("\n\n****** getLastModified **********\n\n");
    int  lastModified = getLastModified("Last-Modified: Thu, 30 Nov 2011 13:52:45 GMT \r\n Aca sigue con otras cosas");
    printf("lastModified %d\n",lastModified);


    printf("\n\n****** getModifiedSince **********\n\n");
    int  modifiedSince = getModifiedSince("If-Modified-Since: Wed, 01 Sep 2004 13:24:52 GMT\r\n");
    printf("modifiedSince %d\n",modifiedSince);


    printf("\n\n****** getHeader1 **********\n\n");
    const char * head = getHeader("este es el header \r\n\r\n primera linea del body: algo \r\n segunda linea del body \r\n");

    if (head != NULL) {
        printf("HEADER: %s\n",head);

    }

    printf("\n\n****** getHeader2 **********\n\n");
    head = getHeader("GET www.servidor.com/index.html HTTP/1.1\r\nHost: www.servidor.com\r\nOtroHeader: abcdefghijk\r\n\r\nAca empieza el body: algo \r\n segunda linea del body \r\n");

    if (head != NULL) {
        printf("HEADER: %s\n",head);

    }

    printf("\n\n****** getBody **********\n\n");
    const char *  body = getBody("este es el header \r\n\r\n primera linea del body: algo \r\n segunda linea del body \r\n");

    if (body != NULL) {
        printf("BODY: %s\n",body);

    }

    printf("\n\n****** getHost **********\n\n");
    const char *  host = getHost("Host: http://pepelepu.com/asieslacosa.html \r\n");

    if (host != NULL) {
        printf("Host: %s\n",host);

    }

    printf("\n\n****** getUrl 1.1**********\n\n");
    const char *  url = getUrl("GET /index.html HTTP/1.1\r\nHost: www.coso.com\r\n");

    if (url != NULL) {
        printf("Url: %s\n",url);

    }

    printf("\n\n****** getUrl 1.0**********\n\n");
    const char *  url2 = getUrl("GET www.pepe.com/index.html HTTP/1.0\r\n");

    if (url2 != NULL) {
        printf("Url2: %s\n",url2);

    }

    printf("\n\n****** getUrl POST 1.1**********\n\n");
    const char *  url3 = getUrl("POST /index.html HTTP/1.1\r\nHost: www.coso.com\r\n");

    if (url3 != NULL) {
        printf("Url3: %s\n",url3);

    }

    printf("\n\n****** isHttp10 **********\n\n");
    bool  is10 = isHttp10("GET http://www.example.com/index.html HTTP/1.0\r\n");

    if (is10) {
        printf("is10\n");

    }

    printf("\n\n****** Test format time 1: Wed, 01 Sep 2004 13:24:52 GMT **********\n\n");

    int  dateFormat1 = stringToTime("Wed, 01 Sep 2004 13:24:52 GMT");
    printf("dateFormat1 %d\n",dateFormat1);

    printf("\n\n****** Test format time 2: Sunday, 06-Nov-94 08:49:37 GMT **********\n\n");
    int  dateFormat2 = stringToTime("Sunday, 06-Nov-94 08:49:37 GMT");
    printf("dateFormat2 %d\n",dateFormat2);

    printf("\n\n****** Test format time 3: Sun Nov  6 08:49:37 1994 **********\n\n");
    int  dateFormat3 = stringToTime("Sun Nov  6 08:49:37 1994");
    printf("dateFormat3 %d\n",dateFormat3);

    printf("\n\n****** getToday **********\n\n");
    int  today = getToday();
    printf("today %d\n",today);


    printf("\n\n****** getModifiedSince: prueba si no explota cuando no encuentra la fecha **********\n\n");
    int  modifiedSince4 = getModifiedSince("curucul:24:52 GMT\r\n");
    printf("modifiedSince %d\n",modifiedSince4);

    printf("\n\n****** isExpires **********\n\n");
    bool  expire2 = isExpires("Expires: algo\r\n");
    if (expire2) {
        printf("isExpires\n");

    }

    printf("\n\n****** getexpire **********\n\n");
    int  expire = getExpires("Expires: Wed, 01 Sep 2004 13:24:52 GMT\r\n");
    printf("expire %d\n",expire);


    printf("\n\n****** modifyURL 1.1 a 1.0 **********\n\n");
    char * resp = new char [1024];
    strcpy(resp,"HTTP/1.1 205 Reset Content \r\nHost: www.coso.com\r\n");
    cambioVersionHttp11A10(resp);
    printf("Response: %s\n",resp);


    printf("\n\n****** getStateCode **********\n\n");
    strcpy(resp,"HTTP/1.1 205 Reset Content \r\nHost: www.coso.com\r\n");
    char * code = getStateCode(resp);

    if (code != NULL) {
        printf("StateCode: %s\n",code);
    }
    else
    	printf("response es NULL\n");

    delete [] resp;

*/
    return 0;


}
