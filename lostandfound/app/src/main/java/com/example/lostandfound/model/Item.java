package com.example.lostandfound.model;

public class Item {
    private int item_id;
    private String type;
    private String item_name;
    private String phone;
    private String description;
    private String date;
    private String location;

    public Item(String type, String item_name, String phone, String description, String date, String location) {
        this.type = type;
        this.item_name = item_name;
        this.phone = phone;
        this.description = description;
        this.date = date;
        this.location = location;
    }

    public Item() {
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
