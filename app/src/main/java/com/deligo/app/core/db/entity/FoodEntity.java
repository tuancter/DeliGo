package com.deligo.app.core.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "foods")
public class FoodEntity {
    @PrimaryKey
    public long id;
    public String name;
    public String description;
    @ColumnInfo(name = "category_name")
    public String categoryName;
    public double price;
    @ColumnInfo(name = "image_url")
    public String imageUrl;
    @ColumnInfo(name = "is_available")
    public boolean isAvailable;

    public FoodEntity(long id, String name, String description, String categoryName, double price, String imageUrl, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categoryName = categoryName;
        this.price = price;
        this.imageUrl = imageUrl;
        this.isAvailable = isAvailable;
    }
}
