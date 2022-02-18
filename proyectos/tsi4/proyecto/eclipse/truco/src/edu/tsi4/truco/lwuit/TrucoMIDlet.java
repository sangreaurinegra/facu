package edu.tsi4.truco.lwuit;

import java.io.IOException;
import java.util.Hashtable;

import javax.microedition.io.PushRegistry;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.MessageListener;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Display;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.animations.Transition;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.events.FocusListener;
import com.sun.lwuit.impl.midp.VKBImplementationFactory;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.GridLayout;
import com.sun.lwuit.plaf.LookAndFeel;
import com.sun.lwuit.plaf.UIManager;
import com.sun.lwuit.util.Resources;

import edu.tsi4.truco.bl.truco.mensaje.MensajeAbstracto;
import edu.tsi4.truco.config.ConfigManager;
import edu.tsi4.truco.data.comm.sms.SmsHelper;

public class TrucoMIDlet extends MIDlet implements ActionListener {
	
	private static final int EXIT_COMMAND = 1;
    private static final int RUN_COMMAND = 2;
    private static final int BACK_COMMAND = 3;
    private static final int ABOUT_COMMAND = 4;
    private static final int DRAG_MODE_COMMAND = 5;
    private static final int SCROLL_MODE_COMMAND = 6;
    private static final int RTL_COMMAND = 7;
    
    private static final Command runCommand = new Command("Run", RUN_COMMAND);
    private static final Command exitCommand = new Command("Exit", EXIT_COMMAND);
    private static final Command backCommand = new Command("Back", BACK_COMMAND);
    private static final Command aboutCommand = new Command("About", ABOUT_COMMAND);
    private static final Command dragModeCommand = new Command("Drag Mode", DRAG_MODE_COMMAND);
    private static final Command scrollModeCommand = new Command("Scroll Mode", SCROLL_MODE_COMMAND);
    private static final Command rtlCommand = new Command("RTL", RTL_COMMAND);
    private static final Pantalla[] Pantallas = new Pantalla[]{
    	new InvitacionPantalla(true), new InvitacionPantalla(false), new ConfiguracionPantalla(),new TransitionDemo()};
    // new JuegoPantalla(false),
	
    private int elementWidth;
    private int cols = 0;
    
	private Resources res;
	private static Transition componentTransitions;
	private static MainScreenForm mainMenu;
	private Pantalla currentPantalla;
	private Hashtable pantallasHash = new Hashtable();
	
