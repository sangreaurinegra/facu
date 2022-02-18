using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ArmazonGr6.Models
{
    public class CategoriaRepository
    {
        private ArmazonModelDataContext db = new ArmazonModelDataContext();

        //
        // Query Methods
        public IQueryable<Categoria> FindAllCategorias()
        {
            return db.Categorias;
        }
        public IQueryable<Categoria> FindAllRaices() {
            return from categoria in db.Categorias
                   where categoria.idSuperCategoria == null
                   orderby categoria.nombre
                   select categoria;
        }
        public IQueryable<Categoria> FindSubCategorias(Categoria c)
        {
            return from categoria in db.Categorias
                   where categoria.idSuperCategoria == c.id
                   orderby categoria.nombre
                   select categoria;
        }

        //devuelve todas las categorias padre
        public IQueryable<Categoria> FindSuperCategorias() {
            return from categoria in db.Categorias
                   where categoria.idSuperCategoria == null
                   orderby categoria.nombre
                   select categoria;
        }

        public Categoria GetSuperCategoria(Categoria c)
        {
            return db.Categorias.SingleOrDefault(d => d.id == c.idSuperCategoria);
        }
        public Categoria GetCategoria(int id)
        {
            return db.Categorias.SingleOrDefault(d => d.id == id);
        }
        public IQueryable<Articulo> FindAllArticulos()
        {
            return db.Articulos ;
        }
        public IEnumerable<Articulo> OrdenarPorPromedioCalificacion(List<Articulo> listaAOrdenar)
        {
            var queryArticulo = from a in listaAOrdenar
                                join c in db.Calificacions on a.id equals c.idArticulo
                                group c.puntuacion by a.id into promedioPorArt
                                select new { idArticulo = promedioPorArt.Key, promArt = promedioPorArt.Average() };

            return from art in queryArticulo
                   join a in db.Articulos on art.idArticulo equals a.id
                   orderby art.promArt descending
                   select a;
        }
        //
        // Insert/Delete Methods
        public void Add(Categoria c)
        {
            if(c.id > 0)
            {
                db.Categorias.Attach(c,true);   
            }else
            {
                db.Categorias.InsertOnSubmit(c); 
            }
        }
        /*
        private List<Categoria> paraBorrar(Categoria cat)
        {
            IEnumerable<Categoria> subcats = cat.getHijos();
            List<Categoria> listAux = new List<Categoria>();
            foreach (Categoria subcat in subcats)
            {
                listAux.AddRange(paraBorrar(subcat));
                
            }
            listAux.Add(cat);
            return listAux;
        }*/

        public bool Delete(Categoria c) 
        {
            if(!tieneArticulosCategoria(c))
            {
                
                IEnumerable<Categoria> subcats = c.getHijos();
                if ((subcats == null) || (subcats.Count() == 0))
                {
                    Categoria cat = db.Categorias.SingleOrDefault(ca => ca.id == c.id);
                    db.Categorias.DeleteOnSubmit(cat);
                    db.SubmitChanges();
                }
                else
                {
                    foreach (Categoria subcat in subcats)
                    {
                        Delete(subcat);
                    }
                    db.Categorias.DeleteOnSubmit(c);
                    db.SubmitChanges();
                    
                }
                
                return true;
            }else
            {
                return false;
            }
            
        }
        //
        // Persistence
        public void Save()
        {
            db.SubmitChanges();
        }

        public Categoria GetCategoriaByName(String nombre) {
            return db.Categorias.SingleOrDefault(d => d.nombre == nombre);
        }


        private bool tieneArticulosCategoria(Categoria cat)
        {
            bool ret = false;
            if(cat.Articulos.Count > 0)
            {
                return true;
            }else
            {
                IEnumerable<Categoria> subcats = cat.getTodasLasSubCategorias();
                foreach (Categoria subcat in subcats)
                {
                    if(subcat.Articulos.Count > 0)
                    {
                        return true;
                    }
                }
            }
            return ret;
        }
        public void Update(Categoria cat)
        {
            var categ = db.Categorias.SingleOrDefault(d => d.id == cat.id);
            categ.nombre = cat.nombre;
            categ.idSuperCategoria = cat.idSuperCategoria;
            db.SubmitChanges();
        }
    }

}
