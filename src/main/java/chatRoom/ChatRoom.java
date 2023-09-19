package chatRoom;

import org.example.HandlingClient;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ChatRoom {
    private boolean isRunning = true;
    private ChatRoomOutput chatRoomOutput;
    private ChatRoomInput chatRoomInput;
    private HandlingClient handlingClient;
    private String client;

    // Faz a comunicação quando em chat
    public ChatRoom(ObjectInputStream input, ObjectOutputStream output, String client, HandlingClient handlingClient){
        chatRoomInput = new ChatRoomInput(input,client, this);
        chatRoomOutput = new ChatRoomOutput(output, this);
        this.client = client;
        this.handlingClient = handlingClient;

        chatRoomInput.start();
    }

    public ChatRoomOutput getChatRoomOutput(){
        return chatRoomOutput;
    }


    public void close() {
        System.out.println(client+" encerrou o chat");
        handlingClient.setInChat(false);
        isRunning = false;
        handlingClient.close();
    }

    public boolean isRunning() {
        return isRunning;
    }
}
