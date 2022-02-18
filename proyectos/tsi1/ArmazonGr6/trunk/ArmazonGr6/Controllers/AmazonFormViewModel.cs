using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ArmazonGr6.Models;

namespace ArmazonGr6.Controllers
{
    public class AmazonFormViewModel
    {
        List<Articulo> listaArticulos;
        String msgError;
        
        bool hayError;

        public AmazonFormViewModel(List<Articulo> l) {
            hayError = false;
            listaArticulos = l;
        }
        public AmazonFormViewModel(string s)
        {
            hayError = true;
            msgError =  s;
        }
        public void setLista(List<Articulo> l)
        {
            hayError = false;
            listaArticulos=l;
        }
        public void setMsgError(string s)
        {
            hayError = true;
            msgError = s;
        }
        public List<Articulo> getLista()
        {
            return listaArticulos;
        }
        public string getMsgError(){
            return msgError;
        }
        public bool getHayError()
        {
            return this.hayError;
        }
    }
}