	private boolean smsinit = false; // dice si se inicio por sms o no
	private MensajeAbstracto msg = null;
	public TrucoMIDlet() {
		// TODO Auto-generated constructor stub
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// cerramos la configuracion
		try {
			ConfigManager.closeConfig();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void startApp() throws MIDletStateChangeException {
		try {
			// iniciamos la configuracion
			ConfigManager.initConfig();
			String[] connections = PushRegistry.listConnections(true);
			
            if ((connections != null) && (connections.length > 0)) { // el usuario inicio el midlet	
            	System.out.println(">>>>>>>>>>>>>>>>> ENTRO A RECIBIR SMS---- "+connections.length+" conexion:"+connections[0]);
               	SmsHelper sh = new SmsHelper();
            	msg = sh.recibir();
            	System.out.println(">>>>>>>>>>>>>>>>> SMS : "+msg.toXml());
            	smsinit = true;
            }	
				
				//By using the VKBImplementationFactory.init() we automatically 
				//bundle the LWUIT Virtual Keyboard.
				//If your application is not aimed to touch screen devices, 
				//this line of code should be removed.
				VKBImplementationFactory.init();
				Display.init(this);
				//set the theme
				Resources theme = Resources.open("/LWUITtheme.res");
				UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
				//open the resources file that contains all the icons
				res = Resources.open("/resources.res");
				//although calling directly to setMainForm(r2) will work on
				//most devices, a good coding practice will be to allow the midp 
				//thread to return and to do all the UI on the EDT.
				Display.getInstance().callSerially(new Runnable() {
				    public void run() {
				        setMainForm(res);
				        if(msg != null && smsinit){
				        	InvitacionPantalla ip = (InvitacionPantalla)Pantallas[1];
			            	ip.setNombreUsuario(msg.getParametro("creador"));
			            	currentPantalla = ip;
			            	currentPantalla.run(backCommand, TrucoMIDlet.this);
				        }
				    }
				});
            
		} catch (Throwable ex) {
		    ex.printStackTrace();
		    Dialog.show("Exception", ex.getMessage(), "OK", null);
		}

	}
	
	public static void setComponentTransition(Transition t) {
        if (t != null) {
            mainMenu.setSmoothScrolling(false);
        }
        componentTransitions = t;
    }

    public static Transition getComponentTransition() {
        return componentTransitions;
    }
	
	 void setMainForm(Resources r) {
	        UIManager.getInstance().setResourceBundle(r.getL10N("localize", "en"));

	        MainScreenForm main = new MainScreenForm(this, "Truco");
	        if(mainMenu != null){
	            main.setTransitionInAnimator(mainMenu.getTransitionInAnimator());
	            main.setTransitionOutAnimator(mainMenu.getTransitionOutAnimator());
	        }else{
	            main.setTransitionOutAnimator(CommonTransitions.createFade(400));
	        }
	        mainMenu = main;
	        
	        // application logic determins the number of columns based on the screen size
	        // this is why we need to be aware of screen size changes which is currently
	        // only received using this approach
	        int width = Display.getInstance().getDisplayWidth(); //get the display width 

	        elementWidth = 0;

	        
	        Image[] selectedImages = new Image[Pantallas.length];
	        Image[] unselectedImages = new Image[Pantallas.length];

	        final ButtonActionListener bAListner = new ButtonActionListener();
	        for (int i = 0; i < Pantallas.length; i++) {
	            Image temp = r.getImage(Pantallas[i].getName() + "_sel.png");
	            selectedImages[i] = temp;
	            unselectedImages[i] = r.getImage(Pantallas[i].getName() + "_unsel.png");
	            final Button b = new Button(Pantallas[i].getName(), unselectedImages[i]);
	            b.setUIID("Juego");
	            b.setRolloverIcon(selectedImages[i]);
	            b.setAlignment(Label.CENTER);
	            b.setTextPosition(Label.BOTTOM);
	            mainMenu.addComponent(b);
	            b.addActionListener(bAListner);
	            b.addFocusListener(new FocusListener() {

	                public void focusGained(Component cmp) {
	                    if (componentTransitions != null) {
	                        mainMenu.replace(b, b, componentTransitions);
	                    }
	                }

	                public void focusLost(Component cmp) {
	                }
	            });

	            pantallasHash.put(b, Pantallas[i]);
	            elementWidth = Math.max(b.getPreferredW(), elementWidth);
	        }

	        //Calculate the number of columns for the GridLayout according to the 
	        //screen width
	        if(cols == 0){
	            cols = width / elementWidth;
	        }
	        int rows = Pantallas.length / cols;
	        mainMenu.setLayout(new GridLayout(rows, cols));

	        mainMenu.addCommand(exitCommand);
	        mainMenu.addCommand(aboutCommand);
	        mainMenu.addCommand(rtlCommand);
	        mainMenu.addCommand(dragModeCommand);
	        mainMenu.addCommand(runCommand);

	        mainMenu.setCommandListener(this);
	        //if(!smsinit)
	        	mainMenu.show();
	    }

	 
	 /**
	     * Invoked when a command is clicked. We could derive from Command but that would 
	     * require 3 separate classes.
	     */
	    public void actionPerformed(ActionEvent evt) {
	        Command cmd = evt.getCommand();
	        switch (cmd.getId()) {
	            case RUN_COMMAND:
	                currentPantalla = ((Pantalla) (pantallasHash.get(mainMenu.getFocused())));
	                currentPantalla.run(backCommand, this);
	                break;
	            case EXIT_COMMAND:
	                notifyDestroyed();
	                break;
	            case BACK_COMMAND:
	                currentPantalla.cleanup();
	                mainMenu.show();
	                break;
	            case ABOUT_COMMAND:
	                Form aboutForm = new Form("About");
	                aboutForm.setScrollable(false);
	                aboutForm.setLayout(new BorderLayout());
	                TextArea aboutText = new TextArea(getAboutText(), 5, 10);
	                aboutText.setEditable(false);
	                aboutForm.addComponent(BorderLayout.CENTER, aboutText);
	                aboutForm.addCommand(new Command("Back") {

	                    public void actionPerformed(ActionEvent evt) {
	                        mainMenu.show();
	                    }
	                });
	                aboutForm.show();
	                break;
	            case DRAG_MODE_COMMAND:
	                mainMenu.setDragMode(true);
	                mainMenu.removeCommand(dragModeCommand);
	                mainMenu.addCommand(scrollModeCommand);
	                break;
	            case SCROLL_MODE_COMMAND:
	                mainMenu.setDragMode(false);
	                mainMenu.removeCommand(scrollModeCommand);
	                mainMenu.addCommand(dragModeCommand);
	                break;
	            case RTL_COMMAND:
	                LookAndFeel laf = UIManager.getInstance().getLookAndFeel();
	                laf.setRTL(!laf.isRTL());
	                setMainForm(res);
	                break;
	        }
	    }
	    
	    private String getAboutText() {
	        return "Entrega Tsi4 Gabriel Centurion, Maximiliano Felix";
	    }
	 
	 private class ButtonActionListener implements ActionListener {

	        public void actionPerformed(ActionEvent evt) {
	        	currentPantalla = ((Pantalla) (pantallasHash.get(evt.getSource())));
	        	currentPantalla.run(backCommand, TrucoMIDlet.this);
	        }
	 }

	  public static Resources getResource(String name) throws IOException {
          return Resources.open("/" + name + ".res");
  }
	  
	   public static void setMenuTransition(Transition in, Transition out) {
	        mainMenu.setMenuTransitions(in, out);
	        UIManager.getInstance().getLookAndFeel().setDefaultMenuTransitionIn(in);
	        UIManager.getInstance().getLookAndFeel().setDefaultMenuTransitionOut(out);
	    }
	   public static void setTransition(Transition in, Transition out) {
	        mainMenu.setTransitionInAnimator(in);
	        mainMenu.setTransitionOutAnimator(out);
	    }

	   
}
