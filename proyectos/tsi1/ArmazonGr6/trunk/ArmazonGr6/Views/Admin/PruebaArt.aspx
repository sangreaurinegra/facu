<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<ArmazonGr6.Models.Articulo>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	PruebaArt
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>PruebaArt</h2>

    <fieldset>
        <legend>Fields</legend>
        <p>
            IsValid:
            <%= Html.Encode(Model.IsValid) %>
        </p>
        <p>
            id:
            <%= Html.Encode(Model.id) %>
        </p>
        <p>
            nombre:
            <%= Html.Encode(Model.nombre) %>
        </p>
        <p>
            idCategoria:
            <%= Html.Encode(Model.idCategoria) %>
        </p>
        <p>
            precio:
            <%= Html.Encode(String.Format("{0:F}", Model.precio)) %>
        </p>
        <p>
            imagen:
            <img src="<%= Html.Encode(Model.imagen) %>" alt="sdf" />
        </p>
    </fieldset>
    <p>

        <%=Html.ActionLink("Edit", "Edit", new { id=Model.id }) %> |
        <%=Html.ActionLink("Back to List", "Index") %>
    </p>

</asp:Content>

