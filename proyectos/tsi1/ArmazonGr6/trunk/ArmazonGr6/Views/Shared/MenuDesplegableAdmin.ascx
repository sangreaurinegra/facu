<%@ Control Language="C#" Inherits="System.Web.Mvc.ViewUserControl" %>
<li><%= Html.ActionLink("__IMAGEN__", "AdminMain", "Admin").Replace("__IMAGEN__", "<img src=\"../../Content/imagenes/image.png\" alt\"nada\"> Página Principal")%></li>
Mantenimientos
<li><%= Html.ActionLink("__IMAGEN__", "AllTags", "Calificacion").Replace("__IMAGEN__", "<img src=\"../../Content/imagenes/wand.png\" alt\"nada\"> Listado de Tags")%></li>
<li><%= Html.ActionLink("__IMAGEN__", "Index", "Sucursals").Replace("__IMAGEN__", "<img src=\"../../Content/imagenes/wand.png\" alt\"nada\"> ABM de Sucursales")%></li>
<li><%= Html.ActionLink("__IMAGEN__", "Mantenimiento", "Categorias").Replace("__IMAGEN__", "<img src=\"../../Content/imagenes/wand.png\" alt\"nada\"> ABM de Categorias")%></li>
<li><%= Html.ActionLink("__IMAGEN__", "Mantenimiento", "Articulo").Replace("__IMAGEN__", "<img src=\"../../Content/imagenes/wand.png\" alt\"nada\"> ABM de Articulos")%></li>
<li><%= Html.ActionLink("__IMAGEN__", "Index", "Plantilla").Replace("__IMAGEN__", "<img src=\"../../Content/imagenes/wand.png\" alt\"nada\"> ABM de Plantillas")%></li>
<li><%= Html.ActionLink("__IMAGEN__", "MantProveedores", "Admin").Replace("__IMAGEN__", "<img src=\"../../Content/imagenes/wand.png\" alt\"nada\"> ABM de Proveedores")%></li>
<li><%= Html.ActionLink("__IMAGEN__", "ArticuloSinCategoria", "Articulo").Replace("__IMAGEN__", "<img src=\"../../Content/imagenes/wand.png\" alt\"nada\"> Ver Articulos SIN Categoria")%></li>
Reportes
<li><%= Html.ActionLink("__IMAGEN__", "ReporteVentas", "Admin").Replace("__IMAGEN__", "<img src=\"../../Content/imagenes/coins.png\" alt\"nada\"> Ventas en un período.")%></li>
<li><%= Html.ActionLink("__IMAGEN__", "ReporteArticulosPorCategoriaResult", "Admin").Replace("__IMAGEN__", "<img src=\"../../Content/imagenes/chart_bar.png\" alt\"nada\"> Articulos mas vendidos/Categoría.")%></li>
<li><%= Html.ActionLink("__IMAGEN__", "ReporteArticulosMejorCalifResult", "Admin").Replace("__IMAGEN__", "<img src=\"../../Content/imagenes/award_star_gold_1.png\" alt\"nada\"> Articulos mejor calificados/Categoría.")%></li>


