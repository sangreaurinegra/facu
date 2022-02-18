<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<ArmazonGr6.Controllers.AmazonFormViewModel>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	Index
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <script src="/Scripts/MicrosoftAjax.js" type="text/javascript"></script>
<script src="/Scripts/MicrosoftMvcAjax.js" type="text/javascript"></script>
    
    <h2>Index</h2>
    
    <div id="buscador">
        
        <% using (Html.BeginForm()){%>
            <fieldset>

                <p>
                    
                    <%= Html.TextBox("textoAObtener") %>
                </p>
                <p>
                        <input type="submit" value="Buscar" />
                </p>
        
            </fieldset>
         <% } %>
    
    </div>
    
    <div id="idArticulosAmazon">
        <% if (Model.getHayError() == true)
           { %>
        
            <%= Html.Encode(Model.getMsgError() ) %>
        <%}
           else
           { %>
                <% if (Model.getLista() != null)
                   { %>
                    <table>
                        <tr>            
                            <th>
                                
                            </th>
                            <th>
                                ASIN
                            </th>
                            <th>
                                Nombre
                            </th>
                            <th>
                                Precio
                            </th>
                                        
                        </tr>
                        <% foreach (var articulo in Model.getLista())
                           {
                           %>
                            
                            <tr>
                                <td>
                                    <%if (articulo.Imagen != null)
                                      { %>
                                    <%= Html.ActionLink("__IMAGE_PLACEHOLDER__", "Products").Replace("__IMAGE_PLACEHOLDER__", "<img src=\"" + articulo.Imagen + "\" />")%>
                                    <%} %>

                                </td>
                                <td>
                                    <%= Html.Encode(articulo.Asin)%>
                                </td>
                                <td>
                                    <%= Html.Encode(articulo.Titulo)%>
                                </td>
                                <td>
                                    <%if (articulo.Amount != null)
                                      { %>
                                        <%= Html.Encode(articulo.Amount)%>
                                    <%} %>
                                </td>
                            </tr>
                        <%  }%>
               
                    </table>
                <%}
                   else
                   {%>
                           <h2>Lista vacía</h2> 
                <%} %>
        <%} %>
        
        <!--%= Ajax.ActionLink("<<", "PaginaAnterior", "PaginacionAmazon", new AjaxOptions { UpdateTargetId = "idArticulosAmazon" })%-->
        
      </div>
      
      <%= Ajax.ActionLink("<<", "PaginaAnterior", "Amazon", new AjaxOptions { UpdateTargetId = "idArticulosAmazon" })%>        
      <%= Ajax.ActionLink(">>", "PaginaSiguiente", "Amazon", new AjaxOptions { UpdateTargetId = "idArticulosAmazon" })%>        

</asp:Content>
