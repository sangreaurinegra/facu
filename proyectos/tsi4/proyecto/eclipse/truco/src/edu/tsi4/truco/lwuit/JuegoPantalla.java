package edu.tsi4.truco.lwuit;


import java.io.IOException;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.table.TableLayout;
import com.sun.lwuit.util.Log;
import com.sun.lwuit.util.Resources;

import edu.tsi4.truco.bl.AccionBLGUI;
import edu.tsi4.truco.bl.GameLogic;
import edu.tsi4.truco.bl.truco.AccionTruco;
import edu.tsi4.truco.bl.truco.Carta;
import edu.tsi4.truco.bl.truco.ComandoTruco;
import edu.tsi4.truco.lwuit.listener.IGUIListener;
import edu.tsi4.truco.util.Logger;



import java.io.InputStream;

import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;

public class JuegoPantalla extends Pantalla implements IGUIListener{

	public boolean esServidor = false;
	private boolean esMiTurno = false;
	
	private Image  cartasImg[] = null;
	private Carta cartas[];
	public String tanteador = "...";
	
	private Label ticker = null;
	final Button btnCarta1 = new Button();
	final Button btnCarta2 = new Button();
	final Button btnCarta3 = new Button();
	
	int cartasJugadas[] = {0,0}; // para manejo de la mesa
	
	boolean cartasJugadasBtn[] = {false,false,false}; // para manejo de acualizacion por turno
	
	Label muestra = null;
	Container contMesa = null;
	Container contMedio = null;
	Label cartaJugada1 = null;
	Label cartaJugada2 = null;
	
	int iMuestra = 0;
	private static int gradosImgMuestra = 15;
	int iCarta1 = 0;
	int iCarta2 = 0;
	int iCarta3 = 0;
	
	public Form juegoForm = null;
	ToquesPantalla toquesForm = null;
	TrucoPantalla trucoForm = null;
	
	public JuegoPantalla(boolean esServidor){
		super();
		this.esServidor = esServidor;
		esMiTurno=esServidor;
		//SETEO EL LISTENER
		ComandoTruco c = new ComandoTruco();
		c.setComando(ComandoTruco.CMD_SET_LISTENER);
		GameLogic.instance().ejecutarComando(c, this);
		trucoForm = new TrucoPantalla(JuegoPantalla.this);
	}
	
	public void setCartas(Carta cartas[]){
		this.cartas = cartas;
	}
	
	protected void execute(Form f) {
		inicializar(f);
	}
	
	private void inicializar(Form f){
		f.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		
		getCartas();
		agregarComponetesAPantalla(f);
		agregarComandosAPantalla(f);
	}
	
