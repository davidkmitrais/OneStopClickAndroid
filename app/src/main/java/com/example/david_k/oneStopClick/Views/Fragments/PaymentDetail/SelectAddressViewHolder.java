package com.example.david_k.oneStopClick.Views.Fragments.PaymentDetail;

import android.media.Image;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.david_k.oneStopClick.ModelLayers.Database.Address;
import com.example.david_k.oneStopClick.R;

/**
 * Created by David_K on 31/10/2017.
 */

public class SelectAddressViewHolder extends RecyclerView.ViewHolder {

    public TextView addressName;
    public TextView streetName;
    public TextView cityStateName;
    public ImageButton removeButton;

    public SelectAddressViewHolder(View itemView) {
        super(itemView);

        addressName = (TextView) itemView.findViewById(R.id.address_name_view_holder);
        streetName = (TextView) itemView.findViewById(R.id.street_name_view_holder);
        cityStateName = (TextView) itemView.findViewById(R.id.city_state_view_holder);
        removeButton = (ImageButton) itemView.findViewById(R.id.remove_address_image_button);
    }

    public void configureWith(Address address) {
        addressName.setText(address.getAddressName());
        streetName.setText(address.getDeliveryAddress());
        cityStateName.setText(address.getCity() + ", " + address.getState());
    }
}
