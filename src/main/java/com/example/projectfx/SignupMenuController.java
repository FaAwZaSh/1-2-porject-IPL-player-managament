package com.example.projectfx;

import CurrentPlayers.CurrentPlayers;
import File.UsernamePasswordFile;
import Networking.Message;
import Utils.SignUp;
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

public class SignupMenuController {

    @FXML
    private TextField usernameSignupField, passwordSignupField, confirmPassField;
    private UsernamePasswordFile file;
    private HashMap<String, String> passMap;
    private CurrentPlayers currentPlayers;
    private final String serverAddress = "127.0.0.1";
    private final int serverPort = 44444;

    public void setStuff(HashMap<String, String> passMap) {
        this.passMap = passMap;
    }

    public void setCurrentPlayers(CurrentPlayers currentPlayers) {
        this.currentPlayers = currentPlayers;
    }

    public void confirmSignupButtonClicked(ActionEvent event) throws IOException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OwnerMenu.fxml"));
        Parent ownerMenuParent = fxmlLoader.load();
        Scene ownerMenuScene = new Scene(ownerMenuParent);

        AlertBoxController alertBoxController;
        alertBoxController = new AlertBoxController();

        String user = usernameSignupField.getText();
        String pass = passwordSignupField.getText();
        String passConfirm = confirmPassField.getText();

        if (user.isEmpty() || pass.isEmpty() || passConfirm.isEmpty()) {
            alertBoxController.showAlert(Alert.AlertType.WARNING, "Empty Fields", "Please make sure to fill all fields");
            return;
        }
        if (!pass.equals(passConfirm)) {
            alertBoxController.showAlert(Alert.AlertType.WARNING, "Password Mismatch", "Passwords do not match");
            confirmPassField.clear();
             return;
        }

        String message = String.join(",", user, pass);
        Message passMessage = new Message(message,3);

        SignUp signUp = new SignUp();
        boolean signUpFlag = signUp.work(passMessage, serverAddress, serverPort);

        if(signUpFlag){
            alertBoxController.showAlert(Alert.AlertType.CONFIRMATION, "Sign-up Successful", "Successfully signed up as " + user.toUpperCase());
        }
        else{
            alertBoxController.showAlert(Alert.AlertType.WARNING, "Invalid Username", "A user with this name already exists");
        }

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(ownerMenuScene);
        window.show();

    }

    public void backButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent mainMenuParent = fxmlLoader.load();

        Scene mainMenuScene = new Scene(mainMenuParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(mainMenuScene);
        window.show();
    }
}

