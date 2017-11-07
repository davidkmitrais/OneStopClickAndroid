package com.example.david_k.oneStopClick.Views.Activities.PaymentMethods;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.david_k.oneStopClick.ModelLayers.CenterRepository;
import com.example.david_k.oneStopClick.ModelLayers.Database.Address;
import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.R;

public class CashOnDeliveryPaymentActivity extends AppCompatActivity {

    private final String TAG = "CODPaymentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_on_delivery_payment);

        setupUI();
    }

    private void setupUI() {

        Product selectedProduct = CenterRepository.getCenterRepository().getSelectedProduct();
        Address selectedAddress = CenterRepository.getCenterRepository().getSelectedAddress();

        TextView productNameText = (TextView) findViewById(R.id.product_detail_name_cod);
        TextView productOrderQtyText = (TextView) findViewById(R.id.product_detail_orderQty_cod);
        TextView productTotalLabelText = (TextView) findViewById(R.id.product_detail_total_cod_label);
        TextView productTotalText = (TextView) findViewById(R.id.product_detail_total_cod);
        ImageView productImageConfirmation = (ImageView) findViewById(R.id.product_detail_cod_image);

        productNameText.setText(selectedProduct.getName());
        productOrderQtyText.setText(String.valueOf(selectedProduct.getOrderQty()));
        productTotalLabelText.setText("Total ( " + String.valueOf(selectedProduct.getOrderQty()) + " x " + String.valueOf(selectedProduct.getPrice()) + " ):");
        int totalPrice = selectedProduct.getOrderQty() * selectedProduct.getPrice();
        productTotalText.setText(String.valueOf(totalPrice) + " USD");
        productImageConfirmation.setImageResource(selectedProduct.getImageId());

        TextView addressDeliveryText = (TextView) findViewById(R.id.address_detail_delivery_cod);
        TextView addressCityText = (TextView) findViewById(R.id.address_detail_city_cod);
        TextView addressStateText = (TextView) findViewById(R.id.address_detail_state_cod);
        Button confirmButton = (Button) findViewById(R.id.continue_to_payment_cod_button);

        addressDeliveryText.setText(selectedAddress.getDeliveryAddress());
        addressCityText.setText(selectedAddress.getCity());
        addressStateText.setText(selectedAddress.getState());

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: save to repo " + selectedProduct.getName());

                Toast.makeText(v.getContext(), "added to order", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
