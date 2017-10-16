package com.example.david_k.oneStopClick.Presenters.ProductList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David_K on 12/10/2017.
 */

public class ProductFragment extends Fragment {

    private static final String TAG = "ProductFragment";
    private List<Product> products = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProductViewAdapter productViewAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setDummyProduct();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*View rootView = inflater.inflate(R.layout.product_fragment, container, false);
        rootView.setTag(TAG);

        setupProductData(rootView);

        return rootView;*/

        return inflater.inflate(R.layout.product_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupProductData(view);

//        view.findViewById(R.id.productButton).setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "in product fragment", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void setupProductData(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.item_recycler_view);

        linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setHasFixedSize(true);

        setDummyProduct();

        productViewAdapter = new ProductViewAdapter(getContext(), products);

        recyclerView.setAdapter(productViewAdapter);

    }

    private void setDummyProduct() {
        int[] productIds = new int[]{
                R.drawable.ic_menu_product};

        Product addProduct = null;

        for (int i = 0; i < 5; i++) {
            addProduct = new Product(i + 1, "Product " + (i + 1), i * 10, productIds[0]);
            products.add(addProduct);
        }

        //productViewAdapter.notifyDataSetChanged();
    }

/*
    //region Private Method

    //region Helper Method

    private void setupUI() {

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        recyclerView = (RecyclerView) getView().findViewById(R.id.item_recycler_view);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
    }

    private void setupData() {
        try {
            initializeListView();
//            initializeData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //endregion

    //region User Interaction

    private void rowTapped(int position) {
        Spy spy = spies.get(position);
//        gotoSpyDetails(spy.id);
    }

    //region List View Adapter

    private void initializeListView() {
        ProductViewAdapter adapter = new ProductViewAdapter(spies, (v, position) -> rowTapped(position));
        recyclerView.setAdapter(adapter);
    }

    //endregion


    //region Navigation

    private void gotoSpyDetails(int spyId) {

        Bundle bundle = new Bundle();
        bundle.putInt(Constants.spyIdKey, spyId);

        Intent intent = new Intent(getActivity().getApplicationContext(), getActivity().getClass());
        intent.putExtras(bundle);

        startActivity(intent);
    }

    //endregion

    //endregion*/
}
