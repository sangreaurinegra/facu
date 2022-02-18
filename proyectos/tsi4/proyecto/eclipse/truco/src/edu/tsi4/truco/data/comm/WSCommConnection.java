package edu.tsi4.truco.data.comm;

import edu.tsi4.truco.bl.truco.mensaje.MensajeAbstracto;
import edu.tsi4.truco.config.ConfigManager;
import edu.tsi4.truco.data.comm.ws.Truco_PortType_Stub;
import edu.tsi4.truco.data.xml.XmlHelper;
import edu.tsi4.truco.util.Logger;

public class WSCommConnection implements ICommunicationConnection {

	protected MensajeAbstracto mensaje;
	protected String destino;
	protected String invitado;
	protected ICommunicationListener listener;
	
	public void conectar(boolean soyServidor, String destino, ICommunicationListener listener) {
		// seteo el listener, y levanto el hilo escuchador
		this.listener = listener;
		this.invitado = destino;
		RecibirWorker w = new RecibirWorker();
		Thread t = new Thread(w);
		t.start();
	}
	
	public void enviar(MensajeAbstracto mensaje, String destino) throws CommException {
		//seteamos los datos
		this.mensaje = mensaje;
		this.destino = destino;
		
		//abrimos thread y enviamos y recibimos
		
		Worker w = new Worker();
		Thread t = new Thread(w);
		t.start();
		try { // hacemos join para esperar a que se cargue el listener, antes de retornar a la gui
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class Worker implements Runnable {

		public void run() {
			// TODO Auto-generated method stub
			try {
				enviar();
			} catch (CommException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private void enviar() throws CommException{			
			try {
				Truco_PortType_Stub stub = new Truco_PortType_Stub(ConfigManager.urlServidor());
				String u = ConfigManager.usuario();
				Logger.Log(Logger.INFO, this.getClass(), "mensaje.toXml: "+mensaje.toXml());
				stub.enviar(mensaje.toXml(), destino, u);
			} catch (Exception e) {
				e.printStackTrace();
				throw new CommException(e.getMessage());
			}
		
		}

	}
	
	private class RecibirWorker implements Runnable {

		public void run() {
			// TODO Auto-generated method stub
			try {
				recibir(listener);
			} catch (CommException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private void recibir(ICommunicationListener listener) throws CommException{
			try {
				Truco_PortType_Stub stub = new Truco_PortType_Stub(ConfigManager.urlServidor());
				String u = ConfigManager.usuario();
				String response = null;
				while (true){
					//Logger.Log(Logger.INFO, this.getClass(), "recibiendo..");
					response = stub.recibir(u, invitado);
					if(response != null){
						Logger.Log(Logger.INFO, this.getClass(), "mensaje recibido: "+response);
						listener.recibir(XmlHelper.getMensaje(response));
					}
					Thread.sleep(3000);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new CommException(e.getMessage());
			}
		
		}

	}

	

	
	
}
