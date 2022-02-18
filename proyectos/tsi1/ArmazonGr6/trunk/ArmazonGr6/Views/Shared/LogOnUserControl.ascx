<%@ Control Language="C#" Inherits="System.Web.Mvc.ViewUserControl" %>
<%
    if (Request.IsAuthenticated) {
%>
        Bienvenido <b><%= Html.Encode(Page.User.Identity.Name) %></b>!
        [ <%= Html.ActionLink("__IMAGEN__", "LogOff", "Account").Replace("__IMAGEN__", "<img src=\"../../Content/imagenes/door_out.png\" alt\"nada\"> Log Off") %> ]
<%
    }
    else {
%> 
        [ <%= Html.ActionLink("__IMAGEN__", "LogOn", "Account").Replace("__IMAGEN__", "<img src=\"../../Content/imagenes/door_in.png\" alt\"nada\"> Log On")%> ]
<%
    }
%>
