package com.example.david_k.oneStopClick.Views.Fragments.PayPalPayment;

import android.annotation.SuppressLint;
import android.app.Dialog;
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

import com.example.david_k.oneStopClick.Firebase.FirebaseProvider;
import com.example.david_k.oneStopClick.Helper.Constants;
import com.example.david_k.oneStopClick.Helper.FirebaseProviderHelper;
import com.example.david_k.oneStopClick.Helper.OnGetDataListener;
import com.example.david_k.oneStopClick.ModelLayers.Database.Address;
import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.ModelLayers.Database.ProductCart;
import com.example.david_k.oneStopClick.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaypalReviewFragment extends Fragment {

    FirebaseProviderHelper firebaseProviderHelper = new FirebaseProviderHelper();
    private int orderQty;
    private int price;

    public PaypalReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_paypal_review, container, false);

        TextView productDetailText = (TextView) rootView.findViewById(R.id.product_detail_info_paypal_review_text);
        TextView productPriceText = (TextView) rootView.findViewById(R.id.product_price_info_paypal_review_text);
        TextView addressDelivery = (TextView) rootView.findViewById(R.id.address_line1_detail_info_paypal_review_text);
        TextView addressCityState = (TextView) rootView.findViewById(R.id.address_line2_detail_info_paypal_review_text);

        ProductCart productCart = getActivity().getIntent().getExtras().getParcelable(Constants.productCartKey);
        orderQty = productCart != null ? productCart.getOrderQty() : 0;

        DatabaseReference productDBRef = FirebaseProvider.getCurrentProvider().getProductDBReference();
        Query productDetailQuery = productDBRef.orderByKey().equalTo(productCart.getProductKey());

        firebaseProviderHelper.getDataSnapshotOnceFromQuery(productDetailQuery, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                for (DataSnapshot dataItem: data.getChildren()) {
                    Product selectedProduct;
                    selectedProduct = dataItem.getValue(Product.class);

                    price = selectedProduct.getPrice();

                    String productDetailStr = selectedProduct.getName() + ": " + productCart.getOrderQty() + " pcs";
                    String productPriceStr = "$" +  productCart.getOrderQty() * selectedProduct.getPrice() + " USD";

                    productDetailText.setText(productDetailStr);
                    productPriceText.setText(productPriceStr);
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });

        DatabaseReference addressDbRef = FirebaseProvider.getCurrentProvider().getAddressDBReference()
                .child(Address.CHILD_SELECTED_ADDRESS);
        firebaseProviderHelper.getDataSnapshotOnceFromDBRef(addressDbRef, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                String addressKey = data.getValue(String.class);

                if(!addressKey.equals(Constants.notSetKey)) {
                    Query selectedAddressQuery = FirebaseProvider.getCurrentProvider().getAddressDBReference()
                            .child(Address.CHILD_ADDRESS_LIST).orderByKey().equalTo(addressKey);
                    firebaseProviderHelper.getDataSnapshotOnceFromQuery(selectedAddressQuery, new OnGetDataListener() {
                        @Override
                        public void onStart() {

                        }

                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onSuccess(DataSnapshot data) {
                            for (DataSnapshot dataItem: data.getChildren()) {
                                Address selectedAddress;
                                selectedAddress = dataItem.getValue(Address.class);

                                addressDelivery.setText(selectedAddress.getDeliveryAddress());
                                addressCityState.setText(selectedAddress.getCity() + ", " + selectedAddress.getState());
                            }
                        }

                        @Override
                        public void onFailed(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });

        Button payNow = (Button) rootView.findViewById(R.id.paypal_review_payNow_button);
        payNow.setOnClickListener(v -> {
            ReviewPaypalPaymentDialogFragment dialogFragment = new ReviewPaypalPaymentDialogFragment(orderQty, price);
            dialogFragment.show(getFragmentManager(), "PaypalReviewFragment");
        });

        return rootView;
    }

    @SuppressLint("ValidFragment")
    public static class ReviewPaypalPaymentDialogFragment extends DialogFragment {

        int orderQty;
        int price;

        @SuppressLint("ValidFragment")
        ReviewPaypalPaymentDialogFragment(int orderQty, int price){
            this.orderQty = orderQty;
            this.price = price;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            String productPriceStr = "$" +  orderQty * price + " USD";

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Do you want to procced the payment "+productPriceStr+" ?")
                    .setPositiveButton("Proceed", (dialog, which) -> {
                        Fragment fragment = null;
                        try {
                            fragment = PaypalDonePaymentFragment.class.newInstance();
                        } catch (java.lang.InstantiationException | IllegalAccessException e) {
                            e.printStackTrace();
                        }

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.paypal_frame, fragment).commit();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            return builder.create();
        }
    }
}
