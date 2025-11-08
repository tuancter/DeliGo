package com.deligo.app.data.api;

import com.deligo.app.data.api.dto.ReviewRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ReviewApi {
    @POST("api/reviews")
    Call<Void> submitReview(@Body ReviewRequest request);
}
