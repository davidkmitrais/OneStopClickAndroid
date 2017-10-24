package com.example.david_k.oneStopClick.Views.Fragments.ProductList;

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

import com.example.david_k.oneStopClick.Database.DataSource;
import com.example.david_k.oneStopClick.Helper.Constants;
import com.example.david_k.oneStopClick.ModelLayers.CenterRepository;
import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.ModelLayers.SampleProductProvider;
import com.example.david_k.oneStopClick.R;
import com.example.david_k.oneStopClick.Views.Activities.ProductDetail.ProductDetailActivity;

import java.util.List;
import java.util.Optional;

public class ProductListFragment extends Fragment {


    DataSource mDataSource;
    private RecyclerView recyclerView;
    private ProductViewAdapter adapter;
    private List<Product> productList = SampleProductProvider.productList;
    private List<Product> productListFromDB;

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

        SetupProductData();
        List<Product> productListDisplay = CenterRepository.getCenterRepository().getListOfProductsInShoppingList();

        adapter = new ProductViewAdapter(getActivity(), productListDisplay, (v, position) -> rowTapped(position));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mDataSource.close();
    }

    @Override
    public void onPause() {
        super.onPause();
        mDataSource.open();
    }

    private void SetupProductData(){

        mDataSource = new DataSource(getActivity());
        mDataSource.open();
        mDataSource.seedDatabase(productList);

        productListFromDB = mDataSource.getAllItems();

        CenterRepository.getCenterRepository().setListOfProductsInShoppingList(productListFromDB);
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
                if (adapter != null) {
                    adapter.getFilter().filter(newText);
                }

                return true;
            }

        });
    }

    private void rowTapped(int position) {
        int productId = adapter.filteredProducts.get(position).getId();
        Product product  = CenterRepository.getCenterRepository()
                                        .getProductById(productId);

        goToProductDetail(product);
    }

    private void goToProductDetail(Product product){

        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        intent.putExtra(Constants.productKey, product);

        startActivity(intent);

    }
}
