package com.example.david_k.oneStopClick.Views.Fragments.PaymentDetail;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.david_k.oneStopClick.Firebase.FirebaseProvider;
import com.example.david_k.oneStopClick.Helper.Constants;
import com.example.david_k.oneStopClick.Helper.FirebaseProviderHelper;
import com.example.david_k.oneStopClick.Helper.OnGetDataListener;
import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.ModelLayers.Database.ProductCart;
import com.example.david_k.oneStopClick.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class ConfirmationPaymentFragment extends Fragment {

    SharedPreferences selectedAddressSharedPreferences;
    private FirebaseProviderHelper firebaseProviderHelper = new FirebaseProviderHelper();

    public ConfirmationPaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_payment_detail_confirmation, container, false);

        setupUI(rootView);

        return rootView;
    }

    private void setupUI(View rootView) {

        ProductCart productCart = getActivity().getIntent().getExtras().getParcelable(Constants.productCartKey);

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

                    TextView productNameText = (TextView)rootView.findViewById(R.id.product_detail_name_confirmation);
                    TextView productOrderQtyText = (TextView)rootView.findViewById(R.id.product_detail_orderQty_confirmation);
                    TextView productTotalLabelText = (TextView)rootView.findViewById(R.id.product_detail_total_confirmation_label);
                    TextView productTotalText = (TextView)rootView.findViewById(R.id.product_detail_total_confirmation);
                    ImageView productImageConfirmation = (ImageView)rootView.findViewById(R.id.product_detail_confirmation_image);

                    productNameText.setText(selectedProduct.getName());
                    productOrderQtyText.setText(String.valueOf(productCart.getOrderQty()));
                    productTotalLabelText.setText("Total ( " + String.valueOf(productCart.getOrderQty()) + " x " + String.valueOf(selectedProduct.getPrice()) + " ):");
                    int totalPrice = productCart.getOrderQty() * selectedProduct.getPrice();
                    productTotalText.setText(String.valueOf(totalPrice) + " USD");
                    firebaseProviderHelper.setupProductPhoto(getContext(), selectedProduct.getImageName(), productImageConfirmation);
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
    }
}
