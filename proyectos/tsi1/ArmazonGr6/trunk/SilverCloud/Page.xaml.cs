using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.ServiceModel;
using System.Windows;
using System.Windows.Browser;
using System.Windows.Controls;
using System.Windows.Controls.Primitives;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Shapes;
using SilverCloud.ServiceReferenceSilver;

namespace SilverCloud
{
    public partial class Page : UserControl
    {
        private ServiceSilverClient proxy;
        IList<Etiqueta> tags = null;

        private String host;
        private int port;

        Style StyleAux;

        public Page()
        {
            InitializeComponent();
           
        }


        private void UserControl_Loaded(object sender, RoutedEventArgs e)
        {
           host = HtmlPage.Document.DocumentUri.Host; 
           port = HtmlPage.Document.DocumentUri.Port;

           proxy = new ServiceSilverClient("BasicHttpBinding_ServiceSilver", "http://" + host + ":" + port + "/ServiceSilver.svc");
            proxy.GetCloudItemsCompleted += (cliente_ObtenerTagsCompleted); // new EventHandler<GetCloudItemsCompletedEventArgs>
            proxy.GetCloudItemsAsync();
        }

        void cliente_ObtenerTagsCompleted(object sender, GetCloudItemsCompletedEventArgs e)
         {

             tags = null;
                 if (!e.Cancelled)
                     tags = e.Result;
                 else
                     return;

            RedoLayout();


         }

        private void RedoLayout()
        {
            LayoutRoot.Children.Clear();

            double minWeight = tags.Min((cloudItem => cloudItem.Tamanio));
            double maxWeight = tags.Max((cloudItem => cloudItem.Tamanio));
            
            IEnumerable<TextBlock> textBlocks = (from cloudItem in tags
                                                 select
                                                     ConvertToTextBlock(cloudItem, minWeight, maxWeight));
            foreach (TextBlock textBlock in textBlocks)
            {
                textBlock.MouseLeftButtonDown +=
                    (sender, mouseButtonEventArgs) => OnCloudItemClicked(sender as TextBlock);

                textBlock.MouseEnter +=
                    (sender, mouseEventArgs) => OnMouseEnter(sender as TextBlock);

                textBlock.MouseLeave +=
                    (sender, mouseEventArgs) => OnMouseLeave(sender as TextBlock);

                LayoutRoot.Children.Add(textBlock);
              
            }
        }

        public TextBlock ConvertToTextBlock(Etiqueta cloudItem, double minWeight, double maxWeight)
        {
            
            TextBlock ret = new TextBlock
                        {
                          Text = cloudItem.Nombre,
                          Style =Application.Current.Resources
                    [DetermineResourceForWeight(cloudItem, minWeight,   maxWeight)] as Style,
                          Tag = cloudItem,
                          Margin = new Thickness(3)
                        };
      
            return ret;
        }

       public string DetermineResourceForWeight(Etiqueta cloudItem, double minWeight, double maxWeight)
        {
            double distribution = (maxWeight - minWeight) / 3;
            if (cloudItem.Tamanio == minWeight)
                return "CloudTagStyleSmallest";
            if (cloudItem.Tamanio == maxWeight)
                return "CloudTagStyleLargest";
            if (cloudItem.Tamanio > (minWeight + (distribution * 2)))
                return "CloudTagStyleLarge";
            if (cloudItem.Tamanio > (minWeight + (distribution)))
                return "CloudTagStyleMedium";
            return "CloudTagStyleSmall";
        }


       private void OnCloudItemClicked(FrameworkElement clickedTextBlock)
       {
           TextBlock tb = (TextBlock)clickedTextBlock;
           HtmlPage.Window.Navigate(new Uri("http://" + host +":"+port+"/Articulo.mvc/Etiqueta/" + tb.Text));
       }


       private void OnMouseEnter(FrameworkElement onTextBlock)
       {
           /*TextBlock tb = (TextBlock)onTextBlock;
           StyleAux = tb.Style;
           tb.Style = Application.Current.Resources["CloudTagStyleOnMouse"] as Style;
           LayoutRoot.Children.Add(tb);*/
       }

       private void OnMouseLeave(FrameworkElement leaveTextBlock)
       {

           /*TextBlock tb = (TextBlock)leaveTextBlock;
           Style = StyleAux;
           LayoutRoot.Children.Add(tb);*/
       }
    }
}
