<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<PaginatedList<ArmazonGr6.Models.Articulo>>" %>
<%@ Import Namespace="CommunicationServer.com.amazon.webservices"%>
<%@ Import Namespace="ArmazonGr6.Helpers"%>
<%@ Import Namespace="ArmazonGr6.Models"%>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	ListaArticulo
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Lista de Articulos</h2>
    <h3><label id="mensaje" style="color:Red"></label></h3>       
    
 <% if (Model.hasPreviousPage) { %>
 
        <%= Html.RouteLink("<<<", (string)ViewData["nombreInvocador"], new { page = (Model.PageIndex - 1) })%>

        <% } %>
        <%= Html.Encode("  Páginas  ") %>
        <% if (Model.hasNextPage) {  %>
         
        <%= Html.RouteLink(">>>", (string)ViewData["nombreInvocador"], new { page = (Model.PageIndex + 1) })%>

     <% } %>  

    <table style="border-style:none;">
        
    <% foreach (var item in Model) { %>
    
        <tr>
            <td style="text-align:justify; border-style:none;">
                <%  String img = "";
                    if (item.imagen != null)
                        img = item.imagen.Trim();
                    String prefijo = "";
                    if (!img.StartsWith("http"))
                        prefijo = "../../Content/ArticulosImg/";
                %>
                <%= Html.ActionLink("__IMAGE_PLACEHOLDER__", "Detalles", new { id = item.id }).Replace("__IMAGE_PLACEHOLDER__", "<img src=\"" + prefijo + img + "\"  alt=\"No Image\" width=\"60\" height=\"100\" />")%>
                 
            </td>
            <td style="border-style:none;">
                <span style="font-family:Verdana; font-size:smaller; color:Black;"> <%= Html.Encode(item.nombre) %><br />
                <%= Html.Encode(" $ ") %>
                <%= Html.Encode(String.Format("{0:F}", item.precio)) %></span>
            </td>

            <% if (Request.IsAuthenticated) {
                   UsuarioRepository ur = new UsuarioRepository();
                   bool esAdmin = ur.esAdmin(Page.User.Identity.Name);
                   if (!esAdmin) {%>
                        <td style="border-style:none;">
                            <%= Html.DropDownList("list_" + item.id, ViewData["list"] as SelectList)%>    
                        </td>
                        <td style="border-style:none;">
                            <input type="button"  value="Añadir al carrito" onclick="agregarAlCarrito(<%=item.id%>, document.getElementById('list_<%= item.id %>').options[document.getElementById('list_<%= item.id %>').selectedIndex].value)" />          
                        </td>
            <%     }
               }%>
        </tr>
    
    <% } %>

    </table>
    
    
        
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

