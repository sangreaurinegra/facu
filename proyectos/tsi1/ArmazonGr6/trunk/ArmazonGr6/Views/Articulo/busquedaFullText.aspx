<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	busquedaFullText
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Resultados:</h2>
    <form method="post" action="/Articulo.mvc/buscarFullText">
    
    <input type="text" name="texto" />
    <p>
       <input type="submit" value="buscar" />
    </p>
    </form>
    <asp:Panel ID="Panel1" runat="server">
        <script src="../../Scripts/jquery-1.3.2.js" type="text/javascript"></script>

        <!--script type="text/javascript">
            function buscar(texto) {
                $.getJSON(
                    '<%= Url.Action("/buscarFullText") %>',
                    {texto:texto},
                    function(data) {
                        
                    });        
            }
            
        </script-->
               
    </asp:Panel>

</asp:Content>
