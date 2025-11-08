package com.deligo.app.feature.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.deligo.app.core.util.Result;
import com.deligo.app.data.repo.AuthRepository;
import com.deligo.app.domain.model.User;

public class AuthViewModel extends ViewModel {
    private final AuthRepository authRepository;
    private final MutableLiveData<AuthAction> actionLiveData = new MutableLiveData<>();
    private final LiveData<Result<User>> resultLiveData;

    public AuthViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
        resultLiveData = Transformations.switchMap(actionLiveData, action -> {
            if (action == null) {
                return new MutableLiveData<>();
            }
            if (action.type == AuthAction.Type.LOGIN) {
                return authRepository.login(action.email, action.password);
            } else {
                return authRepository.register(action.email, action.password);
            }
        });
    }

    public LiveData<Result<User>> getResultLiveData() {
        return resultLiveData;
    }

    public void login(String email, String password) {
        actionLiveData.setValue(new AuthAction(AuthAction.Type.LOGIN, email, password));
    }

    public void register(String email, String password) {
        actionLiveData.setValue(new AuthAction(AuthAction.Type.REGISTER, email, password));
    }

    public void logout() {
        authRepository.logout();
    }

    private static class AuthAction {
        enum Type { LOGIN, REGISTER }

        final Type type;
        final String email;
        final String password;

        AuthAction(Type type, String email, String password) {
            this.type = type;
            this.email = email;
            this.password = password;
        }
    }
}
