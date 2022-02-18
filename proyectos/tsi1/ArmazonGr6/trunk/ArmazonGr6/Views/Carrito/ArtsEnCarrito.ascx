<%@ Control Language="C#" Inherits="System.Web.Mvc.ViewUserControl<IEnumerable<ArmazonGr6.Models.ArticuloCompra>>" %>
<%@ Import Namespace="ArmazonGr6.Models"%>

    

    <table>
        <tr>            
            <th>
                Nombre
            </th>
            <th>
                Precio unit.
            </th>
            <th>
                Cant.
            </th>
            <th>
                SubTotal
            </th>
            <th>
                Comprar ahora!
            </th>
            <th></th>
        </tr>

    <% foreach (ArticuloCompra item in Model) { %>
    
        <tr>
            <td>
                <%= Html.Encode(item.nombre) %>
            </td>
            <td>
                <%= Html.Encode(String.Format("{0:F}", item.precio))%>
            </td>
            <td>
                <%= Html.Encode(item.cantidad) %>             
            </td>
            <td>
                <%= Html.Encode(String.Format("{0:F}", item.precio * item.cantidad))%>                
            </td>
            <td>
                <input type="checkbox" value="True" onclick="comprarAhora(<%=item.idArticulo%>,<%=item.cantidad%>)" />               
            </td>
            
            <td>
                <!--%= Html.ActionLink("Quitar del carrito", "QuitarArticuloDelCarrito", new { idArt = item.idArticulo })%-->
                <%= Ajax.ActionLink("Quitar!", "QuitarArticuloDelCarrito", "Carrito", new { idArt = item.idArticulo }, new AjaxOptions { UpdateTargetId = "idArtsCarrito" })%>  
            </td>
        </tr>
    
    <% } %>

    </table>