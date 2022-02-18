#include <iostream>
#include <vector>
#include <string.h>
#include <math.h>
#include "TipoValor.h"
#include "Pila.h"
#include "ManejadorPila.h"
#include <stdlib.h>



using namespace std;


class Statement;
class Expresion;
class FuncionDeclaracion;
class FuncionDeclaracionConParametros;
class Entero;
class Float;
class String;
class OperacionBinaria;
class Block;
class ExpresionStatement;
class IfStatment;
class WhileStatment;
class ClassStatment;
class Arreglo;
class FuncionDeclaracion;
class Asignacion;
class Variable;
class Identificador;
class Argumento;
class VariableGlobal;
class DefArgs;
class ArregloPosicion;
class BloqueClase;
class FuncionDeclaracionClase;
class Accesor;

typedef std::vector<Statement*> StatementList;
typedef std::vector<Expresion*> ExpressionList;
typedef std::vector<Argumento*> listaArgsDef;

class NodoPrincipal {

    public:
       virtual ~NodoPrincipal() {}
};

class Expresion : public NodoPrincipal {
    public:
     virtual void a()=0;
	 virtual TipoValor* evaluar() = 0;
	 virtual string getTipo()=0;
};

class Statement : public NodoPrincipal {
    public:
    virtual void b()=0;
	virtual void ejecutar() = 0;
	virtual string getTipo()=0;
};



class ListaFunciones{
    private:
        static ListaFunciones* instancia;

        vector<Statement*> funciones;

    public:
        ListaFunciones();
        static ListaFunciones* getInstancia();
        int agragarFuncion(Statement* f);
        Statement* buscarFuncion(string id);
        void imprimirFunciones();
        virtual ~ListaFunciones();


};

class ListaClases{
    private:
        static ListaClases* instancia;
        ListaClases();
        vector<ClassStatment*> clases;


    public:
        static ListaClases* getInstancia();
        int agragarClase(ClassStatment* clase);
        ClassStatment* buscarClase(string id);
        void imprimirClases();
        virtual ~ListaClases();


};



class Entero : public Expresion {
public:
    int value;
    Entero(int value) : value(value) {}
    void a(){
        cout<<"valor int:"<< value <<"\n";
    }

    string getTipo(){
        return "claseEntero";
    }

	TipoValor* evaluar(){
		TipoValor* t = new TInteger(value);
        return t;
	}
};

class Boolean : public Expresion {
public:
    bool value;
    Boolean(int value) : value(value) {}
    void a(){

        cout<<"valor Bool:"<< value <<"\n";
    }

    string getTipo(){
        return "claseBoolean";
    }


	TipoValor* evaluar(){
		TipoValor* t = new TBool(value);
        return t;
	}
};

class Float : public Expresion {
public:
    float value;
    Float(float value) : value(value) { }

    void a(){
        cout<<"real:"<<value<<"\n";
    }

    string getTipo(){
        return "claseFloat";
    }

    TipoValor* evaluar(){
        TipoValor* t = new TReal(value);
		return t;
    }
};

class String : public Expresion {
public:
    string str ;
    String(string s) : str(s) {}
	String(char* s) : str(s) {}

    void a(){
        cout<< "valor string:" << str << "\n";
    }

    string getTipo(){
        return "claseString";
    }

    TipoValor* evaluar(){
        TipoValor* t = new TString(str);
		return t;
    }
};

class Operador: Expresion{
public :
        char* op;
        Operador( char* op): op(op) { }
        void a(){
        cout<< "OPERADOR:" << op << "\n";
    }

    string getTipo(){
        return "claseOperador";
    }

    TipoValor* evaluar(){
		return NULL;
    }
};

class Identificador : public Expresion {
public:
    string str ;

    Identificador(string s){
       int i = 0;
       str[0] = '\0';
       while ((i < s.length()) && ((s[i] >= 'a' && s[i] <= 'z') ||(s[i] >= 'A' && s[i]<= 'Z')||(s[i] >= '0' && s[i] <= '9')||s[i] == '_' )) {
           i++;
       }
       str = s.substr(0,i);
       //cout << "Identificador - Tamaño y String " << str.length() << " " <<str << "\n";
   }

    void a(){
        cout<< "valor string Id:" << str << "\n";
    }

    string getTipo(){
        return "claseIdentificador";
    }

    TipoValor* evaluar(){
        TipoValor* t = new TString(str);
		return t;
    }
};

class IdentificadorGlobal : public Expresion {
public:
    string str ;
    IdentificadorGlobal(string s){

       int i = 0;
       str[0] = '\0';
       while ((i < s.length()) && ((s[i] >= 'a' && s[i] <= 'z') ||(s[i] >= 'A' && s[i]<= 'Z')||(s[i] >= '0' && s[i] <= '9')||s[i] == '_' )) {
           i++;
       }
       str = s.substr(0,i);
   }


    void a(){
        cout<< "valor IDENTIFICADOR GLOBAL Id:" << str << "\n";
    }

    string getTipo(){
        return "claseIdentificadorGlobal";
    }

    TipoValor* evaluar(){
        cout<<"eval id global";
        TipoValor* t = new TString(str);
		return t;
    }
};

class Atributo : public Expresion {
public:
    string str ;
    Atributo(string s){
        int i = 0;
        str[0] = '\0';
        while ((i < s.length()) && ((s[i] >= 'a' && s[i] <= 'z') ||(s[i] >= 'A' && s[i]<= 'Z')||(s[i] >= '0' && s[i] <= '9')||s[i] == '_' )) {
           i++;
        }
        str = s.substr(0,i);
    }

    void a(){
        cout<< "valor string Atrbuto:" << str << "****\n";
    }

    string getTipo(){
        return "claseAtributo";
    }

    TipoValor* evaluar(){
        TipoValor* t = new TString(str);
		return t;
    }
};

class AtributoAsig : public Expresion {
public:
    string str ;
    Expresion& nombreAtributo; /*siempre va a ser un Variable*/
    AtributoAsig(Expresion& nA,string s) :nombreAtributo(nA),str(s){

    }

    void a(){
        cout<< "Nombre del atributo:\n";
        nombreAtributo.a();
        cout<< "Asignar atributo:" << str << "\n";
    }

    string getTipo(){
        return "claseAtributoAsig";
    }

    TipoValor* evaluar(){
        return NULL;
    }
};


class Argumento : public Expresion {
public:

    string str ;
    Argumento(string s){
        str=s;
    }

    void a(){
        cout<< "valor  ARGUMENTO:" << str << "\n";
    }

    string getTipo(){
        return "claseArgumento";
    }

    TipoValor* evaluar(){
        return NULL;
    }
};

class OperacionBinaria : public Expresion {
public:
    char* op;
    Expresion& lhs;
    Expresion& rhs;
    OperacionBinaria(Expresion& lhs, char* op, Expresion& rhs) :    lhs(lhs), rhs(rhs), op(op){}

