/*
 * Builds and displays client GUI
 */
package studproj;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.*;
import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;


public class startGui extends JFrame implements ActionListener {
    
    private StudProj holder;        //Holds main to add panels to GUI
    private JButton startBut = new JButton("Start");
    private JButton graph = new JButton("Log out");
    private JLabel attScore = new JLabel("0");
    private JLabel timScore = new JLabel("0 seconds");
    private JLabel corScore = new JLabel("0");
    
    private String student = "";
    
    public startGui(StudProj x, String user) {
        
        holder = x;
        student = user;
        
    }
    public startGui (StudProj x, String user, String tempAtt, String tempCor, String tempTime) {
        //This should be fixed at some point
        try {
            tempAtt = tempAtt.substring(0, tempAtt.indexOf(".") + 2) + " out of 100";
            tempCor = tempCor.substring(0, tempCor.indexOf(".") + 2)  + "%";
            tempTime = tempTime.substring(0, tempTime.indexOf(".") + 2)  + " seconds";
        
            holder = x;
            student = user;
            attScore.setText(tempAtt);
            corScore.setText(tempCor);
            timScore.setText(tempTime);
        } catch (Exception e) {
            
            holder = x;
            student = user;
            attScore.setText(tempAtt);
            corScore.setText(tempCor);
            timScore.setText(tempTime);
            
        }
        
    }
    
    public JPanel returnGui() {
        //Panels
        JPanel test = new JPanel();             //Main
        JPanel report = new JPanel();           //Selector
        JPanel reportCont = new JPanel();       //control
        JPanel report01 = new JPanel();         //Report
        JPanel test01 = new JPanel();           //title
        JPanel test02 = new JPanel();           //center
        JPanel score01 = new JPanel();           //score
        JPanel score02 = new JPanel();           //score
        JPanel score03 = new JPanel();           //score
        //JPanel score04 = new JPanel();           //score
        
        test.setBackground(Color.black);
        report.setBackground(Color.black);
        reportCont.setBackground(Color.black);
        report01.setBackground(Color.black);
        test01.setBackground(Color.black);
        test02.setBackground(Color.black);
        score01.setBackground(Color.black);
        score02.setBackground(Color.black);
        score03.setBackground(Color.black);
        //score04.setBackground(Color.black);
        
        //Panel layouts
        test.setLayout(new BorderLayout());
        test.setPreferredSize(new Dimension(1000,500));
        test02.setLayout(new GridBagLayout());
        report.setPreferredSize(new Dimension(200,500));
        reportCont.setLayout(new BoxLayout(reportCont,BoxLayout.Y_AXIS));
        report.setLayout(new GridBagLayout());
        report01.setLayout(new GridLayout(2,4,200,200));
        report01.setSize(new Dimension(300,300));
        score01.setLayout(new BoxLayout(score01, BoxLayout.Y_AXIS));
        score02.setLayout(new BoxLayout(score02, BoxLayout.Y_AXIS));
        score03.setLayout(new BoxLayout(score03, BoxLayout.Y_AXIS));
        //score04.setLayout(new BoxLayout(score04, BoxLayout.Y_AXIS));
        
        //Components
        
        JLabel title = new JLabel("Overall Quiz Results");
        title.setForeground(Color.green);
        Font labelFont = title.getFont();
        title.setFont(new Font(labelFont.getName(),Font.PLAIN, 25));
        
        Font subTitle = new Font(labelFont.getName(),Font.PLAIN, 20);
        JLabel titleAtt = new JLabel("Average Attention");
        titleAtt.setForeground(Color.green);
        titleAtt.setFont(subTitle);
        
        JLabel titleRelax = new JLabel("Average Time");
        titleRelax.setForeground(Color.green);
        titleRelax.setFont(subTitle);
        
        JLabel titleCorrect = new JLabel("Percent Correct");
        titleCorrect.setForeground(Color.green);
        titleCorrect.setFont(subTitle); 
        
        attScore.setForeground(Color.green);
        attScore.setFont(subTitle);
        
        timScore.setFont(subTitle);
        timScore.setForeground(Color.green);
        
        corScore.setForeground(Color.green);
        corScore.setFont(subTitle);

        
        //Adding subpanels
        test01.add(title);
        test.add(test01,BorderLayout.NORTH);
        test.add(report, BorderLayout.LINE_START);
        test.add(test02,BorderLayout.CENTER);
        report.add(reportCont);
        test02.add(report01);
        report01.add(score01);
        report01.add(score02);
        report01.add(score03);
        //report01.add(score04);
 
        //Adding components
        
        reportCont.add(graph);
        reportCont.add(startBut);
        score01.add(titleAtt);
        score01.add(attScore);
        score02.add(titleRelax);
        score02.add(timScore);
        score03.add(titleCorrect);
        score03.add(corScore);
        
        startBut.addActionListener(this);
        graph.addActionListener(this);
        
        return test;
        
    }
    public void actionPerformed(ActionEvent event) {
        
        if(event.getSource() == startBut) {
            
            mathPort quiz = new mathPort(student, holder);
            holder.changePanel(quiz.quizPanel());
            
        }
        else if (event.getSource() == graph) {
            loginGui.dataConnection.logOut(student);
            JPanel loginRet;
            loginGui temp = new loginGui(holder);
            holder.changePanel(temp.returnLog());
            StudProj.connected = false;
        }
    }
}
