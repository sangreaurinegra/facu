<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<IEnumerable<ArmazonGr6.Models.ArtCant>>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	ConfirmarCompra
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Confirmar Compra</h2>

    <table>
        <tr>
            <th></th>
            <th>
                idCarrito
            </th>
            <th>
                idArticulo
            </th>
            <th>
                cantidad
            </th>
            <th>
                id
            </th>
        </tr>

    <% foreach (var item in Model) { %>
    
        <tr>
            <td>
                <%= Html.ActionLink("Editar", "Edit", new { id=item.id }) %> |
                <%= Html.ActionLink("Detalles", "Details", new { id=item.id })%>
            </td>
            <td>
                <%= Html.Encode(item.idCarrito) %>
            </td>
            <td>
                <%= Html.Encode(item.idArticulo) %>
            </td>
            <td>
                <%= Html.Encode(item.cantidad) %>
            </td>
            <td>
                <%= Html.Encode(item.id) %>
            </td>
        </tr>
    
    <% } %>

    </table>

    <p>
        <%= Html.ActionLink("Crear Nuevo", "Create") %>
    </p>

</asp:Content>

