package com.deligo.app.domain.model;

import java.util.List;

public class Order {
    private final long id;
    private final List<CartItem> items;
    private final String status;
    private final double total;
    private final long createdAt;

    public Order(long id, List<CartItem> items, String status, double total, long createdAt) {
        this.id = id;
        this.items = items;
        this.status = status;
        this.total = total;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public String getStatus() {
        return status;
    }

    public double getTotal() {
        return total;
    }

    public long getCreatedAt() {
        return createdAt;
    }
}
