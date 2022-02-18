#include "../../logger.h"
#include "../../admin.h"


int main(int argc, char *argv[]) {

	setLogLevel(LOGGER_TRACE);
	log("Test Admin",LOGGER_INFO);
	log("Entradas OK",LOGGER_INFO);

	string  comando = "show run";
	log(comando+"-> "+ intToStr(getComando(comando))+" : "+int64_tToStr(getValor(comando)),LOGGER_INFO);

	comando = "purge";
	log(comando+"-> "+ intToStr(getComando(comando))+" : "+int64_tToStr(getValor(comando)),LOGGER_INFO);

	comando = "set max_object_size 124";
	log(comando+"-> "+ intToStr(getComando(comando))+" : "+int64_tToStr(getValor(comando)),LOGGER_INFO);

	comando = "set max_cached_object_size 125";
	log(comando+"-> "+ intToStr(getComando(comando))+" : "+int64_tToStr(getValor(comando)),LOGGER_INFO);

	comando = "set max_object_count 12";
	log(comando+"-> "+ intToStr(getComando(comando))+" : "+int64_tToStr(getValor(comando)),LOGGER_INFO);

	comando = "quit";
	log(comando+"-> "+ intToStr(getComando(comando))+" : "+int64_tToStr(getValor(comando)),LOGGER_INFO);

	log("Entradas ERROR",LOGGER_INFO);

	 comando = "show run 123";
		log(comando+"-> "+ intToStr(getComando(comando))+" : "+int64_tToStr(getValor(comando)),LOGGER_INFO);

		comando = "purGe";
		log(comando+"-> "+ intToStr(getComando(comando))+" : "+int64_tToStr(getValor(comando)),LOGGER_INFO);

		comando = "set max_object_size 1234";
		log(comando+"-> "+ intToStr(getComando(comando))+" : "+int64_tToStr(getValor(comando)),LOGGER_INFO);

		comando = "set max_cached_object_size 001";
		log(comando+"-> "+ intToStr(getComando(comando))+" : "+int64_tToStr(getValor(comando)),LOGGER_INFO);

		comando = "set max_object_count ";
		log(comando+"-> "+ intToStr(getComando(comando))+" : "+int64_tToStr(getValor(comando)),LOGGER_INFO);

		comando = "set max_cached_object_size num";
		log(comando+"-> "+ intToStr(getComando(comando))+" : "+int64_tToStr(getValor(comando)),LOGGER_INFO);

		cout<<"\n";
		cout<<"\n";
		log("********* Pruebas String **********",LOGGER_INFO);

		log("Char * -> string",LOGGER_INFO);

		char * prustr = new char[10];
		*prustr = 'a';
		prustr[1] = 'b';

		cout<<prustr;
		cout<<"\n";
		prustr[2]='\0';

		cout<<prustr;
		cout<<"\n";

		string strStringPru = string(prustr);

		cout<<strStringPru;

		cout<<"\n";

		log(strStringPru,LOGGER_INFO);




		log("string -> Char *",LOGGER_INFO);


		cout<< strStringPru.c_str();
		cout<<"\n";

		log("********* Pruebas str->uint64_t  **********",LOGGER_INFO);

		string num = "12345";
		cout<< num+"->";
		cout<< strTouInt64_t(num);
		cout<<"\n";

		num = "12345678";
		cout<< num+"->";
		cout<< strTouInt64_t(num);
		cout<<"\n";

		num = "0";
		cout<< num+"->";
		cout<< strTouInt64_t(num);
		cout<<"\n";

		num = "001";
		cout<< num+"->";
		cout<< strTouInt64_t(num);
		cout<<"\n";

		num = "1234567890";
		cout<< num+"->";
		cout<< strTouInt64_t(num);
		cout<<"\n";

		log("********* Pruebas uint64_t ->str  **********",LOGGER_INFO);


		uint64_t num_uint64_t = 123456;
		cout<< num_uint64_t;
		cout.flush();
		cout<< "->"+int64_tToStr(num_uint64_t);
		cout<<"\n";

		num_uint64_t = 001;
		cout<< num_uint64_t;
		cout<< "->"+int64_tToStr(num_uint64_t);
		cout<<"\n";

		num_uint64_t = 12345689;
		cout<< num_uint64_t;
		cout<< "->"+int64_tToStr(num_uint64_t);
		cout<<"\n";

}
