package com.deligo.app.core.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "orders")
public class OrderEntity {
    @PrimaryKey
    public long id;
    public double total;
    public String status;
    @ColumnInfo(name = "created_at")
    public long createdAt;

    public OrderEntity(long id, double total, String status, long createdAt) {
        this.id = id;
        this.total = total;
        this.status = status;
        this.createdAt = createdAt;
    }
}
