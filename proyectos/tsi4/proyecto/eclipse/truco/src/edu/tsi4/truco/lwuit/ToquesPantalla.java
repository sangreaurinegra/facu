package edu.tsi4.truco.lwuit;

import com.sun.lwuit.Button;
import com.sun.lwuit.Form;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;

public class ToquesPantalla extends Form{

	Button btnEnvido = null;
	Button btn3Envido = null;
	Button btnRealEnvido = null;
	Button btnFaltaEnvido = null;
	
	public ToquesPantalla(){
		super("Toques");
		
		 this.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
         
		 btnEnvido = new Button("Envido");
		 btn3Envido = new Button("3 Tantos");
		 btnRealEnvido = new Button("Real");
		 btnFaltaEnvido = new Button("Falta");
		 
//		 btnEnvido.setBorderPainted(false);
//		 btn3Envido.setBorderPainted(false);
//		 btnRealEnvido.setBorderPainted(false);
//		 btnFaltaEnvido.setBorderPainted(false);
		 
		 ActionListener listenerEnvido = new ActionListener() {

				public void actionPerformed(ActionEvent evt) {
					Object source = evt.getSource();
				
					if(source == btnEnvido) {
						
					}else if(source == btn3Envido) {
						//jugarCarta(2,iCarta2,f);
					}else if(source == btnRealEnvido) {
						//jugarCarta(3,iCarta3,f);
					}else if(source == btnFaltaEnvido) {
						//jugarCarta(3,iCarta3,f);
					}
					
				}
			};
			btnEnvido.addActionListener(listenerEnvido);
			btn3Envido.addActionListener(listenerEnvido);
			btnRealEnvido.addActionListener(listenerEnvido);
			btnFaltaEnvido.addActionListener(listenerEnvido);
        
			this.addComponent(btnEnvido);
			this.addComponent(btn3Envido);
			this.addComponent(btnRealEnvido);
			this.addComponent(btnFaltaEnvido);
		
	}
	
	
}