	void a(){
        cout<<"operador:" <<op<<"\n";
        lhs.a();
        rhs.a();
    }

    string getTipo(){
        return "claseOperacionBinaria";
    }

	TipoValor* evaluar(){

        TipoValor* res = NULL;
		TipoValor* izq = lhs.evaluar();
		TipoValor* der = rhs.evaluar();

		if (izq == NULL || der == NULL){
            throw string ("Error:  La funcion evaluada no tiene valor de retorno.\n");
        }else{

		if (strcmp(op,"1") == 0) { // SUMA
            if ((izq->getTipo() == 0) && (der->getTipo() == 0))
                res = new TInteger(((TInteger*)izq)->getValor() + ((TInteger*)der)->getValor());
            else if ((izq->getTipo() == 0) && (der->getTipo() == 1))
                res = new TReal (((TInteger*)izq)->getValor() + ((TReal*)der)->getValor());
            else if ((izq->getTipo() == 1) && (der->getTipo() == 0))
                res = new TReal (((TReal*)izq)->getValor()+ ((TInteger*)der)->getValor());
            else if ((izq->getTipo() == 1) && (der->getTipo() == 1))
                res = new TReal (((TReal*)izq)->getValor() + ((TReal*)der)->getValor());
            else if ((izq->getTipo() == 2) && (der->getTipo() == 2))
                res = new TString (((TString*)izq)->getValor() + ((TString*)der)->getValor());
            else
                throw string("Error: Tipos de datos no esperados para la operacion Suma.\n");
		}
		else if (strcmp(op,"2") == 0) { // RESTA
            if ((izq->getTipo() == 0) && (der->getTipo() == 0))         //Entero - Entero
                res = new TInteger(((TInteger*)izq)->getValor() - ((TInteger*)der)->getValor());
            else if ((izq->getTipo() == 0) && (der->getTipo() == 1))    //Entero - Real
                res = new TReal (((TInteger*)izq)->getValor() - ((TReal*)der)->getValor());
            else if ((izq->getTipo() == 1) && (der->getTipo() == 0))    //Real - Entero
                res = new TReal (((TInteger*)der)->getValor() - ((TReal*)izq)->getValor());
            else if ((izq->getTipo() == 1) && (der->getTipo() == 1))    //Real - Real
                res = new TReal (((TReal*)izq)->getValor() - ((TReal*)der)->getValor());
            else
                throw string("Error: Tipos de datos no esperados para la operacion Resta.\n");
		}
		else if (strcmp(op,"3") == 0) { // MULTIPLICACION
            if ((izq->getTipo() == 0) && (der->getTipo() == 0))
                res = new TInteger(((TInteger*)izq)->getValor() * ((TInteger*)der)->getValor());
            else if ((izq->getTipo() == 0) && (der->getTipo() == 1))
                res = new TReal (((TInteger*)izq)->getValor() * ((TReal*)der)->getValor());
            else if ((izq->getTipo() == 1) && (der->getTipo() == 0))
                res = new TReal (((TInteger*)der)->getValor() * ((TReal*)izq)->getValor());
            else if ((izq->getTipo() == 1) && (der->getTipo() == 1))
                res = new TReal (((TReal*)izq)->getValor() * ((TReal*)der)->getValor());
            else if ((izq->getTipo() == 2) && (der->getTipo() == 0)){
                int i;
                string s = "";
                for(i=0;i<((TInteger*)der)->getValor();i++)
                    s = s + ((TString*)izq)->getValor();
                res = new TString (s);
            }
            else
                throw string("Error: Tipos de datos no esperados para la operacion Producto.\n");
		}
		else if (strcmp(op,"4") == 0) { // DIVISION
            if ((izq->getTipo() == 0) && (der->getTipo() == 0))
                res = new TInteger(((TInteger*)izq)->getValor() / ((TInteger*)der)->getValor());
            else if ((izq->getTipo() == 0) && (der->getTipo() == 1))
                res = new TReal (((TInteger*)izq)->getValor() / ((TReal*)der)->getValor());
            else if ((izq->getTipo() == 1) && (der->getTipo() == 0))
                res = new TReal (((TReal*)izq)->getValor() / ((TInteger*)der)->getValor());
            else if ((izq->getTipo() == 1) && (der->getTipo() == 1))
                res = new TReal (((TReal*)izq)->getValor() / ((TReal*)der)->getValor());
            else
                throw string("Error: Tipos de datos no esperados para la operacion Division.\n");
		}
        else if (strcmp(op,"5") == 0) { // MODULO
            if ((izq->getTipo() == 0) && (der->getTipo() == 0))
                res = new TInteger(((TInteger*)izq)->getValor() % ((TInteger*)der)->getValor());
            else if ((izq->getTipo() == 0) && (der->getTipo() == 1))
                res = new TReal (fmod(((TInteger*)izq)->getValor(),((TReal*)der)->getValor()));
            else if ((izq->getTipo() == 1) && (der->getTipo() == 0))
                res = new TReal (fmod(((TReal*)izq)->getValor(),((TInteger*)der)->getValor()));
            else if ((izq->getTipo() == 1) && (der->getTipo() == 1))
                res = new TReal (fmod(((TReal*)izq)->getValor(),((TReal*)der)->getValor()));
            else
                throw string("Error: Tipos de datos no esperados para la operacion Modulo.\n");
        }
		else if (strcmp(op,"6") == 0) { // EXPONENCIACION
            if ((izq->getTipo() == 0) && (der->getTipo() == 0))
                res = new TInteger(pow(((TInteger*)izq)->getValor(),((TInteger*)der)->getValor()));
            else if ((izq->getTipo() == 0) && (der->getTipo() == 1))
                res = new TReal (pow(((TInteger*)izq)->getValor(),((TReal*)der)->getValor()));
            else if ((izq->getTipo() == 1) && (der->getTipo() == 0))
                res = new TReal (pow(((TReal*)izq)->getValor(),((TInteger*)der)->getValor()));
            else if ((izq->getTipo() == 1) && (der->getTipo() == 1))
                res = new TReal (pow(((TReal*)izq)->getValor(),((TReal*)der)->getValor()));
            else
                throw string("Error: Tipos de datos no esperados para la operacion Exponenciacion.\n");
		}
        else if (strcmp(op,"9") == 0) { // COMPARACION
            if ((izq->getTipo() == 0) && (der->getTipo() == 0)) {
                if (((TInteger*)izq)->getValor() < ((TInteger*)der)->getValor())
                    res = new TInteger(-1);
                else if (((TInteger*)izq)->getValor() == ((TInteger*)der)->getValor())
                    res = new TInteger(0);
                else if (((TInteger*)izq)->getValor() > ((TInteger*)der)->getValor())
                    res = new TInteger(1);
            }
            else if ((izq->getTipo() == 0) && (der->getTipo() == 1)){
                if (((TInteger*)izq)->getValor() < ((TReal*)der)->getValor())
                    res = new TInteger(-1);
                else if (((TInteger*)izq)->getValor() == ((TReal*)der)->getValor())
                    res = new TInteger(0);
                else if (((TInteger*)izq)->getValor() > ((TReal*)der)->getValor())
                    res = new TInteger(1);
            }
            else if ((izq->getTipo() == 1) && (der->getTipo() == 0)) {

                if (((TReal*)izq)->getValor() < ((TInteger*)der)->getValor())
                    res = new TInteger(-1);
                else if (((TReal*)izq)->getValor() == ((TInteger*)der)->getValor())
                    res = new TInteger(0);
                else if (((TReal*)izq)->getValor() > ((TInteger*)der)->getValor())
                    res = new TInteger(1);
            }
            else if ((izq->getTipo() == 1) && (der->getTipo() == 1)) {

                if (((TReal*)izq)->getValor() < ((TReal*)der)->getValor())
                    res = new TInteger(-1);
                else if (((TReal*)izq)->getValor() == ((TReal*)der)->getValor())
                    res = new TInteger(0);
                else if (((TReal*)izq)->getValor() > ((TReal*)der)->getValor())
                    res = new TInteger(1);
            }
            else
                throw string("Error: Tipos de datos no esperados para la operacion Comparacion.\n");
        }
        else if (strcmp(op,"10") == 0){ // MAYOR
            if ((izq->getTipo() == 0) && (der->getTipo() == 0))
                res = new TBool(((TInteger*)izq)->getValor() > ((TInteger*)der)->getValor());
            else if ((izq->getTipo() == 0) && (der->getTipo() == 1))
                res = new TBool (((TInteger*)izq)->getValor() > ((TReal*)der)->getValor());
            else if ((izq->getTipo() == 1) && (der->getTipo() == 0))
                res = new TBool (((TReal*)izq)->getValor() > ((TInteger*)der)->getValor());
            else if ((izq->getTipo() == 1) && (der->getTipo() == 1))
                res = new TBool (((TReal*)izq)->getValor() > ((TReal*)der)->getValor());
            else
                throw string("Error: Tipos de datos no esperados para la operacion Mayor.\n");
        }
		else if (strcmp(op,"11") == 0){ // MAYOR IGUAL
            if ((izq->getTipo() == 0) && (der->getTipo() == 0))
                res = new TBool(((TInteger*)izq)->getValor() >= ((TInteger*)der)->getValor());
            else if ((izq->getTipo() == 0) && (der->getTipo() == 1))
                res = new TBool (((TInteger*)izq)->getValor() >= ((TReal*)der)->getValor());
            else if ((izq->getTipo() == 1) && (der->getTipo() == 0))
                res = new TBool (((TReal*)izq)->getValor() >= ((TInteger*)der)->getValor());
            else if ((izq->getTipo() == 1) && (der->getTipo() == 1))
                res = new TBool (((TReal*)izq)->getValor() >= ((TReal*)der)->getValor());
            else
                throw string("Error: Tipos de datos no esperados para la operacion Mayor Igual.\n");
		}
		else if (strcmp(op,"12") == 0){ // MENOR
            if ((izq->getTipo() == 0) && (der->getTipo() == 0))
                res = new TBool(((TInteger*)izq)->getValor() < ((TInteger*)der)->getValor());
            else if ((izq->getTipo() == 0) && (der->getTipo() == 1))
                res = new TBool (((TInteger*)izq)->getValor() < ((TReal*)der)->getValor());
            else if ((izq->getTipo() == 1) && (der->getTipo() == 0))
                res = new TBool (((TReal*)izq)->getValor() < ((TInteger*)der)->getValor());
            else if ((izq->getTipo() == 1) && (der->getTipo() == 1))
                res = new TBool (((TReal*)izq)->getValor() < ((TReal*)der)->getValor());
            else
                throw string("Error: Tipos de datos no esperados para la operacion Menor.\n");
		}
        else if (strcmp(op,"13") == 0){ // MENOR IGUAL
            if ((izq->getTipo() == 0) && (der->getTipo() == 0))
                res = new TBool(((TInteger*)izq)->getValor() <= ((TInteger*)der)->getValor());
            else if ((izq->getTipo() == 0) && (der->getTipo() == 1))
                res = new TBool (((TInteger*)izq)->getValor() <= ((TReal*)der)->getValor());
            else if ((izq->getTipo() == 1) && (der->getTipo() == 0))
                res = new TBool (((TReal*)izq)->getValor() <= ((TInteger*)der)->getValor());
            else if ((izq->getTipo() == 1) && (der->getTipo() == 1))
                res = new TBool (((TReal*)izq)->getValor() <= ((TReal*)der)->getValor());
            else
                throw string("Error: Tipos de datos no esperados para la operacion Menor Igual.\n");
         }
        else if (strcmp(op,"14") == 0){ // IGUAL
            if ((izq->getTipo() == 0) && (der->getTipo() == 0))
                res = new TBool(((TInteger*)izq)->getValor() == ((TInteger*)der)->getValor());
            else if ((izq->getTipo() == 0) && (der->getTipo() == 1))
                res = new TBool (((TInteger*)izq)->getValor() == ((TReal*)der)->getValor());
            else if ((izq->getTipo() == 1) && (der->getTipo() == 0))
                res = new TBool (((TReal*)izq)->getValor() == ((TInteger*)der)->getValor());
            else if ((izq->getTipo() == 1) && (der->getTipo() == 1))
                res = new TBool (((TReal*)izq)->getValor() == ((TReal*)der)->getValor());
            else if ((izq->getTipo() == 2) && (der->getTipo() == 2))
                res = new TBool (((TString*)izq)->getValor() == ((TString*)der)->getValor());
            else if ((izq->getTipo() == 3) && (der->getTipo() == 3))
                res = new TBool (((TBool*)izq)->getValor() == ((TBool*)der)->getValor());
            else
                throw string("Error: Tipos de datos no esperados para la operacion Igual.\n");
        }
        else if (strcmp(op,"16") == 0){ // DISTINTO
            if ((izq->getTipo() == 0) && (der->getTipo() == 0))
                res = new TBool(((TInteger*)izq)->getValor() != ((TInteger*)der)->getValor());
            else if ((izq->getTipo() == 0) && (der->getTipo() == 1))
                res = new TBool (((TInteger*)izq)->getValor() != ((TReal*)der)->getValor());
            else if ((izq->getTipo() == 1) && (der->getTipo() == 0))
                res = new TBool (((TReal*)izq)->getValor() != ((TInteger*)der)->getValor());
            else if ((izq->getTipo() == 1) && (der->getTipo() == 1))
                res = new TBool (((TReal*)izq)->getValor() != ((TReal*)der)->getValor());
            else if ((izq->getTipo() == 2) && (der->getTipo() == 2))
                res = new TBool (((TString*)izq)->getValor() != ((TString*)der)->getValor());
            else if ((izq->getTipo() == 3) && (der->getTipo() == 3))
                res = new TBool (((TBool*)izq)->getValor() != ((TBool*)der)->getValor());
            else
                throw string("Error: Tipos de datos no esperados para la operacion Distinto.\n");
        }
        else if ((strcmp(op,"19") == 0) || (strcmp(op,"21") == 0)){ // AND
            if ((izq->getTipo() == 3) && (der->getTipo() == 3))                 //Bool AND Bool = Bool
                res = new TBool (((TBool*)izq)->getValor() && ((TBool*)der)->getValor());
            else
                throw string("Error: Tipos de datos no esperados para la operacion AND.\n");
        }
        else if ((strcmp(op,"20") == 0) || (strcmp(op,"22") == 0)){ // OR
            if ((izq->getTipo() == 3) && (der->getTipo() == 3))                 //Bool OR Bool = Bool
                res = new TBool (((TBool*)izq)->getValor() || ((TBool*)der)->getValor());
            else
                throw string("Error: Tipos de datos no esperados para la operacion OR.\n");
        }
        }
		return res;
	}
};

