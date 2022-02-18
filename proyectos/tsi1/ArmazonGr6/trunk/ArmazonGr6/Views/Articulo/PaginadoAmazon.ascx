<%@ Control Language="C#" Inherits="System.Web.Mvc.ViewUserControl<ArmazonGr6.Controllers.ArticuloFormViewModel>" %>

<% if (Model.getHayErrorAmazon () == true){ %>
        
            <%= Html.Encode(Model.getMsgError() ) %>
<%} else { %>

    <%if (Model.getListaAmazon().Count()>0) { %>

        <% if (Model.hasPreviousPageAm)
           { %>
                <%= Ajax.ActionLink("<<", "MostrarPagAmazon", "Articulo", new { pagActual = Model.getPagActAm() - 1, texto = Model.getTexto() }, new AjaxOptions { UpdateTargetId = "amazonId" })%>
        <%} %>
        
        <%= Html.Encode(Model.getPagActAm() )%>
        
        <% if (Model.hasNextPageAm )
           { %>
                <%= Ajax.ActionLink(">>", "MostrarPagAmazon", "Articulo", new { pagActual = Model.getPagActAm() + 1, texto = Model.getTexto() }, new AjaxOptions { UpdateTargetId = "amazonId" })%>
        <%} %>


        <table style="border-top-style:dashed;  border-bottom-style:dashed; border-left-style:none; border-right-style:none;">
                                
         <% foreach (var item in Model.getListaAmazon() ) { %>

            <tr>
                <td style="border-style:none;">
                    <%  String img = "";
                        if (item.imagen != null)
                            img = item.imagen.Trim();
                        String prefijo = "";
                        if (!img.StartsWith("http"))
                        prefijo = "../../Content/ArticulosImg/"; %>
                    <%= Html.ActionLink("__IMAGE_PLACEHOLDER__", "Detalles", new { id = item.id }).Replace("__IMAGE_PLACEHOLDER__", "<img src=\"" + prefijo + img + "\"  alt=\"No Image\"  width=\"60\" height=\"100\"  />")%>
                </td>
                <td style="border-style:none;">
                    <span style="font-family:Verdana; font-size:small; color:Black;"><%= Html.Encode(item.nombre) %> <br />
                    $<%= Html.Encode(String.Format("{0:F}", item.precio)) %> </span>
                </td>
                <%  bool esAdmin = (bool)ViewData["esAdmin"];
                if (Request.IsAuthenticated && !esAdmin) {  %>
                    <td style="border-style:none;">
                        <%= Html.DropDownList("list_AR"+item.id, ViewData["list"] as SelectList)%>    
                    </td>
                    <td style="border-style:none;">
                        <input type="button"  value="Añadir al carrito" onclick="agregarAlCarrito(<%=item.id%>, document.getElementById('list_AR<%= item.id %>').options[document.getElementById('list_AR<%= item.id %>').selectedIndex].value)" />          
                    </td>
                <% } %>

            </tr>
         <%  }%>
        </table>
                            
    <%} else { %>
        La lista devuelta por el Amazon es vacía.
    <%} %>                        
<% } %>