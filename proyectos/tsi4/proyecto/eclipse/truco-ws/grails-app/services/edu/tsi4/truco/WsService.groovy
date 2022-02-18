package edu.tsi4.truco

//import edu.tsi4.truco.wsprototipo

class WsService {

    boolean transactional = true

    static expose = ["xfire"]

    String returnString() {
	"valor"
    }

	String[] returnStringArray() {
		String[] a = ["hola","adios"]
		return a
    }

	Long returnLong() {
		return new Long(15)
    }

	Objeto returnObjeto() {
		return new Objeto()
    }

	void logStringArray(String[] val){
		if(val == null)
			throw new Exception("val es null")
		println val
	}

	int suma(int a, int b){
		return a + b
	}

	Hashtable returnHashtable(){
		def h = new Hashtable()
		h.uno = "2"
		return h
	}

}
