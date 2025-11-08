package com.deligo.app.data.api;

import com.deligo.app.data.api.dto.OrderRequest;
import com.deligo.app.data.api.dto.OrderResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrderApi {
    @POST("api/orders")
    Call<OrderResponse> createOrder(@Body OrderRequest request);

    @GET("api/orders")
    Call<List<OrderResponse>> getOrders();

    @GET("api/orders/{id}")
    Call<OrderResponse> getOrderDetail(@Path("id") long orderId);

    @GET("api/orders/{id}/status")
    Call<OrderResponse> getOrderStatus(@Path("id") long orderId);
}
