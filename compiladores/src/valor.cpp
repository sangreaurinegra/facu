#include <iostream>
#include <cmath>
#include <cstring>
#include "valor.h"
#include "contexto.h"
#include "parser.h"

Valor::~Valor()
{}

/***** VALOR INTEGER   *******/
ValorInteger::ValorInteger(int v) : valor(v) {}
ValorInteger::~ValorInteger() {}

void ValorInteger::imprimir() const { cout << valor; }
Tipo* ValorInteger::getTipo() const { return new TipoInt; }
Valor* ValorInteger::copiar() const { return new ValorInteger(valor); }
int ValorInteger::getValorInteger() const { return valor; }

/***** VALOR BOOL   *******/
ValorBool::ValorBool(bool v) : valor(v) {}
ValorBool::~ValorBool() {}

void ValorBool::imprimir() const { (valor) ? cout << "true" : cout <<"false"; }
Tipo* ValorBool::getTipo() const { return new TipoBool; }
Valor* ValorBool::copiar() const { return new ValorBool(valor); }
bool ValorBool::getValorBool() const { return valor; }

/***** VALOR REAL   *******/
ValorReal::ValorReal(float v) : valor(v) {}
ValorReal::~ValorReal() {}

void ValorReal::imprimir() const { cout << valor; }
Tipo* ValorReal::getTipo() const { return new TipoReal; }
Valor* ValorReal::copiar() const { return new ValorReal(valor); }
float ValorReal::getValorReal() const { return valor; }

/***** VALOR STRING   *******/
ValorString::ValorString(string v) : valor(v) {}
ValorString::~ValorString() {}

void ValorString::imprimir() const { cout  <<valor; }
Tipo* ValorString::getTipo() const { return new TipoString; }
Valor* ValorString::copiar() const { return new ValorString(valor); }
string ValorString::getValorString() const { return valor; }


/***** VALOR NIL  *******/
ValorNIL::ValorNIL(int v) : valor(v) {}
ValorNIL::~ValorNIL() {}

void ValorNIL::imprimir() const { cout << "nil"; }
Tipo* ValorNIL::getTipo() const { return new TipoNIL; }
Valor* ValorNIL::copiar() const { return new ValorNIL(valor); }
int ValorNIL::getValorNIL() const { return valor; }

/***** VALOR TABLE  *******/
ValorTable::ValorTable() { }

ValorTable::~ValorTable() {
        if (tableConClave != NULL){
                elementsTable* tmp = tableConClave->sig;
	        while (tmp != NULL) {
                        delete tableConClave;
                        tableConClave = tmp;	                
                        tmp = tmp->sig;
	        }
                delete tableConClave;
        }
        if (tableSinClave != NULL){
                elementsTable* tmp = tableSinClave->sig;
	        while (tmp != NULL) {
                        delete tableSinClave;
                        tableSinClave = tmp;	                
                        tmp = tmp->sig;
	        }
                delete tableSinClave;
        }
}

// inserta al final de la lista de elementos con clave
void ValorTable::insertElemConClave(ElemTable* elem) {
    elementsTable* e = new elementsTable;
	e->elem	= elem;
	e->sig = NULL;
	if (tableConClave == NULL){
		tableConClave = e;
        tableConClave->cantElem = 1;
	}else{
        elementsTable* tmp = tableConClave;
        tableConClave->cantElem = tableConClave->cantElem + 1;
        while (tmp->sig != NULL) { tmp = tmp->sig; }
        tmp->sig = e;
    }
}

// inserta al final de la lista de elementos sin clave
void ValorTable::insertElemSinClave(ElemTable* elem) {
    elementsTable* e = new elementsTable;
	e->elem	= elem;
	e->sig = NULL;
	if (tableSinClave == NULL){
		tableSinClave = e;
        tableSinClave->cantElem = 1;
	}else{
        elementsTable* tmp = tableSinClave;
        tableSinClave->cantElem = tableSinClave->cantElem + 1;
	    while (tmp->sig != NULL) { tmp = tmp->sig; }
        tmp->sig = e;
    }
}

