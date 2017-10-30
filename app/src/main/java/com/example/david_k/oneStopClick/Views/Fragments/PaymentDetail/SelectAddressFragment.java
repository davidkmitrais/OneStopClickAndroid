package com.example.david_k.oneStopClick.Views.Fragments.PaymentDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.david_k.oneStopClick.Views.Activities.PaymentAddAddress.PaymentAddAddressActivity;
import com.example.david_k.oneStopClick.R;

public class SelectAddressFragment extends Fragment {

    FloatingActionButton addNewAdress;

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

        return rootView;
    }
}
