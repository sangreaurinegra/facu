<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<IEnumerable<ArmazonGr6.Models.Articulo>>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	PruebaSearch
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>PruebaSearch</h2>

    <table>
        <tr>
            <th></th>
            <th>
                IsValid
            </th>
            <th>
                id
            </th>
            <th>
                nombre
            </th>
            <th>
                idCategoria
            </th>
            <th>
                precio
            </th>
            <th>
                imagen
            </th>
        </tr>

    <% foreach (var item in Model) { %>
    
        <tr>
            <td>
                <%= Html.ActionLink("Edit", "Edit", new { id=item.id }) %> |
                <%= Html.ActionLink("Details", "Details", new { id=item.id })%>
            </td>
            <td>
                <%= Html.Encode(item.IsValid) %>
            </td>
            <td>
                <%= Html.Encode(item.id) %>
            </td>
            <td>
                <%= Html.Encode(item.nombre) %>
            </td>
            <td>
                <%= Html.Encode(item.idCategoria) %>
            </td>
            <td>
                <%= Html.Encode(String.Format("{0:F}", item.precio)) %>
            </td>
            <td>
                <%= Html.Encode(item.imagen) %>
            </td>
        </tr>
    
    <% } %>

    </table>

    <p>
        <%= Html.ActionLink("Create New", "Create") %>
    </p>

</asp:Content>

