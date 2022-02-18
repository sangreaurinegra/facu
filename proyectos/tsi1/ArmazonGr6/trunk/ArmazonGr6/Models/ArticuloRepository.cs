using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ArmazonGr6.Models
{
    public class ArticuloRepository
    {
        private ArmazonModelDataContext db;
        //
        // Query Methods

        public ArticuloRepository(string conn)
        {
            db = new ArmazonModelDataContext(conn);
        }

        public ArticuloRepository()
        {
            db = new ArmazonModelDataContext();
        }

        
        public IQueryable<Articulo> FindAllArticulos()
        {
            return db.Articulos;
        }

        public IQueryable<Articulo> FindArticulosConEtiqueta(string etiqueta)
        {
            int idEtiqueta = (db.CloudItems.Where(ci => ci.name.Trim()==etiqueta.Trim()).First().id);
            var ret = from tgArt in db.TagArticulos
                      join  art in db.Articulos
                      on tgArt.idArticulo  equals art.id
                      where tgArt.idCloudItem == idEtiqueta
                      select art;

            return ret;
        }

        public IQueryable<Articulo> FindArticulosSinCategoria() {
            return db.Articulos.Where(a => a.idCategoria == null);
        }

        public IQueryable<Articulo> FindArticulo(string nom)
        {
            return from Articulos in db.Articulos
                   where Articulos.nombre == nom
                   select Articulos;
        }
        public IQueryable<Articulo> FindArticuloExterno(string claveExterna)
        {
            return from artExt in db.Externos
                   join  art in db.Articulos
                   on artExt.id equals art.id
                    where artExt.claveExterna==claveExterna
                    select art;
                    
        }
        public Articulo GetArticulo(int id)
        {
            return db.Articulos.SingleOrDefault(d => d.id == id);
        }
        //
        // Insert/Delete Methods
        public void Add(Articulo art)
        {
            db.Articulos.InsertOnSubmit(art);
        }

        public void Delete(Articulo art)
        {
            db.Articulos.DeleteOnSubmit(art);
        }
        public void AddExterno(Externo art)
        {
            db.Externos.InsertOnSubmit(art);
        }

        public void DeleteExterno(Externo art)
        {
            db.Externos.DeleteOnSubmit(art);
        }
        //
        // Persistence
        public void Save()
        {
            db.SubmitChanges();
        }

        public IEnumerable<Registro> Registros(int idArt) {
            return db.Registros.Where(r => r.idArticulo == idArt);
        }

        public IEnumerable<Calificacion> Calificaciones(int idArt) {
            return db.Calificacions.Where(r => r.idArticulo == idArt);
        }

        public float GetPromedioCalificaciones(int idArt) {
            float avg = 0;
            try
            {
                avg = (float) db.Calificacions.Where(c => c.idArticulo == idArt).Average(a => a.puntuacion);
            }catch{
                // no hay calificaciones retorno 0
            }
            return avg;
        }

        public Dictionary<int,int> GetCalificacionesPorUsuario(int idArt) {
            Dictionary<int, int> dic = new Dictionary<int,int>();
            try {
                var califs = from c in db.Calificacions
                             where c.idArticulo == idArt
                             group c by c.puntuacion into g
                             select new { puntos = g.Key, cantidad = g.Count() };
                foreach (var cal in califs) {
                    dic.Add(cal.puntos, cal.cantidad);
                }
                
            }
            catch {
                // no hay calificaciones retorno 0
            }
            return dic;
        }

        public void Update(Articulo art)
        {
            try
            {
                var articulo = db.Articulos.SingleOrDefault(d => d.id == art.id);
                articulo.Calificacions = art.Calificacions;
                //articulo.Categoria = art.Categoria;
                //articulo.Externo = art.Externo;
                //articulo.id = art.id;
                articulo.idCategoria = art.idCategoria;
                articulo.imagen = art.imagen;
                //articulo.Interno = art.Interno;
                articulo.nombre = art.nombre;
                articulo.precio = art.precio;
                //articulo.Registros = art.Registros;
                // articulo.TagArticulos = art.TagArticulos;
                db.SubmitChanges();
            }
            catch(Exception e)
            {
                
            }
        }

        public void UpdateExterno(Externo art)
        {
            var articulo = db.Externos.SingleOrDefault(d => d.id == art.id);
            if(articulo !=null){
                articulo.Articulo = art.Articulo;
                articulo.id = art.id;
                articulo.claveExterna = art.claveExterna;
                articulo.idProveedor = art.idProveedor;
            }else
            {
                db.Externos.InsertOnSubmit(art);
            }
            db.SubmitChanges();
        }

        public void UpdateRegistro(Registro reg)
        {
            var registro = db.Registros.SingleOrDefault(d => d.idArticulo == reg.idArticulo && d.idCampo==reg.idCampo);
            if (registro != null)
            {
                registro.valor = reg.valor;
            }
            else
            {
                db.Registros.InsertOnSubmit(reg);
            }
            db.SubmitChanges();
        }

        public List<Articulo> buscarStringEnArticulos(string texto)
        {
            List<Articulo> arts = (from art in db.Articulos
                                      join cat in db.Categorias
                                      on art.idCategoria equals cat.id
                                      where cat.nombre.Contains(texto)
                                      || art.nombre.Contains(texto)
                                      select art).ToList();
           
            List<Articulo> masArts = (from art in db.Articulos
                                   where art.idCategoria == null &&
                                   art.nombre.Contains(texto)
                                   select art).ToList();
            masArts.AddRange(arts);

            return masArts;
        }

        
    }
}
