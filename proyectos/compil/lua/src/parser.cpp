//Grupo 19
#include <stdio.h>
#include "parser.h"
#include <iostream>
#include <cstring>
#include <math.h>
#include <sstream>

void throwException(string msg){
    char *ex = new char[msg.length() + 1];
    strcpy(ex, msg.c_str());
    throw ex;       
}

void throwException(string msg, int linea){
    std::stringstream lineaString;
    lineaString << " ";
    lineaString << linea ;
	msg += lineaString.str();
	msg += "\n";
    char *ex = new char[msg.length() + 1];
	strcpy(ex, msg.c_str());
    throw ex;       
}


Valor* NodoInteger::evaluar(Contexto *contexto)
{ 
	return new ValorInteger(valor);
}

Valor* NodoReal::evaluar(Contexto *contexto)
{ 
	return new ValorReal(valor);
}

Valor* NodoString::evaluar(Contexto *contexto)
{ 
	return new ValorString(valor);
}

Valor* NodoNIL::evaluar(Contexto *contexto)
{ 
	return new ValorNIL(valor);
}

Valor* NodoBool::evaluar(Contexto *contexto)
{ 
	return new ValorBool(valor);
}

Valor* NodoReturn::evaluar(Contexto *contexto)
{
	return valor->evaluar(contexto); 
}

Valor* ExpresionSentencia::evaluar(Contexto *contexto)
{
	return exp->evaluar(contexto); 
}

Valor* NodoBloque::evaluar(Contexto *contexto,vector<Variable> vars)
{ 
	contexto->nuevoScope();
	Variable * varAux = NULL ;
	for ( int i = 0; i < vars.size(); i++){
		varAux = &vars.at(i);
		contexto->agregarVariableAScopeActual(varAux);
	}
	
	Valor* result = 0;
	for (unsigned int i = 0; i < sentencias.size(); ++i){
		 result = sentencias.at(i)->evaluar(contexto);
	}
	contexto->eliminarScope();
	
	return result;

}

Valor* NodoBloque::evaluar(Contexto *contexto)
{  
	vector<Variable> conNada;
	return evaluar(contexto,conNada);
}

Valor* NodoVariable::evaluar (Contexto *ctx) {

    Variable *v = new Variable(nombre);
	if (ctx->getVariable(v)) {
	    return v->getValor();
    }
        
    return new ValorNIL(0);
}


Valor* NodoVariablesLocal::evaluar (Contexto *ctx)
{
	for (unsigned int i = 0; i < listaArgumentos->argumentos.size(); i++){
		char* vName = listaArgumentos->argumentos.at(i); // nombre de la variable local
		Valor* derechaValor = NULL;
		if(expListaDerecha!=NULL){
			NodoExp *expDerecha = expListaDerecha->expresiones.at(i);
			if(expDerecha!=NULL){
				derechaValor = expDerecha->evaluar(ctx);
			}else{
				derechaValor =  new ValorNIL(0);
			}
		}else{
			derechaValor = new ValorNIL(0);
		}
		Variable *v = new Variable(vName,derechaValor);
        ctx->agregarVariableAScopeActual(v);
	}
	
}


// TODO falta verificar con variables local declaradas , !!! 
Valor* NodoAsignacion::evaluar (Contexto *ctx)
{
	for (unsigned int i = 0; i < listaVariables->variables.size(); ++i){

                char* vName = listaVariables->variables.at(i);
                Valor *derechaValor = new ValorNIL(0);
                NodoExp *expDerecha;

                if (i <= expListaDerecha->expresiones.size()-1 ) { //del lado derecho hay todavia argumentos sino el resto seran nil
                        expDerecha = expListaDerecha->expresiones.at(i); 
                        derechaValor = expDerecha->evaluar(ctx);
                }

                
                //Es una funcion asi que la agregamos a las funciones
                if (derechaValor != NULL && derechaValor->getTipo()->getTipoBasico() == StringTipoClase
                        && ((ValorString*)derechaValor)->getValorString() == "funcion" ){
                        

                        ValorString * tempValor =  (ValorString*)derechaValor;
                        

                        NodoFuncionAsignada * funAsignada = (NodoFuncionAsignada*)expDerecha; 
                        NodoFuncion * fun = new NodoFuncion(linea, vName, funAsignada->bloqueFuncion, funAsignada->listaArgumento);                       
                        ctx->agregarFuncion(fun); //agrego la funcion al contexto actual


                } else { //es una variable
                        if (derechaValor == NULL) {
                                derechaValor = new ValorNIL(0);
                        }
                        Variable *v = new Variable(vName,derechaValor);

                        ctx->agregarVariable(v);
                        //ctx->printContexto(); 
                }               
        }
      
	return NULL;    

}



void NodoOperacionBinaria::imprimir (){ 
	expDer->imprimir() ;
	cout<<"Operacion "<<op;
	expIzq->imprimir() ;	
};

	 
string enumOperUn2Str(EnumOperacionUnaria op){

	string ret = "";
	switch (op){
		case menosu:
			ret = "- (unario)";
		break;
		case enot:
			ret = "NOT";
		break;
		case largo:
			ret = "#";
		break;
		default:
			string msg = "Error: Operacion Unaria No reconocida";
		throwException(msg);
		}
	return ret ;
}

