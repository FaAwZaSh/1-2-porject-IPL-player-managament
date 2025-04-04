package Networking;

import File.*;
import CurrentPlayers.*;
import com.example.projectfx.AlertBoxController;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;
import java.util.HashMap;


public class Server {

    private ServerSocket serverSocket;
    private HashMap<String, SocketWrapper> clientMap;
    private HashMap<String, ArrayList<Player>> clublistMap;
    private HashMap<String, String> passMap;
    private File fileOps;
    private UsernamePasswordFile passFile;
    private CurrentPlayers currentPlayers;
    private ArrayList<Player> marketList;
    private ArrayList<Player> masterList;
    private AlertBoxController alertBoxController;


    public Server() throws IOException {

        System.out.println("SERVER ONLINE");
        alertBoxController = new AlertBoxController();
        fileOps = new File();
        marketList = fileOps.loadMarketList();
        masterList = fileOps.loadFile();
        passFile = new UsernamePasswordFile();
        passMap = passFile.loadFile();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                saveData();
            } catch (IOException e) {
                System.err.println("Error saving data during shutdown: " + e.getMessage());
            }
        }));

        currentPlayers = new CurrentPlayers(masterList);
        clientMap = new HashMap<>();
        clublistMap = new HashMap<>();

        try {
            serverSocket = new ServerSocket(44444);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                serve(clientSocket);
            }

        } catch (Exception e) {
            System.out.println("Server starts:" + e);
        }
    }

    public void serve(Socket clientSocket) throws IOException, ClassNotFoundException {

        SocketWrapper socketWrapper = new SocketWrapper(clientSocket);
        Object o = socketWrapper.read();

        if (o instanceof String) {
            String clientName = (String) o;
            if (clientMap.containsKey(clientName)) {
                alertBoxController.showAlert(Alert.AlertType.ERROR, "Duplicate Client", "Client is already logged in.");
                socketWrapper.closeConnection();
                return;
            }

            ArrayList<Player> tempClubList = currentPlayers.ClubQuery(clientName);

            if (tempClubList == null || tempClubList.isEmpty()) {
                tempClubList = new ArrayList<>();
            }

            socketWrapper.write(tempClubList);

            if (marketList == null || marketList.isEmpty()) {
                marketList = new ArrayList<>();
            }

            socketWrapper.write(marketList);
            clientMap.put(clientName, socketWrapper);
            clublistMap.put(clientName, currentPlayers.ClubQuery(clientName));

            new ReadThreadServer(socketWrapper, this);
        } else if (o instanceof Message) {
            Message message = (Message) o;
            String username, pass;
            String[] Data = message.getClientName().split(",");

            int token = message.getToken();
            username = Data[0];
            pass = Data[1];

            switch (token) {
                case 3:
                    if (passMap.containsKey(username)) {
                        socketWrapper.write("ERROR");
                    } else {
                        passMap.put(username, pass);
                        socketWrapper.write("SUCCESS");
                    }
                    break;

                case 4:
                    if (clientMap.containsKey(username)) {
                        socketWrapper.write("DUPLICATE");
                    } else {
                        if (passMap.containsKey(username) && pass.equals(passMap.get(username))) {
                            socketWrapper.write("SUCCESS");
                        } else {
                            socketWrapper.write("ERROR");
                        }
                    }
                    break;
            }
        }
    }

    public synchronized void BuyPlayer(String clientName, Player player) throws IOException {

        boolean flag = marketList.remove(player);

        if (!flag) {
            System.out.println("PLAYER WAS NOT REMOVED FROM MARKET");
        }

        ArrayList<Player> tempClubList = clublistMap.get(clientName);
        if (tempClubList == null || tempClubList.isEmpty()) {
            tempClubList = new ArrayList<>();
        }
        boolean flag2 = tempClubList.add(player);
        clublistMap.put(clientName, tempClubList);
        if (!flag2) {
            System.out.println("PLAYER WAS NOT ADDED TO MASTER");
        }

        broadcast(marketList);
        player.setClub(clientName);
        masterList.add(player);

    }

    public synchronized void SellPlayer(String clientName, Player player) throws IOException {

        boolean flag = masterList.remove(player);
        if (!flag) {
            System.out.println("PLAYER COULD NOT BE REMOVED FROM MASTER");
        }

        if (marketList == null) {
            marketList = new ArrayList<>();
        }

        boolean flag2 = marketList.add(player);

        if (!flag2) {
            System.out.println("PLAYER COULD NOT BE ADDED TO MARKET");
        }

        ArrayList<Player> tempClubList = clublistMap.get(clientName);
        tempClubList.remove(player);
        clublistMap.put(clientName, tempClubList);


        fileOps.saveFile(masterList);
        fileOps.saveMarketList(marketList);

        broadcast(marketList);
    }

    public synchronized void AddPlayer(String clientName, Player player) throws IOException {

        boolean flag = masterList.add(player);
        if (!flag) {
            System.out.println("PLAYER COULD NOT BE ADDED TO MASTER");
        }

        ArrayList<Player> tempClubList = clublistMap.get(clientName);
        tempClubList.add(player);
        clublistMap.put(clientName, tempClubList);

        fileOps.saveFile(masterList);

    }

    public synchronized void broadcast(ArrayList<Player> updatedMarketList) {
        ArrayList<Player> Copy = new ArrayList<>(updatedMarketList);
        for (String clientName : clientMap.keySet()) {
            SocketWrapper clientSocket = clientMap.get(clientName);
            try {
                clientSocket.write(Copy);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) throws IOException {
        new Server();
    }

    public synchronized CurrentPlayers getCurrentPlayers() {
        return currentPlayers;
    }

    public synchronized void setCurrentPlayers(CurrentPlayers currentPlayers) {
        this.currentPlayers = currentPlayers;
    }

    private synchronized void saveData() throws IOException {
        fileOps.saveMarketList(this.marketList);
        fileOps.saveFile(this.masterList);
        passFile.saveFile(passMap);
    }

    public HashMap<String, SocketWrapper> getClientMap() {
        return clientMap;
    }

}