// inserta en la posicion indicada en lista de elementos sin clave
bool ValorTable::insertElemSinClave(ElemTable* elem, Valor* posicion){
    bool okPosicion = true;    
    float pos;    
    if (posicion->getTipo()->getTipoBasico() == RealTipoClase){
        pos = trunc(((ValorReal*)posicion)->getValorReal());
    }else{
        pos = ((ValorInteger*)posicion)->getValorInteger();
    }
    int cantElem = 0;
    if (tableSinClave != NULL)
        cantElem = tableSinClave->cantElem;
    if ((pos > 0) && (pos <= (cantElem + 1))){
        if ((tableSinClave == NULL) || (pos == (cantElem + 1))){
            ValorTable::insertElemSinClave(elem);
        }else{
            elementsTable* e = new elementsTable;
	        e->elem	= elem;
            int cont = 1;
            elementsTable* tmp = tableSinClave;
            pos--;
            while (cont < pos) { tmp = tmp->sig; cont++; }
            e->sig = tmp->sig;            
            tmp->sig = e;
            tableSinClave->cantElem = tableSinClave->cantElem + 1;
        }
    }else{
        okPosicion = false;    
    }
    return okPosicion;
}

void insertarOrdenado(elementsTable* &tabla, ElemTable* elem){
    if (tabla == NULL){
        tabla = new elementsTable;
	    tabla->elem	= elem;
	    tabla->sig = NULL;
    }else{
        elementsTable* nodo = new elementsTable;
	    nodo->elem	= elem;
	    
        bool finComparacion = false;
        elementsTable* iter2 = NULL;
        elementsTable* iter1 = tabla;
        while ((iter1 != NULL) && (!finComparacion)){
            if ((elem->valor->getTipo()->getTipoBasico() == IntTipoClase) || 
               (elem->valor->getTipo()->getTipoBasico() == RealTipoClase)){
                float valorTabla = iter1->elem->valor->getTipo()->getTipoBasico() == IntTipoClase ?
                                   ((ValorInteger*)iter1->elem->valor)->getValorInteger() : 
                                   ((ValorReal*)iter1->elem->valor)->getValorReal();     
                float valorElem = elem->valor->getTipo()->getTipoBasico() == IntTipoClase ?
                                  ((ValorInteger*)elem->valor)->getValorInteger() : 
                                  ((ValorReal*)elem->valor)->getValorReal();
                if (valorTabla <= valorElem){
                    iter2 = iter1;
                    iter1 = iter1->sig;
                }else{
                    finComparacion = true;
                }
            }else{
                string valorTabla = ((ValorString*)iter1->elem->valor)->getValorString();     
                string valorElem = ((ValorString*)elem->valor)->getValorString();
                if (valorTabla.compare(valorElem) <= 0){
                    iter2 = iter1;
                    iter1 = iter1->sig;
                }else{
                    finComparacion = true;
                }
            }        
        }
        if (iter1 == NULL){ //se inserta al final
            iter2->sig = nodo;
            nodo->sig = NULL;
        }else if (iter2 == NULL){//se inserta al principio
            nodo->sig = iter1;
            tabla = nodo;
        }else{//se inserta en el medio
            nodo->sig = iter1;
            iter2->sig = nodo;
        }
    }
}

