package Networking;

import CurrentPlayers.Player;

import java.io.Serializable;

public class Message implements Serializable {
    private String clientName;
    private Player player;
    private int token;

    public Message(String clientName, Player player, int token) {
        this.clientName = clientName;
        this.player = player;
        this.token = token;
    }
    public Message(String cred, int token){
        this.clientName = cred;
        this.token = token;
    }

    public String getClientName() {
        return clientName;
    }

    public Player getPlayer() {
        return player;
    }

    public int getToken() {
        return token;
    }
}
