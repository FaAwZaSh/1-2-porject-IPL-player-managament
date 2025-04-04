package com.example.projectfx;

import CurrentPlayers.CurrentPlayers;
import CurrentPlayers.Player;
import Networking.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ClientListController {
    @FXML
    private TableColumn<Player, String> nameColumn;
    @FXML
    private TableColumn<Player, String> positionColumn;
    @FXML
    private TableColumn<Player, String> countryColumn;
    @FXML
    private TableColumn<Player, String> clubColumn;
    @FXML
    private TableView clientTablePlayers;

    private String user;
    private Client client;
    private ArrayList<Player> clubList;
    private PlayerDetailsController pd;
    private CurrentPlayers currentPlayers;

    public void setStuff(Client client) throws IOException {

        this.client = client;
        this.user = client.getClientName();
        this.currentPlayers = new CurrentPlayers(client.getClubList());
        pd = new PlayerDetailsController();
        clubList = client.getClubList();

        if (clubList == null) {
            clubList = new ArrayList<>();
        }

        if (clubList.isEmpty()) {
            ObservableList<Player> observableMarketList = FXCollections.observableArrayList();
            clientTablePlayers.setItems(observableMarketList);
            clientTablePlayers.setPlaceholder(new Label("No players available to display."));
        } else {
            ObservableList<Player> observableClubList = FXCollections.observableArrayList(clubList);
            clientTablePlayers.setItems(observableClubList);
        }
    }

    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        clubColumn.setCellValueFactory(new PropertyValueFactory<>("club"));
        pd = new PlayerDetailsController();

        clientTablePlayers.setRowFactory(do_sth -> {
            TableRow<Player> row = new TableRow<>() {
                @Override
                public void updateItem(Player player, boolean empty) {
                    super.updateItem(player, empty);
                    setPrefHeight(empty ? 30 : 50);
                }
            };

            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) {
                    Player clickedPlayer = row.getItem();
                    try {
                        pd.show(clickedPlayer);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return row;
        });
    }


    public void backButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ClientMenu.fxml"));
        Parent clientRoot = fxmlLoader.load();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        ClientMenuController clientMenuController = fxmlLoader.getController();
        clientMenuController.setStuff(client, window);

        Scene clientMenuScene = new Scene(clientRoot);
        window.setScene(clientMenuScene);
        window.show();
    }
}
