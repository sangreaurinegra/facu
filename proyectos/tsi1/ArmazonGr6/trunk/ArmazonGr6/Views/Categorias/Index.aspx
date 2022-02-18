<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<ArmazonGr6.Controllers.CategoriaIndexViewModel>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	Las Categorías



</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

<script src="/Scripts/MicrosoftAjax.js" type="text/javascript"></script>
<script src="/Scripts/MicrosoftMvcAjax.js" type="text/javascript"></script>

    <h2>Categorías/Articulos</h2>
    <h3><label id="mensaje" style="color:Red"></label></h3>
    <div id="rutaCategorias">
        
         <%= Html.ActionLink("Inicio", "Index")%> 

        
        <%foreach (var cat in Model.getRutaCategorias()){ %>
        
            --> <%= Html.ActionLink(cat.nombre, "Index", new { idCatPadre = cat.id })%>
        
        <%} %>
    
    </div>
    
    <table >
        <tr>
            <td style="vertical-align:top;">
                <div id="categoriasDiv" style="padding-left:-10px;">
                    <ul style="margin-left:-28px;">
                        <%foreach (var categoria in Model.getListaCategoriasAMostrar()){ %>                  
                            <li><%= Html.ActionLink(categoria.nombre, "Index", new { idCatPadre = categoria.id })%></li>
                        <%} %>
                    </ul>
                </div>
            </td>
            <td>
    <div id="paginasId">
       
        <div id="articulosId">
            
            <% Html.RenderPartial("ArticulosPaginados", Model ); %>

        </div>
        
    </div>
    <br />
    <br />
    <div id="Articulos a proponer">
    
        <span style="font-family:Arial; font-size:large; font-style:italic; color:Green;"> Destacados: <br /></span>
        
        <table style="border-top-style:dashed;  border-bottom-style:none; border-left-style:none; border-right-style:none;">
            <% foreach (var item in Model.getListaArtAProponer())
                 {
               %>
                        
              <tr>
                <td style="border-style:none;">
                    <%  String img = "";
                        if (item.imagen != null)
                            img = item.imagen.Trim();                    
                    %>
                    <%= Html.ActionLink("__IMAGE_PLACEHOLDER__", "../Articulo/Detalles", new { id = item.id }).Replace("__IMAGE_PLACEHOLDER__", "<img src=\"" + "../../Content/ArticulosImg/" + img + "\"  alt=\"No Image\"  width=\"60\" height=\"100\"  />")%>
                </td>
                <td style="border-style:none;">
                    <span style="font-family:Verdana; font-size:smaller; color:Black;">
                    <%= Html.Encode(item.nombre) %> <br />
                    $<%= Html.Encode(String.Format("{0:F}", item.precio)) %> </span>
                </td>
                <%  bool esAdmin = (bool)ViewData["esAdmin"];
                if (Request.IsAuthenticated && !esAdmin) {  %>
                    <td style="border-style:none;">
                      <%= Html.DropDownList("list_"+item.id, ViewData["list"] as SelectList)%>    
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
                        { idArt: ida, cantidad: cant }, agregado);

            }

            function agregado(data) {
                document.getElementById("mensaje").innerHTML = "El articulo " + data + " se agrego con exito.";
            }
        </script>
    
    </div>
    
    </td>
    </tr>
    </table>        

    
</asp:Content>


