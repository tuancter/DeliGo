package com.deligo.app.core.util;

import android.content.Context;

import com.deligo.app.core.network.ApiClient;
import com.deligo.app.data.api.ComplaintApi;
import com.deligo.app.data.api.OrderApi;
import com.deligo.app.data.api.ReviewApi;
import com.deligo.app.data.repo.AuthRepository;
import com.deligo.app.data.repo.CartRepository;
import com.deligo.app.data.repo.ComplaintRepository;
import com.deligo.app.data.repo.FoodRepository;
import com.deligo.app.data.repo.OrderRepository;
import com.deligo.app.data.repo.ReviewRepository;
import com.deligo.app.data.repoimpl.AuthRepositoryImpl;
import com.deligo.app.data.repoimpl.CartRepositoryImpl;
import com.deligo.app.data.repoimpl.ComplaintRepositoryImpl;
import com.deligo.app.data.repoimpl.FoodRepositoryImpl;
import com.deligo.app.data.repoimpl.OrderRepositoryImpl;
import com.deligo.app.data.repoimpl.ReviewRepositoryImpl;

public class AppServiceLocator {
    private static volatile AppServiceLocator INSTANCE;

    private final AuthRepository authRepository;
    private final FoodRepository foodRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;
    private final ComplaintRepository complaintRepository;

    private AppServiceLocator(Context context) {
        Context appContext = context.getApplicationContext();
        ApiClient apiClient = new ApiClient(appContext);
        FoodApi foodApi = apiClient.create(FoodApi.class);
        OrderApi orderApi = apiClient.create(OrderApi.class);
        ReviewApi reviewApi = apiClient.create(ReviewApi.class);
        ComplaintApi complaintApi = apiClient.create(ComplaintApi.class);

        authRepository = new AuthRepositoryImpl(appContext);
        foodRepository = new FoodRepositoryImpl(appContext, foodApi);
        cartRepository = new CartRepositoryImpl(appContext);
        orderRepository = new OrderRepositoryImpl(appContext, orderApi);
        reviewRepository = new ReviewRepositoryImpl(reviewApi);
        complaintRepository = new ComplaintRepositoryImpl(complaintApi);
    }

    public static AppServiceLocator getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppServiceLocator.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppServiceLocator(context);
                }
            }
        }
        return INSTANCE;
    }

    public AuthRepository getAuthRepository() {
        return authRepository;
    }

    public FoodRepository getFoodRepository() {
        return foodRepository;
    }

    public CartRepository getCartRepository() {
        return cartRepository;
    }

    public OrderRepository getOrderRepository() {
        return orderRepository;
    }

    public ReviewRepository getReviewRepository() {
        return reviewRepository;
    }

    public ComplaintRepository getComplaintRepository() {
        return complaintRepository;
    }
}
