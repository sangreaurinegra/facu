package edu.tsi4.truco.data.comm;

import java.io.IOException;

import javax.bluetooth.RemoteDevice;

import net.java.dev.marge.autocon.AutoConnect;
import net.java.dev.marge.communication.CommunicationListener;
import net.java.dev.marge.communication.ConnectionListener;
import net.java.dev.marge.entity.Device;
import net.java.dev.marge.entity.ServerDevice;
import edu.tsi4.truco.bl.truco.mensaje.MensajeAbstracto;
import edu.tsi4.truco.data.xml.XmlHelper;
import edu.tsi4.truco.util.Logger;

public class BTCommConnection implements CommunicationListener, ConnectionListener, ICommunicationConnection {

	
	private final static String NOMBRESERVIDOR = "trucoServer";
	
	private Device device; 
	private boolean conecto = false; 
	
	
	private boolean servidor = false;
	
	private ICommunicationListener listener = null;

	public void connectionEstablished(ServerDevice sd, RemoteDevice rd) {
		Logger.Log(Logger.INFO, BTCommConnection.this.getClass(), "Entro a connectionEstablished");
		
		 this.device = sd;
	     this.conecto = true;
	}

	public void errorOnConnection(IOException ioe) {
		Logger.Log(Logger.ERROR, BTCommConnection.this.getClass(), "errorOnConnection");
	}

	public void initialisationSucessful() {
		Logger.Log(Logger.INFO, BTCommConnection.this.getClass(), "initialisationSucessful");
	}

	public void errorOnReceiving(IOException ioe) {
		Logger.Log(Logger.ERROR, BTCommConnection.this.getClass(), "errorOnReceiving");
		
	}

	public void errorOnSending(IOException ioe) {
		ioe.printStackTrace();
		Logger.Log(Logger.ERROR, BTCommConnection.this.getClass(), "errorOnSending");
		
	}

	public void receiveMessage(byte[] message) {
		
		
		Logger.Log(Logger.INFO, BTCommConnection.this.getClass(), "Entro a receiveMessage");
		String stMsg = null;
		if(message==null){
			Logger.Log(Logger.ERROR, BTCommConnection.this.getClass(), "byte[] message null");
			
		}else{
			Logger.Log(Logger.ERROR, BTCommConnection.this.getClass(), "byte[] message "+  message.toString());
			stMsg = new String(message);
			MensajeAbstracto msg = XmlHelper.getMensaje(stMsg);
			
			Logger.Log(Logger.INFO, BTCommConnection.this.getClass(), "receiveMessage "+stMsg);
			Logger.Log(Logger.INFO, BTCommConnection.this.getClass(), "Mensaje Creado "+msg.paraPruebas());
			
			listener.recibir(msg);
		}
	}

	
	public void conectar(boolean soyServidor ,String destino, ICommunicationListener listener) {                                   

		this.listener = listener;
		
		this.servidor = soyServidor ;
		
		Logger.Log(Logger.INFO, BTCommConnection.this.getClass(), "voy a Conectar");
		
		new Thread(){

			public void run() {
				super.run();
				if (servidor) {
					Logger.Log(Logger.INFO, BTCommConnection.this.getClass(), "voy a createServer");
					AutoConnect.createServer(NOMBRESERVIDOR, BTCommConnection.this, BTCommConnection.this);
					Logger.Log(Logger.INFO, BTCommConnection.this.getClass(), "espero por conexion");
					while (!conecto) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
					}
					
					Logger.Log(Logger.INFO, BTCommConnection.this.getClass(), "Se conecto alguien al servidor");
					
				} else if (!servidor) {  
					try {
						
						Logger.Log(Logger.INFO, BTCommConnection.this.getClass(), "voy a connectToServer");
						
						BTCommConnection.this.device = AutoConnect.connectToServer(NOMBRESERVIDOR, BTCommConnection.this);
						
						Logger.Log(Logger.INFO, BTCommConnection.this.getClass(), "Me conecte al servidor");
						conecto=true;
						
					} catch (IOException ex) {
						ex.printStackTrace();
						Logger.Log(Logger.ERROR, BTCommConnection.this.getClass(), "NO me conecte al servidor");
					}
				}                                      
			}                                    
		
		}.start();
	}

	public boolean isServidor() {
		return servidor;
	}


	public void enviar(MensajeAbstracto mensaje, String destino)
			throws CommException {
		if(mensaje==null)Logger.Log(Logger.ERROR, this.getClass(), "enviar MensajeAbstracto null");
		else Logger.Log(Logger.INFO, this.getClass(), "ENVIANDO "+mensaje.paraPruebas());
		
		final MensajeAbstracto mensajeTh = mensaje;
		
		if(!conecto){
			Logger.Log(Logger.INFO, this.getClass(), "conecto false");
			new Thread(){

				public void run() {
					super.run();
					while (!conecto) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
					}
					if(device!=null){
						String xml = mensajeTh.toXml();
						Logger.Log(Logger.INFO, this.getClass(), "ENVIANDO de  Thread"+xml);
						BTCommConnection.this.device.send(xml.getBytes());
					}else{
						Logger.Log(Logger.ERROR, this.getClass(), "enviar Device null");
						//throw new CommException("Device null");
					}
				
				}
			}.start();
			
		}else{
			Logger.Log(Logger.INFO, this.getClass(), "conecto true");
			if(device!=null){
				String xml = mensaje.toXml();
				Logger.Log(Logger.INFO, this.getClass(), "ENVIANDO "+xml);
				BTCommConnection.this.device.send(xml.getBytes());
			}else{
				Logger.Log(Logger.ERROR, this.getClass(), "enviar Device null");
				throw new CommException("Device null");
			}
		}
		
		
		
		
	}         
	
}
