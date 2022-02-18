<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<IEnumerable<ArmazonGr6.Models.Sucursal>>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	Sucursales
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">
    
    <form id="form1" runat="server">
    
    <h2>Sucursales</h2>
    <% bool si = false;
       try {
           int idCarrito = (int)HttpContext.Current.Session["idHistorico"];
           si = idCarrito != 0;
       }
       catch { } %>
    <table>
        <tr>
            <% if (Request.IsAuthenticated && si) {  %>
                <th></th>
            <% } %>
            <th>
                nombre
            </th>
            <th>
                direccion
            </th>
            <th>
                ciudad
            </th>
            <th>
                Centrar
            </th>           
        </tr>

    <% foreach (var item in Model) { %>
    
        <tr>
            
            <% // si esta logeado y tiene compra pendiente
                // si tengo carro historico es que estoy terminando la compra                    
                if (Request.IsAuthenticated && si) {  %>
                    <td>
                        <%= Html.ActionLink("Seleccionar", "FinCompraExitosa", "Carrito")%>
                    </td>
            <% } %>
            <td>
                <span style="font-family:Verdana; font-size:smaller; "><%= Html.Encode(item.nombre) %></span>
            </td>
            <td>
                <span style="font-family:Verdana; font-size:smaller; "><%= Html.Encode(item.direccion) %></span>
            </td>
            <td>
                <span style="font-family:Verdana; font-size:smaller; "><%= Html.Encode(item.ciudad) %></span>
            </td>     
            <td>
                <input type="button"  value="Centrar mapa" onclick="centrarSucursal(<%=item.id%>)" />
            </td>   
        </tr>
    
    <% } %>

    </table>    

    <p>
        
    </p>

    <asp:Panel ID="Panel1" runat="server">
        <script src="http://maps.google.com/maps?file=api&v=2&sensor=false
            &key=<%=Html.Encode(ViewData["GoogleKey"])%>"
            type="text/javascript">
        </script>
        
        <script src="../../Scripts/jquery-1.3.2.js" type="text/javascript"></script>

        <script type="text/javascript">
            var map;
            
            function createMarker(point, nomb, direc, telef, ciudad) {
                //var icon1 = new GIcon(G_DEFAULT_ICON, "http://www.google.com/mapfiles/markerA.png");
                var icon1 = new GIcon(G_DEFAULT_ICON, "../../Content/imagenes/libro2.gif");
                markerOptions = { icon: icon1 };
                var marker = new GMarker(point, markerOptions);
    
                GEvent.addListener(marker, "click", function() {
                    marker.openInfoWindowHtml("<b>" + nomb + "</b><br>" +
                               " Direccion: " + direc + "<br> Telefono: " + telef + "<br> Ciudad: " + ciudad);
                });
                map.addOverlay(marker);
                return marker;
            }

            function initialize() {
                if (GBrowserIsCompatible()) {

                    map = new GMap2(document.getElementById("mapa_sucu"));
                    //el segundo valor es el valor del acercamiento, de 1 a 15 masomenos;
                    //cuanto mayor es el valor , mas cerca se ve.
                    //Ahora esta centrado en montevideo.
                    var centro = new GLatLng(-32.50, -56.0)
                    map.setCenter(centro,6);
                    map.setUIToDefault();

                    $.getJSON(
                        '<%= Url.Action("/GetElements") %>',
                        {},
                        function(data) {
                            $.each(data, function(i, item) {

                                //Parseo el string
                                var datos = item.split('|');
                                var idSucu = datos[0];
                                var nombre = datos[1];
                                var direccion = datos[2];
                                var telefono = datos[3];
                                var ciudad = datos[4];

                                var latlng = new GLatLng(datos[5], datos[6]);
                                createMarker(latlng, nombre, direccion, telefono, ciudad);
                            });
                        });
                    
                                       
                }               
            }
            function centrarSucursal(ids) {
                $.getJSON('<%= Url.Action("/GetSucursalJSON") %>',
                        {idsu : ids} , function(data) {                            
                            //Parseo el string
                            var datos = data.split('|');
                            var idSucu = datos[0];
                            var nombre = datos[1];
                            var direccion = datos[2];
                            var telefono = datos[3];
                            var ciudad = datos[4];

                            var latlng = new GLatLng(datos[5], datos[6]);
                            var marcador = createMarker(latlng, nombre, direccion, telefono, ciudad);
                            map.setCenter(latlng, 12);       
                        });
            }


    </script>
        
    <body onload="initialize()" onunload="GUnload()">
        <div id="mapa_sucu" style="width: 400px; height: 350px"></div>
    </body>
    
    </asp:Panel>
    </form>

</asp:Content>