Valor* NodoOperacionUnaria::evaluar (Contexto *ctx){
	Valor* ret = NULL;
	Valor* val = exp->evaluar(ctx);	
	Tipo* tipo = val->getTipo();
	TipoBasico tipoBasico = tipo->getTipoBasico();
	//menosu,enot,largo
	bool error = false;
	switch (op){
		case menosu:
			if (tipoBasico == IntTipoClase){
				int eval = -(((ValorInteger*)val)->getValorInteger());
				ret = new ValorInteger(eval);
			}else if(tipoBasico == RealTipoClase){
				float eval = -(((ValorReal*)val)->getValorReal());
				ret = new ValorReal(eval);
			}else{
				error =true;
			}
		break;
		case enot:
			if(tipoBasico == BoolTipoClase){
				bool eval = not (((ValorBool*)val)->getValorBool());
				ret = new ValorBool(eval);
			}else if (tipoBasico == NILTipoClase){
				ret = new ValorBool(true);
			} else {
				ret = new ValorBool(false);
                        }
		break;
		case largo: // # soporte para string y tablas solamente
		 //ret = new ValorInteger(eval);
			if (tipoBasico == StringTipoClase){
				int eval = (((ValorString*)val)->getValorString()).size();
				ret = new ValorInteger(eval);
				//cout << "Parser - Evaluado largo "<<eval;
			} else if (tipoBasico == TableTipoClase){
				int eval = ((ValorTable*)val)->largoTabla();
				ret = new ValorInteger(eval);
			}
			else{
				error =true;
			}
		 
		break;
		default:
			string msg = "Error: Operacion Unaria No reconocida  linea ";
			throwException(msg,linea);
	}

	if (error){
		string msg = "Error: No se puede realizar operacion Unaria "+enumOperUn2Str(op) ;
		msg += " con los datos ";
		msg += tipo->imprimir() + "  linea ";
		throwException(msg, linea);
	}
	return ret;
}

string enumOperBin2Str(EnumOperacionBinaria op){
	string ret = "";
	
	switch (op){
		case mas:
			ret = "+";
		break;
		case menos:
			ret = "-";
		break;
		case mul:
			ret = "*";
		break;
		case ediv:
			ret = "/";
		break;
		case potencia:
			ret = "^";
		break;
		case modulo:
			ret = "%";
		break;
		case concatenacion:
			ret = "..";
		break;
		case menor:
			ret = "<";
		break;
		case menorigual:
			ret = "<=";
		break;
		case mayor:
			ret = ">";
		break;
		case mayorigual:
			ret = ">=";
		break;
		case igual:
			ret = "=";
		break;
		case distinto:
			ret = "~=";
		break;
		case eand:
			ret = "AND";
		break;
		case eor:
			ret = "OR";
		break;
		
		default:
			string msg = "Error: enumOperBin2Str Operacion No reconocida";
			throwException(msg);
	}
	
	return ret;
}

