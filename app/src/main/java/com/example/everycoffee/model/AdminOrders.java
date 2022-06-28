package com.example.everycoffee.model;

import java.util.List;

public class AdminOrders {
    private String username, name, phone, address, city, state, date, time, totalAmount, oid;
    private List<CartModel> childItemList;

    public AdminOrders() { }

    public AdminOrders(String username, String name, String phone, String address, String city, String state, String date, String time, String totalAmount, String oid, List<CartModel> childItemList) {
        this.username = username;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.date = date;
        this.time = time;
        this.totalAmount = totalAmount;
        this.oid = oid;
        this.childItemList = childItemList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public List<CartModel> getChildItemList() {
        return childItemList;
    }

    public void setChildItemList(List<CartModel> childItemList) {
        this.childItemList = childItemList;
    }
}

