//Grupo 19 
#ifndef PARSER_H
#define PARSER_H

#define ASIG_PARENTESIS	1
#define ASIG_PUNTO		2

#include "tipo.h"
#include "contexto.h"
#include "valor.h"
#include "variable.h"
#include <math.h>
 
class NodoExp;
class NodoSentencia;
class Parametro;
class NodoFuncion;
class NodoListaExp;
class NodoListVar;
class NodoListaArgumento;

typedef std::vector<NodoSentencia*> listaSentencia;

enum EnumOperacionBinaria
{
	mas,menos,mul,ediv,potencia,modulo,concatenacion,menor,menorigual,mayor,mayorigual,igual,distinto,eand,eor
};

string enumOperBin2Str(EnumOperacionBinaria op);

enum EnumOperacionUnaria
{
	 menosu,enot,largo
};
string enumOperUn2Str(EnumOperacionUnaria op);

class Nodo {
public:

public:
    //virtual ~Nodo();
    virtual Valor* evaluar(Contexto *ctx) = 0;

};

class NodoExp : public Nodo {
	public:
	virtual void imprimir() = 0;
};

class NodoSentencia : public Nodo {

};

class NodoBloque : public NodoExp {
public:
    listaSentencia sentencias;
    NodoBloque() {};
    void imprimir() {};
    Valor* evaluar(Contexto *contexto);
    Valor* evaluar(Contexto *contexto,vector<Variable>);

};

class NodoInteger : public NodoExp {
public:
    int valor;
    NodoInteger(int valor) : valor(valor) { } //constructor asigna valor
    Valor* evaluar(Contexto *contexto);
	void imprimir() {};
};

class NodoBool : public NodoExp {
public:
    bool valor;
    NodoBool(bool valor) : valor(valor) { }
    Valor* evaluar(Contexto *contexto);
    void imprimir() {};
};

class NodoReal : public NodoExp {
public:
    float valor;
    NodoReal(float valor) : valor(valor) { }
    Valor* evaluar(Contexto *contexto);
    void imprimir() {};
};

class NodoString : public NodoExp {
public:
    char* valor;
    NodoString(char* valor) : valor(valor) { }
    Valor* evaluar(Contexto *contexto);
    void imprimir() {};
};

class NodoNIL : public NodoExp {
public:
    int valor;
    NodoNIL(int valor) : valor(valor) { }
    Valor* evaluar(Contexto *contexto);
    void imprimir() {};
};

class NodoVariable : public NodoExp {
public:

	NodoVariable(char* nombre) : nombre(nombre) {}
        Valor* evaluar(Contexto *contexto);
        void imprimir() {};
private:
	char* nombre;

};

class NodoVariableLocal : public NodoExp {
public:

	NodoVariableLocal(char* nombre) : nombre(nombre) {}
    Valor* evaluar(Contexto *contexto);
    void imprimir() {};
private:
	char* nombre;

};


typedef std::vector<char*> listaVar;

class NodoListaVar : public NodoSentencia {
public:
	listaVar variables;
	NodoListaVar(){};
    	Valor* evaluar(Contexto *ctx);
    	~NodoListaVar();	
};


class NodoVariablesLocal : public NodoSentencia
{
public:

     NodoVariablesLocal (int linea, NodoListaArgumento * listaArgumentos, NodoListaExp *expListaDerecha):linea(linea), listaArgumentos(listaArgumentos), expListaDerecha(expListaDerecha) {};
     NodoVariablesLocal (int linea, NodoListaArgumento * listaArgumentos):linea(linea), listaArgumentos(listaArgumentos), expListaDerecha(NULL) {};
	~NodoVariablesLocal();
	Valor* evaluar(Contexto *ctx);
        void imprimir();

private:
    int linea;
	NodoListaArgumento* listaArgumentos;
	NodoListaExp *expListaDerecha;
};

class NodoAsignacion : public NodoSentencia
{
public:

