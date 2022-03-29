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
        //TODO pull data from database

        // hi whoever is reading this, I changed the logic and UI abit. so description is only gonna be shown when they click into the product, but not on the 'search' page/fragment (2nd tab).
        // with thanks, kellie
        allProductArrayList = new ArrayList<Product>();
        allProductArrayList.add(new Product.ProductBuilder().setProductName("Hai Di Lao Self-Heating Beef Hot Pot - Spicy").setDescription("350g | Brand: Hai Di Lao").setWeight("350g").setImageURL("https://firebasestorage.googleapis.com/v0/b/pickbeforegop.appspot.com/o/imageURL%2Fraw%3A%2Fstorage%2Femulated%2F0%2FDownload%2Fhaidilao.pngMar%2028%2C%20202214%3A40%3A38%20PM.jpg?alt=media&token=8ec985f8-db17-48f7-a185-a37789ad126d").build());
        allProductArrayList.add(new Product.ProductBuilder().setProductName("Meiji Fresh Milk - Regular").setDescription("2L | Brand: Meiji").setWeight("2L").setImageURL("https://firebasestorage.googleapis.com/v0/b/pickbeforegop.appspot.com/o/Product%20Images%2Facc%3D1%3Bdoc%3Dencoded%3DzLuAgyLP5ExYVDAUvRGTWYl%2Bc%2Ffz06hHcWDZs9OwwlHY5XaOrtuoNls%3DMar%2026%2C%20202202%3A19%3A36%20AM.jpg?alt=media&token=0e70df68-5ac1-4cae-a876-9c8df9a35d9c").build());
        allProductArrayList.add(new Product.ProductBuilder().setProductName("M&M's Chocolate Candies - Pranut (Funsize)").setDescription("175.5g | Brand: M&M's").setWeight("175.5g").setImageURL("https://firebasestorage.googleapis.com/v0/b/pickbeforegop.appspot.com/o/Product%20Images%2Facc%3D1%3Bdoc%3Dencoded%3DW2gVn2q37QL%2F%2BE55uvMjgMugZc54yqhxkg%2B3qtSsI1hduo%2Fj8hkfWVM%3DMar%2026%2C%20202202%3A22%3A21%20AM.jpg?alt=media&token=ac38186b-83fb-4afb-a580-b5a76193c3c6").build());
        allProductArrayList.add(new Product.ProductBuilder().setProductName("Oreo Cookie - Dark and White Chocolate").setDescription("123.5g | Brand: Oreo").setImageURL("https://firebasestorage.googleapis.com/v0/b/pickbeforegop.appspot.com/o/imageURL%2Fraw%3A%2Fstorage%2Femulated%2F0%2FDownload%2Fchocolate.pngMar%2028%2C%20202214%3A43%3A52%20PM.jpg?alt=media&token=2d6c4b4a-c1f3-4c6e-a8f0-a0e1a9320f58").setWeight("123.5g").build());

    }
    public ArrayList<Product> getAllProducts() {

        return this.allProductArrayList;
    }

    //TODO filter logic for the different types of products
    public ArrayList<Product> getFavouriteProducts() {
        favouriteProductArrayList = new ArrayList<Product>();


        return this.favouriteProductArrayList;
    }
    public ArrayList<Product> getPromotionProducts() {
        promotionProductArrayList = new ArrayList<Product>();

        return this.promotionProductArrayList;
    }
    public ArrayList<Product> getNoStockProducts() {
        noStockProductArrayList = new ArrayList<Product>();

        return this.noStockProductArrayList;
    }

}
