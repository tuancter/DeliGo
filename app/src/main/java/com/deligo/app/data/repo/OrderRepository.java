package com.deligo.app.data.repo;

import androidx.lifecycle.LiveData;

import com.deligo.app.core.util.Result;
import com.deligo.app.domain.model.Order;

import java.util.List;

public interface OrderRepository {
    LiveData<Result<List<Order>>> observeOrders();

    LiveData<Result<Order>> placeOrder();

    void refreshOrders();
}
