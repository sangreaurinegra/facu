using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ArmazonGr6.ArmazonInterface.Model;
using ArmazonGr6.ArmazonInterface;

namespace ArmazonGr6.ArmazonInterface.Impl {
    public class ArmazonWSImpl {

        ArmazonModelProxy model = new ArmazonModelProxy();

        public ICollection<DCProduct> search(String fullText) {

            return model.search(fullText);
        }


        public DCProduct GetProduct(int idProduct) {

            return model.GetProduct(idProduct);
        }


        public ICollection<DCRating> getRatings(int idProduct) {

            return model.getRatings(idProduct);
        }


        public bool CartBuy(String user, ICollection<DCCartItem> items) {


            return model.CartBuy(user,items);
        }
    }
}
