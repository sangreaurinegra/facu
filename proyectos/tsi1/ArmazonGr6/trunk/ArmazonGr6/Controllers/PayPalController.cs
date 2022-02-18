using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Mvc.Ajax;
using ArmazonGr6.Helpers;
using ArmazonGr6.Models;
using System.Data.Linq;

namespace ArmazonGr6.Controllers
{
    /* CUENTAS DE PAYPAL
     * BUSINESS -Cuenta de armazon como vendedor
     * user : maxi_4_1241897001_biz@yahoo.com.ar
     * pwd  : maferoga
     * 
     * CLIENT -Cuenta de test del cliente que compra en armazon
     * user : client_1241897327_per@yahoo.com.ar 
     * pwd  : maferoga
     * 
     * API USER -Se usa como vendedor para iniciar la venta en armazon.
     * username : maxi_4_1241897001_biz_api1.yahoo.com.ar
     * pwd      : 1241897008
     * signature: AFcWxV21C7fd0v3bYYYRCpSSRl31ArvbIHRhL0QMCfIYZGeySDfdrnxp
     */

    public class PayPalController : Controller
    {
        //
        // GET: /PayPal/

        public ActionResult Index()
        {
            // debo crear un form y redireccionar al sandbox
            // luego capturar el token 
            //obtengo el monto del Carrito con la id guradada en la sesion
            int idCarrito = (int)HttpContext.Session["idHistorico"];
            CarritoRepository cr = new CarritoRepository();
            Carrito carro = cr.GetCarrito(idCarrito);
            CommunicationHelper comm = new CommunicationHelper();

            String result = comm.iniciarCheckOut((float)carro.montoTotal);

            if (result != null && !result.StartsWith("Error")) {
                //guardo el token
                String token = obtenerToken(result);
                cr.guardarToken(token, idCarrito);
                return Redirect(result);

            }

            return RedirectToAction("ErrorDePago"); 
        }

        public ActionResult Direccion()
        {// NO BORRAR LA USO PARA QUE ME ENCUENTRE LA DIR DE PAYPAL
                return View(); 
        }
        
        private String obtenerToken(String result) {
            String token = "";
            char[] separador = { '&' };
            String[] resp = result.Split(separador);
            for (int i = 0; i < resp.Length; i++) {
                String aux = resp[i];
                if (aux.StartsWith("token")) {
                    token = aux.Substring(6);
                    break;
                }
            }

            return token;
        }



        public ActionResult CompraExitosa(String token, String PayerID) {
            // vista de compra exitosa, aca retorna cuando paypal cuando es exitosa
            // y confirmo el pago con paypal y reotrno a la pagina principal.


            CommunicationHelper comm = new CommunicationHelper();
            CarritoRepository cr = new CarritoRepository();
            Carrito carro = cr.getCarritoByToken(token);
            bool result = true;
            var listaArtsExt = cr.articulosExternosCarritoSinAmazon(carro);
            if (listaArtsExt!= null && listaArtsExt.Count > 0)
                result = comm.CartBuy("grupo6", listaArtsExt);
            if (result)
                result = comm.confirmarCheckOut(token,PayerID,carro.montoTotal);

            if (result)
                //return RedirectToAction("CompraExitosa");

                return RedirectToAction("IndexCliente","Sucursals");

            return ErrorDePago(); // RedirectToAction("ErrorDePago"); ;
        }

        public ActionResult ErrorDePago() {
            // vista cuando hay error, aca retorna cuando la 
            // aca tambien tengo que revertir la compra
            string user = AccountController.getUsuarioActual();
            reversarCarrito(user);
            return View();
        }

        public ActionResult PagoCancelado() {
            // vista cuando se cancela el pago
            // tengo que revertir la compra del carrito.
            string user = AccountController.getUsuarioActual();
            reversarCarrito(user);
            
            return View();
        }


        private void reversarCarrito(string usuario) {
            //idea, recorrer el carrito historico y volver a poner los Artcants en el carrito actual
            // luego borramos el carrito historico o vemos que hacemos.

            UsuarioRepository ur = new UsuarioRepository();
            CarritoRepository cr = new CarritoRepository();
            Carrito carroActual = GetCarritoPorUsuario(ur.FindUsuario(usuario).id,cr);
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

        public Carrito GetCarritoPorUsuario(int usuId, CarritoRepository carritoRepo) {
            return carritoRepo.FindCarritoPorIdUsuario(usuId);
       } 
    }
}
