package DisplayInfoMenus;

import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

import CurrentPlayers.CurrentPlayers;
import CurrentPlayers.Player;

public class Menu {

    private Scanner scn;
    private boolean state = true;
    private ArrayList<Player> playerList;
    CurrentPlayers currentPlayers;

    public Menu(CurrentPlayers currentPlayers)
    {
        this.currentPlayers = currentPlayers;
        scn = new Scanner(System.in);
    }

    //Displays the main menu on startup
    public void displayMainMenu() throws IOException {
        while (state == true) {
            System.out.println("\nMain Menu:");
            System.out.println("(1) Search Players");
            System.out.println("(2) Search Clubs");
            System.out.println("(3) Add Player");
            System.out.println("(4) Exit System");
            System.out.print("\nEnter your choice: ");

            String input = scn.nextLine().trim();
            if (input.length() > 1 && input.startsWith("0")) {
                input = input.replace("0", "");
            }

            switch (input) {

                case "1":
                    PlayerOperations sPlayers = new PlayerOperations(currentPlayers, scn);
                    sPlayers.displayMenu();
                    break;

                case "2":
                    ClubOperations sClubs = new ClubOperations(currentPlayers, scn);
                    sClubs.displayMenu();
                    break;

                case "3":
                    System.out.print("Enter player's name: ");
                    String name = scn.nextLine().trim();

                    System.out.print("Enter player's country: ");
                    String country = scn.nextLine().trim();

                    System.out.print("Enter player's age: ");
                    String age = scn.nextLine().trim();

                    System.out.print("Enter player's height: ");
                    String height = scn.nextLine().trim();

                    System.out.print("Enter player's club: ");
                    String club = scn.nextLine().trim();

                    System.out.print("Enter player's position: ");
                    String position = scn.nextLine().trim();

                    System.out.print("Enter player's jersey number (Press enter to skip): ");
                    String jerseyNumber = scn.nextLine().trim();

                    System.out.print("Enter player's salary: ");
                    String salary = scn.nextLine().trim();

                    boolean added = currentPlayers.AddPlayers(name, country, age, height, club, position, jerseyNumber, salary);
                    if (!added) {
                        System.out.println("Player with this name already exists. Please enter a unique player.");
                    } else {
                        System.out.println("Player successfully added");
                    }
                    break;

                case "4":
                    state = false;
                    System.out.println("Exiting the program. Thank you for using.");
                    break;

                default:
                    System.out.println("INVALID INPUT. Please enter a valid input");
                    break;
            }
        }
        scn.close();
    }

    //Displays the Search by Players menu
    public void displaySearchPlayersMenu() {
        System.out.println("Player Searching Options:");
        System.out.println("(1) By Player Name");
        System.out.println("(2) By Club and Country");
        System.out.println("(3) By Position");
        System.out.println("(4) By Salary Range");
        System.out.println("(5) Country-wise Player Count");
        System.out.println("(6) Back to Main Menu");
        System.out.print("\nEnter your choice: ");
    }

    //Displays the Search by Club menu
    public void displaySearchClubsMenu() {
        System.out.println("Club Searching Options:");
        System.out.println("(1) Player(s) with the maximum salary of a club");
        System.out.println("(2) Player(s) with the maximum age of a club");
        System.out.println("(3) Player(s) with the maximum height of club");
        System.out.println("(4) Total yearly salary of a club");
        System.out.println("(5) Back to Main Menu");
        System.out.print("\nEnter your choice: ");
    }
}

