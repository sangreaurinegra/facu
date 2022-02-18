<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<ArmazonGr6.Models.Carrito>" %>
<%@ Import Namespace="ArmazonGr6.Models"%>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	detallesCarritoHistorico
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

   <h2>Historico de compra</h2>

    <fieldset>
        <legend>Detalles de la compra</legend>
        <p><b>
            Fecha de la Compra:</b>
            <%= Html.Encode(String.Format("{0:g}", Model.fechaCompra)) %>
            
        </p>
        <h3><p>
            Importe Total: $
            <%= Html.Encode(String.Format("{0:F}", Model.montoTotal)) %>
        </p></h3>
        
        <h4>Detalle de articulos</h4>
    <table>
        <tr>
            <th>
                nombre
            </th>
            <th>
                precio unit.
            </th>
            <th>
                cantidad
            </th>
            <th>
                precio total
            </th>
        </tr>

    <% foreach (var item in (ViewData["listHistoricos"] as List<ArticuloCompra>))
       { %>
    
        <tr>
           <td>
                <%= Html.Encode(item.nombre) %>
            </td>
            <td>
                <%= Html.Encode(item.precio) %>
            </td>
            <td>
                <%= Html.Encode(item.cantidad) %>             
            </td>
            <td>
                <%= Html.Encode(item.precio * item.cantidad) %>                
            </td>                        
        </tr>
    
    <% } %>

    </table>
    
    </fieldset>
    
    
</asp:Content>

