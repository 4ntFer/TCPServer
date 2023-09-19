package chatRoom;

import chatRoom.ChatRoom;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ChatRoomInput extends Thread {
    private String client;
    private ObjectInputStream input;
    private String mensage = "";
    private ChatRoom chatRoom;

    public ChatRoomInput(ObjectInputStream input, String client, ChatRoom chatRoom){
        this.input = input;
        this.client = client;
        this.chatRoom = chatRoom;
    }

    @Override
    public void run(){
        while(chatRoom.isRunning()){
            try {
                mensage = (String)input.readObject();
                if(!mensage.equals("")) {
                    System.out.println(client + ": " + mensage);
                    if (mensage.equals("/SAIR")) {
                        chatRoom.close();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public static void clear(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
}
