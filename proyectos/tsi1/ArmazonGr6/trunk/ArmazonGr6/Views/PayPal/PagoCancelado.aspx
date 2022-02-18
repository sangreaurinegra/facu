<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	PagoCancelado
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Compra cancelada</h2>
    <p>
    Los articulos seleccionados para comprar estaran disponibles en el carrito de compras.
    </p>
    <p>
        <%= Html.ActionLink("Volver al carrito de compras","ArticulosPorCarrito","Carrito") %>
        <br />
        <%= Html.ActionLink("Menú prinpcipal","Main","Cliente") %>
    </p>
</asp:Content>
