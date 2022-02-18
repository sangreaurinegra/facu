using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Mvc.Ajax;
using ArmazonGr6.Models;

namespace ArmazonGr6.Controllers
{
    public class ClienteController : Controller
    {
        UsuarioRepository clienteBD=new UsuarioRepository();
        const int cantidadDeArtAProponer = 10;
        //
        // GET: /Cliente/

        public ActionResult Main()
        {
            String url = HttpContext.Request.Url.AbsolutePath;
            String[] spliteado = url.Split('/');
            String invocador = spliteado[spliteado.Count() - 2]; // spliteado te indica el nombreinvocador

            ViewData["nombreInvocador"] = "ListaArticulo" + invocador;
            
            
            
            string usuario = AccountController.getUsuarioActual();
            IEnumerable<Articulo> lista = clienteBD.FindArticulosAProponer(usuario, cantidadDeArtAProponer);

            var list = comboCantidades();

            ViewData["list"] = list;

            return View(lista);
        }

        public ActionResult Detalles(int id)
        {
            return RedirectToAction("../Articulo/Detalles", new { id = id });
        }

        private SelectList comboCantidades()
        {
            var list = new SelectList(new[]
                                          {
                                              new {ID="1",Name="1"},
                                              new{ID="2",Name="2"},
                                              new{ID="3",Name="3"},
                                              new {ID="4",Name="4"},
                                              new{ID="5",Name="5"},
                                              new{ID="6",Name="6"},
                                              new {ID="7",Name="7"},
                                              new{ID="8",Name="8"},
                                              new{ID="9",Name="9"},
                                              new{ID="10",Name="10"},
                                          },
                            "ID", "Name", 1);
            return list;
        }

    }
}
