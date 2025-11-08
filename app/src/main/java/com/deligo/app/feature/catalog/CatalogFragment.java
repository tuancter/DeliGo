package com.deligo.app.feature.catalog;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.deligo.app.core.util.AppViewModelFactory;
import com.deligo.app.core.util.Result;
import com.deligo.app.databinding.FragmentCatalogBinding;
import com.deligo.app.domain.model.Food;

import java.util.List;

public class CatalogFragment extends Fragment {

    private FragmentCatalogBinding binding;
    private CatalogViewModel viewModel;
    private FoodAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCatalogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this, new AppViewModelFactory(requireContext())).get(CatalogViewModel.class);
        adapter = new FoodAdapter(food -> viewModel.addToCart(food.getId()));
        binding.foodsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.foodsRecyclerView.setAdapter(adapter);

        binding.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.updateSearchQuery(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        viewModel.getFoods().observe(getViewLifecycleOwner(), result -> {
            if (result == null) {
                return;
            }
            if (result.getStatus() == Result.Status.LOADING) {
                binding.catalogProgressBar.setVisibility(View.VISIBLE);
            } else if (result.getStatus() == Result.Status.SUCCESS) {
                binding.catalogProgressBar.setVisibility(View.GONE);
            } else if (result.getStatus() == Result.Status.ERROR) {
                binding.catalogProgressBar.setVisibility(View.GONE);
                String message = result.getMessage() != null ? result.getMessage() : "Không thể tải danh sách món";
                if (getContext() != null) {
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.getFilteredFoods().observe(getViewLifecycleOwner(), this::renderFoods);
        viewModel.refreshFoods();
    }

    private void renderFoods(List<Food> foods) {
        adapter.submitList(foods);
        binding.emptyTextView.setVisibility(foods == null || foods.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
