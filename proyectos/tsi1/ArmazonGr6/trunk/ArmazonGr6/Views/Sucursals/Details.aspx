<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<ArmazonGr6.Models.Sucursal>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	Details
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Detalles</h2>

    <fieldset>
        <legend>Campos</legend>
        <p>
            id:
            <%= Html.Encode(Model.id) %>
        </p>
        <p>
            nombre:
            <%= Html.Encode(Model.nombre) %>
        </p>
        <p>
            direccion:
            <%= Html.Encode(Model.direccion) %>
        </p>
        <p>
            telefono:
            <%= Html.Encode(Model.telefono) %>
        </p>
        <p>
            ciudad:
            <%= Html.Encode(Model.ciudad) %>
        </p>
        <p>
            latitud:
            <%= Html.Encode(String.Format("{0:F}", Model.latitud)) %>
        </p>
        <p>
            longitud:
            <%= Html.Encode(String.Format("{0:F}", Model.longitud)) %>
        </p>        
    </fieldset>
    <p>

        <%=Html.ActionLink("Editar", "Edit", new { id=Model.id }) %> |
        <%=Html.ActionLink("Borrar", "Delete", new { id=Model.id }) %> 
    </p>

</asp:Content>

