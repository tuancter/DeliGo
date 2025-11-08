package com.deligo.app.data.api;

import com.deligo.app.data.api.dto.FoodResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FoodApi {
    @GET("api/foods")
    Call<List<FoodResponse>> getFoods();
}
