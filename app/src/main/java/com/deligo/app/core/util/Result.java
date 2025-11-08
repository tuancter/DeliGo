package com.deligo.app.core.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Simple result wrapper to unify API and database responses.
 */
public class Result<T> {
    public enum Status { SUCCESS, ERROR, LOADING }

    private final Status status;
    private final T data;
    private final String message;

    private Result(Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Result<T> success(@Nullable T data) {
        return new Result<>(Status.SUCCESS, data, null);
    }

    public static <T> Result<T> error(@NonNull String message, @Nullable T data) {
        return new Result<>(Status.ERROR, data, message);
    }

    public static <T> Result<T> loading(@Nullable T data) {
        return new Result<>(Status.LOADING, data, null);
    }

    public Status getStatus() {
        return status;
    }

    @Nullable
    public T getData() {
        return data;
    }

    @Nullable
    public String getMessage() {
        return message;
    }
}
