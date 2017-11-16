package com.example.david_k.oneStopClick.Views.Fragments.Cart;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.R;

public class CartHolder extends RecyclerView.ViewHolder {

    private TextView cartProductName;
    TextView cartProductTotalPrice;
    private TextView cartProductPrice;
    TextView cartOrderQty;
    ImageView productPhoto;
    ImageButton addButton;
    ImageButton minusButton;

    public CartHolder(View itemView) {
        super(itemView);

        cartProductName = (TextView) itemView.findViewById(R.id.cart_product_name);
        cartProductTotalPrice = (TextView) itemView.findViewById(R.id.cart_product_total_price);
        cartProductPrice = (TextView) itemView.findViewById(R.id.cart_product_price);
        cartOrderQty = (TextView) itemView.findViewById(R.id.cart_order_qty);
        productPhoto = (ImageView) itemView.findViewById(R.id.cart_product_thumb);
        addButton = (ImageButton) itemView.findViewById(R.id.cart_add_item);
        minusButton = (ImageButton) itemView.findViewById(R.id.cart_minus_item);
    }

    @SuppressLint("SetTextI18n")
    public void configureWith(Product product, int orderQty) {
        String price = String.valueOf(product.getPrice());
        int totalPrice;
        totalPrice = product.getPrice() * orderQty;

        cartProductName.setText(product.getName());
        cartProductPrice.setText("Price (USD) : " + price);
        cartProductTotalPrice.setText(totalPrice + " USD");
        cartOrderQty.setText(String.valueOf(orderQty));
    }
}
