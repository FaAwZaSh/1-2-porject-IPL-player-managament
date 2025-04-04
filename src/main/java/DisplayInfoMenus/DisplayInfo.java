package DisplayInfoMenus;

import CurrentPlayers.Player;
import java.util.ArrayList;
import java.util.HashMap;

public class DisplayInfo {

    public void Display(ArrayList<Player> toPrint, int token){
        if(toPrint != null){
            for(Player player : toPrint){

                //Printing details of players
                System.out.println("\tName: " + player.getName().toUpperCase());
                System.out.println("\tCountry: " + player.getCountry().toUpperCase());
                System.out.println("\tAge: " + player.getAge() + " years");
                System.out.println("\tHeight: " + player.getHeight() + " m");
                System.out.println("\tClub: " + player.getClub().toUpperCase());
                System.out.println("\tPosition: " + player.getPosition().toUpperCase());
                if(player.getJersey() == -1){
                    System.out.println("\tNo jersey number");
                }else{
                    System.out.println("\tJersey Number: " + player.getJersey());
                }
                System.out.println("\tSalary: " + player.getSalary() + " Rupees");
                System.out.println();
            }
        }
        else{
            switch (token){
                //Printing error messages
                case 11:
                    System.out.println("No such player with this name exists\n");
                    break;
                case 12:
                    System.out.println("No such player with this country and club exists\n");
                    break;
                case 13:
                    System.out.println("No such player with this position exists\n");
                    break;
                case 14:
                    System.out.println("No such player with this weekly salary range exists\n");
                    break;
                default:
                    System.out.println("No such club with this name exists\n");
            }
        }
    }
    //Overloading for the yearly salary query
    public void Display(String money){
        String [] info = money.split(",");
        if(Long.parseLong(info[0]) == 0){
            System.out.println("No such club with this name exists\n");
        }
        else{
            System.out.println("Total yearly salary of " + info[1].toUpperCase() + " is " + Long.parseLong(info[0]) * 52 + " Rupees\n"); //There are 52 weeks in a year
        }
    }
    //Overloading for the country wise player count
    public void Display(HashMap<String, Integer> countryCount){
        for(String country : countryCount.keySet()){
            System.out.println("\t" + country.toUpperCase() + " has " + countryCount.get(country) + " players");
        }
        System.out.println();
    }
}
