<%@ Page Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage" %>
<%@ Import Namespace="ArmazonGr6.Models" %>

<asp:Content ID="indexTitle" ContentPlaceHolderID="TitleContent" runat="server">
    Home Page
</asp:Content>

<asp:Content ID="indexContent" ContentPlaceHolderID="MainContent" runat="server">
    
    <h2><%= Html.Encode(ViewData["Message"]) %></h2>    
        Virtual Store 

    <p>
        <%= Html.ActionLink("Página de Usuario", "UsuarioIndex", "Home") %>
    </p>
    <%  IEnumerable<ArticuloCompra> lista = (IEnumerable<ArticuloCompra>)ViewData["Destacados"];
        
        if (lista != null && lista.Count() > 0) {
         %>
    <p>
        <h3>Articulos destacados esta semana</h3>
    </p>
    <div>
        <div style="position:relative; float:left;">
            <div id="mygalone" class="svw">
                <ul>        
                    <% ArticuloRepository ar = new ArticuloRepository();
            
                        foreach (ArticuloCompra ac in lista) {
                            Articulo art = ar.GetArticulo(ac.idArticulo);
                            String img = "";
                            if (art.imagen != null)
                                img = art.imagen.Trim();
                            String prefijo = "";
                            if (!img.StartsWith("http"))
                                prefijo = "../../Content/ArticulosImg/"; %>
                                <!--%= Html.ActionLink("__IMAGE_PLACEHOLDER__", "Detalles", new { id=item.id }).Replace("__IMAGE_PLACEHOLDER__", "<img src=\"" + "../../Content/ArticulosImg/" + img + "\"  alt=\"No Image\"  />")%-->
                                <li><img src="<%= Html.Encode(prefijo+img)%>"  alt="<%= Html.Encode(art.nombre)%>" height="250px" width="200px" onmouseover="alertaClick_<%= art.id %>(document.getElementById('div_<%= art.id %>'))"  /></li>    
                    <% } %>
                </ul>
            </div>
        </div>
        
        <% foreach (ArticuloCompra item in lista) { %>
            <div  id="<%= Html.Encode("div_"+item.idArticulo)%>" style="position:relative; float:right; display:none; width: 200px; height:250px;">
                <h2 style="color:Blue; text-align:left;"><%= Html.Encode(item.nombre)%> </h2>
                <span style="font-style:italic;">Calificación:</span>
                <%  ArticuloRepository artRepo = new ArticuloRepository();

                    float fAve = artRepo.GetPromedioCalificaciones(item.idArticulo);
                    int ave = (int)Math.Round(fAve,0);%>

                <%  for (int i = 0; i < 5; i++) {
                        if (i < ave) {%>
                            <img src="../../Content/imagenes/star.png" alt="asdf" />
                        <%}
                        else { %>
                            <img src="../../Content/imagenes/star_transp.gif" alt="asdf" />
                <%      }
                    }     %>
                <br />
                <span style="font-style:italic; font-family:Verdana;">Vendidas: <%= Html.Encode(item.cantidad)%> unidades.</span>
                
                <p style="text-decoration:none;">
                    <h3>Precio: $ <%= Html.Encode(String.Format("{0:F}", item.precio)) %></h3>
                    <%= Html.ActionLink("Ver detalles >>", "Detalles","Articulo", new { id = item.idArticulo },null)%>
                </p>
            </div>
        <% } %>
    </div>
    <br />
    <br />
    <!-- SCRIPT QUE GENERA EL SLIDE VIEWER -->
    <script type="text/javascript">
        $(window).bind("load", function() {
            $("div#mygalone").slideView()
        }); 
    </script>
    <!-- SCRIPT QUE MUESTRA LOS DETALLES DEL ARTICULO -->
    <script type="text/javascript" >
        <% foreach (var item in lista) { %>
            function alertaClick_<%= item.idArticulo %>(elementoDiv) {
            <%  int ultimo = item.idArticulo;
                foreach (var item2 in lista) { %>
                    <% if(item2.idArticulo != ultimo) { %>    
                            $('<%= Html.Encode("div#div_"+item2.idArticulo)%>').hide();
                    <% } %>
            <% } %>
            if ($(elementoDiv).is(":hidden")) {
                $(elementoDiv).slideDown();
                }
            }
        <% } %>
            
    </script>
    <% } %>
    <br />
    <br />   
</asp:Content>

