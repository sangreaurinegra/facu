using System;
using System.Collections;
using System.Collections.Generic;
using System.Data.Linq;
using System.Globalization;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.UI.MobileControls;
using ArmazonGr6.Helpers;
using ArmazonGr6.Models;

namespace ArmazonGr6.Controllers
{
    
    public class CarritoController : Controller
    {
        CarritoRepository carritoRepo = new CarritoRepository();
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        IDictionary<int,ArtCant> articulosCompra = new Dictionary<int,ArtCant>();
        ArticuloRepository artRepo = new ArticuloRepository();
        
        //
        // GET: /Carrito/

        public ActionResult Index()
        {
            return View();
        }

        public ActionResult FinCompraExitosa() {
            List<ArticuloCompra> retArtCompra = new List<ArticuloCompra>();
            IDictionary<int, ArtCant> artCants = (IDictionary<int,ArtCant>)HttpContext.Session["artsCompra"]; 
            if (artCants.ToList().Count > 0)
            {
                Articulo artAux = null;
                ArticuloCompra artCompraAux = null;
                foreach (ArtCant ac in (artCants.Values))
                {
                    artAux = artRepo.GetArticulo(ac.idArticulo);
                    artCompraAux = new ArticuloCompra(ac.idArticulo, artAux.nombre, artAux.precio, ac.cantidad);
                    retArtCompra.Add(artCompraAux);
                }
            }
            ViewData["listComprados"] = retArtCompra;


            int idCarrito = (int)HttpContext.Session["idHistorico"];
            Carrito carro = carritoRepo.GetCarrito(idCarrito);
            carro.estado = 3;
            carritoRepo.Update(carro);
            HttpContext.Session.Remove("idHistorico");
            return View(carritoRepo.GetCarrito(idCarrito));
        }

        [Authorize]
        public ActionResult SeleccionarFormaPago() {
            
            //carga y muestra las formas de pago.
            FormaPagoRepository fpr = new FormaPagoRepository();
            var lista = fpr.FindAllFormaPago();
            return View(lista);
        }

        //pasa a la siguiente vista mostrando los datos necesarios.
        [Authorize]
        public ActionResult FormaPagoDatos(int id) {
            int paypal = 0;
            int pagoEntrega = 1;
            if (id == paypal)
                return RedirectToAction("Index", "PayPal");
            else if (id == pagoEntrega){
                CommunicationHelper comm = new CommunicationHelper();

                int idCarro = (int)HttpContext.Session["idHistorico"];
                Carrito carro = carritoRepo.GetCarrito(idCarro);
                var listaArtsExt = carritoRepo.articulosExternosCarritoSinAmazon(carro);
                bool result = true;
                if (listaArtsExt != null && listaArtsExt.Count > 0)
                    result = comm.CartBuy("grupo6", listaArtsExt);
                

                if (result)
                    return RedirectToAction("IndexCliente","Sucursals");
                else
                    return RedirectToAction("ErrorDePago", "PayPal");
            }
            else {
                /*FormaPagoRepository fpr = new FormaPagoRepository();
                var campos = fpr.obtnerCamposDeFormaPago(id);
                //carga y muestra los campos de las formas de pago.
                return View(campos);*/
                return RedirectToAction("FinCompraExitosa");
            }
        }


        // recibe una lista de campos<nombre, valor>
        //y envia los datos al ente de pago
        public ActionResult EnviarDatos(int id) {

            FormaPagoRepository fpr = new FormaPagoRepository();
            var campos = fpr.obtnerCamposDeFormaPago(id);
            //carga y muestra los campos de las formas de pago.
            return View(campos);
        }

       [Authorize]
        public ActionResult ArticulosPorCarrito()
        {
            string usuario = AccountController.getUsuarioActual();
            reversarCarrito(usuario);
            EntitySet<ArticuloCompra> retArtCompra = new EntitySet<ArticuloCompra>();
            Carrito carro = GetCarritoPorUsuario(usuarioRepository.FindUsuario(usuario).id);

            if (HttpContext != null)
            {
                articulosCompra = new Dictionary<int, ArtCant>();
                HttpContext.Session.Add("artsCompra", articulosCompra);
            }

            if (carro.ArtCants.Count > 0)
            {
                ArticuloRepository artRepo = new ArticuloRepository();
                Articulo artAux = null;
                ArticuloCompra artCompraAux = null;
                foreach (ArtCant ac in carro.ArtCants)
                {
                    artAux = artRepo.GetArticulo(ac.idArticulo);
                    artCompraAux = new ArticuloCompra(ac.idArticulo,artAux.nombre,artAux.precio,ac.cantidad);
                    retArtCompra.Add(artCompraAux);
                }
            }
            return View(retArtCompra);
        }

        public Carrito GetCarritoPorUsuario(int usuId)
        {
            return carritoRepo.FindCarritoPorIdUsuario(usuId);
        }

        public ActionResult AgregarAlCarrito(int idArt,int cantidad)
        {
            string usuario = AccountController.getUsuarioActual();
            Carrito carro = GetCarritoPorUsuario(usuarioRepository.FindUsuario(usuario).id);
            carritoRepo.AgregarAlCarrito(idArt,cantidad, carro.id);
            Articulo artic = artRepo.GetArticulo(idArt);
            return Json(artic.nombre);
        }


