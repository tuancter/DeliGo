package com.deligo.app.core.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.deligo.app.core.db.dao.CartDao;
import com.deligo.app.core.db.dao.FoodDao;
import com.deligo.app.core.db.dao.OrderDao;
import com.deligo.app.core.db.entity.CartItemEntity;
import com.deligo.app.core.db.entity.CategoryEntity;
import com.deligo.app.core.db.entity.FoodEntity;
import com.deligo.app.core.db.entity.OrderEntity;

@Database(
        entities = {FoodEntity.class, CategoryEntity.class, CartItemEntity.class, OrderEntity.class},
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract FoodDao foodDao();

    public abstract CartDao cartDao();

    public abstract OrderDao orderDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "deligo.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
