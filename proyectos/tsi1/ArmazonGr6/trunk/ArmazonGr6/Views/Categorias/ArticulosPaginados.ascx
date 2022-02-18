<%@ Control Language="C#" Inherits="System.Web.Mvc.ViewUserControl<ArmazonGr6.Controllers.CategoriaIndexViewModel>" %>

<% if (Model.getListaArticulosAMostrar().hasPreviousPage)
           { %>
                <%= Ajax.ActionLink("<<", "MostrarPagina", "Paginar", new { pagActual = Model.getPaginaActual() - 1, idCatPadre = Model.getIdCategoriaPadre() }, new AjaxOptions { UpdateTargetId = "articulosId" })%>
        <%} %>
        
        <%= Html.Encode(Model.getPaginaActual() )%>
        
        <% if (Model.getListaArticulosAMostrar().hasNextPage)
           { %>
                <%= Ajax.ActionLink(">>", "MostrarPagina", "Paginar", new { pagActual = Model.getPaginaActual() + 1, idCatPadre = Model.getIdCategoriaPadre() }, new AjaxOptions { UpdateTargetId = "articulosId" })%>
        <%} %>


        <table style="border-top-style:dashed;  border-bottom-style:dashed; border-left-style:none; border-right-style:none;">
            <% foreach (var item in Model.getListaArticulosAMostrar())
                 {
               %>
                        
              <tr>
                <td style="border-style:none;">
                    <%  String img = "";
                        if (item.imagen != null)
                            img = item.imagen.Trim();
                        String prefijo = "";
                        if (!img.StartsWith("http"))
                            prefijo = "../../Content/ArticulosImg/";                    
                    %>
                    <%= Html.ActionLink("__IMAGE_PLACEHOLDER__", "../Articulo/Detalles", new { id = item.id }).Replace("__IMAGE_PLACEHOLDER__", "<img src=\"" + "../../Content/ArticulosImg/" + prefijo + img + "\"  alt=\"No Image\" width=\"60\" height=\"100\"  />")%>
                </td>
                <td style="border-style:none;">
                    <span style="font-family:Verdana; font-size:smaller; color:Black;"><%= Html.Encode(item.nombre) %> <br />
                    $<%= Html.Encode(String.Format("{0:F}", item.precio)) %> </span>
                </td>
                <%  bool esAdmin = (bool)ViewData["esAdmin"];
                    if (Request.IsAuthenticated && !esAdmin) {  %>
                    <td style="border-style:none;">
                      <%= Html.DropDownList("list_" + item.id, ViewData["list"] as SelectList)%>    
                    </td>
                    <td style="border-style:none;">
                        <input type="button"  value="Añadir al carrito" onclick="agregarAlCarrito(<%=item.id%>, document.getElementById('list_<%= item.id %>').options[document.getElementById('list_<%= item.id %>').selectedIndex].value)" />          
                    </td>       
                <% } %>
            </tr>
        <%  } %>
            
          </table>
          
          
          <script src="../../Scripts/jquery-1.3.2.js" type="text/javascript"></script>

        <script type="text/javascript">
            function agregarAlCarrito(ida, cant) {
                $.getJSON('<%= Url.Action("../Carrito/AgregarAlCarrito") %>',
                        { idArt: ida, cantidad: cant }, function(data) {
                            //No hago nada
                        });
                alert('El articulo se agrego con exito.');

            }
        </script>  