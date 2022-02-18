<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<IEnumerable<ArmazonGr6.Models.Articulo>>" %>
<%@ Import Namespace="ArmazonGr6.Models" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	Página Principal del cliente
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Página Principal del Cliente</h2>
    <br />
    <h2 style="color:#ee7844;">Sugerencias:</h2>
    <div>
        <div style="position:relative; float:left;">
            <div id="mygalone" class="svw">
                <ul>        
                    <% foreach (var item in Model) { %>
                        
                             <%  String img = "";
                                    if (item.imagen != null)
                                        img = item.imagen.Trim(); %>
                                <!--%= Html.ActionLink("__IMAGE_PLACEHOLDER__", "Detalles", new { id=item.id }).Replace("__IMAGE_PLACEHOLDER__", "<img src=\"" + "../../Content/ArticulosImg/" + img + "\"  alt=\"No Image\"  />")%-->
                                <li><img src="../../Content/ArticulosImg/<%= Html.Encode(img)%>"  alt="<%= Html.Encode(item.nombre)%>" height="250px" width="200px" onmouseover="alertaClick_<%= item.id %>(document.getElementById('div_<%= item.id %>'))"  /></li>    
                    <% } %>
                </ul>
            </div>
        </div>
        
        <% foreach (var item in Model) { %>
            <div  id="<%= Html.Encode("div_"+item.id)%>" style="position:relative; float:right; display:none; width: 200px; height:250px;">
                <h2 style="color:Blue; text-align:left;"><%= Html.Encode(item.nombre)%> </h2>
                <span style="font-style:italic;">Calificación:</span>
                <%  ArticuloRepository artRepo = new ArticuloRepository();

                    float fAve = artRepo.GetPromedioCalificaciones(item.id);
                    int ave = (int)Math.Round(fAve,0);%>

                <%  for (int i = 0; i < 5; i++) {
                        if (i < ave) {%>
                            <img src="../../Content/imagenes/star.png" alt="asdf" />
                        <%}
                        else { %>
                            <img src="../../Content/imagenes/star_transp.gif" alt="asdf" />
                <%      }
                    }     %>

                <p style="text-decoration:none;">
                    <h3>Precio: $ <%= Html.Encode(String.Format("{0:F}", item.precio)) %></h3>
                    <%= Html.ActionLink("Ver detalles >>", "Detalles", new { id = item.id }) %>
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
        <% foreach (var item in Model) { %>
            function alertaClick_<%= item.id %>(elementoDiv) {
            <%  int ultimo = item.id;
                foreach (var item2 in Model) { %>
                    <% if(item2.id != ultimo) { %>    
                            $('<%= Html.Encode("div#div_"+item2.id)%>').hide();
                    <% } %>
            <% } %>
            if ($(elementoDiv).is(":hidden")) {
                $(elementoDiv).slideDown();
                }
            }
        <% } %>
            
    </script>
</asp:Content>
