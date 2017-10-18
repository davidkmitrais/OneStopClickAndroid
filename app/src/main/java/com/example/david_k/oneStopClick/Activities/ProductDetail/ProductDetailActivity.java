package com.example.david_k.oneStopClick.Activities.ProductDetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.david_k.oneStopClick.Fragments.ProductList.ProductListFragment;
import com.example.david_k.oneStopClick.Helper.Constants;
import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.R;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    private static final String TAG = "ProductDetailActivity";
    private List<Product> productList;
    private Product product;

    private ImageView productImage;
    private TextView nameTextView;
    private TextView  priceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        productList = new ArrayList<>();
        setDummyProduct();

        setupUI();
        parseBundle();
        configureUI();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Toast.makeText(this, "back pressed", Toast.LENGTH_SHORT).show();
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

    private void setDummyProduct() {
        int[] productIds = new int[]{
                R.mipmap.product001,
                R.mipmap.product002,
                R.mipmap.product003,
                R.mipmap.product004,
                R.mipmap.product005,
                R.mipmap.product006,
                R.mipmap.product007,
                R.mipmap.product008,
                R.mipmap.product009,
                R.mipmap.product010};

        Product addProduct = null;

        for (int i = 0; i < 10; i++) {
            addProduct = new Product(i, "Product " + (i + 1), i * 10, productIds[i]);
            productList.add(addProduct);
        }
    }

    private void setupUI() {
        productImage = (ImageView) findViewById(R.id.product_detail_image);
        nameTextView = (TextView) findViewById(R.id.product_detail_name);
        priceTextView = (TextView) findViewById(R.id.product_detail_price);
    }

    private void parseBundle() {
        Bundle bundle = getIntent().getExtras();
        int productId = 0;
        if (bundle != null){
            productId = bundle.getInt(Constants.productIdKey, 0);
        }

        product = productList.get(productId);
    }

    private void configureUI() {
        productImage.setImageResource(product.imageId);
        nameTextView.setText(product.name);
        priceTextView.setText(String.valueOf(product.price));
    }

    private void showProductListFragment() {
        Fragment fragment = null;
        Class fragmentClass = ProductListFragment.class;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.screen_area, fragment).commit();
    }
}
