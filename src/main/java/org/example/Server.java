package org.example;

import chatRoom.ChatRoomInput;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {
    private static ServerSocket serverSocket;

    public static void main(String[] args){
        ArrayList<HandlingClient> allHandlingClients = new ArrayList<HandlingClient>();
        ChatInput chatInput =  new ChatInput(allHandlingClients);

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

}