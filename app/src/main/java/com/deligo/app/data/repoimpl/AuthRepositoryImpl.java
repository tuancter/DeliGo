package com.deligo.app.data.repoimpl;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.deligo.app.core.prefs.AppPreferences;
import com.deligo.app.core.util.Result;
import com.deligo.app.data.repo.AuthRepository;
import com.deligo.app.domain.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AuthRepositoryImpl implements AuthRepository {
    private final AppPreferences preferences;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static final String DEFAULT_EMAIL = "user@deligo.com";
    private static final String DEFAULT_PASSWORD = "password123";
    private static final long DEFAULT_USER_ID = 1L;

    public AuthRepositoryImpl(Context context) {
        this.preferences = new AppPreferences(context);
    }

    @Override
    public LiveData<Result<User>> login(String email, String password) {
        MutableLiveData<Result<User>> liveData = new MutableLiveData<>();
        liveData.setValue(Result.loading(null));
        executor.execute(() -> performLocalLogin(liveData, email, password));
        return liveData;
    }

    @Override
    public LiveData<Result<User>> register(String email, String password) {
        MutableLiveData<Result<User>> liveData = new MutableLiveData<>();
        liveData.setValue(Result.error("Đăng ký chưa được hỗ trợ", null));
        return liveData;
    }

    @Override
    public void logout() {
        preferences.clear();
    }

    private void performLocalLogin(MutableLiveData<Result<User>> liveData, String email, String password) {
        if (DEFAULT_EMAIL.equalsIgnoreCase(email) && DEFAULT_PASSWORD.equals(password)) {
            preferences.saveToken("local-token");
            User user = new User(DEFAULT_USER_ID, "Deligo User", DEFAULT_EMAIL, "USER");
            liveData.postValue(Result.success(user));
        } else {
            liveData.postValue(Result.error("Email hoặc mật khẩu không đúng", null));
        }
    }
}
