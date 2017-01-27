/*
 * Creates and set new math problem to client
 * using random numbers. Checks if correct, and
 * displays attention chart.
 */
package studproj;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.InputVerifier;
import java.util.Random;
import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.*;
import org.jfree.chart.ChartPanel;


public class mathPort extends JFrame implements ActionListener, KeyListener {
    
    //Student frame
    private JLabel oldFormula = new JLabel();           //Previous problem
    private JLabel formula = new JLabel();              //New problem
    private JLabel answer = new JLabel();               //Correct or incorrect
    private JLabel topAnswer = new JLabel();
    private JLabel correctForm = new JLabel();          //Correct answer
    private JLabel warning = new JLabel("");
    private JTextField input = new JTextField(2);       //Student answer
    private JPanel panel01 = new JPanel();              //Login panel
    private JPanel panel = new JPanel();                //Formula layout panel
    private JPanel main = new JPanel();                 //Manin panel for layout
    private JPanel top = new JPanel();                  //Previous prob panel
    private JPanel bottom = new JPanel();               //Previous prob panel
    private JPanel all = new JPanel();
    
    //Numbers for current problem
    private int tempX = 0;
    private int tempY = 0;
    private String operator = "";
    private int count= 0;
    private String answerStr = "";
    private String[][] question = new String[50][3];
    int start = 0;
    int stop = 20;
    int correctAns = 0;
    long firstTime;
    studData[] store = new studData[30];
    StudProj holder;
    
    chart attChart = new chart();
    String student = "";
    
    public mathPort(String x, StudProj y) {
        student = x;
        holder = y;
        firstTime = System.currentTimeMillis();
    }
    public void loadQues() {
        question[1][1] = "test";
        //Loads questions
        try {
            Scanner fileScan = new Scanner(new File("questions.csv"));
            
            String line;                        //Variable file is loaded into
            String comma = ",";                 //Used to separate tokens
           
            int x = 0;
            
            while (fileScan.hasNextLine()) {
                
                line = fileScan.nextLine();
                StringTokenizer token = new StringTokenizer(line);
                    
                question[x][0] = token.nextToken(comma);
                question[x][1] = token.nextToken(comma);
                question[x][2] = token.nextToken(comma);
                x++;
            }
        } catch (Exception e){
            System.out.println(e);
            
        }
    }
    public boolean test() {
        
        boolean correct = false;
        
        if (Integer.parseInt(answerStr) == (tempX + tempY)) {
            correct = true;
            
        }
 
        correctAns = tempX + tempY;
        return correct;
    }
    
    //Sets new values for problem
    public void resetForm() {
        
        tempX = Integer.parseInt(question[count][0]);
        tempY = Integer.parseInt(question[count][1]);
        operator = question[count][2];
        
        count++;
    }
    //Sets answer
    public void setAnswer(String x) {
        answerStr = answerStr + x;
        
    }
    //Returns formula for display
    public String retFormula() {
        
        String formula = "";
        
        return tempX + " + " + tempY + " = ";
    }
    public void resetAnswer() {
    
        answerStr = "";
    }
    //Returns x and y
    public int returnX() {
        return tempX;
    }
    public int returnY() {
        return tempY;
    }
    public String returnAns() {
        return answerStr;
    }
    public int returnCorrect() {
        return correctAns;
    }
    class updateChart extends SwingWorker<String, String> {   
        public String doInBackground() {
            int x = 0;
            while(x != 100) { 
            
                attChart.createDataset(StudProj.eegPort.getAttention()); 
            }
            return "";
        }
    }
    public JPanel quizPanel() {
        
        remove(panel01);
        
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        
        //Panel and label formating
        main.setBackground(Color.black);
        top.setBackground(Color.black);
        bottom.setBackground(Color.black);
        
        panel.setBackground(Color.black);
        
        formula.setForeground(Color.white);
        oldFormula.setForeground(Color.green);
        warning.setForeground(Color.green);
        answer.setForeground(Color.green);
        topAnswer.setForeground(Color.green);
        correctForm.setForeground(Color.green);
        //Adding chart to panel
        ChartPanel chartPanel = (ChartPanel) attChart.createPanel("Your Attention Level:");
        chartPanel.setPreferredSize(new Dimension(200,350));
        main.add(getContentPane().add(chartPanel));
       
        //Setting up textbox listner
        input.addKeyListener(this);
        input.addActionListener(this);
        input.setFocusable(true);
        //Setting up enter key
        InputMap inputMap = input.getInputMap();
        KeyStroke key = KeyStroke.getKeyStroke("Enter");
        inputMap.put(key,"pressed");
        
        
        //Layout and design
        all.setLayout(new BorderLayout());
        formula.setFont(new Font("Serif", 12, 50));
        warning.setFont(new Font("Serif", 12, 50));
        oldFormula.setFont(new Font("Serif",12,50));
        answer.setFont(new Font("Serif", 12, 50));
        topAnswer.setFont(new Font("Serif", 12, 50));
        correctForm.setFont(new Font("Serif", 12, 50));
        
        //adding problems
        loadQues();
        resetForm();
        formula.setText(retFormula());
        panel.add(top);
        panel.add(bottom);
        
        top.add(formula);
        top.add(input);
        top.add(warning);
        bottom.add(topAnswer);
        bottom.add(oldFormula);
        bottom.add(answer);
        bottom.add(correctForm);
        
        
        all.add(panel);
        all.add(main);
        
        all.add(panel, BorderLayout.CENTER);
        all.add(main, BorderLayout.EAST);
        
        //starting chart update thread
        new updateChart().execute();
        
        return all;
    }
    public void keyTyped(KeyEvent e) {
        
    }
     
