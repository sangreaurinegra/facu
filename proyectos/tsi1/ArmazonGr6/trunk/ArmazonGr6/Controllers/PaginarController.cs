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
    public class PaginarController : Controller
    {
        
        const int pageSize = 10;
        private CategoriaRepository cr = new CategoriaRepository();
        UsuarioRepository usuRepo = new UsuarioRepository();
        /*
        [AcceptVerbs(HttpVerbs.Post)]
        public ActionResult MostrarPagina(int id)
        {

            var todosLosArticulos = cr.FindAllArticulos();
            var paginatedArticulos = new PaginatedList<Articulo>(todosLosArticulos,
                                            id,
                                            pageSize);
            return PartialView("../Categorias/ArticulosPaginados", new CategoriaIndexViewModel(paginatedArticulos, id));
        }
        */
        [AcceptVerbs(HttpVerbs.Post)]
        public ActionResult MostrarPagina(int? idCatPadre,int  pagActual)
        {

            var list = comboCantidades();

            ViewData["list"] = list;
            bool esAdmin = usuRepo.esAdmin(AccountController.getUsuarioActual());
            ViewData["esAdmin"] = esAdmin;
            if (idCatPadre == null)
            {
               
                //var todasLasCatRaices= cr.FindAllRaices();
                var todosLosArticulos = cr.FindAllArticulos();
                var paginatedArticulos = new PaginatedList<Articulo>(
                                            todosLosArticulos,
                                            pagActual,
                                            pageSize);
                
                return PartialView("../Categorias/ArticulosPaginados",
                                    new CategoriaIndexViewModel(//todasLasCatRaices,
                                                                null,
                                                                paginatedArticulos,
                                                                null,
                                                                null,
                                                                pagActual,
                                                                idCatPadre));
            }else {

                var listaCatAMostrar = cr.GetCategoria((int) idCatPadre).getTodasLasSubCategorias();
                var listaArticulosAMostrar = new List<Articulo>();
                foreach (Categoria cat in listaCatAMostrar)
                    foreach(Articulo art in cat.Articulos){
                        listaArticulosAMostrar.Add(art);
                    }
                var paginatedArticulos = new PaginatedList<Articulo>(listaArticulosAMostrar,
                                                pagActual,
                                                pageSize);
                return PartialView("../Categorias/ArticulosPaginados", 
                                    new CategoriaIndexViewModel(//listaCatAMostrar,
                                                                null,
                                                                paginatedArticulos,
                                                                null,
                                                                null,
                                                                pagActual,
                                                                idCatPadre));
                
            }
             
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
