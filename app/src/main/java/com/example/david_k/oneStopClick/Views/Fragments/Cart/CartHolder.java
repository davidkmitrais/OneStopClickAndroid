package com.example.david_k.oneStopClick.Views.Fragments.Cart;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.R;

/**
 * Created by David_K on 24/10/2017.
 */

public class CartHolder extends RecyclerView.ViewHolder {

    public TextView cartProductName;
    public TextView cartProductPrice;
    public TextView cartOrderQty;
    public ImageView productPhoto;
    public ImageButton addButton;
    public ImageButton minusButton;

    public CartHolder(View itemView) {
        super(itemView);

        cartProductName = (TextView) itemView.findViewById(R.id.cart_product_name);
        cartProductPrice = (TextView) itemView.findViewById(R.id.cart_product_price);
        cartOrderQty = (TextView) itemView.findViewById(R.id.cart_order_qty);;
        productPhoto = (ImageView) itemView.findViewById(R.id.cart_product_thumb);
        addButton = (ImageButton) itemView.findViewById(R.id.cart_add_item);
        minusButton = (ImageButton) itemView.findViewById(R.id.cart_minus_item);
    }

    public void configureWith(Product product) {
        String price = String.valueOf(product.price);

        cartProductName.setText(product.name);
        cartProductPrice.setText(price);
        cartOrderQty.setText(String.valueOf(product.orderQty));

        //itemPhoto.setImageResource(product.imageId);
    }
}
