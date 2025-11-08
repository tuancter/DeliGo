package com.deligo.app.data.api.dto;

import java.util.List;

public class OrderResponse {
    private long id;
    private String status;
    private double total;
    private long createdAt;
    private List<OrderItemResponse> items;

    public long getId() {
        return id;
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

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public static class OrderItemResponse {
        private long foodId;
        private int quantity;

        public long getFoodId() {
            return foodId;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}
