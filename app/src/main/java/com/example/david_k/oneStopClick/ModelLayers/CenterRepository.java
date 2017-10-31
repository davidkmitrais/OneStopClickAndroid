package com.example.david_k.oneStopClick.ModelLayers;

import com.example.david_k.oneStopClick.ModelLayers.Database.Address;
import com.example.david_k.oneStopClick.ModelLayers.Database.Product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by David_K on 24/10/2017.
 */

public class CenterRepository {

    private static CenterRepository centerRepository;
    private List<Product> listOfProductsInShoppingList = Collections.synchronizedList(new ArrayList<Product>());
    private List<Address> listOfAddress = Collections.synchronizedList(new ArrayList<Address>());

    public static CenterRepository getCenterRepository() {

        if (null == centerRepository) {
            centerRepository = new CenterRepository();

        }
        return centerRepository;
    }

    public List<Product> getListOfProductsInShoppingList() {
        return listOfProductsInShoppingList;
    }

    public List<Product> getListOfCartInShoppingList() {
        List<Product> listOfCartInShoppingList = new ArrayList<>();

        for (Product product: listOfProductsInShoppingList) {
            if(product.getOrderQty() > 0){
                listOfCartInShoppingList.add(product);
            }
        }

        return listOfCartInShoppingList;
    }

    public Product getProductById(int productId) {
        Product result = null;

        for(Product product : listOfProductsInShoppingList) {
            if(product.getId() == productId) {
                result = product;
                break;
            }
        }

        return result;
    }

    public void setListOfProductsInShoppingList(List<Product> getShoppingList) {
        this.listOfProductsInShoppingList = getShoppingList;
    }

    public List<Address> getListOfAddress() {
        return listOfAddress;
    }

    public void addToAddressList(Address address){
        this.listOfAddress.add(address);
    }

    public void setDummyAddressDefault() {
        Address defAddress = new Address("Work address", "By Pass Ngurah Rai, Gg Mina Utama No 1", "Denpasar", "Bali");
        addToAddressList(defAddress);
    }
}
