<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<ArmazonGr6.Models.Categoria>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	Confirmación de Borrado: <%=Html.Encode(Model.nombre) %>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Confirmación de Borrado: <%=Html.Encode(Model.nombre) %></h2>
    
    <p>Desea eliminar la caregoría <i><%=Html.Encode(Model.nombre) %></i>?</p>
    
    <%using (Html.BeginForm())
      { %>
        <input name="confirmButton" type="submit" value="Borrar" />
    <%} %>

</asp:Content>
