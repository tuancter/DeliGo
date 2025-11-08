package com.deligo.app.feature.review;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.deligo.app.core.util.Result;
import com.deligo.app.data.repo.ReviewRepository;

public class ReviewViewModel extends ViewModel {
    private final ReviewRepository reviewRepository;

    public ReviewViewModel(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public LiveData<Result<Void>> submitReview(long orderId, long foodId, int rating, String content) {
        return reviewRepository.submitReview(orderId, foodId, rating, content);
    }
}
