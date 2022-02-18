using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ArmazonGr6.Models;
using ArmazonGr6.Helpers;
using ArmazonGr6.ArmazonInterface;


namespace ArmazonGr6.ArmazonInterface.Model {
    public class ModelConverter {
        /*
         * Esta clase convierte los objetos del Armazon local a los objetos de la interfaz.
         * De esta forma el Impl de la interfaz nunca trabaja con objetos del modelo interno.
         * 
         */


        public static ICollection<DCRating> convert(IEnumerable<Calificacion> registros) {

            LinkedList<DCRating> califs = new LinkedList<DCRating>();

            foreach (Calificacion reg in registros) {

                DCRating rat = new DCRating();
                rat.Comment = reg.revision;
                rat.Rating = reg.puntuacion;
                califs.AddLast(rat);
            }
            return califs;

        }

        public static ArtCant convert(DCCartItem item) {

            ArtCant ac = new ArtCant();
            ac.idArticulo = item.ProductId;
            ac.cantidad = item.Quantity;
            return ac;

        }

        public static DCProduct convert(Articulo art,IEnumerable<Registro> registros, float avg) {
            DCProduct prod = new DCProduct();
            prod.ProductId = art.id;
            prod.Attrs = new LinkedList<DCProductAttr>();

            foreach (Registro reg in registros) { 

                prod.Attrs.Add(setValue(reg.Campo.tipo,reg.valor,reg.Campo.nombre));
            }
            //ahora cargo la url de la imagen.
            prod.Attrs.Add(setValue("imagen", art.imagen, "imagen"));
            prod.Attrs.Add(setValue("precio", art.precio + "", "Precio"));
            prod.Attrs.Add(setValue("Nombre", art.nombre, "Nombre"));
            prod.RatingAvg = avg;
            return prod;   
            
        }

        public static ICollection<DCProduct> convert(IEnumerable<Articulo> registros) {
            List<DCProduct> lista = new List<DCProduct>();
            ArmazonModelProxy p = new ArmazonModelProxy();

            foreach (Articulo a in registros) { 
                lista.Add(convert(a,p.Registros(a.id),p.AvgRating(a.id)));            
            }

            return lista;
        }

        private static String urlImagen(String imagen) {
            String url = "";
            if (imagen != null && imagen.StartsWith("http"))
                return imagen;
            else {
                SystemProperties sp = null;
                if (SystemProperties.isSystemPropertiesLoaded())
                    sp = SystemProperties.getSystemProperties();
                else
                    sp = SystemProperties.loadProperties("C:/Projects/ArmazonGr6/ArmazonGr6/config/config.txt");
                String PATHIMAGEN = sp.getProperty("PathImageRelativo").Replace('\\', '/');
                String URLActual = sp.getProperty("UrlActual");
                if (imagen == null)
                    return "";
                else
                    url = URLActual + PATHIMAGEN + "/" + imagen;
            }
            return url;
        }

        private static DCProductAttr setValue(String tipo, String value, String nombre) {
            DCProductAttr attr = null;
            if (tipo.Trim() == "String" || tipo.Trim() == "Nombre" || tipo.Trim() == "url") {
                attr = new DCProductAttrString();
                ((DCProductAttrString)attr).Value = value;
            }
            else if (tipo.Trim() == "int") {
                attr = new DCProductAttrInt();
                ((DCProductAttrInt)attr).Value = int.Parse(value);
            }
            else if (tipo.Trim() == "bool") {
                attr = new DCProductAttrBool();
                bool valor = false;
                if (value.Trim() == "Si")
                    valor = true;
                ((DCProductAttrBool)attr).Value = valor;
            }
            else if (tipo.Trim() == "double") {
                attr = new DCProductAttrFloat();
                ((DCProductAttrFloat)attr).Value = float.Parse(value);
            }
            else if (tipo.Trim() == "precio") {
                attr = new DCProductAttrDouble();
                ((DCProductAttrDouble)attr).Value = float.Parse(value);
            }
            else { // image
                attr = new DCProductAttrImage();
                ((DCProductAttrImage)attr).Url = urlImagen(value);
            }
            attr.Name = nombre;
            return attr;
        }
    }
}
