package com.example.david_k.oneStopClick.View.Adapter.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.david_k.oneStopClick.ModelLayers.Database.Category;
import com.example.david_k.oneStopClick.R;

public class CategoryCardViewHolder extends RecyclerView.ViewHolder {

    public TextView categoryName;
    public ImageView itemPhoto;


    public CategoryCardViewHolder(View itemView) {
        super(itemView);

        categoryName = itemView.findViewById(R.id.title_category_card);
        itemPhoto = itemView.findViewById(R.id.thumbnail_category_card);
    }

    public void configureWith(Category category){
        categoryName.setText(category.getCategoryName());

    }
}
