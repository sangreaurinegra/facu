<ul>
	<li>
		<h3><g:link controller="proyecto" action="proyectos">Proyectos</g:link></h3>
		<ul>
			<li>
				<g:link controller="proyecto" action="create">Crear</g:link>
			</li>	
			<li>
				<g:link controller="tarea" action="create">Crear tarea</g:link>
			</li>
			
			<g:if test="${session.proyecto != null }" >
				<li>
					<g:link controller="proyecto" action="resumen" id="${session.proyecto }">${ session.proy_nombre}</g:link>
				</li>
			</g:if>
		</ul>
	</li>
	<li>
		<h3><g:link controller="reunion" action="reuniones">Reuniones</g:link></h3>
		<ul>
			<li>
				<g:link controller="reunion" action="create">Crear</g:link>
			</li>	
		</ul>
	</li>
	
	<%--
	<h3><a href="#">Available Controllers:</a></h3> 
	<g:each var="c" in="${grailsApplication.controllerClasses}">
		<li class="controller"><g:link controller="${c.logicalPropertyName}">${c.name}</g:link></li>
	</g:each>
	<li>
		<h3><a href="#">Installed Plugins</a></h3>
		<ul>
			<g:set var="pluginManager" value="${applicationContext.getBean('pluginManager')}"></g:set>
	
			<g:each var="plugin" in="${pluginManager.allPlugins}">
				<li>${plugin.name} - ${plugin.version}</li>
			</g:each>
	
		</ul>
	</li>
	--%>		
</ul>