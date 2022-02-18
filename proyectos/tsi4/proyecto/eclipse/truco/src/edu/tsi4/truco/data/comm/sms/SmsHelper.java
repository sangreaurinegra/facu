package edu.tsi4.truco.data.comm.sms;

import java.io.IOException;

import javax.microedition.io.Connector;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.MessageListener;
import javax.wireless.messaging.TextMessage;

import edu.tsi4.truco.bl.truco.mensaje.MensajeAbstracto;
import edu.tsi4.truco.data.xml.XmlHelper;

public class SmsHelper {

	
	public void enviar(MensajeAbstracto mensaje, String cel){
		
		SmsWorker w = new SmsWorker();
		w.destino = cel.trim();
		w.enviar = true;
		w.sms = mensaje.toXml();
		Thread t = new Thread(w);
		t.start();
		
	}
	
	public MensajeAbstracto recibir() throws InterruptedException{
		SmsWorker w = new SmsWorker();
		Thread t = new Thread(w);
		w.enviar = false;
		t.start();
		t.join();
		return  XmlHelper.getMensaje(w.sms);
	}
	
	public class SmsWorker implements Runnable, MessageListener{

		public String sms;
		public boolean enviar = false; // si envia o recibe el mensaje.
		public String destino = ""; // numero de telefono destino cuando va a enviar
		
		private String smsUrl = "sms://";
		private String smsport = ":6000";
		
		private MessageConnection con = null;
		
		public void run() {
			try {
				if(enviar)					
					enviarSms();					
				else
					recibirSms();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch(SecurityException e){
				e.printStackTrace();
			}
		}

		private void recibirSms() throws IOException {
			open("", true);
			TextMessage msg = (TextMessage) con.receive();
			sms = msg.getPayloadText();
		}

		private void enviarSms() throws IOException {
			open(destino, true);
			System.out.println(">>>>>>>>>>>>>>>>> ABRO CONEXION");
			TextMessage msg = (TextMessage) con.newMessage(MessageConnection.TEXT_MESSAGE);
			msg.setAddress(smsUrl+destino+smsport);
			msg.setPayloadText(sms);
			System.out.println(">>>>>>>>>>>>>>>>> TRATO DE ENVIAR MENSAJE");
			con.send(msg);
			System.out.println(">>>>>>>>>>>>>>>>> ENVIE MENSAJE");
		}

		public void notifyIncomingMessage(MessageConnection arg0) {
			System.out.println(">>>>>>>>>>>>>>>>> ENTRO NOTIFY SMS <<<<<<<<<<<<<<<<<<<<");
			
		}
		
		private void open(String dest, boolean nueva) throws IOException{
			if(con == null || nueva)
				con = (MessageConnection) Connector.open(smsUrl+dest+smsport);
		}
		
	}
}
