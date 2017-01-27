/*
 * Student data from client software
 * Includes:
 *          Attention
 *          Frustration
 *          Problem
 *          Answer
 *          Whether student was correct
 *          Time
 *
 * Future planed updates:
 *      Move away .csv to database
 *      Add passwords
 *      hash and salt passwords
 */
package tutorproj;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.util.Date;
import java.util.Scanner;

public class studData {
    
    //EEG Data
    private String attention;
    private int frustration;
    private String user;
    //Student problem info
    private String problem;
    private int answer;
    private String correct;
    private String startTime;
    private String stopTime;
    private int[] attenitonInt = new int[20];
    private String tooLong = "";
    private String currUser;
    
    public studData() {
        
        
    }
    //Setters for student data
    public void setUser(String x) {
       
       user = x;
    }
    public void setAttention(String x) {   
        attention = x;
        
    }
    public void setFrustration(int x) {
        frustration = x;
       
    }
    public void setCorrect(String x) {
        correct = x;
        
    }
    public void setStartTime(String x) {
        startTime = x;
        
    }
    public void setStopTime(String x) {
        stopTime = x;
        
    }
    public void setProblem(String x) {
        problem = x;
       
    }
    public void setAnswer(int x) {
        answer = x;
        
    }
    public String getAttention() {
        
        return attention;
    }
    public String getStartTime() {
        
        return startTime;
    }
    public String getStopTime() {
        
        return stopTime;
    }
    public String getUser() {
        
        return user;
    }
    public String getCorrect() {
        return correct;
    }
    public String getProb() {
        return problem;
    }

    //Creates .csv for student data
    //File naming conevention <user><timestamp>.csv

    public void writeData(studData[] data) {
        currUser = data[0].getUser();
        String timeStamp = Long.toString(System.currentTimeMillis());
          
        tooLong = currUser + timeStamp + ".csv";
        
        try {
            
            File file = new File(tooLong);
            
            file.createNewFile();
            
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.write(data[0].getUser());
            bw.newLine();
            
            for (int y = 0; y < 4; y++) {
                
                bw.write(data[y].getAttention());
            }
            bw.newLine();
            
            for (int y = 0; y < 4; y++) {
                
                bw.write(data[y].getCorrect());
            }
            bw.newLine();
            
            for (int y = 0; y < 4; y++) {
                
                bw.write(data[y].getProb());
            }
            
            bw.newLine();
            
            for (int y = 0; y < 4; y++) {
                bw.write(data[y].getStartTime());

            }
            bw.newLine();
            
            for (int y = 0; y < 4; y++) {
                bw.write(data[y].getStopTime() + ",");

            }

            bw.flush();
            bw.close();
        } catch (Exception e) {
            System.out.println("Problem writing file: " + e);
        }

    }
    //Reads student file and returns data
    public String[] readData(String x) {
        
        stuFileList item = new stuFileList(x);
        File[] items = item.retFile();

        String[] dataRet = new String[(items.length) * 2];
        String line = "";
        int count = 0;
        
        try {
            for (int y = 0; y < items.length; y++) {
                
                Scanner fileScan = new Scanner(items[y]);
                fileScan.nextLine();
            
                dataRet[count] = fileScan.nextLine();
                dataRet[count + 1] = fileScan.nextLine();

                count = count + 2;
            
            }
                     
        } catch (Exception e) {
            
           System.out.println(e); 
        }
        
        
        return dataRet;
        
    }

/*
* Pull averages from file if avail
* add averages to new averages and send
*/
  
    public String[] averageData() {
        
        String[] rawData;
        String[] averageRaw;
        String[] sendData = new String[10];
        int total = 20;
        
        dataBreak values = new dataBreak(tooLong);
        rawData = values.returnValue();
        averageRaw = values.returnAverageValue(currUser);
        
        float average = 0;
        float percentCorrect = 0;
        float timeAverage = 0;
        
        for (int x  = 1; x < 21; x++) {
            
            average = average + Integer.parseInt(rawData[x]);
        }
        
        if (averageRaw[0].compareTo("new") == 0) {
            average = average/20;
            
        } else {
            average = (average + Float.parseFloat(averageRaw[1]))/(20 + Float.parseFloat(averageRaw[0]));
            
        }
        
        for (int x  = 21; x < 41; x++) {
            
            if (Boolean.parseBoolean(rawData[x])) {
                percentCorrect = percentCorrect + 1;
            }
        }
        
        if (averageRaw[0].compareTo("new") == 0) {
            percentCorrect = (percentCorrect/20) * 100;
            
        } else {
            percentCorrect = (percentCorrect+ (Float.parseFloat(averageRaw[2])/100)*Float.parseFloat(averageRaw[0]))/
                    (20 + Float.parseFloat(averageRaw[0])) * 100;
            
        }
        
        for (int x  = 81; x < 101; x++) {

            timeAverage = timeAverage + (Long.parseLong(rawData[x]) - Long.parseLong(rawData[x-1]));
 
        }

        if (averageRaw[0].compareTo("new") == 0) {
            timeAverage = (timeAverage/20)/1000;
            averageRaw[0] = "0";
        } else {
            timeAverage = ((timeAverage+ (Float.parseFloat(averageRaw[3])*1000))/(20 + 
                            Float.parseFloat(averageRaw[0])))/1000;
            
        }
        
        sendData[0] = Integer.toString(total + Integer.parseInt(averageRaw[0]));
        sendData[1] = Float.toString(average);
        sendData[2] = Float.toString(percentCorrect);
        sendData[3] = Float.toString(timeAverage);
        
         //write file
        try {
            
            File file = new File(currUser + "Average.avg");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            
            for (int x = 0; x < 4; x++) {
                
                bw.write(sendData[x] + ",");

            } 
            
            bw.flush();
            bw.close();
   
        } catch (Exception e) {
           System.out.println(e);
        }
         
        return sendData;
        
    }
}
