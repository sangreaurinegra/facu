using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace ArmazonGr6.ArmazonInterface {
    // NOTE: If you change the interface name "IService1" here, you must also update the reference to "IService1" in Web.config.
    [ServiceContract]
    public interface IArmazonWS {

        [OperationContract]
        ICollection<DCProduct> search(String fullText);

        [OperationContract]
        DCProduct getProduct(int idProduct);

        [OperationContract]
        ICollection<DCRating> getRatings(int idProduct);

        [OperationContract]
        bool CartBuy(String user, ICollection<DCCartItem> items);

    }


    // Use a data contract as illustrated in the sample below to add composite types to service operations.
    [DataContract]
    public class CompositeType {
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
    }

    /*==================================================================================
     *  DATA CONTRACTS
     * 
     * =================================================================================
     */
    [Serializable]
    [DataContract]
    [KnownType(typeof(DCProductAttrBool))]
    [KnownType(typeof(DCProductAttrString))]
    [KnownType(typeof(DCProductAttrDouble))]
    [KnownType(typeof(DCProductAttrFloat))]
    [KnownType(typeof(DCProductAttrImage))]
    [KnownType(typeof(DCProductAttrInt))]
    public class DCProduct {
        private int productID = 0;
        private float ratingAvg = 0;
        private ICollection<DCProductAttr> attrs = null;

        [DataMember]
        public int ProductId {
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
        private int productID = 0;
        private int quantity = 0;

        [DataMember]
        public int ProductId {
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
        private string comments = "";
        private int rating = 0;

        [DataMember]
        public string Comment {
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
        private bool _isKey;
        private String _name;

        public DCProductAttr() {
        }

        public DCProductAttr(string name, bool isKey) {
            _name = name;
            _isKey = isKey;
        }

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
    }

    [Serializable]
    [DataContract]
    public class DCProductAttrBool : DCProductAttr {
        private bool value = false;
        
        [DataMember]
        public bool Value {
            get { return (bool)value; }
            set { this.value = value; }
        }
    }

    [Serializable]
    [DataContract]
    public class DCProductAttrString : DCProductAttr {
        private string value = "";

        [DataMember]
        public string Value {
            get { return (string)value; }
            set { this.value = value; }
        }
    }

    [Serializable]
    [DataContract]
    public class DCProductAttrInt : DCProductAttr {
        private int value = 0;

        [DataMember]
        public int Value {
            get { return (int)value; }
            set { this.value = value; }
        }
    }

    [Serializable]
    [DataContract]
    public class DCProductAttrFloat : DCProductAttr {
        private float value = 0;

        [DataMember]
        public float Value {
            get { return (float)value; }
            set { this.value = value; }
        }
    }

    [Serializable]
    [DataContract]
    public class DCProductAttrImage : DCProductAttr {
        private string _url;

        public DCProductAttrImage()
            : base() {
        }

        public DCProductAttrImage(string name, bool isKey, string url)
            : base(name, isKey) {
            _url = url;
        }

        [DataMember]
        public string Url {
            get { return _url; }
            set { _url = value; }
        }
    }

    [Serializable]
    [DataContract]
    public class DCProductAttrDouble : DCProductAttr {
        private double _value;

        public DCProductAttrDouble()
            : base() {
        }

        public DCProductAttrDouble(string name, bool isKey, double value)
            : base(name, isKey) {
            _value = value;
        }

        [DataMember]
        public double Value {
            get { return _value; }
            set { _value = value; }
        }
    }
}