        public ActionResult QuitarArticuloDelCarrito(int idArt)
        {
            string usuario = AccountController.getUsuarioActual();
            Carrito carro = GetCarritoPorUsuario(usuarioRepository.FindUsuario(usuario).id);
            carritoRepo.QuitarArticuloDeCarrito(idArt, carro.id);
            //return Redirect("ArticulosPorCarrito");
 
            /////////

            EntitySet<ArticuloCompra> retArtCompra = new EntitySet<ArticuloCompra>();
            /*if (HttpContext != null)
            {
                articulosCompra = new Dictionary<int, ArtCant>();
                HttpContext.Session.Add("artsCompra", articulosCompra);
            }*/

            if (carro.ArtCants.Count > 0)
            {
                ArticuloRepository artRepo = new ArticuloRepository();
                Articulo artAux = null;
                ArticuloCompra artCompraAux = null;
                foreach (ArtCant ac in carro.ArtCants)
                {
                    artAux = artRepo.GetArticulo(ac.idArticulo);
                    artCompraAux = new ArticuloCompra(ac.idArticulo, artAux.nombre, artAux.precio, ac.cantidad);
                    retArtCompra.Add(artCompraAux);
                }
            }
            return PartialView("../Carrito/ArtsEnCarrito",retArtCompra);

            /////////
        }

        public void ComprarAhora(int idArt, int cantidad)
        {
            articulosCompra = (IDictionary<int,ArtCant>)HttpContext.Session["artsCompra"];
            string usuario = AccountController.getUsuarioActual();
            Carrito carro = GetCarritoPorUsuario(usuarioRepository.FindUsuario(usuario).id);
            if(articulosCompra.ContainsKey(idArt))
            {
                articulosCompra.Remove(idArt);
            }
            else
            {
                articulosCompra.Add(idArt, new ArtCant(idArt, carro.id, cantidad));
            }
            HttpContext.Session["artsCompra"] = articulosCompra;
        }

        public ActionResult ConfirmarCompra()
        {
            string usuario = AccountController.getUsuarioActual();
            Carrito carro = GetCarritoPorUsuario(usuarioRepository.FindUsuario(usuario).id);
            
            if (HttpContext != null)
            {
                HttpContext.Session.Add("idHistorico", carro.id);
                articulosCompra = (IDictionary<int, ArtCant>) HttpContext.Session["artsCompra"];
            }
            
            carritoRepo.ConfirmarCompra(carro,articulosCompra);
            return RedirectToAction("SeleccionarFormaPago","Carrito");
            
        }

        public ActionResult historicoComprasUsuario()
        {
            string usuario = AccountController.getUsuarioActual();
            List<Carrito> historicos = carritoRepo.GetCarritosHistoricoPoUsuario(usuarioRepository.FindUsuario(usuario).id);
            return View(historicos);
        }

        public ActionResult detallesCarritoHistorico(int idCarrito)
        {
            Carrito carro = carritoRepo.GetCarrito(idCarrito);
            List<ArticuloCompra> retArtCompra = new List<ArticuloCompra>();
            if (carro.ArtCants.Count > 0)
            {
                ArticuloRepository artRepo = new ArticuloRepository();
                Articulo artAux = null;
                ArticuloCompra artCompraAux = null;
                foreach (ArtCant ac in (carro.ArtCants))
                {
                    artAux = artRepo.GetArticulo(ac.idArticulo);
                    artCompraAux = new ArticuloCompra(ac.idArticulo, artAux.nombre, artAux.precio, ac.cantidad);
                    retArtCompra.Add(artCompraAux);
                }
            }
            ViewData["listHistoricos"] = retArtCompra;
            return View(carro);
        }

        private void reversarCarrito(string usuario) {
            //idea, recorrer el carrito historico y volver a poner los Artcants en el carrito actual
            // luego borramos el carrito historico o vemos que hacemos.

            UsuarioRepository ur = new UsuarioRepository();
            CarritoRepository cr = new CarritoRepository();
            Carrito carroActual = GetCarritoPorUsuario(ur.FindUsuario(usuario).id);
            List<Carrito> carrosPendiente = cr.GetCarritosPendientes();
            EntitySet<ArtCant> artCants = new EntitySet<ArtCant>();
            List<Carrito> borrar = new List<Carrito>();
            foreach (Carrito carrito in carrosPendiente) {
                foreach (ArtCant ac in carrito.ArtCants) {
                    ArtCant ac2 = new ArtCant();
                    ac2.idArticulo = ac.idArticulo;
                    ac2.idCarrito = carroActual.id;
                    ac2.cantidad = ac.cantidad;
                    cr.AgregarAlCarrito(ac.idArticulo, ac.cantidad, carroActual.id);
                }
                cr.QuitarTodosArticulosDeCarrito(carrito.id);
                borrar.Add(carrito);
            }
            foreach (var carro in borrar) {
                cr.Delete(carro);
            }
            cr.Save();
        }

    }
}