class OperadorUnario : public Expresion {
public:
    char* op;
    Expresion& rhs;
    OperadorUnario( char* op, Expresion& rhs) : rhs(rhs), op(op){}
    void a(){
        cout<<"operador unario:" <<op<<"\n";
        rhs.a();
 	}

 	string getTipo(){
        return "claseOperadorUnario";
    }

 	TipoValor* evaluar(){
        TipoValor* res;
 	    TipoValor* der = rhs.evaluar();

 	    if (der == NULL)
            throw string ("Error: La funcion evaluada no tiene valor de retorno.\n");
        else{

 	    if(strcmp(op,"7") == 0)
            if(der->getTipo() == 0) // POSITIVO
                res = new TInteger(((TInteger*)der)->getValor());
            else if(der->getTipo() == 1)
                res = new TReal(((TReal*)der)->getValor());
            else
                throw string("Error: Tipo de dato no esperado para la operacion + Unario.\n");
        else if (strcmp(op,"8") == 0) // NEGATIVO
            if(der->getTipo() == 0)
                res = new TInteger(-((TInteger*)der)->getValor());
            else if(der->getTipo() == 1)
                res = new TReal(-((TReal*)der)->getValor());
            else
                throw string("Error: Tipo de dato no esperado para la operacion Unaria Negativo.\n");
        else if ((strcmp(op,"17") == 0) || (strcmp(op,"23") == 0)) // NOT
            if (der->getTipo() == 3)
                res = new TBool (!((TBool*)der)->getValor());
            else
                throw string("Error: Tipo de dato no esperado para la operacion Unaria NOT.\n");
        else if (strcmp(op,"18") == 0) // COMPLEMENTO
            if (der->getTipo() == 0)
                res = new TBool (~((TInteger*)der)->getValor());
            else
                throw string("Error: Tipo de dato no esperado para la operacion Unaria COMPLEMENTO.\n");
        }
        return res;
 	}
};