    /** Handle the key pressed event from the text field. */
    public void keyPressed(KeyEvent e) {

            String temp = Character.toString(e.getKeyChar());
             
            
            if(!verify(temp) && !(e.getKeyCode() == KeyEvent.VK_ENTER)) {
                //System.out.println("Wrong");
                warning.setText("Invalid Input");
                
            } 
            else {
                warning.setText("");
                Boolean temp01 = false;
  
                //String setOldProblem = retFormula();
                String setOld = retFormula() + returnAns();
                String setOldProblem = "";
            
                studData capture = new studData();
                capture.setUser(student);
            
           
                capture.setStartTime(firstTime);
                
            
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
            
                    if (test()) {
            
                        temp01 = true;
               
                    }
                    else if (!test()) {
            
                        temp01 = false;
                        setOldProblem = retFormula() + returnCorrect();
                    }
                    if (count < 20) {
                        resetForm();
                    } 
                
                    formula.setText(retFormula());
                
                    input.setText("");
                    answer.setText("");
                }
                else {
                    setAnswer(temp);
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                
                    correctForm.setText("");
                    if (temp01) {
                        //Capturing data for problem
                        capture.setProblem(setOld);
                        capture.setAttention(StudProj.eegPort.getAttention());
                        capture.setFrustration(0);
                        capture.setCorrect(true);
                        capture.setStopTime(System.currentTimeMillis());
                        //capture.setAnswer(Integer.parseInt(old));
                        //Setting old problem in gui
                        topAnswer.setText("");
                        oldFormula.setText(setOld);
                        answer.setText("Correct");
                        resetAnswer();
                        start++;
                    }
                    else {
                        //Captureing data for problem
                        capture.setProblem(setOld);
                        capture.setAttention(StudProj.eegPort.getAttention());
                        capture.setFrustration(0);
                        capture.setCorrect(false);
                        capture.setStopTime(System.currentTimeMillis());
                        capture.setAnswer(Integer.parseInt(returnAns()));
                        //Setting old problem in gui
                        topAnswer.setText("Incorrect");
                        oldFormula.setText(setOld);
                        answer.setText("Correct");
                        correctForm.setText(setOldProblem);
                        resetAnswer();
                        start++;
                    }
                 
                    store[start -1] = capture;
                    count = start;
                    //Sends data to server

                    if (count % 5 == 0) {
                        loginGui.dataConnection.sendCom("quiz");
                        String attention = "";
                        String ansCor = "";
                        String sendQuest = "";
                        String timStart = "";
                        String timStop = "";
                    
                        if (loginGui.dataConnection.recieveCom().compareTo("ready") == 0) {
                        
                            loginGui.dataConnection.sendCom(capture.getUser());
                        
                            if(loginGui.dataConnection.recieveCom().compareTo("ready") == 0) {
                                if (count == 20) {
                                    for (int x = count - 5; x < count; x++) {
                                        attention = attention + store[x].getAttention() + ",";
                                        ansCor = ansCor + store[x].getCorrect() + ",";
                                        sendQuest = sendQuest + store[x].getProb() + ",";
                                        timStart = timStart + store[x].getStartTime() + ",";
                                        timStop = timStop + store[x].getStopTime() + ",";
                            
                                    }
                                    count = 0;
                                } else {
                                    for (int x = count - 5; x < count; x++) {
                                        attention = attention + store[x].getAttention() + ",";
                                        ansCor = ansCor + store[x].getCorrect() + ",";
                                        sendQuest = sendQuest + store[x].getProb() + ",";
                                        timStart = timStart + store[x].getStartTime() + ",";
                                        timStop = timStop + store[x].getStopTime() + ",";
                                    }
                                }
                                loginGui.dataConnection.sendCom(attention);
                                loginGui.dataConnection.sendCom(ansCor);
                                loginGui.dataConnection.sendCom(sendQuest);
                                loginGui.dataConnection.sendCom(timStart);
                                loginGui.dataConnection.sendCom(timStop);
                            }
                        }
                    }
                }
        
                if (start == stop) {        
                    //StudProj.eegPort.done();
                    loginGui.dataConnection.sendCom("complete");
            
                    String count = loginGui.dataConnection.recieveCom();
                    String tempAttent = loginGui.dataConnection.recieveCom();
                    String tempCorrect = loginGui.dataConnection.recieveCom();
                    String tempTime = loginGui.dataConnection.recieveCom();
                    startGui report = new startGui(holder, student, tempAttent, tempCorrect, tempTime);
                    holder.changePanel(report.returnGui());   
            
                }   
            } 
    }
     
    /** Handle the key released event from the text field. */
    public void keyReleased(KeyEvent e) {
        
    }
    
    //Loads new question
    public void actionPerformed(ActionEvent event) {
        
      
    }
    public boolean verify (String com) {
            //JTextField txt = (JTextField) com;
            try {
                
                
                int value = Integer.parseInt(com);
                
            } catch (NumberFormatException nfe) {
                
                return false;
            }
            
            return true;
        }
    
}
