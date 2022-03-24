package com.example.PickBeforeGo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.PickBeforeGo.activities.AdminFormActivity;
import com.example.PickBeforeGo.adapters.UserRVAdapter;
import com.example.PickBeforeGo.components.Product;
import com.example.PickBeforeGo.helper.GetProduct_helper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements  UserRVAdapter.OnNoteListener{
    private Toolbar topbar;
    private BottomNavigationView bottom_bar;
    private static final String TAG = "Main activity";

    private ArrayList<Product> allProductArrayList;
    private ArrayList<Product> favouriteProductArrayList;
    private ArrayList<Product> promoProductArrayList;
    private ArrayList<Product> noStockProductArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set up the bottom navigation bar
        bottom_bar = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this,R.id.my_nav);
        NavigationUI.setupWithNavController(bottom_bar,navController);

        //get data from database using helper function
        GetProduct_helper getProduct = new GetProduct_helper();
        allProductArrayList = getProduct.getAllProducts();
        favouriteProductArrayList = getProduct.getFavouriteProducts();
        promoProductArrayList = getProduct.getPromotionProducts();
        noStockProductArrayList = getProduct.getNoStockProducts();

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