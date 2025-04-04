package com.example.projectfx;

import CurrentPlayers.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class PlayerDetailsController {
    @FXML private Label nameLabel;
    @FXML private Label countryLabel;
    @FXML private Label clubLabel;
    @FXML private Label positionLabel;
    @FXML private Label salaryLabel;
    @FXML private Label ageLabel;
    @FXML private Label heightLabel;
    @FXML private Label jerseyLabel;

    public void setPlayerDetails(Player player) {

        nameLabel.setText("Name: " + player.getName());
        countryLabel.setText("Country: " + player.getCountry());
        clubLabel.setText("Club: " + player.getClub());
        positionLabel.setText("Position: " + player.getPosition());
        salaryLabel.setText("Salary: " + player.getSalary() + " Rupees");
        ageLabel.setText("Age: " + player.getAge() + " years");
        heightLabel.setText("Height: " + player.getHeight() + " meters");
        jerseyLabel.setText("Jersey number: " + player.getJersey());
    }

    public void show(Player player) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PlayerDetails.fxml"));
        Parent root = loader.load();

        PlayerDetailsController controller = loader.getController();
        controller.setPlayerDetails(player);

        Stage stage = new Stage();
        stage.setTitle("Player Details");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void showCountryCount(HashMap <String, Integer> HashCountryCount) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CountryCount.fxml"));
        Parent showCountryCountryRoot = loader.load();

        CountryCountController countryCountController = loader.getController();
        countryCountController.setStuff(HashCountryCount);

        Stage stage = new Stage();
        stage.setTitle("Country-wise Player Count");
        stage.setScene(new Scene(showCountryCountryRoot));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
