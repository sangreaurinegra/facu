security {

	// see DefaultSecurityConfig.groovy for all settable/overridable properties

	active = true

	loginUserDomainClass = "edu.tsi3.scrumme.Usuario"
	authorityDomainClass = "edu.tsi3.scrumme.Role"
	requestMapClass = "edu.tsi3.scrumme.RequestMap"
		
	defaultRole = "ROLE_USER"
        

    defaultTargetUrl = "/inicio/index"

}
