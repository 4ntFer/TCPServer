package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class ChatInput extends Thread {
    private static Scanner scanner = new Scanner(System.in);;
    private String mensage = "";
    private ArrayList<HandlingClient> clients;

    public ChatInput(ArrayList<HandlingClient> clients){
        scanner = new Scanner(System.in);
        this.clients = clients;
        start();
    }

    @Override
    public void run(){
        while(true){
            newMensage();

            for(int i = 0; i < clients.size() ; i++){
                if(clients.get(i).inChat()){
                    if(clients.get(i).getChatRoom() !=  null) {
                        clients.get(i).getChatRoom().getChatRoomOutput().sendMensage(mensage);
                    }
                }
            }
        }
    }

    public void newMensage(){
        mensage = scanner.nextLine();
    }



    public String getMensage() {
        return mensage;
    }
}
