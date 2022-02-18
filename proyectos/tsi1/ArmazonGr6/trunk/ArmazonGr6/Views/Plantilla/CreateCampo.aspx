<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<ArmazonGr6.Models.Campo>" %>
<%@ Import Namespace="ArmazonGr6.Models"%>
<%@ Import Namespace="System.Collections"%>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	CreateCampo
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Crear Campo</h2>

    <%= Html.ValidationSummary("Create was unsuccessful. Please correct the errors and try again.") %>

    <% using (Html.BeginForm()) {%>

        <fieldset>
            <legend>Campo</legend>
            <p>
                <label for="nombre">nombre:</label>
                <%= Html.TextBox("nombre") %>
                <%= Html.ValidationMessage("nombre", "*") %>
            </p>
            <p>
                <label for="tipo">tipo:</label>
                <%= Html.DropDownList("tipo", Campo.getListaValores()) %>*
                <%= Html.ValidationMessage("tipo", "*") %>
            </p>
            <p>
                <input type="submit" value="Create" />
            </p>
        </fieldset>
        <%= Html.Hidden("idPlantilla",ViewData["idPlantilla"])%>
    <% } %>
    

</asp:Content>

