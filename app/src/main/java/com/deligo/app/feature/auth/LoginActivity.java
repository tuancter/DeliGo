package com.deligo.app.feature.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.deligo.app.MainActivity;
import com.deligo.app.core.util.AppViewModelFactory;
import com.deligo.app.core.util.Result;
import com.deligo.app.core.util.Validator;
import com.deligo.app.databinding.ActivityLoginBinding;
import com.deligo.app.domain.model.User;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private AuthViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (new com.deligo.app.core.prefs.AppPreferences(this).getToken() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this, new AppViewModelFactory(this)).get(AuthViewModel.class);

        binding.loginButton.setOnClickListener(v -> attemptLogin());
        binding.registerText.setOnClickListener(v -> attemptRegister());

        viewModel.getResultLiveData().observe(this, this::handleAuthResult);
    }

    private void attemptLogin() {
        String email = binding.emailEditText.getText() != null ? binding.emailEditText.getText().toString() : "";
        String password = binding.passwordEditText.getText() != null ? binding.passwordEditText.getText().toString() : "";
        if (!Validator.isValidEmail(email)) {
            binding.emailInputLayout.setError("Email không hợp lệ");
            return;
        } else {
            binding.emailInputLayout.setError(null);
        }
        if (!Validator.isValidPassword(password)) {
            binding.passwordInputLayout.setError("Mật khẩu tối thiểu 6 ký tự");
            return;
        } else {
            binding.passwordInputLayout.setError(null);
        }
        binding.loginButton.setEnabled(false);
        viewModel.login(email, password);
    }

    private void attemptRegister() {
        String email = binding.emailEditText.getText() != null ? binding.emailEditText.getText().toString() : "";
        String password = binding.passwordEditText.getText() != null ? binding.passwordEditText.getText().toString() : "";
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Nhập email và mật khẩu để đăng ký", Toast.LENGTH_SHORT).show();
            return;
        }
        binding.loginButton.setEnabled(false);
        viewModel.register(email, password);
    }

    private void handleAuthResult(Result<User> result) {
        if (result == null) {
            return;
        }
        if (result.getStatus() == Result.Status.LOADING) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else if (result.getStatus() == Result.Status.SUCCESS) {
            binding.progressBar.setVisibility(View.GONE);
            binding.loginButton.setEnabled(true);
            Toast.makeText(this, "Chào mừng " + result.getData().getName(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else if (result.getStatus() == Result.Status.ERROR) {
            binding.progressBar.setVisibility(View.GONE);
            binding.loginButton.setEnabled(true);
            Toast.makeText(this, result.getMessage() != null ? result.getMessage() : "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
        }
    }
}
