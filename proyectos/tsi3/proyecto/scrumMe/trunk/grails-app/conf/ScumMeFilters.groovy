/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Maxi
 */

import org.grails.plugins.springsecurity.service.AuthenticateService

class ScumMeFilters {

    def authenticateService

    def filters = {
       loginCheck(controller:'*', action:'*') {
           before = {
              if(authenticateService.isLoggedIn()) {
                  //println "esta logueado"
                  if(!session.usuario){
                      println "no hay usuario"
                      session.usuario = authenticateService.userDomain()
                  }
                  return true
               }
           }

        }
    }
}

