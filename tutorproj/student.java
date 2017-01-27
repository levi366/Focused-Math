/*
 * Simple student object to hold email and professor
 * information.
 */
package tutorproj;

public class student {
    
    String username;        //Usually email addess
    String professor;       //Current professor
    
    public student() {
        
    }
    //set username
    public void setUser(String x) {
        username = x;
        
    }
    //set professor
    public void setProf(String x) {
        
        professor = x;
    }
    //return username
    public String getUser() {
        
        return username;
    }
    //return professor
    public String getProf() {
        
        return professor;
    }
}

