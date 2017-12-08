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
    public TextView viewCountText;
    public TextView likeCountText;

    public ProductDetailCardViewHolder(View itemView) {
        super(itemView);

        itemName = itemView.findViewById(R.id.title_product_card);
        itemPrice = itemView.findViewById(R.id.price_product_card);
        itemPhoto = itemView.findViewById(R.id.thumbnail_product_card);
        overflowIcon = itemView.findViewById(R.id.overflow_product_card);
        likeCountText = itemView.findViewById(R.id.like_count_product_card);
        viewCountText = itemView.findViewById(R.id.view_count_product_card);
    }

    public void configureWith(Product product){
        String price = String.valueOf(product.getPrice()) + " USD";
        String likeCount = "Likes : " + String.valueOf(product.getLikeCount());
        String viewCount = "Views : " + String.valueOf(product.getViewCount());

        itemName.setText(product.name);
        itemPrice.setText(price);
        likeCountText.setText(likeCount);
        viewCountText.setText(viewCount);
    }
}
