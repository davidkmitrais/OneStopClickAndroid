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

    public Address() {

    }

    public Address (String addressName, String deliveryAddress, String city, String state){
        this.addressName = addressName;
        this.deliveryAddress = deliveryAddress;
        this.city = city;
        this.state = state;
    }

    public String getAddressName() { return this.addressName; }
    public String getDeliveryAddress() { return this.deliveryAddress; }
    public String getCity() { return this.city; }
    public String getState() { return this.state; }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress= deliveryAddress;
    }

    public void setCity(String city) {
        this.city= city;
    }

    public void setState(String state) {
        this.state= state;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.addressName);
        dest.writeString(this.deliveryAddress);
        dest.writeString(this.city);
        dest.writeString(this.state);
    }

    protected Address(Parcel in) {
        this.addressName = in.readString();
        this.deliveryAddress = in.readString();
        this.city = in.readString();
        this.state = in.readString();
    }

    public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
