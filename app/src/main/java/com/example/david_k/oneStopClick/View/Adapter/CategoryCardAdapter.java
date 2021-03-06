package com.example.david_k.oneStopClick.View.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.david_k.oneStopClick.Helper.FirebaseProviderHelper;
import com.example.david_k.oneStopClick.Helper.Interface.CategoryItemClickListener;
import com.example.david_k.oneStopClick.ModelLayers.Database.Category;
import com.example.david_k.oneStopClick.R;
import com.example.david_k.oneStopClick.View.Adapter.ViewHolder.CategoryCardViewHolder;

import java.util.List;

public class CategoryCardAdapter extends RecyclerView.Adapter<CategoryCardViewHolder> {

    public List<Category> categories;

    private Context context;
    CategoryItemClickListener listener;
    private FirebaseProviderHelper firebaseProviderHelper = new FirebaseProviderHelper();

    public CategoryCardAdapter(Context context, List<Category> categoryList, CategoryItemClickListener listener) {
        this.context = context;
        this.categories = categoryList;
        this.listener = listener;
    }

    @Override
    public CategoryCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View categoryView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_category, parent, false);

        final CategoryCardViewHolder holder = new CategoryCardViewHolder(categoryView);

        categoryView.setOnClickListener(v -> listener.onCategoryClick(v, getCategoryByPosition(holder.getAdapterPosition())));

        return holder;
    }

    @Override
    public void onBindViewHolder(CategoryCardViewHolder holder, int position) {
        Category category = getCategoryByPosition(position);
        holder.configureWith(category);

        firebaseProviderHelper.setupCategoryPhoto(context, category.getImageName(), holder.itemPhoto);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    private Category getCategoryByPosition(int position){
        return categories.get(position);
    }
}
