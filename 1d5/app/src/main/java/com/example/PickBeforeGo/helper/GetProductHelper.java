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

    //SAMPLE TESTING DATA FOR PRODUCTS
    public GetProductHelper(){
        prodDatabase = FirebaseDatabase.getInstance();
        prodDatabaseReference = prodDatabase.getReference();
        allProductArrayList = new ArrayList<Product>();

        prodDatabaseReference.child("Product_List").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get all of the children at this level.
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                //need to extract out the attributes of the firebase brnaches
                for (DataSnapshot childSnapshot : children) {
                    Product product = childSnapshot.getValue(Product.class);
                    System.out.println(product.getProductID());
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
