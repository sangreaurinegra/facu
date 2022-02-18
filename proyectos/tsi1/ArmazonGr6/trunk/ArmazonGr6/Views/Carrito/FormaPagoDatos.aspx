<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<IEnumerable<ArmazonGr6.Models.Campo>>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	FormaPagoDatos
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Datos Personales:</h2>
    <% using (Html.BeginForm("EnviarDatos","Carrito")) { %>
    
    <fieldset>
    <table>
        <tr>
            <th>
                Datos
            </th>
            <th>
                <!-- aca va el valor que ingresa el usuario -->
            </th>
            
        </tr>

    <% foreach (var item in Model) { %>
    
        <tr>
            <td>
                <%= Html.Encode(item.nombre)%>
            </td>
            <td>
            <% if (item.tipo.Trim() == "pwd") { %>
           
                <%= Html.Password(item.nombre.Trim())%>
            
           <%}
               else {%> 
               <%= Html.TextBox(item.nombre.Trim())%>
               <%}%> 
            </td>
        </tr>
    
    <% } } %>
    
    </table>
    <p>
        <input type="submit" value="Enviar datos" />
    </p>
    </fieldset>
    
    <p>
        <br />
        Paso 3 de 4 <br />
        <img src="../../Content/imagenes/estadosCarrito3.JPG" alt="sdf" 
            style="height: 44px; width: 322px" />
    </p>


</asp:Content>

