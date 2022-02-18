<%@ Control Language="C#" Inherits="System.Web.Mvc.ViewUserControl<ArmazonGr6.Controllers.CategoriaFormViewModel>" %>

    <%= Html.ValidationSummary("Por favor revise los errores y vuelva a intentar.") %>

    <% using (Html.BeginForm()) {%>

        <fieldset>
            
            <p>
                <label for="nombre">Nombre:</label>
                <%= Html.TextBox("nombre", Model.categoria.nombre) %>
                <%= Html.ValidationMessage("nombre", "*") %>
            </p>
            <p>
                <label for="idSuperCategoria">Seleccione Super Categoría:</label>
                <%= Html.DropDownList("SuperCategoria", Model.listaNombreCategorias) %>
                <%= Html.ValidationMessage("SuperCategoria", "*") %>
            </p>
            <p>
                <input type="submit" value="Guardar" />
            </p>
        </fieldset>

    <% } %>
