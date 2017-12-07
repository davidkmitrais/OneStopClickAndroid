package com.example.david_k.oneStopClick.ModelLayers.Database;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by David_K on 7/12/2017.
 */

public class Category implements Parcelable {

    public String categoryName;
    public String imageName;
    public String firebaseKey;

    public static final String COLUMN_NAME = "categoryName";
    public static final String COLUMN_IMAGE_NAME = "imageName";

    public Category() {

    }

    public Category (String categoryName, String imageName){
        this.categoryName = categoryName;
        this.imageName = imageName;
    }

    protected Category(Parcel in) {
        categoryName = in.readString();
        imageName = in.readString();
        firebaseKey = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(categoryName);
        dest.writeString(imageName);
        dest.writeString(firebaseKey);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public String getCategoryName() { return this.categoryName; }
    public String getImageName() { return this.imageName; }
    public String getFirebaseKey() { return firebaseKey; }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public void setImageName(String imageName) { this.imageName = imageName; }
    public void setFirebaseKey(String firebaseKey){this.firebaseKey = firebaseKey; }
}
