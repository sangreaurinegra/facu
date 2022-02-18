using System;
using System.Collections;
using System.Linq;
using System.Web.Mvc;
using ArmazonGr6.Models;
using System.Globalization;
using ArmazonGr6.Helpers;

namespace ArmazonGr6.Controllers
{
    public class SucursalsController : Controller
    {
        private SucursalesRepository sucursalesRepo = new SucursalesRepository();
        
        //
        // GET: /Sucursals/

        public ActionResult Index()
        {
            SystemProperties sp;
            if (SystemProperties.isSystemPropertiesLoaded())
                sp = SystemProperties.getSystemProperties();
            else
                sp = SystemProperties.loadProperties("C:/Projects/ArmazonGr6/ArmazonGr6/config/config.txt");
            ViewData["GoogleKey"] = sp.getProperty("GoogleKey");
            var sucus = sucursalesRepo.FindAllSucursals().ToList();
            return View("Index",sucus);
        }

        public ActionResult IndexCliente() {
            SystemProperties sp;
            if (SystemProperties.isSystemPropertiesLoaded())
                sp = SystemProperties.getSystemProperties();
            else
                sp = SystemProperties.loadProperties("C:/Projects/ArmazonGr6/ArmazonGr6/config/config.txt");
            ViewData["GoogleKey"] = sp.getProperty("GoogleKey");
            var sucus = sucursalesRepo.FindAllSucursals().ToList();
            return View("IndexCliente", sucus);
        }

        //
        // GET: /Sucursals/Details/5

        public ActionResult Details(int id)
        {
            var suc = sucursalesRepo.GetSucursal(id);
            return View("Details",suc);
        }

        //
        // GET: /Sucursals/Create

        public ActionResult Create()
        {
            return View();
        } 

        //
        // POST: /Sucursals/Create

        [AcceptVerbs(HttpVerbs.Post)]
        public ActionResult Create(Sucursal sucur)
        {
            try
            {
                sucursalesRepo.Add(sucur);
                sucursalesRepo.Save();
                return RedirectToAction("Index");
            }
            catch
            {
                return View();
            }
        }

        //
        // GET: /Sucursals/Edit/5
 
        public ActionResult Edit(int id)
        {
            var suc = sucursalesRepo.GetSucursal(id);
            return View("Edit",suc);
        }

        //
        // POST: /Sucursals/Edit/5

        [AcceptVerbs(HttpVerbs.Post)]
        public ActionResult Edit(int id, Sucursal sucu)
        {
            try
            {
                sucursalesRepo.GetSucursal(id);
                sucu.id = id;
                sucursalesRepo.Update(sucu);
                sucursalesRepo.Save();
                return RedirectToAction("Index");
            }
            catch
            {
                return View(Index());
            }
        }

        public ActionResult Delete(int id)
        {
            var suc = sucursalesRepo.GetSucursal(id);
            sucursalesRepo.Delete(suc);
            sucursalesRepo.Save();
            return Index();
        }

        
        public ActionResult GetSucursalJSON(string idsu)
        {
            var sucu = sucursalesRepo.GetSucursal(Convert.ToInt32(idsu));
            string auxSucu = sucu.id + "|" + sucu.nombre + "|" + sucu.direccion
                        + "|" + sucu.telefono + "|" + sucu.ciudad + "|" + sucu.latitud.ToString(CultureInfo.InvariantCulture) + 
                        "|" + sucu.longitud.ToString(CultureInfo.InvariantCulture);
            return Json(auxSucu);
        }

        
        public ActionResult GetElements()
        {
            Sucursal sucu;
            string auxSucu = "";
            var sucusResult = new ArrayList();
            var sucus = sucursalesRepo.FindAllSucursals().ToList();
            for (var i = 0; i < sucus.Count; i++)
            {
                sucu = sucus.ElementAt(i);
                auxSucu = sucu.id + "|" + sucu.nombre + "|" + sucu.direccion
                        + "|" + sucu.telefono + "|" + sucu.ciudad + "|" + sucu.latitud.ToString(CultureInfo.InvariantCulture) + "|" + sucu.longitud.ToString(CultureInfo.InvariantCulture);
                sucusResult.Add(auxSucu);
            }

            IEnumerable result = sucusResult.Cast<string>().Select(sucur => sucur);


            return Json(result);
        }

        public ActionResult FinCompraExitosa()
        {
            return RedirectToAction("FinCompraExitosa", "Carrito");
        }
    }
}
