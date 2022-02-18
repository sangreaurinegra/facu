
<%@page import="edu.tsi3.scrumme.util.Utilidades"%>
<html>
    <head>
        <title><g:layoutTitle default="ScrumMe" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css', contextPath:'')}" />
        <link rel="shortcut icon" href="${resource(dir:'images',file:'sport_football.png', contextPath:'')}" type="image/png" />
        <script type="text/javascript" src="${resource(dir:'js/jquery', file:'jquery-1.4.2.min.js', contextPath:'')}" ></script>
        <link href="${resource(dir:'css/excite-bike', file:'jquery-ui-1.8.2.custom.css', contextPath:'')}" type="text/css" rel="stylesheet" media="screen, projection" />
        <script src="${resource(dir:'js/jquery-ui/excite-bike', file:'jquery-ui-1.8.2.custom.min.js', contextPath:'')}" type="text/javascript" ></script>

        <link rel="stylesheet" href="${resource(dir:'css',file:'scrumme.css', contextPath:'')}" />
        <g:layoutHead />
        <g:javascript library="application" />
        <ewiki:resources jquery="false" jqueryui="false" ></ewiki:resources>
    </head>
    <body>
    	<% def estoyEnIndex = controllerName == 'inicio' && actionName == 'index' %>
        <div id="spinner" class="spinner" style="display:none;">
            <img src="${resource(dir:'images',file:'spinner.gif')}" alt="Spinner" />
        </div>
        <div id="header">
			<div id="logo">
				<h1><a href="${createLink(uri:'/') }">Scrum<span style="color: white;">Me</span></a></h1>
				<h2><a href="${createLink(uri:'/') }">Yo! estoy comprometido y tú ...</a></h2>
			</div>
			<g:if test="${session.usuario }">
				<form method="get" action="${createLink(uri:'/logout') }">
						<input id="salirsubmit" type="submit" value="salir" />
				</form>
			</g:if>
			<%-- 
			<div id="search">
				<form method="get" action="">
					<fieldset>
					<input id="searchinput" type="text" name="searchinput" value="" />
					<input id="searchsubmit" type="submit" value="Search" />
					</fieldset>
				</form>
			</div>
			--%>
		</div>
		<div id="menu">
			<g:if test="${session.usuario }">
				<ul>
					<li class="${Utilidades.esActivo(controllerName,'inicio')}"><g:link action="index" controller="inicio"><b>Inicio</b></g:link></li>
					<li class="${Utilidades.esActivo(controllerName,'proyectos')}"><g:link action="index" controller="proyecto"><b>Proyectos</b></g:link></li>
					<li class="${Utilidades.esActivo(controllerName,'juego')}"><g:link action="index" controller="juego"><b>Juego de cartas</b></g:link></li>
					<li class="${Utilidades.esActivo(controllerName,'admin')}"><g:link action="index" controller="admin"><b>Administración</b></g:link></li>
					<li class="${Utilidades.esActivo(controllerName,'ayuda')}"><g:link action="index" controller="easywiki"><b>Ayuda</b></g:link></li>
				</ul>
			</g:if>
			<span class="fecha">
				<g:if test="${session.proy_nombre }" ><g:link action="resumen" controller="proyecto" id="${session.proyecto }">${ session.proy_nombre}</g:link> &nbsp;&nbsp;-&nbsp; </g:if>
				<g:if test="${session.usuario }" >${ session.usuario.userRealName} &nbsp;&nbsp;-&nbsp; </g:if>
				<g:formatDate date="${new Date()}" format="dd / MMM / yyyy"/>
			</span>
		</div>
        
        <div id="page">
        	<div id="sidebar" class="two-cols">
				<div class="col-one">
					<g:if test="${session.usuario }">
						<div class="box-blue">
							<h2 class="section"><b>Menú</b></h2>
							<div class="content">
								<g:render template="../menu_izq" />
							</div>
						</div>
					</g:if>
				</div>
			</div>
			<% def ancho = estoyEnIndex==false?'anchomax':'' %>
			<% def clogin = controllerName=='login'?'clogin':'' %>
			<div id="content" class="main_content ${ancho } ${clogin }">
				
				<div id="feature" class="box-orange">
					<h2 class="section"><b><g:layoutTitle default="ScrumMe" /></b></h2>
					<div class="content">
						<g:layoutBody />
					</div>
				</div>
				
				
			</div>
			<g:if test="${ estoyEnIndex }" >
				<div id="sidebarR" class="two-cols">
					<div class="col-two">
						<div class="box-pink">
							<h2 class="section"><b>Mensajes</b></h2>
							<div  class="content" >
							<div id="feed" style="width:160px; height:600px">
							
							
							</div>
							
							</div>
						</div>
					</div>
				</div>
			</g:if>
			<div style="clear: both;">&nbsp;</div>
		</div>
		<div id="footer">
			<p id="legal">ScrumMe es un producto de Maximiliano Felix y Gabriel Centurión - Distribuído bajo licencia Apache 2.0</p>
			<p id="links"><a href="http://grails.org" target="blank">Grails</a> | <a target="blank" href="http://www.famfamfam.com/">FamFamFam Icons</a></p>
		</div>
		<script type="text/javascript">
			$(function() {
				//$("#alarmas").dialog({ autoOpen: false });
				$.ajax({
					   type: "GET",
					   url: "${createLink(action:'checkAlarmas', controller:'mensaje') }",
					   data: "",
					   success: function(html){
						   if(html != "no"){
								$("#alarmas").html(html);
								$("#alarmas").get(0).style.display = '';
						   }
					   }
					 });

				$.ajax({
					   type: "GET",
					   url: "${createLink(action:'index', controller:'feed') }",
					   data: "",
					   success: function(html){
						   
								$("#feed").html(html);
								
						   
					   }
					 });
				 
			});
		</script>
		<div id="alarmas" class="ui-widget-content" style="display: none;"></div>
		
		
    </body>
</html>