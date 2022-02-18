using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Mvc.Ajax;
using System.Web.UI.MobileControls;
using ArmazonGr6.Helpers;
using ArmazonGr6.Models;
using System.IO;
using CommunicationServer;

namespace ArmazonGr6.Controllers
{
    public class ArticuloController : Controller
    {

        const int sizePagFT = 10;
        const int sizePagA = 5;
        const int sizePAgO = 5;
        private UsuarioRepository usuRepo = new UsuarioRepository();

        //
        // AJAX: /Categorias/Asociar
        [Authorize, AcceptVerbs(HttpVerbs.Post)]
        public ActionResult Asociar(int id)
        {
            return Content("AJAX probado con éxito");
        }

        //mantenimiento de articulos

        ArticuloRepository repo = new ArticuloRepository();

        [Authorize]
        public ActionResult Editar(int id) {
            if (usuRepo.esAdmin(AccountController.getUsuarioActual()))
            {
                Articulo c = repo.GetArticulo(id);
                return View(c);
            }else
            {
                return RedirectToAction("ListaArticulo",null,0);
            }

        }
        [AcceptVerbs(HttpVerbs.Post)]
        public ActionResult Editar(int id, FormCollection valoresDelForm)
        {
            if (usuRepo.esAdmin(AccountController.getUsuarioActual()))
            {
                Articulo c = repo.GetArticulo(id);
                try
                {
                    c.nombre = valoresDelForm["nombre"];
                    c.precio = double.Parse(valoresDelForm["precio"]);
                    CategoriaRepository cr = new CategoriaRepository();
                    if (!valoresDelForm["Categoria"].Trim().Equals(""))
                        c.idCategoria = cr.GetCategoriaByName(valoresDelForm["Categoria"]).id;
                    repo.Save();

                    System.Web.Routing.RouteValueDictionary dic = new System.Web.Routing.RouteValueDictionary();
                    dic.Add("id", c.id);
                    return RedirectToAction("EditDetallesArticulo", dic);
                }
                catch
                {
                    return RedirectToAction("ListaArticulo", null, 0);
                }
            }
            else
            {
                return RedirectToAction("ListaArticulo", null, 0);
            }
            return null;
        }

        //
        // GET: Categorias/Crear/
        public ActionResult Crear() {
            if (usuRepo.esAdmin(AccountController.getUsuarioActual()))
            {
                Articulo c = new Articulo();

                return View(c);
            }else
            {
                return RedirectToAction("ListaArticulo", null, 0);
            }
        }

        [AcceptVerbs(HttpVerbs.Post)]
        public ActionResult Crear(FormCollection valoresDelForm) {
            Articulo c = new Articulo();
            if (ModelState.IsValid) {
                try {
                    c.nombre = valoresDelForm["nombre"];
                    c.precio = double.Parse(valoresDelForm["precio"]);
                    CategoriaRepository cr = new CategoriaRepository();
                    String cat = valoresDelForm["Categoria"];
                    if (!cat.Trim().Equals(""))
                        c.idCategoria = cr.GetCategoriaByName(cat).id;
                    repo.Add(c);
                    repo.Save();
                    System.Web.Routing.RouteValueDictionary dic = new System.Web.Routing.RouteValueDictionary();
                    dic.Add("id", c.id);
                    return RedirectToAction("EditDetallesArticulo", dic);
                }
                catch {
                    //ModelState.AddReglasVioladas(c.GetRuleViolations());

                }
            }
            return View(c);
        }
        //
        // GET: Categorias/Borrar/id
        /*public ActionResult Borrar(int id) {
            Articulo c = repo.GetArticulo(id);

            if (c == null)
                return View("NoEncontrada");
            else
                return View(c);
        }*/

