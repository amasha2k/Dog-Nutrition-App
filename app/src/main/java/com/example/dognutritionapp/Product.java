package com.example.dognutritionapp;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private byte[] image;
    private String category;

    public Product(int id, String name, String description, double price, byte[] image, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public byte[] getImage() { return image; }
    public String getCategory() { return category; }

    public void setId(int anInt) {
    }

    public void setName(String string) {
    }

    public void setDescription(String string) {
    }

    public void setPrice(double aDouble) {
    }

    public void setCategory(String string) {
    }

    public void setImage(byte[] blob) {
    }
}
