<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<IEnumerable<ArmazonGr6.Models.Calificacion>>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	Pruebas
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Pruebas</h2>

    <table>
        <tr>
            <th></th>
            <th>
                id
            </th>
            <th>
                puntuacion
            </th>
            <th>
                revision
            </th>
            <th>
                idCliente
            </th>
            <th>
                idArticulo
            </th>
        </tr>

    <% foreach (var item in Model) { %>
    
        <tr>
            <td>
                <%= Html.ActionLink("Edit", "Edit", new { id=item.id }) %> |
                <%= Html.ActionLink("Details", "Details", new { id=item.id })%>
            </td>
            <td>
                <%= Html.Encode(item.id) %>
            </td>
            <td>
                <%= Html.Encode(item.puntuacion) %>
            </td>
            <td>
                <%= Html.Encode(item.revision) %>
            </td>
            <td>
                <%= Html.Encode(item.idCliente) %>
            </td>
            <td>
                <%= Html.Encode(item.idArticulo) %>
            </td>
        </tr>
    
    <% } %>

    </table>

    <p>
        <%= Html.ActionLink("Create New", "Create") %>
    </p>

</asp:Content>

