package com.example.projectfx;

import File.UsernamePasswordFile;
import Networking.Client;
import Networking.Message;
import Utils.LogIn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class LoginMenuController {

    @FXML
    private TextField usernameField, passwordField;
    private Client client;
    private final String serverAddress = "127.0.0.1";
    private final int serverPort = 44444;
    private String user;
    private HashMap<String, String> passMap;


    public void setStuff(HashMap<String, String> passMap){
        this.passMap = passMap;
    }

    public void confirmButtonClicked(ActionEvent event) throws IOException {

        AlertBoxController alertBoxController;
        alertBoxController = new AlertBoxController();

        user = usernameField.getText();
        String pass = passwordField.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            alertBoxController.showAlert(Alert.AlertType.WARNING, "Empty Fields", "Please make sure to fill all fields");
            clearFields();
            return;
        }

        String message = String.join(",", user, pass);
        Message passMessage = new Message(message, 4);

        LogIn logIn = new LogIn();
        String LogInFlag = logIn.work(passMessage, serverAddress, serverPort);

        if(LogInFlag.equals("SUCCESS")){
            alertBoxController.showAlert(Alert.AlertType.CONFIRMATION, "Login Successful", "Successfully logged in as " + user.toUpperCase());

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OwnerMenu.fxml"));
            Parent ownerMenuParent = fxmlLoader.load();
            Scene ownerMenuScene = new Scene(ownerMenuParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(ownerMenuScene);
            window.show();

            FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("ClientMenu.fxml"));
            Parent clientParent = fxmlLoader2.load();

            client = new Client(user, serverAddress, serverPort);

            Stage clientStage = new Stage();
            clientStage.setTitle("Client - " + user.toUpperCase());

            ClientMenuController clientController = fxmlLoader2.getController();
            clientController.setStuff(client, clientStage);

            clientStage.setScene(new Scene(clientParent));
            clientStage.show();
        } else if (LogInFlag.equals("DUPLICATE")) {
            alertBoxController.showAlert(Alert.AlertType.WARNING, "Login Failed", "Client already in use.");
            passwordField.clear();
        } else{
            alertBoxController.showAlert(Alert.AlertType.WARNING, "Login Failed", "Invalid Username or Password or Client already in use");
            passwordField.clear();
            usernameField.clear();
        }
    }

    public void clearFields(){
        usernameField.clear();
        passwordField.clear();
    }

    public void backButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OwnerMenu.fxml"));
        Parent mainMenuParent = fxmlLoader.load();

        Scene mainMenuScene = new Scene(mainMenuParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainMenuScene);
        window.show();
    }
}
