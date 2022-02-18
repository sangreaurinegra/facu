using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ArmazonGr6.Models
{
    public partial class Categoria
    {
        private CategoriaRepository categoriaRepository = new CategoriaRepository();

        public IQueryable<Categoria> getHijos(){
            return categoriaRepository.FindSubCategorias(this);
        }
        /*
        public IQueryable<Articulo> buscarArticulos()
        {
            //IQueryable<Articulo> 
            var lista = this.Articulos;//.AsQueryable<Articulo> ;
            foreach (Categoria c in this.getHijos()){
                var lista2 =  c.buscarArticulos();
                if (lista2 != null)
                    lista.Concat(lista2);
            };
            return lista.AsQueryable<Articulo>() ;

        }
        */
        public IEnumerable<Categoria> getTodasLasSubCategorias()
        {
            
            Stack<Categoria> stackAux = new Stack<Categoria>();
            List<Categoria> lista = new List<Categoria>();
            Categoria c=null;

            stackAux.Push(this);
            while (stackAux.Count() != 0)
            {
                c = stackAux.Pop();
                lista.Add(c);
                foreach (Categoria cat in c.getHijos()) {
                    stackAux.Push(cat);
                }
            };

            return lista;            
        }

        public Categoria getPadre() 
        {             
            Categoria c = categoriaRepository.GetCategoria((int) this.idSuperCategoria);
            return c;
                 
        }

        public bool IsValid
        {
            get { return (GetRuleViolations().Count() == 0); }
        }
        public IEnumerable<RuleViolation> GetRuleViolations()
        {
            if (String.IsNullOrEmpty(nombre))
                yield return new RuleViolation("Nombre requerido", "Nombre");

            if (idSuperCategoria != null)
            {
                Categoria c = categoriaRepository.GetCategoria((int)idSuperCategoria);
                if (c == null)
                    yield return new RuleViolation("No existe la categoría " + idSuperCategoria, "idSuperCategoria");
                else if (id == idSuperCategoria)
                    yield return new RuleViolation("No pueden coincidir la categoría y la super categoría", "idSuperCategoria");
            }

            yield break;
        }
        partial void OnValidate(System.Data.Linq.ChangeAction action)
        {
            if (!IsValid)
                throw new ApplicationException("No se guardaron datos por violación de reglas");
        }
    }

    public class RuleViolation
    {
        public string ErrorMessage { get; private set; }
        public string PropertyName { get; private set; }
        public RuleViolation(string errorMessage)
        {
            ErrorMessage = errorMessage;
        }
        public RuleViolation(string errorMessage, string propertyName)
        {
            ErrorMessage = errorMessage;
            PropertyName = propertyName;
        }
    }
}
