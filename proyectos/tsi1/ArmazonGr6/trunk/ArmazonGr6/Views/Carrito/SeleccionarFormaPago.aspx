<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<IEnumerable<ArmazonGr6.Models.FormaPago>>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	SeleccionarFormaPago
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Seleccione la Forma de Pago</h2>

    <table>
        <tr>
            <th></th>
            <th>
                nombre
            </th>
            <th><!-- esta columna es para las imagenes de los entes de pago -->
            </th>
        </tr>

    <% foreach (var item in Model) { %>
        <% if (item.id < 2) { %>
        <tr>
            <td>
                <%= Html.ActionLink("Seleccionar", "FormaPagoDatos", new { id = item.id })%> 
            </td>
            <td>
                <%= Html.Encode(item.nombre)%>
            </td>
            <td>
                <img src="
                <%= Html.Encode(item.imgUrl) %>
                " alt="asdfdf" style="height: 60px; width: 65px" />
            </td>
        </tr>
    
    <%  }
       } %>

    </table>

    <p>
        <br />
        Paso 2 de 4 <br />
        <img src="../../Content/imagenes/estadosCarrito2.JPG" alt="sdf" 
            style="height: 44px; width: 322px" />
    </p>
    <form method="post" action="/PayPal.mvc/PagoCancelado">
    <p>
       <input type="submit" value="Cancelar compra" />
    </p>
    </form>
    
</asp:Content>

