<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	Index
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Index</h2>

    <form method="post" action="https://api-3t.sandbox.paypal.com/nvp">
        <input type="hidden" name="USER" value="maxi_4_1241897001_biz_api1.yahoo.com.ar"/>
        <input type="hidden" name="PWD" value="1241897008"/>
        <input type="hidden" name="SIGNATURE" value="AFcWxV21C7fd0v3bYYYRCpSSRl31ArvbIHRhL0QMCfIYZGeySDfdrnxp"/>
        <input type="hidden" name="VERSION" value="2.3"/>
        <input type="hidden" name="PAYMENTACTION" value="Authorization"/>
        <input name="AMT" value="19.95"/>
        <input type="hidden" name="RETURNURL" value="http://www.YourReturnURL.com"/>
        <input type="hidden" name="CANCELURL" value="http://www.YourCancelURL.com"/>
        <input type="submit" name="METHOD" value="SetExpressCheckout"/>
    </form>

</asp:Content>
