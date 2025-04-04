package com.example.projectfx;

import CurrentPlayers.*;
import Networking.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ClientSearchController {

    private AlertBoxController alertController;

    private ArrayList<Player> clubList, temp;
    private PlayerDetailsController pd;
    private CurrentPlayers currentPlayers;
    private Client client;
    private AlertBoxController alertBoxController;

    public void setStuff(Client client) throws IOException {
        this.client = client;
        this.currentPlayers = new CurrentPlayers(client.getClubList());
        this.clubList = client.getClubList();
        alertBoxController = new AlertBoxController();
        pd = new PlayerDetailsController();
        temp = new ArrayList<>();
    }

    public void clientBackButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ClientMenu.fxml"));
        Parent mainMenuClientParent = fxmlLoader.load();

        Scene mainMenuScene = new Scene(mainMenuClientParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        ClientMenuController clientMenuController = fxmlLoader.getController();
        clientMenuController.setStuff(client, window);

        window.setScene(mainMenuScene);
        window.show();
    }

    public void oldestButtonClicked(ActionEvent event) throws IOException {
        try {
            temp = currentPlayers.maxAgeQuery(client.getClientName());
            if (temp == null) {
                alertBoxController.showAlert(Alert.AlertType.ERROR, "Player Unavailable", "No such player found");

            } else {
                pd.show(temp.get(0));
            }
        } catch (IOException e) {
            alertBoxController.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }


    public void richestButtonClicked(ActionEvent event) throws IOException {
        try {
            temp = currentPlayers.maxSalaryQuery(client.getClientName());
            if (temp == null) {
                alertBoxController.showAlert(Alert.AlertType.ERROR, "Player Unavailable", "No such player found");

            } else {
                pd.show(temp.get(0));
            }
        } catch (IOException e) {
            alertBoxController.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    public void tallestButtonClicked(ActionEvent event) throws IOException {
        try {
            temp = currentPlayers.maxHeightQuery(client.getClientName());
            if (temp == null) {
                alertBoxController.showAlert(Alert.AlertType.ERROR, "Player Unavailable", "No such player found");

            } else {
                pd.show(temp.get(0));
            }
        } catch (IOException e) {
            alertBoxController.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    public void countryWiseCountButtonClicked(ActionEvent event) throws IOException {
        pd.showCountryCount(currentPlayers.countryWisePlayerQuery());
    }
}

