package com.example.david_k.oneStopClick.Views.Fragments.Cart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.david_k.oneStopClick.Firebase.FirebaseProvider;
import com.example.david_k.oneStopClick.Helper.Constants;
import com.example.david_k.oneStopClick.Helper.FirebaseProviderHelper;
import com.example.david_k.oneStopClick.ModelLayers.Database.ProductCart;
import com.example.david_k.oneStopClick.R;
import com.example.david_k.oneStopClick.Views.Activities.PaymentDetail.PaymentDetailTabActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private RecyclerView recyclerView;
    private CartAdapter adapter;

    List<ProductCart> productCartList;
    private FirebaseProviderHelper firebaseProviderHelper = new FirebaseProviderHelper();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams")
        View view =  inflater.inflate(R.layout.cart_fragment, null);

        setupRecyclerView(view);

        return view;
    }

    private void setupRecyclerView(View view){
        productCartList = new ArrayList<>();
        DatabaseReference productCartDatabaseReference = FirebaseProvider.getCurrentProvider().getProductCartDBReference();

        recyclerView = (RecyclerView) view.findViewById(R.id.cardCell_item);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        productCartDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getAllCart(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getAllCart(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                cartDeletion(dataSnapshot);
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void rowTapped(int position) {

        ProductCart selectedProductCart = adapter.cartProducts.get(position);

        firebaseProviderHelper.setOrderQtyForProductCart(false, selectedProductCart.getOrderQty(), selectedProductCart.getProductKey());

        goToCartDetail(selectedProductCart);
    }

    private void goToCartDetail(ProductCart productCart){

        Intent intent = new Intent(getActivity(), PaymentDetailTabActivity.class);
        intent.putExtra(Constants.productKey, productCart);

        startActivity(intent);

    }

    private void getAllCart(DataSnapshot dataSnapshot){
        ProductCart productCart = dataSnapshot.getValue(ProductCart.class);
        productCart.setFirebaseKey(dataSnapshot.getKey());
        productCartList.add(productCart);
        adapter = new CartAdapter(getActivity(), productCartList, (v, position) -> rowTapped(position));
        recyclerView.setAdapter(adapter);
    }

    private void cartDeletion(DataSnapshot dataSnapshot){
        for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
            ProductCart productCart = singleSnapshot.getValue(ProductCart.class);
            for(int i = 0; i < productCartList.size(); i++){
                if(productCartList.get(i).getProductKey().equals(productCart.getProductKey())){
                    productCartList.remove(i);
                }
            }
            Log.d("taskDeletion", "Product Cart key " + productCart.getProductKey());
            adapter.notifyDataSetChanged();
            adapter = new CartAdapter(getActivity(), productCartList, (v, position) -> rowTapped(position));
            recyclerView.setAdapter(adapter);
        }
    }
}
