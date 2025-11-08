package com.deligo.app.data.repoimpl;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.deligo.app.core.db.AppDatabase;
import com.deligo.app.core.db.dao.FoodDao;
import com.deligo.app.core.db.entity.FoodEntity;
import com.deligo.app.core.util.Result;
import com.deligo.app.data.repo.FoodRepository;
import com.deligo.app.domain.model.Food;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FoodRepositoryImpl implements FoodRepository {
    private final FoodDao foodDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public FoodRepositoryImpl(Context context) {
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
            foodDao.clear();
            foodDao.upsertAll(buildDemoFoods());
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

    private List<FoodEntity> buildDemoFoods() {
        List<FoodEntity> demoFoods = new ArrayList<>();
        demoFoods.add(new FoodEntity(
                1,
                "Bánh mì đặc biệt",
                "Ổ bánh mì giòn rụm với thịt nguội, chả lụa, rau thơm và nước sốt pate",
                "Bánh mì",
                25000,
                "https://images.unsplash.com/photo-1543339308-43e59d6b73a6",
                true
        ));
        demoFoods.add(new FoodEntity(
                2,
                "Phở bò tái",
                "Tô phở bò thơm ngọt nước hầm xương, bánh phở mềm và thịt bò tái",
                "Món chính",
                45000,
                "https://images.unsplash.com/photo-1525755662778-989d0524087e",
                true
        ));
        demoFoods.add(new FoodEntity(
                3,
                "Bún chả Hà Nội",
                "Bún tươi ăn kèm chả nướng, nước mắm chua ngọt và rau sống",
                "Món chính",
                42000,
                "https://images.unsplash.com/photo-1604908177430-0b215cddf1af",
                true
        ));
        demoFoods.add(new FoodEntity(
                4,
                "Gỏi cuốn tôm thịt",
                "Cuốn bánh tráng với tôm, thịt ba chỉ, bún và rau thơm chấm mắm nêm",
                "Khai vị",
                30000,
                "https://images.unsplash.com/photo-1568605114967-8130f3a36994",
                true
        ));
        demoFoods.add(new FoodEntity(
                5,
                "Cơm tấm sườn bì",
                "Đĩa cơm tấm thơm với sườn nướng, bì, trứng ốp la và đồ chua",
                "Món chính",
                38000,
                "https://images.unsplash.com/photo-1604908177522-402e01842488",
                true
        ));
        demoFoods.add(new FoodEntity(
                6,
                "Bánh xèo miền Tây",
                "Bánh xèo vàng giòn nhân tôm thịt, ăn kèm rau sống và nước chấm",
                "Món ăn vặt",
                32000,
                "https://images.unsplash.com/photo-1525755662778-989d0524087e?ixid=mx",
                false
        ));
        demoFoods.add(new FoodEntity(
                7,
                "Chè ba màu",
                "Cốc chè thanh mát với đậu xanh, đậu đỏ, thạch và nước cốt dừa",
                "Tráng miệng",
                22000,
                "https://images.unsplash.com/photo-1589308078059-be1415eab4c3",
                true
        ));
        demoFoods.add(new FoodEntity(
                8,
                "Cà phê sữa đá",
                "Ly cà phê đậm đà kết hợp sữa đặc đá mát lạnh",
                "Đồ uống",
                18000,
                "https://images.unsplash.com/photo-1541167760496-1628856ab772",
                true
        ));
        demoFoods.add(new FoodEntity(
                9,
                "Trà tắc mật ong",
                "Thức uống giải khát với trà thơm, tắc tươi và vị ngọt nhẹ của mật ong",
                "Đồ uống",
                20000,
                "https://images.unsplash.com/photo-1504544750208-dc0358e63f7f",
                true
        ));
        return demoFoods;
    }
}
