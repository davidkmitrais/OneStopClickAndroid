package com.example.david_k.oneStopClick.Views.Activities.PaymentMethods;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.david_k.oneStopClick.Firebase.FirebaseProvider;
import com.example.david_k.oneStopClick.Helper.Constants;
import com.example.david_k.oneStopClick.Helper.FirebaseProviderHelper;
import com.example.david_k.oneStopClick.Helper.OnGetDataListener;
import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.ModelLayers.Database.ProductCart;
import com.example.david_k.oneStopClick.R;
import com.example.david_k.oneStopClick.Views.Fragments.PayPalPayment.PayPalLoginFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class PaypalPaymentActivity extends AppCompatActivity {

    FirebaseProviderHelper firebaseProviderHelper = new FirebaseProviderHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal_payment);

        Fragment fragment = null;
        try {
            fragment = PayPalLoginFragment.class.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.paypal_frame, fragment).commit();

        ProductCart productCart = this.getIntent().getExtras().getParcelable(Constants.productCartKey);
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

                    int totalPrice = selectedProduct.getPrice() * productCart.getOrderQty();
                    TextView infoTotalText = (TextView) findViewById(R.id.paypal_total_info_text);
                    infoTotalText.setText("My Total : " + String.valueOf(totalPrice) + " USD");
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
    }
}
