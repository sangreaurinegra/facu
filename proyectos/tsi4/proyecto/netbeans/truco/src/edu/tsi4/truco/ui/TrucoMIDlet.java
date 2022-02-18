/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tsi4.truco.ui;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import org.netbeans.microedition.lcdui.SplashScreen;

/**
 * @author Usuario
 */
public class TrucoMIDlet extends MIDlet implements CommandListener {

    private boolean midletPaused = false;
    private String usuario = "";

    //<editor-fold defaultstate="collapsed" desc=" Generated Fields ">//GEN-BEGIN:|fields|0|
    private Form mainForm;
    private TextField textField3;
    private TextField textField4;
    private List menuInicial;
    private SplashScreen splashScreen;
    private Form datosUsuarioform;
    private TextField textField;
    private Form configForm;
    private TextField textField2;
    private TextField textField1;
    private Command exitCommand;
    private Command exitCommand1;
    private Command exitCommand2;
    private Command backCommand;
    private Command backCommand1;
    private Command okCommand;
    private Command cancelCommand;
    private Image image1;
    private Font font;
    //</editor-fold>//GEN-END:|fields|0|

    /**
     * The TrucoMIDlet constructor.
     */
    public TrucoMIDlet() {
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
        switchDisplayable(null, getSplashScreen());//GEN-LINE:|3-startMIDlet|1|3-postAction
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
        if (displayable == configForm) {//GEN-BEGIN:|7-commandAction|1|61-preAction
            if (command == cancelCommand) {//GEN-END:|7-commandAction|1|61-preAction
                // write pre-action user code here
                switchDisplayable(null, getMenuInicial());//GEN-LINE:|7-commandAction|2|61-postAction
                // write post-action user code here
            } else if (command == okCommand) {//GEN-LINE:|7-commandAction|3|63-preAction
                // write pre-action user code here
                switchDisplayable(null, getMenuInicial());//GEN-LINE:|7-commandAction|4|63-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|5|48-preAction
        } else if (displayable == datosUsuarioform) {
            if (command == backCommand) {//GEN-END:|7-commandAction|5|48-preAction
                // write pre-action user code here
                switchDisplayable(null, getMenuInicial());//GEN-LINE:|7-commandAction|6|48-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|7|16-preAction
        } else if (displayable == mainForm) {
            if (command == exitCommand) {//GEN-END:|7-commandAction|7|16-preAction
                // write pre-action user code here
                exitMIDlet();//GEN-LINE:|7-commandAction|8|16-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|9|21-preAction
        } else if (displayable == menuInicial) {
            if (command == List.SELECT_COMMAND) {//GEN-END:|7-commandAction|9|21-preAction
                // write pre-action user code here
                menuInicialAction();//GEN-LINE:|7-commandAction|10|21-postAction
                // write post-action user code here
            } else if (command == exitCommand1) {//GEN-LINE:|7-commandAction|11|24-preAction
                // write pre-action user code here
                exitMIDlet();//GEN-LINE:|7-commandAction|12|24-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|13|33-preAction
        } else if (displayable == splashScreen) {
            if (command == SplashScreen.DISMISS_COMMAND) {//GEN-END:|7-commandAction|13|33-preAction
                // write pre-action user code here
                switchDisplayable(null, getMenuInicial());//GEN-LINE:|7-commandAction|14|33-postAction
                // write post-action user code here
            } else if (command == exitCommand2) {//GEN-LINE:|7-commandAction|15|39-preAction
                // write pre-action user code here
//GEN-LINE:|7-commandAction|16|39-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|17|7-postCommandAction
        }//GEN-END:|7-commandAction|17|7-postCommandAction
        // write post-action user code here
    }//GEN-BEGIN:|7-commandAction|18|
    //</editor-fold>//GEN-END:|7-commandAction|18|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: mainForm ">//GEN-BEGIN:|14-getter|0|14-preInit
    /**
     * Returns an initiliazed instance of mainForm component.
     * @return the initialized component instance
     */
    public Form getMainForm() {
        if (mainForm == null) {//GEN-END:|14-getter|0|14-preInit
            // write pre-init user code here
            mainForm = new Form("Configuraci\u00F3n", new Item[] { getTextField3(), getTextField4() });//GEN-BEGIN:|14-getter|1|14-postInit
            mainForm.addCommand(getExitCommand());
            mainForm.setCommandListener(this);//GEN-END:|14-getter|1|14-postInit
            // write post-init user code here
        }//GEN-BEGIN:|14-getter|2|
        return mainForm;
    }
    //</editor-fold>//GEN-END:|14-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: menuInicial ">//GEN-BEGIN:|19-getter|0|19-preInit
    /**
     * Returns an initiliazed instance of menuInicial component.
     * @return the initialized component instance
     */
    public List getMenuInicial() {
        if (menuInicial == null) {//GEN-END:|19-getter|0|19-preInit
            // write pre-init user code here
            menuInicial = new List("list", Choice.IMPLICIT);//GEN-BEGIN:|19-getter|1|19-postInit
            menuInicial.append("Nueva Partida Bluetooth", null);
            menuInicial.append("Nueva Partida Internet", null);
            menuInicial.append("Unirse Partida Bluetooth", null);
            menuInicial.append("Unirse Partida Internet", null);
            menuInicial.append("Configuraci\u00F3n", null);
            menuInicial.addCommand(getExitCommand1());
            menuInicial.setCommandListener(this);
            menuInicial.setSelectedFlags(new boolean[] { false, false, true, false, false });//GEN-END:|19-getter|1|19-postInit
            // write post-init user code here
        }//GEN-BEGIN:|19-getter|2|
        return menuInicial;
    }
    //</editor-fold>//GEN-END:|19-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: menuInicialAction ">//GEN-BEGIN:|19-action|0|19-preAction
    /**
     * Performs an action assigned to the selected list element in the menuInicial component.
     */
    public void menuInicialAction() {//GEN-END:|19-action|0|19-preAction
        // enter pre-action user code here
        String __selectedString = getMenuInicial().getString(getMenuInicial().getSelectedIndex());//GEN-BEGIN:|19-action|1|25-preAction
        if (__selectedString != null) {
            if (__selectedString.equals("Nueva Partida Bluetooth")) {//GEN-END:|19-action|1|25-preAction
                // write pre-action user code here
                switchDisplayable(null, getDatosUsuarioform());//GEN-LINE:|19-action|2|25-postAction
                // write post-action user code here
            } else if (__selectedString.equals("Nueva Partida Internet")) {//GEN-LINE:|19-action|3|26-preAction
                // write pre-action user code here
                switchDisplayable(null, getDatosUsuarioform());//GEN-LINE:|19-action|4|26-postAction
                // write post-action user code here
            } else if (__selectedString.equals("Unirse Partida Bluetooth")) {//GEN-LINE:|19-action|5|28-preAction
                // write pre-action user code here
                switchDisplayable(null, getDatosUsuarioform());//GEN-LINE:|19-action|6|28-postAction
                // write post-action user code here
            } else if (__selectedString.equals("Unirse Partida Internet")) {//GEN-LINE:|19-action|7|29-preAction
                // write pre-action user code here
                switchDisplayable(null, getDatosUsuarioform());//GEN-LINE:|19-action|8|29-postAction
                // write post-action user code here
            } else if (__selectedString.equals("Configuraci\u00F3n")) {//GEN-LINE:|19-action|9|52-preAction
                // write pre-action user code here
                switchDisplayable(null, getConfigForm());//GEN-LINE:|19-action|10|52-postAction
                // write post-action user code here
            }//GEN-BEGIN:|19-action|11|19-postAction
        }//GEN-END:|19-action|11|19-postAction
        // enter post-action user code here
    }//GEN-BEGIN:|19-action|12|
    //</editor-fold>//GEN-END:|19-action|12|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand ">//GEN-BEGIN:|15-getter|0|15-preInit
    /**
     * Returns an initiliazed instance of exitCommand component.
     * @return the initialized component instance
     */
    public Command getExitCommand() {
        if (exitCommand == null) {//GEN-END:|15-getter|0|15-preInit
            // write pre-init user code here
            exitCommand = new Command("Exit", Command.EXIT, 0);//GEN-LINE:|15-getter|1|15-postInit
            // write post-init user code here
        }//GEN-BEGIN:|15-getter|2|
        return exitCommand;
    }
    //</editor-fold>//GEN-END:|15-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand1 ">//GEN-BEGIN:|23-getter|0|23-preInit
    /**
     * Returns an initiliazed instance of exitCommand1 component.
     * @return the initialized component instance
     */
    public Command getExitCommand1() {
        if (exitCommand1 == null) {//GEN-END:|23-getter|0|23-preInit
            // write pre-init user code here
            exitCommand1 = new Command("Exit", Command.EXIT, 0);//GEN-LINE:|23-getter|1|23-postInit
            // write post-init user code here
        }//GEN-BEGIN:|23-getter|2|
        return exitCommand1;
    }
    //</editor-fold>//GEN-END:|23-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: splashScreen ">//GEN-BEGIN:|31-getter|0|31-preInit
    /**
     * Returns an initiliazed instance of splashScreen component.
     * @return the initialized component instance
     */
    public SplashScreen getSplashScreen() {
        if (splashScreen == null) {//GEN-END:|31-getter|0|31-preInit
            // write pre-init user code here
            splashScreen = new SplashScreen(getDisplay());//GEN-BEGIN:|31-getter|1|31-postInit
            splashScreen.setTitle("splashScreen");
            splashScreen.addCommand(getExitCommand2());
            splashScreen.setCommandListener(this);
            splashScreen.setFullScreenMode(true);
            splashScreen.setImage(getImage1());
            splashScreen.setText("Truco");
            splashScreen.setTextFont(getFont());
            splashScreen.setTimeout(15000);//GEN-END:|31-getter|1|31-postInit
            // write post-init user code here
        }//GEN-BEGIN:|31-getter|2|
        return splashScreen;
    }
    //</editor-fold>//GEN-END:|31-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: image1 ">//GEN-BEGIN:|36-getter|0|36-preInit
    /**
     * Returns an initiliazed instance of image1 component.
     * @return the initialized component instance
     */
    public Image getImage1() {
        if (image1 == null) {//GEN-END:|36-getter|0|36-preInit
            // write pre-init user code here
            try {//GEN-BEGIN:|36-getter|1|36-@java.io.IOException
                image1 = Image.createImage("/truco.jpg");
            } catch (java.io.IOException e) {//GEN-END:|36-getter|1|36-@java.io.IOException
                e.printStackTrace();
            }//GEN-LINE:|36-getter|2|36-postInit
            // write post-init user code here
        }//GEN-BEGIN:|36-getter|3|
        return image1;
    }
    //</editor-fold>//GEN-END:|36-getter|3|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand2 ">//GEN-BEGIN:|38-getter|0|38-preInit
    /**
     * Returns an initiliazed instance of exitCommand2 component.
     * @return the initialized component instance
     */
    public Command getExitCommand2() {
        if (exitCommand2 == null) {//GEN-END:|38-getter|0|38-preInit
            // write pre-init user code here
            exitCommand2 = new Command("Exit", Command.EXIT, 0);//GEN-LINE:|38-getter|1|38-postInit
            // write post-init user code here
        }//GEN-BEGIN:|38-getter|2|
        return exitCommand2;
    }
    //</editor-fold>//GEN-END:|38-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: font ">//GEN-BEGIN:|40-getter|0|40-preInit
    /**
     * Returns an initiliazed instance of font component.
     * @return the initialized component instance
     */
    public Font getFont() {
        if (font == null) {//GEN-END:|40-getter|0|40-preInit
            // write pre-init user code here
            font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE);//GEN-LINE:|40-getter|1|40-postInit
            // write post-init user code here
        }//GEN-BEGIN:|40-getter|2|
        return font;
    }
    //</editor-fold>//GEN-END:|40-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: datosUsuarioform ">//GEN-BEGIN:|42-getter|0|42-preInit
    /**
     * Returns an initiliazed instance of datosUsuarioform component.
     * @return the initialized component instance
     */
    public Form getDatosUsuarioform() {
        if (datosUsuarioform == null) {//GEN-END:|42-getter|0|42-preInit
            // write pre-init user code here
            datosUsuarioform = new Form("form", new Item[] { getTextField() });//GEN-BEGIN:|42-getter|1|42-postInit
            datosUsuarioform.addCommand(getBackCommand());
            datosUsuarioform.setCommandListener(this);//GEN-END:|42-getter|1|42-postInit
            // write post-init user code here
        }//GEN-BEGIN:|42-getter|2|
        return datosUsuarioform;
    }
    //</editor-fold>//GEN-END:|42-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: textField ">//GEN-BEGIN:|50-getter|0|50-preInit
    /**
     * Returns an initiliazed instance of textField component.
     * @return the initialized component instance
     */
    public TextField getTextField() {
        if (textField == null) {//GEN-END:|50-getter|0|50-preInit
            // write pre-init user code here
            textField = new TextField("Usuario", usuario, 32, TextField.ANY);//GEN-LINE:|50-getter|1|50-postInit
            // write post-init user code here
        }//GEN-BEGIN:|50-getter|2|
        return textField;
    }
    //</editor-fold>//GEN-END:|50-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCommand ">//GEN-BEGIN:|47-getter|0|47-preInit
    /**
     * Returns an initiliazed instance of backCommand component.
     * @return the initialized component instance
     */
    public Command getBackCommand() {
        if (backCommand == null) {//GEN-END:|47-getter|0|47-preInit
            // write pre-init user code here
            backCommand = new Command("Back", Command.BACK, 0);//GEN-LINE:|47-getter|1|47-postInit
            // write post-init user code here
        }//GEN-BEGIN:|47-getter|2|
        return backCommand;
    }
    //</editor-fold>//GEN-END:|47-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: configForm ">//GEN-BEGIN:|53-getter|0|53-preInit
    /**
     * Returns an initiliazed instance of configForm component.
     * @return the initialized component instance
     */
    public Form getConfigForm() {
        if (configForm == null) {//GEN-END:|53-getter|0|53-preInit
            // write pre-init user code here
            configForm = new Form("Configuracion", new Item[] { getTextField1(), getTextField2() });//GEN-BEGIN:|53-getter|1|53-postInit
            configForm.addCommand(getCancelCommand());
            configForm.addCommand(getOkCommand());
            configForm.setCommandListener(this);//GEN-END:|53-getter|1|53-postInit
            // write post-init user code here
        }//GEN-BEGIN:|53-getter|2|
        return configForm;
    }
    //</editor-fold>//GEN-END:|53-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: textField1 ">//GEN-BEGIN:|54-getter|0|54-preInit
    /**
     * Returns an initiliazed instance of textField1 component.
     * @return the initialized component instance
     */
    public TextField getTextField1() {
        if (textField1 == null) {//GEN-END:|54-getter|0|54-preInit
            // write pre-init user code here
            textField1 = new TextField("Usuario", "", 32, TextField.ANY);//GEN-LINE:|54-getter|1|54-postInit
            // write post-init user code here
        }//GEN-BEGIN:|54-getter|2|
        return textField1;
    }
    //</editor-fold>//GEN-END:|54-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: textField2 ">//GEN-BEGIN:|55-getter|0|55-preInit
    /**
     * Returns an initiliazed instance of textField2 component.
     * @return the initialized component instance
     */
    public TextField getTextField2() {
        if (textField2 == null) {//GEN-END:|55-getter|0|55-preInit
            // write pre-init user code here
            textField2 = new TextField("URL Servidor Internet", "", 32, TextField.ANY);//GEN-LINE:|55-getter|1|55-postInit
            // write post-init user code here
        }//GEN-BEGIN:|55-getter|2|
        return textField2;
    }
    //</editor-fold>//GEN-END:|55-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCommand1 ">//GEN-BEGIN:|57-getter|0|57-preInit
    /**
     * Returns an initiliazed instance of backCommand1 component.
     * @return the initialized component instance
     */
    public Command getBackCommand1() {
        if (backCommand1 == null) {//GEN-END:|57-getter|0|57-preInit
            // write pre-init user code here
            backCommand1 = new Command("Back", Command.BACK, 0);//GEN-LINE:|57-getter|1|57-postInit
            // write post-init user code here
        }//GEN-BEGIN:|57-getter|2|
        return backCommand1;
    }
    //</editor-fold>//GEN-END:|57-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cancelCommand ">//GEN-BEGIN:|60-getter|0|60-preInit
    /**
     * Returns an initiliazed instance of cancelCommand component.
     * @return the initialized component instance
     */
    public Command getCancelCommand() {
        if (cancelCommand == null) {//GEN-END:|60-getter|0|60-preInit
            // write pre-init user code here
            cancelCommand = new Command("Cancel", Command.CANCEL, 0);//GEN-LINE:|60-getter|1|60-postInit
            // write post-init user code here
        }//GEN-BEGIN:|60-getter|2|
        return cancelCommand;
    }
    //</editor-fold>//GEN-END:|60-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand ">//GEN-BEGIN:|62-getter|0|62-preInit
    /**
     * Returns an initiliazed instance of okCommand component.
     * @return the initialized component instance
     */
    public Command getOkCommand() {
        if (okCommand == null) {//GEN-END:|62-getter|0|62-preInit
            // write pre-init user code here
            okCommand = new Command("Ok", Command.OK, 0);//GEN-LINE:|62-getter|1|62-postInit
            // write post-init user code here
        }//GEN-BEGIN:|62-getter|2|
        return okCommand;
    }
    //</editor-fold>//GEN-END:|62-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: textField3 ">//GEN-BEGIN:|66-getter|0|66-preInit
    /**
     * Returns an initiliazed instance of textField3 component.
     * @return the initialized component instance
     */
    public TextField getTextField3() {
        if (textField3 == null) {//GEN-END:|66-getter|0|66-preInit
            // write pre-init user code here
            textField3 = new TextField("Usuario", "", 32, TextField.ANY);//GEN-LINE:|66-getter|1|66-postInit
            // write post-init user code here
        }//GEN-BEGIN:|66-getter|2|
        return textField3;
    }
    //</editor-fold>//GEN-END:|66-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: textField4 ">//GEN-BEGIN:|67-getter|0|67-preInit
    /**
     * Returns an initiliazed instance of textField4 component.
     * @return the initialized component instance
     */
    public TextField getTextField4() {
        if (textField4 == null) {//GEN-END:|67-getter|0|67-preInit
            // write pre-init user code here
            textField4 = new TextField("Url Servidor web", "", 32, TextField.ANY);//GEN-LINE:|67-getter|1|67-postInit
            // write post-init user code here
        }//GEN-BEGIN:|67-getter|2|
        return textField4;
    }
    //</editor-fold>//GEN-END:|67-getter|2|



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

}
