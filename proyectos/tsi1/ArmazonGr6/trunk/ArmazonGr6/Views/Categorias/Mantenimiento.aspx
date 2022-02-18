<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<IEnumerable<ArmazonGr6.Models.Categoria>>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	Mantenimiento
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Mantenimiento</h2>

    <table>
        <tr>
            <th></th>
            <th>
                Nombre
            </th>
            <th>
                Super Categoria
            </th>
          
        </tr>

    <% foreach (var item in Model) { %>
    
        <tr>
            <td>
                <%= Html.ActionLink("Editar", "Editar", new { id=item.id }) %> |
                <%= Html.ActionLink("Detalles", "Detalles", new { id=item.id })%>
            </td>
            <td>
                <%= Html.Encode(item.nombre) %>
            </td>
            <td>
                <% if(item.Categoria1 != null){ %>
                <%= Html.Encode(item.Categoria1.nombre) %>
                <% } else { %>
                 Inicial
                <% } %>
            </td>
           
        </tr>
    
    <% } %>

    </table>

    <p>
        <%= Html.ActionLink("Crear Nuevo", "Crear") %>
    </p>

</asp:Content>

