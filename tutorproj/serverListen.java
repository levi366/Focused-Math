/*
 * Provides communication between client and server. Limitations
 * of users based on projected users from UAT and hardware limitations
 * (only two mindwave headsets).
 * 
 * Security was not forefront due to project being a proof of concept.
 * Future updates:
 * Encrypted communications
 */
package tutorproj;
import java.io.*;
import java.util.concurrent.*;
import java.net.ServerSocket;
import java.net.*;

/**
 *
 * This is the listener for the server and handles all network communication
 * 
 * The amount of students possible at this time is 100 and 2 connections at a time
 * 
 */
public class serverListen extends Thread {
    
    private student[] authList = new student[100];      //Currently up to 100 students
    private int count = 0;                              //Number of students
    private String questions = "";
    private TutorProj holder = new TutorProj();         //holder allows changes to control gui
    
    //Starts threads and socket listener
    public void startServer() {
    
        Runnable serverTask = new Runnable() {
            public void run() {
                //Allows server to work without interupting rest of program
                //In additon, complete control of thread and socket
                final ExecutorService clientProcessing = Executors.newFixedThreadPool(2);
                
                //Allows for multiple connections
                try(ServerSocket serverSocket = new ServerSocket(4444)) { 
                    while (true) {
                        Socket clientSocket = serverSocket.accept();

                        clientProcessing.submit(new ClientTask(clientSocket));
                    
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        };
        //Starts first listener
        Thread serverThread = new Thread(serverTask);
        serverThread.start();
    }
    //Places data in object
    public void setData(student[] x, int y, TutorProj z) {
        authList = x;
        count = y;
        holder = z;  
    }
    //Sends data to client
    public void sendFile(String nameFile, Socket clientSocket) {
        
        try {
            
            File myFile = new File(nameFile);
            byte[] myByteArray = new byte[(int)myFile.length()];
            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(myByteArray,0,myByteArray.length);

            //After read thie is when it is sent
            OutputStream os = clientSocket.getOutputStream();
            os.write(myByteArray, 0, myByteArray.length);
	} catch (IOException e) {

            System.out.println("Issues sending file: "+ e);

	}
    }
   
    private class ClientTask implements Runnable {
        private final Socket clientSocket;
        private int countData = 0;
        private String updateAtt = "";
        chart test = new chart();
        
        private ClientTask(Socket clientSocket) {
            this.clientSocket = clientSocket;
            
        }
        @Override
        public void run() {
            
            boolean quit = false;       //Used to terminate the tread
            String user = "";           //Username
            String inputLine;           //Input from student
            boolean pass = false;
            boolean found = false;
            
            
            try {
                
                //Recieves data from student program
		BufferedReader in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
                       
                //Outputs historical data and questions to student
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                studData[] toWrite = new studData[20];
                
                //Runs server
		while (!quit) {
                    
                    inputLine = in.readLine();
                    found = false;
                    //Student program just started and authenticating
                    if (inputLine.compareTo("newUser") == 0){
                        out.println("ready");
                        inputLine = in.readLine();
                        int x = 0;
                        while(!found && x < count) {
                            
                            if (inputLine.compareTo(authList[x].getUser()) == 0) {
                                found = true;
                                user = inputLine;
                                x = count;
                            }
                            x++;
                        }
                        out.println(found);
                                          
                    }
                    //Authenticated and starting quick
                    //This loads the questions
                    else if (inputLine.compareTo("startQuiz") == 0) {
                        test = new chart();
                        holder.changePanel(test.createPanel(user), user);
                        
                        String professor = "";
                        String file = "";
                        for(int x = 0; x < count; x++) {
                            
                            if (user.compareTo(authList[x].getUser()) == 0) {
                                user = authList[x].getUser();
                                professor = authList[x].getProf();
                            }
                        }
                        //Each professor has a separate csv file
                        file = professor + ".csv";
                        
                        String file2 =  user + "Average.avg";
                        
                        dataBreak temp = new dataBreak("");
                        temp.returnAverageValue(user);
                        
                        sendFile(file, clientSocket);
                         inputLine = in.readLine();
                         if (inputLine.compareTo("ready") == 0) {
                             sendFile(file2, clientSocket);
                         }
                        
                    }
                    else if (inputLine.compareTo("quiz") == 0) {
                        
                        
                        if (pass) {
                            test = new chart();
                           
                            holder.deletePanel(user);
                            holder.changePanel(test.createPanel(user), user);
                            pass = false;
                        }
                        
                        studData temp = new studData();
                        out.println("ready");
                        inputLine = in.readLine();
                        temp.setUser(inputLine);
                        out.println("ready");
             
                        temp.setAttention(in.readLine());
                        temp.setCorrect(in.readLine());
                        temp.setProblem(in.readLine());
                        temp.setStartTime(in.readLine());
                        temp.setStopTime(in.readLine());
                        toWrite[countData] = temp;
                        updateAtt = temp.getAttention();
                        String tempCorr = temp.getCorrect();
                        test.createDataset(updateAtt, tempCorr);
                        
                        countData++;
                    }
                    else if (inputLine.compareTo("complete") == 0) {
                        String[] tempData;
                        pass = true;
                        studData solve = new studData();
                        solve.writeData(toWrite);
                        tempData = solve.averageData();
                        out.println(tempData[0]);
                        out.println(tempData[1]);
                        out.println(tempData[2]);
                        out.println(tempData[3]);

                        countData = 0;
                    }
                    else if (inputLine.compareTo("quit") == 0) {
                        //userNum is the position in the area of JPanel where user info is displayed
                        String student = in.readLine();
                        holder.deletePanel(student);
                        quit = true;
                    }
                }
            } catch (IOException e) {
                System.out.println("Could not create socket here: " + e);
                System.exit(0);
            }
            catch (Exception e) {
                
                e.printStackTrace();
            }
            
        }
        public String getAttention() {
            
            return updateAtt;
        }
    }
}
