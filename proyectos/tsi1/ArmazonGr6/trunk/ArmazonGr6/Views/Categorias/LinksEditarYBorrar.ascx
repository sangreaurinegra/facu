<%@ Control Language="C#" Inherits="System.Web.Mvc.ViewUserControl<ArmazonGr6.Models.Categoria>" %>

<%if (Request.IsAuthenticated)
      { %>
        <p>

            <%=Html.ActionLink("Editar Categor�a", "Editar", new { id = Model.id })%> |
            <%=Html.ActionLink("Borrar Categor�a", "Borrar", new { id = Model.id })%>
        </p>
    <%} else{ %>
        <a href="/Account/Logon">Log On</a> para editar y borrar categor�as.
    <%} %>