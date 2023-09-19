package org.example;

import chatRoom.ChatRoom;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class HandlingClient  extends  Thread{
    private Socket socket;
    private ArrayList<HandlingClient> clientsList;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private boolean connected = true;
    private boolean chat = false;

    private ChatRoom chatRoom = null;

    public HandlingClient (Socket clientSocket, ArrayList<HandlingClient> clientsList){
        socket = clientSocket;
        this.clientsList = clientsList;

        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Cliente connectatdo: " + socket.getInetAddress().getHostAddress());

        start();
    }

    @Override
    public void run() {
        while(connected) {
            try {

                if(chat){
                    if(chatRoom == null){
                        System.out.println(socket.getInetAddress().getHostAddress() + " abriu um chat");
                        chatRoom = new ChatRoom(input, output, socket.getInetAddress().getHostAddress(), this);
                    }
                }else if(!socket.isClosed()){
                    String menssage = (String) input.readObject();
                    System.out.println("Mensagem recebida do cliente " + socket.getInetAddress().getHostAddress() + ": " + menssage);

                    if (((String) menssage).equals("SAIR")) {
                        this.close();
                    } else if (((String) menssage).equals("ARQUIVO")) {

                        WebFile file = new WebFile("src/res/extremely_important_file.mp4");
                        sendFile(file);

                    } else if (((String) menssage).equals("CHAT")) {
                            setInChat(true);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void setInChat(boolean val){
        chat = val;

        if(!val){
            chatRoom = null;
        }
    }

    public boolean inChat(){
        return  chat;
    }


    private void sendFile(WebFile file){
        System.out.println("Arquivo enviado ao cliente " + socket.getInetAddress().getHostAddress());

        try {

            //Enviando o arquivo
            output.flush();
            output.writeObject(file.getName());
            output.flush();
            output.writeObject(file.getSize());
            output.flush();
            output.writeObject(file.getContent());
            output.writeObject(file.getHash());
        } catch (IOException e) {
            System.out.println("Erro na serialização do arquivo!");
            throw new RuntimeException(e);
        }



    }

    public void close(){
        try {
            output.flush();
            output.writeObject(true);
            socket.close();
            output.close();
            input.close();
            connected = false;
            System.out.println("Cliente " + socket.getInetAddress().getHostAddress() +  " saiu!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        remove();
    }

    private void remove(){
        clientsList.remove(this);
    }

    public ChatRoom getChatRoom(){
        return chatRoom;
    }
}