class BloqueFuncion : public Statement {
public:
    StatementList statements;
    BloqueFuncion() {}

    void b(){
        int i;
        int s=statements.size();
        for(i=0;i<statements.size();i++)
            statements.at(i)->b();
    }

    string getTipo(){
        return "claseBloqueFuncion";
    }

	void ejecutar(){

        int i;
        for (i=0; i < statements.size(); i++)
            statements.at(i)->ejecutar();

    }
};

class Block : public Statement {
public:
    StatementList statements;
    Block() {}

    void b(){
        int i;
        int s=statements.size();
        for(i=0;i<statements.size();i++)
            statements.at(i)->b();
    }

    string getTipo(){
        return "claseBlock";
    }

	void ejecutar(){

	    ManejadorPila* mp = ManejadorPila::getInstancia();
        Pila* p = mp->obtenerPilaActual();
        p->abreScope();

	    int i;
        for (i=0; i < statements.size(); i++)
            statements.at(i)->ejecutar();
       // cout<<"----stack antes de cerrar scope-----\n";
        //p->imprimirStack();
        p->cierraScope(); //comentado para ver el stack
        //cout<<"----stack despues de cerrar scope-----\n";
    }
};


class ExpresionStatement : public Statement {
public:
    Expresion* expression;
    ExpresionStatement() { expression = NULL;};
    ExpresionStatement(Expresion* expression) : expression(expression) { }

	void b(){
        if (expression != NULL)
            expression->a();
	}

    string getTipo(){
        return "claseExpresionStatement";
    }

	void ejecutar(){
        TipoValor* res = expression->evaluar();
        //cout<<"res:" <<((TReal*)res)->getValor() << "\n";
    }

};

class IfStatment : public Statement{
public:
        Expresion& condicion;
        Block& cuerpo;
        Block& cuerpoElse;
        IfStatment(Expresion& cond,Block& cue,Block& cElse) :
         condicion(cond),cuerpo(cue),cuerpoElse(cElse){ }
        void b(){
            cout<<"if\n";
            cout<<"Condicion:\n";
            condicion.a();
            cout<<"Cuerpo:\n";
            cuerpo.b();
            cout<<"Cuerpo del Else:\n";
            cuerpoElse.b();
        }

        string getTipo(){
            return "claseIfStatment";
        }

        void ejecutar(){
            TipoValor* cond = condicion.evaluar();
            if (((TBool*)cond)->getValor())
                cuerpo.ejecutar();
            else/* if (cuerpoElse != NULL)*/
                cuerpoElse.ejecutar();
        }
};

class WhileStatment : public Statement{

    public:
        Expresion& condicion;
        Block& cuerpo;
        WhileStatment(Expresion& cond,Block& cue) : condicion(cond),cuerpo(cue){ }

        void b(){
			cout<<"while\n";
            condicion.a();
            cuerpo.b();
        }

        string getTipo(){
            return "claseWhileStatment";
        }

        void ejecutar(){
            TipoValor* cond = condicion.evaluar();
            while (((TBool*)cond)->getValor()){
                //cout<<"entre:"<<((TBool*)cond)->getValor()<<"\n";
                cuerpo.ejecutar();
                cond = condicion.evaluar();
            }
        }

};

class BloqueClase: public Statement{
public:
    StatementList statements;
    BloqueClase() {}

    void b(){
        int i;
        int s=statements.size();
        for(i=0;i<statements.size();i++)
            statements.at(i)->b();
    }


    string getTipo(){
        return "claseBloqueClase";
    }

