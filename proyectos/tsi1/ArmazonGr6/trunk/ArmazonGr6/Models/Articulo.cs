using System;
using System.Collections.Generic;
using System.Data.Linq;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace ArmazonGr6.Models
{
    public partial class Articulo
    {
        public static SelectList nombresCategorias;



        public bool IsValid
        {
            get { return (GetRuleViolations().Count() == 0); }
        }
        public IEnumerable<RuleViolationS> GetRuleViolations()
        {
            yield break;
        }
        partial void OnValidate(ChangeAction action)
        {
            if (!IsValid)
                throw new ApplicationException("Rule violations prevent saving");
        }

        public /*static*/ SelectList NombreCategorias(Articulo a)
        {
            CategoriaRepository catRep = new CategoriaRepository();
            List<String> lista = new List<String>();
            lista.Add("  ");
            Categoria c = null;
            foreach (Categoria aux in catRep.FindAllCategorias())
            {
                //lista.Add(aux.nombre, (int) aux.idSuperCategoria);

                lista.Add(aux.nombre.Trim());
                if(a != null && a.idCategoria == aux.id)
                    c = aux;
            }
            String valor = "  ";
            if (c != null)
                valor = c.nombre.Trim();
            nombresCategorias = new SelectList(lista, valor);
            return nombresCategorias;
            
        }

            // Coleccion de Items Clave Valor
       /* private List<Registro> registros;

        public List<Registro> Registros(Articulo a)
        {
            List<Registro> regs=null;
            

            return regs;
        }*/
    
    }

   
    


}
