package com.deligo.app.feature.cart;

import android.os.Bundle;
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
import com.deligo.app.databinding.FragmentCartBinding;
import com.deligo.app.domain.model.CartItem;

import java.util.List;
import java.util.Locale;

public class CartFragment extends Fragment implements CartItemAdapter.CartItemListener {

    private FragmentCartBinding binding;
    private CartViewModel viewModel;
    private CartItemAdapter adapter;
    private boolean isProcessingCheckout = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this, new AppViewModelFactory(requireContext())).get(CartViewModel.class);
        adapter = new CartItemAdapter(this);
        binding.cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.cartRecyclerView.setAdapter(adapter);

        viewModel.getCartLiveData().observe(getViewLifecycleOwner(), cart -> {
            List<CartItem> items = cart != null ? cart.getItems() : null;
            adapter.submitList(items);
            boolean hasItems = items != null && !items.isEmpty();
            binding.emptyCartTextView.setVisibility(hasItems ? View.GONE : View.VISIBLE);
            if (!isProcessingCheckout) {
                binding.checkoutButton.setEnabled(hasItems);
            }
        });

        viewModel.getTotalLiveData().observe(getViewLifecycleOwner(), total -> {
            double value = total != null ? total : 0;
            binding.totalTextView.setText(String.format(Locale.getDefault(), "Tổng: %,.0f đ", value));
        });

        viewModel.getCheckoutResult().observe(getViewLifecycleOwner(), result -> {
            if (result == null) {
                return;
            }
            if (result.getStatus() == Result.Status.LOADING) {
                isProcessingCheckout = true;
                binding.checkoutButton.setEnabled(false);
            } else if (result.getStatus() == Result.Status.SUCCESS) {
                isProcessingCheckout = false;
                binding.checkoutButton.setEnabled(true);
                String message = "Đã đặt đơn hàng thành công!";
                if (result.getData() != null) {
                    message = "Đơn hàng #" + result.getData().getId() + " đã được tạo.";
                }
                if (getContext() != null) {
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                }
            } else if (result.getStatus() == Result.Status.ERROR) {
                isProcessingCheckout = false;
                binding.checkoutButton.setEnabled(true);
                String message = result.getMessage() != null ? result.getMessage() : "Không thể đặt hàng";
                if (getContext() != null) {
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.checkoutButton.setOnClickListener(v -> viewModel.checkout());
    }

    @Override
    public void onIncreaseQuantity(long foodId) {
        viewModel.addToCart(foodId);
    }

    @Override
    public void onDecreaseQuantity(long foodId) {
        viewModel.removeFromCart(foodId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isProcessingCheckout = false;
        binding = null;
    }
}
