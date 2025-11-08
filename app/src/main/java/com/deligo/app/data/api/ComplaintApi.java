package com.deligo.app.data.api;

import com.deligo.app.data.api.dto.ComplaintRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ComplaintApi {
    @POST("api/complaints")
    Call<Void> submitComplaint(@Body ComplaintRequest request);
}
