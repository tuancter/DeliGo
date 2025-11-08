package com.deligo.app.data.api.dto;

public class ReviewRequest {
    private final long orderId;
    private final long foodId;
    private final int rating;
    private final String content;

    public ReviewRequest(long orderId, long foodId, int rating, String content) {
        this.orderId = orderId;
        this.foodId = foodId;
        this.rating = rating;
        this.content = content;
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

    public String getContent() {
        return content;
    }
}
