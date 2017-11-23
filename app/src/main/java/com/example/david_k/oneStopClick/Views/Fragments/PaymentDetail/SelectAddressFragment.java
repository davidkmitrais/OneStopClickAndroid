package com.example.david_k.oneStopClick.Views.Fragments.PaymentDetail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.david_k.oneStopClick.Firebase.FirebaseProvider;
import com.example.david_k.oneStopClick.Helper.Constants;
import com.example.david_k.oneStopClick.Helper.FirebaseProviderHelper;
import com.example.david_k.oneStopClick.ModelLayers.Database.Address;
import com.example.david_k.oneStopClick.ModelLayers.Database.ProductCart;
import com.example.david_k.oneStopClick.R;
import com.example.david_k.oneStopClick.Views.Activities.PaymentAddAddress.PaymentAddAddressActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class SelectAddressFragment extends Fragment {

    private FloatingActionButton addNewAdress;
    private RecyclerView recyclerView;
    private SelectAddressViewAdapter adapter;
    private Button nextButon;
    private FirebaseProviderHelper firebaseHelper = new FirebaseProviderHelper();

    private List<Address> addressList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_payment_detail_select_address, container, false);

        addNewAdress = (FloatingActionButton) rootView.findViewById(R.id.add_new_address_button);
        addNewAdress.setOnClickListener(v -> {
            ProductCart productCart = getActivity().getIntent().getExtras().getParcelable(Constants.productCartKey);

            Intent intent = new Intent(getActivity(), PaymentAddAddressActivity.class);
            intent.putExtra(Constants.productCartKey, productCart);

            startActivity(intent);
        });

        nextButon = (Button)rootView.findViewById(R.id.select_address_next_buton);
        nextButon.setOnClickListener(v -> {

            ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.payment_tab_view_pager);
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        });

        setupRecyclerView(rootView);

        return rootView;
    }

    private void setupRecyclerView(View view){
        addressList = new ArrayList<>();
        DatabaseReference addressListDBRef = FirebaseProvider.getCurrentProvider().getAddressDBReference()
                .child(Address.CHILD_ADDRESS_LIST);

        recyclerView = (RecyclerView) view.findViewById(R.id.address_list_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        addressListDBRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getAllAddress(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getAllAddress(dataSnapshot);
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

            private void getAllAddress(DataSnapshot dataSnapshot){
                Address address = dataSnapshot.getValue(Address.class);
                address.setFirebaseKey(dataSnapshot.getKey());
                addressList.add(address);
                adapter = new SelectAddressViewAdapter(getActivity(), addressList, (v, position) -> rowTapped(v, recyclerView, position));
                recyclerView.setAdapter(adapter);
            }
        });
    }

    private void rowTapped(View view, RecyclerView recyclerView, int position) {

        Address address = addressList.get(position);

        for (int i = 0; i < recyclerView.getChildCount(); i++){
            if (i == position) {
                continue;
            }

            View childView = recyclerView.getChildAt(i);
            TextView childSelectedText = (TextView) childView.findViewById(R.id.selected_address_text);
            boolean isSelectedTextDisplayed = childSelectedText.getVisibility() == View.VISIBLE;
            if (isSelectedTextDisplayed) {
                childSelectedText.setVisibility(View.INVISIBLE);
                Log.d("SelectAddressFragment", "Remove selected text at : " + i);
            }
        }

        TextView selectedText = (TextView) view.findViewById(R.id.selected_address_text);
        boolean isSelectedTextDisplayed = selectedText.getVisibility() == View.VISIBLE;

        if (isSelectedTextDisplayed) {
            firebaseHelper.updateSelectedAddress(Constants.notSetKey);
            selectedText.setVisibility(View.INVISIBLE);
            Log.d("SelectAddressFragment", "Un-Select for : " + address.getAddressName());
        }
        else {
            firebaseHelper.updateSelectedAddress(address.getFirebaseKey());
            selectedText.setVisibility(View.VISIBLE);
            Log.d("SelectAddressFragment", "Select for : " + address.getAddressName());
        }
    }
}
