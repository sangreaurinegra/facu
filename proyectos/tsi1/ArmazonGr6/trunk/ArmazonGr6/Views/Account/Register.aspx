<%@ Page Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage" %>

<asp:Content ID="registerTitle" ContentPlaceHolderID="TitleContent" runat="server">
    Register
</asp:Content>

<asp:Content ID="registerContent" ContentPlaceHolderID="MainContent" runat="server">
    <h2>Crear Nueva Cuenta</h2>
    <p>
        Utilice el siguiente formulario para crear una nueva cuenta. 
    </p>
    <p>
        Los passwords requieren un minimo de <%=Html.Encode(ViewData["PasswordLength"])%> caracteres de tamaño.
    </p>
    <%= Html.ValidationSummary("No se pudo crear la Cuenta. Por favor corrija los errores e intente nuevamente.") %>

    <% using (Html.BeginForm()) { %>
        <div>
            <fieldset>
                <legend>Información de la Cuenta</legend>
                <p>
                    <label for="username">Nombre de Usuario:</label>
                    <%= Html.TextBox("username") %>
                    <%= Html.ValidationMessage("username") %>
                </p>
                <p>
                    <label for="email">Email:</label>
                    <%= Html.TextBox("email") %>
                    <%= Html.ValidationMessage("email") %>
                </p>
                <p>
                    <label for="password">Password:</label>
                    <%= Html.Password("password") %>
                    <%= Html.ValidationMessage("password") %>
                </p>
                <p>
                    <label for="confirmPassword">Confirmar password:</label>
                    <%= Html.Password("confirmPassword") %>
                    <%= Html.ValidationMessage("confirmPassword") %>
                </p>
                <p>
                    <input type="submit" value="Register" />
                </p>
            </fieldset>
        </div>
    <% } %>
</asp:Content>
