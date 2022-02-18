<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<ArmazonGr6.Models.Sucursal>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	EditConfirm
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Confirmacion</h2>

    <%= Html.ValidationSummary("Edit was unsuccessful. Please correct the errors and try again.") %>

    <% using (Html.BeginForm()) {%>

        <fieldset>
            <legend>Campos</legend>
            <p>
                <label for="nombre">nombre:</label>
                <%= Html.TextBox("nombre", Model.nombre) %>
                <%= Html.ValidationMessage("nombre", "*") %>
            </p>
            <p>
                <label for="direccion">direccion:</label>
                <%= Html.TextBox("direccion", Model.direccion) %>
                <%= Html.ValidationMessage("direccion", "*") %>
            </p>
            <p>
                <label for="telefono">telefono:</label>
                <%= Html.TextBox("telefono", Model.telefono) %>
                <%= Html.ValidationMessage("telefono", "*") %>
            </p>
            <p>
                <label for="ciudad">ciudad:</label>
                <%= Html.TextBox("ciudad", Model.ciudad) %>
                <%= Html.ValidationMessage("ciudad", "*") %>
            </p>
            <p>
                <label for="latitud">latitud:</label>
                <%= Html.TextBox("latitud", String.Format("{0:F}", Model.latitud)) %>
                <%= Html.ValidationMessage("latitud", "*") %>
            </p>
            <p>
                <label for="longitud">longitud:</label>
                <%= Html.TextBox("longitud", String.Format("{0:F}", Model.longitud)) %>
                <%= Html.ValidationMessage("longitud", "*") %>
            </p>
            <p>
                <input type="submit" value="Save" />
            </p>
        </fieldset>

    <% } %>

   
</asp:Content>

