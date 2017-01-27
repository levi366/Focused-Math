/*
 * Uses username of student to obtain an array of 
 * historical data from .csv files
 * File naming convention :  <user><timestamp>.csv
 * Currently student data saved in root file of project
 */
package tutorproj;


import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.nio.file.Paths;


public class stuFileList {
    private String user;
    //Constructor initializes object with student username as user
    public stuFileList (String x) {
        user = x;
    }
    //Return File array with list of users historical data
    public File[] retFile() {
       
       Path currentRelativePath = Paths.get("");
       String s = currentRelativePath.toAbsolutePath().toString();
        
        File dir = new File(s);

        File[] matches = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith(user) && name.endsWith(".csv");
            }
        });
  
        return matches; 
    }
}
