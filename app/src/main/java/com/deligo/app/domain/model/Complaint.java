package com.deligo.app.domain.model;

public class Complaint {
    private final long orderId;
    private final String message;

    public Complaint(long orderId, String message) {
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
