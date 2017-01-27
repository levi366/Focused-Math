/*
 * This was used to create .csv for all data storage
 * Since proof of concept the thought was this would be simpler.
 * Lesson learned was implemenation of a database would have made
 * admin and other functions simplier.
 *
 * File format:
 *      data,data,\n
 *      data,data,\n
 */
package tutorproj;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.StringTokenizer;


public class dataBreak {
    
    private String fileName = "";
    
    public dataBreak(String x) {
        fileName = x;
    }
    public String[] returnValue() {
        
        String[] fileValue = new String[101]; 
        
         try {
            Scanner fileScan = new Scanner(new File(fileName));
            String line;						//Variable file is loaded into
            String comma = ",";					//Used to separate tokens
            /* These are tokens that hold the string divided on commas */
            String token01;
            
            int start = 1;
            int end = 21;
      
		/*
		 * while loop loads the next line then using StringTokenizer the data is
		 * separated on commas
		 */
                line = fileScan.nextLine();
                fileValue[0] = line;
                
		while (fileScan.hasNextLine()) {

			line = fileScan.nextLine();			//loads line from file
                       
			/*
			 * Creates test StringTokenizer every loop and separates each data
			 * point on commas, and no comma at the end of the line
			 */
			StringTokenizer test = new StringTokenizer(line);
                        for (int x = start; x < end; x++) {
                            
                            token01 = test.nextToken(comma);
                            fileValue[x] = token01;
			
                        }
                        start = end;
                        end = end + 20;
		}
        } catch (Exception e) {
            
            System.out.println(e);
        }
        
        
        return fileValue;
    
    }
    public String[] returnAverageValue(String y) {
        
        String[] rawData = new String[4];
        try {
            
            File averageFile = new File(y + "Average.avg");
            
            if (averageFile.exists()) {
                
                Scanner fileScan = new Scanner(averageFile);
                String line;						//Variable file is loaded into
                String comma = ",";					//Used to separate tokens
                /* These are tokens that hold the string divided on commas */
                String token01;
                
                int start = 0;
                int end = 4;
      
		/*
		 * while loop loads the next line then using StringTokenizer the data is
		 * separated on commas
		 */
                 //line = fileScan.nextLine();
                    
                    line = fileScan.nextLine();			//loads line from file
                    
                    /*
                    * Creates test StringTokenizer every loop and separates each data
                    * point on commas, and no comma at the end of the line
                    */
                    StringTokenizer test = new StringTokenizer(line);
                    for (int x = start; x < end; x++) {
                        token01 = test.nextToken(comma);
                        rawData[x] = token01;
                        
                    }
            } else {
                
                rawData[0] = "new";
                rawData[1] = "0";
                rawData[2] = "0";
                rawData[3] = "0";
                 //write file
        try {
            
            File file = new File(y + "Average.avg");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            
            for (int x = 0; x < 4; x++) {
                
                bw.write(rawData[x] + ",");
            } 
            
            bw.flush();
            bw.close();
   
        } catch (Exception e) {
           System.out.println(e);
        }
                

            }
        } catch (Exception e) {
            
            System.out.println(e);
        }
        
        return rawData;
    }
}
