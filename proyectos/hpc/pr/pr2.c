#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <crypt.h>

int main(void)
{
char *p = crypt("texto secreto", "13");
printf("%s\n", p);
}


