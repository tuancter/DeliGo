package com.deligo.app.feature.profile;

import androidx.lifecycle.ViewModel;

import com.deligo.app.data.repo.AuthRepository;

public class ProfileViewModel extends ViewModel {
    private final AuthRepository authRepository;

    public ProfileViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void logout() {
        authRepository.logout();
    }
}
