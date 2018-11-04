package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by codecadet on 01/11/2018.
 */
public class ServerWithBlackJackAndHookers {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private List<ClientHandler> clients;
    private BufferedReader connectionIN;
    private PrintWriter connectionOUT;
    private Scanner scanner = new Scanner(System.in);


    public ServerWithBlackJackAndHookers() {

        try {

            System.out.println("Which port to connecto to?");
            serverSocket = new ServerSocket(Integer.parseInt(scanner.next()));
            clients = new ArrayList<ClientHandler>();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void start() {

        ExecutorService executor = Executors.newCachedThreadPool();

        while (true) {

            try {

                System.out.println("Waiting for a connection...");
                clientSocket = serverSocket.accept();
                System.out.println("Connection received.");

                ClientHandler client = new ClientHandler(clientSocket, (int) (Math.random() * 100));
                System.out.println("ClientHandler built");
                clients.add(client);
                System.out.println("Client added to list");
                executor.submit(client);
                System.out.println("Client submited to thread");

            } catch (IOException e) {
                e.printStackTrace();
            }



        }


    }

    public void broadcast (String msg) {

        for (ClientHandler client : clients) {
            client.writer.write(msg);
        }
    }



    private class ClientHandler implements Runnable {


        private int ID;
        private BufferedReader reader;
        private PrintWriter writer;
        private Socket clientSocket;

        public ClientHandler(Socket socket, int ID) {

            clientSocket = socket;
            this.ID = ID;
            try {
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void send(String msg) {

           broadcast(msg);
        }



        @Override
        public void run() {

            while (true) {

                try {

                    System.out.println("Ready to read message from user.");
                    String msg = reader.readLine();
                    System.out.println("Message inserted");
                    send(msg);
                    System.out.println("message sent");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

