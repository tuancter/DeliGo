package com.deligo.app.data.repo;

import androidx.lifecycle.LiveData;

import com.deligo.app.core.util.Result;
import com.deligo.app.domain.model.Food;

import java.util.List;

public interface FoodRepository {
    LiveData<Result<List<Food>>> observeFoods();

    void refreshFoods();
}
