package Main;

import CurrentPlayers.CurrentPlayers;
import CurrentPlayers.Player;
import DisplayInfoMenus.Menu;
import File.*;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {

        File fileOps = new File(); 
        ArrayList<Player> playersFromFile = fileOps.loadFile();
        CurrentPlayers currentPlayers = new CurrentPlayers(playersFromFile);

        Menu menu = new Menu(currentPlayers);
        menu.displayMainMenu();
        
        fileOps.saveFile(playersFromFile);
    }
}