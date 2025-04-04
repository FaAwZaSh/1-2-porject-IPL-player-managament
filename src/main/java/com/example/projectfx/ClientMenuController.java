package com.example.projectfx;

import Networking.Client;
import Networking.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientMenuController {
    @FXML
    private Label welcomeLabel;



    private Client client;
    private AlertBoxController alertBoxController;
    private Stage stage;

    public void setStuff(Client client, Stage stage) {
        this.client = client;
        this.stage = stage;
        welcomeLabel.setText("Welcome " + client.getClientName() + " Manager");
        alertBoxController = new AlertBoxController();

        stage.setOnCloseRequest(event -> {
            event.consume();
            try {
                logoutButtonClicked();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void searchButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ClientSearch.fxml"));
        Parent clientSearchParent = fxmlLoader.load();

        Scene clientSearchScene = new Scene(clientSearchParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(clientSearchScene);
        window.show();

        ClientSearchController clientSearchController = fxmlLoader.getController();
        clientSearchController.setStuff(client);
    }

    public void viewButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ClientList.fxml"));
        Parent clientListParent = fxmlLoader.load();

        Scene clientListScene = new Scene(clientListParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(clientListScene);
        window.show();

        ClientListController clientListController = fxmlLoader.getController();
        clientListController.setStuff(client);
    }

    public void addButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ClientAdd.fxml"));
        Parent clientAddParent = fxmlLoader.load();

        Scene clientAddScene = new Scene(clientAddParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(clientAddScene);
        window.show();

        ClientAddController clientAddController = fxmlLoader.getController();
        clientAddController.setStuff(client);

    }

    public void buyButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BuyList.fxml"));
        Parent buyListParent = fxmlLoader.load();

        Scene buyListScene = new Scene(buyListParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(buyListScene);
        window.show();

        BuyListController buyListController = fxmlLoader.getController();
        buyListController.setStuff(client);
    }

    public void sellButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SellList.fxml"));
        Parent selltListParent = fxmlLoader.load();

        Scene sellListScene = new Scene(selltListParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(sellListScene);
        window.show();

        SellListController sellListController = fxmlLoader.getController();
        sellListController.setStuff(client);
    }


    public void logoutButtonClicked() throws IOException {
        Stage window = (Stage) welcomeLabel.getScene().getWindow();
        window.close();
        Message message = new Message(client.getClientName(), 5);
        client.socketWrapper.write(message);
        client.socketWrapper.closeConnection();
        alertBoxController.showAlert(Alert.AlertType.CONFIRMATION, "Logout", "Successfully logged out of the user: " + client.getClientName().toUpperCase());
    }
}
