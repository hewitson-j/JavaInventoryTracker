package com.example.javainventorytracker.models;

public class Product {
    private int id;
    private static int counter = 1;
    private String name;
    private int stock;
    private double price;

    public Product(String name, double price){
        this.name = name;
        this.price = price;
        this.id = counter;
        counter++;
        this.stock = 0;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }
}
