package com.example.david_k.oneStopClick.Fragments.ProductList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.bumptech.glide.Glide;
import com.example.david_k.oneStopClick.Helper.CustomItemClickListener;
import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.R;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by David_K on 11/10/2017.
 */

public class ProductViewAdapter extends RecyclerView.Adapter<ProductViewHolder> implements Filterable{

    private Context context;
    private List<Product> products;
    CustomItemClickListener listener;
    private List<Product> filteredProducts;

    public ProductViewAdapter(Context context, List<Product> productList, CustomItemClickListener listener) {
        this.context = context;
        this.products = productList;
        this.listener = listener;
        this.filteredProducts = productList;
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
        Product product = filteredProducts.get(position);
        holder.configureWith(product);

        Glide.with(context).load(product.getImageId()).into(holder.itemPhoto);
    }

    @Override
    public int getItemCount() {
        return filteredProducts.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();

                if (charString.isEmpty()) {

                    filteredProducts = products;
                } else {

                    ArrayList<Product> filteredList = new ArrayList<>();

                    for (Product androidVersion : products) {

                        if (androidVersion.getName().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                        }
                    }

                    filteredProducts = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredProducts;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredProducts = (ArrayList<Product>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
