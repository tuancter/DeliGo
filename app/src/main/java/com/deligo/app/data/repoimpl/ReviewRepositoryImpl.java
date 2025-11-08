package com.deligo.app.data.repoimpl;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.deligo.app.core.util.Result;
import com.deligo.app.data.api.ReviewApi;
import com.deligo.app.data.api.dto.ReviewRequest;
import com.deligo.app.data.repo.ReviewRepository;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReviewRepositoryImpl implements ReviewRepository {
    private final ReviewApi reviewApi;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public ReviewRepositoryImpl(ReviewApi reviewApi) {
        this.reviewApi = reviewApi;
    }

    @Override
    public LiveData<Result<Void>> submitReview(long orderId, long foodId, int rating, String content) {
        MutableLiveData<Result<Void>> liveData = new MutableLiveData<>();
        liveData.setValue(Result.loading(null));
        executor.execute(() -> {
            try {
                reviewApi.submitReview(new ReviewRequest(orderId, foodId, rating, content)).execute();
                liveData.postValue(Result.success(null));
            } catch (IOException e) {
                liveData.postValue(Result.error(e.getMessage(), null));
            }
        });
        return liveData;
    }
}
