using System;
using System.Collections;
using System.Collections.Generic;
using System.Data.Linq;
using System.Globalization;
using System.Linq;
using System.Web;
using System.Web.UI.MobileControls;
using ArmazonGr6.Controllers;
using ArmazonGr6.Helpers;


namespace ArmazonGr6.Models
{
    public class CarritoRepository
    {
        private ArmazonModelDataContext db;

        public CarritoRepository(string conn)
        {
            db = new ArmazonModelDataContext(conn);
        }

        public CarritoRepository()
        {
            db = new ArmazonModelDataContext();
        }

        public IQueryable<Carrito> FindAllCarritos()
        {
            return db.Carritos;
        }

        public Carrito FindCarritoPorIdUsuario(int idCliente)
        {
            Cliente cliente = (from Cliente in db.Clientes
                               where Cliente.id == idCliente
                               select Cliente).First();
            return GetCarrito(cliente.idCarritoActual);
        }

        public Carrito GetCarrito(int id)
        {
            return db.Carritos.SingleOrDefault(d => d.id == id);
        }
        //
        // Insert/Delete Methods
        public void Add(Carrito carrito)
        {
            db.Carritos.InsertOnSubmit(carrito);
        }

        public void Delete(Carrito Carrito)
        {
            db.Carritos.DeleteOnSubmit(Carrito);
        }
        //
        // Persistence
        public void Save()
        {
            db.SubmitChanges();
        }

        public void Update(Carrito carrito)
        {
            Carrito carri = db.Carritos.SingleOrDefault(d => d.id == carrito.id);
            carri.Clientes = carrito.Clientes;
            carri.fechaCompra = carrito.fechaCompra;
            carri.FormaPago = carrito.FormaPago;
            carri.montoTotal = carrito.montoTotal;
            db.SubmitChanges();
        }

        
        public void AgregarAlCarrito(int idArt, int cantidad, int idCarrito)
        {
            List<ArtCant> artCants = (from ArtCants in db.ArtCants
                   where ArtCants.idCarrito == idCarrito && ArtCants.idArticulo==idArt
                   select ArtCants).ToList();

            if (artCants.Count > 0)
            {
                ArtCant artCant = artCants.ElementAt(0);
                artCant.cantidad = artCant.cantidad + cantidad;
                
            }else
            {
                db.ArtCants.InsertOnSubmit(new ArtCant(idArt, idCarrito, cantidad));
            }
            Save();
        }

        public void guardarToken(String token, int idCarrito) {
            Carrito carrito = (from c in db.Carritos where c.id == idCarrito select c).First();
            carrito.token = token;
            db.SubmitChanges();
        
        }

        public Carrito getCarritoByToken(String token) {
            Carrito carrito = (from c in db.Carritos where c.token.Trim().Equals(token)  select c).First();
            return carrito;

        }

        public void ConfirmarCompra(Carrito carro,IDictionary<int,ArtCant> articulosCompra)
        {
            if (carro.ArtCants.Count >= articulosCompra.Count)
            {
                EntitySet<ArtCant> setAux = new EntitySet<ArtCant>();
                ArticuloRepository artRepo = new ArticuloRepository();
                Carrito nuevoCarro = new Carrito();
                int idCliente = db.Clientes.SingleOrDefault(c => c.idCarritoActual == carro.id).id;
                nuevoCarro.Clientes = carro.Clientes;
                nuevoCarro.estado = 1;
                Add(nuevoCarro);
                Save();
                System.Console.WriteLine(nuevoCarro.id);
                foreach (var artCant in carro.ArtCants)
                {
                    // no se debe borrar los artcants, cambio el algoritmo para que los artcants que 
                    // no voy a comprar los asigne al carrito nuevo
                    if (!articulosCompra.ContainsKey(artCant.idArticulo))
                    {

                        artCant.idCarrito = nuevoCarro.id;
                        Save();
                        
                    }
                    else
                    {
                        setAux.Add(artCant);
                        
                    }
                    /*
                     if (!articulosCompra.ContainsKey(artCant.idArticulo))
                    {
                        ArtCant nuevoArtCant = new ArtCant(artCant.idArticulo,nuevoCarro.id,artCant.cantidad);
                        AddArtCant(nuevoArtCant);
                        Save();
                    }
                    else
                    {
                        setAux.Add(artCant);
                    }
                     
                     */
                }
                /*
                foreach (ArtCant cant in setAux)
                {
                    DeleteArtCant(cant);
                    Save();
                }*/

                Articulo articAux = null;
                carro.fechaCompra = DateTime.Now;
                carro.estado = 2;
                foreach (KeyValuePair<int, ArtCant> acAux in articulosCompra)
                {
                    articAux = artRepo.GetArticulo(acAux.Key);
                    carro.montoTotal = carro.montoTotal + acAux.Value.cantidad * articAux.precio;
                }
                HistoricoCarrito hisCArt = new HistoricoCarrito();
                hisCArt.idCarrito = carro.id;
                hisCArt.idCliente = idCliente;
                db.HistoricoCarritos.InsertOnSubmit(hisCArt);
                Update(carro);

            }
        }


