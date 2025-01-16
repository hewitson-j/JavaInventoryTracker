package com.example.javainventorytracker;

import com.example.javainventorytracker.models.Product;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AdjustStockController {
    @FXML
    private Spinner<Integer> stockSpinner;

    private Product selectedProduct;
    private int newStock;

    // Setter for selectedProduct
    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;

        // If the spinner is already loaded, initialize it
        if (stockSpinner != null) {
            initializeSpinner();
        }
    }

    public void alertAndClose(Alert.AlertType alertType, String title, String headerText, String contentText){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.setHeaderText(headerText);

        alert.show();

        Stage stage = (Stage) stockSpinner.getScene().getWindow();
        stage.close();
    }

    public void quickAdd(){
        selectedProduct.setStock(selectedProduct.getStock() + 1);
        alertAndClose(Alert.AlertType.INFORMATION, "Success!", "Quick Add Success", "Successfully added one item to stock.");
    }

    public void quickRemove(){
        int stock = selectedProduct.getStock();

        if (stock == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Cannot remove if stock is already at 0.");
            alert.setHeaderText("Quick Remove Error");
            alert.show();

            return;
        }

        selectedProduct.setStock(stock - 1);
        alertAndClose(Alert.AlertType.INFORMATION, "Success!", "Quick Remove Success", "Successfully removed one item from stock.");
    }

    public void onSaveButtonClick(){
        if (this.newStock < 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Cannot save if stock is less than 0.");
            alert.setHeaderText("Save Error");
            alert.show();

            return;
        }

        selectedProduct.setStock(this.newStock);
        alertAndClose(Alert.AlertType.INFORMATION, "Success!", "Adjust Stock Success", "Save successful.");
    }

    public void onCancelButtonClick(){
        Stage stage = (Stage) stockSpinner.getScene().getWindow();
        stage.close();
    }

    public void setNewStock(int newStock) {
        this.newStock = newStock;
    }

    private void initializeSpinner() {
        if (selectedProduct == null) {
            throw new IllegalStateException("Selected product must be set before initializing the spinner");
        }

        int stock = selectedProduct.getStock();

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0, Integer.MAX_VALUE, stock, 1
        );
        stockSpinner.setValueFactory(valueFactory);

        stockSpinner.setEditable(true);

        TextField spinnerTextField = stockSpinner.getEditor();

        // Add TextFormatter to restrict input to digits
        spinnerTextField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) { // Accept digits only
                return change;
            }
            return null; // Reject the change
        }));

        // Add a focus listener to sanitize input when losing focus
        spinnerTextField.focusedProperty().addListener((_, _, isNowFocused) -> {
            if (!isNowFocused) {
                try {
                    int value = Integer.parseInt(spinnerTextField.getText());
                    stockSpinner.getValueFactory().setValue(value);
                } catch (NumberFormatException e) {
                    spinnerTextField.setText(String.valueOf(stockSpinner.getValue()));
                }
            }
        });

        // Fix caret position after each text update
        spinnerTextField.textProperty().addListener((_, _, newText) -> {
            javafx.application.Platform.runLater(() -> spinnerTextField.positionCaret(newText.length()));
        });

        // Add a listener to update newStock whenever the value changes
        stockSpinner.valueProperty().addListener((obs, _, newValue) -> {
            setNewStock(newValue);
        });
    }



}
