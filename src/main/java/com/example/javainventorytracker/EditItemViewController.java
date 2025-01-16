package com.example.javainventorytracker;

import com.example.javainventorytracker.models.Inventory;
import com.example.javainventorytracker.models.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditItemViewController {
    @FXML
    private TextField productPriceInput;
    @FXML
    private TextField productNameInput;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label pageHeaderText;

    private Product selectedProduct;
    private Inventory inventory;

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void populateFields() {
        if (selectedProduct != null) {
            pageHeaderText.setText("Edit Item - " + selectedProduct.getName());
            productNameInput.setText(selectedProduct.getName());
            productPriceInput.setText(String.valueOf(selectedProduct.getPrice()));
        }
    }

    public void onSaveButtonClick(){
        try {
            String name = productNameInput.getText();
            double price = Double.parseDouble(productPriceInput.getText());

            selectedProduct.setName(name);
            selectedProduct.setPrice(price);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Success!");
            alert.setTitle("Success!");
            alert.setContentText(selectedProduct.getName() + " saved successfully.");

            alert.show();

            Stage stage = (Stage) productNameInput.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error!");
            alert.setTitle("Error Saving");
            alert.setContentText("Could not save " + selectedProduct.getName() + ". Please check to make sure information is added correctly.");

            alert.show();
        }
    }

    public void onCancelButtonClick(){
        Stage stage = (Stage) productNameInput.getScene().getWindow();
        stage.close();
    }
}
