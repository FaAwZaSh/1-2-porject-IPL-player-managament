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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class BuyListController {

    @FXML
    private TableColumn<Player, String> nameColumn;
    @FXML
    private TableColumn<Player, String> positionColumn;
    @FXML
    private TableColumn<Player, String> countryColumn;
    @FXML
    private TableColumn<Player, String> ageColumn;
    @FXML
    private TableColumn<Player, String> heightColumn;
    @FXML
    private TableColumn<Player, String> jerseyColumn;
    @FXML
    private TableColumn<Player, String> salaryColumn;
    @FXML
    private TableView<Player> clientTablePlayers;

    private Client client;
    private ArrayList<Player> clubList;
    private PlayerDetailsController pd;
    private CurrentPlayers currentPlayers;
    private ArrayList<Player> localBuyList;

    public void setStuff(Client client) throws IOException {
        this.client = client;
        this.currentPlayers = new CurrentPlayers(client.getClubList());
        pd = new PlayerDetailsController();
        clubList = client.getClubList();
        this.localBuyList = client.getMarketList();
        client.setBuyListController(this);

        this.localBuyList = client.getMarketList();
        if (localBuyList == null || localBuyList.isEmpty()) {
            localBuyList = new ArrayList<>();
        }
        populateTable(localBuyList);
    }

    public void initialize() {
        setupTableColumns();
        setupRowInteraction();
        addBuyColumn();
    }

    private void setupTableColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        jerseyColumn.setCellValueFactory(new PropertyValueFactory<>("jersey"));
    }

    private void setupRowInteraction() {
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

    public void populateTable(ArrayList<Player> marketList) {

        if (marketList == null || marketList.isEmpty()) {
            ObservableList<Player> observableMarketList = FXCollections.observableArrayList();
            clientTablePlayers.setItems(observableMarketList);
            clientTablePlayers.setPlaceholder(new Label("No players available to display."));
        } else {
            ObservableList<Player> observableMarketList = FXCollections.observableArrayList(marketList);
            clientTablePlayers.setItems(observableMarketList);
            clientTablePlayers.refresh();
        }
    }

    private void addBuyColumn() {
        TableColumn<Player, Void> buyColumn = new TableColumn<>("Buy");
        buyColumn.setCellFactory(param -> new TableCell<>() {
            private final Button buyButton = new Button("Buy");

            {
                buyButton.setOnAction(event -> handleBuyButtonClick(getIndex()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(buyButton);
                }
            }
        });

        clientTablePlayers.getColumns().add(buyColumn);
    }

    private void handleBuyButtonClick(int index) {
        Player player = clientTablePlayers.getItems().get(index);

        try {

            client.BuyPlayer(client.getClientName(), player);
            localBuyList = client.getMarketList();
            localBuyList.remove(player);
            populateTable(localBuyList);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        player.setClub(client.getClientName());
        client.getClubList().add(player);
    }

    public void backButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ClientMenu.fxml"));
        Parent clientRoot = fxmlLoader.load();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene clientMenuScene = new Scene(clientRoot);
        ClientMenuController clientMenuController = fxmlLoader.getController();
        clientMenuController.setStuff(client, window);
        window.setScene(clientMenuScene);
        window.show();
    }

}
