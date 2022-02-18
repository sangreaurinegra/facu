using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using CommunicationServer.ArmazonWSClient;

namespace CommunicationServer.ArmazonInterface {
    public class ArmazonInterfaceClient {
       
        public ICollection<DCProduct> search(String fullText) {

            return null ;
        }


        public DCProduct GetProduct(int idProduct) {
            ArmazonWSClient.ArmazonWSClient client = new ArmazonWSClient.ArmazonWSClient();
            var response = client.GetProduct(idProduct);
            DCProduct prod = new DCProduct();
            prod.ProductID = response.ProductID;
            foreach (ArmazonWSClient.DCProductAttr r in response.Attrs) {
                DCProductAttr attr = null;
      /*          if (r is ArmazonWSClient.DCProductAttrString) {
                    
                }
                else if (attr is DCProductAttrInt) {
                    reg = new Registro();
                    reg.idCampo = Campo.Campo_Int;
                    reg.valor = attr.value.ToString();
                }
                else if (attr is DCProductAttrBool) {
                    reg = new Registro();
                    reg.idCampo = Campo.Campo_Bool;
                    reg.valor = attr.value.ToString();
                }
                else if (attr is DCProductAttrFloat) {
                    reg = new Registro();
                    reg.idCampo = Campo.Campo_Float;
                    reg.valor = attr.value.ToString();
                }*/
                prod.Attrs.Add(attr);
            }
            
            prod.RatingAvg = response.RatingAvg;
            return prod;
        }


        public ICollection<DCRating> getRatings(int idProduct) {
            ArmazonWSClient.ArmazonWSClient client = new ArmazonWSClient.ArmazonWSClient();
            var response = client.getRatings(idProduct);
            List<DCRating> lista = new List<DCRating>();
            foreach (ArmazonWSClient.DCRating r in response) {
                DCRating dcr = new DCRating();
                dcr.Comments = r.Comments;
                dcr.Rating = r.Rating;
                lista.Add(dcr);
            }
            return lista;
        }


        public bool CartBuy(String user, ICollection<DCCartItem> items) {
            ArmazonWSClient.ArmazonWSClient client = new ArmazonWSClient.ArmazonWSClient();
            List<ArmazonWSClient.DCCartItem> lista = new List<ArmazonWSClient.DCCartItem>();
            foreach (DCCartItem item in items.ToList())
            {
                ArmazonWSClient.DCCartItem dci = new ArmazonWSClient.DCCartItem();
                dci.ProductID = item.ProductID;
                dci.Quantity = item.Quantity;
                lista.Add(dci);
            }
            return client.CartBuy(user, lista.ToArray());
        }
  

    }
}
