#ifndef TIPOVALOR_H
#define TIPOVALOR_H
#include <iostream>
#include <vector>



using namespace std;
class Pila;

class TipoValor {
    public:
        virtual int getTipo() = 0;
        virtual void imprimir()=0;
        virtual ~TipoValor();
};

class TInteger : public TipoValor{
    private:
        int num;
    public:
        TInteger(int n){
            num = n;
        }
        int getValor(){
            return num;
        }
        int getTipo(){
            return 0;   //0-TipoInt
        }
        void imprimir(){
            cout<<num;
        }
};

class TReal : public TipoValor{
    private:
        float num;

    public:
        TReal(float n){
            num = n;
        }
        float getValor(){
            return num;
        }
        int getTipo(){
            return 1;   //1-TipoReal
        }
        void imprimir(){
            cout<<num;
        }
};

class TString : public TipoValor{
    private:
        string str;
    public:
        TString(string s){
            str = s;
        }
        string getValor(){
                return str;
            }
        int getTipo(){
            return 2;   //2-tipoString
        }
        void imprimir(){
            cout<<str;
        }
};

class TBool : public TipoValor{
    private:
        bool booleano;

    public:
        TBool(bool b){
            booleano = b;
        }
        bool getValor(){
                return booleano;
            }
        int getTipo(){
            return 3;   //3-tipoBool
        }
        void imprimir(){
            if (booleano)
                cout<<"true";
            else
                cout<<"false";
        }
};

class TNil : public TipoValor{
    private:
        bool nil;

    public:
        TNil(){
            nil = false;
        }
        bool getValor(){
                return nil;
        }
        int getTipo(){
            return 6;
        }
        void imprimir(){
            cout<<"nil";
        }
};

class TArreglo : public TipoValor{
    private:
        vector<TipoValor*>* arreglo;

    public:
        TArreglo(){
            arreglo = new vector<TipoValor*>(50,new TNil());
        }

        vector<TipoValor*>* getValor(){
                return arreglo;
        }
        int getTipo(){
            return 4;
        }

        TipoValor* getPosicion(int i){
            return arreglo->at(i);
        }

        void asignarPosicion(int i, TipoValor* v){
            if (i<500)
                arreglo->at(i) = v;
            else
                throw string("Error: Fuera de rango\n");
        }
        void imprimir(){
            int i;
            cout<<"[ ";
            for (i=0;i<arreglo->size();i++){
                if (arreglo->at(i)->getTipo()!=6){
                    cout<<"(" << i << ",";
                    arreglo->at(i)->imprimir();
                    cout<<") ";
                }
            }
            cout<<"]\n";
        }
};

class TObjeto: public TipoValor{

    private:

        Pila* atributos;
        string nombre;

    public:

        TObjeto(){
        }

        Pila* getAtributo(){
            return atributos;
        }

        void setNombre(string nom){
            nombre=nom;
        }

        void setPila(Pila* p){
            atributos=p;
        }


        int getTipo(){
            return 5;
        }
        void imprimir(){
            cout<<"nombre: "<<nombre<<"\n";
            cout<<"atributos:";
        }

};



#endif // TIPOVALOR_H
