<%@ Page Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<System.Web.Mvc.HandleErrorInfo>" %>

<asp:Content ID="errorTitle" ContentPlaceHolderID="TitleContent" runat="server">
    Error
</asp:Content>

<asp:Content ID="errorContent" ContentPlaceHolderID="MainContent" runat="server">
    <h2 style="color:Red;">
        Lo sentimos, ha ocurrido un error.
    </h2>
    <span style="font-style:italic; font-size:large; font-weight:bold;"> Verifique el siguiente mensaje: <br /><br /></span>
    <%= Html.Encode((string)ViewData["MensajeError"]) %>
</asp:Content>
