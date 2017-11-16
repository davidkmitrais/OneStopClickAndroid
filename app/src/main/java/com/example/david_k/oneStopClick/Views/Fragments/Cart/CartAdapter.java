package com.example.david_k.oneStopClick.Views.Fragments.Cart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.david_k.oneStopClick.Firebase.FirebaseProvider;
import com.example.david_k.oneStopClick.Helper.CustomItemClickListener;
import com.example.david_k.oneStopClick.Helper.FirebaseProviderHelper;
import com.example.david_k.oneStopClick.Helper.OnGetDataListener;
import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.ModelLayers.Database.ProductCart;
import com.example.david_k.oneStopClick.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartHolder> {
    private Context context;
    private CustomItemClickListener listener;

    List<ProductCart> cartProducts;
    private Map<String, Integer> productPriceRef;
    private DatabaseReference productDBRef;
    private FirebaseProviderHelper firebaseProviderHelper = new FirebaseProviderHelper();

    public CartAdapter(Context context, List<ProductCart> cartProductList, CustomItemClickListener listener) {
        this.context = context;
        this.cartProducts = cartProductList;
        this.listener = listener;
        this.productDBRef = FirebaseProvider.getCurrentProvider().getProductDBReference();
        this.productPriceRef = new HashMap<>();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View cartView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_cell, parent, false);

        final CartHolder holder = new CartHolder(cartView);

        cartView.setOnClickListener(v -> listener.onItemClick(v, holder.getAdapterPosition()));

        holder.addButton.setOnClickListener(v -> {
            ProductCart currentProductCart = cartProducts.get(holder.getAdapterPosition());
            int plusCartOrderQty = currentProductCart.getOrderQty() + 1;
            int price = productPriceRef.get(currentProductCart.getProductKey());
            int totalPrice = plusCartOrderQty * price;

            holder.cartOrderQty.setText(String.valueOf(plusCartOrderQty));
            holder.cartProductTotalPrice.setText(totalPrice + " USD");

            currentProductCart.setOrderQty(plusCartOrderQty);
        });

        holder.minusButton.setOnClickListener(v -> {

            ProductCart currentProductCart = cartProducts.get(holder.getAdapterPosition());
            int minusCartOrderQty = currentProductCart.getOrderQty() - 1;
            if (minusCartOrderQty < 1) {
                minusCartOrderQty = 1;
                Toast.makeText(context, "show remove item dialog", Toast.LENGTH_SHORT).show();
            }
            int price = productPriceRef.get(currentProductCart.getProductKey());
            int totalPrice = minusCartOrderQty * price;

            holder.cartOrderQty.setText(String.valueOf(minusCartOrderQty));
            holder.cartProductTotalPrice.setText(totalPrice + " USD");

            currentProductCart.setOrderQty(minusCartOrderQty);
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(CartHolder holder, int position) {
        ProductCart productCart = cartProducts.get(position);
        String productKey = productCart.getProductKey();
        int orderQty = productCart.getOrderQty();
        
        Query query = productDBRef.orderByKey().equalTo(productKey);
        firebaseProviderHelper.getDataSnapshotOnceFromQuery(query, new OnGetDataListener() {
            @Override
            public void onStart() {
                
            }

            @Override
            public void onSuccess(DataSnapshot data) {
                for (DataSnapshot dataItem: data.getChildren()) {
                    Product product;
                    product = dataItem.getValue(Product.class);

                    productPriceRef.put(dataItem.getKey(), product.getPrice());

                    holder.configureWith(product, orderQty);
                    firebaseProviderHelper.setupProductPhoto(context, product.getImageName(), holder.productPhoto);
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return cartProducts.size();
    }
}
