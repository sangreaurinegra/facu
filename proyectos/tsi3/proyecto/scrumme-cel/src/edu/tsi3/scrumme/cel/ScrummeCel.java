package edu.tsi3.scrumme.cel;

import java.rmi.RemoteException;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import edu.tsi3.scrumme.cel.net.ArrayOfLong;
import edu.tsi3.scrumme.cel.net.Web_PortType_Stub;

public class ScrummeCel extends MIDlet implements ItemCommandListener, CommandListener {

	static Display display;
	static Form login;
	static Form menu;
	static Form proyectos;
	static Long usuario;
	
	//gui
	private TextField username;
	private TextField pwd;
	
	//comandos
	private static final Command CMD_PRESS = new Command("Aceptar", Command.ITEM, 1);
	
	public ScrummeCel() {
		// obtengo display para mostrar
		display = Display.getDisplay(this);
		crearLogin();
		// agrego el form al display para mostrar
		display.setCurrent(login);
	}

	private void crearLogin() {
		login = new Form("Login en Scrumme");
		username = new TextField("Usuario:","",50,TextField.PLAIN);
		pwd = new TextField("Password:","",50,TextField.PASSWORD);
		login.append(username);
		login.append(pwd);
		login.addCommand(CMD_PRESS);
		login.setCommandListener(this);
		
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub

	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO Auto-generated method stub

	}

	public void commandAction(Command arg0, Item arg1) {
		EjecutarWebService ews = new EjecutarWebService();
		ews.run();
		
	}

	public void commandAction(Command arg0, Displayable arg1) {
		EjecutarWebService ews = new EjecutarWebService();
		ews.start();
		
	}
	
	public class EjecutarWebService extends Thread {
		
		public int cmd = 0; //0 es login, 1 es proyectos, 2 es nombre proyecto
		private Long login(){
			Web_PortType_Stub stub = new Web_PortType_Stub();
			try {
				return stub.login(username.getString(), pwd.getString());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new Long(-1);
			}
		}
		
		public void run(){
			if(cmd == 0){
				usuario = login();
				if(usuario.longValue() < 0){
					Alert a = new Alert("Respuesta","Usuario o password incorrecto.",null, AlertType.ERROR);
					display.setCurrent(a);
				}
				else{
					proyectos = new Form("Proyectos en Scrumme");
					proyectos.append("Acceso concedido. Obteniendo proyectos...");					
					display.setCurrent(proyectos);
					String proys = obtenerProyectos();
					proyectos.append(proys);
					//EjecutarWebService ews = new EjecutarWebService();
					//ews.cmd = 1;
					//ews.start();
				}
			}
			else if(cmd == 1){
				
			}
		}

		private String obtenerProyectos() {
			Web_PortType_Stub stub = new Web_PortType_Stub();
			try {
				String res = "";
				ArrayOfLong proys = stub.proyectosAsignado(usuario);
				for(int i = 0;i < proys.get_long().length;i++){
					String nombre = stub.nombreProyecto(proys.get_long()[i]);
					res += proys.get_long()[i]+" - "+nombre+"\n";
				}
				return res;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
		}
	}


	
}
