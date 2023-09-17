package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {
    private static ServerSocket serverSocket;
    private static boolean chat = false;

    public static void main(String[] args){
        ArrayList<HandlingClient> allHandlingClients = new ArrayList<HandlingClient>();

        try {
            serverSocket = new ServerSocket(4242);

            System.out.println("Servidor aberto na porta 4242");

            while(true){
                /**
                 * O método accpet bloqueia a execução até que
                 * seja solicitada uma nova conexão.
                 */

                allHandlingClients.add(new HandlingClient(serverSocket.accept(), allHandlingClients));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean inChat(){
        return chat;
    }

    public synchronized static void setChat(boolean val){
        chat = val;
    }
}