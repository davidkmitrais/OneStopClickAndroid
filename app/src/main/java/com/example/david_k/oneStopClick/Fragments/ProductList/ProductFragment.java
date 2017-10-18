package com.example.david_k.oneStopClick.Fragments.ProductList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.david_k.oneStopClick.Helper.Constants;
import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David_K on 12/10/2017.
 */

public class ProductFragment extends Fragment {

    private static final String TAG = "ProductFragment";
    private List<Product> productList;
    private Product product;

    private ImageView productImage;
    private TextView  nameTextView;
    private TextView  priceTextView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        productList = new ArrayList<>();

        setDummyProduct();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_fragment, container, false);
        rootView.setTag(TAG);

        setupUI(rootView);
        parseBundle();
        configureUI();

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toast.makeText(getActivity(), "Show product Detail '" + product.getName() + "' (id:" + product.id + ")", Toast.LENGTH_SHORT).show();

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

    private void setupUI(View view) {
        productImage = (ImageView) view.findViewById(R.id.product_image);
        nameTextView = (TextView) view.findViewById(R.id.product_name);
        priceTextView = (TextView) view.findViewById(R.id.product_price);
    }

    private void parseBundle() {
        Bundle bundle = this.getArguments();
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
}
