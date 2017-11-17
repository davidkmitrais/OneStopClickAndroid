package com.example.david_k.oneStopClick.Helper;

import com.example.david_k.oneStopClick.ModelLayers.Database.Address;
import com.example.david_k.oneStopClick.ModelLayers.Database.Product;
import com.example.david_k.oneStopClick.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David_K on 25/10/2017.
 */

public class CenterRepositoryHelper {

    public Product setDummySelecetedProduct() {
        return new Product(99,"dummy product", 999, R.drawable.product001, "product001");
    }

    public Address setDummySelecetedAddress() {
        return new Address("name","delivery","city","state");
    }
}