        /*[AcceptVerbs(HttpVerbs.Post)]*/
        public ActionResult Borrar(int id) {
            if (usuRepo.esAdmin(AccountController.getUsuarioActual()))
            {

                Articulo c = repo.GetArticulo(id);
                if (c == null)
                    return View("NoEncontrada");

                repo.Delete(c);
                repo.Save();
            }
            return RedirectToAction("ListaArticulo", null, 0);

        }

        public ActionResult SubirImagen(int id) {
            if (usuRepo.esAdmin(AccountController.getUsuarioActual()))
            {
                Articulo c = repo.GetArticulo(id);
                return View(c);
            }else
            {
                return RedirectToAction("ListaArticulo", null, 0);
            }
        }

        [AcceptVerbs(HttpVerbs.Post)]
        public ActionResult SubirImagen(FormCollection valoresDelForm) {
            int idArt = int.Parse((String)valoresDelForm["idArticulo"]);
            Articulo c = repo.GetArticulo(idArt);
            //salvamos la imagen y luego la url en el articulo.

            SystemProperties sp = null;

            if (SystemProperties.isSystemPropertiesLoaded())
                sp = SystemProperties.getSystemProperties();
            else
                sp = SystemProperties.loadProperties("C:/Projects/ArmazonGr6/ArmazonGr6/config/config.txt");

            String PATHIMAGEN = sp.getProperty("PathImage");

            foreach (string inputTagName in Request.Files) {
                HttpPostedFileBase file = Request.Files[inputTagName];
                if (file.ContentLength > 0) {
                    string filePath = Path.Combine(@PATHIMAGEN, c.id + "_" + Path.GetFileName(file.FileName));
                    file.SaveAs(filePath);
                }
                c.imagen = c.id + "_" + file.FileName;
            }

            repo.Save();
            return RedirectToAction("Mantenimiento");

        }

        public ActionResult Mantenimiento() {
            if (usuRepo.esAdmin(AccountController.getUsuarioActual()))
            {
                var cats = repo.FindAllArticulos();
                return View(cats);
            }else
            {
                return RedirectToAction("ListaArticulo", null, 0);
            }
        }

        public ActionResult Detalles(int id ,int? page) {
            //Response.Write("<h1> Detalles de la categoría: "+id+" </h1>");
            Articulo c = repo.GetArticulo(id);
            if (page != null) ViewData["paginaCall"] = page;
            ViewData["paginaCall"] = 0;
            var list = comboCantidades();
            bool esAdmin = usuRepo.esAdmin(AccountController.getUsuarioActual());
            ViewData["esAdmin"] = esAdmin;
            ViewData["list"] = list;
            // obtengo id proveedor de amazon
            SystemProperties sp = null;
                if (SystemProperties.isSystemPropertiesLoaded())
                    sp = SystemProperties.getSystemProperties();
                else
                    sp = SystemProperties.loadProperties("C:/Projects/ArmazonGr6/ArmazonGr6/config/config.txt");
                int idAmazon = int.Parse(sp.getProperty("IdProveedorAmazon"));
            if (c.Externo != null && c.Externo.idProveedor != idAmazon) { // debo mostrar las calificaciones
                CommunicationHelper ch = new CommunicationHelper();
                try {

                    c.Calificacions.AddRange(ch.getRatings(int.Parse(c.Externo.claveExterna)));
                }
                catch { 
                    // no hago nada, si no puedo obtener las revisiones
                }
            }
            if (c == null) {
                ViewData["MensajeError"] = "El articulo especificado no existe en nuestra base de articulos";
                return View("Error");
            }
            else {

                //cuando el nombre del método coincide con el de la pág template, no es
                //necesario indicar el nombre de esta en el método View.
                return View(c);
            }
        }

