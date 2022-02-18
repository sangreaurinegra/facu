<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<IEnumerable<ArmazonGr6.Models.Campo>>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	CamposSummary
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Campos de la plantilla: <%= Html.Encode(ViewData["nombrePlant"])%></h2>

    <table>
        <tr>
            <th></th>
            <th>
                nombre
            </th>
            <th>
                tipo
            </th>
        </tr>

    <% foreach (var item in Model) { %>
    
        <tr>
            <td>
                <%= Html.ActionLink("Editar", "Edit", new { id=item.id }) %> || 
                <%= Html.ActionLink("Borrar", "DeleteCampo", new { id = item.id })%>
            </td>
            <td>
                <%= Html.Encode(item.nombre) %>
            </td>
            <td>
                <%= Html.Encode(item.tipo) %>
            </td>
        </tr>
    
    <% } %>

    </table>
            
    <p>
        <%= Html.ActionLink("Crear Nuevo", "CreateCampo", new { idPlantilla = ViewData["idPlantilla"] })%>
    </p>

</asp:Content>

