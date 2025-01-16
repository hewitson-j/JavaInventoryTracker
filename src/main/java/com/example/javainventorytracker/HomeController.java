package com.example.javainventorytracker;

import com.example.javainventorytracker.models.Inventory;
import com.example.javainventorytracker.models.Product;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {
    @FXML
    private Label inventoryText;
    @FXML
    private TableView<Product> inventoryTable;
    @FXML
    private TableColumn<Product, Integer> inventoryTableId;
    @FXML
    private TableColumn<Product, String> inventoryTableName;
    @FXML
    private TableColumn<Product, Double> inventoryTablePrice;
    @FXML
    private TableColumn<Product, Integer> inventoryTableStock;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editProductButton;
    @FXML
    private Button adjustStockButton;

    private final Inventory inventory = new Inventory();
    private Product selectedProduct = null;

    @FXML
    public void initialize() {
        inventory.addProduct(new Product("Bike", 124.50));
        inventory.addProduct(new Product("Car", 1000.00));
        inventory.addProduct(new Product("Robot", 500.25));

        inventoryTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        inventoryTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryTablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventoryTableStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        inventoryTable.setItems(inventory.getProductList());

        // Bind the disable property of the delete button to the selection
        deleteButton.disableProperty().bind(
                Bindings.isNull(inventoryTable.getSelectionModel().selectedItemProperty())
        );
        editProductButton.disableProperty().bind(
                Bindings.isNull(inventoryTable.getSelectionModel().selectedItemProperty())
        );
        adjustStockButton.disableProperty().bind(
                Bindings.isNull(inventoryTable.getSelectionModel().selectedItemProperty())
        );

        inventoryTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Product>() {
            @Override
            public void changed(ObservableValue<? extends Product> observable, Product oldValue, Product newValue) {
                selectedProduct = newValue;
            }
        });
    }

    @FXML
    private void onAddProductButtonClick() throws IOException {
        // Load the FXML for the new window
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-item-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 650);

        AddItemViewController controller = fxmlLoader.getController();
        controller.setInventory(inventory);

        // Create a new stage
        Stage addItemStage = new Stage();
        addItemStage.setTitle("Inventory Tracker - Add");
        addItemStage.setScene(scene);

        // Show the new stage
        addItemStage.show();
    }

    @FXML
    private void onEditProductButtonClick() throws IOException {
        // Load the FXML for the new window
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("edit-item-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 650);

        // Get the controller created by the FXMLLoader
        EditItemViewController controller = fxmlLoader.getController();

        // Set the selectedProduct and inventory on the controller
        controller.setInventory(inventory);
        controller.setSelectedProduct(selectedProduct);

        // Create a new stage
        Stage editItemStage = new Stage();
        editItemStage.setTitle("Inventory Tracker - Edit Product");
        editItemStage.setScene(scene);

        // Populate the fields after setting the dependencies
        controller.populateFields();

        // Show the new stage
        editItemStage.setOnHiding(event -> inventoryTable.refresh());
        editItemStage.show();
    }


    @FXML
    private void onDeleteButtonClick(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Delete Item?");
        alert.setTitle("Delete?");
        alert.setContentText("Are you sure you want to delete this item?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK){
                inventory.removeProduct(selectedProduct.getId());

                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Item Deleted");
                success.setHeaderText("Delete Successful");
                success.setContentText("Item deleted successfully.");
                success.show();
            }
        });
    }

    @FXML
    private void onExitButtonClick() throws IOException {
        Stage stage = (Stage) inventoryText.getScene().getWindow();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Inventory Tracker - Closed");
        alert.setHeaderText("Goodbye!");
        alert.setContentText("Thanks for using the Inventory Tracker.");
        alert.show();

        stage.close();
    }
}