bool ValorTable::sort(){
    bool okTipos = true;

    if ((tableSinClave != NULL) && (tableSinClave->cantElem > 0)){
        elementsTable* iterador = tableSinClave;
        elementsTable* nuevaTabla = NULL;
        bool tipoEsIntNext, tipoEsRealNext, tipoEsStringNext; 
        bool tipoEsInt = iterador->elem->valor->getTipo()->getTipoBasico() == IntTipoClase; 
        bool tipoEsReal = iterador->elem->valor->getTipo()->getTipoBasico() == RealTipoClase;
        bool tipoEsString = iterador->elem->valor->getTipo()->getTipoBasico() == StringTipoClase;
        while ((iterador != NULL) && (okTipos)){
            insertarOrdenado(nuevaTabla, iterador->elem);
            iterador = iterador->sig;

            if (iterador != NULL){
                tipoEsIntNext = iterador->elem->valor->getTipo()->getTipoBasico() == IntTipoClase; 
                tipoEsRealNext = iterador->elem->valor->getTipo()->getTipoBasico() == RealTipoClase;
                tipoEsStringNext = iterador->elem->valor->getTipo()->getTipoBasico() == StringTipoClase;
                
                if (!((tipoEsInt && (tipoEsIntNext || tipoEsRealNext))
                   || (tipoEsReal && (tipoEsIntNext || tipoEsRealNext))
                   || (tipoEsString && tipoEsStringNext))) {
                    okTipos = false;
                } 
            }
        }    
        if (okTipos){
            nuevaTabla->cantElem = tableSinClave->cantElem; 
            iterador = tableSinClave;
            tableSinClave = nuevaTabla;
            delete iterador;
        }else{
            delete nuevaTabla;
        }
    }

    return okTipos;
}

bool ValorTable::remove(Valor* posicion, Valor* &valorResult){
		bool okPosicion = true;
		
		int pos;
		if (posicion != NULL){
			pos = ((ValorInteger*)posicion)->getValorInteger();
		}

        int cantElem = 0;
        if (tableSinClave != NULL){
			cantElem = tableSinClave->cantElem;
        }

		if (((cantElem > 0) && ((posicion == NULL) || (pos > 0 && pos <= cantElem)))
			|| ((cantElem == 0) && ((posicion == NULL) || (pos == 0)))){
				if ((cantElem == 0) && ((posicion == NULL) || (pos == 0))){
					valorResult = new ValorNIL(0);
				}else{
					if (posicion == NULL){
						pos = cantElem;
					}
					
					int i = 1;
					elementsTable* iter1 = tableSinClave;
					elementsTable* iter2 = NULL;
					while ((iter1 != NULL) && (i < pos)){
						i++;
						iter2 = iter1;
						iter1 = iter1->sig;
					}
					
					valorResult = iter1->elem->valor;
					tableSinClave->cantElem = tableSinClave->cantElem - 1;

					if ((iter1->sig == NULL) && (iter2 == NULL)){ 
						tableSinClave = NULL;
						delete iter1;
					}else if ((iter1->sig == NULL) && (iter2 != NULL)){
						iter2->sig = NULL;
						delete iter1;
					}else if ((iter1->sig != NULL) && (iter2 == NULL)){
                        iter1->sig->cantElem = tableSinClave->cantElem;
						tableSinClave = iter1->sig;
						iter1->sig = NULL;
						delete iter1;
					}else if ((iter1->sig != NULL) && (iter2 != NULL)){
						iter2->sig = iter1->sig;
						iter1->sig = NULL;
						delete iter1;
					}
				}
		}else{
			okPosicion = false;
		}
		return okPosicion;
}

void ValorTable::imprimir() const {
    int pos = 1;
    elementsTable* tmp = tableSinClave;
	while (tmp != NULL) {
        cout << pos;
        cout << " ";
        tmp->elem->valor->imprimir();                
        cout << endl;
        tmp = tmp->sig;
        pos++;
	}

    tmp = tableConClave;
    while (tmp != NULL) {
        tmp->elem->clave->imprimir();
        cout << " ";
        tmp->elem->valor->imprimir();                
        cout << endl;
        tmp = tmp->sig;
	}
}

int ValorTable::largoTabla(){
    int largo = 0;
    
    if (tableSinClave != NULL){
        largo = largo + tableSinClave->cantElem; 
    }    
    if (tableConClave != NULL){
        largo = largo + tableConClave->cantElem;
    }

    return largo;
}

