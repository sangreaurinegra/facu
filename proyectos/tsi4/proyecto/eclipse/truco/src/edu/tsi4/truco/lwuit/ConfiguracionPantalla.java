package edu.tsi4.truco.lwuit;


import java.io.IOException;
import java.io.InputStream;

import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import com.sun.lwuit.util.Log;

import edu.tsi4.truco.util.Logger;


import com.sun.lwuit.Button;
import com.sun.lwuit.ButtonGroup;
import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.RadioButton;
import com.sun.lwuit.TextField;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;

import edu.tsi4.truco.bl.AccionBLGUI;
import edu.tsi4.truco.bl.GameLogic;
import edu.tsi4.truco.bl.truco.ComandoTruco;
import edu.tsi4.truco.config.ConfigManager;

public class ConfiguracionPantalla extends Pantalla {

	final static String textoUrl = "Ingrese Url"; 
	final static String textoUsuario = "Usuario"; 
	
	 RadioButton rbWs = null;
	 TextField txUrl = null;
	 RadioButton rbBluetooth = null;
	 TextField txUsuario = null;
	
	protected void execute(Form f) {
		// TODO Auto-generated method stub
		
		rbWs = new RadioButton("Internet");
		txUrl = new TextField(textoUrl);
		rbBluetooth = new RadioButton("Bluetooth");
		txUsuario = new TextField(textoUsuario);
	
		cargarDatosIniciales();
		ButtonGroup btnGroup = new ButtonGroup();
		
		f.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		
		
		f.addComponent(txUsuario);
	    f.addComponent(rbBluetooth);
	    f.addComponent(rbWs);
	    f.addComponent(txUrl);
	  
	   
	    
	    btnGroup.add(rbBluetooth);
	    btnGroup.add(rbWs);
	    
	    ActionListener listener = new ActionListener() {
           
            public void actionPerformed(ActionEvent evt) {
                Object source = evt.getSource();
                /*if(source == setAsDefault) {
                    Border.setDefaultBorder(lastBorder);
                    return;
                }*/
                
                if(source == rbBluetooth) {
                   // TODO seteo bluetooth
                	txUrl.setEnabled(false);
                    return;
                }
                
                if(source == rbWs) {
                	// TODO seteo ws y habilito texfield url 
                	txUrl.setEnabled(true);
                	if(txUrl.getText().equals(textoUrl)) txUrl.setText("");
                	
//                	buscar alternativa 
//                	rbWs.setFocus(false);
//                	txUrl.setFocus(true);
                	
                	return;
                }
               
            }
        };
        
       
        rbBluetooth.addActionListener(listener);
        rbWs.addActionListener(listener);
        
        
        
        
        ActionListener listenerFlor = new ActionListener() {
            
            public void actionPerformed(ActionEvent evt) {
                Object source = evt.getSource();
                /*if(source == setAsDefault) {
                    Border.setDefaultBorder(lastBorder);
                    return;
                }*/
                
              
                
              
               
            }
        };
        
       
        // prueba comando
        Command cmdGuardar = new Command("Guardar"){
            public void actionPerformed(ActionEvent evt) {
            	guardarDatos();
            }
        };
        f.addCommand(cmdGuardar);
        
	}

	private void cargarDatosIniciales() {
		
		try{
			txUsuario.setEnabled(true);
			txUsuario.setText(ConfigManager.usuario());
			
			int tipocon = ConfigManager.tipoConexion();
			rbBluetooth.setSelected(tipocon == ConfigManager.TIPO_CON_BLUETOOTH);
			rbWs.setSelected(tipocon == ConfigManager.TIPO_CON_WS);
			
			txUrl.setEnabled(tipocon == ConfigManager.TIPO_CON_WS);
			txUrl.setText(ConfigManager.urlServidor());
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}

	public String getName() {
		// TODO Auto-generated method stub
		return "Configuracion";
	}

	private void guardarDatos(){
		// Gusrda los datos seleccionados de la configuracion
		// consultando los componentes 
		try{
			if(rbBluetooth.isSelected()){
				// seleccionedo bluetooth
				ConfigManager.saveTipoConexion(ConfigManager.TIPO_CON_BLUETOOTH);
				System.out.println("seleccionedo bluetooth");
				
			}else if (rbWs.isSelected()) {
				// seleccionado ws
				ConfigManager.saveTipoConexion(ConfigManager.TIPO_CON_WS);
				System.out.println("seleccionado ws");				
			}
			ConfigManager.saveUrlServidor(txUrl.getText());
			ConfigManager.saveUsuario(txUsuario.getText());
		}
		catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	
//	private void mandarFlor(){
//		ComandoTruco cmd = new ComandoTruco();
//		cmd.setComando(ComandoTruco.CMD_CONTRA_FLOR);
//		GameLogic.instance().crearJuego(true, null, false, null, null);
//		System.out.println("mando el comando");
//		AccionBLGUI accion = GameLogic.instance().ejecutarComando(cmd, null);
//		
//		System.out.println("ejecuteCMD");
//		System.out.println("accion"+accion.getAccion());
//		System.out.flush();
//		/*
//		if(accion.getAccion()==AccionBLGUI.ACCION_MOSTRAR_MENSAJE){
//			lblFlor.setText(accion.getMensaje().getParametro("msg"));
//		}*/
//	}
	
}
