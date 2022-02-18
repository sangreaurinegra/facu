using System;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Activation;
using System.Collections.Generic;
using System.Text;
using ArmazonGr6.Controllers;
using ArmazonGr6.Helpers;
using ArmazonGr6.Models;

namespace ArmazonGr6
{
    [ServiceContract(Namespace = "")]
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class ServiceSilver
    {
         [OperationContract]
        public IList<Etiqueta> GetCloudItems()
        {

            var bd = new ArmazonModelDataContext();

             // Tomo las 50 etiquetas mas votadas
            var query = (from c in bd.CloudItems
                                               orderby c.weight descending 
                                               select c).ToList().Take(30);

             // las ordeno por nombre para evitar entregarlas de menor a mayor
             
              query = query.OrderByDescending(cloudItem => cloudItem.name);


            var retorno = new List<Etiqueta>();

            foreach (CloudItem cloudItem in query)
            {
                Etiqueta aux = new Etiqueta {Nombre = cloudItem.name, Tamanio = cloudItem.weight, Url = cloudItem.url};
                System.Console.WriteLine("Tag- "+cloudItem.name);
                  
                retorno.Add(aux);
            }

            return retorno;
        
        }

         [OperationContract]
         public string direccionDelServer()
         {
             SystemProperties sp = null;
              if (SystemProperties.isSystemPropertiesLoaded())
                sp = SystemProperties.getSystemProperties();
            else
                  sp = SystemProperties.loadProperties("C:/Projects/ArmazonGr6/ArmazonGr6/config/config.txt");

             return sp.getProperty("UrlActual");
         }

    }



    [DataContract]
    public class Etiqueta
    {
        [DataMember]
        public string Nombre { get; set; }
        [DataMember]
        public long Tamanio { get; set; }
        [DataMember]
        public string Url { get; set; }
    }
}

