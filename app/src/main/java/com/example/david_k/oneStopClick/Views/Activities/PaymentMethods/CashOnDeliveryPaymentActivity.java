package com.example.david_k.oneStopClick.Views.Activities.PaymentMethods;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class CashOnDeliveryPaymentActivity extends AppCompatActivity {

    private final String TAG = "CODPaymentActivity";
    ProductCart productCartFromBundle;
    FirebaseProviderHelper firebaseProviderHelper = new FirebaseProviderHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_on_delivery_payment);

        productCartFromBundle = this.getIntent().getExtras().getParcelable(Constants.productCartKey);

        setupUI();
    }

    private void setupUI() {

        Context context = this;

        TextView productNameText = (TextView) findViewById(R.id.product_detail_name_cod);
        TextView productOrderQtyText = (TextView) findViewById(R.id.product_detail_orderQty_cod);
        TextView productTotalLabelText = (TextView) findViewById(R.id.product_detail_total_cod_label);
        TextView productTotalText = (TextView) findViewById(R.id.product_detail_total_cod);
        ImageView productImageConfirmation = (ImageView) findViewById(R.id.product_detail_cod_image);

        TextView addressDeliveryText = (TextView) findViewById(R.id.address_detail_delivery_cod);
        TextView addressCityText = (TextView) findViewById(R.id.address_detail_city_cod);
        TextView addressStateText = (TextView) findViewById(R.id.address_detail_state_cod);
        Button confirmButton = (Button) findViewById(R.id.continue_to_payment_cod_button);

        ProductCart productCart = productCartFromBundle;

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

                    productNameText.setText(selectedProduct.getName());
                    productOrderQtyText.setText(String.valueOf(productCart.getOrderQty()));
                    productTotalLabelText.setText("Total ( " + String.valueOf(productCart.getOrderQty()) + " x " + String.valueOf(selectedProduct.getPrice()) + " ):");
                    int totalPrice = productCart.getOrderQty() * selectedProduct.getPrice();
                    productTotalText.setText(String.valueOf(totalPrice) + " USD");
                    firebaseProviderHelper.setupProductPhoto(context, selectedProduct.getImageName(), productImageConfirmation);

                    confirmButton.setOnClickListener(v -> {
                        Log.d(TAG, "onClick: save to repo " + selectedProduct.getName());

                        Toast.makeText(v.getContext(), "added to order", Toast.LENGTH_SHORT).show();
                    });
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

                if(!addressKey.equals(Constants.notSetKey)){
                    Query selectedAddressQuery = FirebaseProvider.getCurrentProvider().getAddressDBReference()
                            .child(Address.CHILD_ADDRESS_LIST).orderByKey().equalTo(addressKey);
                    firebaseProviderHelper.getDataSnapshotOnceFromQuery(selectedAddressQuery, new OnGetDataListener() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onSuccess(DataSnapshot data) {
                            for (DataSnapshot dataItem: data.getChildren()) {
                                Address selectedAddress;
                                selectedAddress = dataItem.getValue(Address.class);

                                addressDeliveryText.setText(selectedAddress.getDeliveryAddress());
                                addressCityText.setText(selectedAddress.getCity());
                                addressStateText.setText(selectedAddress.getState());
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
    }
}
