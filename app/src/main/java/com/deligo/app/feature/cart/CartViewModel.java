package com.deligo.app.feature.cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.deligo.app.core.util.Result;
import com.deligo.app.data.repo.CartRepository;
import com.deligo.app.data.repo.OrderRepository;
import com.deligo.app.domain.model.Cart;
import com.deligo.app.domain.model.Order;

public class CartViewModel extends ViewModel {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final LiveData<Cart> cartLiveData;
    private final MediatorLiveData<Result<Order>> checkoutResult = new MediatorLiveData<>();

    public CartViewModel(CartRepository cartRepository, OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.cartLiveData = cartRepository.observeCart();
    }

    public LiveData<Cart> getCartLiveData() {
        return cartLiveData;
    }

    public LiveData<Double> getTotalLiveData() {
        return Transformations.map(cartLiveData, cart -> cart != null ? cart.getTotal() : 0.0);
    }

    public LiveData<Result<Order>> getCheckoutResult() {
        return checkoutResult;
    }

    public void addToCart(long foodId) {
        cartRepository.addToCart(foodId);
    }

    public void removeFromCart(long foodId) {
        cartRepository.removeFromCart(foodId);
    }

    public void clearCart() {
        cartRepository.clearCart();
    }

    public void checkout() {
        LiveData<Result<Order>> source = orderRepository.placeOrder();
        checkoutResult.setValue(Result.loading(null));
        checkoutResult.addSource(source, result -> {
            checkoutResult.setValue(result);
            if (result.getStatus() != Result.Status.LOADING) {
                checkoutResult.removeSource(source);
            }
        });
    }
}
