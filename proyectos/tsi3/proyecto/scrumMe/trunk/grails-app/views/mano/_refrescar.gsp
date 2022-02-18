
<%@ page import="edu.tsi3.planningpoker.Mano" %>
<%@ page import="edu.tsi3.planningpoker.CartaUsuario" %>
<% def lomo = 1; def sin_jugar = 15%>
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
            