        public ActionResult EditDetallesArticulo(int id) {
            if (usuRepo.esAdmin(AccountController.getUsuarioActual()))
            {
                //carga todos los campos de las plantillas asociadas a las categorias de un articulo.
                Articulo c = repo.GetArticulo(id);

                if (c == null)
                {
                    return View("NoEncontrada");
                }
                else
                {
                    ViewData["idArticulo"] = c.id;
                    PlantillaRepository pr = new PlantillaRepository();
                    if (c.idCategoria != null)
                        return View(pr.FindTodosCamposArticulo((int) c.idCategoria, true));
                    else
                        return View(pr.FindAllCampos(PlantillaRepository.PLANTILLA_POR_DEFECTO));
                }
            }else
            {
                return RedirectToAction("ListaArticulo", null, 0);
            }
        }

        [AcceptVerbs(HttpVerbs.Post)]
        public ActionResult EditDetallesArticulo(FormCollection valoresDelForm) {
            /*IDEA como obtener los valores:
             * busco las keys que empiezan con "ID", con el valor, obtengo el 
             * valor de las keys que se llaman "VALUE+valorID", asi obtengo los datos de los texboxs
             */
            int idArt = int.Parse((String)valoresDelForm["idArticulo"]);
            Articulo c = repo.GetArticulo(idArt);
            PlantillaRepository pr = new PlantillaRepository();
            bool nuevo = false;
            foreach (String s in valoresDelForm.Keys) {
                if (s.StartsWith("ID")) {
                    int idCampo = int.Parse((String)valoresDelForm[s]);
                    String valor = (String)valoresDelForm["VALUE" + idCampo];
                    String tipo = (String)valoresDelForm["TYPE" + idCampo];
                    Registro reg = pr.GetRegistro(idArt,idCampo);
                    if (reg == null) {
                        reg = new Registro();
                        reg.idArticulo = idArt;
                        reg.idCampo = idCampo;
                        nuevo = true;
                    }
                    reg.valor = valor;
                    if (nuevo) {
                        pr.Add(reg);
                        pr.Save();
                    }
                    else
                        pr.Update(reg);
                    nuevo = false;
                }
            }
            return RedirectToAction("Mantenimiento");
        }

        //[AcceptVerbs(HttpVerbs.Post)]
        public ActionResult BuscarFullText(string texto)
        {
            
            ViewData["list"] = comboCantidades();
            ViewData["busqueda"] = texto;
            bool esAdmin = usuRepo.esAdmin(AccountController.getUsuarioActual());
            ViewData["esAdmin"] = esAdmin;
            bool hayErrorOAr = false;
            bool hayErrorA = false;
            int tipoError;
            try
            {
                List<Articulo> listaArmazon = this.BusquedaFullTextArmazon(texto);


                CommunicationHelper c = new CommunicationHelper();

                //lista otro Armazon
                List<Articulo> listaOtroAr = new List<Articulo>();
                try
                {
                    listaOtroAr = c.search(texto).ToList<Articulo>(); //new List<Articulo>();
                }
                catch (Exception)
                {
                    //return View(new ArticuloFormViewModel("Estamos teniendo problemas en la conexión con otro ARMAZON",3));
                    hayErrorOAr = true;
                    listaOtroAr = new List<Articulo>();
                }

                //lista AMAZON                
                List<Articulo> lista = new List<Articulo>();                
                try
                {
                    lista = c.buscarEnAmazon(texto, 1);

                }
                catch (Exception)
                {
                    //return View(new ArticuloFormViewModel("Estamos teniendo problemas en la conexión con AMAZON",2));
                    hayErrorA = true;
                    lista = new List<Articulo>();
                }

               
                return View(new ArticuloFormViewModel(listaArmazon,0,sizePagFT,
                                                      lista, 0, sizePagA,
                                                      listaOtroAr,0,sizePAgO,
                                                      hayErrorA,
                                                      hayErrorOAr,
                                                      texto));
            }
            catch (Exception e)
            {
                return View(new ArticuloFormViewModel(e.Message,5));
            };

            
        }

