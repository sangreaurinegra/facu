<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<ArmazonGr6.Models.Articulo>" %>
<%@ Import Namespace="ArmazonGr6.Models" %>
<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	Editar
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Editar</h2>

    <%= Html.ValidationSummary("Edit was unsuccessful. Please correct the errors and try again.") %>

    <% using (Html.BeginForm()) {%>

        <fieldset>
            <legend>Datos del Articulo</legend>
            <p>
                <label for="nombre">nombre:</label>
                <%= Html.TextBox("nombre", Model.nombre) %>
                <%= Html.ValidationMessage("nombre", "*") %>
            </p>
            <p>
                <label for="idCategoria">Categoria a la que pertenece:</label>
                <%if (Model.Categoria != null)
                  {%>
                    <%= Html.DropDownList("Categoria", Model.NombreCategorias(Model), Model.Categoria.nombre)%>
                <%}
                  else
                  {%>
                    <%= Html.DropDownList("Categoria", Model.NombreCategorias(Model))%>
                <%} %>
                <%= Html.ValidationMessage("Categoria", "*") %>
            </p>
            <p>
                <label for="precio">precio:</label>
                <%= Html.TextBox("precio", String.Format("{0:F}", Model.precio)) %>
                <%= Html.ValidationMessage("precio", "*") %>
            </p>
            <p>
                <input type="submit" value="Save" />
            </p>
            <br />
            <p>
                <%=Html.ActionLink("Editar Detalles", "EditDetallesArticulo", new { id = Model.id })%>
            </p>
            <p>
                <%=Html.ActionLink("Subir Imagen", "SubirImagen", new { id = Model.id })%>
            </p>
        </fieldset>

    <% } %>

   
</asp:Content>

