<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<IEnumerable<ArmazonGr6.Models.Campo>>" %>
<%@ Import Namespace="ArmazonGr6.Models" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	EditDetallesArticulo
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Editor de Detalles de Articulo</h2>
    <fieldset>
    <% using (Html.BeginForm("EditDetallesArticulo", "Articulo")) { %>
    <table>
        <tr>
            <th>
                Campo
            </th>
            <th>
                Valor
            </th>
        </tr>

    <% foreach (var item in Model) { %>
    
        <tr>
            <td>
                <%= Html.Encode(item.nombre) %>
            </td>
            <td>
                <% PlantillaRepository pr = new PlantillaRepository();
                   int idArt = (int)ViewData["idArticulo"];
                   Registro reg = pr.GetRegistro(idArt, item.id);
                   String valor = " ";
                   if (reg != null)
                       valor = reg.valor;
           
                    if (item.tipo.Trim() == "bool") {%>
                    <%= Html.DropDownList("VALUE" + item.id, Campo.getValoresBool(valor))%>
                <% }
                   else { %>
                    <%= Html.TextBox("VALUE" + item.id, valor) %>
                <% } %>
            </td>
        </tr>
    
        <%= Html.Hidden("ID" + item.id, item.id)%>
        <%= Html.Hidden("TYPE" + item.id, item.tipo)%>
    <% } %>

    </table>
    <p>
        <%= Html.Hidden("idArticulo", ViewData["idArticulo"])%>
        <input type="submit" value="Guardar" />
    </p>
    
    <% } %>
    
    </fieldset>
    
   
</asp:Content>

