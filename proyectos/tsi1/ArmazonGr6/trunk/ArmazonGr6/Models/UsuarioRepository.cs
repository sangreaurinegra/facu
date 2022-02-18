using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ArmazonGr6.Models {
    public class UsuarioRepository {


        private ArmazonModelDataContext db;

        public UsuarioRepository() {
            db = new ArmazonModelDataContext();
        }

        public UsuarioRepository(String conStr) {
            db = new ArmazonModelDataContext(conStr);
        }

        public void RegistrarUsuario(String usuario) {
            Carrito cart = new Carrito();
            Cliente clien = new Cliente();
            Usuario u = new Usuario();
            u.esAdmin = 0;
            u.login = usuario;
            
            db.Usuarios.InsertOnSubmit(u);
            db.Carritos.InsertOnSubmit(cart);
            db.SubmitChanges();
            clien.id = u.id;
            clien.idCarritoActual = cart.id;
            db.Clientes.InsertOnSubmit(clien);
            db.SubmitChanges();
            
        }

        public Usuario FindUsuario(string user)
        {
            return (from Usuarios in db.Usuarios
                   where Usuarios.login == user
                   select Usuarios).First();
        }

        public Cliente FindCliente(string user) {
            int idUser = (from Usuarios in db.Usuarios
                    where Usuarios.login == user
                    select Usuarios.id).First();
            
            return db.Clientes.SingleOrDefault(c => c.id == idUser);
        }

        //usada para dar de alta un cliente cuando una tienda externa nos compra
        // articulos
        //RETORNO: id de cliente.
        public Cliente AddClienteProveedor(Usuario user) {
            // creamos el usuario
            db.Usuarios.InsertOnSubmit(user);
            db.SubmitChanges();
            
            //creamos el cliente
            Cliente cli = new Cliente();
            cli.id = user.id;
            
            //creamos el proveedor con el mismo nombre
            Proveedor prov = new Proveedor();
            prov.nombre = user.login;
            prov.url = "SISTEMA CLIENTE";
            db.Proveedors.InsertOnSubmit(prov);

            //creamos el carrito que no puede ser nulo.
            Carrito car = new Carrito();
            db.Carritos.InsertOnSubmit(car);
            db.SubmitChanges();
            cli.idCarritoActual = car.id;
            db.Clientes.InsertOnSubmit(cli);
            db.SubmitChanges();

            return cli;
        }

        public int iniciarSesion(String usuario) {


            try {
                int esAdmin = (from u in db.Usuarios
                              where
                                  u.login == usuario
                              select u.esAdmin).First();
                return esAdmin;
            }
            catch (Exception e) { 
                // no hago nada retorno 2 y listo.
            }
            return 2;
        }

        public void salvarCalificacion(Calificacion caf) {
            db.Calificacions.InsertOnSubmit(caf);
            db.SubmitChanges();
        }

        public CloudItem obtenerTag(String name){
                return db.CloudItems.SingleOrDefault(t => t.name.Trim() == name);
        }

        public IEnumerable<CloudItem> obtenerTags(int idArt) {
            var idtags = (from t in db.TagArticulos
                         where t.idArticulo == idArt
                         select t.idCloudItem).ToList();
            return db.CloudItems.Where(c => idtags.Contains(c.id));
        }

        public bool estaArticuloTageado(int idArt, int tag) {
            var tagArt = db.TagArticulos.SingleOrDefault(ta => ta.idArticulo == idArt && ta.idCloudItem == tag);
            return tagArt != null;
        }

        public int salvarTag(CloudItem ci) {
            db.CloudItems.InsertOnSubmit(ci);
            db.SubmitChanges();
            return ci.id;
        }

        public void salvarTagArticulo(int tag, int art) {
            TagArticulo ta = new TagArticulo();
            ta.idArticulo = art;
            ta.idCloudItem = tag;
            db.TagArticulos.InsertOnSubmit(ta);
            db.SubmitChanges();
            
        }

        public void Save() {
            db.SubmitChanges();
        }

        public bool esAdmin(String usuario) {

            if(usuario.Equals(""))
                return false;
            try {
                int esAdmin = (from u in db.Usuarios
                               where
                                   u.login == usuario
                               select u.esAdmin).First();
                return esAdmin==1;
            }
            catch (Exception e) {
                // no hago nada retorno 2 y listo.
            }
            return false;
        }

        //las categorías que se le vendieron al usuario ordenadas por cantidad de productos en forma descendente
        public IQueryable<Categoria> FindCategoriasVendidas(string usuario)
        {
           var queryCategorias = from h in db.HistoricoCarritos
                                  join c in db.Carritos on h.idCarrito equals c.id
                                  join ac in db.ArtCants on c.id equals ac.idCarrito
                                  join a in db.Articulos on ac.idArticulo equals a.id
                                  join u in db.Usuarios on h.idCliente equals u.id
                                  where u.login.Equals(usuario)
                                  join cat in db.Categorias on a.idCategoria equals cat.id
                                  group ac.cantidad by a.idCategoria into totalPorCat
                                  select new { idcategoria = totalPorCat.Key, sumaCant = totalPorCat.Sum() };

           return from cat in queryCategorias
                  join c in db.Categorias on cat.idcategoria equals c.id
                  orderby cat.sumaCant descending
                  select c;
        }

        //devolver el articulo mejor rankeado de una categoría
        public IQueryable<Articulo> FindArticulosRankeados(int? idCat)
        {
            
            var queryArticulo = from a in db.Articulos
                                where ((idCat!=null && a.idCategoria == idCat)
                                        || idCat==null)
                                join c in db.Calificacions on a.id equals c.idArticulo
                                group c.puntuacion by a.id into promedioPorArt
                                select new {idArticulo = promedioPorArt.Key, promArt = promedioPorArt.Average() };
            
            return from art in queryArticulo
                   join a in db.Articulos on art.idArticulo equals a.id
                   orderby art.promArt descending
                   select a;
        }
        
        public IEnumerable<Articulo> FindArticulosAProponer(string usuario, int cantAProponer)
        {
            
            var listaCatOrdDesc = this.FindCategoriasVendidas(usuario);
            List<Articulo> listaArtAProponer=new List<Articulo>();
            if (listaCatOrdDesc.Count()==0 )
            {
                var listaArtRank = this.FindArticulosRankeados(null);  
                if (listaArtRank!=null){
                    int encontre = 0;
                    int i = 0;

                    while (listaArtRank.Count<Articulo>() > i && encontre < cantAProponer)
                    {
                        listaArtAProponer.AddRange(listaArtRank.Skip(i).Take(1) );
                        encontre++;
                        i++;
                    }

                }
            }
            else
            {
                int encontre = 0;
                int i=0;
                
                while ( listaCatOrdDesc.Count<Categoria>() > i && encontre < cantAProponer)
                {
                    var listaArt = this.FindArticulosRankeados(listaCatOrdDesc.Skip(i).Take(1).First().id);
                    if (listaArt != null && listaArt.Count() > 0) {
                        listaArtAProponer.Add( listaArt.First<Articulo>());
                        encontre++;
                    }
                    i++;
                }
            }

            return listaArtAProponer;     
        }

        public IQueryable<CloudItem> GetAllTags()
        {
            return db.CloudItems.OrderByDescending(c => c.weight);
        }

        public void DeleteTag(int idTag)
        {
            CloudItem tag = db.CloudItems.SingleOrDefault(c => c.id == idTag);
            IQueryable<TagArticulo> tagArts = from ta in db.TagArticulos
                                        where ta.idCloudItem == idTag
                                        select ta;
            db.TagArticulos.DeleteAllOnSubmit(tagArts);  
            db.CloudItems.DeleteOnSubmit(tag);
            db.SubmitChanges();
            
        }
        
    }


    
}