using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Mvc.Ajax;
using ArmazonGr6.Models;
using ArmazonGr6.Helpers;

namespace ArmazonGr6.Controllers
{
    public class AdminController : Controller
    {
        /////////////////////////////////////////////////////////////7
        /////////////////////// PRUEBAS ////////////////////////////7
        public ActionResult Pruebas(int id) {
            CommunicationHelper ch = new CommunicationHelper();
            IEnumerable<Calificacion> califs = ch.getRatings(id);
            return View(califs);
        }

        public ActionResult PruebaArt() {
            CommunicationHelper ch = new CommunicationHelper();
            Articulo art = ch.GetProduct(3);
            return View(art);
        }

        public ActionResult PruebaSearch() {
            CommunicationHelper ch = new CommunicationHelper();
            IEnumerable<Articulo> art = ch.search("");
            return View(art);
        }
        ///////////////////////////////////////////////////////////
        
        //
        // GET: /Admin/

        public ActionResult AdminMain()
        {
            return View();
        }

        public ActionResult ReporteVentas() {
            return View();
        }

        public ActionResult ReporteVentasResult(String fechaDesde, String fechaHasta) {
            CarritoRepository cr = new CarritoRepository();
            DateTime fd = DateTime.Parse(fechaDesde);
            DateTime fh = DateTime.Parse(fechaHasta);
            return View(cr.reporteVentas(fd,fh));
        }

        public ActionResult ReporteArticulosPorCategoria() {
            return View();
        }

        public ActionResult ReporteArticulosPorCategoriaResult() {
            CarritoRepository cr = new CarritoRepository();
            ViewData["result"] = cr.reporteArticulosMasVendidosCategoria(-1);
            return View();
        }

        public ActionResult ReporteArticulosMejorCalif() {
            return View();
        }

        public ActionResult ReporteArticulosMejorCalifResult() {
            CarritoRepository cr = new CarritoRepository();
            return View(cr.reporteArtsMejorCalifcadosCategoria(-1));
        }


        public ActionResult MantProveedores() {
            ProveedorRepository pr = new ProveedorRepository();
            return View(pr.FindAllProveedors());
        }

        public ActionResult Create() {
            
            return View();
        }

        //
        // POST: /Calificacion/Create

        [AcceptVerbs(HttpVerbs.Post)]
        public ActionResult Create(FormCollection collection) {

            Proveedor p = new Proveedor();
            p.nombre = collection["nombre"];
            p.url = collection["url"];
            ProveedorRepository pr = new ProveedorRepository();
            pr.Add(p);
            pr.Save();
            return RedirectToAction("MantProveedores", "Admin");
            
        }

        public ActionResult Edit(int id) {
            ProveedorRepository pr = new ProveedorRepository();
            var prov = pr.GetProveedor(id);
            return View("Edit", prov);
        }

        //
        // POST: /Sucursals/Edit/5

        [AcceptVerbs(HttpVerbs.Post)]
        public ActionResult Edit(int id, Proveedor prov) {
            
                ProveedorRepository pr = new ProveedorRepository();
                var prov2 = pr.GetProveedor(id);
                pr.Update(prov);
                pr.Save();
                return RedirectToAction("MantProveedores", "Admin");
            
        }

        public ActionResult Delete(int id) {
            ProveedorRepository pr = new ProveedorRepository();
            var prov2 = pr.GetProveedor(id);
            pr.Delete(prov2);
            pr.Save();
            return RedirectToAction("MantProveedores", "Admin");
        }

    }
}