	void ejecutar(){

//	    ManejadorPila* mp = ManejadorPila::getInstancia();
//        Pila* p = mp->obtenerPilaActual();
//        p->abreScope();
//
//	    int i;
//        for (i=0; i < statements.size(); i++)
//            statements.at(i)->ejecutar();
//        cout<<"----stack antes de cerrar scope-----\n";
//        p->imprimirStack();
//        p->cierraScope(); //comentado para ver el stack
    }

};

class ClassStatment : public Statement{

    public:
        Identificador& nombre;
        BloqueClase& cuerpo;
        ListaFunciones* funciones;
        vector<Accesor*> accesores;
        vector<Variable*> atributos;
        ClassStatment(Identificador& nom,BloqueClase& cue):nombre(nom),cuerpo(cue){ }

        void b(){
            cout<<"***Definicion de una clase***\n\n";
            cout<<"***Nombre de la clase: ";
            nombre.a();
            cout<<"\n";
            cout<<"***Cuerpo de la clase***\n\n";
            cuerpo.b();
            cout<<"\n";
             cout<<"----------------------------\n";
        }

        string getTipo(){
            return "claseClassStatment";
        }

        void ejecutar(){
           ListaClases* lc = ListaClases::getInstancia();
            int error = lc->agragarClase(this);

            if (error == 0)
                throw string("Error: Clase "+ nombre.str +" ya definida.\n");
            else{
                funciones = new ListaFunciones();
                int i;
                for (i=0;i<cuerpo.statements.size();i++){
                    Statement* s = cuerpo.statements.at(i);
                    if ((s->getTipo()=="claseFuncionDeclaracionClase")|| (s->getTipo()=="claseFuncionDeclaracionClaseConParametros")){
                       funciones->agragarFuncion(s);
                    }else if (s->getTipo()=="claseAccesor")
                       accesores.push_back((Accesor*)s);
                }
                //funciones->imprimirFunciones();
            }
        }
};



class FuncionDeclaracion : public Statement {
public:
	Identificador& nombre;
    BloqueFuncion& cuerpo;
    ExpresionStatement& retorno;
    FuncionDeclaracion( Identificador& nombr,BloqueFuncion& cuerp, ExpresionStatement& retorn) :
            nombre(nombr),cuerpo(cuerp),retorno(retorn) {}
    void b(){
        cout<<"Def funcion s/params:\n";
        cout<<"***Nombre de la funcion***\n";
        nombre.a();
        cout<<"\n--------------------------\n";
        cout<<"***Cuerpo de la funcion***\n";
        cuerpo.b();
        cout<<"--------------------------\n";
        cout<<"***Retorno de la funcion***\n";
        retorno.b();
        cout<<"\n--------------------------\n";
    }
     string getTipo(){
        return "claseFuncionDeclaracion";
    }


    void ejecutar(){
        ListaFunciones* lf = ListaFunciones::getInstancia();
        int n = lf->agragarFuncion(this); // si n=0 retornar error porq ya existe
        if (n == 0)
            throw string("Error: Funcion "+ nombre.str +" ya definida.\n");
    }
};



class DefArgs : public Statement {
public:

     listaArgsDef args;
     DefArgs() { }
     DefArgs(listaArgsDef args) :args(args){ }

    void b() {
        int i;
        int s=args.size();
        for(i=0;i<args.size();i++)
            args.at(i)->a();
    }

    string getTipo(){
        return "claseDefArgs";
    }

    void ejecutar(){


    }
};

class FuncionDeclaracionConParametros : public Statement {
public:
    Identificador& nombre;
    DefArgs& args;
    BloqueFuncion& cuerpo;
    ExpresionStatement& retorno;
    FuncionDeclaracionConParametros( Identificador& nombr,DefArgs& arg,BloqueFuncion& cuerp, ExpresionStatement& retorn) :
            nombre(nombr),args(arg),cuerpo(cuerp),retorno(retorn) { }


    void b(){
        cout<<"Def funcion c/params:\n";
        cout<<"***Nombre de la funcion***\n";
        nombre.a();
        cout<<"\n--------------------------\n";
        cout<<"***Argumentos de la funcion***\n";
        args.b();
        cout<<"\n--------------------------\n";
        cout<<"***Cuerpo de la funcion***\n";
        cuerpo.b();
        cout<<"--------------------------\n";
        cout<<"***Retorno de la funcion***\n";
        retorno.b();
        cout<<"\n--------------------------\n";
    }

    string getTipo(){
        return "claseFuncionDeclaracionConParametros";
    }

    void ejecutar(){
        ListaFunciones* lf = ListaFunciones::getInstancia();
        int n = lf->agragarFuncion(this); // si n=0 retornar error porq ya existe
        if (n == 0)
            throw string("ERROR: Funcion "+ nombre.str +" ya definida.\n");
    }
};


class CallArgs : public Statement {
public:
     ExpressionList args;
     CallArgs() { }
     CallArgs(ExpressionList args) :args(args){ }
    void b(){
		int i;
        int s=args.size();
        for(i=0;i<args.size();i++)
            args.at(i)->a();
    }

    string getTipo(){
        return "claseCallArgs";
    }

    void ejecutar(){
    }
};


class CallFunctionSinParametros : public Expresion { /*solo para el gets*/
public:
    string nombre;

    CallFunctionSinParametros( string nombr) :    nombre(nombr) { }

    void a(){
        cout<<"Llamada a  funcion s/params:\n";
        cout<<"***Nombre de la funcion***\n";
        cout<<nombre;
        cout<<"\n--------------------------\n";
    }

    string getTipo(){
        return "claseCallFunctionSinParametros";
    }

	int Es_Numero (string str){
        int i = 0;

        bool in = false;
        bool fl = false;
        while (i < str.length()){
                if (str[i] >= '0' && str[i] <= '9')
                    in = true;
                else if (str[i] == '.'){
                    fl=true;
                    in = false;
                }
                else {
                    return 0;
                }
                i++;
        }
        if (fl)
            return 2;
        else if (in)
            return 1;
        else
            return 0;
	}

    TipoValor* evaluar(){
         string str;
          TipoValor* t;
          cin >> str;


           if ((Es_Numero(str.c_str())) == 1){
                //cout <<"Su entrada es un numero  entero"<< str<<"\n";
                int ent = atoi(str.c_str());
                t  = new TInteger(ent);
          }else if (Es_Numero(str.c_str()) == 2){
                //cout <<"Su entrada es un numero  float"<< str<<"\n";
                 float f = atof(str.c_str());
                 t  = new TReal(f);
           }else{
                //cout <<"Su entrada es un string\n "<< str<<"\n";
                t  = new TString(str);

           }

            return t;
    }
};

class CallFunctionConParametros : public Expresion {
public:
    Identificador& nombre;
    CallArgs* args;

    CallFunctionConParametros( Identificador& nombr,CallArgs* arg):
            nombre(nombr),args(arg) { }


