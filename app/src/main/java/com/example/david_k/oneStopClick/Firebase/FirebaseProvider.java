package com.example.david_k.oneStopClick.Firebase;

import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by David_K on 13/11/2017.
 */

public class FirebaseProvider{

    private static final String TAG = "FirebaseProvider";
    private static FirebaseProvider firebaseProvider;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseStorage firebaseStorage;
    private FirebaseRemoteConfig firebaseRemoteConfig;

    private DatabaseReference productDBReference;
    private DatabaseReference productCartDBReference;
    private DatabaseReference addressCartDBReference;
    private DatabaseReference categoryDBReference;

    private StorageReference productPhotoStorageRef;
    private StorageReference categoryIconStorageRef;

    private ChildEventListener productChildEventListener = null;

    public static final String PRODUCT_TABLE = "products";
    public static final String PRODUCT_CART_TABLE = "product_carts";
    public static final String ADDRESS_TABLE = "address";
    public static final String CATEGORY_TABLE = "categories";

    public static final String PRODUCT_PHOTO_STORAGE = "product_photos";
    public static final String CATEGORY_PHOTO_STORAGE = "category_icons";

    public static FirebaseProvider getCurrentProvider() {

        if (null == firebaseProvider) {
            Log.d(TAG, "getCurrentProvider: Create FirebaseProvider class.");
            firebaseProvider = new FirebaseProvider();

            InitializeFirebaseComponent();
        }

        return firebaseProvider;
    }

    public ChildEventListener getProductChildEventListener(){
        return this.productChildEventListener;
    }

    public DatabaseReference getProductDBReference(){
        return this.productDBReference;
    }
    public DatabaseReference getProductCartDBReference(){
        return this.productCartDBReference;
    }
    public DatabaseReference getAddressDBReference(){
        return this.addressCartDBReference;
    }
    public DatabaseReference getCategoryDBReference(){
        return this.categoryDBReference;
    }


    public StorageReference getProductStorageReference(){
        return this.productPhotoStorageRef;
    }
    public StorageReference getCategoryIconStorageRef(){
        return this.categoryIconStorageRef;
    }

    private static void InitializeFirebaseComponent() {

        Log.d(TAG, "getCurrentProvider: Create FirebaseProvider class.");

        // Initialize Firebase
        firebaseProvider.firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseProvider.firebaseStorage = FirebaseStorage.getInstance();

        // Initialize Database References
        firebaseProvider.productDBReference = firebaseProvider.firebaseDatabase.getReference().child(PRODUCT_TABLE);
        firebaseProvider.productCartDBReference = firebaseProvider.firebaseDatabase.getReference().child(PRODUCT_CART_TABLE);
        firebaseProvider.addressCartDBReference = firebaseProvider.firebaseDatabase.getReference().child(ADDRESS_TABLE);
        firebaseProvider.categoryDBReference = firebaseProvider.firebaseDatabase.getReference().child(CATEGORY_TABLE);

        // Initialize Storage References
        firebaseProvider.productPhotoStorageRef = firebaseProvider.firebaseStorage.getReference().child(PRODUCT_PHOTO_STORAGE);
        firebaseProvider.categoryIconStorageRef = firebaseProvider.firebaseStorage.getReference().child(CATEGORY_PHOTO_STORAGE);
    }
}
