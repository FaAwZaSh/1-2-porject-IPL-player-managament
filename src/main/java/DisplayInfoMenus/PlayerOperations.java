package DisplayInfoMenus;

import CurrentPlayers.CurrentPlayers;

import java.util.Scanner;


public class PlayerOperations {

    private Scanner scn;
    private boolean state = true;
    private DisplayInfo dis;
    private Menu menu;
    private CurrentPlayers currentPlayers;

    public PlayerOperations(CurrentPlayers currentPlayers, Scanner scn) {
        this.scn = scn;
        dis = new DisplayInfo();
        menu = new Menu(currentPlayers);
        this.currentPlayers = currentPlayers;
    }

    public void displayMenu() {

        while (state == true) {

            menu.displaySearchPlayersMenu();   //Displays the search players menu
            String input = scn.nextLine().trim();
            if (input.length() > 1 && input.startsWith("0")) {
                input = input.replace("0", "");
            }
            switch (input) {

                case "1":
                    System.out.print("Enter a player's name: ");
                    String name = scn.nextLine().trim();
                    dis.Display(currentPlayers.playerNameQuery(name), 11);
                    break;

                case "2":
                    System.out.print("Enter a country: ");
                    String country = scn.nextLine().trim();
                    System.out.print("Enter a club: ");
                    String club = scn.nextLine().trim();
                    dis.Display(currentPlayers.countryClubQuery(country, club), 12);
                    break;

                case "3":
                    System.out.print("Enter a position: ");
                    String position = scn.nextLine().trim();
                    dis.Display(currentPlayers.positionQuery(position), 13);
                    break;

                case "4":
                    System.out.print("Enter an upper limit: ");
                    int upper = scn.nextInt();
                    scn.nextLine();

                    System.out.print("Enter a lower limit: ");
                    int lower = scn.nextInt();
                    scn.nextLine();

                    dis.Display(currentPlayers.salaryQuery(upper, lower), 14);
                    break;
                case "5":
                    dis.Display(currentPlayers.countryWisePlayerQuery());
                    break;

                case "6":
                    state = false;
                    break;

                default:
                    System.out.println("Invalid input, please enter a valid input\n");
                    break;
            }
        }
    }
}

