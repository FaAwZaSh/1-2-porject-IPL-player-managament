package com.example.projectfx;

import CurrentPlayers.*;
import File.File;
import Networking.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientAddController {

    private CurrentPlayers currentPlayers;
    private String username;
    private Client client;
    private File file;

    @FXML
    private TextField nameField, countryField, ageField, heightField, positionField, jerseyField, salaryField;

    public void setStuff(Client client) throws IOException {
        this.client = client;
        this.currentPlayers = new CurrentPlayers(client.getClubList());
        this.username = client.getClientName();
    }

    public void addButtonClicked(ActionEvent event) throws IOException {

        AlertBoxController alertBoxController = new AlertBoxController();

        String name, country, age, height, position, jersey, salary;

        name = nameField.getText();
        country = countryField.getText();
        age = ageField.getText();
        height = heightField.getText();
        position = positionField.getText();
        jersey = jerseyField.getText();
        salary = salaryField.getText();

        try{
            Integer.parseInt(age);
            Integer.parseInt(salary);
            Double.parseDouble(height);
            if(!jersey.isEmpty()){
                try {
                    Integer.parseInt(jersey);
                }catch (Exception e){
                    alertBoxController.showAlert(Alert.AlertType.WARNING, "Input-type mismatch", "Please enter valid numeric inputs");
                    jerseyField.clear();
                    ageField.clear();
                    heightField.clear();
                    salaryField.clear();
                }
            }
        }catch (Exception e){
            alertBoxController.showAlert(Alert.AlertType.WARNING, "Input-type mismatch", "Please enter valid numeric inputs");
            ageField.clear();
            heightField.clear();
            salaryField.clear();
            return;
        }

        currentPlayers.AddPlayers(name, country, age, height, username, position, jersey, salary);

        int jerseyNum = jersey.isBlank() ? -1 : Integer.parseInt(jersey);
        Player newPlayer = new Player(name, country, Integer.parseInt(age), Double.parseDouble(height), username, position, jerseyNum, Integer.parseInt(salary));

        client.AddPlayer(client.getClientName(), newPlayer);

        file = new File();
        file.appendFile(newPlayer, "src/players.txt");

        alertBoxController.showAlert(Alert.AlertType.CONFIRMATION, "Player Added", "Successfully added " + newPlayer.getName().toUpperCase());

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ClientMenu.fxml"));
        Parent clientMenuParent = fxmlLoader.load();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene clientMenuScene = new Scene(clientMenuParent);

        ClientMenuController clientMenuController = fxmlLoader.getController();
        clientMenuController.setStuff(client, window);

        window.setScene(clientMenuScene);
        window.show();

    }

    public void backButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ClientMenu.fxml"));
        Parent clientMenuParent = fxmlLoader.load();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        ClientMenuController clientMenuController = fxmlLoader.getController();
        clientMenuController.setStuff(client, window);

        Scene clientMenuScene = new Scene(clientMenuParent);
        window.setScene(clientMenuScene);
        window.show();
    }
}
