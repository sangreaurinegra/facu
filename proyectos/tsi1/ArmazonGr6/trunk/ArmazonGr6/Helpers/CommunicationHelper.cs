using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ArmazonGr6.ServiciosExternosProxy;
using CommunicationServer;
using ArmazonGr6.Models;
using ArmazonGr6.Client_Armazon;

namespace ArmazonGr6.Helpers {

    // Este helper se usa para comunicarse con el Communication Server
    public class CommunicationHelper {

        private ArticuloRepository artRepository;
        private ProveedorRepository provRepository;
        

        public CommunicationHelper()
        {
            artRepository = new ArticuloRepository();
            provRepository = new ProveedorRepository();
        }

        public String iniciarCheckOut(float importe) {
            ServiciosExternosProxy.IServiciosExternos proxy = new ServiciosExternosProxy.ServiciosExternosClient();

            return proxy.iniciarCheckOut(importe);
        }


        public bool confirmarCheckOut(String token, String payerId, double monto) {

            ServiciosExternosProxy.IServiciosExternos proxy = new ServiciosExternosProxy.ServiciosExternosClient();

            return proxy.confirmarCheckOut(token,payerId, monto);
        }

        public List<Articulo> buscarEnAmazon(String textoABuscar, int nroPag)
        {
            ServiciosExternosProxy.IServiciosExternos proxy = new ServiciosExternosProxy.ServiciosExternosClient();

            int nroP = nroPag < 6 ? nroPag : 5;
            nroPag = nroPag > 0 ? nroPag : 1;
            var ret = new List<Articulo>();

            List<CompositeType> listaArticulos = proxy.buscarEnAmazon(textoABuscar,nroPag).ToList<CompositeType>();

            
            if (listaArticulos != null) {
                //mientras hayan elementos en la listaArticulos
                Proveedor prov;

                //TODO: el Amazon lo agrego a mano
                prov = provRepository.FindProveedor("Amazon");
                if (prov == null){
                    prov = new Proveedor() {nombre="Amazon", url="www.amazon.com"};
                    provRepository.Add(prov);
                    provRepository.Save();
                };

                foreach (CompositeType articulo in listaArticulos)
                {
                    //agregarlo a la BD si no lo tenemos
                    var lista = artRepository.FindArticuloExterno(articulo.Asin);

                    if (lista.Count() == 0) // si no existe
                    {
                        
                        //solamente ingresamos
                        Articulo art = new Articulo()
                        {
                            nombre = (articulo.Titulo.Length <= 50) ? articulo.Titulo : articulo.Titulo.Remove(49),
                            precio = articulo.Amount != null ? float.Parse(articulo.Amount):0,
                            imagen = articulo.Imagen
                        };

                        artRepository.Add(art);
                        
                        Externo artExt = new Externo()
                            {
                                //id = art.id,
                                claveExterna = articulo.Asin,
                                idProveedor = prov.id
                                //TODO: agregar el idProovedor de Amazon
                            };
                        artExt.Articulo = art;
                        artRepository.AddExterno(artExt);
                        
                    }
                    else
                    {
                        // si lo tenemos debemos actualizar el precio y la URL de la imagen
                        Articulo artAModif = lista.First<Articulo>();
                        //TODO: el precio se cambia solamente si el que recivimos es distinto de null
                        artAModif.precio = articulo.Amount!=null ? float.Parse(articulo.Amount) : 0;
                        artAModif.imagen = articulo.Imagen;
                    }
                    
                    
                }
                artRepository.Save();

                
                foreach (CompositeType articulo in listaArticulos)
                {
                    //agregarlo a la BD si no lo tenemos
                    var articuloMio = artRepository.FindArticuloExterno(articulo.Asin).First();
                    ret.Add(articuloMio);
                }

            }

            return ret;
        }


        /*
         * SERVICIOS CONSUMIDOS DESDE OTRO ARMAZON
         * 
         */

        public IEnumerable<Articulo> search(String fullText) {
            //guardamos en la sesion la busqueda, y luego consultamos esa busqueda.
            string idSesion;
            try {
                idSesion = (string)HttpContext.Current.Session["SearchFT"];
            }
            catch {
                idSesion = "__BUSCAR__";
            }
            string current = HttpContext.Current.Session.SessionID + "_" + fullText;
            IEnumerable<Articulo> lista;
            if (idSesion == null || idSesion == "__BUSCAR__" || idSesion != current) {
                try {
                    Client_Armazon.ArmazonWSClient proxy = new ArmazonWSClient();
                    var pepe = proxy.search(fullText);
                    lista = ModelConverter.convert(pepe);
                    HttpContext.Current.Session["SearchFTResult"] = lista;
                    HttpContext.Current.Session["SearchFT"] = current+"_"+fullText;
                }
                catch(Exception e) {
                    lista = new List<Articulo>();
                }
            }
            else {
                lista = (IEnumerable<Articulo>)HttpContext.Current.Session["SearchFTResult"];
            }
            return lista;
        }


        public Articulo GetProduct(int idProduct) {
            Client_Armazon.ArmazonWSClient proxy = new ArmazonWSClient();
            
            return ModelConverter.convert(proxy.getProduct(idProduct));
        }


        public IEnumerable<Calificacion> getRatings(int idProduct) {

            Client_Armazon.ArmazonWSClient proxy = new ArmazonWSClient();
            return ModelConverter.convert(proxy.getRatings(idProduct));
        }


        public bool CartBuy(String user, ICollection<ArtCant> items) {
            ICollection<Client_Armazon.DCCartItem> listAux = ModelConverter.convert(items);
            
            Client_Armazon.ArmazonWSClient proxy = new ArmazonWSClient();
            return proxy.CartBuy(user, listAux.ToArray());
        }

    }
}
