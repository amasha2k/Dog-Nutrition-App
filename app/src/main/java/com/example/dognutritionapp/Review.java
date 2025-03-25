package com.example.dognutritionapp;

public class Review {
    private int id;
    private String customerEmail;
    private String name;
    private String comment;

    // Constructors, getters, and setters

    public Review() {}

    public Review(int id, String customerEmail, String name, String comment) {
        this.id = id;
        this.customerEmail = customerEmail;
        this.name = name;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
