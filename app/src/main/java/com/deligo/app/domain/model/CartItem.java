package com.deligo.app.domain.model;

public class CartItem {
    private final Food food;
    private final int quantity;

    public CartItem(Food food, int quantity) {
        this.food = food;
        this.quantity = quantity;
    }

    public Food getFood() {
        return food;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return food.getPrice() * quantity;
    }
}
