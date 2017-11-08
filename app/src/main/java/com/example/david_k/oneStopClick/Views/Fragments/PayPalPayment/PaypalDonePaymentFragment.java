package com.example.david_k.oneStopClick.Views.Fragments.PayPalPayment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.david_k.oneStopClick.MainActivity;
import com.example.david_k.oneStopClick.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaypalDonePaymentFragment extends Fragment {


    public PaypalDonePaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_paypal_done_payment, container, false);

        Button doneButton = (Button) view.findViewById(R.id.return_paypal_done_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
            }
        });

        return view;
    }

}
