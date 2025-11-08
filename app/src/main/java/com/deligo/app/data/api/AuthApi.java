package com.deligo.app.data.api;

import com.deligo.app.data.api.dto.AuthRequest;
import com.deligo.app.data.api.dto.AuthResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("api/auth/login")
    Call<AuthResponse> login(@Body AuthRequest request);

    @POST("api/auth/register")
    Call<AuthResponse> register(@Body AuthRequest request);
}
