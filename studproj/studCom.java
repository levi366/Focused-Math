/*
 * Handles client communication to server
 */
package studproj;

import java.net.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.JOptionPane;


public class studCom {

	private Socket clientSocket;

    BufferedReader in;
    BufferedReader in01;
	private PrintWriter out;
	private InetAddress location;

	public studCom() {

		clientSocket = null;
		out = null;

	}
	//Sets ip address of server and makes connection
	public studCom(int A, int B, int C, int D) {
                
		byte[] bAddress = {(byte)A, (byte)B, (byte)C, (byte)D};

		try {
			//Converting ip address into InetAddress
			location = InetAddress.getByAddress(bAddress);

			//Connecting to server
			clientSocket = new Socket(location, 4444);
                        
			//Buffer for request
			in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));               
                        
			out = new PrintWriter(clientSocket.getOutputStream(), true);     
		}
		catch (UnknownHostException e) {

			System.err.println("Cannot find host");
			System.exit(1);
		}
		catch (IOException e) {

			JOptionPane.showMessageDialog(new JFrame(),"Tutoring Server Not "
                                + "found \n Please Contact The Tutor On Duty", 
                                "System", JOptionPane.PLAIN_MESSAGE);
			System.exit(1);

		}
	}
        
	
        public void makeConnect(int A, int B, int C, int D) {
            
            byte[] bAddress = {(byte)A, (byte)B, (byte)C, (byte)D};

		try {
			//Converting ip address into InetAddress
			location = InetAddress.getByAddress(bAddress);

			//Connecting to server
			clientSocket = new Socket(location, 4444);
                        
			//Buffer for request
			in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));               
                        
			out = new PrintWriter(clientSocket.getOutputStream(), true);     
		}
		catch (UnknownHostException e) {

			System.err.println("Can not find host");
			System.exit(1);
		}
		catch (IOException e) {

			JOptionPane.showMessageDialog(new JFrame(),"Tutoring Server Not "
                                + "found \n Please Contact The Tutor On Duty", 
                                "System", JOptionPane.PLAIN_MESSAGE);
			System.exit(1);

		}
    }
    //Recieves text file and saves it
	public void recieveFile(String file) {
        try {
                int filesize = 6022386; // filesize temporary hardcoded
		        int bytesRead;
		        int current = 0;

		        byte [] mybytearray  = new byte [filesize];
		        InputStream is = clientSocket.getInputStream();

		        FileOutputStream fos = new FileOutputStream(file);
		        BufferedOutputStream bos = new BufferedOutputStream(fos);
                                
                bytesRead = is.read(mybytearray,0,mybytearray.length);
                current = bytesRead;
                                
		        bos.write(mybytearray, 0 , current);
		        bos.flush();
                fos.close();
                bos.close();
            } catch (UnknownHostException e) {

		System.err.println("Can not find host");
		System.exit(1);
            } catch (IOException e) {

		System.err.println("I/O issue: " + e);
		System.exit(1);
            }
	}
	//Sends command to server
	public void sendCom(String command) {
            //This sends data to remove server
            out.println(command);
	}
        public String recieveCom() {
            String temp = "";
            try {
               temp = in.readLine(); 
            }
            catch(Exception e) {
                System.err.println("I/O issue: " + e);
	        System.exit(1);
            }
            
            return temp;
        }
        
	//Closes connection to server
	public void close(String student) {
		try {

			out.println("quit");
            out.println(student);
            out.close();
            clientSocket.close();
           
		}
		catch (IOException e) {

			System.err.println("I/O issue");
			System.exit(1);

		}
	}
    public void logOut(String student) {

	out.println("quit");
        out.println(student);           
         	
    }
}
