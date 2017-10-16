package com.example.david_k.oneStopClick.ModelLayers.Database;

/**
 * Created by David_K on 13/10/2017.
 */

public class Product {
    public int id;
    public int price;
    public String name;
    public String imageName;
    public int imageId;


    public Product(int id, String name, int price, int imageId) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.imageId = imageId;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
