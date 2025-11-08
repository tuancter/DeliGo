package com.deligo.app.data.repo;

import androidx.lifecycle.LiveData;

import com.deligo.app.core.util.Result;
import com.deligo.app.domain.model.User;

public interface AuthRepository {
    LiveData<Result<User>> login(String email, String password);

    LiveData<Result<User>> register(String email, String password);

    void logout();
}
