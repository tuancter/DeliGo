package com.deligo.app.data.repoimpl;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.deligo.app.core.db.AppDatabase;
import com.deligo.app.core.db.dao.CartDao;
import com.deligo.app.core.db.dao.FoodDao;
import com.deligo.app.core.db.dao.OrderDao;
import com.deligo.app.core.db.entity.CartItemEntity;
import com.deligo.app.core.db.entity.FoodEntity;
import com.deligo.app.core.db.entity.OrderEntity;
import com.deligo.app.core.util.Result;
import com.deligo.app.data.api.OrderApi;
import com.deligo.app.data.api.dto.OrderRequest;
import com.deligo.app.data.api.dto.OrderResponse;
import com.deligo.app.data.repo.OrderRepository;
import com.deligo.app.domain.model.CartItem;
import com.deligo.app.domain.model.Food;
import com.deligo.app.domain.model.Order;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Response;

public class OrderRepositoryImpl implements OrderRepository {
    private final OrderApi orderApi;
    private final OrderDao orderDao;
    private final CartDao cartDao;
    private final FoodDao foodDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public OrderRepositoryImpl(Context context, OrderApi orderApi) {
        AppDatabase database = AppDatabase.getInstance(context);
        this.orderApi = orderApi;
        this.orderDao = database.orderDao();
        this.cartDao = database.cartDao();
        this.foodDao = database.foodDao();
    }

    @Override
    public LiveData<Result<List<Order>>> observeOrders() {
        MediatorLiveData<Result<List<Order>>> liveData = new MediatorLiveData<>();
        liveData.setValue(Result.loading(new ArrayList<>()));
        liveData.addSource(orderDao.observeOrders(), orderEntities -> liveData.setValue(Result.success(mapOrders(orderEntities))));
        return liveData;
    }

    @Override
    public LiveData<Result<Order>> placeOrder() {
        MutableLiveData<Result<Order>> liveData = new MutableLiveData<>();
        liveData.setValue(Result.loading(null));
        executor.execute(() -> {
            List<CartItemEntity> cartItems = cartDao.getCartItems();
            if (cartItems == null || cartItems.isEmpty()) {
                liveData.postValue(Result.error("Giỏ hàng trống", null));
                return;
            }
            List<OrderRequest.OrderRequestItem> requestItems = new ArrayList<>();
            for (CartItemEntity item : cartItems) {
                requestItems.add(new OrderRequest.OrderRequestItem(item.foodId, item.quantity));
            }
            try {
                Response<OrderResponse> response = orderApi.createOrder(new OrderRequest(requestItems)).execute();
                if (response.isSuccessful() && response.body() != null) {
                    OrderResponse body = response.body();
                    Order order = new Order(
                            body.getId(),
                            mapOrderItems(body.getItems()),
                            body.getStatus(),
                            body.getTotal(),
                            body.getCreatedAt()
                    );
                    orderDao.insert(new OrderEntity(order.getId(), order.getTotal(), order.getStatus(), order.getCreatedAt()));
                    cartDao.clear();
                    liveData.postValue(Result.success(order));
                } else {
                    liveData.postValue(Result.error("Đặt hàng thất bại", null));
                }
            } catch (IOException e) {
                liveData.postValue(Result.error(e.getMessage(), null));
            }
        });
        return liveData;
    }

    @Override
    public void refreshOrders() {
        executor.execute(() -> {
            try {
                Response<List<OrderResponse>> response = orderApi.getOrders().execute();
                if (response.isSuccessful() && response.body() != null) {
                    orderDao.clear();
                    for (OrderResponse item : response.body()) {
                        orderDao.insert(new OrderEntity(item.getId(), item.getTotal(), item.getStatus(), item.getCreatedAt()));
                    }
                }
            } catch (IOException e) {
                // ignore network error, keep cached data
            }
        });
    }

    private List<Order> mapOrders(List<OrderEntity> entities) {
        List<Order> orders = new ArrayList<>();
        if (entities != null) {
            for (OrderEntity entity : entities) {
                orders.add(new Order(entity.id, new ArrayList<>(), entity.status, entity.total, entity.createdAt));
            }
        }
        return orders;
    }

    private List<CartItem> mapOrderItems(List<OrderResponse.OrderItemResponse> responses) {
        List<CartItem> items = new ArrayList<>();
        if (responses != null) {
            for (OrderResponse.OrderItemResponse response : responses) {
                FoodEntity foodEntity = foodDao.findById(response.getFoodId());
                if (foodEntity != null) {
                    Food food = new Food(
                            foodEntity.id,
                            foodEntity.name,
                            foodEntity.description,
                            foodEntity.categoryName,
                            foodEntity.price,
                            foodEntity.imageUrl,
                            foodEntity.isAvailable
                    );
                    items.add(new CartItem(food, response.getQuantity()));
                }
            }
        }
        return items;
    }
}