    void a(){
        cout<<"Llamada a  funcion c/params:\n";
        cout<<"***Nombre de la funcion***\n";
        nombre.a();
        cout<<"\n--------------------------\n";
        cout<<"***Parametros la funcion***\n";
        if(args!=NULL)
            args->b();
        cout<<"\n--------------------------\n";
    }

    string getTipo(){
        return "claseCallFunctionConParametros";
    }


    TipoValor* evaluar(){

        ManejadorPila* mp = ManejadorPila::getInstancia();
        Pila* pNueva = mp->crearPilaNueva();
        pNueva->abreScope();

        TipoValor* ret = NULL;
        ListaFunciones* lf = ListaFunciones::getInstancia();
        Statement* f = lf->buscarFuncion(nombre.str);

        if (f != NULL){

            if (args != NULL){
                FuncionDeclaracionConParametros* fDef = (FuncionDeclaracionConParametros*)f;
                int cantArgsDef = fDef->args.args.size();
                int cantsArgsCall = args->args.size();
                if (cantArgsDef != cantsArgsCall){
                    //char* c;
                    //itoa(cantArgsFun,c,10);
                    throw string("ERROR: La funcion tiene cantidad incorrecta de parametros.\n");
                }else{

                    int i;
                    for (i=0;i<cantsArgsCall;i++){

                        string var = fDef->args.args.at(i)->str;
                        Expresion* e =args->args.at(i);
                        TipoValor* exp = e->evaluar(); //evaluo los parametros de la llamada

                        int type = exp->getTipo();
                        if (type == 0)
                              pNueva->agregarVarPila(var,((TInteger*)exp)->getValor());
                        else if (type == 1)
                              pNueva->agregarVarPila(var,((TReal*)exp)->getValor());
                        else if (type == 2)
                              pNueva->agregarVarPila(var,((TString*)exp)->getValor());
                        else if (type == 3)
                              pNueva->agregarVarPila(var,((TBool*)exp)->getValor());
                         else if (type == 4)
                              pNueva->agregarVarPila(var,(TArreglo*)exp);

                        //pNueva->imprimirStack();

                    }
                    mp->agregarPilaCreada(pNueva);
                    fDef->cuerpo.ejecutar();

                    if (fDef->retorno.expression != NULL){
                        ret = fDef->retorno.expression->evaluar();
                    }

                    pNueva->cierraScope();
                    mp->borrarPila();
                }
            }else{ //funcion sin parametros

                    FuncionDeclaracion* fDef = (FuncionDeclaracion*)f;
                    mp->agregarPilaCreada(pNueva);
                    fDef->cuerpo.ejecutar();
                    if (fDef->retorno.expression != NULL)
                        ret = fDef->retorno.expression->evaluar();

                    pNueva->cierraScope();
                    mp->borrarPila();
            }

        }else{
                throw string("Error: La funcion " + nombre.str +" no esta definida.\n");
        }
        return ret;
    }
};

class Puts : public Expresion { /*solo para el gets*/
public:
    string nombre;
    Expresion& exp;

    Puts( string nombr,Expresion& exp) :    nombre(nombr), exp(exp) { }

    void a(){
        cout<<"Llamada a  Puts:\n";
        cout<<"***Nombre de la funcion***\n";
        cout<<nombre;
        cout<<"\n--------------------------\n";
        cout<<"***Expresion del puts***\n";
        exp.a();
        cout<<"\n--------------------------\n";
    }

    string getTipo(){
        return "clasePuts";
    }

    TipoValor* evaluar(){

       TipoValor* expresion = exp.evaluar();
       if (expresion == NULL){
           throw string("Error: La variable no esta definida.\n");
       }else{
           int type = expresion->getTipo();
           //cout << "El puts va a imprimir: ";
           if (type == 0)
                 cout << ((TInteger*)expresion)->getValor() << "\n";
           else if (type == 1)
                 cout << ((TReal*)expresion)->getValor() << "\n";
           else if (type == 2)
                 cout << ((TString*)expresion)->getValor() << "\n";
           else if (type == 3)
               if (((TBool*)expresion)->getValor() == 1)
                   cout << "true" << "\n";
               else
                   cout << "false" << "\n";

            else if (type == 4)
                ((TArreglo*)expresion)->imprimir();
       }
       return NULL;
   }
};


class PutsCmd : public Expresion {
public:
    string nombre;
    Expresion& exp;

    PutsCmd( string nombr,Expresion& exp) :    nombre(nombr), exp(exp) { }

    void a(){
        cout<<"Llamada a Puts Command:\n";
        cout<<"***Nombre de la funcion***\n";
        cout<<nombre;
        cout<<"\n--------------------------\n";
        cout<<"***Expresion del puts***\n";
        exp.a();
        cout<<"\n--------------------------\n";
    }

    string getTipo(){
        return "clasePutsCmd";
    }

    TipoValor* evaluar(){
        TipoValor* expresion = exp.evaluar();

         if (expresion == NULL)
            throw string ("Error: La variable no esta definida.\n");

        else{
        string comando = ((TString*)expresion)->getValor();
        const char * c = comando.c_str();
        system(c);
        return NULL;
        }
    }
};



class Variable : public Expresion{
public:
    Identificador* idLocal;
	IdentificadorGlobal* idGlobal;

	ArregloPosicion* arrPosi;
	Atributo* atributo;
	AtributoAsig* atributoAsig;

    Variable (Identificador* idLocal): idLocal(idLocal), idGlobal(NULL),arrPosi(NULL),atributo(NULL),atributoAsig(NULL){}
    Variable (IdentificadorGlobal* idGlobal): idGlobal(idGlobal),idLocal(NULL),arrPosi(NULL),atributo(NULL),atributoAsig(NULL) {}
    Variable (ArregloPosicion* arrPosi): arrPosi(arrPosi),idLocal(NULL),idGlobal(NULL),atributo(NULL),atributoAsig(NULL) {}
    Variable (Atributo* atributo):atributo(atributo), arrPosi(NULL),idLocal(NULL),idGlobal(NULL),atributoAsig(NULL) {}
    Variable (AtributoAsig* atributoAsig):atributoAsig(atributoAsig),atributo(NULL), arrPosi(NULL),idLocal(NULL),idGlobal(NULL) {}


    string getNombre(){
        if(idLocal!=NULL)
            return idLocal->str;
        else if(idGlobal!=NULL)
            return idGlobal->str;
    }

    string getTipo(){
        return "claseVariable";
    }


    void a(){
        if(idLocal!=NULL)
            idLocal->a();
        if(idGlobal!=NULL)
        idGlobal->a();
        //if(arrPosi!=NULL)
           // arrPosi->a();
        if(atributo!=NULL)
            atributo->a();
        if(atributoAsig!=NULL)
            atributoAsig->a();
    }