Valor* NodoOperacionBinaria::evaluar (Contexto *ctx){
	Valor* ret = NULL;
	Valor* valDer = expDer->evaluar(ctx);
	Valor* valIzq = expIzq->evaluar(ctx);
	Tipo* tipoDer = valDer->getTipo();
	Tipo* tipoIzq = valIzq->getTipo();
	TipoBasico tipoBasicoDer = tipoDer->getTipoBasico();
	TipoBasico tipoBasicoIzq = tipoIzq->getTipoBasico();
//mas,menos,mul,div,potencia,modulo,concatenacion,menor,menorigual,mayor,mayorigual,igual,distinto,and,or
	bool error = false;
	switch (op){
		case mas:
			if ((tipoBasicoDer == IntTipoClase) && ( tipoBasicoIzq == IntTipoClase)){
				int eval = (((ValorInteger*)valDer)->getValorInteger())+(((ValorInteger*)valIzq)->getValorInteger());
				ret = new ValorInteger(eval);
			}else if((tipoBasicoDer == RealTipoClase) && ( tipoBasicoIzq == RealTipoClase)){
				float eval = (((ValorReal*)valDer)->getValorReal())+(((ValorReal*)valIzq)->getValorReal());
				ret = new ValorReal(eval);
			}else if((tipoBasicoDer == IntTipoClase) && ( tipoBasicoIzq == RealTipoClase)){
				float eval = (((ValorInteger*)valDer)->getValorInteger())+(((ValorReal*)valIzq)->getValorReal()) ;
				ret = new ValorReal(eval);
			}else if((tipoBasicoDer == RealTipoClase) && ( tipoBasicoIzq == IntTipoClase)){
				float eval =  (((ValorReal*)valDer)->getValorReal())+(((ValorInteger*)valIzq)->getValorInteger());
				ret = new ValorReal(eval);
			}else{
				error =true;
			}
		break;
		case menos:
			if ((tipoBasicoDer == IntTipoClase) && ( tipoBasicoIzq == IntTipoClase)){
				float eval = (((ValorInteger*)valIzq)->getValorInteger())-(((ValorInteger*)valDer)->getValorInteger());
				ret = new ValorReal(eval);

			}else if((tipoBasicoDer == RealTipoClase) && ( tipoBasicoIzq == RealTipoClase)){
				float eval = (((ValorReal*)valIzq)->getValorReal())-(((ValorReal*)valDer)->getValorReal());
				ret = new ValorReal(eval);

			}else if((tipoBasicoDer == IntTipoClase) && ( tipoBasicoIzq == RealTipoClase)){
				float eval = (((ValorReal*)valIzq)->getValorReal())-(((ValorInteger*)valDer)->getValorInteger()) ;
				ret = new ValorReal(eval);

			}else if((tipoBasicoDer == RealTipoClase) && ( tipoBasicoIzq == IntTipoClase)){
				float eval =  (((ValorInteger*)valIzq)->getValorInteger())-(((ValorReal*)valDer)->getValorReal());
				ret = new ValorReal(eval);

			}else{
				error =true;
			}
		break;
		case mul:
			if ((tipoBasicoDer == IntTipoClase) && ( tipoBasicoIzq == IntTipoClase)){
				int eval = (((ValorInteger*)valDer)->getValorInteger())*(((ValorInteger*)valIzq)->getValorInteger());
				ret = new ValorInteger(eval);
			}else if((tipoBasicoDer == RealTipoClase) && ( tipoBasicoIzq == RealTipoClase)){
				float eval = (((ValorReal*)valDer)->getValorReal())*(((ValorReal*)valIzq)->getValorReal());
				ret = new ValorReal(eval);
			}else if((tipoBasicoDer == IntTipoClase) && ( tipoBasicoIzq == RealTipoClase)){
				float eval = (((ValorInteger*)valDer)->getValorInteger())*(((ValorReal*)valIzq)->getValorReal()) ;
				ret = new ValorReal(eval);
			}else if((tipoBasicoDer == RealTipoClase) && ( tipoBasicoIzq == IntTipoClase)){
				float eval =  (((ValorReal*)valDer)->getValorReal())*(((ValorInteger*)valIzq)->getValorInteger());
				ret = new ValorReal(eval);
			}else{
				error =true;
			}
		break;
		case ediv:
			if ((tipoBasicoDer == IntTipoClase) && ( tipoBasicoIzq == IntTipoClase)){
				float divisor = (((ValorInteger*)valDer)->getValorInteger());
				if (divisor==0) throwException("Error: Se intenta dividir por 0 linea ",linea);
				float eval = (((ValorInteger*)valIzq)->getValorInteger())/divisor;
				ret = new ValorReal(eval);

			}else if((tipoBasicoDer == RealTipoClase) && ( tipoBasicoIzq == RealTipoClase)){
				float divisor = (((ValorReal*)valDer)->getValorReal());
				if (divisor==0.0) throwException("Error: Se intenta dividir por 0 linea ",linea);
				float eval = (((ValorReal*)valIzq)->getValorReal())/divisor;
				ret = new ValorReal(eval);

			}else if((tipoBasicoDer == IntTipoClase) && ( tipoBasicoIzq == RealTipoClase)){
				int divisor = (((ValorInteger*)valDer)->getValorInteger()) ;
				if (divisor==0.0) throwException("Error: Se intenta dividir por 0 linea ",linea);
				float eval = (((ValorReal*)valIzq)->getValorReal())/divisor;
				ret = new ValorReal(eval);

			}else if((tipoBasicoDer == RealTipoClase) && ( tipoBasicoIzq == IntTipoClase)){

				float divisor =(((ValorReal*)valDer)->getValorReal());
				if (divisor==0) throwException("Error: Se intenta dividir por 0 linea ",linea);
				float eval =  (((ValorInteger*)valIzq)->getValorInteger())/divisor;

				ret = new ValorReal(eval);

			}else{
				error =true;
			}
		break;
		case potencia:
			if ((tipoBasicoDer == IntTipoClase) && ( tipoBasicoIzq == IntTipoClase)){
				int eval = pow((((ValorInteger*)valIzq)->getValorInteger()),(((ValorInteger*)valDer)->getValorInteger()));
				ret = new ValorInteger(eval);
			}else if((tipoBasicoDer == RealTipoClase) && ( tipoBasicoIzq == RealTipoClase)){
				float eval = pow((((ValorReal*)valIzq)->getValorReal()),(((ValorReal*)valDer)->getValorReal()));
				ret = new ValorReal(eval);
			}else if((tipoBasicoDer == IntTipoClase) && ( tipoBasicoIzq == RealTipoClase)){
				float eval =  pow((((ValorReal*)valIzq)->getValorReal()),(((ValorInteger*)valDer)->getValorInteger()));
				ret = new ValorReal(eval);
			}else if((tipoBasicoDer == RealTipoClase) && ( tipoBasicoIzq == IntTipoClase)){
				float eval = pow((((ValorInteger*)valIzq)->getValorInteger()),(((ValorReal*)valDer)->getValorReal())) ;
				ret = new ValorReal(eval);
			}else{
				error =true;
			}
		break;
		case modulo:
			if ((tipoBasicoDer == IntTipoClase) && ( tipoBasicoIzq == IntTipoClase)){
				int eval = (((ValorInteger*)valIzq)->getValorInteger())%(((ValorInteger*)valDer)->getValorInteger());
				ret = new ValorInteger(eval);
			}else if((tipoBasicoDer == RealTipoClase) && ( tipoBasicoIzq == RealTipoClase)){
				float eval = fmod((((ValorReal*)valIzq)->getValorReal()),(((ValorReal*)valDer)->getValorReal()));
				ret = new ValorReal(eval);
			}else if((tipoBasicoDer == IntTipoClase) && ( tipoBasicoIzq == RealTipoClase)){
				float eval =  fmod((((ValorReal*)valIzq)->getValorReal()),(((ValorInteger*)valDer)->getValorInteger()));
				ret = new ValorReal(eval);
			}else if((tipoBasicoDer == RealTipoClase) && ( tipoBasicoIzq == IntTipoClase)){
				float eval = fmod((((ValorInteger*)valIzq)->getValorInteger()),(((ValorReal*)valDer)->getValorReal()));
				ret = new ValorReal(eval);
			}else{
				error =true;
			}
		break;
		case concatenacion: {
                        string derString;
                        string izqString;

                        if(tipoBasicoDer == StringTipoClase) {

                                derString = ((ValorString*)valDer)->getValorString();

                        }else if (tipoBasicoDer == IntTipoClase) {
                                std::stringstream lineaString;
                                lineaString << ((ValorInteger*)valDer)->getValorInteger();
	                        derString = lineaString.str();

                        }else if (tipoBasicoDer == RealTipoClase) {
                                std::stringstream lineaString;
                                lineaString << ((ValorReal*)valDer)->getValorReal();
	                        derString = lineaString.str();

                        } else {
                                throwException("Error: Concatenando tipos incorrectos (esta permitido numeros y string), cerca de linea ",linea);
                                error = true; 
                        }


                        if(tipoBasicoIzq == StringTipoClase) {

                                izqString = ((ValorString*)valIzq)->getValorString();

                        }else if (tipoBasicoIzq == IntTipoClase) {
                                std::stringstream lineaString;
                                lineaString << ((ValorInteger*)valIzq)->getValorInteger();
	                        izqString = lineaString.str();

                        }else if (tipoBasicoIzq == RealTipoClase) {
                                std::stringstream lineaString;
                                lineaString << ((ValorReal*)valIzq)->getValorReal();
	                        izqString = lineaString.str();

                        } else {
                                throwException("Error: Concatenando tipos incorrectos (esta permitido numeros y string), cerca de  linea ",linea);
                                error = true; 
                        }


			string eval = string(izqString+ derString);
			ret = new ValorString(eval);

                }
		break;
		case menor:
			if ((tipoBasicoDer == IntTipoClase) && ( tipoBasicoIzq == IntTipoClase)){
				bool eval = (((ValorInteger*)valIzq)->getValorInteger())<(((ValorInteger*)valDer)->getValorInteger());
				ret = new ValorBool(eval);
			}else if((tipoBasicoDer == RealTipoClase) && ( tipoBasicoIzq == RealTipoClase)){
				bool eval = (((ValorReal*)valIzq)->getValorReal())<(((ValorReal*)valDer)->getValorReal());
				ret = new ValorBool(eval);
			}else if((tipoBasicoDer == IntTipoClase) && ( tipoBasicoIzq == RealTipoClase)){
				bool eval = (((ValorReal*)valIzq)->getValorReal())<(((ValorInteger*)valDer)->getValorInteger());
				ret = new ValorBool(eval);
			}else if((tipoBasicoDer == RealTipoClase) && ( tipoBasicoIzq == IntTipoClase)){
				bool eval = (((ValorInteger*)valIzq)->getValorInteger())<(((ValorReal*)valDer)->getValorReal());
				ret = new ValorBool(eval);
			}else if((tipoBasicoDer == StringTipoClase) && ( tipoBasicoIzq == StringTipoClase)){
				bool eval = (((ValorString*)valIzq)->getValorString())<(((ValorString*)valDer)->getValorString());
				ret = new ValorBool(eval);
			}else if((tipoBasicoDer == BoolTipoClase) && ( tipoBasicoIzq == BoolTipoClase)){
				bool eval = (((ValorBool*)valIzq)->getValorBool())<(((ValorBool*)valDer)->getValorBool());
				ret = new ValorBool(eval);	
			}
			else{
				error = true;
			}
		break;
		case menorigual:
			if ((tipoBasicoDer == IntTipoClase) && ( tipoBasicoIzq == IntTipoClase)){
				bool eval = (((ValorInteger*)valIzq)->getValorInteger())<=(((ValorInteger*)valDer)->getValorInteger());
				ret = new ValorBool(eval);
			}else if((tipoBasicoDer == RealTipoClase) && ( tipoBasicoIzq == RealTipoClase)){
				bool eval = (((ValorReal*)valIzq)->getValorReal())<=(((ValorReal*)valDer)->getValorReal());
				ret = new ValorBool(eval);
			}else if((tipoBasicoDer == IntTipoClase) && ( tipoBasicoIzq == RealTipoClase)){
				bool eval = (((ValorReal*)valIzq)->getValorReal())<=(((ValorInteger*)valDer)->getValorInteger());
				ret = new ValorBool(eval);
			}else if((tipoBasicoDer == RealTipoClase) && ( tipoBasicoIzq == IntTipoClase)){
				bool eval = (((ValorInteger*)valIzq)->getValorInteger())<=(((ValorReal*)valDer)->getValorReal());
				ret = new ValorBool(eval);
			}else if((tipoBasicoDer == StringTipoClase) && ( tipoBasicoIzq == StringTipoClase)){
				bool eval = (((ValorString*)valIzq)->getValorString())<=(((ValorString*)valDer)->getValorString());
				ret = new ValorBool(eval);
			}else if((tipoBasicoDer == BoolTipoClase) && ( tipoBasicoIzq == BoolTipoClase)){
				bool eval = (((ValorBool*)valIzq)->getValorBool())<=(((ValorBool*)valDer)->getValorBool());
				ret = new ValorBool(eval);	
			}
			else{
				error = true;
			}
		break;
		case mayor:
			if ((tipoBasicoDer == IntTipoClase) && ( tipoBasicoIzq == IntTipoClase)){
				bool eval = (((ValorInteger*)valIzq)->getValorInteger())>(((ValorInteger*)valDer)->getValorInteger());
				ret = new ValorBool(eval);
			}else if((tipoBasicoDer == RealTipoClase) && ( tipoBasicoIzq == RealTipoClase)){
				bool eval = (((ValorReal*)valIzq)->getValorReal())>(((ValorReal*)valDer)->getValorReal());
				ret = new ValorBool(eval);
			}else if((tipoBasicoDer == IntTipoClase) && ( tipoBasicoIzq == RealTipoClase)){
				bool eval = (((ValorReal*)valIzq)->getValorReal())>(((ValorInteger*)valDer)->getValorInteger());
				ret = new ValorBool(eval);
			}else if((tipoBasicoDer == RealTipoClase) && ( tipoBasicoIzq == IntTipoClase)){
				bool eval = (((ValorInteger*)valIzq)->getValorInteger())>(((ValorReal*)valDer)->getValorReal());
				ret = new ValorBool(eval);
			}else if((tipoBasicoDer == StringTipoClase) && ( tipoBasicoIzq == StringTipoClase)){
				bool eval = (((ValorString*)valIzq)->getValorString())>(((ValorString*)valDer)->getValorString());
				ret = new ValorBool(eval);
			}else if((tipoBasicoDer == BoolTipoClase) && ( tipoBasicoIzq == BoolTipoClase)){
				bool eval = (((ValorBool*)valIzq)->getValorBool())>(((ValorBool*)valDer)->getValorBool());
				ret = new ValorBool(eval);	
			}
			else{
				error = true;
			}
		break;
		case mayorigual:
			if ((tipoBasicoDer == IntTipoClase) && ( tipoBasicoIzq == IntTipoClase)){
				bool eval = (((ValorInteger*)valIzq)->getValorInteger())>=(((ValorInteger*)valDer)->getValorInteger());
				ret = new ValorBool(eval);
			}else if((tipoBasicoDer == RealTipoClase) && ( tipoBasicoIzq == RealTipoClase)){
				bool eval = (((ValorReal*)valIzq)->getValorReal())>=(((ValorReal*)valDer)->getValorReal());
				ret = new ValorBool(eval);
			}else if((tipoBasicoDer == IntTipoClase) && ( tipoBasicoIzq == RealTipoClase)){
				bool eval = (((ValorReal*)valIzq)->getValorReal())>=(((ValorInteger*)valDer)->getValorInteger());
				ret = new ValorBool(eval);
			}else if((tipoBasicoDer == RealTipoClase) && ( tipoBasicoIzq == IntTipoClase)){
				bool eval = (((ValorInteger*)valIzq)->getValorInteger())>=(((ValorReal*)valDer)->getValorReal());
				ret = new ValorBool(eval);
			}else if((tipoBasicoDer == StringTipoClase) && ( tipoBasicoIzq == StringTipoClase)){
				bool eval = (((ValorString*)valIzq)->getValorString())>=(((ValorString*)valDer)->getValorString());
				ret = new ValorBool(eval);
			}else if((tipoBasicoDer == BoolTipoClase) && ( tipoBasicoIzq == BoolTipoClase)){
				bool eval = (((ValorBool*)valIzq)->getValorBool())>=(((ValorBool*)valDer)->getValorBool());
				ret = new ValorBool(eval);	
			}else{
				error = true;
			}
		break;
		case igual:
			if ((tipoBasicoDer == IntTipoClase) && ( tipoBasicoIzq == IntTipoClase)){
				bool eval = (((ValorInteger*)valDer)->getValorInteger())==(((ValorInteger*)valIzq)->getValorInteger());
				ret = new ValorBool(eval);
			}else if((tipoBasicoDer == RealTipoClase) && ( tipoBasicoIzq == RealTipoClase)){
				bool eval = (((ValorReal*)valDer)->getValorReal())==(((ValorReal*)valIzq)->getValorReal());
				ret = new ValorBool(eval);
			}else if((tipoBasicoDer == IntTipoClase) && ( tipoBasicoIzq == RealTipoClase)){
				bool eval = (((ValorInteger*)valDer)->getValorInteger())==(((ValorReal*)valIzq)->getValorReal());
				ret = new ValorBool(eval);
			}else if((tipoBasicoDer == RealTipoClase) && ( tipoBasicoIzq == IntTipoClase)){
				bool eval = (((ValorReal*)valDer)->getValorReal())==(((ValorInteger*)valIzq)->getValorInteger());
				ret = new ValorBool(eval);
			}else if((tipoBasicoDer == StringTipoClase) && ( tipoBasicoIzq == StringTipoClase)){
				bool eval = (((ValorString*)valDer)->getValorString())==(((ValorString*)valIzq)->getValorString());
				ret = new ValorBool(eval);
			}else if((tipoBasicoDer == BoolTipoClase) && ( tipoBasicoIzq == BoolTipoClase)){
				bool eval = (((ValorBool*)valDer)->getValorBool())==(((ValorBool*)valIzq)->getValorBool());
				ret = new ValorBool(eval);	
			}else{
				error = true;
			}
		break;
		case distinto:
			if ((tipoBasicoDer == IntTipoClase) && ( tipoBasicoIzq == IntTipoClase)){
				bool eval = (((ValorInteger*)valDer)->getValorInteger())!=(((ValorInteger*)valIzq)->getValorInteger());
				ret = new ValorBool(eval);
			}else if((tipoBasicoDer == RealTipoClase) && ( tipoBasicoIzq == RealTipoClase)){
				bool eval = (((ValorReal*)valDer)->getValorReal())!=(((ValorReal*)valIzq)->getValorReal());
				ret = new ValorBool(eval);
			}else if((tipoBasicoDer == IntTipoClase) && ( tipoBasicoIzq == RealTipoClase)){
				bool eval = (((ValorInteger*)valDer)->getValorInteger())!=(((ValorReal*)valIzq)->getValorReal());
				ret = new ValorBool(eval);
			}else if((tipoBasicoDer == RealTipoClase) && ( tipoBasicoIzq == IntTipoClase)){
				bool eval = (((ValorReal*)valDer)->getValorReal())!=(((ValorInteger*)valIzq)->getValorInteger());
				ret = new ValorBool(eval);
			}else if((tipoBasicoDer == StringTipoClase) && ( tipoBasicoIzq == StringTipoClase)){
				bool eval = (((ValorString*)valDer)->getValorString())!=(((ValorString*)valIzq)->getValorString());
				ret = new ValorBool(eval);
			}else if((tipoBasicoDer == BoolTipoClase) && ( tipoBasicoIzq == BoolTipoClase)){
				bool eval = (((ValorBool*)valDer)->getValorBool())!=(((ValorBool*)valIzq)->getValorBool());
				ret = new ValorBool(eval);	
			}else{
				error = true;
			}
		break;
		case eand:
			if ((tipoBasicoDer == BoolTipoClase) && ( tipoBasicoIzq == BoolTipoClase)){
				bool eval = (((ValorBool*)valDer)->getValorBool()) and (((ValorBool*)valIzq)->getValorBool());
				ret = new ValorBool(eval);
			}else{
				error = true;
			}
		break;
		case eor:
			if ((tipoBasicoDer == BoolTipoClase) && ( tipoBasicoIzq == BoolTipoClase)){
				bool eval = (((ValorBool*)valDer)->getValorBool()) or (((ValorBool*)valIzq)->getValorBool());
				ret = new ValorBool(eval);
			}else if (tipoBasicoIzq == NILTipoClase){ //si el de la izq es nil , asigno el de la derecha
                                ret = valDer->copiar();
                        }else{
				error = true;
			}
		break;
		
		default:
			string msg = "Error: Operacion No reconocida  linea ";
			throwException(msg,linea);
	}

	if (error){
		string msg = "Error: No se puede realizar operacion "+enumOperBin2Str(op) ;
		msg += " con los datos ";
		msg += tipoDer->imprimir() + " y ";
		msg += tipoIzq->imprimir() + "  linea: ";
		throwException(msg, linea);
	}


	return ret;
}

