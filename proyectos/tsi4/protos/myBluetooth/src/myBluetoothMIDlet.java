/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.exploringxml.xml.Node;
import javax.bluetooth.RemoteDevice;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import net.java.dev.marge.autocon.AutoConnect;
import net.java.dev.marge.communication.CommunicationListener;
import net.java.dev.marge.communication.ConnectionListener;
import net.java.dev.marge.entity.Device;
import net.java.dev.marge.entity.ServerDevice;

import com.exploringxml.xml.Xparse;
import java.io.IOException;


/**
 * @author Usuario
 */
public class myBluetoothMIDlet extends MIDlet implements CommandListener, CommunicationListener, ConnectionListener {

    private boolean midletPaused = false;

    private final static String NOMBRESERVIDOR = "myBluetooth ";

    private boolean conecto = false;

    //<editor-fold defaultstate="collapsed" desc=" Generated Fields ">//GEN-BEGIN:|fields|0|
    private Form form;
    private TextField textField;
    private List list;
    private Command exitCommand;
    private Command exitCommand1;
    private Command okCommand;
    //</editor-fold>//GEN-END:|fields|0|
    private Device device;
    //</editor-fold>

    /**
     * The myBluetoothMIDlet constructor.
     */
    public myBluetoothMIDlet() {
    }