    TipoValor* evaluar(){

        ManejadorPila* mp = ManejadorPila::getInstancia();
        TipoValor* res = NULL;

        if (idLocal!=NULL){
            Pila* p = mp->obtenerPilaActual();
            res = p->buscarVariable(idLocal->str);
            if (res == NULL)
                throw string("Error: Variable "+ idLocal->str+" no inicializada.\n");
        }else if (idGlobal!=NULL){
            res = mp->obtenerVariableGlobal(idGlobal->str);
            if (res == NULL)
                throw string("Error: Variable "+ idGlobal->str+" no inicializada.\n");
        } else
            throw string("Error: Variable no inicializada.\n");

        return res;
    }
};

class ArregloPosicion : public Expresion{
public:
    Expresion* id;
    Expresion& exp;

    ArregloPosicion (Expresion* id, Expresion& exp) : id(id),exp(exp) {}
    void a(){
        cout<<"Arreglo Posicion:\n";
        id->a();
        exp.a();
    }
    string getTipo(){
        return "claseArregloPosicion";
    }

    TipoValor* evaluar(){

        Variable* var = (Variable*)id;

        TipoValor* res = NULL;
        ManejadorPila* mp = ManejadorPila::getInstancia();
        string nom = var->getNombre();
        TipoValor* a;
        if (var->idLocal!=NULL){
           Pila* p = mp->obtenerPilaActual();
           a = p->buscarVariable(nom);
           if (a==NULL)
               throw string("Error: Arreglo "+ nom +" no definido.\n");
       }else if (var->idGlobal!=NULL){
           a = mp->obtenerVariableGlobal(var->idGlobal->str);
           if (a==NULL)
               throw string("Error: Arreglo "+ nom +" no definido.\n");
       }

        TArreglo* arr = (TArreglo*)a;
        TipoValor* indiceRes = exp.evaluar();
        if (indiceRes->getTipo()!=0)
                throw string("Error: Los indices del arreglo deben ser enteros.\n");
        else
            res = arr->getPosicion(((TInteger*)indiceRes)->getValor());


        return res;
    }
};

class Asignacion : public Expresion{
public:
    Variable& var;
    Operador& op;
    Expresion& expresion;
    Asignacion (Variable& var,Operador& op,Expresion& expresion) : var(var),op(op),expresion(expresion) {}
    void a(){
        cout<< "***Asignacion***\n";
        cout<< "Lado Izquiero\n";
        var.a();
		op.a();
        cout<< "Lado Derecho\n";
        expresion.a();
        cout<< "----------------\n";
    }

    string getTipo(){
        return "claseAsignacion";
    }

    TipoValor* evaluar(){
	 	TipoValor* exp;
         if(op.op == "0"){
             exp = expresion.evaluar(); // solo si es igual

         }
        else if (op.op == "1")
            exp = (new OperacionBinaria(var,"1",expresion))->evaluar();
        else if (op.op == "2")
            exp = (new OperacionBinaria(var,"2",expresion))->evaluar();
        else if (op.op == "3")
            exp = (new OperacionBinaria(var,"3",expresion))->evaluar();
        else if (op.op == "4")
            exp = (new OperacionBinaria(var,"4",expresion))->evaluar();
        else if (op.op == "5")
            exp = (new OperacionBinaria(var,"5",expresion))->evaluar();
        else if (op.op == "6")
            exp = (new OperacionBinaria(var,"6",expresion))->evaluar();
        else if (op.op == "7")
            exp = (new OperacionBinaria(var,"19",expresion))->evaluar();//and bool
        else if (op.op == "8")
            exp = (new OperacionBinaria(var,"20",expresion))->evaluar();//or bool
        else if (op.op == "9")
            exp = (new OperacionBinaria(var,"21",expresion))->evaluar();





        if (exp == NULL)
            throw string ("Error: La funcion evaluada no tiene valor de retorno.\n");
        else{
            ManejadorPila* mp = ManejadorPila::getInstancia();

            int type = exp->getTipo();

            if (var.idLocal != NULL){

                Pila* p = mp->obtenerPilaActual();

                if (type == 0)
                      p->agregarVarPila(var.idLocal->str,((TInteger*)exp)->getValor());
                else if (type == 1)
                      p->agregarVarPila(var.idLocal->str,((TReal*)exp)->getValor());
                else if (type == 2)
                      p->agregarVarPila(var.idLocal->str,((TString*)exp)->getValor());
                else if (type == 3)
                      p->agregarVarPila(var.idLocal->str,((TBool*)exp)->getValor());
                else if (type == 4)
                      p->agregarVarPila(var.idLocal->str,(TArreglo*)exp);


            }else if (var.idGlobal != NULL){

                VarPila* v = new VarPila();
                v->setId(var.idGlobal->str);
                v->setTipo(type);

                if (type == 0)
                    v->setValor(((TInteger*)exp)->getValor());
                else if (type == 1)
                    v->setValor(((TReal*)exp)->getValor());
                else if (type == 2)
                    v->setValor(((TString*)exp)->getValor());
                else if (type == 3)
                    v->setValor(((TBool*)exp)->getValor());
                else if (type == 4)
                    v->setValor((TArreglo*)exp);

                mp->agragarVariableGlobal(v);

            }else if (var.arrPosi != NULL){

                Pila* p = mp->obtenerPilaActual();
                Variable* v = (Variable*)var.arrPosi->id;
                string nom = v->getNombre();
                TipoValor* a;
                if(v->idLocal!=NULL)
                    a = p->buscarVariable(nom);
                else if(v->idGlobal!=NULL)
                    a = mp->obtenerVariableGlobal(v->idGlobal->str);

                TArreglo* arr = (TArreglo*)a;
                TipoValor* indiceRes = var.arrPosi->exp.evaluar();
                if (indiceRes->getTipo()!=0)
                    throw string("Error: los indices del arreglo deben ser enteros.\n");
                else
                    arr->asignarPosicion(((TInteger*)indiceRes)->getValor(),exp);
            }
        }
        return exp;
    }
};

class FuncionDeclaracionClase : public Statement {
public:
	Identificador& nombre;
    BloqueFuncion& cuerpo;
    ExpresionStatement& retorno;
    FuncionDeclaracionClase( Identificador& nombr,BloqueFuncion& cuerp, ExpresionStatement& retorn) :
            nombre(nombr),cuerpo(cuerp),retorno(retorn) {}

    void b(){
       /* cout<<"Def funcion Clase s/params:\n";
        cout<<"***Nombre de la funcion***\n";
        nombre.a();
        cout<<"\n--------------------------\n";
        cout<<"***Cuerpo de la funcion***\n";
        cuerpo.b();
        cout<<"--------------------------\n";
        cout<<"***Retorno de la funcion***\n";
        retorno.b();
        cout<<"\n--------------------------\n";*/

    }

    string getTipo(){
        return "claseFuncionDeclaracionClase";
    }

