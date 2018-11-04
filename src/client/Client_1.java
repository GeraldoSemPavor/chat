package client;

/**
 * Created by codecadet on 01/11/2018.
 */
public class Client_1 {

    public static void main(String[] args) {

        ChatClient chatClient = new ChatClient();

        System.out.println("Client created");

        chatClient.listen();


    }
}
