package com.example.javainventorytracker.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private ObservableList<Product> productList;

    public Inventory(){
        this.productList = FXCollections.observableArrayList();
    }

    public ObservableList<Product> getProductList() {
        return productList;
    }

    public void addProduct(Product product){
        this.productList.add(product);
    }

    public boolean removeProduct(int id){
        Product prodToRemove = null;
        for (Product prod : productList){
            if (prod.getId() == id){
                prodToRemove = prod;
                break;
            }
        }

        if (prodToRemove != null){
            productList.remove(prodToRemove);
            return true;
        }

        return false;
    }
}
