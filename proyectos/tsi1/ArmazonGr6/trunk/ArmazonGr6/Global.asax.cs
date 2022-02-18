using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Routing;

namespace ArmazonGr6
{
    // Note: For instructions on enabling IIS6 or IIS7 classic mode, 
    // visit http://go.microsoft.com/?LinkId=9394801

    public class MvcApplication : System.Web.HttpApplication
    {
        public static void RegisterRoutes(RouteCollection routes)
        {
            routes.IgnoreRoute("{resource}.axd/{*pathInfo}");


            routes.MapRoute(
            "ListaArticuloArticulo",                               // Route name
            "Articulo/Listado/{etiqueta}",                           // URL with params
            new { controller = "Articulo", action = "ListaArticulo", etiqueta = "Todos", pagina = 0 } // Param defaults
              );

            routes.MapRoute(
            "ListaArticuloEtiqueta",                               // Route name
            "Articulo.mvc/Etiqueta/{etiqueta}",                           // URL with params
            new { controller = "Articulo", action = "ListaArticulo", etiqueta = "Todos", pagina = 0 } // Param defaults
              );


            routes.MapRoute(
                "BuscarFullText", // Route name
                "Articulo.mvc/BuscarFullText/{busqueda}", // URL with params
                new {controller = "Articulo", action = "BuscarFullText", busqueda = "porong", pagina = 0 } // Param defaults
              );
            

            routes.MapRoute(
                "ListaCategorias",
                "Categorias/Pagina/{pagina}",
                new { controller = "Categorias", action = "Index" }
            );

            routes.Add(new Route(  
            "{controller}.mvc/{action}/{id}",  
            new RouteValueDictionary(new { action = "Index", id = (string)null }),  
            new MvcRouteHandler()));  
            
            routes.MapRoute(
                "Default",                                              // Route name
                "{controller}/{action}/{id}",                           // URL with parameters
                new { controller = "Home", action = "Index", id = "" }  // Parameter defaults
            );

        }

        protected void Application_Start()
        {
            RegisterRoutes(RouteTable.Routes);
        }
    }
}