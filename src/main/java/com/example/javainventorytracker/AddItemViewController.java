package com.example.javainventorytracker;

import com.example.javainventorytracker.models.Inventory;
import com.example.javainventorytracker.models.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddItemViewController {
    @FXML
    private TextField productPriceInput;
    @FXML
    private TextField productNameInput;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private Inventory inventory;
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void onCancelButtonClick(){
        Stage stage = (Stage) productNameInput.getScene().getWindow();
        stage.close();
    }

    public void onSaveButtonClick(){
        String productPrice = productPriceInput.getText();
        String productName = productNameInput.getText();

        if (productName.isEmpty() || productPrice.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error Saving");
            alert.setContentText("You must fill out both fields before saving.");
            alert.show();

            return;
        }

        try {
            double price = Double.parseDouble(productPrice);
            Product prod = new Product(productName, price);
            inventory.addProduct(prod);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Success");
            alert.setHeaderText("Success!");
            alert.setContentText("Saved successfully.");
            alert.show();

            Stage stage = (Stage) productNameInput.getScene().getWindow();
            stage.close();
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error Saving");
            alert.setContentText("Please check input, must put a price and a name correctly.");
            alert.show();
        }
    }
}
