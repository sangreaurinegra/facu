<%@ Control Language="C#" Inherits="System.Web.Mvc.ViewUserControl" %>
<li><%= Html.ActionLink("__IMAGEN__", "Main", "Cliente").Replace("__IMAGEN__", "<img src=\"../../Content/imagenes/image.png\" alt\"nada\"> Página Principal")%></li>
<li><%= Html.ActionLink("__IMAGEN__", "ArticulosPorCarrito", "Carrito").Replace("__IMAGEN__", "<img src=\"../../Content/imagenes/cart.png\" alt\"nada\"> Carrito de compras")%></li>
<li><%= Html.ActionLink("__IMAGEN__", "historicoComprasUsuario", "Carrito").Replace("__IMAGEN__", "<img src=\"../../Content/imagenes/box.png\" alt\"nada\"> Historial de compras")%></li>
<li><%= Html.ActionLink("__IMAGEN__", "IndexCliente", "Sucursals").Replace("__IMAGEN__", "<img src=\"../../Content/imagenes/house.png\" alt\"nada\"> Sucursales")%></li>
