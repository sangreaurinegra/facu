<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<ArmazonGr6.Models.CloudItem>" %>
<%@ Import Namespace="ArmazonGr6.Models" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	Crear Tag
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Tags</h2>

    <%= Html.ValidationSummary("Create was unsuccessful. Please correct the errors and try again.") %>

    <% using (Html.BeginForm()) {%>

        <fieldset>
            <legend>Campos</legend>
            <p>
                <label for="name">Nombre:</label>
                <%= Html.TextBox("name") %>
                <%= Html.ValidationMessage("name", "*") %>
            </p>
            <p>
                <input type="submit" value="Create" />
            </p>
        </fieldset>
        <div style="border-width:1px; border-style:solid;">
        <p>
            <h4 style="color:Blue;"> Otros tags del articulo</h4> <br />
            <% IEnumerable<CloudItem> tags = (IEnumerable<CloudItem>)ViewData["tags"];
               foreach (CloudItem c in tags) { 
                %>
                <%= Html.ActionLink(c.name, "AumentarTag", new { name = c.name, idArticulo = ViewData["idArticulo"]})%>
                <%= Html.Encode("  -  ")%>
            <% } %>
        </p>
        </div>
        <%= Html.Hidden("idArticulo", ViewData["idArticulo"])%>
    <% } %>

    

</asp:Content>

