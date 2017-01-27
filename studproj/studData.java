/*
 * Student data object
 */
package studproj;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class studData {
    
    //EEG Data
    private int attention;
    private int frustration;
    private String user;
    //Student problem info
    private String problem;
    private int answer;
    private boolean correct;
    private long startTime;
    private long stopTime;
    private int count = 0;
    private studData[] toWrite;
    
    public studData() {
        
        
    }
    public void setUser(String x) {
        
       user = x;
    }
    public void setAttention(int x) {   
        attention = x;
        
    }
    public void setFrustration(int x) {
        frustration = x;
       
    }
    public void setCorrect(boolean x) {
        correct = x;
        
    }
    public void setStartTime(long x) {
        startTime = x;
        
    }
    public void setStopTime(long x) {
        stopTime = x;
        
    }
    public void setProblem(String x) {
        problem = x;
       
    }
    public void setAnswer(int x) {
        answer = x;
        
    }
    public void setToWrite(studData[] x) {
        toWrite = x;
    }
    public String getAttention() {
        
        return String.valueOf(attention);
    }
    public long getStartTime() {
        
        return startTime;
    }
    public long getStopTime() {
        
        return stopTime;
    }
    public String getUser() {
        
        return user;
    }
    public boolean getCorrect() {
        return correct;
    }
    public String getProb() {
        return problem;
    }
}
