package com.deligo.app.data.repoimpl;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.deligo.app.core.db.AppDatabase;
import com.deligo.app.core.db.dao.CartDao;
import com.deligo.app.core.db.dao.FoodDao;
import com.deligo.app.core.db.entity.CartItemEntity;
import com.deligo.app.core.db.entity.FoodEntity;
import com.deligo.app.data.repo.CartRepository;
import com.deligo.app.domain.model.Cart;
import com.deligo.app.domain.model.CartItem;
import com.deligo.app.domain.model.Food;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CartRepositoryImpl implements CartRepository {
    private final CartDao cartDao;
    private final FoodDao foodDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final MediatorLiveData<Cart> cartLiveData = new MediatorLiveData<>();

    public CartRepositoryImpl(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.cartDao = db.cartDao();
        this.foodDao = db.foodDao();
        cartLiveData.setValue(new Cart());
        cartLiveData.addSource(cartDao.observeCart(), cartItems -> executor.execute(() -> cartLiveData.postValue(mapCart(cartItems))));
    }

    @Override
    public LiveData<Cart> observeCart() {
        return cartLiveData;
    }

    @Override
    public void addToCart(long foodId) {
        executor.execute(() -> {
            CartItemEntity existing = cartDao.findByFoodId(foodId);
            if (existing == null) {
                cartDao.insert(new CartItemEntity(foodId, 1));
            } else {
                existing.quantity += 1;
                cartDao.update(existing);
            }
        });
    }

    @Override
    public void removeFromCart(long foodId) {
        executor.execute(() -> {
            CartItemEntity existing = cartDao.findByFoodId(foodId);
            if (existing == null) {
                return;
            }
            if (existing.quantity <= 1) {
                cartDao.delete(existing);
            } else {
                existing.quantity -= 1;
                cartDao.update(existing);
            }
        });
    }

    @Override
    public void clearCart() {
        executor.execute(cartDao::clear);
    }

    private Cart mapCart(List<CartItemEntity> entities) {
        List<CartItem> items = new ArrayList<>();
        if (entities != null) {
            for (CartItemEntity entity : entities) {
                FoodEntity foodEntity = foodDao.findById(entity.foodId);
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
                    items.add(new CartItem(food, entity.quantity));
                }
            }
        }
        return new Cart(items);
    }
}
