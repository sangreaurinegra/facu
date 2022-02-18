//Grupo 19
#include "scope.h"

Scope* Scope::scopeVacio()
{
	return new Scope();
}
 
void Scope::agregarVariable(Variable * variable){
	
	// TODO control de copia , si la variable ya esta en el scope
	//cerr << "Scope::agregarVariable "<<variable->getNombre()<< endl;
	variables.push_back(*variable);

}

Variable* Scope::getVariable (string nombre){
	//cerr << "Scope::getVariable "<<nombre<< " variables.size() "<< variables.size() << endl;
	Variable* aux = NULL;
	for (int i = 0; i < (int)variables.size(); i++){
		aux = &variables.at(i);
		
		//cerr << "Scope::getVariable Comparando "<<aux->getNombre()<< " "<<nombre<<endl;
		if (nombre.compare(aux->getNombre())==0){
			//cerr << "Encontre "<< nombre <<endl;
			return aux;
		}

	}	
	return NULL;
}

