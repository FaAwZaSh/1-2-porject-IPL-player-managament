package CurrentPlayers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CurrentPlayers {
    public ArrayList<Player> currPlayerList;
    private ArrayList<Player> temp;

    public CurrentPlayers(ArrayList<Player> currPlayerList) throws IOException {
        temp = new ArrayList<>();
        this.currPlayerList = currPlayerList;
    }

    public ArrayList<Player> playerNameQuery(String name){
        temp.clear();
        for(Player player: currPlayerList){
            if(name.equalsIgnoreCase(player.getName())){
                temp.add(player);
                return temp;
            }
        }
        return null;
    }

    public ArrayList<Player> countryClubQuery(String country, String club){
        temp.clear();
        boolean found = false;
        for(Player player: currPlayerList){
            if (country.equalsIgnoreCase(player.getCountry())) {
                if (club.equalsIgnoreCase("ANY") || club.equalsIgnoreCase(player.getClub())) {
                    temp.add(player);
                    found = true;
                }
            }
        }
        if(!found){
            return null;
        }
        return temp;
    }

    public ArrayList<Player> ClubQuery(String club){
        temp.clear();
        boolean found = false;
        for(Player player: currPlayerList){
                if (club.equalsIgnoreCase("ANY") || club.equalsIgnoreCase(player.getClub())) {
                    temp.add(player);
                    found = true;
            }
        }
        if(!found){
            return null;
        }
        return temp;
    }

    public ArrayList<Player> positionQuery(String position){
        temp.clear();
        boolean found = false;
        for(Player player: currPlayerList){
            if(position.equalsIgnoreCase(player.getPosition())){
                temp.add(player);
                found = true;
            }
        }if(!found){
            return null;
        }
        return temp;
    }

    public ArrayList<Player> salaryQuery(int upper, int lower){
        temp.clear();
        boolean found = false;

        for(Player player: currPlayerList){
            if(player.getSalary() <= upper && player.getSalary() >= lower){
                found = true;
                temp.add(player);
            }
        }if(!found){
            return null;
        }
        return temp;
    }


    public HashMap<String, Integer> countryWisePlayerQuery(){
        HashMap<String, Integer> countryCount = new HashMap<>();
        for(Player player : currPlayerList){
            String country = player.getCountry().toLowerCase();
            if(countryCount.containsKey(country)){
                countryCount.put(country, countryCount.get(country) + 1);
            }
            else{
                countryCount.put(country, 1);
            }
        }
        return countryCount;
    }

    public ArrayList<Player> maxAgeQuery(String club){
        temp.clear();
        int max = 0;
        boolean found = false;
        for(Player player: currPlayerList){
            if(club.equalsIgnoreCase(player.getClub())){
                found = true;
                if(player.getAge() >= max){
                    max = player.getAge();
                }
            }
        }
        if(!found){
            return null;
        }
        else{
            for(Player player: currPlayerList){
                if(club.equalsIgnoreCase(player.getClub())){
                    if(player.getAge() == max){
                        temp.add(player);
                    }
                }
            }
        }
        return temp;
    }

    public ArrayList<Player> maxHeightQuery(String club){
        temp.clear();
        double max = 0;
        boolean found = false;
        for(Player player: currPlayerList){
            if(club.equalsIgnoreCase(player.getClub())){
                found = true;
                if(player.getHeight() >= max){
                    max = player.getHeight();
                }
            }
        }
        if(!found){
            return null;
        }
        else{
            for(Player player: currPlayerList){
                if(club.equalsIgnoreCase(player.getClub())){
                    if(player.getHeight() == max){
                        temp.add(player);
                    }
                }
            }
        }
        return temp;
    }

    public ArrayList<Player> maxSalaryQuery(String club){
        temp.clear();
        int max = 0;
        boolean found = false;
        for(Player player: currPlayerList){
            if(club.equalsIgnoreCase(player.getClub())){
                found = true;
                if(player.getSalary() >= max){
                    max = player.getSalary();
                }
            }
        }
        if(!found){
            return null;
        }
        else{
            for(Player player: currPlayerList){
                if(club.equalsIgnoreCase(player.getClub())){
                    if(player.getSalary() == max){
                        temp.add(player);
                    }
                }
            }
        }
        return temp;
    }

    public String totalSalaryQuery(String club){
        boolean found = false;
        long cumSalary = 0;
        for(Player player: currPlayerList){
            if(club.equalsIgnoreCase(player.getClub())){
                found = true;
                cumSalary += player.getSalary();
            }
        }
        if(!found){
            return "0,";
        }
        else{
            return String.join(",",String.valueOf(cumSalary), club);
        }
    }

    public boolean AddPlayers(String name, String country, String age, String height, String club, String position, String jerseyNumber, String salary) {
        int jerseyNum = jerseyNumber.isBlank() ? -1 : Integer.parseInt(jerseyNumber);
        Player newPlayer = new Player(name, country, Integer.parseInt(age), Double.parseDouble(height), club, position, jerseyNum, Integer.parseInt(salary));

        for (Player pl : currPlayerList) {
            if (newPlayer.getName().equalsIgnoreCase(pl.getName())) {
                return false;
            }
        }
        currPlayerList.add(newPlayer);
        return true;
    }



}
