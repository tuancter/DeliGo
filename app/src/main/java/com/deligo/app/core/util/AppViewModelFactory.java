package com.deligo.app.core.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.deligo.app.feature.auth.AuthViewModel;
import com.deligo.app.feature.cart.CartViewModel;
import com.deligo.app.feature.catalog.CatalogViewModel;
import com.deligo.app.feature.complaint.ComplaintViewModel;
import com.deligo.app.feature.order.OrdersViewModel;
import com.deligo.app.feature.profile.ProfileViewModel;
import com.deligo.app.feature.review.ReviewViewModel;

public class AppViewModelFactory implements ViewModelProvider.Factory {
    private final AppServiceLocator locator;

    public AppViewModelFactory(Context context) {
        locator = AppServiceLocator.getInstance(context);
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AuthViewModel.class)) {
            return (T) new AuthViewModel(locator.getAuthRepository());
        } else if (modelClass.isAssignableFrom(CatalogViewModel.class)) {
            return (T) new CatalogViewModel(locator.getFoodRepository(), locator.getCartRepository());
        } else if (modelClass.isAssignableFrom(CartViewModel.class)) {
            return (T) new CartViewModel(locator.getCartRepository(), locator.getOrderRepository());
        } else if (modelClass.isAssignableFrom(OrdersViewModel.class)) {
            return (T) new OrdersViewModel(locator.getOrderRepository());
        } else if (modelClass.isAssignableFrom(ReviewViewModel.class)) {
            return (T) new ReviewViewModel(locator.getReviewRepository());
        } else if (modelClass.isAssignableFrom(ComplaintViewModel.class)) {
            return (T) new ComplaintViewModel(locator.getComplaintRepository());
        } else if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
            return (T) new ProfileViewModel(locator.getAuthRepository());
        }
        throw new IllegalArgumentException("Unknown ViewModel " + modelClass.getSimpleName());
    }
}