    //<editor-fold defaultstate="collapsed" desc=" Generated Methods ">//GEN-BEGIN:|methods|0|
    //</editor-fold>//GEN-END:|methods|0|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: initialize ">//GEN-BEGIN:|0-initialize|0|0-preInitialize
    /**
     * Initilizes the application.
     * It is called only once when the MIDlet is started. The method is called before the <code>startMIDlet</code> method.
     */
    private void initialize() {//GEN-END:|0-initialize|0|0-preInitialize
        // write pre-initialize user code here
//GEN-LINE:|0-initialize|1|0-postInitialize
        // write post-initialize user code here
    }//GEN-BEGIN:|0-initialize|2|
    //</editor-fold>//GEN-END:|0-initialize|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: startMIDlet ">//GEN-BEGIN:|3-startMIDlet|0|3-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Started point.
     */
    public void startMIDlet() {//GEN-END:|3-startMIDlet|0|3-preAction
        // write pre-action user code here
        switchDisplayable(null, getList());//GEN-LINE:|3-startMIDlet|1|3-postAction
        // write post-action user code here
    }//GEN-BEGIN:|3-startMIDlet|2|
    //</editor-fold>//GEN-END:|3-startMIDlet|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: resumeMIDlet ">//GEN-BEGIN:|4-resumeMIDlet|0|4-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Resumed point.
     */
    public void resumeMIDlet() {//GEN-END:|4-resumeMIDlet|0|4-preAction
        // write pre-action user code here
//GEN-LINE:|4-resumeMIDlet|1|4-postAction
        // write post-action user code here
    }//GEN-BEGIN:|4-resumeMIDlet|2|
    //</editor-fold>//GEN-END:|4-resumeMIDlet|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: switchDisplayable ">//GEN-BEGIN:|5-switchDisplayable|0|5-preSwitch
    /**
     * Switches a current displayable in a display. The <code>display</code> instance is taken from <code>getDisplay</code> method. This method is used by all actions in the design for switching displayable.
     * @param alert the Alert which is temporarily set to the display; if <code>null</code>, then <code>nextDisplayable</code> is set immediately
     * @param nextDisplayable the Displayable to be set
     */
    public void switchDisplayable(Alert alert, Displayable nextDisplayable) {//GEN-END:|5-switchDisplayable|0|5-preSwitch
        // write pre-switch user code here
        Display display = getDisplay();//GEN-BEGIN:|5-switchDisplayable|1|5-postSwitch
        if (alert == null) {
            display.setCurrent(nextDisplayable);
        } else {
            display.setCurrent(alert, nextDisplayable);
        }//GEN-END:|5-switchDisplayable|1|5-postSwitch
        // write post-switch user code here
    }//GEN-BEGIN:|5-switchDisplayable|2|
    //</editor-fold>//GEN-END:|5-switchDisplayable|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: commandAction for Displayables ">//GEN-BEGIN:|7-commandAction|0|7-preCommandAction
    /**
     * Called by a system to indicated that a command has been invoked on a particular displayable.
     * @param command the Command that was invoked
     * @param displayable the Displayable where the command was invoked
     */
    public void commandAction(Command command, Displayable displayable) {//GEN-END:|7-commandAction|0|7-preCommandAction
        // write pre-action user code here
        if (displayable == form) {//GEN-BEGIN:|7-commandAction|1|28-preAction
            if (command == exitCommand1) {//GEN-END:|7-commandAction|1|28-preAction
                // write pre-action user code here
                exitMIDlet();//GEN-LINE:|7-commandAction|2|28-postAction
                // write post-action user code here
            } else if (command == okCommand) {//GEN-LINE:|7-commandAction|3|30-preAction
                // write pre-action user code here
                String mensaje = this.getTextField().getString();
                this.device.send(mensaje.getBytes());
                this.getForm().append(new StringItem("Enviado ", mensaje));
                this.getTextField().setString("");

//GEN-LINE:|7-commandAction|4|30-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|5|17-preAction
        } else if (displayable == list) {
            if (command == List.SELECT_COMMAND) {//GEN-END:|7-commandAction|5|17-preAction
                // write pre-action user code here
                listAction();//GEN-LINE:|7-commandAction|6|17-postAction
                // write post-action user code here
            } else if (command == exitCommand) {//GEN-LINE:|7-commandAction|7|20-preAction
                // write pre-action user code here
                exitMIDlet();//GEN-LINE:|7-commandAction|8|20-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|9|7-postCommandAction
        }//GEN-END:|7-commandAction|9|7-postCommandAction
        // write post-action user code here
    }//GEN-BEGIN:|7-commandAction|10|
    //</editor-fold>//GEN-END:|7-commandAction|10|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: form ">//GEN-BEGIN:|14-getter|0|14-preInit
    /**
     * Returns an initiliazed instance of form component.
     * @return the initialized component instance
     */
    public Form getForm() {
        if (form == null) {//GEN-END:|14-getter|0|14-preInit
            // write pre-init user code here
            form = new Form("form", new Item[] { getTextField() });//GEN-BEGIN:|14-getter|1|14-postInit
            form.addCommand(getExitCommand1());
            form.addCommand(getOkCommand());
            form.setCommandListener(this);//GEN-END:|14-getter|1|14-postInit
            // write post-init user code here
        }//GEN-BEGIN:|14-getter|2|
        return form;
    }
    //</editor-fold>//GEN-END:|14-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: list ">//GEN-BEGIN:|15-getter|0|15-preInit
    /**
     * Returns an initiliazed instance of list component.
     * @return the initialized component instance
     */
    public List getList() {
        if (list == null) {//GEN-END:|15-getter|0|15-preInit
            // write pre-init user code here
            list = new List("list", Choice.IMPLICIT);//GEN-BEGIN:|15-getter|1|15-postInit
            list.append("Servidor", null);
            list.append("Cliente", null);
            list.addCommand(getExitCommand());
            list.setCommandListener(this);
            list.setSelectedFlags(new boolean[] { false, false });//GEN-END:|15-getter|1|15-postInit
            // write post-init user code here
        }//GEN-BEGIN:|15-getter|2|
        return list;
    }
    //</editor-fold>//GEN-END:|15-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: listAction ">//GEN-BEGIN:|15-action|0|15-preAction
    /**
     * Performs an action assigned to the selected list element in the list component.
     */
    public void listAction() {//GEN-END:|15-action|0|15-preAction

        new Thread(){

            public void run() {
                super.run();

        // enter pre-action user code here

                String __selectedString = getList().getString(getList().getSelectedIndex());//GEN-BEGIN:|15-action|1|21-preAction
                if (__selectedString != null) {
                    if (__selectedString.equals("Servidor")) {//GEN-END:|15-action|1|21-preAction
                // write pre-action user code here
                AutoConnect.createServer(NOMBRESERVIDOR, myBluetoothMIDlet.this, myBluetoothMIDlet.this);

                while (!conecto) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                }

                switchDisplayable(null, getForm());//GEN-LINE:|15-action|2|21-postAction
                // write post-action user code here
                    } else if (__selectedString.equals("Cliente")) {//GEN-LINE:|15-action|3|22-preAction
                        try {
                            // write pre-action user code here
                            myBluetoothMIDlet.this.device = AutoConnect.connectToServer(NOMBRESERVIDOR, myBluetoothMIDlet.this);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                        switchDisplayable(null, getForm());//GEN-LINE:|15-action|4|22-postAction
                // write post-action user code here
                    }//GEN-BEGIN:|15-action|5|15-postAction
                }//GEN-END:|15-action|5|15-postAction
        // enter post-action user code here
            }


        }.start();
    }//GEN-BEGIN:|15-action|6|
    //</editor-fold>//GEN-END:|15-action|6|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand ">//GEN-BEGIN:|19-getter|0|19-preInit
    /**
     * Returns an initiliazed instance of exitCommand component.
     * @return the initialized component instance
     */
    public Command getExitCommand() {
        if (exitCommand == null) {//GEN-END:|19-getter|0|19-preInit
            // write pre-init user code here
            exitCommand = new Command("Exit", Command.EXIT, 0);//GEN-LINE:|19-getter|1|19-postInit
            // write post-init user code here
        }//GEN-BEGIN:|19-getter|2|
        return exitCommand;
    }
    //</editor-fold>//GEN-END:|19-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: textField ">//GEN-BEGIN:|31-getter|0|31-preInit
    /**
     * Returns an initiliazed instance of textField component.
     * @return the initialized component instance
     */
    public TextField getTextField() {
        if (textField == null) {//GEN-END:|31-getter|0|31-preInit
            // write pre-init user code here
            textField = new TextField("xml :", null, 256, TextField.ANY);//GEN-BEGIN:|31-getter|1|31-postInit
            textField.setPreferredSize(-1, -1);//GEN-END:|31-getter|1|31-postInit
            // write post-init user code here
        }//GEN-BEGIN:|31-getter|2|
        return textField;
    }
    //</editor-fold>//GEN-END:|31-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand1 ">//GEN-BEGIN:|27-getter|0|27-preInit
    /**
     * Returns an initiliazed instance of exitCommand1 component.
     * @return the initialized component instance
     */
    public Command getExitCommand1() {
        if (exitCommand1 == null) {//GEN-END:|27-getter|0|27-preInit
            // write pre-init user code here
            exitCommand1 = new Command("Exit", Command.EXIT, 0);//GEN-LINE:|27-getter|1|27-postInit
            // write post-init user code here
        }//GEN-BEGIN:|27-getter|2|
        return exitCommand1;
    }
    //</editor-fold>//GEN-END:|27-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand ">//GEN-BEGIN:|29-getter|0|29-preInit
    /**
     * Returns an initiliazed instance of okCommand component.
     * @return the initialized component instance
     */
    public Command getOkCommand() {
        if (okCommand == null) {//GEN-END:|29-getter|0|29-preInit
            // write pre-init user code here
            okCommand = new Command("Ok", Command.OK, 0);//GEN-LINE:|29-getter|1|29-postInit
            // write post-init user code here
        }//GEN-BEGIN:|29-getter|2|
        return okCommand;
    }
    //</editor-fold>//GEN-END:|29-getter|2|

    /**
     * Returns a display instance.
     * @return the display instance.
     */
    public Display getDisplay () {
        return Display.getDisplay(this);
    }

    /**
     * Exits MIDlet.
     */
    public void exitMIDlet() {
        switchDisplayable (null, null);
        destroyApp(true);
        notifyDestroyed();
    }

    /**
     * Called when MIDlet is started.
     * Checks whether the MIDlet have been already started and initialize/starts or resumes the MIDlet.
     */
    public void startApp() {
        if (midletPaused) {
            resumeMIDlet ();
        } else {
            initialize ();
            startMIDlet ();
        }
        midletPaused = false;
    }

    /**
     * Called when MIDlet is paused.
     */
    public void pauseApp() {
        midletPaused = true;
    }

    /**
     * Called to signal the MIDlet to terminate.
     * @param unconditional if true, then the MIDlet has to be unconditionally terminated and all resources has to be released.
     */
    public void destroyApp(boolean unconditional) {
    }

    public void receiveMessage(byte[] mensaje) {
        String strMensaje = new String(mensaje);

        strMensaje = procesarXml(strMensaje);

        String mostrar = "NARANJA";

        if(strMensaje!=null){
        mostrar = strMensaje;
        }

        this.getForm().append(new StringItem("Llego : ",mostrar ));
    }

    public void errorOnReceiving(IOException ioe) {
        ioe.printStackTrace();
    }

    public void errorOnSending(IOException ioe) {
        ioe.printStackTrace();
    }

    public void connectionEstablished(ServerDevice sd, RemoteDevice rd) {
        this.device = sd;
        this.conecto = true;
    }

    public void errorOnConnection(IOException ioe) {
        ioe.printStackTrace();
    }

    public void initialisationSucessful() {
       // throw new UnsupportedOperationException("Not supported yet.");
    }

    private String procesarXml(String xml) {
      // <login><status><message> taran </message></status></login>
       Node root = new Xparse().parse(xml);

        String mensaje ="Ups";

        int occur[] = {1};
        Node doc = root.find("login", occur);
        
        if (doc == null) {
             mensaje = "<login> missing.";
             System.out.println("login: " + mensaje);
        }

        int occur2[] = {1, 1};
        Node n = doc.find("status/message", occur2);
        if (n == null) {
             mensaje = "<status><message> missing.";
        }else{
            mensaje = n.getCharacters();
            System.out.println("Mensaje: " + mensaje);
        }
        return  mensaje ;
       // throw new UnsupportedOperationException("Not yet implemented");
    }

}
