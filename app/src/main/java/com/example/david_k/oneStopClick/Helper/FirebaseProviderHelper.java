package com.example.david_k.oneStopClick.Helper;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.david_k.oneStopClick.Firebase.FirebaseProvider;
import com.example.david_k.oneStopClick.ModelLayers.Database.ProductCart;
import com.firebase.ui.storage.images.FirebaseImageLoader;
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

    public void setupProductPhoto(Context context, String imageName, ImageView imageView){
        StorageReference itemPhotoRef = FirebaseProvider.getCurrentProvider().getProductStorageReference()
                .child(imageName + ".jpg");

        Glide.with(context).using(new FirebaseImageLoader())
                .load(itemPhotoRef)
                .into(imageView);
    }

    public void setOrderQtyForProductCart(boolean isAdded, int numItemOrdered, String productFirebaseKey) {

        DatabaseReference productCartRef = FirebaseProvider.getCurrentProvider().getProductCartDBReference();

        if (isAdded) {
            // Add new Product Cart
            addNewOrderQtyForProductCart(productCartRef, numItemOrdered, productFirebaseKey);
        } else {
            // Update Product Cart
            updateOrderQtyForProductCart(productCartRef, numItemOrdered, productFirebaseKey);
        }
    }

    private void addNewOrderQtyForProductCart(DatabaseReference productCartRef, int numItemOrdered, String productFirebaseKey) {
        String newKey = productCartRef.push().getKey();
        ProductCart newProductCart = new ProductCart(productFirebaseKey, numItemOrdered);
        productCartRef.child(newKey).setValue(newProductCart);
    }

    private void updateOrderQtyForProductCart(DatabaseReference productCartRef, int numItemOrdered, String productFirebaseKey) {

        Query query = productCartRef.orderByChild(ProductCart.COLUMN_PRODUCT_KEY).equalTo(productFirebaseKey);

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
}
