package edu.tsi4.truco.lwuit;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;

import com.sun.lwuit.Button;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.util.Log;

import edu.tsi4.truco.bl.GameLogic;
import edu.tsi4.truco.bl.truco.AccionTruco;
import edu.tsi4.truco.bl.truco.ComandoTruco;
import edu.tsi4.truco.util.Logger;

/**
 * @author Usuario
 *
 */
public class TrucoPantalla extends Form{

	
	public static int ESTADO_INICIAL=1;
	public static int ESTADO_ME_GRITARON_TRUCO=2;
	public static int ESTADO_ME_GRITARON_RETRUCO=3;
	public static int ESTADO_ME_GRITARON_VALE4=4;
	
	public static int ESTADO_ME_QUISIERON=5;
	
	// faltan estados ....
	
	private int estado = 1; 
	
	Label lblInfo = null;
	Button btnTruco = null;
	Button btnReTruco = null;
	Button btnVale4 = null;
	Button btnQuiero = null;
	Button btnNoQuiero = null;
	
	JuegoPantalla pantallaJuego=null;
	
	public TrucoPantalla(final JuegoPantalla pantallaJuego){
		super("Gritos");
		this.pantallaJuego=pantallaJuego;
		 this.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
         
		 btnTruco = new Button("Truco");
		 btnReTruco = new Button("Re Truco");
		 btnVale4 = new Button("Vale 4");
		 btnQuiero = new Button("Quiero");
		 btnNoQuiero = new Button("No Quiero");
		 
		 
		 lblInfo = new Label("Opciones de Gritos");
//		 TODO
//		 InputStream in = getClass().getResourceAsStream("/truco.wav");
//		 try {
//			Player sonido = Manager.createPlayer(in, "audio/x-wav");
//			sonido.start();
//		} catch (IOException e) {
//			Logger.Log(Log.ERROR, this.getClass(), "SONIDO");
//			e.printStackTrace();
//		} catch (MediaException e) {
//			Logger.Log(Log.ERROR, this.getClass(), "SONIDO");
//			e.printStackTrace();
//		}
		
		

//		 btnTruco.setBorderPainted(false);
//		 btn3Truco.setBorderPainted(false);
//		 btnRealTruco.setBorderPainted(false);
//		 btnFaltaTruco.setBorderPainted(false);
		 
		 ActionListener listenerTruco = new ActionListener() {

				public void actionPerformed(ActionEvent evt) {
					Object source = evt.getSource();
				
					if(source == btnTruco) {
						ComandoTruco c = new ComandoTruco();
						c.setComando(ComandoTruco.CMD_TRUCO);
						GameLogic.instance().ejecutarComando(c, pantallaJuego);
						pantallaJuego.habilitarCartas();
						pantallaJuego.juegoForm.show();
					}else if(source == btnReTruco) {
						ComandoTruco c = new ComandoTruco();
						c.setComando(ComandoTruco.CMD_RE_TRUCO);
						GameLogic.instance().ejecutarComando(c, pantallaJuego);
						pantallaJuego.habilitarCartas();
						pantallaJuego.juegoForm.show();
					}else if(source == btnVale4) {
						ComandoTruco c = new ComandoTruco();
						c.setComando(ComandoTruco.CMD_VALE_4);
						GameLogic.instance().ejecutarComando(c, pantallaJuego);
						pantallaJuego.habilitarCartas();
						pantallaJuego.juegoForm.show();
					}else if(source == btnQuiero) {
						ComandoTruco c = new ComandoTruco();
						c.setComando(ComandoTruco.CMD_QUIERO);
						GameLogic.instance().ejecutarComando(c, pantallaJuego);
						pantallaJuego.habilitarCartas();
						pantallaJuego.juegoForm.show();
					}else if(source == btnNoQuiero) {
						ComandoTruco c = new ComandoTruco();
						c.setComando(ComandoTruco.CMD_NO_QUIERO);
						AccionTruco accion = (AccionTruco)GameLogic.instance().ejecutarComando(c, pantallaJuego);
						pantallaJuego.habilitarCartas();
						pantallaJuego.recibir(accion);
						pantallaJuego.juegoForm.show();
					}
					
				}
			};
			btnTruco.addActionListener(listenerTruco);
			btnReTruco.addActionListener(listenerTruco);
			btnVale4.addActionListener(listenerTruco);
			btnQuiero.addActionListener(listenerTruco);
			btnNoQuiero.addActionListener(listenerTruco);
        
			this.addComponent(lblInfo);
			this.addComponent(btnTruco);
			this.addComponent(btnReTruco);
			this.addComponent(btnVale4);
			this.addComponent(btnQuiero);
			this.addComponent(btnNoQuiero);
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public void show() {
		// TODO Auto-generated method stub
		switch (estado) {
		case 1://ESTADO_INICIAL
			lblInfo.setText("Opciones de Gritos");
			agregarBoton(btnTruco);
			quitarBoton(btnReTruco);
			quitarBoton(btnVale4);
			quitarBoton(btnQuiero);
			quitarBoton(btnNoQuiero);
			break;
		case 2://ESTADO_ME_GRITARON_TRUCO
			lblInfo.setText("TRUCO !!!!");
			quitarBoton(btnTruco);
			agregarBoton(btnReTruco);
			quitarBoton(btnVale4);
			agregarBoton(btnQuiero);
			agregarBoton(btnNoQuiero);
			break;
		case 3://ESTADO_ME_GRITARON_RETRUCO
			lblInfo.setText("RE TRUCO !!!!");
			quitarBoton(btnTruco);
			quitarBoton(btnReTruco);
			agregarBoton(btnVale4);
			agregarBoton(btnQuiero);
			agregarBoton(btnNoQuiero);
			break;
		case 4://ESTADO_ME_GRITARON_VALE4
			lblInfo.setText("VALE 4 !!!!");
			quitarBoton(btnTruco);
			quitarBoton(btnReTruco);
			quitarBoton(btnVale4);
			agregarBoton(btnQuiero);
			agregarBoton(btnNoQuiero);
			break;
		case 5:
			lblInfo.setText("QUIERO !!");
			quitarBoton(btnTruco);
			quitarBoton(btnReTruco);
			quitarBoton(btnVale4);
			quitarBoton(btnQuiero);
			quitarBoton(btnNoQuiero);
			
			break;

		default:
			break;
		}
		super.show();
	}
	
	void quitarBoton(Button btn){
		btn.setEnabled(false);
		btn.setVisible(false);
	}
	
	void agregarBoton(Button btn){
		btn.setEnabled(true);
		btn.setVisible(true);
	}
	
}
