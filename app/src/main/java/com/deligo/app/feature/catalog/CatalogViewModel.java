package com.deligo.app.feature.catalog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.deligo.app.core.util.Result;
import com.deligo.app.data.repo.CartRepository;
import com.deligo.app.data.repo.FoodRepository;
import com.deligo.app.domain.model.Food;

import java.util.ArrayList;
import java.util.List;

public class CatalogViewModel extends ViewModel {
    private final FoodRepository foodRepository;
    private final CartRepository cartRepository;
    private final LiveData<Result<List<Food>>> foods;
    private final MutableLiveData<String> searchQuery = new MutableLiveData<>("");
    private final MediatorLiveData<List<Food>> filteredFoods = new MediatorLiveData<>();
    private List<Food> currentFoods = new ArrayList<>();

    public CatalogViewModel(FoodRepository foodRepository, CartRepository cartRepository) {
        this.foodRepository = foodRepository;
        this.cartRepository = cartRepository;
        this.foods = foodRepository.observeFoods();
        filteredFoods.setValue(new ArrayList<>());
        filteredFoods.addSource(Transformations.map(foods, Result::getData), list -> {
            currentFoods = list != null ? list : new ArrayList<>();
            applyFilter(currentFoods, searchQuery.getValue());
        });
        filteredFoods.addSource(searchQuery, query -> applyFilter(currentFoods, query));
    }

    public LiveData<Result<List<Food>>> getFoods() {
        return foods;
    }

    public LiveData<List<Food>> getFilteredFoods() {
        return filteredFoods;
    }

    public void refreshFoods() {
        foodRepository.refreshFoods();
    }

    public void addToCart(long foodId) {
        cartRepository.addToCart(foodId);
    }

    public void updateSearchQuery(String query) {
        searchQuery.setValue(query);
    }

    private void applyFilter(List<Food> foodList, String query) {
        if (foodList == null) {
            filteredFoods.setValue(new ArrayList<>());
            return;
        }
        String safeQuery = query == null ? "" : query.toLowerCase();
        List<Food> result = new ArrayList<>();
        for (Food food : foodList) {
            if (food.getName().toLowerCase().contains(safeQuery) || food.getCategory().toLowerCase().contains(safeQuery)) {
                result.add(food);
            }
        }
        filteredFoods.setValue(result);
    }
}
