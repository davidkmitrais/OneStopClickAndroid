package com.example.david_k.oneStopClick.Fragments.ProductList;

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
 * Created by David_K on 11/10/2017.
 */

public class ProductViewAdapter extends RecyclerView.Adapter<ProductViewHolder>{

    private Context context;
    private List<Product> products;
    CustomItemClickListener listener;

    public ProductViewAdapter(Context context, List<Product> productList, CustomItemClickListener listener) {
        this.context = context;
        this.products = productList;
        this.listener = listener;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View productView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_cell, parent, false);

        final ProductViewHolder holder = new ProductViewHolder(productView);

        productView.setOnClickListener(v -> listener.onItemClick(v, holder.getAdapterPosition()));

        return holder;
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.configureWith(product);

        Glide.with(context).load(product.getImageId()).into(holder.itemPhoto);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
