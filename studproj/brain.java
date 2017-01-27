/*
 * Connects to neurosky mindwave and
 * creates thread to recieve dta
 */
package studproj;

import java.io.*;
import java.util.concurrent.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Iterator;
import javax.swing.*;


public class brain extends Thread {
    
     Socket neuroSocket;
     OutputStream outStream;
     InputStream inStream;
     BufferedReader stdIn;
     int attention = 0;
     volatile boolean disconnect = false;
   
    public void disconnect() {
        
        try {
            
            neuroSocket.close();
            inStream.close();
            outStream.close();
            stdIn.close();
            stdIn = null;
            
        }
	catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Exception within io " + e);
            
        } catch (Exception e) {
            System.out.println("Exception within io " + e);
            
        }
        
        
    }
    //Creates thread to update attention
    
    public void run() {

        String userInput;
        try {
            neuroSocket = new Socket("127.0.0.1",13854);
            
            inStream  = neuroSocket.getInputStream();
            outStream = neuroSocket.getOutputStream();
            stdIn = new BufferedReader(new InputStreamReader(neuroSocket.getInputStream()));
            
            
            PrintWriter out = new PrintWriter(outStream, true);
            JSONObject format = new JSONObject();
            
            format.put("enableRawOutput:", true);
            format.put("format", "Json");
            
            out.println(format.toString());
            while ((userInput = stdIn.readLine()) != null && !disconnect) {
                String[] packets = userInput.split("/\r/");
		for(int s=0;s<packets.length;s++){
                     
                    if(((String) packets[s]).indexOf("{")>-1){
                        
                        JSONObject obj = new JSONObject((String) packets[s]);                
                        Iterator itr = obj.keys(); 
                        int raw[] = new int[512];
                    
                        while(itr.hasNext()&& !disconnect) {
                            Object e = itr.next(); 
                            String key = e.toString();
            
                            try{
                                if(key.matches("eSense")){
                                    JSONObject esense = obj.getJSONObject("eSense");
                    
                                    attention = esense.getInt("attention");
                                }
                            }
                            catch(Exception ex) {

                                ex.printStackTrace();
                            }                      
                        }
                    }
                }
                
                
            }
            stdIn.close();
            outStream.close();
            inStream.close();
            neuroSocket.close();
        } 
        catch(SocketException e) {
           
        }
        catch (IOException e) {
	
            
        }
        
        catch (JSONException e) {
            try {
                System.out.println("JSON error");
                stdIn.close();
                outStream.close();
                inStream.close();
                run();
                
                
            } catch (Exception ev) {
            
            }
        }
        

    
        disconnect();
    }
    
    @Override
    public void interrupt() {
        super.interrupt();
    }
     public void done() {
  
           disconnect = true;
        
    }
    public int getAttention() {
        
        return attention;
    }
}
