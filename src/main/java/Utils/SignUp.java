package Utils;

import Networking.Message;
import Networking.SocketWrapper;

import java.io.IOException;

public class SignUp {
    public boolean work(Message message, String serverAddress, int serverPort) {
        try{

            SocketWrapper socketWrapper = new SocketWrapper(serverAddress, serverPort);
            socketWrapper.write(message);

            Object response = socketWrapper.read();
            if (response instanceof String && response.equals("SUCCESS")) {
                return true;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
