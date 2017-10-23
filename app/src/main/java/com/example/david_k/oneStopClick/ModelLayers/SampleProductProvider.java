package com.example.david_k.oneStopClick.ModelLayers;

import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by David_K on 23/10/2017.
 */

public class SampleProductProvider {
    public static List<Product> productList;
    public static Map<Integer, Product> productMap;

    static {
        productList = new ArrayList<>();
        productMap = new HashMap<>();

        addItem(new Product(1, "Sport Shoe", 150, R.drawable.product001, "product001.jpg"));
        addItem(new Product(2, "Eye Glass", 70, R.drawable.product002, "product002.jpg"));
        addItem(new Product(3, "Guitar", 140, R.drawable.product003, "product003.jpg"));
        addItem(new Product(4, "Faucet", 40, R.drawable.product004, "product004.jpg"));
        addItem(new Product(5, "Hat", 10, R.drawable.product005, "product005.jpg"));
        addItem(new Product(6, "Kitchen", 30, R.drawable.product006, "product006.jpg"));
        addItem(new Product(7, "Wheel", 200, R.drawable.product007, "product007.jpg"));
        addItem(new Product(8, "Whatch", 150, R.drawable.product008, "product008.jpg"));
        addItem(new Product(9, "Woman Shoe", 100, R.drawable.product009, "product009.jpg"));
        addItem(new Product(10, "Hand Bag", 180, R.drawable.product010, "product010.jpg"));
    }

    private static void addItem(Product item) {
        productList.add(item);
        productMap.put(item.getId(), item);
    }
}
