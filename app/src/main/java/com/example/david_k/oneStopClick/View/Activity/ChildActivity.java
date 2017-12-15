package com.example.david_k.oneStopClick.View.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.david_k.oneStopClick.Helper.Constants;
import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.R;
import com.example.david_k.oneStopClick.View.Fragment.ProductDetailFragment;

public class ChildActivity extends AppCompatActivity {

    private static final String TAG = "ChildActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        // get fragment
        Fragment fragment = getFragmentByActivityKey();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.child_frame, fragment).commit();
    }

    private Fragment getFragmentByActivityKey(){

        String activityKey = this.getIntent().getExtras().getString(Constants.childPageActivityKey);
        Fragment fragment = null;

        switch (activityKey){
            case "ProductDetail":
                fragment = setupProductDetailFragment();
                break;
        }

        return fragment;
    }

    private Fragment setupProductDetailFragment() {
        Fragment fragment = null;

        try{
            getSupportActionBar().setTitle("Product Detail");

            Product product = this.getIntent().getExtras().getParcelable(Constants.productKey);
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.productKey, product);

            fragment = ProductDetailFragment.class.newInstance();
            fragment.setArguments(bundle);
        }
        catch (Exception ex){
            Log.e(TAG, "setupProductDetailFragment: " + ex.getLocalizedMessage(), null);
        }

        return fragment;
    }
}
