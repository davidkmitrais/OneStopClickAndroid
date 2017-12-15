package com.example.david_k.oneStopClick.ModelLayers.Database;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by David_K on 15/11/2017.
 */

public class ProductLike implements Parcelable {

    public String productKey;
    public Boolean isLiked;
    public String firebaseKey;
    public String userId;

    public static final String COLUMN_IS_LIKED = "isLiked";
    public static final String COLUMN_PRODUCT_KEY = "productKey";
    public static final String COLUMN_UID = "userId";

    public ProductLike() {
    }

    public ProductLike(String productKey, Boolean isLiked, String userId) {
        this.productKey = productKey;
        this.isLiked = isLiked;
        this.userId = userId;
    }

    public String getProductKey() {return this.productKey;}
    public Boolean getIsLiked() {return this.isLiked;}
    public String getFirebaseKey() { return firebaseKey; }
    public String getUserId() { return userId; }

    public void setProductKey(String productKey){this.productKey = productKey;}
    public void setIsLiked(boolean isLiked){this.isLiked= isLiked;}
    public void setFirebaseKey(String firebaseKey){this.firebaseKey = firebaseKey; }
    public void setUserId(String userId){this.userId = userId; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productKey);
        dest.writeByte((byte) (isLiked? 1 : 0));
        dest.writeString(firebaseKey);
        dest.writeString(userId);
    }

    protected ProductLike(Parcel in) {
        productKey = in.readString();
        isLiked = in.readByte() != 0;
        firebaseKey = in.readString();
        userId = in.readString();
    }

    public static final Creator<ProductLike> CREATOR = new Creator<ProductLike>() {
        @Override
        public ProductLike createFromParcel(Parcel in) {
            return new ProductLike(in);
        }

        @Override
        public ProductLike[] newArray(int size) {
            return new ProductLike[size];
        }
    };
}
