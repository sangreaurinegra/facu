<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<IEnumerable<ArmazonGr6.Models.Articulo>>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	PlanillaArt
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Datos del articulo</h2>

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
        </tr>

    <% foreach (var item in Model) { %>
    
        <tr>
            <td>
                <%= Html.ActionLink("Editar", "Edit", new { id=item.id }) %> |
                <%= Html.ActionLink("Detalles", "Details", new { id=item.id })%>
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
             
        </tr>
    
    <% } %>

    </table>
    

    <p>
        <%= Html.ActionLink("Crear Nuevo", "Create") %>
    </p>

</asp:Content>

