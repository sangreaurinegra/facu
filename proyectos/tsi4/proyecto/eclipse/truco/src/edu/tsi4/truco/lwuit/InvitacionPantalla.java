package edu.tsi4.truco.lwuit;

import com.sun.lwuit.CheckBox;
import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextField;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;

import edu.tsi4.truco.bl.AccionBLGUI;
import edu.tsi4.truco.bl.GameLogic;
import edu.tsi4.truco.bl.truco.AccionTruco;
import edu.tsi4.truco.lwuit.listener.IGUIListener;
import edu.tsi4.truco.util.Logger;

public class InvitacionPantalla extends Pantalla implements IGUIListener{

	final static String textoUrl = "Ingrese Url"; 
	final static String textoUsuario = "Usuario"; 
	String nombreUsuario = "Creador";
	private boolean esServidor=false;
	 
	JuegoPantalla pantallaJuego = null;
	
	Form currentForm = null;
	
	Label ticker = null; 
	 Label lblInvitado = null; 
	 TextField txInvitado = null;	 
	 CheckBox chbSms = null;
	 TextField txCelSms = null;
	
	 Command cmdOk = null;
	 
	 public InvitacionPantalla(boolean esServidor){
			super();
			this.esServidor = esServidor;
			 
		}
	
	protected void execute(Form f) {
		// TODO Auto-generated method stub
		
		currentForm=f;
		
		ticker = new Label();
		f.addComponent(ticker);
		
		lblInvitado = new Label();
		txInvitado = new TextField();
		chbSms = new CheckBox("Sms");
		txCelSms = new TextField();

		txCelSms.setText("5550001");//BORRAR
		cargarDatosIniciales();
		
		f.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		
		f.addComponent(lblInvitado);
	    f.addComponent(txInvitado);
	    if(esServidor){
	    	f.addComponent(chbSms);
	    	f.addComponent(txCelSms);
	    	lblInvitado.setText("Invitar a ...");
	    	
	    	nombreUsuario = "Invitado";	    	
	    }else{
	    	lblInvitado.setText("Unirse a ...");
	    }
	    // seteo el nombre del usuario
	    txInvitado.setText(nombreUsuario);
	    
	    ActionListener listener = new ActionListener() {
           
            public void actionPerformed(ActionEvent evt) {
                Object source = evt.getSource();
                
                if(source == chbSms) {
                	if(chbSms.isSelected()){
                		txCelSms.setEnabled(true);
                	}else{
                		txCelSms.setEnabled(false);
                	}
                	
                    return;
                }
               
            }
        };

        chbSms.addActionListener(listener);
       
        cmdOk = new Command("Ok"){
            public void actionPerformed(ActionEvent evt) {
            	if(esServidor){
            		GameLogic.instance().crearJuego(esServidor, txInvitado.getText().trim(), chbSms.isSelected(), txCelSms.getText().trim(), InvitacionPantalla.this);
            		ticker.setText("Esperando por "+txInvitado.getText().trim());
            		ticker.startTicker(0, true);
            		deshabilitarParaEspera();
            	}else{
            		Logger.Log(Logger.INFO, InvitacionPantalla.this.getClass(), "Me Voy a unir");
            		GameLogic.instance().unirseJuego(txInvitado.getText().trim(), null,InvitacionPantalla.this);
            		Logger.Log(Logger.INFO, InvitacionPantalla.this.getClass(), "espero por listener");
            	}
            	
            }
        };
        f.addCommand(cmdOk);
        
	}

	private void cargarDatosIniciales() {
			chbSms.setSelected(false);
			txCelSms.setEnabled(false);
	}

	public String getName() {
		return esServidor ? "Nuevo" : "Unirse";
	}

	
	public void deshabilitarParaEspera(){
		currentForm.removeCommand(cmdOk);
		txInvitado.setEnabled(false);
		chbSms.setEnabled(false);
		txCelSms.setEnabled(false);
	}
	
	public void habilitarParaEspera(){
		currentForm.addCommand(cmdOk);
		txInvitado.setEnabled(true);
		chbSms.setEnabled(true);
		txCelSms.setEnabled(true);
	}
	
	public void recibir(AccionBLGUI accion) {
		AccionTruco acc = (AccionTruco)accion;

		Logger.Log(Logger.INFO, InvitacionPantalla.this.getClass(), "----> servidor:"+esServidor+" accion:"+accion.getAccion());
		if(accion.getAccion()==AccionTruco.ACCION_MOSTRAR_CARTAS){ // invitado	// servidor
			pantallaJuego = new JuegoPantalla(esServidor);
			pantallaJuego.setCartas(acc.cartas);
			pantallaJuego.tanteador=acc.getMensaje().getParametro("tanteador");
			pantallaJuego.run(currentBackCommand, currentCommandListener);
		}
	}
	
	public void setNombreUsuario(String nombre){
		nombreUsuario = nombre;
	}
	
}
