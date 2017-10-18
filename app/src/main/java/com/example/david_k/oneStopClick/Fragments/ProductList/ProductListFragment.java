package com.example.david_k.oneStopClick.Fragments.ProductList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.david_k.oneStopClick.Helper.Constants;
import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.ProductDetailActivity;
import com.example.david_k.oneStopClick.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private ProductViewAdapter adapter;
    private List<Product> productList;

    public ProductListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductListFragment newInstance(String param1, String param2) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.product_list_fragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.product_list_recycler_view);

        productList = new ArrayList<>();
        adapter = new ProductViewAdapter(getActivity(), productList, (v, position) -> rowTapped(position));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        setDummyProduct();

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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

        adapter.notifyDataSetChanged();
    }

    private void rowTapped(int position) {
        Product product = productList.get(position);
        goToProductDetail(product);
    }

    private void goToProductDetail(Product product){
        //Toast.makeText(getActivity(), "Go to product Detail '"+product.getName()+"' (id:"+product.id+")", Toast.LENGTH_SHORT).show();

//        Fragment fragment = null;
//        Class fragmentClass = ProductFragment.class;
//        try {
//            fragment = (Fragment) fragmentClass.newInstance();
//            Bundle bundle = new Bundle();
//            bundle.putInt(Constants.productIdKey, product.id);
//            fragment.setArguments(bundle);
//        } catch (java.lang.InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.screen_area, fragment).commit();

        Bundle bundle = new Bundle();
        bundle.putInt(Constants.productIdKey, product.id);

        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);

    }
}
