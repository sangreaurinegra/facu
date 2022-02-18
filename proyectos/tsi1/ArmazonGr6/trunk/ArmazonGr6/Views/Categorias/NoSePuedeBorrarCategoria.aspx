<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<ArmazonGr6.Models.Categoria>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	No se pudo borrar la Categoria
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>No se pudo borrar la Categoria</h2>
    <label>Alguna subcategoria o la misma categoria que s quiere eliminar tiene al menos un articulo asociado aun.</label>
    <div><p><%=Html.ActionLink("Lista de categorías", "Index") %></p></div>
    

</asp:Content>
