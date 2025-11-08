package com.deligo.app.feature.order;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.deligo.app.core.util.DateTimeUtil;
import com.deligo.app.databinding.ItemOrderBinding;
import com.deligo.app.domain.model.Order;

import java.util.Locale;

public class OrdersHistoryAdapter extends ListAdapter<Order, OrdersHistoryAdapter.OrderViewHolder> {

    public OrdersHistoryAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderBinding binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new OrderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    private static final DiffUtil.ItemCallback<Order> DIFF_CALLBACK = new DiffUtil.ItemCallback<Order>() {
        @Override
        public boolean areItemsTheSame(@NonNull Order oldItem, @NonNull Order newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Order oldItem, @NonNull Order newItem) {
            return oldItem.getStatus().equals(newItem.getStatus()) && oldItem.getTotal() == newItem.getTotal();
        }
    };

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        private final ItemOrderBinding binding;

        OrderViewHolder(ItemOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Order order) {
            binding.orderIdTextView.setText("#" + order.getId());
            binding.orderStatusTextView.setText(order.getStatus());
            binding.orderTotalTextView.setText(String.format(Locale.getDefault(), "%,.0f đ", order.getTotal()));
            binding.orderDateTextView.setText(DateTimeUtil.formatDate(order.getCreatedAt()));
        }
    }
}
