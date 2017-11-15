package com.example.david_k.oneStopClick.ModelLayers.Database;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.david_k.oneStopClick.Database.ProductTable;

/**
 * Created by David_K on 13/10/2017.
 */

public class Product implements Parcelable {
    public int id;
    public int price;
    public String name;
    public String imageName;
    public int imageId;
    public int orderQty;
    public String firebaseKey;

    public Product() {
    }

    public Product(int id, String name, int price, int imageId, String imageName) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.imageId = imageId;
        this.imageName = imageName;
    }

    public int getId() {
        return id;
    }
    public String getName(){
        return name;
    }
    public int getImageId() { return imageId; }
    public int getPrice() {return price; }
    public int getOrderQty() {return orderQty; }
    public String getImageName() { return imageName; }
    public String getFirebaseKey() { return firebaseKey; }

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
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
    public void setOrderQty(int orderQty) {this.orderQty = orderQty; }
    public void setFirebaseKey(String firebaseKey){this.firebaseKey = firebaseKey; }

    public ContentValues toValues() {
        ContentValues values = new ContentValues(4);

        values.put(ProductTable.COLUMN_ID, id);
        values.put(ProductTable.COLUMN_NAME, name);
        values.put(ProductTable.COLUMN_PRICE, price);
        values.put(ProductTable.COLUMN_IMAGE_ID, imageId);
        values.put(ProductTable.COLUMN_IMAGE_NAME, imageName);

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
        dest.writeInt(this.imageId);
        dest.writeInt(this.orderQty);
    }

    protected Product(Parcel in) {
        this.id = in.readInt();
        this.price = in.readInt();
        this.name = in.readString();
        this.imageName = in.readString();
        this.imageId = in.readInt();
        this.orderQty = in.readInt();
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
