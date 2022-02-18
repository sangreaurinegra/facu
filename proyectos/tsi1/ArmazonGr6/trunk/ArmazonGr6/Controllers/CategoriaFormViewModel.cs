using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ArmazonGr6.Models;
using System.Web.Mvc;

namespace ArmazonGr6.Controllers
{
    public class CategoriaFormViewModel
    {
        public Categoria categoria { get; private set; }
        public SelectList listaNombreCategorias { get; private set; }
        private CategoriaRepository catRep = new CategoriaRepository();


        public CategoriaFormViewModel(Categoria c)
        {
            categoria = c;
            Categoria aux2 = null;
            List<String> lista = new List<String>();
            //Dictionary<String,int> lista = new Dictionary<String,int>();
            lista.Add("  ");
            foreach (Categoria aux in catRep.FindAllCategorias())
            {
                //lista.Add(aux.nombre, (int) aux.idSuperCategoria);

                lista.Add(aux.nombre);
                if (c.idSuperCategoria == aux.id)
                    aux2 = aux;
            }
            String val = "  ";
            if (aux2 != null)
                val = aux2.nombre;
            listaNombreCategorias = new SelectList(lista, val);
            
        }

    }
}
