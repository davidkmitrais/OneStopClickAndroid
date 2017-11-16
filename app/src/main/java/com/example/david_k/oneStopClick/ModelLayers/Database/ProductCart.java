package com.example.david_k.oneStopClick.ModelLayers.Database;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by David_K on 15/11/2017.
 */

public class ProductCart {

    public String productKey;
    public int orderQty;
    public String firebaseKey;

    public static final String COLUMN_ORDER_QTY = "orderQty";
    public static final String COLUMN_PRODUCT_KEY = "productKey";
    public ProductCart() {
    }

    public ProductCart(String productKey, int orderQty) {
        this.productKey = productKey;
        this.orderQty = orderQty;
    }

    public String getProductKey() {return this.productKey;}
    public int getOrderQty() {return this.orderQty;}

    public void setProductKey(String productKey){this.productKey = productKey;}
    public void setOrderQty(int orderQty){this.orderQty = orderQty;}
}
