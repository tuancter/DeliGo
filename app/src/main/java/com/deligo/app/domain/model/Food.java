package com.deligo.app.domain.model;

public class Food {
    private final long id;
    private final String name;
    private final String description;
    private final String category;
    private final double price;
    private final String imageUrl;
    private final boolean available;

    public Food(long id, String name, String description, String category, double price, String imageUrl, boolean available) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.imageUrl = imageUrl;
        this.available = available;
    }

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

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isAvailable() {
        return available;
    }
}
