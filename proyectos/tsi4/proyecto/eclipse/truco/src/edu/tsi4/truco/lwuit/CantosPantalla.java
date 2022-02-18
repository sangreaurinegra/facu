package edu.tsi4.truco.lwuit;

import com.sun.lwuit.Button;
import com.sun.lwuit.Form;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;

public class CantosPantalla extends Form{

	Button btnFlor = null;
	Button btnContra = null;
	Button btn2Flor = null;
	Button btn3Flor = null;
	Button btn5Flor = null;

	public CantosPantalla(){
		super("Toques");

		this.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

		btnFlor = new Button("Flor");
		btnContra = new Button("Contra");
		btn2Flor = new Button("2 Tantos");
		btn3Flor = new Button("3 Tantos");
		btn5Flor = new Button("5 Tantos");

		ActionListener listenerEnvido = new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				Object source = evt.getSource();

				if(source == btnFlor) {

				}else if(source == btnContra) {
					//jugarCarta(2,iCarta2,f);
				}else if(source == btn2Flor) {
					//jugarCarta(3,iCarta3,f);
				}else if(source == btn3Flor) {
					//jugarCarta(3,iCarta3,f);
				}else if(source == btn5Flor) {
					//jugarCarta(3,iCarta3,f);
				}

			}
		};

		btnFlor.addActionListener(listenerEnvido);
		btnContra.addActionListener(listenerEnvido);
		btn2Flor.addActionListener(listenerEnvido);
		btn3Flor.addActionListener(listenerEnvido);
		btn5Flor.addActionListener(listenerEnvido);

		this.addComponent(btnFlor);
		this.addComponent(btnContra);
		this.addComponent(btn2Flor);
		this.addComponent(btn3Flor);
		this.addComponent(btn5Flor);

	}

}