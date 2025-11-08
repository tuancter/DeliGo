package com.deligo.app.feature.order;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.deligo.app.core.util.Result;
import com.deligo.app.data.repo.OrderRepository;
import com.deligo.app.domain.model.Order;

import java.util.List;

public class OrdersViewModel extends ViewModel {
    private final OrderRepository orderRepository;

    public OrdersViewModel(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public LiveData<Result<List<Order>>> getOrders() {
        return orderRepository.observeOrders();
    }

    public void refreshOrders() {
        orderRepository.refreshOrders();
    }
}
