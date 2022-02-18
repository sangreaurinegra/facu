using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ArmazonGr6.Models;
using ArmazonGr6.ServiciosExternosProxy;
using CommunicationServer;
using ArmazonGr6.Client_Armazon;

namespace ArmazonGr6.Helpers {
    public class ModelConverter {
        /*
         * Esta clase convierte los objetos del Armazon local a los objetos de la interfaz.
         * De esta forma el Impl de la interfaz nunca trabaja con objetos del modelo interno.
         * 
         */

        // lista(DCRating) --> lista(Calificacion)
        public static IEnumerable<Calificacion> convert(ICollection<Client_Armazon.DCRating> registros) {

            List<Calificacion> califs = new List<Calificacion>();
            List<Client_Armazon.DCRating> regs = registros.ToList();
            foreach (Client_Armazon.DCRating reg in regs) {

                Calificacion rat = new Calificacion();
                rat.revision = reg.Comment;
                rat.puntuacion = reg.Rating;
                califs.Add(rat);
            }
            return califs;

        }

        // DCItem --> Artcant
        public static ArtCant convert(Client_Armazon.DCCartItem item) {

            ArtCant ac = new ArtCant();
         //   ac.idArticulo = item.ProductID;
            ac.cantidad = item.Quantity;
            return ac;

        }

        // DCProduct -->  Articulo
        public static Articulo convert(Client_Armazon.DCProduct prod) {
            ProveedorRepository provRepo = new ProveedorRepository();
            Proveedor provee = provRepo.FindProveedor("ArmazonTst");
            Articulo art = new Articulo();
            /*art.Externo = new Externo();
            art.Externo.claveExterna = ""+prod.ProductId;
            art.Externo.idProveedor = provee.id;*/
            List<Registro> regs = new List<Registro>();
            Externo exte = new Externo();
            exte.claveExterna = "" + prod.ProductId;
            exte.idProveedor = provee.id;

            foreach (Client_Armazon.DCProductAttr reg in prod.Attrs.ToList()) {

                if (reg is Client_Armazon.DCProductAttrImage) {
                    art.imagen = ((Client_Armazon.DCProductAttrImage)reg).Url;
                }
                else if (reg is Client_Armazon.DCProductAttrDouble) {
                    if (reg.Name == "Precio")
                        art.precio = ((Client_Armazon.DCProductAttrDouble)reg).Value;
                    else
                    {
                        regs.Add(setValueRegistro(reg)); 
                        //art.Registros.Add(setValueRegistro(reg)); 
                    }
                        
                }else if(reg is Client_Armazon.DCProductAttrString)
                {
                    if(reg.Name == "Nombre")
                    {
                        art.nombre = ((Client_Armazon.DCProductAttrString)reg).Value;
                    }
                    else
                    {
                        regs.Add(setValueRegistro(reg));
                        //art.Registros.Add(setValueRegistro(reg));
                    }
                }else {
                    regs.Add(setValueRegistro(reg));
                    //art.Registros.Add(setValueRegistro(reg));
                }
            }
            ArticuloRepository artrepo = new ArticuloRepository();
            
            var artsExt = artrepo.FindArticuloExterno(exte.claveExterna);
            Articulo artExt = null;

            PlantillaRepository pr = new PlantillaRepository();
            if( artsExt!= null && artsExt.Count() > 0){
                artExt = artsExt.First();
                artExt.imagen = art.imagen;
                artExt.precio = art.precio;
                artrepo.Update(artExt);
                Registro regis = null;
                foreach (Registro reg in regs){
                    regis = pr.GetRegistroPorNombreYArticulo(artExt.id, reg.Campo.nombre.Trim());
                    if(regis != null){
                        regis.valor = reg.valor;
                        pr.Update(regis);
                    }
                    else{
                        reg.idArticulo = artExt.id;
                        pr.Add(reg);
                        pr.Save();
                    }   
                }
                try{
                    artrepo.Save();
                }
                catch (Exception e){

                }
                art = artExt;
            }
            else{
                artrepo.Add(art);
                artrepo.Save();
                exte.id = art.id;
                art.Externo = exte;
            
                
                foreach (Registro reg in regs)
                {
                    reg.idArticulo = art.id;
                    pr.Add(reg);
                    pr.Save();
                }
                
                try
                {
                    artrepo.Save();
                }
                catch (Exception e)
                {

                }
            }
            
            return art;   
            
        }

        public static IEnumerable<Articulo> convert(ICollection<Client_Armazon.DCProduct> registros) {

            List<Articulo> califs = new List<Articulo>();
            List<Client_Armazon.DCProduct> regs = registros.ToList();
            foreach (Client_Armazon.DCProduct reg in regs) {

                califs.Add(ModelConverter.convert(reg));
            }
            return califs;

        }

