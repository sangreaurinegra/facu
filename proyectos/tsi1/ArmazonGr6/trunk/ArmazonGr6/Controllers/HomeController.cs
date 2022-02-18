using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using ArmazonGr6.Models;

namespace ArmazonGr6.Controllers
{
    [HandleError]
    public class HomeController : Controller
    {
        public ActionResult Index()
        {
            ViewData["Message"] = "Bienvenido a ARMAZON Gr6(tm)";
            CarritoRepository cr = new CarritoRepository();
            ViewData["Destacados"]= cr.reporteVentas(DateTime.Now.AddDays(-7),DateTime.Now).OrderByDescending(c => c.cantidad).Take(3);
            return View();
        }

        public ActionResult About()
        {
            return View();
        }

        public ActionResult menu() {
            return View();
        }

        public ActionResult UsuarioIndex()
        {
            UsuarioRepository ur = new UsuarioRepository();
            String userName = null;
            try {
                userName = AccountController.getUsuarioActual();
            }
            catch (Exception e) { 
                //nada
            }
            int esAdmin = 2;
            if(userName != null)
                esAdmin = ur.iniciarSesion(userName);
            if (esAdmin == 0) {
                return RedirectToAction("Main", "Cliente");
            }
            else if(esAdmin < 2) {

                return RedirectToAction("AdminMain", "Admin");
            }
            return RedirectToAction("Index","Home");
        }
    }
}
