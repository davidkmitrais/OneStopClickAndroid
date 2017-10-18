package com.example.david_k.oneStopClick.Fragments.ProductList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.david_k.oneStopClick.Activities.ProductDetail.ProductDetailActivity;
import com.example.david_k.oneStopClick.Helper.Constants;
import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.R;

import java.util.ArrayList;
import java.util.List;

public class ProductListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductViewAdapter adapter;
    private List<Product> productList;

    public ProductListFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.product_list_fragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.product_list_recycler_view);

        productList = new ArrayList<>();
        adapter = new ProductViewAdapter(getActivity(), productList, (v, position) -> rowTapped(position));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        setDummyProduct();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu_product, menu);

        MenuItem search = menu.findItem(R.id.search_product);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter != null) adapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    private void setDummyProduct() {
        int[] productIds = new int[]{
                R.mipmap.product001,
                R.mipmap.product002,
                R.mipmap.product003,
                R.mipmap.product004,
                R.mipmap.product005,
                R.mipmap.product006,
                R.mipmap.product007,
                R.mipmap.product008,
                R.mipmap.product009,
                R.mipmap.product010};

        Product addProduct = null;

        for (int i = 0; i < 10; i++) {
            addProduct = new Product(i, "Product " + (i + 1), i * 10, productIds[i]);
            productList.add(addProduct);
        }

        adapter.notifyDataSetChanged();
    }

    private void rowTapped(int position) {
        Product product = productList.get(position);
        goToProductDetail(product);
    }

    private void goToProductDetail(Product product){

        Bundle bundle = new Bundle();
        bundle.putInt(Constants.productIdKey, product.id);

        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);

    }
}
