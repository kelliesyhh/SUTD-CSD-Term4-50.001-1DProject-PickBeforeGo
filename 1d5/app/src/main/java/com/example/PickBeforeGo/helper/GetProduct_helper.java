package com.example.PickBeforeGo.helper;

import com.example.PickBeforeGo.components.Product;

import java.util.ArrayList;

public class GetProduct_helper {
    private ArrayList<Product> allProductArrayList;
    private ArrayList<Product> favouriteProductArrayList;
    private ArrayList<Product> promotionProductArrayList;
    private ArrayList<Product> noStockProductArrayList;


    //SAMPLE TESTING DATA FOR PRODUCTS
    public GetProduct_helper(){
        //TODO Tianqin can help put the code to pull data from database for all products here??
        allProductArrayList = new ArrayList<Product>();
        allProductArrayList.add(new Product.ProductBuilder().setProductName("White Bread").setDescription("Gardenia").setWeight("500g").build());
        allProductArrayList.add(new Product.ProductBuilder().setProductName("White Bread").setDescription("Gardenia").setWeight("500g").build());

    }
    public ArrayList<Product> getAllProducts() {

        return this.allProductArrayList;
    }

    //TODO filter logic for the different types of products
    public ArrayList<Product> getFavouriteProducts() {
        favouriteProductArrayList = new ArrayList<Product>();


        return this.allProductArrayList;
    }
    public ArrayList<Product> getPromotionProducts() {
        promotionProductArrayList = new ArrayList<Product>();

        return this.allProductArrayList;
    }
    public ArrayList<Product> getNoStockProducts() {
        noStockProductArrayList = new ArrayList<Product>();

        return this.allProductArrayList;
    }

}
