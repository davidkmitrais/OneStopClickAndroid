package com.example.david_k.oneStopClick.Views.Fragments.Cart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.david_k.oneStopClick.Helper.CustomItemClickListener;
import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.R;

import java.util.List;

/**
 * Created by David_K on 24/10/2017.
 */

public class CartAdapter extends RecyclerView.Adapter<CartHolder> {
    private Context context;
    CustomItemClickListener listener;
    public List<Product> cartProducts;

    public CartAdapter(Context context, List<Product> cartProductList, CustomItemClickListener listener) {
        this.context = context;
        this.cartProducts = cartProductList;
        this.listener = listener;
    }

    @Override
    public CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View cartView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_cell, parent, false);

        final CartHolder holder = new CartHolder(cartView);

        cartView.setOnClickListener(v -> listener.onItemClick(v, holder.getAdapterPosition()));

        return holder;
    }

    @Override
    public void onBindViewHolder(CartHolder holder, int position) {
        Product product = cartProducts.get(position);
        holder.configureWith(product);

        Glide.with(context).load(product.getImageId()).into(holder.productPhoto);
    }

    @Override
    public int getItemCount() {
        return cartProducts.size();
    }
}
