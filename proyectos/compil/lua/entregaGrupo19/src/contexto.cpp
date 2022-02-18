//Grupo 19
#include "contexto.h"

Contexto* Contexto::contextoVacio()
{
	return new Contexto();
}
 

bool Contexto::getVariable (Variable *&v){
	//cerr << "getVariable "<< endl;
	if (v == 0) return false;

	//cerr << "v != 0 scope.size "<<scopes.size()<< endl;
	// primero busco en el scope y cada scope desde el ultimo pusheado al primero
	for (int i = ((int)scopes.size()-1); i >= 0 ; i--){
		
		//cerr << "for scopes i "<<i<< endl;
		
		Variable* aux = scopes.at(i).getVariable(v->getNombre());
		
		if(aux!=NULL){ // encontre la variable en un scope
			  delete v;
			  //cerr << "Encontre en Scope "  << aux->getNombre()  <<" ";
			   //aux->getValor()->imprimir();
			   //cerr << endl;
			   //cout.flush();
			   
			v = aux;
			return true;
		}
	}
	
	// si no encontre en los scopes busco en las globales

	for (int i = 0; i < (int)variables.size(); ++i){
		if (variables.at(i).getNombre() == v->getNombre()){ // encontre en las globales
			delete v;
			v = &variables.at(i);
			return true;
		}
	}
	return false;
}

//Cambia el valor de la funcion que encuentra en las globales o en las locales por el valor de v
bool Contexto::lookupVariable (Variable *v)
{
	Variable *actualV = new Variable(v->getNombre());
	if (!getVariable(actualV)) {
		return false;
        }

	Valor *val = v->getValor();
	if (val != 0){
		val = val->copiar();
        }
	
	actualV->setValor(val);

	return true;
}

Contexto::Contexto(): variables(), scopes()
{}

void Contexto::agregarFuncion(NodoFuncion* funcionNueva){

        bool encontre = false;

        int i = 0;
        while (i<funciones.size() && !encontre){
                NodoFuncion* funcion = funciones.at(i);
                        
                if (funcion != NULL && strcmp (funcionNueva->nombre,funcion->nombre) == 0 ){
                        funciones.at(i) = funcionNueva;    
                        encontre = true;
                }
                i++;

        }

        if (!encontre) {
                funciones.push_back(funcionNueva);
        }


}

void Contexto::agregarVariable(Variable* variable){
	//cout << "agregando variable "  << variable->getNombre() <<endl;  
     
        bool duplicada = lookupVariable(variable); //si la encuentra le soobrescribe el valor

        if (!duplicada) {
	        //cout << "agregando variable no duplicada "  << variable->getNombre() <<endl;       
                variables.push_back(*variable);
        } 

}

NodoFuncion* Contexto::buscarFuncion(char* id) {

        int i = 0;

        while (i<funciones.size()){
                NodoFuncion* funcion = funciones.at(i);
	        //cerr << "buscando funcion "  << funcion->nombre <<endl;       

                        
                if (funcion != NULL && strcmp (id,funcion->nombre) == 0 ){
	                //cerr << "encontre "  << funcion->nombre <<endl;       
                        return funcion;
                }
                i++;
                //cerr << "iterando "  <<endl;       
        }
   
     
        return NULL;
}

void Contexto::printContexto(int tabLevel) const
{
	string tabs;
	for (int j = 0; j < tabLevel; ++j)
		tabs += "\t";

	if (tabLevel == 0)
		cout << "Contexto :\n";

	cout << tabs << "\tNumero de variables Globales: " << variables.size() << endl;
	for (unsigned int i = 0; i < variables.size(); ++i)
	{
		const Variable *it = &variables[i];
		cout << tabs << "\t" << it->getNombre() << " - ";
		if (it->getValor() != 0)
			it->getValor()->imprimir();
		else if (it->getTipo() != 0)
			cout << it->getTipo()->imprimir();
		else
			cout << "NADA";
		cout << endl;
	}
	
	cout << tabs << "\tNumero de Scopes: " << scopes.size() << endl;
	
	
	cout << tabs << "\tNumero de funciones: " << funciones.size() << endl;
	for (unsigned int i = 0; i < funciones.size(); ++i)
	{
                NodoFuncion* funcion = funciones.at(i);
	        cerr << tabs << "\t"  << funcion->nombre <<endl;       
	}
}


void Contexto::nuevoScope(){ // pushea un scope
	Scope* scopeNuevo = Scope::scopeVacio();
	scopes.push_back(*scopeNuevo);
}

void Contexto::agregarVariableAScopeActual(Variable * variable){
	if(scopes.size()==0){
		Contexto::nuevoScope(); // para el caso de scope inicial
		//cerr<<" Contexto::agregarVariableAScopeActual Contexto::nuevoScope() caso scope Inicial"<<endl; 
	} 
	scopes.at(scopes.size()-1).agregarVariable(variable);
}

void Contexto::eliminarScope(){ // elimina el ultimo scope pusheado
	if(scopes.size()>0) //  si hay para quitar 
		scopes.pop_back(); // quito
}

