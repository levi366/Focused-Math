/*
 * Handles the login logic
 * Loads user's historical data if available
 */
package studproj;

import java.io.*;
import java.io.File;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.JFrame;
import javax.swing.*;

public class loginGui extends JFrame implements ActionListener {
    
    private JButton loginBut = new JButton("Login");
    private JPanel panel = new JPanel();
    private JPanel logPanel = new JPanel();
    private JTextField input = new JTextField(15);   //Student answer
    private JLabel status = new JLabel("");
    
    private StudProj holder;                        //Holds main to add panels to GUI
    static String student;
    
    public static studCom dataConnection = new studCom();
   
    //Constructor takes the StudProj object from main to control panels
    public loginGui(StudProj x) {
    
        holder = x;
   
    }
    public JPanel returnLog() {
        
        panel.setBackground(Color.black);
        logPanel.setBackground(Color.black);
        
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        logPanel.setLayout(new FlowLayout());
        
        JLabel title = new JLabel("Focused Math");
        title.setForeground(Color.green);
        status.setForeground(Color.green);
        
        title.setAlignmentX(CENTER_ALIGNMENT);
        
        
        ImageIcon icon = new ImageIcon("3.jpg","Focused Math" );
        JLabel imageIcon = new JLabel(icon);
        imageIcon.setAlignmentX(CENTER_ALIGNMENT);
        
        Font labelFont = title.getFont();
        title.setFont(new Font(labelFont.getName(),Font.PLAIN, 25));
   
        loginBut.addActionListener(this);
        
        logPanel.add(input);
        logPanel.add(loginBut);
        logPanel.add(status);
        panel.add(title);
        panel.add(imageIcon);
        panel.add(logPanel);
        
        
        input.addActionListener(this);
        input.setFocusable(true);
        
        return panel;
    }
    public void actionPerformed(ActionEvent event) {
        student = "";
        String questions = "";
        if(event.getSource() == loginBut) {
            dataConnection.makeConnect(172,20,1,127);
            //Login procedure
            student = input.getText();
            student = student.replace(" ", "");
            student = student.toLowerCase();

            dataConnection.sendCom("newUser");
            
            if (dataConnection.recieveCom().compareTo("ready") == 0) {
                dataConnection.sendCom(student);
                if(dataConnection.recieveCom().compareTo("true") == 0) {
                    dataConnection.sendCom("startQuiz");
                    StudProj.connected = true;
                    dataConnection.recieveFile("questions.csv");
                    dataConnection.sendCom("ready");
                    dataConnection.recieveFile("average.csv");
                    File scores = new File("average.csv");
                    
                    try {
                        
                        Scanner fileScan = new Scanner(scores);
                        String line;						//Variable file is loaded into
                        String comma = ",";					//Used to separate tokens
                        String[] valuesSet = new String[4];
                        
                        /* These are tokens that hold the string divided on commas */
                        String token01;
            
                        int start = 1;
                        int end = 21;
      
                    	/*
                        * while loop loads the next line then using StringTokenizer the data is
                        * separated on commas
                        */
                        line = fileScan.nextLine();

			StringTokenizer test = new StringTokenizer(line);
                        for (int x = 0; x < 4; x++) {
                            
                            token01 = test.nextToken(comma);
                            valuesSet[x] = token01;
			
                        }
                        startGui report = new startGui(holder, student, valuesSet[1], valuesSet[2], valuesSet[3]);
                        holder.changePanel(report.returnGui());
                        fileScan.close();
                } catch (Exception e) {
            
                    
                }

                }else {
                   
                    status.setText("Invalid Username");
                }
                
            }       
        }
    }
}
