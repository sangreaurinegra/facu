<%@ Control Language="C#" Inherits="System.Web.Mvc.ViewUserControl" %>
<%@ Import Namespace="ArmazonGr6.Models"%>
<%@ Import Namespace="ArmazonGr6.Controllers"%>


<%
    if (Request.IsAuthenticated) {
        UsuarioRepository ur = new UsuarioRepository();
        bool esAdmin = ur.esAdmin(Page.User.Identity.Name);
        if (esAdmin){
%>
          
          <a href="/Admin.mvc/AdminMain"><img id="menuImagen3" src="../../Content/imagenes/wrench.png" alt="nada" style="opacity: 0.4" /></a>  

<%
        }
        else {
%>
            <a href="/Cliente.mvc/Main"><img id="menuImagen" src="../../Content/imagenes/house.png" alt="nada" /></a>
            <%=  Html.Encode("   ")%>
            <a href="/Carrito.mvc/ArticulosPorCarrito"><img id="menuImagen2" src="../../Content/imagenes/cart.png" alt="nada" /></a>

<%
        }
    }
    else {/* si no esta loggeado no muestro nada */ }
%> 
        

