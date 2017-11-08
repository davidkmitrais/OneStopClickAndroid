package com.example.david_k.oneStopClick.Views.Fragments.PayPalPayment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.david_k.oneStopClick.Helper.Helper;
import com.example.david_k.oneStopClick.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class PayPalLoginFragment extends Fragment {

    EditText emailText;
    EditText passwordText;

    public PayPalLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_paypal_login, container, false);

        emailText = (EditText) rootView.findViewById(R.id.paypay_login_email_input);
        passwordText = (EditText) rootView.findViewById(R.id.paypay_login_password_input);
        Button loginButton = (Button) rootView.findViewById(R.id.paypal_login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateLoginInputText()){
                    Toast.makeText(getActivity(), "UN - Success Login", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "Success Login", Toast.LENGTH_SHORT).show();

                    Fragment fragment = null;
                    try {
                        fragment = PaypalReviewFragment.class.newInstance();
                    } catch (java.lang.InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.paypal_frame, fragment).commit();

                }
            }
        });

        return rootView;
    }


    private boolean validateLoginInputText(){
        if (Helper.editTextIsEmptyOrNull(emailText)
                || Helper.editTextIsEmptyOrNull(passwordText)){

            return false;
        }

        return true;
    }
}
