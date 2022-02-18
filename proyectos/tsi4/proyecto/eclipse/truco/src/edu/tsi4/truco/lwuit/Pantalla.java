package edu.tsi4.truco.lwuit;

import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.geom.Dimension;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.plaf.UIManager;

public abstract class Pantalla {
	
	
	public ActionListener currentCommandListener;
	
	public Command currentBackCommand;
	
	 /**
     * returns the name to display in the list
     */
    public abstract String getName();

    /**
     * Invoked by the main code to start 
     */
    public final void run(final Command backCommand, ActionListener commandListener) {
        System.gc();
        final Form form = new Form(getName());
        
        this.currentCommandListener = commandListener;
        this.currentBackCommand = backCommand; 
        
        form.addCommand(backCommand);
        form.setCommandListener(commandListener);
        form.setBackCommand(backCommand);
 // NO BORRAR       
//        form.addCommand(new Command("Help") {
//            public void actionPerformed(ActionEvent evt) {
//                Form helpForm = new Form("Help");
//                helpForm.setLayout(new BorderLayout());
//                TextArea helpText = new TextArea(getHelpImpl(), 5, 10);
//                helpText.setEditable(false);
//                helpForm.setScrollable(false);
//                helpForm.addComponent(BorderLayout.CENTER, helpText);
//                Command c = new Command("Back") {
//                    public void actionPerformed(ActionEvent evt) {
//                        form.show();
//                    }
//                };
//                helpForm.addCommand(c);
//                helpForm.setBackCommand(c);
//                helpForm.show();
//            }
//        });
        
   
      
        
        execute(form);
        form.show();
    }
    
    /**
     * Returns the text that should appear in the help command
     */
    private String getHelpImpl() {
        String h = getHelp();
        return UIManager.getInstance().localize(h, h);
    }

    /**
     * Returns the text that should appear in the help command
     */
    protected String getHelp() {
        // return a key value for localization
        String n = getClass().getName();
        return n.substring(n.lastIndexOf('.') + 1) + ".help";
    }
    
    /**
     * The demo should place its UI into the given form 
     */
    protected abstract void execute(Form f);

    
    /**
     * Helper method that allows us to create a pair of components label and the given
     * component in a horizontal layout with a minimum label width
     */
    protected Container createPair(String label, Component c, int minWidth) {
        Container pair = new Container(new BorderLayout());
        Label l =  new Label(label);
        Dimension d = l.getPreferredSize();
        d.setWidth(Math.max(d.getWidth(), minWidth));
        l.setPreferredSize(d);
        pair.addComponent(BorderLayout.WEST,l);
        pair.addComponent(BorderLayout.CENTER, c);
        return pair;
    }
    
      /**
     * Helper method that allows us to create a pair of components label and the given
     * component in a horizontal layout
     */
     protected Container createPair(String label, Component c) {
         return createPair(label,c,0);
     }
    
     public void cleanup() {
     }
	
}
