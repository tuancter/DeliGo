package com.deligo.app.data.repoimpl;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.deligo.app.core.db.AppDatabase;
import com.deligo.app.core.db.dao.FoodDao;
import com.deligo.app.core.db.entity.FoodEntity;
import com.deligo.app.core.util.Result;
import com.deligo.app.data.api.FoodApi;
import com.deligo.app.data.api.dto.FoodResponse;
import com.deligo.app.data.repo.FoodRepository;
import com.deligo.app.domain.model.Food;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Response;

public class FoodRepositoryImpl implements FoodRepository {
    private final FoodApi foodApi;
    private final FoodDao foodDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public FoodRepositoryImpl(Context context, FoodApi foodApi) {
        this.foodApi = foodApi;
        this.foodDao = AppDatabase.getInstance(context).foodDao();
    }

    @Override
    public LiveData<Result<List<Food>>> observeFoods() {
        MediatorLiveData<Result<List<Food>>> liveData = new MediatorLiveData<>();
        liveData.setValue(Result.loading(new ArrayList<>()));
        liveData.addSource(foodDao.observeFoods(), entities -> liveData.setValue(Result.success(mapEntities(entities))));
        return liveData;
    }

    @Override
    public void refreshFoods() {
        executor.execute(() -> {
            try {
                Response<List<FoodResponse>> response = foodApi.getFoods().execute();
                if (response.isSuccessful() && response.body() != null) {
                    List<FoodEntity> entities = new ArrayList<>();
                    for (FoodResponse item : response.body()) {
                        entities.add(new FoodEntity(
                                item.getId(),
                                item.getName(),
                                item.getDescription(),
                                item.getCategory(),
                                item.getPrice(),
                                item.getImage(),
                                item.isAvailable()
                        ));
                    }
                    foodDao.upsertAll(entities);
                }
            } catch (IOException e) {
                // ignore for now, UI will show cached data
            }
        });
    }

    private List<Food> mapEntities(List<FoodEntity> entities) {
        List<Food> foods = new ArrayList<>();
        if (entities == null) {
            return foods;
        }
        for (FoodEntity entity : entities) {
            foods.add(new Food(
                    entity.id,
                    entity.name,
                    entity.description,
                    entity.categoryName,
                    entity.price,
                    entity.imageUrl,
                    entity.isAvailable
            ));
        }
        return foods;
    }
}
