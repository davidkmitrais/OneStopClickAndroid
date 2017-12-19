package com.example.david_k.oneStopClick.View.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.david_k.oneStopClick.Helper.Constants;
import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.R;
import com.example.david_k.oneStopClick.View.Fragment.ProductDetailFragment;

public class GrandChildActivity extends AppCompatActivity {

    private static final String TAG = "GrandChildActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grand_child);

        // get fragment
        Fragment fragment = getFragmentByActivityKey();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.grand_child_frame, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Fragment getFragmentByActivityKey(){

        int activityKey = this.getIntent().getExtras().getInt(Constants.grandChildPageActivityKey);
        Fragment fragment = null;

        switch (activityKey){
            case Constants.GrandChildActivityPagesEnum.PRODUCT_DETAIL:
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
