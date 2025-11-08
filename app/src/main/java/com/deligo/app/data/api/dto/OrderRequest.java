package com.deligo.app.data.api.dto;

import java.util.List;

public class OrderRequest {
    private final List<OrderRequestItem> items;

    public OrderRequest(List<OrderRequestItem> items) {
        this.items = items;
    }

    public List<OrderRequestItem> getItems() {
        return items;
    }

    public static class OrderRequestItem {
        private final long foodId;
        private final int quantity;

        public OrderRequestItem(long foodId, int quantity) {
            this.foodId = foodId;
            this.quantity = quantity;
        }

        public long getFoodId() {
            return foodId;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}
