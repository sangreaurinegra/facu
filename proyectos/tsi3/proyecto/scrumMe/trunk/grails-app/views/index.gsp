
<%@ page import="edu.tsi3.scrumme.Proyecto" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=ISO-8859-1" />
	<title>ScrumMe - Bienvenido</title>
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<meta name="layout" content="main" />
	<script type="text/javascript" src="${resource(dir:'js/jquery', file:'jquery.roundabout.min.js')}" ></script>
	<script type="text/javascript" src="${resource(dir:'js/jquery', file:'jquery.roundabout-shapes-1.1.min.js')}" ></script>
	 <style type="text/css">
	   /* <![CDATA[ */
	   .roundabout-holder { padding: 0; height: 5em; }
	   .roundabout-moveable-item {
	      height: 120px;
	      width: 120px;
	      cursor: pointer;
	      background-color: #ffc;
	      border: 1px solid #999;
	   }
	   .roundabout-in-focus { cursor: auto; }
	   /* ]]> */
	</style> 
</head>
<body>
	<div style="width: 500px; padding-top: 100px">
		<ul id="proyectos">
			<g:each in="${ proyectos }" var="p">
				<li>
					<scrumme:caratula idproy="${p.id }" nombre="${p.nombre }" estado="${p.estado.nombreEstado }"/>
				</li>
			</g:each>
		</ul>
	</div>
	 <script type="text/javascript">
	   // <![CDATA[
	   $(document).ready(function() {
	      $('ul#proyectos').roundabout({
	         shape: 'diagonalRingLeft'
	      });
	   });
	   // ]]>
	</script> 

	
</body>
</html>
