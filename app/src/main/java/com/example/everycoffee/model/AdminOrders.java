package com.example.everycoffee.model;

import java.util.List;

public class AdminOrders {
    private String username, name, phone, address, state, date, time, totalAmount, oid;


    public AdminOrders() { }

    public AdminOrders(String username, String name, String phone, String address, String state, String date, String time, String totalAmount, String oid) {
        this.username = username;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.date = date;
        this.time = time;
        this.totalAmount = totalAmount;
        this.oid = oid;
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

}

