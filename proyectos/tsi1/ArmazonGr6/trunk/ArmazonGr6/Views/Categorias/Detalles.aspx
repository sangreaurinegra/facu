<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<ArmazonGr6.Models.Categoria>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	Categoría: <%= Html.Encode(Model.nombre) %>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Detalle de la Categoria</h2>

        <p>
            <strong>Id: </strong>
            <%= Html.Encode(Model.id) %>
        </p>
        <p>
            <strong>Nombre: </strong>
            <%= Html.Encode(Model.nombre) %>
        </p>
        <p>
            <strong>Super Categoría: </strong>
             <% if (Model.Categoria1 != null){ %>
                <%= Html.Encode(Model.Categoria1.nombre)%>
                <% } else { %>
                 Inicial
                <% } %>
           
        </p>
    
        <% Html.RenderPartial("ArticuloStatus"); %>
        <% Html.RenderPartial("LinksEditarYBorrar"); %>
        
</asp:Content>