bool evaluarFuncionPredeterminada(char* nombre, NodoListaExp* argumentos, Contexto* contexto, Valor* &valorResult,
                                int linea){
        bool okFuncPredeterminada = true;
        if ((strcmp("table.sort",nombre) == 0) || (strcmp("table.insert",nombre) == 0) || 
           (strcmp("table.remove",nombre) == 0) || (strcmp("math.sqrt",nombre) == 0) || 
           (strcmp("pairs",nombre) == 0) || (strcmp("print",nombre) == 0)){
                int cantArgumentos = argumentos != NULL ? argumentos->expresiones.size() : 0;
                if (cantArgumentos > 0){
                    if ((strcmp("table.sort",nombre) == 0) || (strcmp("table.insert",nombre) == 0) 
                       || (strcmp("table.remove",nombre) == 0)){
                            NodoExp* e = argumentos->expresiones.at(0);
                            Valor* argValor = e->evaluar(contexto);
                            if (argValor->getTipo()->getTipoBasico() != TableTipoClase){
                                string msg = "Argumento #1 invalido en ";
	                            msg += nombre;
	                            msg += ", se esperaba una tabla en linea ";
	                            throwException(msg, linea);
                            }else{
                                    if (strcmp("table.sort",nombre) == 0){
                                        bool okTipos = ((ValorTable*)argValor)->sort();
                                        if (!okTipos){
                                            string msg = "Tipos incompatibles en table.sort en linea ";
	                                        throwException(msg, linea);
                                        }
                                    }else if (strcmp("table.insert",nombre) == 0){
                                        if (cantArgumentos == 2){
                                            Valor* elemValor = (argumentos->expresiones.at(1))->evaluar(contexto);
                                            ElemTable* elem = new ElemTable;
                                            elem->valor = elemValor;
                                            ((ValorTable*)argValor)->insertElemSinClave(elem);
                                        }else if (cantArgumentos == 3){
                                            Valor* posValor = (argumentos->expresiones.at(1))->evaluar(contexto);
                                            if ((posValor->getTipo()->getTipoBasico() != IntTipoClase) &&
                                                (posValor->getTipo()->getTipoBasico() != RealTipoClase)){
                                                string msg = "Argumento invalido en table.insert en linea ";
                                                throwException(msg, linea);
                                            }else{
                                                Valor* elemValor = (argumentos->expresiones.at(2))->evaluar(contexto);
                                                ElemTable* elem = new ElemTable;
                                                elem->valor = elemValor;
                                                bool okPos = ((ValorTable*)argValor)->insertElemSinClave(elem, posValor);
                                                if (!okPos){
                                                    string msg = "Posicion fuera de rango en table.insert en linea ";
                                                    throwException(msg, linea);
                                                }
                                            }
                                        }else{
                                            string msg = "Cantidad de argumentos invalidos en table.insert en linea ";
                                            throwException(msg, linea);
                                        }        
                                    }else if (strcmp("table.remove",nombre) == 0){
                                        Valor* posValor = NULL;
										if (cantArgumentos == 2){
											posValor = (argumentos->expresiones.at(1))->evaluar(contexto);
										}
										bool okPos = ((ValorTable*)argValor)->remove(posValor, valorResult);
										
										if (!okPos){
											string msg = "Posicion fuera de rango en table.remove en linea ";
											throwException(msg, linea);
										}
                                    }
                            }
                    }else if (strcmp("math.sqrt",nombre) == 0){
                            //1 argumento real o int
                            NodoExp* e = argumentos->expresiones.at(0);
                            Valor* argValor = e->evaluar(contexto);
                            if (argValor->getTipo()->getTipoBasico() == RealTipoClase){
                                    float result = sqrt(((ValorReal*)argValor)->getValorReal());
                                    valorResult = new ValorReal(result);
                                             
                            }else if (argValor->getTipo()->getTipoBasico() == IntTipoClase){
                                    float result = sqrt(((ValorInteger*)argValor)->getValorInteger());
                                    valorResult = new ValorReal(result);
                            }else{
                                string msg = "Tipo de dato incorrecto en math.sqrt en linea ";
	                            throwException(msg, linea);
                            }
                    }else if (strcmp("pairs",nombre) == 0){
                    //1 argumento for
                    }else if (strcmp("print",nombre) == 0){

                            for (unsigned int i = 0; i < cantArgumentos; ++i){
                                    NodoExp* e = argumentos->expresiones.at(i);
                                    Valor* argValor = e->evaluar(contexto);
                                    if (argValor->getTipo()->getTipoBasico() == TableTipoClase){
                                        cout<< "table: " << &argValor;
                                    }else{
                                        argValor->imprimir();
                                        cout<<"\t";
                                    }
                            }  
                            cout<<endl;  
                            valorResult = NULL;  

                    }
                }else{
                    string msg = "Faltan argumentos en el llamado a ";
		            msg += nombre;
		            msg += " en linea ";
		            throwException(msg, linea);
                }
        }else{
            okFuncPredeterminada = false;
        }
        return okFuncPredeterminada;
}

