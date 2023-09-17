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

    public ChatRoom(ObjectInputStream input, ObjectOutputStream output, String client, HandlingClient handlingClient){
        chatRoomInput = new ChatRoomInput(input,client, this);
        chatRoomOutput = new ChatRoomOutput(output, this);
        this.client = client;
        this.handlingClient = handlingClient;

        chatRoomOutput.start();
        chatRoomInput.start();
    }


    public void close() {
        System.out.println(client+" encerrou o chat");
        handlingClient.setInChat(false);
        isRunning = false;
        chatRoomInput.close();
        chatRoomOutput.close();
    }

    public boolean isRunning() {
        return isRunning;
    }
}
