package com.example.david_k.oneStopClick.Views.Fragments.Cart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.david_k.oneStopClick.Helper.CenterRepositoryHelper;
import com.example.david_k.oneStopClick.Helper.Constants;
import com.example.david_k.oneStopClick.ModelLayers.CenterRepository;
import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.R;
import com.example.david_k.oneStopClick.Views.Activities.PaymentDetail.PaymentDetailTabActivity;

import java.util.List;

/**
 * Created by David_K on 12/10/2017.
 */

public class CartFragment extends Fragment {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private CenterRepositoryHelper centerRepositoryHelper = new CenterRepositoryHelper();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.cart_fragment, null);

        recyclerView = (RecyclerView) view.findViewById(R.id.cardCell_item);
        List<Product> cartOrderList = CenterRepository.getCenterRepository().getListOfCartInShoppingList();

        adapter = new CartAdapter(getContext(), cartOrderList, (v, position) -> rowTapped(position));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void rowTapped(int position) {
        int productId = adapter.cartProducts.get(position).getId();
        Product product  = centerRepositoryHelper.GetProductById(productId);

        goToCartDetail(product);
    }

    private void goToCartDetail(Product product){

//        Toast.makeText(getContext(), "goto Cart Detail for : "+product.getName(), Toast.LENGTH_SHORT);
        Intent intent = new Intent(getActivity(), PaymentDetailTabActivity.class);
        intent.putExtra(Constants.productKey, product);

        startActivity(intent);

    }
}