Valor* NodoLlamarFuncion::evaluar(Contexto *contexto) {
        Valor* valorResult;

	bool okFuncPredeterminada = evaluarFuncionPredeterminada(nombre,listaLlamarArgumentos,
                                                                contexto, valorResult, linea); 

        if (!okFuncPredeterminada) {

                NodoFuncion * funcion = contexto->buscarFuncion(nombre); //agrego la funcion al contexto actual

                if (funcion != NULL) {
 
                        //Chekeando cantidad de argumentos de la funcion y de la llamada
                        int cantidadArgsDefinicion = (funcion->listaArgumento != NULL? funcion->listaArgumento->argumentos.size() : 0);
                        int cantidadArgsLlamada = (this->listaLlamarArgumentos != NULL ?this->listaLlamarArgumentos->expresiones.size():0);
			
                        vector<Variable> vars;
                        if (cantidadArgsDefinicion >0) {
                                if (cantidadArgsLlamada <=cantidadArgsDefinicion) {
	                                for (unsigned int i = 0; i < cantidadArgsLlamada; ++i){				
                                                string vName = funcion->listaArgumento->argumentos.at(i);
                                                NodoExp* e = this->listaLlamarArgumentos->expresiones.at(i);
	                                        Valor *derechaValor = e->evaluar(contexto);
                                                //evaluo los parametros de la llamada
		                                vars.push_back(Variable(vName,derechaValor));
	                                }

                                        //Si llamo a la funcion con menos parametros pongo el resto en NIL
	                                for (unsigned int i = cantidadArgsLlamada; i < cantidadArgsDefinicion; ++i){				
                                                string vName = funcion->listaArgumento->argumentos.at(i);
                                                //evaluo los parametros de la llamada
		                                vars.push_back(Variable(vName,new ValorNIL(0)));
	                                }

	                                return funcion->bloqueFuncion->evaluar(contexto,vars); 

                                } else { //llamo a la funcion con mas parametros q la definicion tomo solo los de la def  

	                                for (unsigned int i = 0; i < cantidadArgsDefinicion; ++i){				
                                                string vName = funcion->listaArgumento->argumentos.at(i);
                                                NodoExp* e = this->listaLlamarArgumentos->expresiones.at(i);
	                                        Valor *derechaValor = e->evaluar(contexto);
                                                //evaluo los parametros de la llamada
		                                vars.push_back(Variable(vName,derechaValor));
	                                }

	                                return funcion->bloqueFuncion->evaluar(contexto,vars); 
                                }


                        }else{
			        return funcion->bloqueFuncion->evaluar(contexto); 
			}


                }else {
                                throwException("ERROR: La funcion "+ string(nombre)+" no existe y fue llamada desde la linea: ",linea);

                }
        }else if (valorResult != NULL){

                return valorResult;
        }

	//si llega aca algo esta mal
	return NULL;
}

