package com.deligo.app.data.api.dto;

public class AuthResponse {
    private long userId;
    private String name;
    private String email;
    private String role;
    private String token;

    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getToken() {
        return token;
    }
}