Valor* getValorTablaConClave(elementsTable* &tabla, Valor* clave, Valor* valor, bool actualizar){
	elementsTable* iter = tabla;
	Valor* result = NULL;
	bool encontre = false;
	bool tipoEsInt, tipoEsReal, tipoEsString;
	while ((iter != NULL) && (!encontre)){
		if (iter->elem->clave->getTipo()->getTipoBasico() == IntTipoClase) {
			if (clave->getTipo()->getTipoBasico() == IntTipoClase) {
				int claveIter = ((ValorInteger*)iter->elem->clave)->getValorInteger();
				int claveClave = ((ValorInteger*)clave)->getValorInteger();
				if (claveIter == claveClave){
					encontre = true;
					result = iter->elem->valor;
                    if (actualizar){
                        iter->elem->valor = valor;
                    }
				}
			}
		}else if (iter->elem->clave->getTipo()->getTipoBasico() == RealTipoClase){
			if (clave->getTipo()->getTipoBasico() == RealTipoClase) {
				float claveIter = ((ValorReal*)iter->elem->clave)->getValorReal();
				float claveClave = ((ValorReal*)clave)->getValorReal();
				if (claveIter == claveClave){
					encontre = true;
					result = iter->elem->valor;
                    if (actualizar){
                        iter->elem->valor = valor;
                    }
				}
			}
		}else if (iter->elem->clave->getTipo()->getTipoBasico() == StringTipoClase){
			if (clave->getTipo()->getTipoBasico() == StringTipoClase) {
				string claveIter = ((ValorString*)iter->elem->clave)->getValorString();
				string claveClave = ((ValorString*)clave)->getValorString();
				if (claveIter.compare(claveClave) == 0){
					encontre = true;
					result = iter->elem->valor;
                    if (actualizar){
                        iter->elem->valor = valor;
                    }
				}
			}
		}
		iter = iter->sig;
	}
	return result;
}

Valor* getValorTablaSinClave(elementsTable* &tabla, int posicion, Valor* valor, bool actualizar){
	elementsTable* iter = tabla;
	Valor* result = NULL;
	int i = 1;
	while ((iter != NULL) && (i < posicion)){
		iter = iter->sig;
		i++;
	}
	if (iter != NULL){
		result = iter->elem->valor;
        if (actualizar){
            iter->elem->valor = valor;
        }
	}
	return result;
}

void ValorTable::setValor(Valor* clave, Valor* valor){
	Valor* valorEnLaTabla = getValorTablaConClave(tableConClave,clave,valor,true);
	if ((valorEnLaTabla == NULL) && (clave->getTipo()->getTipoBasico() == IntTipoClase)){
	    valorEnLaTabla = getValorTablaSinClave(tableSinClave,((ValorInteger*)clave)->getValorInteger(),valor,true);
	}
	if ((valorEnLaTabla == NULL) && (clave->getTipo()->getTipoBasico() == IntTipoClase)){
         
        int pos = ((ValorInteger*)clave)->getValorInteger();
        int cantElem = 0;
        if (tableSinClave != NULL)
            cantElem = tableSinClave->cantElem;
        if ((pos > 0) && (pos <= (cantElem + 1))){
            ElemTable* elem = new ElemTable;
            elem->clave = clave;
            elem->valor = valor;
            insertElemSinClave(elem);
        }else{
            ElemTable* elem = new ElemTable;
            elem->clave = clave;
            elem->valor = valor;
		    insertElemConClave(elem);
        }
	}else if (valorEnLaTabla == NULL){
		ElemTable* elem = new ElemTable;
        elem->clave = clave;
        elem->valor = valor;
		insertElemConClave(elem);
    }
}

Valor* ValorTable::getValor(Valor* clave){
	Valor* valorEnLaTabla = getValorTablaConClave(tableConClave,clave,NULL,false);
	if ((valorEnLaTabla == NULL) && (clave->getTipo()->getTipoBasico() == IntTipoClase)){
		valorEnLaTabla = getValorTablaSinClave(tableSinClave,((ValorInteger*)clave)->getValorInteger(),NULL,false);
	}
	return valorEnLaTabla;
}

Tipo* ValorTable::getTipo() const { return new TipoTable; }
Valor* ValorTable::copiar() const { }
elementsTable* ValorTable::getValorTableConClave() const { return tableConClave; }
elementsTable* ValorTable::getValorTableSinClave() const { return tableSinClave; }

