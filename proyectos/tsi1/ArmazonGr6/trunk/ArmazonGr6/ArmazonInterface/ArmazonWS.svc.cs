using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.ServiceModel.Activation;
using ArmazonGr6.ArmazonInterface.Impl;


namespace ArmazonGr6.ArmazonInterface {
    // NOTE: If you change the class name "Service1" here, you must also update the reference to "Service1" in Web.config and in the associated .svc file.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class ArmazonWS : IArmazonWS {

        ArmazonWSImpl impl = new ArmazonWSImpl();

        public ICollection<DCProduct> search(String fullText) {

            return impl.search(fullText) ;
        }


        public DCProduct getProduct(int idProduct) {

            return impl.GetProduct(idProduct);
        }


        public ICollection<DCRating> getRatings(int idProduct) {

            return impl.getRatings(idProduct);
        }


        public bool CartBuy(String user, ICollection<DCCartItem> items) {


            return impl.CartBuy(user,items);
        }
    }
}
