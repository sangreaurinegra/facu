<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<ArmazonGr6.Models.Plantilla>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	AddCategoria
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Asociar Categoria a la plantilla</h2>
    <% 
        using (Html.BeginForm()) {%>
    <fieldset>
        <legend>Campos</legend>
        <p>
            nombre:
            <%= Html.Encode(Model.nombre) %>
        </p>
        <p>
            nombre:
            <%= Html.DropDownList("categoria",(SelectList)ViewData["categorias"]) %>
        </p>
        <%= Html.Hidden("idPlantilla",Model.id) %>
        <p>
            <input type="submit" value="Asociar" />
        </p>
        </fieldset>

    <% } %>
    

</asp:Content>

