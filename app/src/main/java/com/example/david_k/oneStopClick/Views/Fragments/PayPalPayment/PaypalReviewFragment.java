package com.example.david_k.oneStopClick.Views.Fragments.PayPalPayment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.david_k.oneStopClick.Helper.CenterRepositoryHelper;
import com.example.david_k.oneStopClick.ModelLayers.CenterRepository;
import com.example.david_k.oneStopClick.ModelLayers.Database.Address;
import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaypalReviewFragment extends Fragment {


    public PaypalReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_paypal_review, container, false);

        Product product = new CenterRepositoryHelper().setDummySelecetedProduct();//CenterRepository.getCenterRepository().getSelectedProduct();

        String productDetailStr = product.getName() + ": " + product.getOrderQty() + " pcs";
        String productPriceStr = "$" +  product.getOrderQty() * product.getPrice() + " USD";

        TextView productDetailText = (TextView) rootView.findViewById(R.id.product_detail_info_paypal_review_text);
        TextView productPriceText = (TextView) rootView.findViewById(R.id.product_price_info_paypal_review_text);

        productDetailText.setText(productDetailStr);
        productPriceText.setText(productPriceStr);

        Address selectedAddress = new CenterRepositoryHelper().setDummySelecetedAddress();//CenterRepository.getCenterRepository().getSelectedAddress();

        TextView addressDelivery = (TextView) rootView.findViewById(R.id.address_line1_detail_info_paypal_review_text);
        TextView addressCityState = (TextView) rootView.findViewById(R.id.address_line2_detail_info_paypal_review_text);

        addressDelivery.setText(selectedAddress.getDeliveryAddress());
        addressCityState.setText(selectedAddress.getCity() + ", " + selectedAddress.getState());

        Button payNow = (Button) rootView.findViewById(R.id.paypal_review_payNow_button);
        payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReviewPaypalPaymentDialogFragment dialogFragment = new ReviewPaypalPaymentDialogFragment();
                dialogFragment.show(getFragmentManager(), "PaypalReviewFragment");
            }
        });

        return rootView;
    }

    public static class ReviewPaypalPaymentDialogFragment extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            Product product = new CenterRepositoryHelper().setDummySelecetedProduct();//CenterRepository.getCenterRepository().getSelectedProduct();
            String productPriceStr = "$" +  product.getOrderQty() * product.getPrice() + " USD";

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Do you want to procced the payment "+productPriceStr+" ?")
                    .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Fragment fragment = null;
                            try {
                                fragment = PaypalDonePaymentFragment.class.newInstance();
                            } catch (java.lang.InstantiationException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }

                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.paypal_frame, fragment).commit();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            return builder.create();
        }
    }
}
