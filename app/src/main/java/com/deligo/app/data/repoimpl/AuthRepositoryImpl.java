package com.deligo.app.data.repoimpl;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.deligo.app.core.prefs.AppPreferences;
import com.deligo.app.core.util.Result;
import com.deligo.app.data.api.AuthApi;
import com.deligo.app.data.api.dto.AuthRequest;
import com.deligo.app.data.api.dto.AuthResponse;
import com.deligo.app.data.repo.AuthRepository;
import com.deligo.app.domain.model.User;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Response;

public class AuthRepositoryImpl implements AuthRepository {
    private final AuthApi authApi;
    private final AppPreferences preferences;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public AuthRepositoryImpl(Context context, AuthApi authApi) {
        this.authApi = authApi;
        this.preferences = new AppPreferences(context);
    }

    @Override
    public LiveData<Result<User>> login(String email, String password) {
        MutableLiveData<Result<User>> liveData = new MutableLiveData<>();
        liveData.setValue(Result.loading(null));
        executor.execute(() -> performAuth(liveData, authApi.login(new AuthRequest(email, password))));
        return liveData;
    }

    @Override
    public LiveData<Result<User>> register(String email, String password) {
        MutableLiveData<Result<User>> liveData = new MutableLiveData<>();
        liveData.setValue(Result.loading(null));
        executor.execute(() -> performAuth(liveData, authApi.register(new AuthRequest(email, password))));
        return liveData;
    }

    @Override
    public void logout() {
        preferences.clear();
    }

    private void performAuth(MutableLiveData<Result<User>> liveData, Call<AuthResponse> call) {
        try {
            Response<AuthResponse> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                AuthResponse authResponse = response.body();
                preferences.saveToken(authResponse.getToken());
                User user = new User(authResponse.getUserId(), authResponse.getName(), authResponse.getEmail(), authResponse.getRole());
                liveData.postValue(Result.success(user));
            } else {
                liveData.postValue(Result.error("Không thể xác thực", null));
            }
        } catch (IOException e) {
            liveData.postValue(Result.error(e.getMessage(), null));
        }
    }
}
