package com.example.david_k.oneStopClick.View.Adapter.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.R;

public class ProductDetailCardViewHolder extends RecyclerView.ViewHolder {

    public TextView itemName;
    public TextView itemPrice;
    public ImageView itemPhoto;
    public ImageView overflowIcon;


    public ProductDetailCardViewHolder(View itemView) {
        super(itemView);

        itemName = itemView.findViewById(R.id.title_product_card);
        itemPrice = itemView.findViewById(R.id.count_product_card);
        itemPhoto = itemView.findViewById(R.id.thumbnail_product_card);
        overflowIcon = itemView.findViewById(R.id.overflow_product_card);
    }

    public void configureWith(Product product){
        String price = String.valueOf(product.price);

        itemName.setText(product.name);
        itemPrice.setText(price);
    }
}
