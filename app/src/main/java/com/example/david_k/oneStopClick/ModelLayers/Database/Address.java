package com.example.david_k.oneStopClick.ModelLayers.Database;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by David_K on 27/10/2017.
 */

public class Address implements Parcelable {
    public String addressName;
    public String deliveryAddress;
    public String city;
    public String state;
    public String firebaseKey;

    public static final String CHILD_SELECTED_ADDRESS = "selectedAddress";
    public static final String CHILD_ADDRESS_LIST = "addressList";

    public static final String COLUMN_NAME = "addressName";
    public static final String COLUMN_DELIVERY = "deliveryAddress";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_FIREBASE_KEY = "firebaseKey";
    public static final String COLUMN_STATE = "state";

    public Address() {

    }

    public Address (String addressName, String deliveryAddress, String city, String state){
        this.addressName = addressName;
        this.deliveryAddress = deliveryAddress;
        this.city = city;
        this.state = state;
    }

    protected Address(Parcel in) {
        addressName = in.readString();
        deliveryAddress = in.readString();
        city = in.readString();
        state = in.readString();
        firebaseKey = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(addressName);
        dest.writeString(deliveryAddress);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(firebaseKey);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    public String getAddressName() { return this.addressName; }
    public String getDeliveryAddress() { return this.deliveryAddress; }
    public String getCity() { return this.city; }
    public String getState() { return this.state; }
    public String getFirebaseKey() { return firebaseKey; }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress= deliveryAddress; }
    public void setCity(String city) {
        this.city= city;
    }
    public void setState(String state) {
        this.state= state;
    }
    public void setFirebaseKey(String firebaseKey){this.firebaseKey = firebaseKey; }
}
