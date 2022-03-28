package com.example.PickBeforeGo.helper;

import android.util.Log;

import com.example.PickBeforeGo.components.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GetProductHelper {
    private ArrayList<Product> allProductArrayList;
    private ArrayList<Product> favouriteProductArrayList;
    private ArrayList<Product> promotionProductArrayList;
    private ArrayList<Product> noStockProductArrayList;
    private FirebaseDatabase prodDatabase;
    private DatabaseReference prodDatabaseReference;
// ...



    //SAMPLE TESTING DATA FOR PRODUCTS
    public GetProductHelper(){
        //TODO Tianqin can help put the code to pull data from database for all products here??
        prodDatabase = FirebaseDatabase.getInstance();
        prodDatabaseReference = prodDatabase.getReference();
        allProductArrayList = new ArrayList<Product>();

        prodDatabaseReference.child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get all of the children at this level.
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                //need to extract out the attributes of the firebase brnaches
                for (DataSnapshot childSnapshot : children) {
//                    String category = (String) childSnapshot.child("category").getValue();
//                    String date = (String) childSnapshot.child("date").getValue();
//                    String description = (String) childSnapshot.child("description").getValue();
//                    String imageURL = (String) childSnapshot.child("image").getValue();
//                    String productID = (String) childSnapshot.child("pid").getValue();
//                    String productName = (String) childSnapshot.child("pname").getValue();
//                    String price = (String) childSnapshot.child("price").getValue();
//                    String time = (String) childSnapshot.child("time").getValue();
//                    String weight = (String) childSnapshot.child("weight").getValue();
//                    String nextRestockTime = (String) childSnapshot.child("nextRestockTime").getValue();
//                    Long discountPercent = (Long) childSnapshot.child("DiscountPercent").getValue();
//                    Boolean isPromo = (Boolean) childSnapshot.child("discount").getValue();
//                    Boolean isFavourite = (Boolean) childSnapshot.child("favourite").getValue();
//                    Boolean inStock = (Boolean) childSnapshot.child("stock").getValue();
//
//                    allProductArrayList.add(new Product.ProductBuilder().setCategory(category).setDate(date).setDescription(description)
//                            .setImageURL(imageURL).setProductID(productID).setProductName(productName).setPrice(price)
//                            .setTime(time).setWeight(weight).setNextRestockTime(nextRestockTime).setDiscountPercent(discountPercent.doubleValue())
//                            .setIsPromo(isPromo).setIsFavourite(isFavourite).setInStock(inStock).build());

                    Product product = childSnapshot.getValue(Product.class);
                    // can just add in automatically without builder class
                    allProductArrayList.add(product);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("GetProductHelper", "error in calling database");
            }

        });
        System.out.println(allProductArrayList.size());
//        System.out.println(allProductArrayList.get(0).getCategory());
//        System.out.println(allProductArrayList.get(0).getProductName());
    }

    public ArrayList<Product> getAllProducts() {
        return this.allProductArrayList;
    }

    //TODO filter logic for the different types of products
    public ArrayList<Product> getFavouriteProducts() {
        favouriteProductArrayList = new ArrayList<Product>();
        for (Product product : allProductArrayList){
            if (product.getIsFavorite()){
                favouriteProductArrayList.add(product);
            }
        }
        System.out.println(favouriteProductArrayList.size());
        return this.favouriteProductArrayList;
    }

    public ArrayList<Product> getPromotionProducts() {
        promotionProductArrayList = new ArrayList<Product>();
        for (Product product : allProductArrayList){
            if (product.getIsPromo()){
                promotionProductArrayList.add(product);
            }
        }
        return this.promotionProductArrayList;
    }

    public ArrayList<Product> getNoStockProducts() {
        noStockProductArrayList = new ArrayList<Product>();
        for (Product product : allProductArrayList){
            if (!product.getInStock()){
                promotionProductArrayList.add(product);
            }
        }
        return this.promotionProductArrayList;
    }

}
