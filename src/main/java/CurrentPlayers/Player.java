package CurrentPlayers;


import java.io.Serializable;
import java.util.Objects;

public class Player implements Serializable {
    private String name;
    private String country;
    private int age;
    private double height;
    private String club;
    private String position;
    private int jersey;
    private String jers = "";
    private int salary;

    public Player(String name, String country, int age, double height, String club, String position, int jersey, int salary) {
        this.name = name;
        this.country = country;
        this.age = age;
        this.height = height;
        this.club = club;
        this.position = position;
        this.jersey = jersey;
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Same reference
        if (o == null || getClass() != o.getClass()) return false; // Null or different class
        Player player = (Player) o;
        return Objects.equals(name, player.name); // Compare players based on their name
    }

    @Override
    public int hashCode() {
        return Objects.hash(name); // Generate hash based on id
    }

    @Override
    public String toString() {
        if (jersey == -1) {
            return name + "," + country + "," + age + "," + height + "," + club + "," + position + "," + jers + "," + salary;
        }
        return name + "," + country + "," + age + "," + height + "," + club + "," + position + "," + jersey + "," + salary;
    }
    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public int getAge() {
        return age;
    }

    public double getHeight() {
        return height;
    }

    public String getClub() {
        return club;
    }

    public String getPosition() {
        return position;
    }

    public int getJersey() {
        return jersey;
    }

    public int getSalary() {
        return salary;
    }

    public void setClub(String club){
        this.club = club;
    }
}
