package com.deligo.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.deligo.app.databinding.ActivityMainBinding;
import com.deligo.app.feature.auth.LoginActivity;
import com.deligo.app.feature.cart.CartFragment;
import com.deligo.app.feature.catalog.CatalogFragment;
import com.deligo.app.feature.order.OrdersFragment;
import com.deligo.app.feature.profile.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (new com.deligo.app.core.prefs.AppPreferences(this).getToken() == null) {
            navigateToLogin();
            return;
        }
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigation.setOnItemSelectedListener(this::onNavigationItemSelected);
        if (savedInstanceState == null) {
            binding.bottomNavigation.setSelectedItemId(R.id.menu_catalog);
        }
    }

    private boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
        Fragment fragment;
        int itemId = item.getItemId();
        if (itemId == R.id.menu_catalog) {
            fragment = new CatalogFragment();
        } else if (itemId == R.id.menu_cart) {
            fragment = new CartFragment();
        } else if (itemId == R.id.menu_orders) {
            fragment = new OrdersFragment();
        } else if (itemId == R.id.menu_profile) {
            fragment = new ProfileFragment();
        } else {
            fragment = new CatalogFragment();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
        return true;
    }

    public void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
