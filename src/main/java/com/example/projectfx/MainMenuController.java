package com.example.projectfx;

import CurrentPlayers.CurrentPlayers;
import File.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import CurrentPlayers.Player;

public class MainMenuController
{

    private CurrentPlayers currentPlayers;
    private File fileOps;
    private ArrayList<Player> marketList;

    public MainMenuController() throws IOException {
        fileOps = new File();
        currentPlayers = new CurrentPlayers(fileOps.loadFile());
    }


    public void guestButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GuestMenu.fxml"));
        Parent guestParent = fxmlLoader.load();
        Scene guestScene = new Scene(guestParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(guestScene);
        window.show();

        GuestMenuController guestController = fxmlLoader.getController();
        guestController.setCurrentPlayers(currentPlayers);
    }

    public void exitButtonClicked(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    public void ownerButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OwnerMenu.fxml"));
        Parent ownerParent = fxmlLoader.load();
        Scene ownerScene = new Scene(ownerParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(ownerScene);
        window.show();
    }
}