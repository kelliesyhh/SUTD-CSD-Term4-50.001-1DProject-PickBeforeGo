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
    private FirebaseDatabase prodDatabase;
    private DatabaseReference prodDatabaseReference;

    //SAMPLE TESTING DATA FOR PRODUCTS
    public GetProductHelper(){
        prodDatabase = FirebaseDatabase.getInstance();
        prodDatabaseReference = prodDatabase.getReference();
        allProductArrayList = new ArrayList<Product>();
        favouriteProductArrayList = new ArrayList<Product>();
        prodDatabaseReference.child("Product_List").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!allProductArrayList.isEmpty()){
                    allProductArrayList.clear();
                    favouriteProductArrayList.clear();
                }
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot childSnapshot : children) {
                    Product product = childSnapshot.getValue(Product.class);
                    allProductArrayList.add(product);
                    if (product.getIsFavourite()){favouriteProductArrayList.add(product);}
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
    public ArrayList<Product> getFavouriteProducts() { return this.favouriteProductArrayList; }

}
