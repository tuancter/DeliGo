package com.deligo.app.data.api.dto;

public class FoodResponse {
    private long id;
    private String name;
    private String description;
    private String category;
    private double price;
    private String image;
    private boolean available;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public boolean isAvailable() {
        return available;
    }
}
