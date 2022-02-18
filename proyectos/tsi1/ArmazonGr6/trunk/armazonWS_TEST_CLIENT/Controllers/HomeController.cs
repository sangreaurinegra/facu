using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using armazonWS_TEST_CLIENT.Armazon_Client;

namespace armazonWS_TEST_CLIENT.Controllers {
    [HandleError]
    public class HomeController : Controller {
       /* public ActionResult Index() {
            ViewData["Message"] = "INICIO";

            return View();
        }
        */
        public ActionResult About() {
            return View();
        }





        /********************************************************************************/
        /********************************************************************************/
        /********************************************************************************/
        /********************************************************************************/

        public ActionResult getProduct() {

            String msg = "ERROR OBTENIENDO UN PRODUCTO:";
            ArmazonWSClient proxy = new ArmazonWSClient();
            try {
                var resp = proxy.getProduct(5);
                msg = "EXITO: idProd: " + resp.ProductId;
            }
            catch (Exception e) {
                msg += e.Message;
            }
            System.Web.Routing.RouteValueDictionary dic = new System.Web.Routing.RouteValueDictionary();
            dic.Add("msg", msg);
            return RedirectToAction("Index", dic);
        }

        public ActionResult search() {

            String msg = "ERROR FULL TEXT SEARCH:";
            ArmazonWSClient proxy = new ArmazonWSClient();
            try {
                var resp = proxy.search("Revistas");
                msg = "EXITO: ";
                foreach (DCProduct r in resp.ToList()) {
                    msg += "idProd: " + r.ProductId + "\n";
                }
            }
            catch (Exception e) {
                msg += e.Message;
            }



            System.Web.Routing.RouteValueDictionary dic = new System.Web.Routing.RouteValueDictionary();
            dic.Add("msg", msg);
            return RedirectToAction("Index", dic);
        }


        public ActionResult getRatings() {
            String msg = "ERROR OBTENIENDO RATINGS:";
            ArmazonWSClient proxy = new ArmazonWSClient();
            try {
                var resp = proxy.getRatings(5);
                msg = "EXITO: ";
                foreach( DCRating r in resp.ToList() ){
                    msg += "puntos: " + r.Rating + "\n";
                }
            }
            catch (Exception e) {
                msg += e.Message;
            }

            System.Web.Routing.RouteValueDictionary dic = new System.Web.Routing.RouteValueDictionary();
            dic.Add("msg", msg);
            return RedirectToAction("Index", dic);
        }


        public ActionResult CartBuy() {
            String msg = "ERROR COMPRANDO EL CARRO:";
            ArmazonWSClient proxy = new ArmazonWSClient();
            try {
                DCCartItem[] list = new DCCartItem[1];
                DCCartItem item = new DCCartItem();
                item.ProductId = 5;
                item.Quantity = 6;
                list[0] = item;
                var resp = proxy.CartBuy("ArmazonTst", list) ;
                if (resp) 
                    msg = "EXITO: ";

            }
            catch (Exception e) {
                msg += e.Message;
            }



            System.Web.Routing.RouteValueDictionary dic = new System.Web.Routing.RouteValueDictionary();
            dic.Add("msg", msg);
            return RedirectToAction("Index", dic);
            
        }

        public ActionResult Index(String msg) {
            if (msg != null)
                ViewData["Message"] = msg;
            else
                ViewData["Message"] = "INICIO";

            return View();
        }

    }
}
