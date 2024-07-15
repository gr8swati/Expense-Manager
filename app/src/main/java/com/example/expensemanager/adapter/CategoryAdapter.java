package com.example.expensemanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanager.R;
import com.example.expensemanager.databinding.SampleCategoryItemsBinding;
import com.example.expensemanager.models.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    ArrayList<Category> categories;

    public interface CategoryClickListener{
        void onCategoryClickListener(Category category);
    }
    CategoryClickListener categoryClickListener;

    public CategoryAdapter(Context context , ArrayList<Category> categories , CategoryClickListener categoryClickListener){
        this.context = context;
        this.categories = categories;
        this.categoryClickListener = categoryClickListener;
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.sample_category_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
       Category category = categories.get(position);
       holder.binding.categoryText.setText(category.getCategoryName());
       holder.binding.categoryImage.setImageResource(category.getCategoryImage());
       holder.binding.categoryImage.setBackgroundTintList(context.getColorStateList(category.getCategoryColor()));
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               categoryClickListener.onCategoryClickListener(category);
           }
       });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        SampleCategoryItemsBinding binding;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleCategoryItemsBinding.bind(itemView);
        }
    }
}
