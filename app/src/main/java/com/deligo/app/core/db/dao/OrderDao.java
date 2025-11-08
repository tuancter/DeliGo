package com.deligo.app.core.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.deligo.app.core.db.entity.OrderEntity;

import java.util.List;

@Dao
public interface OrderDao {
    @Query("SELECT * FROM orders ORDER BY created_at DESC")
    LiveData<List<OrderEntity>> observeOrders();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(OrderEntity order);

    @Query("DELETE FROM orders")
    void clear();
}
