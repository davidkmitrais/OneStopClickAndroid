package com.example.david_k.oneStopClick.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.david_k.oneStopClick.Firebase.FirebaseProvider;
import com.example.david_k.oneStopClick.Helper.Constants;
import com.example.david_k.oneStopClick.Helper.FirebaseProviderHelper;
import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.R;
import com.example.david_k.oneStopClick.View.Activity.ChildActivity;
import com.example.david_k.oneStopClick.View.Adapter.ProductDetailCardAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PromotedProductFragment extends Fragment {

    private static final String TAG = "PromotedProductFragment";

    RecyclerView recyclerView;
    private ProductDetailCardAdapter adapter;
    private List<Product> promotedProduct;
    private GridLayoutManager layoutManager;
    private FirebaseProviderHelper firebaseProviderHelper = new FirebaseProviderHelper();

    public PromotedProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_promoted_product, container, false);

        setupRecyclerView(view);

        return view;
    }

    public void setupRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.promoted_product_rv);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        promotedProduct = new ArrayList<>();
        DatabaseReference productDatabaseReference = FirebaseProvider.getCurrentProvider().getProductDBReference();

        productDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                addProductFromSnapshot(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //addProductFromSnapshot(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addProductFromSnapshot(DataSnapshot dataSnapshot){
        Product product = dataSnapshot.getValue(Product.class);
        product.setFirebaseKey(dataSnapshot.getKey());
        promotedProduct.add(product);
        adapter = new ProductDetailCardAdapter(getActivity(), promotedProduct, (v, item) -> rowTapped(item));
        recyclerView.setAdapter(adapter);
    }

    private void rowTapped(Product product) {

        int incViewCount = product.getViewCount() + 1;
        firebaseProviderHelper.setViewCountForProduct(incViewCount, product.getFirebaseKey());

//        Toast.makeText(getActivity(), "Go to Product " + product.getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), ChildActivity.class);
        intent.putExtra(Constants.productKey, product);
        intent.putExtra(Constants.childPageActivityKey, "ProductDetail");

        startActivity(intent);
    }
}
