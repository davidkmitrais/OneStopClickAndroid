package com.example.david_k.oneStopClick.Views.Fragments.ProductList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.david_k.oneStopClick.Firebase.FirebaseProvider;
import com.example.david_k.oneStopClick.Helper.Constants;
import com.example.david_k.oneStopClick.Helper.FirebaseProviderHelper;
import com.example.david_k.oneStopClick.Helper.OnGetDataListener;
import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.R;
import com.example.david_k.oneStopClick.Views.Activities.ProductDetail.ProductDetailActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class ProductListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductViewAdapter adapter;

    private List<Product> allProduct;
    private FirebaseProviderHelper firebaseProviderHelper = new FirebaseProviderHelper();

    public ProductListFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    private void setupRecyclerView(View view){
        allProduct = new ArrayList<>();
        DatabaseReference productDatabaseReference = FirebaseProvider.getCurrentProvider().getProductDBReference();

        recyclerView = (RecyclerView) view.findViewById(R.id.product_list_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        productDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getAllProduct(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getAllProduct(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                productDeletion(dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.product_list_fragment, container, false);

        setupRecyclerView(view);

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
                if (adapter != null) {
                    adapter.getFilter().filter(newText);
                }

                return true;
            }

        });
    }

    private void rowTapped(int position) {

        String key = adapter.filteredProducts.get(position).getFirebaseKey();

        Query query = FirebaseProvider.getCurrentProvider().getProductDBReference().orderByKey().equalTo(key);
        firebaseProviderHelper.getDataSnapshotOnceFromQuery(query, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                for (DataSnapshot itemData : data.getChildren()) {
                    Product product = itemData.getValue(Product.class);
                    product.setFirebaseKey(itemData.getKey());
                    goToProductDetail(product);
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
    }

    private void goToProductDetail(Product product){

        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        intent.putExtra(Constants.productKey, product);

        startActivity(intent);
    }

    private void getAllProduct(DataSnapshot dataSnapshot){
        Product product = dataSnapshot.getValue(Product.class);
        product.setFirebaseKey(dataSnapshot.getKey());
        allProduct.add(product);
        adapter = new ProductViewAdapter(getActivity(), allProduct, (v, position) -> rowTapped(position));
        recyclerView.setAdapter(adapter);
    }

    private void productDeletion(DataSnapshot dataSnapshot){
        for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
            Product product = singleSnapshot.getValue(Product.class);
            for(int i = 0; i < allProduct.size(); i++){
                if(allProduct.get(i).getName().equals(product.getName())){
                    allProduct.remove(i);
                }
            }
            Log.d("taskDeletion", "Product Name " + product.getName());
            adapter.notifyDataSetChanged();
            adapter = new ProductViewAdapter(getActivity(), allProduct, (v, position) -> rowTapped(position));
            recyclerView.setAdapter(adapter);
        }
    }
}
