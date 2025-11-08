package com.deligo.app.feature.catalog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.deligo.app.databinding.ItemFoodBinding;
import com.deligo.app.domain.model.Food;

import java.util.Locale;

public class FoodAdapter extends ListAdapter<Food, FoodAdapter.FoodViewHolder> {

    public interface FoodActionListener {
        void onAddToCart(Food food);
    }

    private final FoodActionListener listener;

    public FoodAdapter(FoodActionListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFoodBinding binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FoodViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    private static final DiffUtil.ItemCallback<Food> DIFF_CALLBACK = new DiffUtil.ItemCallback<Food>() {
        @Override
        public boolean areItemsTheSame(@NonNull Food oldItem, @NonNull Food newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Food oldItem, @NonNull Food newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getPrice() == newItem.getPrice() &&
                    oldItem.getCategory().equals(newItem.getCategory()) &&
                    oldItem.isAvailable() == newItem.isAvailable();
        }
    };

    class FoodViewHolder extends RecyclerView.ViewHolder {
        private final ItemFoodBinding binding;

        FoodViewHolder(ItemFoodBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Food food) {
            binding.foodNameTextView.setText(food.getName());
            binding.foodCategoryTextView.setText(food.getCategory());
            binding.foodPriceTextView.setText(String.format(Locale.getDefault(), "%,.0f đ", food.getPrice()));
            binding.addToCartButton.setEnabled(food.isAvailable());
            binding.addToCartButton.setOnClickListener(v -> listener.onAddToCart(food));
            binding.foodImageView.setVisibility(View.VISIBLE);
            Glide.with(binding.getRoot().getContext())
                    .load(food.getImageUrl())
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .into(binding.foodImageView);
        }
    }
}
