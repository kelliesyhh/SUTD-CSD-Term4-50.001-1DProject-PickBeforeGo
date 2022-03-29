package com.example.PickBeforeGo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.PickBeforeGo.activities.AdminFormActivity;
import com.example.PickBeforeGo.adapters.UserRVAdapter;
import com.example.PickBeforeGo.components.Product;
import com.example.PickBeforeGo.helper.GetProductHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements  UserRVAdapter.OnNoteListener{
    private static final String TAG = "Main activity";
    private ArrayList<Product> allProductArrayList;
    private ArrayList<Product> favouriteProductArrayList;
    private ArrayList<Product> promoProductArrayList;
    private ArrayList<Product> noStockProductArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);

        //set up the bottom navigation bar
        BottomNavigationView btmNavBar = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this,R.id.my_nav);
        NavigationUI.setupWithNavController(btmNavBar,navController);

        //Call database if product empty
        if (allProductArrayList == null){
            GetProductHelper getProduct = new GetProductHelper();
            allProductArrayList = getProduct.getAllProducts();
            favouriteProductArrayList = getProduct.getFavouriteProducts();
            promoProductArrayList = getProduct.getPromotionProducts();
            noStockProductArrayList = getProduct.getNoStockProducts();
        }

    }

    //TODO shift this to UserFragment
    public void onNoteClick(int position) {
        Log.d(TAG, "onNoteClick: clicked."+ position);
        if (position == 0){
            Intent intent = new Intent(MainActivity.this, AdminFormActivity.class);
            startActivity(intent);
        }
    }

    //get methods for fragment to access activity products
    public ArrayList<Product> getAllProducts(){
        return this.allProductArrayList;
    }

    public ArrayList<Product> getFavouriteProducts(){
        return this.favouriteProductArrayList;
    }

    public ArrayList<Product> getPromotionProducts(){
        return this.promoProductArrayList;
    }

    public ArrayList<Product> getNoStockProducts(){
        return this.noStockProductArrayList;
    }
}