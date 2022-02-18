using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using CommunicationServer.checkOut;
using CommunicationServer.AmazonConsumer;
using CommunicationServer.ArmazonInterface;

namespace CommunicationServer
{
    // NOTE: If you change the class name "Service1" here, you must also update the reference to "Service1" in Web.config and in the associated .svc file.
    public class ServiciosExternos : IServiciosExternos
    {
        public string GetData(int value)
        {
            return string.Format("You entered: {0}", value);
        }

        public CompositeType GetDataUsingDataContract(CompositeType composite)
        {
            /*if (composite.BoolValue) {
                composite.StringValue += "Suffix";
            }*/
            composite.Titulo += "SuffixTitulo";
            composite.Asin += "SuffixAsin";
            return composite;
        }



        public String iniciarCheckOut(float importe)
        {

            //retorna la direcciona a la cual debe direccionar
            // de esta forma la direccion no depende del web server,
            // y la comunicacion directa con paypal depende de Comm Server
            PayPalCheckOutService pps = new PayPalCheckOutService();

            return pps.iniciarCheckOut(importe);
        }


        public bool confirmarCheckOut(String token, String payerId, double monto)
        {

            PayPalCheckOutService pps = new PayPalCheckOutService();

            return pps.confirmarCheckOut(token, payerId, monto);
        }


        public List<CompositeType> buscarEnAmazon(string textoABuscar, int nroPag)
        {
            AmazonService servicio = new AmazonService();
            return servicio.buscarArticulos(textoABuscar,nroPag);
        }


        public ICollection<DCProduct> search(String fullText) {
            ArmazonInterfaceClient impl = new ArmazonInterfaceClient();
            return impl.search(fullText) ;
        }


        public DCProduct GetProduct(int idProduct) {
            ArmazonInterfaceClient impl = new ArmazonInterfaceClient();
            return impl.GetProduct(idProduct);
        }


        public ICollection<DCRating> getRatings(int idProduct) {
            ArmazonInterfaceClient impl = new ArmazonInterfaceClient();
            return impl.getRatings(idProduct);
        }


        public bool CartBuy(String user, ICollection<DCCartItem> items) {
            ArmazonInterfaceClient impl = new ArmazonInterfaceClient();
            return impl.CartBuy(user,items);
        }

    }
}
