<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	ReporteVentas
	
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Reporte ventas en un período</h2>
    <form method="post" action="/Admin.mvc/ReporteVentasResult">
    
    <p>
        Seleccione las fechas:
    </p>
    <fieldset>
     <table style="border-style:none;">
        <tr>
        <td style="border-style:none;">
        Fecha Desde: 
        </td>
        <td style="border-style:none;"><input id="fechaDesde" type="text" name="fechaDesde" /></td>
        </tr>
        <tr>
        <td style="border-style:none;">Fecha Hasta: 
        </td> 
        <td style="border-style:none;"><input id="fechaHasta" type="text" name="fechaHasta" /></td>
        </tr>          
    </table>
    <input type="submit" name="Buscar" />
    </fieldset>
    </form>
    
    <link type="text/css" href="../../Scripts/ui.datepicker.css" rel="stylesheet" />

    <script type="text/javascript" src="../../Scripts/ui.datepicker.js"></script>

    <script type="text/javascript">
        
        $(function() {
        $("#fechaDesde").datepicker({ dateFormat: 'dd/mm/yy' });
        });

        $(function() {
        $("#fechaHasta").datepicker({ dateFormat: 'dd/mm/yy' });
        });
	</script>
	
</asp:Content>
