#include <time.h>
#include <stdio.h>
#include "timeLib.h"


/*
 * Deveulve un int de la cantidad de segundos. Si hay error devuelve -1
 * Soporta 3 formatos de dates
 * 1: Sun, 06 Nov 1994 08:49:37 GMT
 * 2: Sunday, 06-Nov-94 08:49:37 GMT
 * 3: Sun Nov  6 08:49:37 1994
 */

int stringToTime(const char *date) {

	if (date == NULL) return -1;

    struct tm storage={0,0,0,0,0,0,0,0,0};
    char *p=NULL;
    time_t retval=0;

    if (strptime(date,"%a, %d %b %Y %H:%M:%S %Z",&storage) != NULL) {
        p=(char *)strptime(date,"%a, %d %b %Y %H:%M:%S %Z",&storage);

    }
    else if (strptime(date,"%a, %d-%b-%y %H:%M:%S %Z",&storage) != NULL) {
        p=(char *)strptime(date,"%a, %d-%b-%y %H:%M:%S %Z",&storage);

    }
    else if (strptime(date,"%a %b %d %H:%M:%S %Y",&storage) != NULL) {
        p=(char *)strptime(date,"%a %b %d %H:%M:%S %Y",&storage);

    }



    if(p==NULL) {
            retval=-1;
    }
    else {
            retval=mktime(&storage);
    }
    //printf("string to time_t: %lu \n", retval);

    return retval;

}


int getToday() {

	 time_t today = time(NULL);
     return today;

}
/*
int main()
{
	printf("TEST DATE COMPARATION\n\n");

   char *date1="20-JUN-2006";
   char *date2="21-JUN-2006";
   time_t d1=to_seconds(date1);
   time_t d2=to_seconds(date2);

   printf("date comparison: %s %s ",date1,date2);
   if(d1==d2) printf("equal\n");
   if(d2>d1)  printf("second date is later\n");
   if(d2<d1)  printf("seocnd date is earlier\n");

	printf("TEST STRING TO TIME\n\n");

	tm tm = stringToTime("Thu, 30 Jan 1920 13:52:45 GMT");
	printf("year: %d; month: %d; day: %d;\n",tm.tm_year, tm.tm_mon, tm.tm_mday);
	printf("hour: %d; minute: %d; second: %d\n",tm.tm_hour, tm.tm_min, tm.tm_sec);
   return 0;
}
*/
