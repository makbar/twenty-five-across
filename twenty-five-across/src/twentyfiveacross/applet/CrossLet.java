package twentyfiveacross.applet;

import javax.swing.JApplet;

import crosswordsage.MainScreen;

public class CrossLet extends JApplet {
    private static final long serialVersionUID = 1L;

    //Called when this applet is loaded into the browser.
    public void init() {
        //Execute a job on the event-dispatching thread; creating this applet's GUI.
        try {
            /*SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    JLabel lbl = new JLabel("Hello World Two");
                    add(lbl);
                    JLabel lbl2 = new JLabel("Goodbye, Cruel World");
                    add(lbl2);
                }
            });*/
            MainScreen mainscreen = new MainScreen();
            mainscreen.setTitle("Crossword Window");
            mainscreen.setVisible(true);
        	/* setSize(300,100);

        	JFrame f = new JFrame();
        	f.setTitle("Frame");
        	f.setSize(200,100);
        	f.setVisible(true);*/
        } catch (Exception e) {
            System.err.println("createGUI didn't complete successfully");
        }
    }
}
