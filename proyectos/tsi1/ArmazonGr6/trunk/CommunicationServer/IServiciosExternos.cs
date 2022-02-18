using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace CommunicationServer
{
    // NOTE: If you change the interface name "IService1" here, you must also update the reference to "IService1" in Web.config.
    [ServiceContract]
    public interface IServiciosExternos
    {

        [OperationContract]
        string GetData(int value);

        [OperationContract]
        CompositeType GetDataUsingDataContract(CompositeType composite);

        // TODO: Add your service operations here
        [OperationContract]
        String iniciarCheckOut(float importe);

        [OperationContract]
        bool confirmarCheckOut(String token, String payerId, double monto);

        [OperationContract]
        List<CompositeType> buscarEnAmazon(string textoABuscar, int nroPag);


        /*
         * METODOS PARA USAR INTERFAZ DE OTRO ARMAZON
         */

        [OperationContract]
        ICollection<DCProduct> search(String fullText);

        [OperationContract]
        DCProduct GetProduct(int idProduct);

        [OperationContract]
        ICollection<DCRating> getRatings(int idProduct);

        [OperationContract]
        bool CartBuy(String user, ICollection<DCCartItem> items);

    }


    // Use a data contract as illustrated in the sample below to add composite types to service operations.
    [DataContract]
    public class CompositeType
    {
        /*bool hayError = false;
        List<ArtArmazon> respuesta;
        */
        string asin;
        string titulo;
        string amount;
        string imagen;

        [DataMember]
        public string Asin
        {
            get { return asin; }
            set { asin = value; }
        }

        [DataMember]
        public string Titulo
        {
            get { return titulo; }
            set { titulo = value; }
        }

        [DataMember]
        public string Amount
        {
            get { return amount; }
            set { amount = value; }
        }

        [DataMember]
        public string Imagen
        {
            get { return imagen; }
            set { imagen = value; }
        }
    }


    /*==================================================================================
     *  DATA CONTRACTS
     * ARMAZON INTERFACE
     * =================================================================================
     */

    [Serializable]
    [DataContract]
    public class DCProduct {
        int productID = 0;
        float ratingAvg = 0;
        ICollection<DCProductAttr> attrs = null;

        [DataMember]
        public int ProductID {
            get { return productID; }
            set { productID = value; }
        }

        [DataMember]
        public float RatingAvg {
            get { return ratingAvg; }
            set { ratingAvg = value; }
        }

        [DataMember]
        public ICollection<DCProductAttr> Attrs {
            get { return attrs; }
            set { this.attrs = value; }
        }
    }

    [Serializable]
    [DataContract]
    public class DCCartItem {
        int productID = 0;
        int quantity = 0;

        [DataMember]
        public int ProductID {
            get { return productID; }
            set { productID = value; }
        }

        [DataMember]
        public int Quantity {
            get { return quantity; }
            set { quantity = value; }
        }
    }

    [Serializable]
    [DataContract]
    public class DCRating {
        string comments = "";
        int rating = 0;

        [DataMember]
        public string Comments {
            get { return comments; }
            set { comments = value; }
        }

        [DataMember]
        public int Rating {
            get { return rating; }
            set { rating = value; }
        }
    }

    [Serializable]
    [DataContract]
    public abstract class DCProductAttr {
        public Object value;
    }

    [Serializable]
    [DataContract]
    public class DCProductAttrBool : DCProductAttr {
        //bool value = false;

        [DataMember]
        public bool Value {
            get { return (bool)value; }
            set { this.value = value; }
        }
    }

    [Serializable]
    [DataContract]
    public class DCProductAttrString : DCProductAttr {
        //string value = "";

        [DataMember]
        public string Value {
            get { return (string)value; }
            set { this.value = value; }
        }
    }

    [Serializable]
    [DataContract]
    public class DCProductAttrInt : DCProductAttr {
        private bool _isKey;
        private String _name;

        [DataMember]
        public bool IsKey {
            get { return _isKey; }
            set { _isKey = value; }
        }

        [DataMember]
        public String Name {
            get { return _name; }
            set { _name = value; }
        }


        [DataMember]
        public int Value {
            get { return (int)value; }
            set { this.value = value; }
        }
    }

    [Serializable]
    [DataContract]
    public class DCProductAttrFloat : DCProductAttr {
        //float value = 0;

        [DataMember]
        public float Value {
            get { return (float)value; }
            set { this.value = value; }
        }
    }

    [Serializable]
    [DataContract]
    public class DCProductAttrImage : DCProductAttr {
        //string value = "";

        [DataMember]
        public string Value {
            get { return (string)value; }
            set { this.value = value; }
        }
    }

    /*public class CompositeType {
        bool boolValue = true;
        string stringValue = "Hello ";

        [DataMember]
        public bool BoolValue {
            get { return boolValue; }
            set { boolValue = value; }
        }

        [DataMember]
        public string StringValue {
            get { return stringValue; }
            set { stringValue = value; }
        }
    }*/
}