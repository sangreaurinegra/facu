<%@ Page Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage" %>

<asp:Content ID="indexTitle" ContentPlaceHolderID="TitleContent" runat="server">
    Home Page
</asp:Content>

<asp:Content ID="indexContent" ContentPlaceHolderID="MainContent" runat="server">
    <h2><%= Html.Encode(ViewData["Message"]) %></h2>
    <p>
        <%= Html.ActionLink("getProduct", "getProduct")%>
    </p>
    <p>
        <%= Html.ActionLink("search", "search")%>
    </p>
    <p>
        <%= Html.ActionLink("getRatings", "getRatings")%>
    </p>
    <p>
        <%= Html.ActionLink("CartBuy", "CartBuy")%>
    </p>
</asp:Content>
