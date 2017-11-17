package com.example.david_k.oneStopClick.Views.Fragments.PaymentDetail;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.david_k.oneStopClick.Firebase.FirebaseProvider;
import com.example.david_k.oneStopClick.Helper.CenterRepositoryHelper;
import com.example.david_k.oneStopClick.Helper.CustomItemClickListener;
import com.example.david_k.oneStopClick.Helper.FirebaseProviderHelper;
import com.example.david_k.oneStopClick.Helper.OnGetDataListener;
import com.example.david_k.oneStopClick.ModelLayers.CenterRepository;
import com.example.david_k.oneStopClick.ModelLayers.Database.Address;
import com.example.david_k.oneStopClick.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

/**
 * Created by David_K on 26/10/2017.
 */

public class SelectAddressViewAdapter extends RecyclerView.Adapter<SelectAddressViewHolder> {

    private Context context;
    private List<Address> addressList;
    CustomItemClickListener listener;
    private FirebaseProviderHelper firebaseHelper = new FirebaseProviderHelper();

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
        Address selectedAddress = new CenterRepositoryHelper().setDummySelecetedAddress();//CenterRepository.getCenterRepository().getSelectedAddress();

        DatabaseReference selectedAdrressDBRef = FirebaseProvider.getCurrentProvider().getAddressDBReference().child(Address.CHILD_SELECTED_ADDRESS);
        firebaseHelper.getDataSnapshotOnceFromDBRef(selectedAdrressDBRef, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                String addressKey = data.getValue(String.class);
                boolean isSelected = address.getFirebaseKey().equals(addressKey);
                holder.configureWith(address, isSelected);
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });

        holder.removeButton.setOnClickListener(v -> {
            Address deleteAddress = addressList.get(position);
            addressList.remove(position);
            notifyItemRemoved(position);
            notifyItemChanged(position, addressList.size());

            firebaseHelper.deleteAddressFromList(deleteAddress);
        });
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }
}
