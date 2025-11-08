package com.deligo.app.domain.model;

public class Review {
    private final long orderId;
    private final long foodId;
    private final int rating;
    private final String comment;

    public Review(long orderId, long foodId, int rating, String comment) {
        this.orderId = orderId;
        this.foodId = foodId;
        this.rating = rating;
        this.comment = comment;
    }

    public long getOrderId() {
        return orderId;
    }

    public long getFoodId() {
        return foodId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}
