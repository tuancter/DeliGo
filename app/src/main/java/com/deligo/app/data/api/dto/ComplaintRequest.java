package com.deligo.app.data.api.dto;

public class ComplaintRequest {
    private final long orderId;
    private final String message;

    public ComplaintRequest(long orderId, String message) {
        this.orderId = orderId;
        this.message = message;
    }

    public long getOrderId() {
        return orderId;
    }

    public String getMessage() {
        return message;
    }
}
