package Networking;

import CurrentPlayers.Player;

import java.io.IOException;
import java.util.HashMap;


public class ReadThreadServer implements Runnable {
    private Thread thr;
    private SocketWrapper socketWrapper;
    private Server server;
    private String clientName;
    private Player player;
    private int token;

    public ReadThreadServer(SocketWrapper socketWrapper, Server server) {
        this.socketWrapper = socketWrapper;
        this.server = server;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {

        try {
            while (true) {
                Object message = socketWrapper.read();
                if (message instanceof Message) {

                    this.token = ((Message) message).getToken();
                }
                    switch (token) {
                        case 1:
                            this.clientName = ((Message) message).getClientName();
                            this.player = ((Message) message).getPlayer();
                            server.BuyPlayer(clientName, player);
                            break;

                        case 2:
                            this.clientName = ((Message) message).getClientName();
                            this.player = ((Message) message).getPlayer();
                            server.SellPlayer(clientName, player);
                            break;

                        case 5:
                            String tempName = ((Message) message).getClientName();
                            HashMap<String, SocketWrapper> clientMap = server.getClientMap();
                            clientMap.remove(tempName);
                            break;

                        case 6:
                            this.clientName = ((Message) message).getClientName();
                            this.player = ((Message) message).getPlayer();
                            server.AddPlayer(clientName, player);
                            break;

                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                socketWrapper.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