        [AcceptVerbs(HttpVerbs.Post)]
        public ActionResult MostrarPaginaArmazon(string texto, int pagActual)
        {
            try
            {
                ViewData["list"] = comboCantidades();
                ViewData["busqueda"] = texto;
                bool esAdmin = usuRepo.esAdmin(AccountController.getUsuarioActual());
                ViewData["esAdmin"] = esAdmin;
                List<Articulo> listaArmazon = BusquedaFullTextArmazon(texto);

                return PartialView("../Articulo/PaginadoFullText",
                                new ArticuloFormViewModel(listaArmazon, pagActual, sizePagFT,
                                                          new List<Articulo>(), -1, sizePagA,
                                                          new List<Articulo>(), -1, sizePAgO,
                                                           texto));
            }
            catch (Exception e)
            {
                return PartialView("../Articulo/PaginadoFullText", new ArticuloFormViewModel("Estamos teniendo problemas con la búsqueda en el ARMAZON",1));
            };


        }
        [AcceptVerbs(HttpVerbs.Post)]
        public ActionResult MostrarPagAmazon(string texto, int pagActual)
        {
            try
            {
                ViewData["list"] = comboCantidades();
                ViewData["busqueda"] = texto;
                bool esAdmin = usuRepo.esAdmin(AccountController.getUsuarioActual());
                ViewData["esAdmin"] = esAdmin;
                CommunicationHelper c = new CommunicationHelper();
                List<Articulo> lista = new List<Articulo>();
                try
                {
                    int  pagAPedir =(int) System.Math.Ceiling((float) (sizePagA * (pagActual+1))/10 );
                    lista = c.buscarEnAmazon(texto, pagAPedir);

                }
                catch (Exception)
                {

                }

                return PartialView("../Articulo/PaginadoAmazon",
                                new ArticuloFormViewModel(new List<Articulo>(), -1, sizePagFT, 
                                                          lista, pagActual, sizePagA,
                                                          new List<Articulo>(), -1,sizePAgO,
                                                          texto));
            }
            catch (Exception e)
            {
                return PartialView("../Articulo/PaginadoAmazon", new ArticuloFormViewModel("Estamos teniendo problemas en la conexión con AMAZON", 2));
            };
        }

        [AcceptVerbs(HttpVerbs.Post)]
        public ActionResult MostrarPaginaOtroAr(string texto, int pagActual)
        {
            try
            {
                ViewData["list"] = comboCantidades();
                ViewData["busqueda"] = texto;
                bool esAdmin = usuRepo.esAdmin(AccountController.getUsuarioActual());
                ViewData["esAdmin"] = esAdmin;
                CommunicationHelper c = new CommunicationHelper();
                List<Articulo> listaArmazon = c.search(texto).ToList<Articulo>(); //new List<Articulo>();

                return PartialView("../Articulo/PaginadoOtroAr",
                                new ArticuloFormViewModel(new List<Articulo>(), -1, sizePAgO,
                                                          new List<Articulo>(), -1, sizePagA,
                                                          listaArmazon,pagActual,sizePAgO,
                                                           texto));
            }
            catch (Exception e)
            {
                return PartialView("../Articulo/PaginadoOtroAr", new ArticuloFormViewModel("Estamos teniendo problemas en la conexión con otro ARMAZON", 3));
            };


        }

        public ActionResult ArticuloSinCategoria() {

            var arts = repo.FindArticulosSinCategoria();
            return View(arts);
        }

       

        
        public ActionResult BusquedaFullText()
        {
            return View();
        }

