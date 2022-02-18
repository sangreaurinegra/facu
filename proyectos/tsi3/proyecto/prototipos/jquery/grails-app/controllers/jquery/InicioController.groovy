package jquery

class InicioController {

    def dyd = { }
    
    def cambio = {
    		println "tarea cambio de estado a: "+params.estado
    		render "cambio ok"
    }
    
    def tarea = {
    		render "<h1>tarea ${params.tarea}</h1>"
    }
}
