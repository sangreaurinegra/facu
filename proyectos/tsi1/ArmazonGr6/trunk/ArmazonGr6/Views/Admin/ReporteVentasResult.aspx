<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<LinkedList<ArmazonGr6.Models.ArticuloCompra>>" %>
<%@ Import Namespace="ArmazonGr6.Models"%>
<%@ Import Namespace="System.Collections"%>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	ReporteVentas
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Reporte de ventas en un período.</h2>
    <form id="form1" runat="server">

    <table>
        <tr>            
            <th>
                Nombre
            </th>
            <th>
                Cantidad
            </th>
            <th>
                Precio unit.
            </th>
            <th>
                Subtotal $
            </th>
            
        </tr>

    <%  double total = 0;
        foreach (ArticuloCompra item in Model) {
            total += item.precio * item.cantidad; %>
    
        <tr>
            <td>
                <%= Html.Encode(item.nombre) %>
            </td>
            <td>
                <%= Html.Encode(item.cantidad) %>             
            </td>
            <td>
                <%= Html.Encode(item.precio) %>
            </td>
            <td>
                <%= Html.Encode(item.precio * item.cantidad) %>                
            </td>
        </tr>
    
    <% } %>

    </table>
    <br />
    <table style="float=right">
        <tr>
            <th>TOTAL</th>
            <th>$</th>
        </tr>
        <tr>
            <td>TOTAL:</td>
            <td><%= Html.Encode(total) %></td>
        </tr>
    </table>
    </form>
    <br />
    <br />
</asp:Content>

