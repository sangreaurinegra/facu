using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Mvc.Ajax;
using ArmazonGr6.Helpers;
using ArmazonGr6.Models;

namespace ArmazonGr6.Controllers
{
    public class CategoriasController : Controller
    {
        CategoriaRepository categoriaRepository = new CategoriaRepository();
        UsuarioRepository usuRepo = new UsuarioRepository();
        const int pageSize = 10;
        const int nroArtAProponer = 10;

        //
        // GET: /Categorias/
        public ActionResult Index(int? idCatPadre)
        {

            var list = comboCantidades();

            ViewData["list"] = list;
            bool esAdmin = usuRepo.esAdmin(AccountController.getUsuarioActual());
            ViewData["esAdmin"] = esAdmin;
            if (idCatPadre == null)
            {

                var todasLasCatRaices = categoriaRepository.FindAllRaices();
                var todosLosArticulos = categoriaRepository.FindAllArticulos();

                var listaArtAProponer = categoriaRepository.OrdenarPorPromedioCalificacion(todosLosArticulos.ToList());

                var paginatedArticulos = new PaginatedList<Articulo>(
                                            todosLosArticulos,
                                            0,
                                            pageSize);



                return View( new CategoriaIndexViewModel(todasLasCatRaices,
                                                        paginatedArticulos,
                                                        listaArtAProponer.Take(nroArtAProponer),
                                                        new List<Categoria>(),
                                                        0,
                                                        null));
            }
            else
            {

                var listaCatAux = categoriaRepository.GetCategoria((int)idCatPadre).getTodasLasSubCategorias();
                var listaArticulosAMostrar = new List<Articulo>();
                Dictionary<int, float> diccionarioArtAMostrarConPromedio = new Dictionary<int, float>();
                foreach (Categoria cat in listaCatAux)
                    foreach (Articulo art in cat.Articulos)
                    {
                        listaArticulosAMostrar.Add(art);
                        
                    }

                var listaArtAProponer = categoriaRepository.OrdenarPorPromedioCalificacion(listaArticulosAMostrar);

                var paginatedArticulos = new PaginatedList<Articulo>(listaArticulosAMostrar,
                                                0,
                                                pageSize);

                var listaCatAMostrar = categoriaRepository.GetCategoria((int)idCatPadre).getHijos();

                var rutaCategorias = new List<Categoria>();
                int? idCat = idCatPadre;
                while (idCat != null)
                {
                    Categoria cat=categoriaRepository.GetCategoria((int)idCat);
                    rutaCategorias.Add(cat);
                    idCat = cat.idSuperCategoria;
                }
                rutaCategorias.Reverse();

                return View(new CategoriaIndexViewModel(listaCatAMostrar,
                                                        paginatedArticulos,
                                                        listaArtAProponer.Take(nroArtAProponer),
                                                        rutaCategorias,
                                                        0,
                                                        idCatPadre));

            }

        }


         
        //
        // GET: /Categorias/Detalles/id
        public ActionResult Detalles(int id)
        {
            //Response.Write("<h1> Detalles de la categoría: "+id+" </h1>");
            Categoria c = categoriaRepository.GetCategoria(id);
            if (c == null)
            {
                return View("NoEncontrada");
            }
            else
            {
                
                //cuando el nombre del método coincide con el de la pág template, no es
                //necesario indicar el nombre de esta en el método View.
                return View(c);
            }
        }
        //
        // GET: /Categorias/Hijos
        public ActionResult Hijos(int id)
        {
            //Response.Write("<h1> Detalles de la categoría: "+id+" </h1>");
            Categoria c = categoriaRepository.GetCategoria(id);
            if (c == null)
            {
                return View("NoEncontrada");
            }
            else
            {
                var listaCategoriasHijos = c.getHijos().ToList();
                return View(listaCategoriasHijos);
            }
        }
        
