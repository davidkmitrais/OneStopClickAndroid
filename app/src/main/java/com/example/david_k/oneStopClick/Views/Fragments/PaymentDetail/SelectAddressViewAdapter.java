package com.example.david_k.oneStopClick.Views.Fragments.PaymentDetail;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.david_k.oneStopClick.Helper.CustomItemClickListener;
import com.example.david_k.oneStopClick.ModelLayers.CenterRepository;
import com.example.david_k.oneStopClick.ModelLayers.Database.Address;
import com.example.david_k.oneStopClick.R;

import java.util.List;

/**
 * Created by David_K on 26/10/2017.
 */

public class SelectAddressViewAdapter extends RecyclerView.Adapter<SelectAddressViewHolder> {

    private Context context;
    private List<Address> addressList;
    CustomItemClickListener listener;

    public SelectAddressViewAdapter(Context context, List<Address> addressList, CustomItemClickListener listener){
        this.context = context;
        this.addressList = addressList;
        this.listener = listener;
    }

    @Override
    public SelectAddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View selectAddressView = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_cell, parent, false);

        final SelectAddressViewHolder holder = new SelectAddressViewHolder(selectAddressView);

        selectAddressView.setOnClickListener(v -> listener.onItemClick(v, holder.getAdapterPosition()));

        return holder;
    }

    @Override
    public void onBindViewHolder(SelectAddressViewHolder holder, int position) {
        Address address = addressList.get(position);
        Address selectedAddress = CenterRepository.getCenterRepository().getSelectedAddress();
        boolean isSelected = selectedAddress != null && address.getAddressName() == selectedAddress.getAddressName();
        holder.configureWith(address, isSelected);

        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressList.remove(position);
                notifyItemRemoved(position);
                notifyItemChanged(position, addressList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }
}
