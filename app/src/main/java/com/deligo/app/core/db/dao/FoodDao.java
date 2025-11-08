package com.deligo.app.core.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.deligo.app.core.db.entity.FoodEntity;

import java.util.List;

@Dao
public interface FoodDao {
    @Query("SELECT * FROM foods")
    LiveData<List<FoodEntity>> observeFoods();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upsertAll(List<FoodEntity> foods);

    @Query("DELETE FROM foods")
    void clear();

    @Query("SELECT * FROM foods WHERE id = :foodId LIMIT 1")
    FoodEntity findById(long foodId);
}
