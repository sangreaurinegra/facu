using System;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Activation;
using System.Collections.Generic;
using System.Text;
using ArmazonGr6.Models;

namespace ArmazonGr6
{
    [ServiceContract(Namespace = "")]
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class ServiceCloud
    {
        [OperationContract]
        public IList<Etiqueta> GetCloudItems()
        {

            System.Console.WriteLine("CAROLO ");

            var bd = new ArmazonModelDataContext();

             var query = (from c in bd.CloudItems
                                                orderby c.weight
                                                select c).ToList().Take(50);
                //bd.CloudItems.OrderByDescending(cloudItem => cloudItem.weigth);
            

            /*  var query = new List<CloudItem>();
              var tag1 = new CloudItem {name = "poronga1", weight = 5, url = "wwwalgo1", tooltip = "lalalala1"};
              query.Add(tag1);
              var tag2 = new CloudItem {name = "poronga2", weight = 10, url = "wwwalgo2", tooltip = "lalalala2"};
              query.Add(tag2);
              var tag3 = new CloudItem {name = "poronga3", weight = 15, url = "wwwalgo3", tooltip = "lalalala3"};
              query.Add(tag3);
               */
            var retorno = new List<Etiqueta>();

            foreach (CloudItem cloudItem in query)
            {
                Etiqueta aux = new Etiqueta {Nombre = cloudItem.name, Tamanio = cloudItem.weight, Url = cloudItem.url};
                System.Console.WriteLine("Tag- "+cloudItem.name);
                  
                retorno.Add(aux);
            }

            return retorno;
            //throw new System.NotImplementedException();
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

