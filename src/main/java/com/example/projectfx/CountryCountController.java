package com.example.projectfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.geometry.Pos;

import java.util.HashMap;

public class CountryCountController {
    @FXML private VBox countryVBox;

    public void setStuff(HashMap<String, Integer> hashCountryCount) {
        countryVBox.getChildren().clear();
        countryVBox.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Country-wise Player Count");
        titleLabel.setFont(Font.font("System", 24));
        titleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
        countryVBox.getChildren().add(titleLabel);

        if (hashCountryCount == null || hashCountryCount.isEmpty()) {
            Label noDataLabel = new Label("No data available.");
            noDataLabel.setStyle("-fx-font-weight: bold; -fx-fill: white;");
            countryVBox.getChildren().add(noDataLabel);
            return;
        }

        hashCountryCount.forEach((country, count) -> {
            TextFlow textFlow = new TextFlow();

            Text countryText = new Text(country.toUpperCase() + ": ");
            countryText.setFont(Font.font("System", 18));
            countryText.setStyle("-fx-font-weight: bold; -fx-fill: white;");

            Text playerText = count > 1 ? new Text(count + " Players") : new Text(count + " Player");
            playerText.setFont(Font.font("System", 16));
            playerText.setStyle("-fx-font-weight: bold; -fx-fill: white;");

            textFlow.getChildren().addAll(countryText, playerText);
            textFlow.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

            countryVBox.getChildren().add(textFlow);
        });
    }
}
