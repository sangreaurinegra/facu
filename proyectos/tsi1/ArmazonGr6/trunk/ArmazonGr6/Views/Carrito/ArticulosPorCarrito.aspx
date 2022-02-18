<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<IEnumerable<ArmazonGr6.Models.ArticuloCompra>>" %>
<%@ Import Namespace="ArmazonGr6.Models"%>


<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	ArticulosPorCarrito
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <script src="/Scripts/MicrosoftAjax.js" type="text/javascript"></script>
    <script src="/Scripts/MicrosoftMvcAjax.js" type="text/javascript"></script>
    
    <h2>Artículos en el Carrito</h2>
    
    <div id="idArtsCarrito">
        <% Html.RenderPartial("ArtsEnCarrito", Model); %>
    </div>
    
    <script src="<%=Url.Content("~/Scripts/jquery-1.3.2.js") %>" type="text/javascript"></script>

    <script type="text/javascript">
        function comprarAhora(ida, cant) {
            $.getJSON('<%= Url.Action("/ComprarAhora") %>',

                        { idArt: ida, cantidad: cant }, function(data) {
                            //No hago nada
                            
                        });
                    }

            function confirmarCompra() {
                
                $.getJSON('<%= Url.Action("/ConfirmarCompra") %>',
                {}, function(data) {
                    //No hago nada                                                           
                });
            }                    
    </script>

    <p>
        <% if (Model.Count() > 0) {%>
            <%= Html.ActionLink("__IMG__", "ConfirmarCompra", "Carrito").Replace("__IMG__", "<img src=\"../../Content/imagenes/money.png\" alt=\"\" />Iniciar Pago")%>
        <% }
           else {%>
            <span style="font-family:Verdana; font-style:italic; color:Green;"> <%= Html.Encode("El carrito está vacío")%>  </span>
        <%} %>
    </p>

   
</asp:Content>

