<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<IEnumerable<ArmazonGr6.Models.Articulo>>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	Mantenimiento
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Mantenimiento de articulos</h2>
    <br />
    <br />
    <p>
        <%= Html.ActionLink("Crear Nuevo", "Crear") %>
    </p>

    <table>
        <tr>
            <th></th>
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
        </tr>

    <% foreach (var item in Model) { %>
    
        <tr>
            <td>
                <%= Html.ActionLink("Editar", "Editar", new { id=item.id }) %> |
                <%= Html.ActionLink("Detalles", "Detalles", new { id=item.id })%>
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
        </tr>
    
    <% } %>

    </table>

    <p>
        <%= Html.ActionLink("Crear Nuevo", "Crear") %>
    </p>    

</asp:Content>

