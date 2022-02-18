<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<IEnumerable<ArmazonGr6.Models.Articulo>>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	ArticuloSinCategoria
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Articulos Sin Categoria</h2>

    <table style="text-align:justify; border-style:none;">
        <tr>
            <th>Acción</th>
            <th></th>
            <th></th>
        </tr>

    <% foreach (var item in Model) { %>
    
        <tr>
            <td style="text-align:justify; border-style:none;">
                <%= Html.ActionLink("Editar", "Editar", new { id=item.id }) %> 
            </td>
            <td style="text-align:justify; border-style:none;">
                <%  String img = "";
                    if (item.imagen != null)
                        img = item.imagen.Trim();
                    String prefijo = "";
                    if (!img.StartsWith("http"))
                        prefijo = "../../Content/ArticulosImg/";
                %>
                <%= Html.ActionLink("__IMAGE_PLACEHOLDER__", "Detalles", new { id = item.id }).Replace("__IMAGE_PLACEHOLDER__", "<img src=\"" + prefijo + img + "\"  alt=\"No Image\" width=\"60\" height=\"100\" />")%>
                
            </td>
            <td style="text-align:justify; border-style:none;">
                <span style="font-family:Verdana; font-size:smaller; color:Black;"><%= Html.Encode(item.nombre) %>
                <br />
                $<%= Html.Encode(String.Format("{0:F}", item.precio)) %>
                </span>
            </td>
            
        </tr>
    
    <% } %>

    </table>
</asp:Content>