        public List<Carrito> GetCarritosHistoricoPoUsuario(int idUsuario)
        {
            List<Carrito> historicos = (from carro in db.Carritos
                                      join hc in db.HistoricoCarritos
                                      on carro.id equals hc.idCarrito
                                      where hc.idCliente == idUsuario
                                      select carro).ToList();


            return historicos;
        }

        public List<Carrito> GetCarritosPendientes()
        {
            List<Carrito> historicos = (from carro in db.Carritos
                                        where carro.estado == 2
                                        select carro).ToList();


            return historicos;
        }

        /** A partir de aca  van las funcionalidaes de ArtCAnt **/

        //
        // Insert/Delete Methods
        public void AddArtCant(ArtCant artCant)
        {
            db.ArtCants.InsertOnSubmit(artCant);
        }

        public void DeleteArtCant(ArtCant artCant)
        {
            db.ArtCants.DeleteOnSubmit(artCant);
        }

        public ArtCant FindArtCantEnCarrito(int idArt,int idCarro)
        {
            ArtCant arts = (from Art in db.ArtCants
                               where Art.idArticulo == idArt && Art.idCarrito == idCarro
                               select Art).First();
            return arts;
        }

        public void QuitarArticuloDeCarrito(int idArt, int idCarro)
        {
            ArtCant art = FindArtCantEnCarrito(idArt, idCarro);
            db.ArtCants.DeleteOnSubmit(art);
            db.SubmitChanges();
        }

        public void QuitarTodosArticulosDeCarrito(int idCarro)
        {
            Carrito carro = GetCarrito(idCarro);
            db.ArtCants.DeleteAllOnSubmit(carro.ArtCants);
            db.SubmitChanges();
        }

        public void UpdateArtCant(ArtCant artCant)
        {
            ArtCant artC = db.ArtCants.SingleOrDefault(d => d.id == artCant.id);
            artC.idArticulo = artCant.idArticulo;
            artC.idCarrito = artCant.idCarrito;
            artC.cantidad = artCant.cantidad;
            
            db.SubmitChanges();
        }

        public ICollection<ArtCant> articulosExternosCarritoSinAmazon(Carrito cart)
        {
            SystemProperties sp;
            ICollection<ArtCant> retList = new List<ArtCant>();
            if (SystemProperties.isSystemPropertiesLoaded())
                sp = SystemProperties.getSystemProperties();
            else
                sp = SystemProperties.loadProperties("C:/Projects/ArmazonGr6/ArmazonGr6/config/config.txt");
            int idAmazon = int.Parse(sp.getProperty("IdProveedorAmazon"));
            foreach (ArtCant artC in cart.ArtCants)
            {
                if ((artC.Articulo.Externo != null) && (artC.Articulo.Externo.idProveedor != idAmazon))
                {
                    retList.Add(artC);
                }
            }
            return retList;
        }

        /* --------------------------------------------------------------------------------
         * ************************** REPORTES *******************************************
         * --------------------------------------------------------------------------------
         */

