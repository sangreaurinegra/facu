<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<ArmazonGr6.Models.Calificacion>" %>
<%@ Import Namespace="ArmazonGr6.Models"%>
<%@ Import Namespace="System.Collections"%>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	Create
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Crear</h2>

    <%= Html.ValidationSummary("Create was unsuccessful. Please correct the errors and try again.") %>

    <% 
        using (Html.BeginForm()) {%>

        <fieldset>
            <legend>Campos</legend>
            <p>
                <label for="puntuacion">puntuacion:</label>
                <%= Html.DropDownList("puntuacion", Calificacion.getListaPuntuaciones()) %>*
                <%= Html.ValidationMessage("puntuacion", "*") %>
            </p>
            <p>
                <label for="revision">revision:</label>
                <%= Html.TextBox("revision") %>
                <%= Html.ValidationMessage("revision", "*") %>
            </p>
            <%= Html.Hidden("idArticulo",ViewData["Articulo"]) %>
            <p>
                <input type="submit" value="Calificar" />
            </p>
        </fieldset>

    <% } %>

   
</asp:Content>

