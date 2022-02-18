<%@ Control Language="C#" Inherits="System.Web.Mvc.ViewUserControl<ArmazonGr6.Models.Categoria>" %>

<script src="/Scripts/MicrosoftAjax.js" type="text/javascript"></script>
<script src="/Scripts/MicrosoftMvcAjax.js" type="text/javascript"></script>

    <script type="text/javascript">
        function AnimarMensajeArticulo() {
            $("#productomsg").animate({ fontSize: "1.5em" }, 400);
        }
    </script>
    <!--
    <div id="articulomsg">
        <%if (Request.IsAuthenticated)
          { %>
            <%=Ajax.ActionLink("Probar Ajax",
                                "Asociar",
                                "Articulo", new { id = Model.id },
                                new AjaxOptions { UpdateTargetId = "articulomsg",
                                                  OnSuccess = "AnimarMensajeArticulo"
                                })
            %>
        
        <%}
          else
          {  %>
            <a href="/Account/Logon">Log On</a> para probar AJAX.
        <%} %>
    </div>  
    -->
