package chatRoom;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ChatRoomOutput {
    private ObjectOutputStream output;
    private ChatRoom chatRoom;

    public ChatRoomOutput(ObjectOutputStream output, ChatRoom chatRoom){
        this.output = output;
        this.chatRoom = chatRoom;
    }

    public void sendMensage(String mensage){
        try {
            output.flush();
            output.writeObject(mensage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
