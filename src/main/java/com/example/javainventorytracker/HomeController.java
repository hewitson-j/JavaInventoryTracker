package com.example.javainventorytracker;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private void onAddProductButtonClick() throws IOException {
        // Load the Add Item FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-item-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 725);

        // Get the current stage (window)
        Stage stage = (Stage) welcomeText.getScene().getWindow(); // Assuming welcomeText is part of the current scene

        // Set the new scene
        stage.setScene(scene);
        stage.setTitle("Add Item");
    }
}