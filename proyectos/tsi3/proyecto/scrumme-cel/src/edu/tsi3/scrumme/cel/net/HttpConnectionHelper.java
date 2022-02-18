package edu.tsi3.scrumme.cel.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

public class HttpConnectionHelper implements Runnable {

	private Object notificado;
	
	// atributos privados son los parametros de entrada.
	private int nroComercio;
	
	//estos son donde se guardan los resultados
	private String resultado;
	
	// en caso de error verificar los siguientes atributos
	private String errorMsg;
	// si el codigo de error es mayor que cero entonces hubo errores en la ejecucion, verificar el mensaje.
	private int errorCod;
	
	/**
	 * COMANDOS: Le dicen al metodo run que ejecutar.
	 */
	private int comando;
	
	public static final int COMANDO_OBTENER_PRODUCTOS = 1;
	
	
	public String getErrorMsg() {
		return errorMsg;
	}

	public int getErrorCod() {
		return errorCod;
	}

	public int getComando() {
		return comando;
	}

	public String getResultado() {
		return resultado;
	}

	public void setNroComercio(int nroComercio) {
		this.nroComercio = nroComercio;
	}

	public HttpConnectionHelper(int comando,Object notif) {
		this.comando = comando;
		notificado = notif;
	}

	public void run() {
		errorMsg="";
		errorCod=0;
		if (comando == COMANDO_OBTENER_PRODUCTOS){
			try {
				resultado = obtenerProductosViaHttp(nroComercio);
			} catch (IOException e) {
				errorCod = 1;
				errorMsg = e.getMessage();
			}
		}
		notificado.notify();
	}

	
	private final String url = "http://www.tupedidoweb.com/ExternalServices/GetServices.php";
	
	private String obtenerProductosViaHttp(int idComercio) throws IOException {
        HttpConnection c = null;
        DataInputStream is = null;
        DataOutputStream os = null;
        int rc;
        byte[] data;
        
        try {
            c = (HttpConnection)Connector.open(url);

            // Set the request method and headers
            c.setRequestMethod(HttpConnection.POST);
            
            c.setRequestProperty( "User-Agent","Mozilla/4.0" ); //"Profile/MIDP-1.0 Configuration/CLDC-1.0"
            //c.setRequestProperty("Content-Language", "en-US" );
            c.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); // "text/html"
            //c.setRequestProperty( "Connection","close" );
            
            // cargamos los parametros del servicio
            String params = "id_comercio="+idComercio+"&nro_servicio=1";
            c.setRequestProperty( "Content-Length", String.valueOf(params.length()));
            
            // enviamos los parametros
            os = new DataOutputStream( c.openOutputStream());
            os.write(params.getBytes());
            os.flush();           

            
            rc = c.getResponseCode();
            if (rc != HttpConnection.HTTP_OK) {
                throw new IOException("HTTP response code: " + rc);
            }

            is = new DataInputStream(c.openInputStream());
            
            // Get the ContentType
            String type = c.getType();
            //   processType(type);

            // Get the length and process the data
            int len = (int)c.getLength();
            if (len > 0) {
                 int actual = 0;
                 int bytesread = 0 ;
                 data = new byte[len];
                 while ((bytesread != len) && (actual != -1)) {
                    actual = is.read(data, bytesread, len - bytesread);
                    bytesread += actual;
                 }
                 String s = new String(data,"ISO-8859-1");
                 //s+= "asdf";
                 return s;
             //   process(data);
            } else {
                int ch;
                while ((ch = is.read()) != -1) {
                	//       process((byte)ch);
                }
            }
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Not an HTTP URL");
        } finally {
            if (is != null)
                is.close();
            if (os != null)
                os.close();
            if (c != null)
                c.close();
        }
        return "";
    }
	
	
}
