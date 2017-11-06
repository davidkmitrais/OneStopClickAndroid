package com.example.david_k.oneStopClick.ModelLayers.Enums;

/**
 * Created by David_K on 11/10/2017.
 */

import com.example.david_k.oneStopClick.R;

public enum PaymentMethod {

    COD(R.id.cash_on_delivery_radio_button),
    CC(R.id.credit_card_radio_button),
    PAYPAL(R.id.paypal_radio_button);

    private int itemId;
    PaymentMethod(int id) {
        this.itemId = id;
    }

    public int getItemId() {
        return itemId;
    }

    public static PaymentMethod fromViewId(int viewId){
        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            if (paymentMethod.getItemId() == viewId){
                return paymentMethod;
            }
        }
        throw new IllegalStateException("Cannot find viewType");
    }
}