Valor* NodoListaArgumento::evaluar(Contexto *contexto) {
	//cerr << "evaluando Nodo Lista Parametro " << endl; 
	return NULL;

}

Valor* NodoListaExp::evaluar(Contexto *contexto) {
	//cerr << "evaluando Nodo Lista Exp " << endl; 
	return NULL;

}
Valor* NodoListaVar::evaluar(Contexto *contexto) {
	//cerr << "evaluando Nodo Lista Var " << endl; 
	return NULL;

}

Valor* NodoFuncionAsignada::evaluar(Contexto *contexto) {

	return new ValorString("funcion");

}

Valor* NodoFuncion::evaluar(Contexto *contexto) {

        contexto->agregarFuncion(this); //agrego la funcion al contexto actual
        return NULL;

} 

Valor* NodoForIn::evaluar(Contexto *contexto) {


        if (listaArgumento->argumentos.size() == 2) {
                char * key = listaArgumento->argumentos[0];
                char * value = listaArgumento->argumentos[1];

                Valor* varTable = tabla->evaluar(contexto);
                ///test que no es null
	        /*if( varTable== NULL ) {
                        throwException("ERROR: La variable en pairs no esta definida. Cerca de linea: ", linea);

                } */                 

                //testear que sea una tabla
	        if(varTable->getTipo()->getTipoBasico() != TableTipoClase ) {
                        throwException("ERROR: La variable en pairs no es una tabla. Cerca de linea: ", linea);

                }  else {

                        ValorTable* table = (ValorTable*)varTable;
                        elementsTable* tmp = table->getValorTableSinClave();
                        int pos = 1;
                        vector<Variable> vars;          
              
                        while (tmp != NULL) {

                         
		             vars.push_back(Variable(key, new ValorInteger(pos))); //inserto variable key
		             vars.push_back(Variable(value, tmp->elem->valor)); //inserto variable valor
                     
	                   
                             bloque->evaluar(contexto, vars);  
                             vars.pop_back();
                             vars.pop_back();                                          
	                     tmp = tmp->sig;
                             pos++;
                        }


                        vector<Variable> vars2;                        
                        tmp = table->getValorTableConClave();

                        while (tmp != NULL) {
                             vars2.push_back(Variable(key, tmp->elem->clave)); //inserto variable key
                             vars2.push_back(Variable(value, tmp->elem->valor)); //inserto variable valor
	                  
                             bloque->evaluar(contexto, vars2);                                            
	                     tmp = tmp->sig;
                             vars2.pop_back();
                             vars2.pop_back(); 
                        } 

                        
                }

        } else {

                throwException("ERROR: Deben ser 2 argumentos en sentencia FOR IN cerca de línea: ", linea);

        }
   
        return NULL;
}

