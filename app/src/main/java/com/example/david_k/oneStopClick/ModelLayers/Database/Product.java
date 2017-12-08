package com.example.david_k.oneStopClick.ModelLayers.Database;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by David_K on 13/10/2017.
 */

public class Product implements Parcelable {
    public int id;
    public int price;
    public String name;
    public String imageName;
    public int orderQty;
    public int viewCount;
    public int likeCount;
    public String firebaseKey;

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_FIREBASE_KEY = "firebaseKey";
    public static final String COLUMN_IMAGE_NAME = "imageName";
    public static final String COLUMN_VIEW_COUNT = "viewCount";
    public static final String COLUMN_LIKE_COUNT = "likeCount";

    public Product() {
    }

    public Product(int id, String name, int price, String imageName, int viewCount, int likeCount) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.imageName = imageName;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
    }

    public int getId() {
        return id;
    }
    public String getName(){
        return name;
    }
    public int getPrice() {return price; }
    public int getOrderQty() {return orderQty; }
    public String getImageName() { return imageName; }
    public String getFirebaseKey() { return firebaseKey; }
    public int getViewCount() {
        return viewCount;
    }
    public int getLikeCount() {
        return likeCount;
    }

    public void setId(int id){
        this.id= id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPrice(int price){ this.price= price; }
    public void setImageName(String imageName){
        this.imageName= imageName;
    }
    public void setOrderQty(int orderQty) {this.orderQty = orderQty; }
    public void setFirebaseKey(String firebaseKey){this.firebaseKey = firebaseKey; }

    public ContentValues toValues() {
        ContentValues values = new ContentValues(5);

        values.put(COLUMN_ID, id);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_FIREBASE_KEY, firebaseKey);
        values.put(COLUMN_IMAGE_NAME, imageName);
        values.put(COLUMN_LIKE_COUNT, likeCount);
        values.put(COLUMN_VIEW_COUNT, viewCount);

        return values;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.price);
        dest.writeString(this.name);
        dest.writeString(this.imageName);
        dest.writeString(this.firebaseKey);
        dest.writeInt(this.viewCount);
        dest.writeInt(this.likeCount);
    }

    protected Product(Parcel in) {
        this.id = in.readInt();
        this.price = in.readInt();
        this.name = in.readString();
        this.imageName = in.readString();
        this.firebaseKey = in.readString();
        this.viewCount = in.readInt();
        this.likeCount = in.readInt();
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
