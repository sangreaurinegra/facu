using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ArmazonGr6.Models;
using ArmazonGr6.Helpers;
using ArmazonGr6.ArmazonInterface;

namespace ArmazonGr6.ArmazonInterface.Model {
    public class ArmazonModelProxy {
        ArticuloRepository artRepo;
        CarritoRepository cartRepo;
        //ProveedorRepository provRepo = new ProveedorRepository();
        UsuarioRepository usuRepo;

        public ArmazonModelProxy() { 
            //String con = "Data Source=.\\SQLEXPRESS;AttachDbFilename=|DataDirectory|\\ArmazonBD.mdf;Integrated Security=True;User Instance=True";
            artRepo = new ArticuloRepository();
            cartRepo = new CarritoRepository();
            usuRepo = new UsuarioRepository();
        }


        public ICollection<DCProduct> search(String fullText) {
            //busco los articulos y quito del resultado todos los articulos que sean externos.
            var lista = artRepo.buscarStringEnArticulos(fullText);
            var listaFiltrada = lista.Where(a => a.Externo == null);
            return ModelConverter.convert(listaFiltrada);
        }


        public DCProduct GetProduct(int idProduct) {

            return ModelConverter.convert(artRepo.GetArticulo(idProduct),artRepo.Registros(idProduct), AvgRating(idProduct));
        }


        public ICollection<DCRating> getRatings(int idProduct) {
                        
            return ModelConverter.convert(artRepo.Calificaciones(idProduct));
        }


        public bool CartBuy(String user, ICollection<DCCartItem> items) {
            bool exito = false;
            Cliente cli = null;
            try {
                cli = usuRepo.FindCliente(user);
            }
            catch { // no encontre el cliente lo agrego como cliente-proveedor
                Usuario u = new Usuario();
                u.login = user;
                u.password = "123456";
                cli = usuRepo.AddClienteProveedor(u);
            }
            
            if (cli != null) {
                Carrito car = cartRepo.GetCarrito(cli.idCarritoActual);
                // este carro es recien creado, o el creado en la ultima compra
                // por lo tanto esta vacio, agrego los artcants al carro
                // y confirmo la compra, que es como si comprara todo el carro.
                List<DCCartItem> dcItems = items.ToList();
                Dictionary<int, ArtCant> compras = new Dictionary<int, ArtCant>();
                foreach(DCCartItem it in dcItems){
                    ArtCant ac = ModelConverter.convert(it);
                    ac.idCarrito = car.id;
                    cartRepo.AddArtCant(ac);
                    cartRepo.Save();
                    compras.Add(ac.idArticulo, ac);
                }
                cartRepo.ConfirmarCompra(car, compras);
                exito = true;
            } 
            return exito;
        }


        public float AvgRating(int idArt) {

            return artRepo.GetPromedioCalificaciones(idArt);
        }

        public IEnumerable<Registro> Registros(int idArt) {

            return artRepo.Registros(idArt);
        }
    }
}
