package com.example.david_k.oneStopClick.Views.Fragments.Cart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.david_k.oneStopClick.Helper.CenterRepositoryHelper;
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
    private CenterRepositoryHelper centerRepositoryHelper = new CenterRepositoryHelper();

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

        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product currentProduct = cartProducts.get(holder.getAdapterPosition());
                int plusCartOrderQty = currentProduct.getOrderQty() + 1;
                centerRepositoryHelper.SetOrderQtyByProductId(currentProduct.getId(), plusCartOrderQty);

                setTextForCartOrderedQty(currentProduct.getId());
            }

            private void setTextForCartOrderedQty(int productId){
                int numItemOrdered = centerRepositoryHelper.GetOrderQtyByProductId(productId);

                holder.cartOrderQty.setText(String.valueOf(numItemOrdered));
            }
        });

        holder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product currentProduct = cartProducts.get(holder.getAdapterPosition());
                int minusCartOrderQty = currentProduct.getOrderQty() - 1;
                if (minusCartOrderQty < 1) {
                    minusCartOrderQty = 1;
                    Toast.makeText(context, "show remove item dialog", Toast.LENGTH_SHORT);
                }
                centerRepositoryHelper.SetOrderQtyByProductId(currentProduct.getId(), minusCartOrderQty);

                setTextForCartOrderedQty(currentProduct.getId());
            }

            private void setTextForCartOrderedQty(int productId){
                int numItemOrdered = centerRepositoryHelper.GetOrderQtyByProductId(productId);

                holder.cartOrderQty.setText(String.valueOf(numItemOrdered));
            }
        });

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
