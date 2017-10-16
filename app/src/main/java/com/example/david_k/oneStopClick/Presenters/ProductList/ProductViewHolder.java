package com.example.david_k.oneStopClick.Presenters.ProductList;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.david_k.oneStopClick.Helper.Helper;
import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.ModelLayers.Database.Spy;
import com.example.david_k.oneStopClick.R;

/**
 * Created by David_K on 11/10/2017.
 */

public class ProductViewHolder extends RecyclerView.ViewHolder {

    public TextView itemName;
    public TextView itemPrice;
    public ImageView itemPhoto;

    public ProductViewHolder(View itemView) {
        super(itemView);

        itemName = (TextView) itemView.findViewById(R.id.product_name);
        itemPrice = (TextView) itemView.findViewById(R.id.product_price);
        itemPhoto = (ImageView) itemView.findViewById(R.id.product_photo);
    }

    public void configureWith(Product product) {
        //int imageId = Helper.resourceIdWith(context, product.imageName);
        String price = String.valueOf(product.price);

        itemName.setText(product.name);
        itemPrice.setText(price);

        //itemPhoto.setImageResource(product.imageId);
    }
}
