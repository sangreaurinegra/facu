<%@ Control Language="C#" Inherits="System.Web.Mvc.ViewUserControl<IEnumerable<ArmazonGr6.Models.CloudItem>>" %>

<table>
        <tr>
            <th></th>
            <th>
                Nombre
            </th>
            <th>
                Peso
            </th>
        </tr>

    <% foreach (var item in Model) { %>
    
        <tr>
            <td style="border-style:hidden; text-align:right;">            
                <!--%= Html.ActionLink("Eliminar", "DeleteTag", new { id = item.id }, new { onclick = "return confirm('Realmente desea eliminar el tag "+item.name+" ?')" })%-->                
                <%= Ajax.ActionLink("Eliminar", "DeleteTag", "Calificacion", new { id = item.id }, new AjaxOptions
                { Confirm = "Realmente desea eliminar el tag " + item.name + " ?",HttpMethod = "POST",
                    UpdateTargetId = "tags",OnSuccess = "myCallback"})%>
            </td>
            <td>
                <%= Html.Encode(item.name) %>
            </td>
            <td>
                <%= Html.Encode(item.weight) %>
            </td>
        </tr>
    
    <% } %>

    </table>
    <script type="text/javascript">
        function myCallback() {
            document.getElementById("mensaje").innerHTML = "La etiqueta se ha eliminado con èxito.";
        }
    </script>
    
    