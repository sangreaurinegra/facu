
<%@ page import="edu.tsi3.planningpoker.Mano" %>
<%@ page import="edu.tsi3.planningpoker.CartaUsuario" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        
        <title>Mano: ${ mano.tarea.nombre }</title>
    </head>
    <body>

        <div class="body">
        	<% def lomo = 1; def sin_jugar = 15%>
        	<div id="mesa">
        		<table>
	            	<tr>
						<g:each in="${ jugadores }" var="jug">
							<% def jugada = CartaUsuario.findByUsuarioAndMano(jug.usuario, mano) 
							   def idcarta = jugada?jugada.carta.id:sin_jugar
							   if(jugada && !estanTodos)
								   idcarta = lomo
							%>
	            			<td>
	            				<a>
	            					<img alt="" src="${ createLink(action:'imagen', controller:'carta', id:idcarta) }" />
	            				</a>
	            			</td>
						</g:each>
						<td>
            				<% //def jugadacreador = CartaUsuario.findByUsuarioAndMano(juego.creador, mano) 
            					def idcarta = jugadacreador?jugadacreador.carta.id:sin_jugar
							    if(jugadacreador && !estanTodos)
								   idcarta = lomo
            				%>
            				<img alt="" src="${ createLink(action:'imagen', controller:'carta', id:idcarta) }" />
            			</td>
	            	</tr>
	            	<tr>
						<g:each in="${ jugadores }" var="jug">
	            			<td>
	            				${jug.usuario.userRealName }
	            			</td>
						</g:each>
            			<td>
            				${juego.creador.userRealName }
            			</td>
	            	</tr>
	            </table>
        	
	        	<div id="toolbar" >
	        		<g:if test="${juego.creador.id == session.usuario.id }">
		        		<form action="${ createLink(action:'finalizar', controller:'mano') }">
		        			<input type="hidden" name="mid" value="${mano.id }" />       		
			        		<table>
				            	<tr>
				            		<g:if test="${estanTodos }">
									<td>
			            				<a id="btn_estimar" onclick="estimar()" >
			            					Estimar
			            				</a>
			            			</td>
			            			<td>
			            				<input id="estimacion" name="estimacion" value="0" style="width:95px; " />
			            			</td>
			            			</g:if>
			            			<td>
			            				<input type="submit"  value="Finalizar Mano" id="btn_fin">         				
			            			</td>
			            			<td>
			            				<a id="btn_finjuego" href="${ createLink(action:'finalizar', controller:'juego', params:[jid:juego.id,mid:mano.id]) }">
			            					Fin del juego
			            				</a>         				
			            			</td>
				            	</tr>
				            </table>
			            </form>
		            </g:if>
	        	</div>
        	</div>
            <table>	
            	<tr>
            		<g:each in="${ cartasArriba }" var="carta">
            			<td>
            				<a onclick="elegir(${carta.id})">
            					<img id="carta_${carta.id }" alt="" src="${ createLink(action:'imagen', controller:'carta', id:carta.id) }" />
            				</a>
            			</td>
            		</g:each>
            	</tr>
            	<tr>
            		<g:each in="${ cartasAbajo }" var="carta">
            			<td>
            				<a onclick="elegir(${carta.id})">
            					<img id="carta_${carta.id }" alt="" src="${ createLink(action:'imagen', controller:'carta', id:carta.id) }" />
            				</a>
            			</td>
            		</g:each>
            	</tr>
            </table>
        </div>
        <script type="text/javascript">
	        function elegir(carta){ 
		        var u = ${session.usuario.id}
		        var m = ${mano.id}
		        $("#carta_"+carta).effect("highlight",null,500,null);
				$.ajax({
					   type: "GET",
					   url: "${createLink(action:'elegirCarta', controller:'mano') }",
					   data: "uid="+u+"&mid="+m+"&cid="+carta,
					   success: function(html){
						//window.location.reload();
						refrescar();
					   }
					 });
				return false;
			}
        </script>
        <script type="text/javascript">
	        $(function() {
				$("#btn_estimar").button({
		            icons: {
	                	primary: 'ui-icon-calculator'
	            	},
	               	text:false
	            });
				$("#btn_fin").button({
		            icons: {
	                	primary: 'ui-icon-check'
	            	}
	            });
				$("#btn_finjuego").button({
		            icons: {
	                	primary: 'ui-icon-power'
	            	}
	            });
			});
	        function estimar(){ 
		        var m = ${mano.id}
				$.ajax({
					   type: "GET",
					   url: "${createLink(action:'estimar', controller:'mano') }",
					   data: "&mid="+m,
					   success: function(html){
							document.getElementById('estimacion').value = html;
					   }
					 });
				return false;
			}
        </script>
        <script type="text/javascript">
	        function refrescar(){ 
		        var m = ${mano.id};
		        var url = "${createLink(action:'resumen', controller:'mano', params:[jid:juego.id]) }";
				$.ajax({
					   type: "GET",
					   url: "${createLink(action:'refrescar', controller:'mano') }",
					   data: "mid="+m,
					   success: function(html){
							if(html == 'fin')
								window.location = url;	
							else{
								$("#mesa").html(html)
								$("#btn_estimar").button({
						            icons: {
					                	primary: 'ui-icon-calculator'
					            	},
					               	text:false
					            });
								$("#btn_fin").button({
						            icons: {
					                	primary: 'ui-icon-check'
					            	}
					            });
								$("#btn_finjuego").button({
						            icons: {
					                	primary: 'ui-icon-power'
					            	}
					            });
							}
					   }
					 });
				setTimeout('refrescar();',10000);
			}
	        setTimeout('refrescar();',10000);
        </script>
    </body>
</html>
