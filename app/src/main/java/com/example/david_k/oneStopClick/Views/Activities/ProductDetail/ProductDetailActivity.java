package com.example.david_k.oneStopClick.Views.Activities.ProductDetail;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.david_k.oneStopClick.Firebase.FirebaseProvider;
import com.example.david_k.oneStopClick.Helper.Constants;
import com.example.david_k.oneStopClick.Helper.FirebaseProviderHelper;
import com.example.david_k.oneStopClick.Helper.OnGetDataListener;
import com.example.david_k.oneStopClick.MainActivity;
import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.ModelLayers.Database.ProductCart;
import com.example.david_k.oneStopClick.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;

public class ProductDetailActivity extends AppCompatActivity {

    private static final String TAG = "ProductDetailActivity";
    private Product product;
    private int numItemOrdered;
    private String productFirebaseKey;

    private ImageView productImage;
    private TextView nameTextView;
    private TextView  priceTextView;
    private ImageButton addToCartButton;
    private ImageButton plusItemCartButton;
    private ImageButton minusItemCartButton;
    private TextView itemOrderedEditText;
    private CartAddedDialogFragment cartAddedDialog;
    private FirebaseProviderHelper firebaseProviderHelper = new FirebaseProviderHelper();

    private boolean isNewCart;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        product = getIntent().getExtras().getParcelable(Constants.productKey);
        isNewCart = true;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        setupUI();

        productFirebaseKey = product.getFirebaseKey();
        setOrderQtyByProductKey(productFirebaseKey);

        configureUI();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupUI() {
        productImage = (ImageView) findViewById(R.id.product_detail_image);
        nameTextView = (TextView) findViewById(R.id.product_detail_name);
        priceTextView = (TextView) findViewById(R.id.product_detail_price);

        itemOrderedEditText = (TextView) findViewById(R.id.order_item_text);
        plusItemCartButton = (ImageButton) findViewById(R.id.plus_cart_button);
        minusItemCartButton = (ImageButton) findViewById(R.id.minus_cart_button);
        addToCartButton = (ImageButton) findViewById(R.id.product_add_to_cart_button);

        plusItemCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numItemOrdered++;

                setTextForItemOrdered();
            }
        });

        minusItemCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numItemOrdered--;

                setTextForItemOrdered();
            }
        });

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "added to cart", Toast.LENGTH_SHORT).show();

                // Add / Update Cart Order
                String userId = firebaseUser.getUid();
                firebaseProviderHelper.setOrderQtyForProductCart(isNewCart, numItemOrdered, productFirebaseKey, userId);

                cartAddedDialog = new CartAddedDialogFragment();
                cartAddedDialog.show(getSupportFragmentManager(), "CartAddedDialogFragment");
            }
        });
    }

    public static class CartAddedDialogFragment extends DialogFragment{

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("The product has been added into the cart. Please process the payment.")
                    .setPositiveButton("Pay now.", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            goToCartFragment();
                        }
                    })
                    .setNegativeButton("Continue Shopping", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getActivity().onBackPressed();
                        }
                    });

            return builder.create();
        }

        private void goToCartFragment(){

            Bundle bundle = new Bundle();
            bundle.putBoolean(Constants.mainActivityGoToCartFragmentBool, true);

            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtras(bundle);

            startActivity(intent);
        }
    }

    private void configureUI() {

        firebaseProviderHelper.setupProductPhoto(this, product.getImageName(), productImage);
        nameTextView.setText(product.name);
        priceTextView.setText(String.valueOf(product.price));
    }

    private void setTextForItemOrdered(){
        if (numItemOrdered < 0) {
            numItemOrdered = 0;
        }

        itemOrderedEditText.setText(String.valueOf(numItemOrdered));
    }

    public void setOrderQtyByProductKey(String productKey){

        String userId = firebaseUser.getUid();
        Query query = FirebaseProvider.getCurrentProvider().getProductCartDBReference()
                .child(userId)
                .orderByChild(ProductCart.COLUMN_PRODUCT_KEY).equalTo(productKey);

        firebaseProviderHelper.getDataSnapshotOnceFromQuery(query, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                if (!data.hasChildren()){
                    numItemOrdered = 0;
                    isNewCart = true;
                }

                for (DataSnapshot itemData : data.getChildren()) {
                    numItemOrdered = itemData.child(ProductCart.COLUMN_ORDER_QTY).getValue(int.class);
                    isNewCart = false;
                    break;
                }

                setTextForItemOrdered();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Log.w(TAG, "Failed to get product cart : " + databaseError.getMessage(), null);
            }
        });

    }
}
