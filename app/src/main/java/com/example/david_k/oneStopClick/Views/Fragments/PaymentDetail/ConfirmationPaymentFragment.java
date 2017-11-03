package com.example.david_k.oneStopClick.Views.Fragments.PaymentDetail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.david_k.oneStopClick.ModelLayers.CenterRepository;
import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.R;

public class ConfirmationPaymentFragment extends Fragment {

    TextView addressDeliveryText;
    TextView addressCityText;
    TextView addressStateText;

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

        Product selectedProduct = CenterRepository.getCenterRepository().getSelectedProduct();

        TextView productNameText = (TextView)rootView.findViewById(R.id.product_detail_name_confirmation);
        TextView productOrderQtyText = (TextView)rootView.findViewById(R.id.product_detail_orderQty_confirmation);
        TextView productTotalLabelText = (TextView)rootView.findViewById(R.id.product_detail_total_confirmation_label);
        TextView productTotalText = (TextView)rootView.findViewById(R.id.product_detail_total_confirmation);
        ImageView productImageConfirmation = (ImageView)rootView.findViewById(R.id.product_detail_confirmation_image);

        productNameText.setText(selectedProduct.getName());
        productOrderQtyText.setText(String.valueOf(selectedProduct.getOrderQty()));
        productTotalLabelText.setText("Total ( " + String.valueOf(selectedProduct.getOrderQty()) + " x " + String.valueOf(selectedProduct.getPrice()) + " ):");
        int totalPrice = selectedProduct.getOrderQty() * selectedProduct.getPrice();
        productTotalText.setText(String.valueOf(totalPrice) + " USD");
        productImageConfirmation.setImageResource(selectedProduct.getImageId());

    }
}
