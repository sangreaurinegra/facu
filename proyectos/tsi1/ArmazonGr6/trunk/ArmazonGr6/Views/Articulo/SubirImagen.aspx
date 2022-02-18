<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<ArmazonGr6.Models.Articulo>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
	SubirImagen
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">

    <h2>Subir Imagen</h2>

    <%= Html.ValidationSummary("Edit was unsuccessful. Please correct the errors and try again.") %>

    <!--% using (Html.BeginForm()) {%-->
    <form action="/Articulo.mvc/SubirImagen/" method="post" enctype="multipart/form-data">

        <fieldset>
            <legend>Fields</legend>
            
            <p>
                <label for="nombre">nombre:</label>
                <%= Html.Encode(Model.nombre) %>
            </p>
            <p> 
                <label for="nombre">Imágen actual:</label>
                <% String img = "";
                   if (Model.imagen != null)
                       img = Model.imagen.Trim();
                   String prefijo = "";
                   if (!img.StartsWith("http"))
                       prefijo = "../../Content/ArticulosImg/"; 
                    %>
                <%= Html.Encode("__IMAGE_PLACEHOLDER__").Replace("__IMAGE_PLACEHOLDER__", "<img src=\"" + prefijo + img + "\"  alt=\"No Image\"  />")%>
            </p>
            <p>
                <label for="imagen">Imagen:</label>
                <input type="file" id="imagen" name="imagen" />
            </p>
            <p>
                <input type="submit" value="Save" />
            </p>
        </fieldset>
        <%= Html.Hidden("idArticulo", Model.id) %>
    <!--% } %-->
    </form>

    <div>
        <%=Html.ActionLink("Volver a Editar", "Editar", new { id = Model.id })%>
    </div>

</asp:Content>