Valor* NodoFor::evaluar(Contexto *contexto)
{
	Valor* result = new ValorNIL(0);

        
	Valor* valInicio = inicio->evaluar(contexto);
	Valor* valFin = fin->evaluar(contexto);
	Valor* valIncremento = incremento->evaluar(contexto);
	
	bool okTipoInicio = ((valInicio->getTipo()->getTipoBasico() == RealTipoClase) || 
						(valInicio->getTipo()->getTipoBasico() == IntTipoClase));

	bool okTipoFin = ((valFin->getTipo()->getTipoBasico() == RealTipoClase) || 
					 (valFin->getTipo()->getTipoBasico() == IntTipoClase));
	
	bool okTipoIncremento = ((valIncremento->getTipo()->getTipoBasico() == RealTipoClase) || 
					 		(valIncremento->getTipo()->getTipoBasico() == IntTipoClase));

	if (okTipoInicio && okTipoFin && okTipoIncremento){        
		float desde; 
		if (valInicio->getTipo()->getTipoBasico() == RealTipoClase)
			desde = ((ValorReal*)valInicio)->getValorReal();
		else
	 		desde = ((ValorInteger*)valInicio)->getValorInteger();

		float hasta;
		if (valFin->getTipo()->getTipoBasico() == RealTipoClase)
			hasta = ((ValorReal*)valFin)->getValorReal();
		else
	 		hasta = ((ValorInteger*)valFin)->getValorInteger();

		float salto;
		if (valIncremento->getTipo()->getTipoBasico() == RealTipoClase)
			salto = ((ValorReal*)valIncremento)->getValorReal();
		else
	 		salto = ((ValorInteger*)valIncremento)->getValorInteger();

 		vector<Variable> vars;
		while ((salto > 0 && desde <= hasta) || (salto <= 0 && desde >= hasta)){
                        //Agregar variable dentro de el contexto del for
                        if  (valInicio->getTipo()->getTipoBasico() == RealTipoClase){
                            vars.push_back(Variable(vName, new ValorReal(desde))); //inserto variable valor
                        }else{
                            vars.push_back(Variable(vName, new ValorInteger((int)desde))); //inserto variable valor
                        }
                        
                        bloque->evaluar(contexto, vars);  
                        vars.pop_back();
                        desde = desde + salto;
                }
	}else{

		std::string msg = "Tipo de datos incorrecto en sentencia FOR cerca de línea: ";

		throwException(msg, linea);
	}
		
	return result;
}