        public List<Articulo> BusquedaFullTextArmazon(string texto) {
        //public ActionResult buscarFullText(string texto) {
            // parsear el texto por espacios en blanco y generar una busqueda de mas a menos 
            // token concatenados.
            // si termina con ar er o ir hacer la busqueda con el string que lo precede.

            IDictionary<int,Articulo> artResult = new Dictionary<int, Articulo>();
            List<Articulo> artsAux = null;
            List<string> tokens = new List<string>();
            List<string> tokensSimples = new List<string>();
            IDictionary<string, string> palabrasRes = darPalabrasReservadas();
            if ((texto != null) && (texto != ""))
            {
                string[] sites = texto.Split(' ');
                foreach (string s in sites)
                {
                    if (!palabrasRes.ContainsKey(s))
                    {
                        if (s.Substring(s.Length - 2, 2).Equals("ar")
                            || s.Substring(s.Length - 2, 2).Equals("er")
                            || s.Substring(s.Length - 2, 2).Equals("ir"))
                        {
                            string saux = s.Substring(0, s.Length - 2);
                            tokensSimples.Add(saux);
                        }
                        else
                        {
                            tokensSimples.Add(s);
                        }
                    }

                }
                artsAux = repo.buscarStringEnArticulos(texto);
                foreach (Articulo articulo in artsAux)
                {
                    if (!artResult.ContainsKey(articulo.id))
                    {
                        artResult.Add(articulo.id, articulo);
                    }
                }

                tokens.AddRange(tokensSimples);
                foreach (string token in tokens)
                {
                    artsAux = repo.buscarStringEnArticulos(token);
                    foreach (Articulo articulo in artsAux)
                    {
                        if (!artResult.ContainsKey(articulo.id))
                        {
                            artResult.Add(articulo.id, articulo);
                        }
                    }
                }
                artsAux = new List<Articulo>();
                foreach (KeyValuePair<int, Articulo> pair in artResult)
                {
                    artsAux.Add(pair.Value);
                }
                //return View(artsAux);
            }
            else
            {
                //return Redirect("busquedaFullText");
                throw new Exception("ArticuloController: Debe ingresar un texto para buscar.");
            }
            //return View(artsAux);
            return artsAux;
        }

        private IDictionary<string, string> darPalabrasReservadas()
        {
            IDictionary<string, string> palabrasReservadas = new Dictionary<string, string>();
            palabrasReservadas.Add("a", "a");
            palabrasReservadas.Add("de", "de");
            palabrasReservadas.Add("del", "del");
            palabrasReservadas.Add("el", "el");
            palabrasReservadas.Add("la", "la");
            palabrasReservadas.Add("los", "los");
            palabrasReservadas.Add("las", "las");
            palabrasReservadas.Add("un", "un");
            palabrasReservadas.Add("lo", "lo");
            palabrasReservadas.Add("por", "por");
            palabrasReservadas.Add("en", "en");
            palabrasReservadas.Add("para", "para");
            palabrasReservadas.Add("con", "con");
            palabrasReservadas.Add("sin", "sin");
            palabrasReservadas.Add("cual", "cual");
            palabrasReservadas.Add("donde", "donde");
            palabrasReservadas.Add("porque", "porque");
            palabrasReservadas.Add("como", "como");
            palabrasReservadas.Add("y", "y");
            palabrasReservadas.Add("o", "o");
            palabrasReservadas.Add("se", "se");
            palabrasReservadas.Add("que", "que");
            palabrasReservadas.Add("al", "al");

            return palabrasReservadas;
        }

        public ActionResult ListaArticulo(string etiqueta, int? page)
        {

            const int pageSize = 5;
            String url  = HttpContext.Request.Url.AbsolutePath;
            String[] spliteado = url.Split('/');
            String invocador = spliteado[spliteado.Count() - 2]; // spliteado te indica el nombreinvocador

            ViewData["nombreInvocador"] = "ListaArticulo"+invocador;

            IQueryable<Articulo> arts = null;

            if (etiqueta == null || etiqueta.Equals("Todos"))
            {
                arts = repo.FindAllArticulos();
            }
            else //Se indica una etiqueta
            {
                arts = repo.FindArticulosConEtiqueta(etiqueta);
            }

            var paginatedArts = new PaginatedList<Articulo>(arts, page ?? 0, pageSize);

            var list = comboCantidades();
            
            ViewData["list"] = list;


            return View(paginatedArts); 
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
