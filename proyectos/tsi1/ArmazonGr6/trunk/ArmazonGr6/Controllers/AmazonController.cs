using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Mvc.Ajax;
using ArmazonGr6.Helpers;
using ArmazonGr6.Models;
using CommunicationServer;

namespace ArmazonGr6.Controllers
{
    public class AmazonController : Controller
    {
        //
        // GET: /Amazon/

        public ActionResult Index()
        {
            return View(new AmazonFormViewModel("No hay datos"));
        }

        //
        // POST: /Amazon/
        [AcceptVerbs(HttpVerbs.Post)]
        public ActionResult Index(FormCollection fm)
        {
            try
            {
                CommunicationHelper c = new CommunicationHelper();
                List<Articulo> lista = c.buscarEnAmazon(Request.Form["textoAObtener"],1);
                return View(new AmazonFormViewModel(lista));
            }
            catch (Exception e)
            {
                return View(new AmazonFormViewModel( e.Message));
            }
        }

        [AcceptVerbs(HttpVerbs.Post)]
        public ActionResult PaginaAnterior()
        {
            return Content("No hay pagina anterior");
        }
        [AcceptVerbs(HttpVerbs.Post)]
        public ActionResult PaginaSiguiente()
        {
            return Content("No hay pagina siguiente");
        }

    }
}
