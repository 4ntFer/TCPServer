package chatRoom;

import chatRoom.ChatRoom;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class ChatRoomOutput extends Thread{
    private Scanner scanner = new Scanner(System.in);
    private ObjectOutputStream output;
    private ChatRoom chatRoom;

    public ChatRoomOutput(ObjectOutputStream output, ChatRoom chatRoom){
        this.output = output;
        this.chatRoom = chatRoom;
    }

    @Override
    public void run(){
        while(chatRoom.isRunning()){
            String mensage = scanner.nextLine();

            if(!mensage.equals("")) {
                System.out.println("Server: " + mensage);
                try {
                    output.flush();
                    output.writeObject(mensage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            if(mensage.equals("/SAIR")){
                chatRoom.close();
            }
        }
    }


    public void close() {
        try {
            output.flush();
            output.writeObject("/SAIR");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        interrupt();
    }
}
