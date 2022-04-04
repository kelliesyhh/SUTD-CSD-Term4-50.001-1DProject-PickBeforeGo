package com.example.PickBeforeGo1.helper;

import android.util.Log;

import com.example.PickBeforeGo1.components.Product;
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
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot childSnapshot : children) {
                    Product product = childSnapshot.getValue(Product.class);
                    allProductArrayList.add(product);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("GetProductHelper", "error in calling database");
            }

        });
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