        // Articulo --> DCProduct 
        public static Client_Armazon.DCProduct convert(Articulo art, IEnumerable<Registro> registros, float avg) {
            Client_Armazon.DCProduct prod = new Client_Armazon.DCProduct();
       /*     prod.ProductID = art.id;
            prod.Attrs = new LinkedList<DCProductAttr>();

            foreach (Registro reg in registros) {

                prod.Attrs.Add(setValue(reg.Campo.tipo, reg.valor));
            }
            //ahora cargo la url de la imagen.
            prod.Attrs.Add(setValue("imagen", art.imagen));
            prod.RatingAvg = avg;*/
            return prod;

        }

        private static String urlImagen(String imagen) {
            String url = "";
            SystemProperties sp = null;
            if (SystemProperties.isSystemPropertiesLoaded())
                sp = SystemProperties.getSystemProperties();
            else
                sp = SystemProperties.loadProperties("C:/Projects/ArmazonGr6/ArmazonGr6/config/config.txt");
            String PATHIMAGEN = sp.getProperty("PathImageRelativo").Replace('\\','/');
            String URLActual = sp.getProperty("UrlActual");
            url = URLActual + PATHIMAGEN + "/" + imagen;
            return url;
        }

        private static Client_Armazon.DCProductAttr setValue(String tipo, String value) {
            Client_Armazon.DCProductAttr attr = null;
          /*  if (tipo.Trim() == "String") {
                attr = new Client_Armazon.DCProductAttrString();
                attr = value;
            }
            else if (tipo.Trim() == "int") {
                attr = new Client_Armazon.DCProductAttrInt();
                attr.value = value;
            }
            else if (tipo.Trim() == "bool") {
                attr = new Client_Armazon.DCProductAttrBool();
                attr.value = value;
            }
            else if (tipo.Trim() == "double") {
                attr = new Client_Armazon.DCProductAttrFloat();
                attr.value = value;
            }
            else { // image
                attr = new Client_Armazon.DCProductAttrImage();
                attr.value = value;
            }*/

            return attr;
        }


        private static Registro setValueRegistro(Client_Armazon.DCProductAttr attr) {
            Registro reg = null;
            Campo campo = new Campo();
            campo.nombre = attr.Name;
            campo.idPlantilla = 11; // id PLANTILLA ARTS EXTERNOS
            
            if (attr is Client_Armazon.DCProductAttrString) {
                campo.tipo = Campo.Tipo_Str;
                reg = new Registro();
                reg.valor = ((Client_Armazon.DCProductAttrString)attr).Value.ToString();
            }
            else if (attr is Client_Armazon.DCProductAttrInt) {
                reg = new Registro();
                campo.tipo = Campo.Tipo_Int;
                reg.valor = ((Client_Armazon.DCProductAttrInt)attr).Value.ToString();
            }
            else if (attr is Client_Armazon.DCProductAttrBool) {
                reg = new Registro();
                campo.tipo = Campo.Tipo_Bool;
                String valor = "No";
                if (((Client_Armazon.DCProductAttrBool)attr).Value)
                    valor = "Si";
                reg.valor = valor;
            }
            else if ((attr is Client_Armazon.DCProductAttrDouble)) {
                reg = new Registro();
                campo.tipo = Campo.Tipo_Float;
                reg.valor = ((Client_Armazon.DCProductAttrDouble)attr).Value .ToString();
            }
            else if ((attr is Client_Armazon.DCProductAttrFloat)) {
                reg = new Registro();
                campo.tipo = Campo.Tipo_Float;
                reg.valor = ((Client_Armazon.DCProductAttrFloat)attr).Value.ToString();
            }
            PlantillaRepository pr = new PlantillaRepository();
            //pr.Add(campo);
            //pr.Save();
            //reg.idCampo = campo.id;
            reg.Campo = campo;
            return reg;
        }


        public static Client_Armazon.DCCartItem convert(ArtCant item)
        {
            Client_Armazon.DCCartItem dcc = new Client_Armazon.DCCartItem();
            dcc.ProductId = int.Parse(item.Articulo.Externo.claveExterna);
            dcc.Quantity = item.cantidad;
            return dcc;
        }

        public static ICollection<Client_Armazon.DCCartItem> convert(ICollection<ArtCant> colItem)
        {
            Client_Armazon.DCCartItem dcc;
            ICollection<Client_Armazon.DCCartItem> retCol = new List<Client_Armazon.DCCartItem>();
            foreach (ArtCant item in colItem)
            {
                dcc = ModelConverter.convert(item);
                retCol.Add(dcc);
            }
            
            return retCol;
        }
    }
}
