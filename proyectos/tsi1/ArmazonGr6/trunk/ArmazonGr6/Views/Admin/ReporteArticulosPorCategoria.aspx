<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	ReporteArticulosPorCategoria
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Reporte de Articulos Por Categoria</h2>
    <form method="post" action="/Admin.mvc/ReporteArticulosPorCategoriaResult">
    
    <p>
        Consultar:
    </p>
    
     <p>
        <!--Fecha Desde: <input type="text" name="fechaDesde" />
         <br />
        Fecha Hasta: <input type="text" name="fechaHasta" />
        <br /-->
        <input type="submit" name="Buscar" />
    </p>
    
    </form>
</asp:Content>
