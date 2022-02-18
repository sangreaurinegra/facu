<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<IEnumerable<ArmazonGr6.Models.ReporteResultItem>>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	ReporteArticulosMejorCalifResult
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Reporte de los articulos mejor calificados</h2>

    <table>
        <tr>
            <th>
                Categoria
            </th>
            <th>
                Articulo
            </th>
            <th>
                Calificacion
            </th>
        </tr>

    <% foreach (var item in Model) { %>
    
        <tr>
            
            <td>
                <%= Html.Encode(item.nombreCategoria) %>
            </td>
            <td>
                <%= Html.Encode(item.nombreArticulo) %>
            </td>
            <td>
                <%for (int i = 0; i < 5; i++) {
                      if (i < item.calificacion) {%>
                        <img src="../../Content/imagenes/star.png" alt="asdf" />
                    <%}
                      else { %>
                        <img src="../../Content/imagenes/star_transp.gif" alt="asdf" />
                <%   }
                  }%>
            </td>
        </tr>
    
    <% } %>

    </table>

    
    <br />
    <br />
</asp:Content>

