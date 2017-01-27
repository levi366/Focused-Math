/*
 * Construtor open login gui
 * Main opens frame
 * Can also update main panel
 * 
 */
package studproj;

import javax.swing.*;
import java.awt.event.*;

public class StudProj extends JFrame  {
    
    //Panel allows updates to main frame
    static JPanel main = new JPanel();
    
    //Communicates with MindWave accross program
    static brain eegPort = new brain();
    static public boolean connected = false;
   
    //Constructor starts login process and adds it to frame
    public StudProj() {

        loginGui temp = new loginGui(this);
        main = temp.returnLog();
        add(main);
    }
    //adds new panel to program frame
    public void changePanel(JPanel x) {
        remove(main);
        main = x;
        add(main);
        revalidate();
    
    }
    //Exit popup
    static WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(new JFrame(), "Are You Sure to Close Application?", 
                        "Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == JOptionPane.YES_OPTION && connected) {
                   StudProj.eegPort.done();
                   loginGui.dataConnection.close(loginGui.student);
                   System.exit(0);
                } 
                else {
                    StudProj.eegPort.done();
                    System.exit(0);
                } 
            }
        };
        
    public static void main(String args[]) {
        
        //Creates frame
        StudProj mathTut = new StudProj();

        mathTut.addWindowListener(exitListener);
        mathTut.setSize(800,500);
        mathTut.setVisible(true);
        mathTut.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        eegPort.run();
      
    }
}