	private void getCartas(){
		
		if (cartasImg==null){
			
			Image maso = null;
			
			int cartaWidth= 40;
			int cartaHeight= 59;
			int cols = 10;
			
			int ajusteX=2;
			int ajustey=1;
			
			try {
				Resources images = TrucoMIDlet.getResource("resources");
				maso = images.getImage("maso.jpg");
				cartasImg = new Image[41];
				for(int i = 0; i < cartasImg.length ; i++){
					cartasImg[i] = maso.subImage((i%cols)*cartaWidth + ajusteX, (i/cols)*cartaHeight + ajustey , cartaWidth, cartaHeight, true);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private void agregarComandosAPantalla(final Form form){
		  
		form.addCommand(new Command("Irme al Mazo") {
			public void actionPerformed(ActionEvent evt) {
				
			}
		});
		
		form.addCommand(new Command("Toques") {
			public void actionPerformed(ActionEvent evt) {
				if(toquesForm==null)toquesForm = new ToquesPantalla();
				Command c = new Command("Back") {
					public void actionPerformed(ActionEvent evt) {
						form.show();
					}
				};
				toquesForm.addCommand(c);
				toquesForm.setBackCommand(c);
				toquesForm.show();
			}
		});
		
		form.addCommand(new Command("Cantos") {
			public void actionPerformed(ActionEvent evt) {
				if(toquesForm==null)toquesForm = new ToquesPantalla();
				Command c = new Command("Back") {
					public void actionPerformed(ActionEvent evt) {
						form.show();
					}
				};
				toquesForm.addCommand(c);
				toquesForm.setBackCommand(c);
				toquesForm.show();
			}
		});
		
		
		
		if(trucoForm==null)trucoForm = new TrucoPantalla(JuegoPantalla.this);
		Command c = new Command("Back") {
			public void actionPerformed(ActionEvent evt) {
				form.show();
			}
		};
		trucoForm.addCommand(c);
		trucoForm.setBackCommand(c);
		
		form.addCommand(new Command("Gritos") {
			
			public void actionPerformed(ActionEvent evt) {
				
				trucoForm.show();
			}
		});
		
	}
	
	private void agregarComponetesAPantalla(Form f){
		
		juegoForm = f ;
		
//		btnCarta1 = new Button();
//			
//		btnCarta2 = new Button();
//		
//		btnCarta3 = new Button();
		
		ticker = new Label();
		
		cartaJugada1 = new Label();
		cartaJugada2 = new Label();
		
		muestra = new Label();
		
		ticker.setText("Conectando");
		ticker.startTicker(0, true);
		f.addComponent(ticker);
		
		TableLayout layMedio = new TableLayout(1,3);
		contMedio = new Container(layMedio);
		
		
		if(cartas!=null){
			 iMuestra = Integer.valueOf(cartas[3].toSprite()).intValue();
			 iCarta1 = Integer.valueOf(cartas[0].toSprite()).intValue();
			 iCarta2 = Integer.valueOf(cartas[1].toSprite()).intValue();
			 iCarta3 = Integer.valueOf(cartas[2].toSprite()).intValue();
			
			muestra.setIcon(cartasImg[iMuestra].rotate(gradosImgMuestra));
			muestra.getStyle().setBgColor(0x24732e);
			
			btnCarta1.setIcon(cartasImg[iCarta1]);
			
			btnCarta2.setIcon(cartasImg[iCarta2]);
			
			btnCarta3.setIcon(cartasImg[iCarta3]);
			
			
			
		} else {
			muestra.setIcon(cartasImg[9]);
			btnCarta1.setIcon(cartasImg[0]);
			btnCarta2.setIcon(cartasImg[9]);
			btnCarta3.setIcon(cartasImg[40]);
			
		}
		
		btnCarta1.setBorderPainted(false);
		btnCarta2.setBorderPainted(false);
		btnCarta3.setBorderPainted(false);
		
		TableLayout.Constraint constraintMuestra = layMedio.createConstraint();
		constraintMuestra.setWidthPercentage(30);
		
		TableLayout.Constraint constraintMesa = layMedio.createConstraint();
		constraintMesa.setWidthPercentage(70);
		
		TableLayout layMesa = new TableLayout(1,2);
		contMesa = new Container(layMesa);
		TableLayout.Constraint constraintCartaJugada1 = layMesa.createConstraint();
		constraintCartaJugada1.setWidthPercentage(50);
		
		TableLayout.Constraint constraintCartaJugada2 = layMesa.createConstraint();
		constraintCartaJugada2.setWidthPercentage(50);
		
		contMesa.addComponent(constraintCartaJugada1,cartaJugada1);
		contMesa.addComponent(constraintCartaJugada2,cartaJugada2);
		
		contMedio.addComponent(constraintMuestra, muestra);
		contMedio.addComponent(constraintMesa, contMesa);
		
		f.addComponent(contMedio);
		
		
		TableLayout layCartas = new TableLayout(1,3);
		Container buttonBar = new Container(layCartas);
		
		TableLayout.Constraint constraint = layCartas.createConstraint();
		constraint.setWidthPercentage(32);
		
		buttonBar.addComponent(constraint,btnCarta1);
		
		constraint = layCartas.createConstraint();
		constraint.setWidthPercentage(32);
		
		buttonBar.addComponent(constraint,btnCarta2);
		
		constraint = layCartas.createConstraint();
		constraint.setWidthPercentage(32);
		
		buttonBar.addComponent(constraint,btnCarta3);

		f.addComponent(buttonBar);
		
		cargaDeListeners(f);
		
		habilitarDeshabilitarporTurno(1); // dejo el turno al Servidor
		
	}

	void cargaDeListeners(final Form f){

		ActionListener listenerCartas = new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				Object source = evt.getSource();
			
				if(source == btnCarta1) {
					jugarCarta(1,iCarta1,f);
				}else if(source == btnCarta2) {
					jugarCarta(2,iCarta2,f);
				}else if(source == btnCarta3) {
					jugarCarta(3,iCarta3,f);
				}
				
			}
		};
		btnCarta1.addActionListener(listenerCartas);
		btnCarta2.addActionListener(listenerCartas);
		btnCarta3.addActionListener(listenerCartas);
		
		
	
	}
	
	public String getName() {
		return esServidor ? "Nuevo" : "Unirse";
	}
	
	public void tickerInformar(String msg){
		ticker.setText(msg);
		ticker.startTicker(0, true);
		ticker.repaint();
	}

	public void tickerTanteador(){
		ticker.stopTicker();
		ticker.setText(tanteador);
		ticker.repaint();
	}

	void jugarCarta(int i, int valorCarta, Form f){
		Logger.Log(Log.INFO, this.getClass(), "Se juega carta "+i);

		Button cartaJugada=null;
		
		switch (i) {
		case 1:
			cartaJugada=btnCarta1;
			cartasJugadasBtn[0]=true;
			break;

		case 2:
			cartaJugada=btnCarta2;
			cartasJugadasBtn[1]=true;
			break;
		case 3:
			cartaJugada=btnCarta3;
			cartasJugadasBtn[2]=true;
			break;
		default:
			break;
		}
		
		agregarCartaALaMesa(valorCarta);
		
		cartaJugada.setEnabled(false);
		cartaJugada.setVisible(false);
		
		ComandoTruco c = new ComandoTruco();
		c.setComando(ComandoTruco.CMD_JUGAR_CARTA);
		c.addParametro("jugada", ""+valorCarta);

		AccionTruco accion = (AccionTruco) GameLogic.instance().ejecutarComando(c, this);
		
		String strTurno = accion.getMensaje().getParametro("turno");
		if(strTurno!=null){
			//tengo que evaluar turno
			int turno = Integer.valueOf(strTurno).intValue();
			habilitarDeshabilitarporTurno(turno);
		}else{
			Logger.Log(Log.ERROR, this.getClass(), "accion.getMensaje().getParametro(\"turno\");  No existe");
			if(esServidor){ // siempre deshabilito
				habilitarDeshabilitarporTurno(2);
			}else{
				habilitarDeshabilitarporTurno(1);
			}
		}
		
		if(esServidor) controlarFinDeMano(accion);
	}
	
	 private void habilitarDeshabilitarporTurno(int turno){
		if(esServidor){
			if(turno==1){//es mi turno
				habilitarPorturno();
			}else{
				deshabilitarPorturno();
			}
		}else{
			if(turno==2){//es mi turno
				habilitarPorturno();
			}else{
				deshabilitarPorturno();
			}
		}
	}

	public void agregarCartaALaMesa(int carta){
		Logger.Log(Log.INFO, this.getClass(), "agregarCartaALaMesa ");
		if(cartasJugadas[0]==0){
			cartasJugadas[0]=1;
			cartaJugada1.setIcon(cartasImg[carta]);
			
		}else{
			if(cartasJugadas[1]==0){
				cartasJugadas[1]=1;
				cartaJugada2.setIcon(cartasImg[carta]);
				
			}else{
				limpiarMesa();
				cartasJugadas[0]=1;
				cartaJugada1.setIcon(cartasImg[carta]);
				
			}
		}
		contMesa.repaint();
	}
	
	public void limpiarMesa(){
		Logger.Log(Log.INFO, this.getClass(), "limpiarMesa ");
		cartaJugada1.setIcon(null);
		cartaJugada2.setIcon(null);
		cartasJugadas[0]=0;
		cartasJugadas[1]=0;
	}
	
	
	private void deshabilitarPorturno(){
		deshabilitarCartas();
		tickerInformar("Esperando turno");
		esMiTurno=false;
	}
	
	private void habilitarPorturno(){
		 habilitarCartas();
		tickerTanteador();
		esMiTurno=true;
	}
	
	public void habilitarCartas(){
		if(!cartasJugadasBtn[0])btnCarta1.setEnabled(true);
		if(!cartasJugadasBtn[1])btnCarta2.setEnabled(true);
		if(!cartasJugadasBtn[2])btnCarta3.setEnabled(true);
	}
	
	public void deshabilitarCartas(){
		btnCarta1.setEnabled(false);
		btnCarta2.setEnabled(false);
		btnCarta3.setEnabled(false);
	}
	
	public void recibir(AccionBLGUI acc) {
		AccionTruco accion=(AccionTruco)acc;
		Logger.Log(Log.INFO, this.getClass(), "Recibo Accion "+accion.getAccion());
		
		if(accion.getAccion()==AccionTruco.ACCION_CARTA_JUGADA){
			
			int carta = Integer.valueOf(accion.jugada.toSprite()).intValue();
			agregarCartaALaMesa(carta);
			controlarFinDeMano(accion);
			//consulto si hay que realizar calculo de turno
			String strTurno = accion.getMensaje().getParametro("turno");
			Logger.Log(Log.INFO, this.getClass(), "Turno "+strTurno);
			if(strTurno!=null && !strTurno.equals("")){
				//tengo que evaluar turno
				int turno = Integer.valueOf(strTurno).intValue();
				habilitarDeshabilitarporTurno(turno);
			}else{
				if(esServidor){  // siempre habilito
					habilitarDeshabilitarporTurno(1);
				}else{
					habilitarDeshabilitarporTurno(2);
				}
				
			}
		}
		if(accion.getAccion()==AccionTruco.ACCION_TURNO){
			String strTurno = accion.getMensaje().getParametro("turno");
			Logger.Log(Log.INFO, this.getClass(), "Turno "+strTurno);
			controlarFinDeMano(accion);
			if(strTurno!=null && !strTurno.equals("")){
				//tengo que evaluar turno
				int turno = Integer.valueOf(strTurno).intValue();
				habilitarDeshabilitarporTurno(turno);
			}else{
				Logger.Log(Log.ERROR, this.getClass(), "NO LLEGO TURNO en ACCION_TURNO");
			}
		}
		if(accion.getAccion()==AccionTruco.ACCION_MOSTRAR_CARTAS){ // invitado	// servidor
			Logger.Log(Log.INFO, this.getClass(), "ACCION_MOSTRAR_CARTAS");
			tanteador=accion.getMensaje().getParametro("tanteador");
			Logger.Log(Log.INFO, this.getClass(), "LLEGO TANTEADOR "+tanteador);
			refrescoCartasBtn(accion.cartas);
			if(esMiTurno){
				tickerTanteador();
			}
		}
		
		if(accion.getAccion()==AccionTruco.ACCION_GRITE_TRUCO){ // invitado	
			Logger.Log(Log.INFO, this.getClass(), "ACCION_GRITE_TRUCO");
			soundTruco();
			trucoForm.setEstado(TrucoPantalla.ESTADO_ME_GRITARON_TRUCO);
			deshabilitarCartas();
			trucoForm.show();
		}
		
		if(accion.getAccion()==AccionTruco.ACCION_GRITE_RE_TRUCO){ // invitado	
			Logger.Log(Log.INFO, this.getClass(), "ACCION_GRITE_RE_TRUCO");
			trucoForm.setEstado(TrucoPantalla.ESTADO_ME_GRITARON_RETRUCO);
			deshabilitarCartas();
			trucoForm.show();
		}
		
		if(accion.getAccion()==AccionTruco.ACCION_GRITE_VALE_4){ // invitado
			Logger.Log(Log.INFO, this.getClass(), "ACCION_GRITE_VALE_4");
			trucoForm.setEstado(TrucoPantalla.ESTADO_ME_GRITARON_VALE4);
			deshabilitarCartas();
			trucoForm.show();
		}

		if(accion.getAccion()==AccionTruco.ACCION_QUIERO_TRUCO){ // invitado
			Logger.Log(Log.INFO, this.getClass(), "ACCION_QUIERO_TRUCO");
			trucoForm.setEstado(TrucoPantalla.ESTADO_ME_QUISIERON);
			trucoForm.show();
		}
		
		if(accion.getAccion()==AccionTruco.ACCION_NO_QUIERO_TRUCO){ // invitado
			Logger.Log(Log.INFO, this.getClass(), "ACCION_NO_QUIERO_TRUCO");
			trucoForm.setEstado(TrucoPantalla.ESTADO_INICIAL);
			tickerInformar("No se Quiso ...");
			controlarFinDeMano(accion);
			limpiarMesa();
		}
		
		
	}
	
	public void controlarFinDeMano(AccionTruco accion){
		Logger.Log(Log.INFO, this.getClass(), "controlarFinDeMano");
		// fin mano 
		// pedir cartas 
		// actualizar tanteador
		// actualizar manejador de botones cartasJugadasBtn[0]=false;
		if(accion.finMano){
			Logger.Log(Log.INFO, this.getClass(), "FIN DE MANO");
			ComandoTruco c = new ComandoTruco();
			c.setComando(ComandoTruco.CMD_CARTAS_USUARIO);
			
			AccionTruco nuevaaccion = (AccionTruco) GameLogic.instance().ejecutarComando(c, this);
			if(esServidor){
				tanteador=nuevaaccion.getMensaje().getParametro("tanteador");
				Logger.Log(Log.INFO, this.getClass(), "LLEGO TANTEADOR En servidor "+tanteador);
				refrescoCartasBtn(nuevaaccion.cartas); // sin no la accion me viene vacia
				String strTurno = accion.getMensaje().getParametro("turno");
				if(strTurno!=null && !strTurno.equals("")){
					//tengo que evaluar turno
					int turno = Integer.valueOf(strTurno).intValue();
					habilitarDeshabilitarporTurno(turno);
				}else{
					Logger.Log(Log.ERROR, this.getClass(), "NO LLEGO TURNO en ACCION_TURNO");
				}
				if(esMiTurno){
					tickerTanteador();
				}
			}
			// vuelvo a estado inicial las pantallas opcionales
			trucoForm.setEstado(TrucoPantalla.ESTADO_INICIAL);
		}
	}
	
	private void refrescoCartasBtn(Carta cartas[]){
		
		Logger.Log(Log.INFO, this.getClass(), "refrescoCartasBtn");
		setCartas(cartas);
		cartasJugadasBtn[0]=false;
		cartasJugadasBtn[1]=false;
		cartasJugadasBtn[2]=false;
		iMuestra = Integer.valueOf(cartas[3].toSprite()).intValue();
		iCarta1 = Integer.valueOf(cartas[0].toSprite()).intValue();
		iCarta2 = Integer.valueOf(cartas[1].toSprite()).intValue();
		iCarta3 = Integer.valueOf(cartas[2].toSprite()).intValue();
		
		muestra.setIcon(cartasImg[iMuestra].rotate(gradosImgMuestra));
		btnCarta1.setIcon(cartasImg[iCarta1]);
		btnCarta2.setIcon(cartasImg[iCarta2]);
		btnCarta3.setIcon(cartasImg[iCarta3]);
		
		btnCarta1.setEnabled(true);
		btnCarta2.setEnabled(true);
		btnCarta3.setEnabled(true);
		btnCarta1.setVisible(true);
		btnCarta2.setVisible(true);
		btnCarta3.setVisible(true);
		juegoForm.repaint();
	}
	
	private void soundTruco(){
		
			 InputStream in = getClass().getResourceAsStream("/truco.wav");
			 try {
				Player sonido = Manager.createPlayer(in, "audio/x-wav");
				sonido.start();
			} catch (IOException e) {
				Logger.Log(Log.ERROR, this.getClass(), "SONIDO");
				e.printStackTrace();
			} catch (MediaException e) {
				Logger.Log(Log.ERROR, this.getClass(), "SONIDO");
				e.printStackTrace();
			}
			
	}
}
