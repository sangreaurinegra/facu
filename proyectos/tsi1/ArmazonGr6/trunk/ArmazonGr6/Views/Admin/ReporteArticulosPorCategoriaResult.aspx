<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<IEnumerable<ArmazonGr6.Models.ArticuloCompra>>" %>
<%@ Import Namespace="ArmazonGr6.Models"%>
<%@ Import Namespace="System.Collections"%>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	ReporteArticulosPorCategoriaResult
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">


    <h2>Reporte de articulos por categoria</h2>

    <table>
        <tr>
            <th>
                Categoria
            </th>
            <th>
                Nombre
            </th>
            <th>
                Cantidad
            </th>
        </tr>

    <%  IDictionary<String, ArticuloCompra> result = (IDictionary<String, ArticuloCompra>)ViewData["result"];
        var lista = result.Keys;
        foreach (String item in lista) { %>
    
        <tr>
            <td>
               <%= Html.Encode(item) %> 
            </td>
            <td>
                <%= Html.Encode(result[item].nombre) %>
            </td>
            <td>
                <%= Html.Encode(result[item].cantidad)%>
            </td>
        </tr>
    
    <% } %>

    </table>
    <br />
    <br />
</asp:Content>

