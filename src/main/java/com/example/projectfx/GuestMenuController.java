package com.example.projectfx;

import CurrentPlayers.*;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class GuestMenuController {

    @FXML
    private TableColumn<Player, String> nameColumn;
    @FXML
    private TableColumn<Player, String> positionColumn;
    @FXML
    private TableColumn<Player, String> countryColumn;
    @FXML
    private TableColumn<Player, String> clubColumn;
    @FXML
    private Label searchLabel;
    @FXML
    private TextField searchField, searchField2;
    @FXML
    private VBox VboxSearch;
    @FXML
    private HBox HboxTable;
    @FXML
    private TableView tablePlayers;

    private int state = 0;
    private AlertBoxController alertController;
    private PlayerDetailsController pd;
    private CurrentPlayers currentPlayers;

    public void setCurrentPlayers(CurrentPlayers currentPlayers) {
        this.currentPlayers = currentPlayers;
    }

    @FXML
    public void initialize() {
        pd = new PlayerDetailsController();
        setUpRowInteractions();
        setUpTableColumns();
    }

    private void setUpTableColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        clubColumn.setCellValueFactory(new PropertyValueFactory<>("club"));
    }

        private void setUpRowInteractions() {
        tablePlayers.setRowFactory(do_sth -> {
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
        Parent mainMenuParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        Scene mainMenuScene = new Scene(mainMenuParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainMenuScene);
        window.show();
    }

    public void nameButtonClicked(ActionEvent event) {
        state = 1;
        VboxSearch.setVisible(true);
        searchField2.setVisible(false);
        searchLabel.setText("Search by Name");
        searchField.setPromptText("Enter name");
    }

    public void positionButtonClicked(ActionEvent event) {
        state = 2;
        VboxSearch.setVisible(true);
        searchField2.setVisible(false);
        searchLabel.setText("Search by Position");
        searchField.setPromptText("Enter Position");
    }

    public void countryClubButtonClicked(ActionEvent event) {
        state = 3;
        VboxSearch.setVisible(true);
        searchLabel.setText("Search by Country and Club");
        searchField.setPromptText("Enter Country");
        searchField2.setPromptText("Enter Club");
        searchField2.setVisible(true);

    }

    public void salaryRangeButtonClicked(ActionEvent event) {
        state = 4;
        VboxSearch.setVisible(true);
        searchLabel.setText("Search by Salary Range");
        searchField.setPromptText("Enter Upper Range");
        searchField2.setPromptText("Enter Lower Range");
        searchField2.setVisible(true);
    }

    public void countryWiseCountButtonClicked(ActionEvent event) throws IOException {
        pd.showCountryCount(currentPlayers.countryWisePlayerQuery());
    }

    public void guestSearchButtonClicked(ActionEvent event) {
        HboxTable.setVisible(true);
        ObservableList<Player> filteredPlayers = FXCollections.observableArrayList();
        alertController = new AlertBoxController();

        switch (state) {
            case 1:

                String name = searchField.getText().toLowerCase().trim();

                if (name.isBlank()) {
                    alertController.showAlert(Alert.AlertType.WARNING, "Invalid input", "Please enter a name to search by!");
                    searchField.clear();
                    searchField2.clear();
                    return;
                }
                try {
                    filteredPlayers.addAll(currentPlayers.playerNameQuery(name));
                } catch (Exception e) {
                    alertController.showAlert(Alert.AlertType.WARNING, "Query Failed", "No player was found matching the query!");
                }
                searchField.clear();
                searchField2.clear();
                break;

            case 2:
                String position = searchField.getText().toLowerCase().trim();

                if (position.isBlank()) {
                    alertController.showAlert(Alert.AlertType.WARNING, "Invalid input", "Please enter a position to search by!");
                    searchField.clear();
                    searchField2.clear();
                    return;
                }
                try {
                    filteredPlayers.addAll(currentPlayers.positionQuery(position));
                } catch (Exception e) {
                    alertController.showAlert(Alert.AlertType.WARNING, "Query Failed", "No player was found matching the query!");
                }
                searchField.clear();
                searchField2.clear();
                break;

            case 3:
                String club, country;
                try {
                    country = searchField.getText().toLowerCase().trim();
                    club = searchField2.getText().toLowerCase().trim();
                } catch (Exception e) {
                    alertController.showAlert(Alert.AlertType.WARNING, "Mismatched input", "Please enter valid clubs and countries!");
                    searchField.clear();
                    searchField2.clear();
                    return;
                }
                if (country.isBlank() || club.isBlank()) {
                    alertController.showAlert(Alert.AlertType.WARNING, "Invalid input", "Please enter both queries!");
                    return;
                }
                try {
                    filteredPlayers.addAll(currentPlayers.countryClubQuery(country, club));
                } catch (Exception e) {
                    alertController.showAlert(Alert.AlertType.WARNING, "Query Failed", "No player was found matching the query!");
                }
                searchField.clear();
                searchField2.clear();
                break;

            case 4:
                int upper, lower;
                try {
                    upper = Integer.parseInt(searchField.getText().trim());
                    lower = Integer.parseInt(searchField2.getText().trim());
                } catch (Exception e) {
                    alertController.showAlert(Alert.AlertType.WARNING, "Mismatched input", "Please enter numeric values!");
                    searchField.clear();
                    searchField2.clear();
                    return;
                }
                if (searchField.getText().isEmpty() || searchField2.getText().isEmpty()) {
                    alertController.showAlert(Alert.AlertType.WARNING, "Invalid input", "Please enter values to search by!");
                    searchField.clear();
                    searchField2.clear();
                    return;
                } else if (upper < lower) {
                    alertController.showAlert(Alert.AlertType.WARNING, "Invalid input", "Upper limit cannot be less than lower limit!");
                    searchField.clear();
                    searchField2.clear();
                    return;
                }

                try {
                    filteredPlayers.addAll(currentPlayers.salaryQuery(upper, lower));
                } catch (Exception e) {
                    alertController.showAlert(Alert.AlertType.WARNING, "Query Failed", "No player was found matching the query!");
                }
                searchField.clear();
                searchField2.clear();
                break;

            default:
                break;
        }
        tablePlayers.setItems(filteredPlayers);
    }
}
