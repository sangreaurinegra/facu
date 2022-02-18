using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using CommunicationServer.com.amazon.webservices;


namespace CommunicationServer.AmazonConsumer
{

    public class AmazonService
    {
        //esta clase se encarga de la comunicación con Amazon
        private AWSECommerceService aws;
        private ItemSearchRequest request;
        private const float precioDolar = 30;

        public List<CompositeType> buscarArticulos(string textoABuscar,int nroPag)
        {
            aws = new AWSECommerceService();
            request = new ItemSearchRequest();

            request.SearchIndex = "All"; //busca todos los productos
            request.Keywords = textoABuscar;
            request.ResponseGroup = new string[] { "Small", "OfferSummary", "Images", "Offers" };
            //request.Sort = "salesrank";
            request.ItemPage = nroPag.ToString();
            request.Availability = ItemSearchRequestAvailability.Available; //disponible para vender
            request.MerchantId = "Amazon";// "All";

            ItemSearchRequest[] requests = new ItemSearchRequest[] { request };

            ItemSearch itemSearch = new ItemSearch();
            //itemSearch.AssociateTag = "myassociatetag-20";
            itemSearch.SubscriptionId = "AKIAIRPDXB6LILG6GDQQ";
            itemSearch.Request = requests;

            try
            {
                ItemSearchResponse response = aws.ItemSearch(itemSearch);
                Items info = response.Items[0];
                Item[] items = info.Item;


                if (info.Request.IsValid.Equals("True"))
                {
                    //Label2.Text += "Resultados-" + info.TotalResults + "-Paginas-" + info.TotalPages;

                    if (info.Request.Errors == null)
                    {

                        if (items != null)
                        {
                            CompositeType articulo;
                            List<CompositeType> listaArticulos = new List<CompositeType>();

                            for (int i = 0; i < items.Length; i++)
                            {
                                Item item = items[i];
                                articulo = new CompositeType();

                                articulo.Titulo = item.ItemAttributes.Title;
                                articulo.Asin = item.ASIN;
                                if (item.SmallImage != null)
                                {
                                    articulo.Imagen = item.SmallImage.URL;
                                }
                                if (item.Offers.TotalOffers.Equals("0") == false
                                    && item.OfferSummary.LowestNewPrice.Amount != null)
                                {                                    
                                    //fila["Precio"] = item.OfferSummary.LowestNewPrice.FormattedPrice;
                                    articulo.Amount = (float.Parse(item.OfferSummary.LowestNewPrice.Amount) / 100 * precioDolar).ToString();
                                    //fila["Currency"] = item.OfferSummary.LowestNewPrice.CurrencyCode;
                                }
                                //fila["ProductGroup"] = item.ItemAttributes.ProductGroup;
                                //fila["NroOfertas"] = item.Offers.TotalOffers;
                                //fila["Height"] = item.SmallImage.Height.Value;
                                //fila["Width"] = item.SmallImage.Width.Value;

                                listaArticulos.Add(articulo);
                                

                            }

                            return listaArticulos;
                        }
                        else
                        {
                            throw new Exception("AmazonService: No hay items para mostrar");
                        }
                    }
                    else
                    {
                        string mensajeError = "";
                        for (int j = 0; j < info.Request.Errors.Length; j++)
                        {
                            mensajeError += "-" + info.Request.Errors[j].Code + "<->" + info.Request.Errors[j].Code + "<br />"; ;
                        }
                        throw new Exception("AmazonService: " + mensajeError);
                    }
                }
                else
                {
                    //Label1.Text += "El request esta mal escrito";
                    throw new Exception("AmazonService: El request esta mal escrito");
                }
            }
            catch (Exception ex)
            {
                //Label1.Text = (ex.ToString());
                throw new Exception("AmazonService: " + ex.ToString());
            }

        }

    }
}
