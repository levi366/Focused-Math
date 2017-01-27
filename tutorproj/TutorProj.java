/**
* 
* TutorProj starts the server, loads the users and allows the control gui
* the ability to be updated based on a new user logging in or new data 
* from user.
*
*
*/


package tutorproj;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.*;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;


public class TutorProj extends JFrame implements ActionListener {
    
    private student[] logins = new student[100];
    private static int stuCount = 0;
    
    private static JPanel control = new JPanel();
    private static JPanel main = new JPanel();
    private static JPanel[] activeUserPanel = new JPanel[2];
    private JScrollPane scrollWin;
    private JButton status = new JButton("Begin Tutoring");        
    private JButton history = new JButton("History");
    private JButton history01 =  new JButton("History");
    
    private static student[] logCheck = new student[100];
    private static serverListen startCon = new serverListen();
    private boolean quit = false;
    private int userNum = 0;
    private static int closeFram = 0;
    
    private static String[] activeUser = new String[2];
    
    
    public TutorProj() {
        
        
    }
    //Used to prepare the gui and only used inside TutorProj
    public TutorProj(String y) {
        
        this.setLayout(new BorderLayout());
        
        control.setVisible(true);
        control.add(status);
        
        this.add(control, BorderLayout.NORTH);
        status.addActionListener(this);
        history.addActionListener(this);
        history01.addActionListener(this);
        status.setVisible(true);
        
        main.setPreferredSize(new Dimension(900,600));
        main.setVisible(true);
        
        scrollWin = new JScrollPane(main);
        getContentPane().add(scrollWin);
        scrollWin.setSize(900,900);
        
        add(scrollWin, BorderLayout.CENTER);
        
    }
     /*
     * Loads student accounts from user.csv
     * 
     * If an error occurs at this point server terminated
     */
    public void loadUser() {
        
        try {
            Scanner fileScan = new Scanner(new File("user.csv"));
            
            String line;                        //Variable file is loaded into
            String comma = ",";              //Used to separate tokens

            int x = 0;
            
            while (fileScan.hasNextLine()) {
                
                student temp = new student();
                line = fileScan.nextLine();
                
                StringTokenizer token = new StringTokenizer(line);
                
                temp.setUser(token.nextToken(comma));
                temp.setProf(token.nextToken(comma));
                logins[x]= temp;
                               
                x++;
            }
            stuCount = x;
        } catch (Exception e){
            System.out.println(e);
            System.exit(0);
        }
    }
    //Getter to allow main to access logins
    public student[] retLogins() {
        
        return logins;
    }
    //Getter to allow main to access count of students
    public int retCount() {
        return stuCount;
    }
    /*
     * Allows panels to be changed in the control gui
     * userNum places each panel in an array so it can be updated
     * when users log in and log out
    */
    public void changePanel(JPanel x, String user) {
        JPanel container = new JPanel();
        container.add(x);
        
        if (userNum == 0) {
            userNum = 1;
            container.add(history);
        }
        else {
            userNum = 0;
            container.add(history01);
        }
        
        activeUserPanel[userNum] = container;
        activeUser[userNum] = user;
        
        main.add(activeUserPanel[userNum]);
        
        revalidate();  
 
    }
    public void deletePanel(String x) {
        JPanel blank = new JPanel();
       
       if (activeUser[0] != null && activeUser[0].compareTo(x) == 0) {
           
           main.remove(activeUserPanel[0]);
           activeUser[0] = null;
           userNum = 0;
      
       }
       else {
           
           main.remove(activeUserPanel[1]);
           activeUser[1] = null;
           userNum = 1;
           if (activeUser[0] != null) {
               
               changePanel(activeUserPanel[0], activeUser[0]);
           }
       }
       if (activeUser[0] == null && activeUser[1] == null) {
           
           main.removeAll();
           main.add(blank);
           
       }
       //scrollWin = new JScrollPane(main);
       getContentPane().add(scrollWin);
       revalidate();
       repaint();
       
    }
    //Allows logging in and creation of logout button
    public void actionPerformed(ActionEvent event) {
        
        if (event.getSource() == status) {
            if(!quit) {
                
                startCon.setData(logCheck, stuCount, this);
                startCon.startServer();
                status.setText("Logout");
                quit = true;
                
            } else {
               
                System.exit(0);
            }             
        }
        else if (event.getSource() == history) {
            
             Frame historyPanel = new JFrame();    
            studData individual = new studData();
            String[] data = individual.readData(activeUser[1]);
            String[] temp = new String[2];
            
            JPanel test01 = new JPanel();
            
            test01.setLayout(new GridLayout(data.length/2,0));
            
            for (int x = 0; x < data.length - 1; x = x + 2) {
                
                chart test = new chart();
                
                temp[0] = data[x];
                temp[1] =  data[x + 1];
                
                CategoryDataset dataset = test.createHistDataset(temp);
                
                JFreeChart chart = test.createChart(dataset, activeUser[1]);
                ChartPanel panel = new ChartPanel(chart);
                
                panel.setPreferredSize(new Dimension(900,400));
                panel.setFillZoomRectangle(true);
                panel.setMouseWheelEnabled(true);
            
                test01.add(panel);
                
            }
            
            JScrollPane test02 = new JScrollPane(test01);
            getContentPane().add(test02);
            
            historyPanel.add(test02);
            
            historyPanel.setSize(940,500);
            historyPanel.setVisible(true);
            historyPanel.repaint();
            
        }  
        else if (event.getSource() == history01) {
            
            JFrame historyPanel = new JFrame();
            studData individual = new studData();
            String[] data = individual.readData(activeUser[0]);
            String[] temp = new String[2];
            
            JPanel test01 = new JPanel();
            
            test01.setLayout(new GridLayout(data.length/2,0));
            for (int x = 0; x < data.length - 1; x = x + 2) {
                
                chart test = new chart();

                temp[0] = data[x];
                temp[1] =  data[x + 1];
               
                CategoryDataset dataset = test.createHistDataset(temp);
                
                JFreeChart chart = test.createChart(dataset, activeUser[0]);
                ChartPanel panel = new ChartPanel(chart);
                
                panel.setPreferredSize(new Dimension(900,400));
                panel.setFillZoomRectangle(true);
                panel.setMouseWheelEnabled(true);
            
                test01.add(panel);
                
            }
            
            JScrollPane test02 = new JScrollPane(test01);
            getContentPane().add(test02);
            
            closeFram = 1;
            historyPanel.add(test02);
 
            historyPanel.setSize(940,500);
            historyPanel.setVisible(true);
            historyPanel.repaint();
        }
    }
    public static void main(String[] args) {
 
        //Loads users into system
        TutorProj setup = new TutorProj("Program");
        setup.loadUser();
        
        logCheck = setup.retLogins();
        stuCount = setup.retCount();
        
        //Create frame
        setup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setup.setSize(950,600);
        setup.setVisible(true);
    }
}
