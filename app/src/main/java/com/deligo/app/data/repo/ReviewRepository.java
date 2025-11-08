package com.deligo.app.data.repo;

import androidx.lifecycle.LiveData;

import com.deligo.app.core.util.Result;

public interface ReviewRepository {
    LiveData<Result<Void>> submitReview(long orderId, long foodId, int rating, String content);
}
