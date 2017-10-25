package com.example.david_k.oneStopClick.Helper;

import com.example.david_k.oneStopClick.ModelLayers.CenterRepository;
import com.example.david_k.oneStopClick.ModelLayers.Database.Product;

/**
 * Created by David_K on 25/10/2017.
 */

public class CenterRepositoryHelper {

    public Product GetProductById(int productId){
        return CenterRepository.getCenterRepository()
                .getProductById(productId);
    }

    public int GetOrderQtyByProductId(int productId){
        return CenterRepository.getCenterRepository()
                .getProductById(productId)
                .getOrderQty();
    }

    public void SetOrderQtyByProductId(int productId, int orderQty){
        CenterRepository.getCenterRepository()
                .getProductById(productId)
                .setOrderQty(orderQty);
    }

    public boolean IsProductSetOnCenterRepository(){
        int size = CenterRepository.getCenterRepository()
                        .getListOfProductsInShoppingList()
                        .size();

        return size > 0;
    }
}