Valor* NodoIf::evaluar(Contexto *contexto)
{
	Valor* result = new ValorNIL(0);

	Valor* valCondicion = condicion->evaluar(contexto);
	
        //evaluo el else solo si es nil o falso
        if ( (valCondicion->getTipo()->getTipoBasico() == NILTipoClase) || 
                (valCondicion->getTipo()->getTipoBasico() == BoolTipoClase && ((ValorBool*)valCondicion)->getValorBool()==false)) {
			
                Valor* valLlevaElse = llevaElse->evaluar(contexto);
	        if (((ValorBool*)valLlevaElse)->getValorBool()){
		        result = bloqueElse->evaluar(contexto);
	        }
        } else { //Aca se ejecuta el then
	        result = bloqueThen->evaluar(contexto);	
        }

	/*bool okTipoCondicion = (valCondicion->getTipo()->getTipoBasico() == BoolTipoClase);
	if (okTipoCondicion){
		if (((ValorBool*)valCondicion)->getValorBool()){
			result = bloqueThen->evaluar(contexto);	
		}else{
			Valor* valLlevaElse = llevaElse->evaluar(contexto);
			if (((ValorBool*)valLlevaElse)->getValorBool()){
				result = bloqueElse->evaluar(contexto);
			}
		}
	}else{
		std::string msg = "Tipo de datos incorrecto en sentencia if cerca de línea: ";
		throwException(msg, linea);
	}*/

	return result;
}

Valor* NodoCampoTable::evaluar(Contexto *contexto)
{
        return new ValorNIL(0);
};

Valor* NodoListaTable::evaluar(Contexto *contexto)
{
        ValorTable* result = new ValorTable();        
        
        if (campos.size() > 0){
                for (int i=0; i<campos.size(); i++){
                        Valor* vIzq = (campos.at(i)->ladoIzq)->evaluar(contexto);
                        Valor* vDer = (campos.at(i)->ladoDer)->evaluar(contexto);
                        if (vDer->getTipo()->getTipoBasico() == NILTipoClase){
                            std::string msg = "Tipo de datos incorrecto en construccion de tabla en línea ";
		                    delete (ValorTable*)result;            
                                
		                    throwException(msg, linea);
                        }else{  
                                if (vIzq->getTipo()->getTipoBasico() == NILTipoClase){
                                        ElemTable* elem = new ElemTable;
                                        elem->valor = vDer;
                                        result->insertElemSinClave(elem);
                                }else{
                                        ElemTable* elem = new ElemTable;
                                        elem->clave = vIzq;
                                        elem->valor = vDer;
                                        result->insertElemConClave(elem);
                                }
                        }     
                }
        }        
       
        return result;
};

Valor* NodoTable::evaluar(Contexto *contexto)
{
    return campos.evaluar(contexto);
};

Valor* NodoAsignacionTabla::evaluar(Contexto* contexto)
{
	Variable* v = new Variable(nombreTabla);
	if (contexto->getVariable(v)) {
		Valor* vTabla = v->getValor();
		if (vTabla->getTipo()->getTipoBasico() == TableTipoClase){
			Valor* vClave = clave->evaluar(contexto);
            if (vClave->getTipo()->getTipoBasico() != NILTipoClase){
				Valor* vValor = valor->evaluar(contexto);
				if (vValor->getTipo()->getTipoBasico() != NILTipoClase){
                	if ((tipoAsignacion == ASIG_PUNTO) && (vClave->getTipo()->getTipoBasico() != StringTipoClase)){
						throwException("Se esperaba un nombre en la asignacion en linea", linea);
					}else{
						((ValorTable*)vTabla)->setValor(vClave, vValor);
					}
				}
				return NULL;
			}else
				throwException("Indice en la tabla es nil en linea", linea);
		}else
			throwException("Se esperaba una tabla en linea", linea);
    }else{
		throwException("Variable no definida en linea", linea);
	}
};

Valor* NodoObtenerValorTabla::evaluar(Contexto* contexto)
{
	Variable* v = new Variable(nombreTabla);
	if (contexto->getVariable(v)) {
		Valor* vTabla = v->getValor();
		if (vTabla->getTipo()->getTipoBasico() == TableTipoClase){
			Valor* vClave = clave->evaluar(contexto);
			if (vClave->getTipo()->getTipoBasico() != NILTipoClase){
				return ((ValorTable*)vTabla)->getValor(vClave);
			}else
				throwException("Indice en la tabla es nil en linea", linea);
		}else
			throwException("Se esperaba una tabla en linea", linea);
    }else{
		throwException("Variable no definida en linea", linea);
	}
};


