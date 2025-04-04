package Utils;

import Networking.Message;
import Networking.SocketWrapper;

import java.io.IOException;

public class LogIn {
    public String work(Message message, String serverAddress, int serverPort) {
        try {

            SocketWrapper socketWrapper = new SocketWrapper(serverAddress, serverPort);
            socketWrapper.write(message);
            Object response = socketWrapper.read();
            if (response instanceof String && response.equals("SUCCESS")) {
                return "SUCCESS";
            } else if (response instanceof String && response.equals("DUPLICATE")) {
                return "DUPLICATE";
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }
}
