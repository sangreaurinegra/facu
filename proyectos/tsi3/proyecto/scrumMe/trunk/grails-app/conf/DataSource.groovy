dataSource {
	pooled = true
	driverClassName = "com.mysql.jdbc.Driver" //"org.hsqldb.jdbcDriver"
	username = "scrumme" //"root"
	password = "scrumme" //"maxi"
}
hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class='net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
	development {
		dataSource {
			username = "root"
			password = "maxi"
			dbCreate = "update" // "create-drop" // one of 'create', 'create-drop','update'
			url = "jdbc:mysql://localhost/scrumme" //"jdbc:hsqldb:mem:devDB"
		}
	}
	test {
		dataSource {
			username = "root"
			password = "maxi"
			dbCreate = "update" // "create-drop" // one of 'create', 'create-drop','update'
			url = "jdbc:mysql://localhost/scrumme" //"jdbc:hsqldb:mem:devDB"
			//dbCreate = "update"
			//url = "jdbc:hsqldb:mem:testDb"
		}
	}
	production {
		dataSource {
			username = "scrumme" //"root"
			password = "scrumme" //"maxi"
			dbCreate = "update"
			url = "jdbc:mysql://10.200.8.81:3306/scrumme" //"jdbc:hsqldb:file:prodDb;shutdown=true"
		}
	}
}