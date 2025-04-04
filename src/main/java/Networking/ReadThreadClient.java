package Networking;


import CurrentPlayers.Player;
import javafx.application.Platform;

import java.io.IOException;
import java.util.ArrayList;

public class ReadThreadClient implements Runnable {
    private Thread thr;
    private SocketWrapper socketWrapper;
    private Client client;

    public ReadThreadClient(SocketWrapper socketWrapper, Client client) {
        this.socketWrapper = socketWrapper;
        this.thr = new Thread(this);
        this.client = client;
        thr.start();
    }

    public void run() {

        try {
            while (true) {
                Object temp = socketWrapper.read();
                if (temp instanceof ArrayList<?>) {
                    ArrayList<Player> localMarketList = (ArrayList<Player>) temp;
                    client.setMarketListClient(localMarketList);

                    if (client.getBuyListController() != null) {
                        Platform.runLater(() -> {
                            client.getBuyListController().populateTable(localMarketList);
                        });
                    }
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
