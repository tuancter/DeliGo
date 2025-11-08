package com.deligo.app.data.repo;

import androidx.lifecycle.LiveData;

import com.deligo.app.domain.model.Cart;

public interface CartRepository {
    LiveData<Cart> observeCart();

    void addToCart(long foodId);

    void removeFromCart(long foodId);

    void clearCart();
}
