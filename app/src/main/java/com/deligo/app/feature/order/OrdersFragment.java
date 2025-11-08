package com.deligo.app.feature.order;

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
import com.deligo.app.databinding.FragmentOrdersBinding;
import com.deligo.app.domain.model.Order;

import java.util.List;

public class OrdersFragment extends Fragment {

    private FragmentOrdersBinding binding;
    private OrdersViewModel viewModel;
    private OrdersHistoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOrdersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this, new AppViewModelFactory(requireContext())).get(OrdersViewModel.class);
        adapter = new OrdersHistoryAdapter();
        binding.ordersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.ordersRecyclerView.setAdapter(adapter);

        viewModel.getOrders().observe(getViewLifecycleOwner(), result -> {
            if (result == null) {
                return;
            }
            if (result.getStatus() == Result.Status.LOADING) {
                binding.ordersProgressBar.setVisibility(View.VISIBLE);
            } else if (result.getStatus() == Result.Status.SUCCESS) {
                binding.ordersProgressBar.setVisibility(View.GONE);
                renderOrders(result.getData());
            } else if (result.getStatus() == Result.Status.ERROR) {
                binding.ordersProgressBar.setVisibility(View.GONE);
                String message = result.getMessage() != null ? result.getMessage() : "Không thể tải đơn hàng";
                if (getContext() != null) {
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.refreshOrders();
    }

    private void renderOrders(List<Order> orders) {
        adapter.submitList(orders);
        binding.ordersEmptyTextView.setVisibility(orders == null || orders.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
