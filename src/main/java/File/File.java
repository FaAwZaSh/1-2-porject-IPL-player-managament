package File;

import CurrentPlayers.Player;

import java.io.*;
import java.util.ArrayList;

public class File{
    
    private static final String INPUT_FILE_NAME = "src/players.txt";
    private static final String OUTPUT_FILE_NAME = "src/players.txt";

    public ArrayList<Player> loadFile() throws IOException {
        ArrayList<Player> playerList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME));

        while (true) {
            String line = br.readLine();
            if (line == null){
                break;
            }
            String [] info = line.split(",");
            if(info[6].isEmpty()){
                Player newPlayer = new Player(info[0], info[1], Integer.parseInt(info[2]), Double.parseDouble(info[3]), info[4], info[5], -1, Integer.parseInt(info[7]));
                playerList.add(newPlayer);
            }
            else{
                Player newPlayer = new Player(info[0], info[1], Integer.parseInt(info[2]), Double.parseDouble(info[3]), info[4], info[5], Integer.parseInt(info[6]), Integer.parseInt(info[7]));
                playerList.add(newPlayer);
            }
        }
        br.close();
        return playerList;
    }


    public void saveFile(ArrayList<Player> playerList) throws IOException {

        BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));

        for(Player player : playerList){
            if(player.getJersey() == -1){
                bw.write(player.toString() + "\n");
            }
            else{
                bw.write(player.toString() + "\n");
            }
        }
        bw.close();
    }

    public void appendFile(Player player, String filename) throws IOException {

        BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true));

            if(player.getJersey() == -1){
                bw.write("\n" + player.toString());
            }
            else{
                bw.write("\n" + player.toString());
            }
        bw.close();
    }

    public void saveMarketList(ArrayList<Player> updatedMarketList) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("src/marketList.txt"));

        for(Player player : updatedMarketList){
            if(player.getJersey() == -1){
                bw.write(player.toString() + "\n");
            }
            else{
                bw.write(player.toString() + "\n");
            }
        }
        bw.close();
    }

    public ArrayList<Player> loadMarketList() throws IOException {

        ArrayList<Player> playerList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("src/marketList.txt"));

        while (true) {
            String line = br.readLine();
            if (line == null){
                break;
            }
            String [] info = line.split(",");
            if(info[6].isEmpty()){
                Player newPlayer = new Player(info[0], info[1], Integer.parseInt(info[2]), Double.parseDouble(info[3]), info[4], info[5], -1, Integer.parseInt(info[7]));
                playerList.add(newPlayer);
            }
            else{
                Player newPlayer = new Player(info[0], info[1], Integer.parseInt(info[2]), Double.parseDouble(info[3]), info[4], info[5], Integer.parseInt(info[6]), Integer.parseInt(info[7]));
                playerList.add(newPlayer);
            }
        }
        br.close();
        return playerList;
    }
}
