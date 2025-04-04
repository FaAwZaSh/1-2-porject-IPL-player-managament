package Networking;


import CurrentPlayers.Player;
import com.example.projectfx.BuyListController;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Client implements Serializable {
    public SocketWrapper socketWrapper;
    private ArrayList<Player> clubList;
    private ArrayList<Player> marketList;
    private String clientName;
    private Message message;
    private BuyListController buyListController;
    private ReadThreadClient readThreadClient;

    public Client(String clientName, String serverAddress, int serverPort) {
        this.clientName = clientName;

        try {
            socketWrapper = new SocketWrapper(serverAddress, serverPort);
            socketWrapper.write(clientName);

            Object CLUBLIST = socketWrapper.read();
            Object MARKETLIST = socketWrapper.read();

            if (CLUBLIST instanceof ArrayList<?> && MARKETLIST instanceof ArrayList<?>) {
                clubList = (ArrayList<Player>) CLUBLIST;
                marketList = (ArrayList<Player>) MARKETLIST;

                for (Player test: marketList){
                    System.out.println(test);
                }
            }

            readThreadClient = new ReadThreadClient(socketWrapper, this);
            System.out.println("CLIENT : " + clientName + " ONLINE\n");

        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void BuyPlayer (String clientName, Player player) throws IOException {
        message = new Message(clientName, player, 1);
        this.socketWrapper.write(message);
    }

    public void SellPlayer (String clientName, Player player) throws IOException {
        message = new Message(clientName, player, 2);
        socketWrapper.write(message);
    }

    public void AddPlayer(String clientName, Player player) throws IOException{
        message = new Message(clientName, player, 6);
        this.socketWrapper.write(message);
    }
    public ArrayList<Player> getClubList() {
        return clubList;
    }

    public ArrayList<Player> getMarketList() {
        return marketList;
    }

    public void setMarketListClient(ArrayList<Player> marketList) {
        this.marketList = marketList;
    }

    public String getClientName() {
        return clientName;
    }

    public BuyListController getBuyListController() {
        return buyListController;
    }

    public void setBuyListController(BuyListController buyListController) {
        this.buyListController = buyListController;
    }
}
