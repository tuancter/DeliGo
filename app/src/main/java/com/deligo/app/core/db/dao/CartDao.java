package com.deligo.app.core.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.deligo.app.core.db.entity.CartItemEntity;

import java.util.List;

@Dao
public interface CartDao {
    @Query("SELECT * FROM cart_items")
    LiveData<List<CartItemEntity>> observeCart();

    @Query("SELECT * FROM cart_items")
    List<CartItemEntity> getCartItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CartItemEntity item);

    @Update
    void update(CartItemEntity item);

    @Delete
    void delete(CartItemEntity item);

    @Query("DELETE FROM cart_items")
    void clear();

    @Query("SELECT * FROM cart_items WHERE food_id = :foodId LIMIT 1")
    CartItemEntity findByFoodId(long foodId);
}