        NodoAsignacion (int linea, NodoListaVar * listaVariables, NodoListaExp *expListaDerecha):linea(linea), listaVariables(listaVariables), expListaDerecha(expListaDerecha) {};
	~NodoAsignacion();
	Valor* evaluar(Contexto *ctx);
        void imprimir();

private:
        int linea;
	NodoListaVar* listaVariables;
	NodoListaExp *expListaDerecha;
};

class ExpresionSentencia : public NodoSentencia {
public:

    ExpresionSentencia(NodoExp* exp) : exp(exp) { };
    ~ExpresionSentencia();		
    Valor* evaluar(Contexto *ctx);
private:
	NodoExp* exp;

};


class NodoReturn : public NodoSentencia {
public:
    NodoReturn(NodoExp* valor): valor(valor) {};
    ~NodoReturn();	
    Valor* evaluar(Contexto *ctx);
private:
    NodoExp* valor;	
};

class NodoOperacionUnaria : public NodoExp{
public:
	NodoOperacionUnaria (int linea, EnumOperacionUnaria op, NodoExp *exp): linea(linea), op(op), exp(exp){};
	~NodoOperacionUnaria();
	Valor* evaluar(Contexto *ctx);
    void imprimir(){cout<<enumOperUn2Str(op)<<" "; if (exp == NULL) cout << "NULL \n"; else exp->imprimir();};
private:
	int linea;
	EnumOperacionUnaria op;
	NodoExp *exp;
};

class NodoOperacionBinaria : public NodoExp{
public:
	NodoOperacionBinaria (int linea, EnumOperacionBinaria op, NodoExp *expIzq, NodoExp *expDer): linea(linea), op(op), expDer(expDer),expIzq(expIzq) {};
	~NodoOperacionBinaria();
	Valor* evaluar(Contexto *ctx);
    void imprimir();
private:
	int linea;
	EnumOperacionBinaria op;
	NodoExp *expDer;
	NodoExp *expIzq;
};

class NodoFuncionAsignada : public NodoExp {
public:
    NodoBloque* bloqueFuncion;
    NodoListaArgumento*  listaArgumento;
    void imprimir(){};
    NodoFuncionAsignada(NodoBloque* bloqueFuncion, NodoListaArgumento* listaArgumento ): bloqueFuncion(bloqueFuncion),listaArgumento(listaArgumento) {};
    NodoFuncionAsignada( NodoBloque* bloqueFuncion): bloqueFuncion(bloqueFuncion) {};
    ~NodoFuncionAsignada();	
    Valor* evaluar(Contexto *ctx);
};

//lista_de_nombres IN explist DO bloque
class NodoForIn : public NodoSentencia {
public:
    NodoForIn(int linea,
		NodoListaArgumento* listaArgumento, 
		NodoExp* tabla, 
		NodoBloque* bloque): linea(linea), listaArgumento(listaArgumento), tabla(tabla),  bloque(bloque) {};
    ~NodoForIn();	
    Valor* evaluar(Contexto *ctx);
private:
	int linea;
	NodoListaArgumento* listaArgumento;
        NodoExp* tabla;
	NodoBloque* bloque;
};


class NodoFor : public NodoSentencia {
public:
    NodoFor(int linea,
                        char * vName,
			NodoExp* inicio, 
			NodoExp* fin, 
			NodoExp* incremento, 
			NodoBloque* bloque): vName(vName),linea(linea), inicio(inicio), fin(fin), 
								 incremento(incremento), bloque(bloque) {};
    ~NodoFor();	
    Valor* evaluar(Contexto *ctx);
private:
        char * vName;
	int linea;
	NodoExp* inicio;
	NodoExp* fin;
	NodoExp* incremento;
	NodoBloque* bloque;
};


class NodoIf : public NodoSentencia {
public:
    NodoIf(	int linea, 
                        NodoExp* condicion, 
			NodoBloque* bloqueThen,
			NodoBool* llevaElse,
			NodoBloque* bloqueElse): linea(linea),condicion(condicion), bloqueThen(bloqueThen),
									llevaElse(llevaElse), bloqueElse(bloqueElse) {};
    ~NodoIf();	
    Valor* evaluar(Contexto *ctx);
private:
        int linea;
	NodoExp* condicion;
	NodoBloque* bloqueThen;
	NodoBool* llevaElse;
	NodoBloque* bloqueElse;
};


