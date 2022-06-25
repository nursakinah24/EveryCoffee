package com.example.everycoffee.model;

public class CartModel {
    private String pid, productName, price, quantity;

    public CartModel() {
    }

    public CartModel(String pid, String productName, String price, String quantity) {
        this.pid = pid;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

}
