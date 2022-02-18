using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Mvc.Ajax;
using ArmazonGr6.Models;

namespace ArmazonGr6.Controllers
{
    public class PlantillaController : Controller
    {
        private UsuarioRepository usuRepo = new UsuarioRepository();

        public ActionResult Index()
        {
            if(usuRepo.esAdmin(AccountController.getUsuarioActual()))
            {
                PlantillaRepository pr = new PlantillaRepository();
                return View(pr.FindAllPlantillas());   
            }else
            {
                return RedirectToAction("ListaArticulo", "Articulo");
            }
            
        }

        //
        // GET: /Plantilla/Details/5

        public ActionResult Details(int id)
        {
            if (usuRepo.esAdmin(AccountController.getUsuarioActual()))
            {
                return View();
            }else
            {
                return RedirectToAction("ListaArticulo", "Articulo");
            }
        }

        //
        // GET: /Plantilla/Create

        public ActionResult Create()
        {
            if (usuRepo.esAdmin(AccountController.getUsuarioActual()))
            {
                return View();
            }
            else
            {
                return RedirectToAction("ListaArticulo", "Articulo");
            }
        } 

        //
        // POST: /Plantilla/Create

        [AcceptVerbs(HttpVerbs.Post)]
        public ActionResult Create(FormCollection collection)
        {
            
            try
            {
                PlantillaRepository pr = new PlantillaRepository();
                Plantilla p = new Plantilla();
                p.nombre = collection["nombre"];
                pr.Add(p);
                pr.Save();
                return RedirectToAction("Index");
            }
            catch
            {
                return View();
            }
        
        }

        //
        // GET: /Plantilla/Edit/5
 
        public ActionResult Edit(int id)
        {
            if (usuRepo.esAdmin(AccountController.getUsuarioActual()))
            {
                PlantillaRepository pr = new PlantillaRepository();
                Plantilla p = pr.GetPlantilla(id);
                return View(p);
            }
            else
            {
                return RedirectToAction("ListaArticulo", "Articulo");
            }
        }

        //
        // POST: /Plantilla/Edit/5

        [AcceptVerbs(HttpVerbs.Post)]
        public ActionResult Edit(int id, FormCollection collection)
        {
            try
            {
                PlantillaRepository pr = new PlantillaRepository();
                Plantilla p = pr.GetPlantilla(id);
                p.nombre = collection["nombre"];
                pr.Update(p);
                return RedirectToAction("Index");
            }
            catch(Exception e)
            {
                return View();
            }
        }

        public ActionResult AddCategoria(int idPlantilla) {
            if(usuRepo.esAdmin(AccountController.getUsuarioActual()))
            {
                CategoriaRepository cr = new CategoriaRepository();
                var cats = cr.FindAllCategorias();
                Dictionary<String, Categoria> nombres = new Dictionary<string, Categoria>();
                foreach (Categoria c in cats)
                {
                    nombres.Add(c.nombre, c);
                }
                SelectList categorias = new SelectList(nombres.Keys);
                ViewData["categorias"] = categorias;
                PlantillaRepository pr = new PlantillaRepository();
                Plantilla p = pr.GetPlantilla(idPlantilla);
                return View(p);   
            }
            else
            {
                return RedirectToAction("ListaArticulo", "Articulo");
            }
            
        }

        [AcceptVerbs(HttpVerbs.Post)]
        public ActionResult AddCategoria(FormCollection collection) {

            CategoriaRepository cr = new CategoriaRepository();
            var cat = cr.GetCategoriaByName(collection["categoria"]);
            cat.idPlantilla = int.Parse(collection["idPlantilla"]);
            cr.Save();
            System.Web.Routing.RouteValueDictionary dic = new System.Web.Routing.RouteValueDictionary();
            dic.Add("id", int.Parse(collection["idPlantilla"]));
            return RedirectToAction("Edit", dic);
        }

        /*===============================================================
         * ------------------- ABM DE CAMPOS ----------------------------
         *=============================================================== 
         */

        public ActionResult CamposSummary(int idPlantilla) {
            if(usuRepo.esAdmin(AccountController.getUsuarioActual()))
            {
                PlantillaRepository pr = new PlantillaRepository();
                ViewData["idPlantilla"] = idPlantilla;
                ViewData["nombrePlant"] = pr.GetPlantilla(idPlantilla).nombre;
                return View(pr.FindAllCampos(idPlantilla));
            }
            else
            {
                return RedirectToAction("ListaArticulo", "Articulo");
            }
        }

         //
        // GET: /Plantilla/Create

        public ActionResult CreateCampo(int idPlantilla) {
            if (usuRepo.esAdmin(AccountController.getUsuarioActual()))
            {
                ViewData["idPlantilla"] = idPlantilla;
                return View();
            }
            else
            {
                return RedirectToAction("ListaArticulo", "Articulo");
            }
        }

        //
        // POST: /Plantilla/Create

        [AcceptVerbs(HttpVerbs.Post)]
        public ActionResult CreateCampo(FormCollection collection) {
            //try {
                PlantillaRepository pr = new PlantillaRepository();
                Campo p = new Campo();
                p.nombre = collection["nombre"];
                p.tipo = collection["tipo"];
                p.idPlantilla = int.Parse(collection["idPlantilla"]);
                pr.Add(p);
                pr.Save();
                System.Web.Routing.RouteValueDictionary dic = new System.Web.Routing.RouteValueDictionary();
                dic.Add("idPlantilla", p.idPlantilla);
                return RedirectToAction("CamposSummary", dic);
          /*  }
            catch(Exception e) {
                return View();
            }*/
        }

        //
        // GET: /Plantilla/Edit/5

        public ActionResult EditCampo(int id) {
            if (usuRepo.esAdmin(AccountController.getUsuarioActual()))
            {
                return View();
            }
            else
            {
                return RedirectToAction("ListaArticulo", "Articulo");
            }
        }

        //
        // POST: /Plantilla/Edit/5

        [AcceptVerbs(HttpVerbs.Post)]
        public ActionResult EditCampo(int id, FormCollection collection) {
            try {
                // TODO: Add update logic here

                return RedirectToAction("Index");
            }
            catch {
                return View();
            }
        }
        public ActionResult DeleteCampo(int id) {
            if (usuRepo.esAdmin(AccountController.getUsuarioActual()))
            {
                PlantillaRepository pr = new PlantillaRepository();
                Campo p = pr.GetCampo(id);
                int idP = p.idPlantilla;
                pr.DeleteCampo(p);
                pr.Save();
                System.Web.Routing.RouteValueDictionary dic = new System.Web.Routing.RouteValueDictionary();
                dic.Add("idPlantilla", idP);
                return RedirectToAction("CamposSummary", dic);
            }
            else
            {
                return RedirectToAction("ListaArticulo", "Articulo");
            }
        }
    }
}
