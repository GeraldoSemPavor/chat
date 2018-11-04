package client;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by codecadet on 01/11/2018.
 */
public class ChatClient {

    private Socket clientSocket;

    /**
     * System
     */
    private BufferedReader systemReader;
    private PrintWriter systemWriter;
    /**
     * Connection
     */
    private BufferedReader connectionIN;
    private PrintWriter connectionOUT;


    /**
     * CONSTRUCTOR
     */
    public ChatClient() {

        systemReader = new BufferedReader(new InputStreamReader(System.in));
        systemWriter = new PrintWriter(new OutputStreamWriter(System.out));

        try {

            System.out.println("Please enter host, and after port number. ");
            clientSocket = new Socket(systemReader.readLine(), Integer.parseInt(systemReader.readLine()));
            connectionIN = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            connectionOUT = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    public void listen() {

        ExecutorService singleThread = Executors.newSingleThreadExecutor();

        singleThread.submit(new Runnable() {
            @Override
            public void run() {

                while (true) {

                    try {

                        System.out.println("ready to READ from SERVER");
                        String msg = connectionIN.readLine();
                        System.out.println("READ from SERVER");

                        systemWriter.write(msg);
                        System.err.println(clientSocket.getInetAddress().getHostName() + " SAYS: " + msg);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        while (true) {

            try {

                System.out.println("Ready to WRITE from SYSTEM");
                String msg = systemReader.readLine();
                System.out.println("READY TO SEND TO SERVER");
                System.err.println("USER SAYS: " + msg);
                connectionOUT.write(msg);
                System.out.println("SENT TO SERVER");


            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("hmmmmmmmm");
            }
        }


    }

    public void send(String msg) {

        System.err.println(msg);

    }


}
