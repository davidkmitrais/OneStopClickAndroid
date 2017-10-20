package com.example.david_k.oneStopClick.ModelLayers.Database;

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
    public int imageId;


    public Product(int id, String name, int price, int imageId) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.imageId = imageId;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
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
    }

    protected Product(Parcel in) {
        this.id = in.readInt();
        this.price = in.readInt();
        this.name = in.readString();
        this.imageName = in.readString();
        this.imageId = in.readInt();
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
