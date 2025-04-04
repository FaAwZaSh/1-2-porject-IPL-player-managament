package com.example.projectfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class OwnerMenuController {

    HashMap<String, String> passMap;

    public void setStuff(HashMap<String, String> passMap) {
        this.passMap = passMap;
    }

    public void loginButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginMenu.fxml"));
        Parent loginParent = fxmlLoader.load();
        Scene loginScene = new Scene(loginParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(loginScene);
        window.show();
    }

    public void signupButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SignupMenu.fxml"));
        Parent signupParent = fxmlLoader.load();
        Scene signupScene = new Scene(signupParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(signupScene);
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
