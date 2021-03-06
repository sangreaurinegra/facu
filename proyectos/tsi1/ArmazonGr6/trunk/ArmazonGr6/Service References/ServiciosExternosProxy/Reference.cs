//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:2.0.50727.3053
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace ArmazonGr6.ServiciosExternosProxy {
    
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "3.0.0.0")]
    [System.ServiceModel.ServiceContractAttribute(ConfigurationName="ServiciosExternosProxy.IServiciosExternos")]
    public interface IServiciosExternos {
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IServiciosExternos/GetData", ReplyAction="http://tempuri.org/IServiciosExternos/GetDataResponse")]
        string GetData(int value);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IServiciosExternos/GetDataUsingDataContract", ReplyAction="http://tempuri.org/IServiciosExternos/GetDataUsingDataContractResponse")]
        CommunicationServer.CompositeType GetDataUsingDataContract(CommunicationServer.CompositeType composite);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IServiciosExternos/iniciarCheckOut", ReplyAction="http://tempuri.org/IServiciosExternos/iniciarCheckOutResponse")]
        string iniciarCheckOut(float importe);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IServiciosExternos/confirmarCheckOut", ReplyAction="http://tempuri.org/IServiciosExternos/confirmarCheckOutResponse")]
        bool confirmarCheckOut(string token, string payerId, double monto);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IServiciosExternos/buscarEnAmazon", ReplyAction="http://tempuri.org/IServiciosExternos/buscarEnAmazonResponse")]
        CommunicationServer.CompositeType[] buscarEnAmazon(string textoABuscar, int nroPag);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IServiciosExternos/search", ReplyAction="http://tempuri.org/IServiciosExternos/searchResponse")]
        CommunicationServer.DCProduct[] search(string fullText);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IServiciosExternos/GetProduct", ReplyAction="http://tempuri.org/IServiciosExternos/GetProductResponse")]
        CommunicationServer.DCProduct GetProduct(int idProduct);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IServiciosExternos/getRatings", ReplyAction="http://tempuri.org/IServiciosExternos/getRatingsResponse")]
        CommunicationServer.DCRating[] getRatings(int idProduct);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IServiciosExternos/CartBuy", ReplyAction="http://tempuri.org/IServiciosExternos/CartBuyResponse")]
        bool CartBuy(string user, CommunicationServer.DCCartItem[] items);
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "3.0.0.0")]
    public interface IServiciosExternosChannel : ArmazonGr6.ServiciosExternosProxy.IServiciosExternos, System.ServiceModel.IClientChannel {
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "3.0.0.0")]
    public partial class ServiciosExternosClient : System.ServiceModel.ClientBase<ArmazonGr6.ServiciosExternosProxy.IServiciosExternos>, ArmazonGr6.ServiciosExternosProxy.IServiciosExternos {
        
        public ServiciosExternosClient() {
        }
        
        public ServiciosExternosClient(string endpointConfigurationName) : 
                base(endpointConfigurationName) {
        }
        
        public ServiciosExternosClient(string endpointConfigurationName, string remoteAddress) : 
                base(endpointConfigurationName, remoteAddress) {
        }
        
        public ServiciosExternosClient(string endpointConfigurationName, System.ServiceModel.EndpointAddress remoteAddress) : 
                base(endpointConfigurationName, remoteAddress) {
        }
        
        public ServiciosExternosClient(System.ServiceModel.Channels.Binding binding, System.ServiceModel.EndpointAddress remoteAddress) : 
                base(binding, remoteAddress) {
        }
        
        public string GetData(int value) {
            return base.Channel.GetData(value);
        }
        
        public CommunicationServer.CompositeType GetDataUsingDataContract(CommunicationServer.CompositeType composite) {
            return base.Channel.GetDataUsingDataContract(composite);
        }
        
        public string iniciarCheckOut(float importe) {
            return base.Channel.iniciarCheckOut(importe);
        }
        
        public bool confirmarCheckOut(string token, string payerId, double monto) {
            return base.Channel.confirmarCheckOut(token, payerId, monto);
        }
        
        public CommunicationServer.CompositeType[] buscarEnAmazon(string textoABuscar, int nroPag) {
            return base.Channel.buscarEnAmazon(textoABuscar, nroPag);
        }
        
        public CommunicationServer.DCProduct[] search(string fullText) {
            return base.Channel.search(fullText);
        }
        
        public CommunicationServer.DCProduct GetProduct(int idProduct) {
            return base.Channel.GetProduct(idProduct);
        }
        
        public CommunicationServer.DCRating[] getRatings(int idProduct) {
            return base.Channel.getRatings(idProduct);
        }
        
        public bool CartBuy(string user, CommunicationServer.DCCartItem[] items) {
            return base.Channel.CartBuy(user, items);
        }
    }
}
