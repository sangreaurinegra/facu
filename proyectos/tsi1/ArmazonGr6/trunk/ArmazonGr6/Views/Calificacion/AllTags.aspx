<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<IEnumerable<ArmazonGr6.Models.CloudItem>>" %>
<%@ Import Namespace="ArmazonGr6.Models"%>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	AllTags
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">
<script src="/Scripts/MicrosoftAjax.js" type="text/javascript"></script>
<script src="/Scripts/MicrosoftMvcAjax.js" type="text/javascript"></script>

    <h2>Tags en el Sistema</h2>
    <h3><label id="mensaje" style="color:Red"></label>
    </h3> 
    <div id="tags">
     <% Html.RenderPartial("ListaTags", Model); %>
    </div>        
    
</asp:Content>

