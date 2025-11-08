package com.deligo.app.feature.cart;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.deligo.app.databinding.ItemCartBinding;
import com.deligo.app.domain.model.CartItem;

import java.util.Locale;

public class CartItemAdapter extends ListAdapter<CartItem, CartItemAdapter.CartItemViewHolder> {

    public interface CartItemListener {
        void onIncreaseQuantity(long foodId);

        void onDecreaseQuantity(long foodId);
    }

    private final CartItemListener listener;

    public CartItemAdapter(CartItemListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCartBinding binding = ItemCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CartItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    private static final DiffUtil.ItemCallback<CartItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<CartItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull CartItem oldItem, @NonNull CartItem newItem) {
            return oldItem.getFood().getId() == newItem.getFood().getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull CartItem oldItem, @NonNull CartItem newItem) {
            return oldItem.getQuantity() == newItem.getQuantity();
        }
    };

    class CartItemViewHolder extends RecyclerView.ViewHolder {
        private final ItemCartBinding binding;

        CartItemViewHolder(ItemCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(CartItem item) {
            binding.cartItemNameTextView.setText(item.getFood().getName());
            binding.cartItemQuantityTextView.setText(String.valueOf(item.getQuantity()));
            binding.cartItemPriceTextView.setText(String.format(Locale.getDefault(), "%,.0f đ", item.getTotalPrice()));
            binding.increaseButton.setOnClickListener(v -> listener.onIncreaseQuantity(item.getFood().getId()));
            binding.decreaseButton.setOnClickListener(v -> listener.onDecreaseQuantity(item.getFood().getId()));
        }
    }
}
