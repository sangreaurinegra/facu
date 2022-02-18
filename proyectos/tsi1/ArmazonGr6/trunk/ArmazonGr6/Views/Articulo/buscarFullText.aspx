<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<ArmazonGr6.Controllers.ArticuloFormViewModel>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	Busqueda de artículos
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

<script src="/Scripts/MicrosoftAjax.js" type="text/javascript"></script>
<script src="/Scripts/MicrosoftMvcAjax.js" type="text/javascript"></script>

    <h2>Resultados:</h2>
    <h3><label id="mensaje" style="color:Red"></label></h3>
    <% if (Model.getHayErrorInesperado() == true){ %>
        
            <%= Html.Encode(Model.getMsgError() ) %>
    <%} else { %>
            
            <div id="paginasArmazonId">
              
                    <div id="fulltextId">
                        
                        <% Html.RenderPartial("PaginadoFullText", Model); %>

                    </div>
                
            </div>
            <br />
            <br />
            
            
            
            <!-- Amazon ------------------------------------------->
            
            <br/>
            <br/>
            <span style="font-family:Verdana; font-size:large; color:Green;">PROPUESTAS DE AMAZON</span>
            <div id="idArticulosAmazon">
                <% if (Model.getHayErrorAmazon() == true)
                   { %>
                        
                            Estamos teniendo problemas en la conexión con AMAZON
                <%}
                   else
                   { %>            
                                 
            
                       <div id="amazonId">
                            
                            <% Html.RenderPartial("PaginadoAmazon", Model); %>

                       </div>
                 <%} %>
            </div>
            
            <!-- Otro Armazon -->
            <br />
            <br />
            <span style="font-family:Verdana; font-size:large; color:#222255;">PROPUESTAS DE ARMAZON</span>
            <div id="idArtOtroArmazon">
                <% if (Model.getHayErrorOtroAr() == true)
                 { %>
                        
                            Estamos teniendo problemas en la conexión con otro ARMAZON
                <%}
                   else
                   { %>
                            <div id="otroArId">
                                
                                <% Html.RenderPartial("PaginadoOtroAr", Model); %>

                            </div>
                <%} %> 
            </div>
            

     <%} %>
     
     
      <script src="../../Scripts/jquery-1.3.2.js" type="text/javascript"></script>

        <script type="text/javascript">
            function agregarAlCarrito(ida, cant) {

                $.getJSON('<%= Url.Action("../Carrito/AgregarAlCarrito") %>',
                        { idArt: ida, cantidad: cant }, agregado);

            }

            function agregado(data) {
                document.getElementById("mensaje").innerHTML = "El articulo " + data + " se agrego con exito.";
            }
    </script>
</asp:Content>

