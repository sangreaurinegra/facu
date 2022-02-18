<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<IEnumerable<ArmazonGr6.Models.Carrito>>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	historicoComprasUsuario
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Historial de Compras</h2>

    <table>
        <tr>
            <th></th>
            <th>
                Fecha de Compra
            </th>
            <th>
                Importe Total
            </th>            
        </tr>

    <% foreach (var item in Model) { %>
    
        <tr>
            <td>
                <%= Html.ActionLink("Detalles", "detallesCarritoHistorico", new { idCarrito = item.id })%>
            </td>
            <td>
                <%= Html.Encode(String.Format("{0:g}", item.fechaCompra)) %>
            </td>
            <td>
                <%= Html.Encode(String.Format("{0:F}", item.montoTotal)) %>
            </td>            
        </tr>
    
    <% } %>

    </table>
</asp:Content>