typedef std::vector<char*> listaArgumento;

class NodoListaArgumento : public NodoSentencia {
public:
	listaArgumento argumentos;
	NodoListaArgumento(){};
    	Valor* evaluar(Contexto *ctx);
    	~NodoListaArgumento();	
};



typedef std::vector<NodoExp*> listaExp;

class NodoListaExp : public NodoSentencia {
public:
	listaExp expresiones;
	NodoListaExp(){};
    	Valor* evaluar(Contexto *ctx);
    	~NodoListaExp();	
};
class NodoLlamarFuncion : public NodoSentencia {
public:
    NodoLlamarFuncion(int linea, char* nombre, NodoListaExp* listaLlamarArgumentos): nombre(nombre),listaLlamarArgumentos(listaLlamarArgumentos), linea(linea) {};
    ~NodoLlamarFuncion();	
    Valor* evaluar(Contexto *ctx);
private:
        int linea;
    	char* nombre;	
	NodoListaExp* listaLlamarArgumentos;

};

class NodoFuncion : public NodoSentencia {
public:
    int linea;
    char* nombre;	 
    NodoBloque* bloqueFuncion;
    NodoListaArgumento*  listaArgumento;

    NodoFuncion(int linea, char* nombre, NodoBloque* bloqueFuncion, NodoListaArgumento* listaArgumento ): linea(linea), nombre(nombre),bloqueFuncion(bloqueFuncion),listaArgumento(listaArgumento) {};
    NodoFuncion(int linea, char* nombre, NodoBloque* bloqueFuncion):linea(linea), nombre(nombre),bloqueFuncion(bloqueFuncion) {};
    ~NodoFuncion();	
    Valor* evaluar(Contexto *ctx);

};

class NodoCampoTable : public NodoSentencia {
public:
        NodoExp* ladoIzq;
        NodoExp* ladoDer;

	NodoCampoTable(NodoExp* ladoIzq, NodoExp* ladoDer): ladoIzq(ladoIzq), ladoDer(ladoDer){};
        ~NodoCampoTable();	
        Valor* evaluar(Contexto *ctx);
        void imprimir() {};
};

typedef std::vector<NodoCampoTable*> listaCampos;

class NodoListaTable : public NodoSentencia {
public:
        listaCampos campos;

	NodoListaTable(int linea) : linea(linea) {};
        ~NodoListaTable();	
        Valor* evaluar(Contexto *ctx);
        void imprimir() {};
private:
    int linea;
};

class NodoTable : public NodoExp {
public:
        NodoListaTable& campos;

        NodoTable(NodoListaTable& campos) : campos(campos) {};
        ~NodoTable();
        Valor* evaluar(Contexto *contexto);
        void imprimir() {};
};

class NodoAsignacionTabla : public NodoSentencia {
public:
	NodoAsignacionTabla(int linea, char* nombreTabla, NodoExp* clave, NodoExp* valor, int tipoAsignacion) : 
           linea(linea), nombreTabla(nombreTabla), clave(clave), valor(valor), tipoAsignacion(tipoAsignacion) {};
    ~NodoAsignacionTabla();
    
	Valor* evaluar(Contexto *contexto);
    void imprimir() {};
private:
	char* nombreTabla;
	NodoExp* clave;
	NodoExp* valor;
	int tipoAsignacion;
    int linea; 
};

class NodoObtenerValorTabla : public NodoExp {
public:
	NodoObtenerValorTabla(char* nombreTabla, NodoExp* clave) : linea(linea), nombreTabla(nombreTabla), clave(clave) {};
    ~NodoObtenerValorTabla();
    
	Valor* evaluar(Contexto *contexto);
    void imprimir() {};
private:
	char* nombreTabla;
	NodoExp* clave;
    int linea;
};

#endif