        public LinkedList<ArticuloCompra> reporteVentas(DateTime fechaDesde, DateTime fechaHasta) {
            System.Collections.Generic.LinkedList<ArticuloCompra> lista = new LinkedList<ArticuloCompra>();
            ArticuloRepository ar = new ArticuloRepository();
            fechaHasta = fechaHasta.AddDays(1);

            var carritos = (from c in db.Carritos
                            where c.fechaCompra != null && (c.fechaCompra >= fechaDesde && c.fechaCompra <= fechaHasta)
                            select c).ToList();
            foreach(Carrito c in carritos){
                var arts = from ac in db.ArtCants
                           where ac.idCarrito == c.id
                           group ac by ac.idArticulo into g
                           select g;
                foreach (var g in arts) {
                    ArticuloCompra artcomp = lista.SingleOrDefault(arc => arc.idArticulo == g.First().idArticulo);
                    if (artcomp == null) {
                        Articulo art = ar.GetArticulo(g.First().idArticulo);
                        int cant = g.Sum(value => value.cantidad);
                        artcomp = new ArticuloCompra(art.id, art.nombre, art.precio, cant);
                        lista.AddLast(artcomp);
                    }
                    else {
                        int cant = g.Sum(value => value.cantidad);
                        artcomp.cantidad += cant;
                    }
                                       
                }                
            }
            return lista;
        }

        public IDictionary<String, ArticuloCompra> reporteArticulosMasVendidosCategoria(int idCategoria) {
            IDictionary<String, ArticuloCompra> dic = new Dictionary<String, ArticuloCompra>();
            int lasCant = -1;
            ArticuloCompra artcomp = null;
            bool hayResult = false;
            if (idCategoria < 0) { // retorno de todas las categorias el mas vendido.
                
                ArticuloRepository ar = new ArticuloRepository();
                CategoriaRepository cr = new CategoriaRepository();
                var categorias = cr.FindAllCategorias();
                foreach (Categoria c in categorias) {
                    // obtengo los articulos de la categoria
                    var arts = from a in db.Articulos
                               where a.idCategoria == c.id
                               select a;
                    foreach (var a in arts) {
                        //para los articulos obtengo los artcants. y encuentro el mas vendido
                        var artcants = from ac in db.ArtCants
                                       where ac.idArticulo == a.id
                                   group ac by ac.idArticulo into g
                                   select g;
                        foreach (var g in artcants) {
                            Articulo art = ar.GetArticulo(g.First().idArticulo);
                            int cant = g.Sum(value => value.cantidad);
                            if (cant > lasCant) {
                                lasCant = cant;
                                artcomp = new ArticuloCompra(0, art.nombre, art.precio, cant);
                                hayResult = true;
                            }
                            
                        }
                    }
                    if (hayResult) {
                        dic.Add(c.nombre, artcomp);
                        hayResult = false;
                    }
                }
            }
            return dic;
        }

        public LinkedList<ReporteResultItem> reporteArtsMejorCalifcadosCategoria(int idCategoria) {
            LinkedList<ReporteResultItem> dic = new LinkedList<ReporteResultItem>();
            double lasCant = -1;
            ReporteResultItem artcomp = null;
            bool hayResult = false;
            if (idCategoria < 0) { // retorno de todas las categorias el mas vendido.

                ArticuloRepository ar = new ArticuloRepository();
                CategoriaRepository cr = new CategoriaRepository();
                var categorias = cr.FindAllCategorias();
                foreach (Categoria c in categorias) {
                    // obtengo los articulos de la categoria
                    var arts = from a in db.Articulos
                               where a.idCategoria == c.id
                               select a;
                    foreach (var a in arts) {
                        //para los articulos obtengo los artcants. y encuentro el mas vendido
                        var califs = from ac in db.Calificacions
                                       where ac.idArticulo == a.id
                                       group ac by ac.idArticulo into g
                                       select g;
                        foreach (var g in califs) {
                            Articulo art = ar.GetArticulo(g.First().idArticulo);
                            double cant = g.Average(value => value.puntuacion);
                            if (cant > lasCant) {
                                lasCant = cant;
                                artcomp = new ReporteResultItem();
                                artcomp.nombreArticulo = art.nombre;
                                artcomp.nombreCategoria = c.nombre;
                                artcomp.calificacion = (int)Math.Round(cant, 0);
                                hayResult = true;
                            }

                        }
                    }
                    if (hayResult) {
                        dic.AddLast(artcomp);
                        hayResult = false;
                    }
                    lasCant = -1;
                }
            }
            return dic;
        }

    }
}
