package com.example.david_k.oneStopClick.Helper;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.david_k.oneStopClick.Firebase.FirebaseProvider;
import com.example.david_k.oneStopClick.ModelLayers.Database.Address;
import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.ModelLayers.Database.ProductCart;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

/**
 * Created by David_K on 15/11/2017.
 */

public class FirebaseProviderHelper {

    private static final String TAG = "FirebaseProviderHelper";

    public void getDataSnapshotOnceFromQuery(Query query, OnGetDataListener listener){

        listener.onStart();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "Success to getDataSnapshotOnceFromQuery : " + dataSnapshot.getKey(), null);
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to getDataSnapshotOnceFromQuery : " + databaseError.getMessage(), null);
                listener.onFailed(databaseError);
            }
        });
    }

    public void getDataSnapshotOnceFromDBRef(DatabaseReference databaseReference, OnGetDataListener listener){

        listener.onStart();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "Success to getDataSnapshotOnceFromQuery : " + dataSnapshot.getKey(), null);
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to getDataSnapshotOnceFromQuery : " + databaseError.getMessage(), null);
                listener.onFailed(databaseError);
            }
        });
    }

    //region PRODUCT and CART

    public void setupProductPhoto(Context context, String imageName, ImageView imageView){
        StorageReference itemPhotoRef = FirebaseProvider.getCurrentProvider().getProductStorageReference()
                .child(imageName + ".jpg");

        GlideApp.with(context)
                .load(itemPhotoRef)
                .into(imageView);
    }

    public void setOrderQtyForProductCart(boolean isAdded, int numItemOrdered, String productFirebaseKey, String userId) {

        DatabaseReference productCartRef = FirebaseProvider.getCurrentProvider().getProductCartDBReference();

        if (isAdded) {
            // Add new Product Cart
            addNewOrderQtyForProductCart(productCartRef, numItemOrdered, productFirebaseKey, userId);
        } else {
            // Update Product Cart
            updateOrderQtyForProductCart(productCartRef, numItemOrdered, productFirebaseKey, userId);
        }
    }

    private void addNewOrderQtyForProductCart(DatabaseReference productCartRef, int numItemOrdered, String productFirebaseKey, String userId) {
        //String newKey = productCartRef.push().getKey();
        ProductCart newProductCart = new ProductCart(productFirebaseKey, numItemOrdered, userId);
        newProductCart.setProductKeyUserId();

        String newKey = productCartRef.child(userId).push().getKey();

        productCartRef.child(userId).child(newKey).setValue(newProductCart);
    }

    private void updateOrderQtyForProductCart(DatabaseReference productCartRef, int numItemOrdered, String productFirebaseKey, String userId) {

        Query query = productCartRef.child(userId).orderByChild(ProductCart.COLUMN_PRODUCT_KEY).equalTo(productFirebaseKey);

        getDataSnapshotOnceFromQuery(query, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                for (DataSnapshot itemData : data.getChildren()) {
                    itemData.getRef().child(ProductCart.COLUMN_ORDER_QTY).setValue(numItemOrdered);
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
    }

    //endregion

    //region address

    public void addNewAddressList(Address newAddress, String userId) {

        DatabaseReference addressListDBRef = FirebaseProvider.getCurrentProvider().getAddressDBReference()
                .child(Address.CHILD_ADDRESS_LIST).child(userId);

        String newKey = addressListDBRef.push().getKey();
        addressListDBRef.child(newKey).setValue(newAddress);
    }

    public void deleteAddressFromList(Address address, String userId) {

        FirebaseProvider.getCurrentProvider().getAddressDBReference()
                .child(Address.CHILD_ADDRESS_LIST).child(userId).child(address.getFirebaseKey()).getRef()
                .removeValue()
                .addOnSuccessListener(aVoid -> Log.d(TAG, "deleteAddressFromList: Delete address : " + address.getFirebaseKey()))
                .addOnFailureListener(aVoid -> Log.w(TAG, "deleteAddressFromList: Delete fail" + address.getFirebaseKey()));
    }

    public void updateSelectedAddress(String addressKey, String userId) {

        FirebaseProvider.getCurrentProvider().getAddressDBReference()
                .child(Address.CHILD_SELECTED_ADDRESS).child(userId).setValue(addressKey)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "addSelectedAddress: Success update selected address : " + addressKey))
                .addOnFailureListener(aVoid -> Log.w(TAG, "addSelectedAddress: Fail update selected address" + addressKey));
    }

    //endregion

    //region Category

    public void setupCategoryPhoto(Context context, String imageName, ImageView imageView){
        StorageReference itemPhotoRef = FirebaseProvider.getCurrentProvider().getCategoryIconStorageRef()
                .child(imageName + ".png");

        GlideApp.with(context)
                .load(itemPhotoRef)
                .into(imageView);
    }

    //endregion

    // region Firebase User

    public String getUserId(){
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    //endregion
}
