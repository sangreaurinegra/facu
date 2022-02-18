<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<ArmazonGr6.Models.Articulo>" %>
<%@ Import Namespace="ArmazonGr6.Models" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	Detalles
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2><%= Html.Encode(Model.nombre) %></h2>
    <%  ArticuloRepository artRepo = new ArticuloRepository();
        int ave = 0;
        if (Model.Externo == null) {
            float fAve = artRepo.GetPromedioCalificaciones(Model.id);
            ave = (int)Math.Round(fAve, 0);
        }
        else if (Model.Calificacions != null && Model.Calificacions.Count > 0){
            double fAve = Model.Calificacions.Average(a => a.puntuacion);
            ave = (int)Math.Round(fAve, 0);
        }
            %>
        
    <%  for (int i = 0; i < 5; i++) {
            if (i < ave) {%>
                <img src="../../Content/imagenes/star.png" alt="asdf" />
            <%}
            else { %>
                <img src="../../Content/imagenes/star_transp.gif" alt="asdf" />
    <%      }
        }     %>

    <fieldset style="border-color:black; border-width:thin;">
        <div>            
            <table style="border-style:hidden;">
                <tr> 
                    <td style="border-style:hidden;">       
                        <p>
                            <%  String img = "";
                                if (Model.imagen != null)
                                    img = Model.imagen.Trim();
                                String prefijo = "";
                                if (!img.StartsWith("http"))
                                    prefijo = "../../Content/ArticulosImg/";
                            %>

                            <img  id="<%= Html.Encode("img_"+Model.id) %>" src="<%  
                            if (Model.imagen != null)
                                img = Model.imagen.Trim(); %>
                            <%= Html.Encode(prefijo + img) %>
                            " alt="No Image" style="border-color:Silver; border-style:solid; border-width:medium;" width="150px" height="220px"/>

                            
                        </p>
                    </td>
                    <td>
                        <% foreach (var reg in Model.Registros) { %>
                            
                          <span style="font-weight:bold; color:Blue;"> <%= Html.Encode(reg.Campo.nombre) %>:</span>
                            
                             <% if (reg.Campo.tipo.Trim() == "bool") {%>
                            
                                   <%if (reg.valor.Trim() == "No"){%>
                                         <img src="<%= Html.Encode("../../Content/imagenes/cross.png") %>" alt="No Image"/>
                                   <% } else {%>
                                        <img src="<%= Html.Encode("../../Content/imagenes/tick.png") %>" alt="No Image"/>
                                    <% } %>
                                
                                <% } else { %>
                                  
                                     <%= Html.Encode(reg.valor) %>
                                <% } %>
                            <br/>
                            
                        <% } %>
                    </td>
                </tr>  
            </table>
        </div>
        <table style="border-style:hidden; width:400px;">
            <tr> 
                <td style="border-style:hidden;">
                    <h3>Precio: $ <%= Html.Encode(String.Format("{0:F}", Model.precio)) %> </h3>
                </td>
                <td style="border-style:hidden; text-align:right;">
                    <% if (Request.IsAuthenticated) {  %>
                        <h3>
                            <%=Html.ActionLink("Calificar", "Create", "Calificacion",new { idArticulo = Model.id },null)%>
                            <%=Html.Encode("  -  ")%>
                            <%=Html.ActionLink("Taggear", "CreateTag", "Calificacion",new { idArticulo = Model.id },null)%>
                        </h3>
                    <% } %>
                </td>
            </tr>
            <%  bool esAdmin = (bool)ViewData["esAdmin"];
                if (Request.IsAuthenticated && !esAdmin) {  %>
                <tr>
                    <td style="border-style:hidden; text-align:right; width:300px;">
                      <%= Html.DropDownList("list_"+Model.id, ViewData["list"] as SelectList)%>    
                    </td>
                    <td style="border-style:hidden; text-align:right;">
                        <input type="button"  value="Añadir al carrito" onclick="agregarAlCarrito(<%=Model.id%>, document.getElementById('list_<%= Model.id %>').options[document.getElementById('list_<%= Model.id %>').selectedIndex].value)" />          
                    </td>
                </tr>
            <% } %>  
        </table> 
        <h3><label id="mensaje" style="color:Red"></label></h3>  
        <label id="verl" >Ver Revisiones <img  id="ver" src="../../Content/imagenes/resultset_next.png" alt="" /></label>
        <div id="revisiones" style="display:none;">
            <table style="width:400px;">
                
                <% foreach (var calif in Model.Calificacions) { %>
                    <tr> 
                        <td >
                            <% if (calif.Cliente != null) { %>
                                <span style="font-weight:bold; color:Blue;"> Usuario : </span>
                                 <%= Html.Encode(calif.Cliente.Usuario.login)%>
                                <br />
                            <% } %>
                            <span style="font-weight:bold; color:Blue;">Calificación : </span>
                            <%for (int i = 0; i < 5; i++) {
                                if (i < calif.puntuacion) {%>
                                    <img src="../../Content/imagenes/star.png" alt="asdf" />
                                <%}
                                else { %>
                                    <img src="../../Content/imagenes/star_transp.gif" alt="asdf" />
                                <%   }
                             }%>
                            <br />
                            <span style="font-weight:bold; color:Blue;">Revisión : </span>
                            <p style="text-align:justify;">
                                <%= Html.Encode(calif.revision) %>
                            </p>
                        </td>
                </tr>
                    
                <% } %>
                      
            </table>
            <label id="ocultarl" > <img id="ocultar" src="../../Content/imagenes/resultset_previous.png" alt="" /> Ocultar Revisiones</label>                
        </div>
    </fieldset> 
    
    <div id="jHelperTipContainer">
    </div>
    <% if (Model.Externo == null) {%>
        <div id="tip1" style="background-color:#eeeeee; border-width:1px; border-style:dotted;">
            Total calificaciones de usuarios:<br />
            <% ArticuloRepository ar = new ArticuloRepository();
               Dictionary<int, int> califs = ar.GetCalificacionesPorUsuario(Model.id);
               int total = 0;
               for (int key = 1; key <= 5; key++) {
                   int puntos = califs.SingleOrDefault(c => c.Key == key).Value;
                   total += puntos;
               %>
                <%= Html.Encode("  " + puntos + ": ")%>          
              <%    for (int i = 0; i < 5; i++) {
                        if (i < key) {%>
                            <img src="../../Content/imagenes/star.png" alt="asdf" />
                        <%}
                        else { %>
                            <img src="../../Content/imagenes/star_transp.gif" alt="asdf" />
                        <%   }
                    }%>
              <br />
              <% }   
                 %>
            TOTAL: <%= Html.Encode(total)%>            
        </div>
    <% } %>
    <!-- SCRIPT QUE MUESTRA EL TOOLTIP CON LOS USUARIOS QUE CALIFICARON -->
    <script type="text/javascript">
        $(function() {
            $('#<%= Html.Encode("img_"+Model.id) %>').jHelperTip({
                trigger: "hover",
                dC: "#tip1",
                autoClose: false,
                opacity: 0.9
            });
                        
        });

    </script>
    <!-- SCRIPT QUE MUESTRA Y OCULTA REVISIONES -->
    <script type="text/javascript">
      $(document).ready(function(){

        $("#verl").click(function() {
            if ($("#revisiones").is(":hidden")) {
                $("#revisiones").slideDown("slow");
            } else {
                $("#revisiones").hide("slow");
            }
        });

        $("#ocultar").click(function() {
            if ($("#revisiones").is(":hidden")) {
                $("#revisiones").slideDown("slow");
            } else {
                $("#revisiones").hide("slow");
            }
        });

        $("#ocultarl").click(function() {
            if ($("#revisiones").is(":hidden")) {
                $("#revisiones").slideDown("slow");
            } else {
                $("#revisiones").hide("slow");
            }
        });

      });
    </script>
    <!-- SCRIPT QUE AGREGA AL CARRITO -->
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

