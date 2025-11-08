package com.deligo.app.core.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_items")
public class CartItemEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;
    @ColumnInfo(name = "food_id")
    public long foodId;
    public int quantity;

    public CartItemEntity(long foodId, int quantity) {
        this.foodId = foodId;
        this.quantity = quantity;
    }
}
