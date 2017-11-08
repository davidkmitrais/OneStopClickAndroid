package com.example.david_k.oneStopClick.Views.Activities.PaymentMethods;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.david_k.oneStopClick.ModelLayers.CenterRepository;
import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.Views.Fragments.PayPalPayment.PayPalLoginFragment;
import com.example.david_k.oneStopClick.R;

public class PaypalPaymentActivity extends AppCompatActivity {

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

        Product selectedProduct = CenterRepository.getCenterRepository().getSelectedProduct();
        int totalPrice = selectedProduct.getPrice() * selectedProduct.getOrderQty();
        TextView infoTotalText = (TextView) findViewById(R.id.paypal_total_info_text);
        infoTotalText.setText("My Total : " + String.valueOf(totalPrice) + " USD");

    }
}
