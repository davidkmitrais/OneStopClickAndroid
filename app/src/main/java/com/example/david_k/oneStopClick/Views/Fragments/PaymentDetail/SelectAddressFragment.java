package com.example.david_k.oneStopClick.Views.Fragments.PaymentDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.david_k.oneStopClick.ModelLayers.CenterRepository;
import com.example.david_k.oneStopClick.ModelLayers.Database.Address;
import com.example.david_k.oneStopClick.Views.Activities.PaymentAddAddress.PaymentAddAddressActivity;
import com.example.david_k.oneStopClick.R;

import java.util.List;

public class SelectAddressFragment extends Fragment {

    private FloatingActionButton addNewAdress;
    private RecyclerView recyclerView;
    private SelectAddressViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_payment_detail_select_address, container, false);

        addNewAdress = (FloatingActionButton) rootView.findViewById(R.id.add_new_address_button);
        addNewAdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PaymentAddAddressActivity.class);

                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) rootView.findViewById(R.id.address_list_recycler_view);
        List<Address> addresses = CenterRepository.getCenterRepository().getListOfAddress();
        adapter = new SelectAddressViewAdapter(getActivity(), addresses, (v, position) -> rowTapped(position));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    private void rowTapped(int position) {
        List<Address> addresses = CenterRepository.getCenterRepository().getListOfAddress();
        Address address = addresses.get(position);

        Log.d("SelectAddressFragment", "tapped for : " + address.getAddressName());
    }
}
