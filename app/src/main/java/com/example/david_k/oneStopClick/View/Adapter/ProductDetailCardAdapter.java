package com.example.david_k.oneStopClick.View.Adapter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.example.david_k.oneStopClick.Helper.FirebaseProviderHelper;
import com.example.david_k.oneStopClick.Helper.Interface.ProductItemClickListener;
import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.R;
import com.example.david_k.oneStopClick.View.Adapter.ViewHolder.ProductDetailCardViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailCardAdapter extends RecyclerView.Adapter<ProductDetailCardViewHolder> implements Filterable {

    public List<Product> products;
    public List<Product> filteredProducts;

    private Context context;
    ProductItemClickListener listener;
    private FirebaseProviderHelper firebaseProviderHelper = new FirebaseProviderHelper();

    public ProductDetailCardAdapter(Context context, List<Product> productList, ProductItemClickListener listener) {
        this.context = context;
        this.products = productList;
        this.filteredProducts = productList;
        this.listener = listener;
    }

    @Override
    public ProductDetailCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View productView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_product, parent, false);

        final ProductDetailCardViewHolder holder = new ProductDetailCardViewHolder(productView);

        productView.setOnClickListener(v -> listener.onProductClick(v, getProductByPosition(holder.getAdapterPosition())));

        return holder;
    }

    @Override
    public void onBindViewHolder(ProductDetailCardViewHolder holder, int position) {
        Product product = getProductByPosition(position);
        holder.configureWith(product);

        firebaseProviderHelper.setupProductPhoto(context, product.getImageName(), holder.itemPhoto);

        holder.overflowIcon.setOnClickListener(view ->
                showPopupMenu(view)
        );
    }

    @Override
    public int getItemCount() {
        return filteredProducts.size();
    }

    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_payment_tab_detail, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    private Product getProductByPosition(int position){
        return filteredProducts.get(position);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();

                if (charString.isEmpty()) {
                    filteredProducts = products;
                }
                else {

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

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_settings:
                    Toast.makeText(context, "Setting Clicked", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }
}