    vector<Variable*> getAtributosFuncion(){
        vector<Variable*> res;
        int i;
        for (i=0;i<cuerpo.statements.size();i++){
            Statement* s = cuerpo.statements.at(i);
            if (s->getTipo() == "claseExpresionStatement"){
                ExpresionStatement* es = ((ExpresionStatement*)s);
                Expresion* e = es->expression;
                if(e->getTipo()=="claseAsignacion"){
                    Asignacion* as = (Asignacion*)e;
                    Variable* v = &(as->var);
                    if (v->atributo != NULL){
                       res.push_back(v);
                    }
                }
            }
        }
        return res;
    }

    void ejecutar(){

    }
};

class FuncionDeclaracionClaseConParametros : public Statement {
public:
    Identificador& nombre;
    DefArgs& args;
    BloqueFuncion& cuerpo;
    ExpresionStatement& retorno;
    FuncionDeclaracionClaseConParametros( Identificador& nombr,DefArgs& arg,BloqueFuncion& cuerp, ExpresionStatement& retorn) :
            nombre(nombr),args(arg),cuerpo(cuerp),retorno(retorn) { }


    void b(){
        cout<<"Def funcion De Clase c/params:\n";
        cout<<"***Nombre de la funcion***\n";
        nombre.a();
        cout<<"\n--------------------------\n";
        cout<<"***Argumentos de la funcion***\n";
        args.b();
        cout<<"\n--------------------------\n";
        cout<<"***Cuerpo de la funcion***\n";
        cuerpo.b();
        cout<<"--------------------------\n";
        cout<<"***Retorno de la funcion***\n";
        retorno.b();
        cout<<"\n--------------------------\n";
    }

    string getTipo(){
        return "claseFuncionDeclaracionClaseConParametros";
    }

    vector<Variable*> getAtributosFuncion(){
        vector<Variable*> res;
        int i;
        for (i=0;i<cuerpo.statements.size();i++){
            Statement* s = cuerpo.statements.at(i);
            if (s->getTipo() == "claseExpresionStatement"){
                ExpresionStatement* es = ((ExpresionStatement*)s);
                Expresion* e = es->expression;
                if(e->getTipo()=="claseAsignacion"){
                    Asignacion* as = (Asignacion*)e;
                    Variable* v = &(as->var);
                    if (v->atributo != NULL){
                       res.push_back(v);
                    }
                }
            }
        }
        return res;
    }

    void ejecutar(){
//        ListaFunciones* lf = ListaFunciones::getInstancia();
//        int n = lf->agragarFuncion(this); // si n=0 retornar error porq ya existe
//        if (n == 0)
//            throw string("ERROR: Funcion ya definida.\n");
    }
};


class ListaAccesores : public Statement {
    public:
     listaArgsDef args;
     ListaAccesores() { }
     ListaAccesores(listaArgsDef args) :args(args) { }

    void b() {
        int i;
        int s=args.size();
        for(i=0;i<args.size();i++)
            args.at(i)->a();
    }

    string getTipo(){
        return "claseListaAccesores";
    }

    void ejecutar(){
    }
};

class Accesor: public Statement{
public:
    string tipo;
    ListaAccesores listaAcc;
    Accesor(string tipo, ListaAccesores listaAcc): tipo(tipo), listaAcc(listaAcc) { }

    void b() {
        cout<<"Tipo de Accesor:";
        cout<<tipo;
        cout<<"\n";
        cout<<"\nLista de Accesores:\n\n";
        listaAcc.b();
         cout<<"-------------------\n";

    }

    string getTipo(){
        return "claseAccesor";
    }


    void ejecutar(){
    }
};

class ListaExpresiones : Statement{
public:
    ExpressionList listaExp;

    ListaExpresiones () {}

    void b() {
        int i;
        int s=listaExp.size();
        if(s==0)
            cout<<"Arreglo vacio\n";
        for(i=0;i<listaExp.size();i++)
            listaExp.at(i)->a();
    }

    string getTipo(){
        return "claseListaExpresiones";
    }

    void ejecutar(){

    }

};

class ArregloDec : public Expresion{
public:
    ListaExpresiones& lista;

    ArregloDec (ListaExpresiones& lista) : lista(lista) {}
    void a(){
        lista.b();
    }

    string getTipo(){
        return "claseArregloDec";
    }

    TipoValor* evaluar(){

        TipoValor* arr = new TArreglo();
        int i;
        for (i=0;i<lista.listaExp.size();i++){

            TipoValor* res = lista.listaExp.at(i)->evaluar();
            ((TArreglo*)arr)->asignarPosicion(i,res);
        }
        return arr;
    }

};

class  CallFunctionClass: public Expresion{
public:
        Variable& nombre;
        CallFunctionConParametros& llamada; /*siempre va a ser un CallFunctionConParametros*/
        CallFunctionClass(Variable& nombre,CallFunctionConParametros& llamada):nombre(nombre),llamada(llamada) {}
        void a(){
            cout<<"Llamada a funcion de una clase\n";
            cout<<"Nombre del objeto:\n";
            nombre.a();
            //cout<<"Nombre del objeto:\n";
            llamada.a();

        }

    string getTipo(){
        return "claseCallFunctionClass";
    }

    TipoValor* evaluar(){
        TipoValor* res = NULL;

        /*if (llamada.nombre.str == "new"){

            ListaClases* lc = ListaClases::getInstancia();
            ClassStatment* clase = lc->buscarClase(nombre.idLocal->str);
            Statement* init = clase->funciones->buscarFuncion("initialize");

            TObjeto* objeto = new TObjeto();
            Pila* atr = new Pila();

            if (init->getTipo() == "claseFuncionDeclaracionClaseConParametros"){
                FuncionDeclaracionClaseConParametros* finit = (FuncionDeclaracionClaseConParametros*)init;
                vector<Variable*> atributos = finit->getAtributosFuncion();
                /*int i;
                for (i=0;i<atributos.size();i++)
                    cout<<"atributo:"<<atributos.at(i)->atributo->str<<"\n";*/


         /*       int i;
                cout<<llamada.args->args.size();
                for (i=0;i<llamada.args->args.size();i++){

                    string var = atributos.at(i)->atributo->str;
                    cout<<var;
                    Expresion* e =llamada.args->args.at(i);
                    TipoValor* exp = e->evaluar(); //evaluo los parametros de la llamada

                    int type = exp->getTipo();
                    cout<<type;
                    if (type == 0)
                          atr->agregarVarPila(var,((TInteger*)exp)->getValor());
                    else if (type == 1)
                          atr->agregarVarPila(var,((TReal*)exp)->getValor());
                    else if (type == 2)
                          atr->agregarVarPila(var,((TString*)exp)->getValor());
                    else if (type == 3)
                          atr->agregarVarPila(var,((TBool*)exp)->getValor());
                     else if (type == 4)
                          atr->agregarVarPila(var,(TArreglo*)exp);

                }
            }

            objeto->setPila(atr);
            res = objeto;

        }*/

       return res;
    }

};

