package com.example.david_k.oneStopClick.ModelLayers.Database;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by David_K on 15/11/2017.
 */

public class ProductCart implements Parcelable {

    public String productKey;
    public int orderQty;
    public String firebaseKey;
    public String userId;
    public String productKeyUserId = productKey + " " + userId;

    public static final String COLUMN_ORDER_QTY = "orderQty";
    public static final String COLUMN_PRODUCT_KEY = "productKey";
    public static final String COLUMN_UID = "userId";
    public static final String COLUMN_PRODUCT_KEY_UID = COLUMN_PRODUCT_KEY + "_" + COLUMN_UID;
    public ProductCart() {
    }

    public ProductCart(String productKey, int orderQty, String userId) {
        this.productKey = productKey;
        this.orderQty = orderQty;
        this.userId = userId;
    }

    public String getProductKey() {return this.productKey;}
    public int getOrderQty() {return this.orderQty;}
    public String getFirebaseKey() { return firebaseKey; }
    public String getUserId() { return userId; }
    public String getProductKeyUserId() { return productKeyUserId; }

    public void setProductKey(String productKey){this.productKey = productKey;}
    public void setOrderQty(int orderQty){this.orderQty = orderQty;}
    public void setFirebaseKey(String firebaseKey){this.firebaseKey = firebaseKey; }
    public void setUserId(String userId){this.userId = userId; }
    public void setProductKeyUserId() {this.productKeyUserId = this.productKey + " " + this.userId; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productKey);
        dest.writeInt(orderQty);
        dest.writeString(firebaseKey);
        dest.writeString(userId);
        dest.writeString(productKeyUserId);
    }

    protected ProductCart(Parcel in) {
        productKey = in.readString();
        orderQty = in.readInt();
        firebaseKey = in.readString();
        userId = in.readString();
        productKeyUserId = in.readString();
    }

    public static final Creator<ProductCart> CREATOR = new Creator<ProductCart>() {
        @Override
        public ProductCart createFromParcel(Parcel in) {
            return new ProductCart(in);
        }

        @Override
        public ProductCart[] newArray(int size) {
            return new ProductCart[size];
        }
    };
}
