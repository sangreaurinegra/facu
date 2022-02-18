using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Mvc.Ajax;
using ArmazonGr6.Models;

namespace ArmazonGr6.Controllers
{
    public class CalificacionController : Controller
    {
        //
        // GET: /Calificacion/

        public ActionResult Index()
        {
            return View();
        }

        
        //
        // GET: /Calificacion/Create

        public ActionResult Create(int idArticulo)
        {
            ViewData["Articulo"] = idArticulo;
            return View();
        } 

        //
        // POST: /Calificacion/Create

        [AcceptVerbs(HttpVerbs.Post)]
        public ActionResult Create(FormCollection collection)
        {
           // try
            //{
                int puntuacion = Calificacion.getNumberRating( collection["puntuacion"]);
                String revision = collection["revision"];
                int idArticulo = int.Parse(collection["idArticulo"]);
                Calificacion caf = new Calificacion();
                caf.idArticulo = idArticulo;
                caf.puntuacion = puntuacion;
                caf.revision = revision;
                String login = AccountController.getUsuarioActual();
                UsuarioRepository ur = new UsuarioRepository();
                Usuario u = ur.FindUsuario(login);
                caf.idCliente = u.id;
                ur.salvarCalificacion(caf);
                System.Web.Routing.RouteValueDictionary dic = new System.Web.Routing.RouteValueDictionary();
                dic.Add("id", idArticulo);
                return RedirectToAction("Detalles","Articulo", dic);
           // }
                //  catch
         //   {
            //    return View();
            //   }
        }

        /*==========================================================================
         * CREACION DE TAGS
         */
        public ActionResult CreateTag(int idArticulo) {
            ViewData["idArticulo"] = idArticulo;
            UsuarioRepository ur  = new UsuarioRepository();
            ViewData["tags"] = ur.obtenerTags(idArticulo);
            return View();
        }

        public ActionResult AumentarTag(String name, int idArticulo) {
            
            UsuarioRepository ur = new UsuarioRepository();
            CloudItem tag = ur.obtenerTag(name);
            tag.weight++;
            ur.Save();
            System.Web.Routing.RouteValueDictionary dic = new System.Web.Routing.RouteValueDictionary();
            dic.Add("id", idArticulo);
            return RedirectToAction("Detalles", "Articulo", dic);
        }

        //
        // POST: /Calificacion/Create

        [AcceptVerbs(HttpVerbs.Post)]
        public ActionResult CreateTag(FormCollection collection) {

            String nombre = collection["name"];
            int idArticulo = int.Parse(collection["idArticulo"]);
            UsuarioRepository ur = new UsuarioRepository();
            CloudItem item = ur.obtenerTag(nombre);
            if (item == null) {
                item = new CloudItem();
                item.name = nombre;
                item.weight = 0;
                item.url = "";
                String login = AccountController.getUsuarioActual();
                Usuario u = ur.FindUsuario(login);
                item.idUsuario = u.id;
                item.id = ur.salvarTag(item);
                ur.salvarTagArticulo(item.id, idArticulo);               
            }
            else {
                item.weight++;
                ur.Save();
                //salvo el aumento del tag, pero luego debo ver si esta siendo asociado a un articulo nuevo
                // o se esta solo aumentado nuevamente el tamaño
                bool esta = ur.estaArticuloTageado(idArticulo, item.id);
                if (!esta)
                    ur.salvarTagArticulo(item.id, idArticulo);
            }

            System.Web.Routing.RouteValueDictionary dic = new System.Web.Routing.RouteValueDictionary();
            dic.Add("id", idArticulo);
            return RedirectToAction("Detalles", "Articulo", dic);
            
        }

        public ActionResult AllTags()
        {
            UsuarioRepository ur = new UsuarioRepository();
            return View(ur.GetAllTags());
        }

        public ActionResult DeleteTag(int id)
        {
            UsuarioRepository ur = new UsuarioRepository();
            ur.DeleteTag(id);
            
            return PartialView("ListaTags",ur.GetAllTags());
        }
    }
}
