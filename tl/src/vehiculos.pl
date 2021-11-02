# Gabriel Centurion	gccomputos@gmail.com
# 
while (<>) {
	if ( m/^((\w*(AB|BA)\w*)(\d*?2\d*?3\d*?|\d*?3\d*?2\d*?)),((1\d\d\d|\d1\d\d|\d\d1\d|\d\d\d1)\d{4}[12345]),(\d\d?\/\d\d?\/(\d{4})),(.*?)$/i) {
		if(("$8" ge "1975") && ("$8" le "1984")){
		 print "$_" ;
		}
	}
}