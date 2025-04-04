package DisplayInfoMenus;

import CurrentPlayers.CurrentPlayers;
import CurrentPlayers.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class ClubOperations {

    private String club;
    private Scanner scn;
    private boolean state = true;
    private DisplayInfo dis;
    private Menu menu;
    private CurrentPlayers currentPlayers;
    private ArrayList<Player> temp;

    public ClubOperations(CurrentPlayers currentPlayers, Scanner scn) {
        this.scn = scn;
        this.currentPlayers = currentPlayers;
        dis = new DisplayInfo();
        menu = new Menu(currentPlayers);
    }

    public void displayMenu() {

        while (state == true) {

            menu.displaySearchClubsMenu();   //Displays the search clubs menu
            String input = scn.nextLine().trim();
            if (input.length() > 1) {
                input = input.replace("0", "");
            }
            if (input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4")) {
                System.out.print("Enter the name of the club: ");
                club = scn.nextLine().trim();
            }

            switch (input) {
                case "1":
                    temp = currentPlayers.maxSalaryQuery(club);
                    if (temp == null) {
                        dis.Display(null, 21);
                        System.out.println("Here");
                    } else {
                        System.out.println("Highest paid players in " + club.toUpperCase() + " is/are: ");
                        dis.Display(temp, 21);
                    }
                    break;

                case "2":
                    temp = currentPlayers.maxAgeQuery(club);
                    if (temp == null) {
                        dis.Display(null, 22);
                    } else {
                        System.out.println("Oldest player(s) in the club " + club.toUpperCase() + " is/are:");
                        dis.Display(temp, 22);
                    }
                    break;

                case "3":
                    temp = currentPlayers.maxHeightQuery(club);
                    if (temp == null) {
                        dis.Display(null, 23);
                    } else {
                        System.out.println("\nTallest player(s) in " + club.toUpperCase() + " is/are: ");
                        dis.Display(temp, 23);
                    }
                    break;

                case "4":
                    dis.Display(currentPlayers.totalSalaryQuery(club));
                    break;

                case "5":
                    state = false;
                    break;

                default:
                    System.out.println("Invalid input, please enter a valid input\n");
                    break;
            }
        }
    }
}