        //
        // GET: /Categorias/PlanillaArt
        /*public ActionResult PlanillaArt(int id)
        {
            //Response.Write("<h1> Detalles de la categoría: "+id+" </h1>");
            Categoria c = categoriaRepository.GetCategoria(id);
            if (c == null)
            {
                return View("NoEncontrada");
            }
            else
            {
                var listaArticulos = c.Articulos.ToList();
                return View(listaArticulos);
            }
        }*/
        //
        // GET: /Categorias/Editar/id
        [Authorize]
        public ActionResult Editar(int id)
        {
            if(usuRepo.esAdmin(AccountController.getUsuarioActual()))
            {
                Categoria c = categoriaRepository.GetCategoria(id);

                //ViewData["idSuperCategoria"] = new SelectList(categoriaRepository.FindAllCategorias(),
                //    c.idSuperCategoria);

                //return View(c);
                return View(new CategoriaFormViewModel(c));
            }else
            {
                return RedirectToAction("Index");
            }
            
        }
        [AcceptVerbs(HttpVerbs.Post)]
        public ActionResult Editar(int id, FormCollection valoresDelForm)
        {
            Categoria c = categoriaRepository.GetCategoria(id);
            try
            {

                UpdateModel(c);
                categoriaRepository.Save();
                return RedirectToAction("Detalles", new { id = c.id });
            }
            catch
            {
                /*foreach (var error in c.GetRuleViolations())
                {
                    ModelState.AddModelError(error.PropertyName, error.ErrorMessage);
                }*/
                ModelState.AddReglasVioladas(c.GetRuleViolations());
                //return View(c);
                return View(new CategoriaFormViewModel(c));
            }
        }
        //
        // GET: Categorias/Crear/
        public ActionResult Crear()
        {
            if (usuRepo.esAdmin(AccountController.getUsuarioActual()))
            {
                Categoria c = new Categoria();
                //return View(c);
                return View(new CategoriaFormViewModel(c));
            }else
            {
                return RedirectToAction("Index");
            }
        }

        [AcceptVerbs(HttpVerbs.Post)]
        public ActionResult Crear(String nombre, String SuperCategoria) //[Bind(Include = "nombre,SuperCategoria")] Categoria c
        {
            Categoria c = new Categoria();
            if (ModelState.IsValid)
            {
                try
                {
                    
                    int? idsc = null;
                    c.nombre = nombre;
                    if (!SuperCategoria.Trim().Equals(""))
                        idsc = categoriaRepository.GetCategoriaByName(SuperCategoria).id;
                    c.idSuperCategoria = idsc;

                    categoriaRepository.Add(c);
                    categoriaRepository.Save();
                    return RedirectToAction("Mantenimiento");
                }
                catch
                {
                    ModelState.AddReglasVioladas(c.GetRuleViolations());

                }
            }
            return View(new CategoriaFormViewModel(c));
        }
        //
        // GET: Categorias/Borrar/id
        public ActionResult Borrar(int id)
        {
            if (usuRepo.esAdmin(AccountController.getUsuarioActual()))
            {
                Categoria c = categoriaRepository.GetCategoria(id);

                if (c == null)
                    return View("NoEncontrada");
                else
                    return View(c);
            }else
            {
                return RedirectToAction("Index");
            }
        }
        [AcceptVerbs(HttpVerbs.Post)]
        public ActionResult Borrar(int id, string confirmButton)
        {
            Categoria c = categoriaRepository.GetCategoria(id);
            if (c == null)
                return View("NoEncontrada");

            if(categoriaRepository.Delete(c))
            {
                return View("Borrada"); 
            }else{
                return View("NoSePuedeBorrarCategoria"); 
            }
            

        }

        public ActionResult Mantenimiento() {
            if (usuRepo.esAdmin(AccountController.getUsuarioActual()))
            {
                var cats = categoriaRepository.FindAllCategorias();
                return View(cats);
            }else
            {
                return RedirectToAction("Index");
            }
        }
        /*
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
        */

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
