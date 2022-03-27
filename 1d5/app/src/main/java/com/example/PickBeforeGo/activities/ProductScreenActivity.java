package com.example.PickBeforeGo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.PickBeforeGo.MainActivity;
import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo.fragments.InStockAvailabilityFragment;
import com.example.PickBeforeGo.fragments.InStockProductCardFragment;
import com.example.PickBeforeGo.fragments.NoStockAvailabilityFragment;
import com.example.PickBeforeGo.fragments.NoStockProductCardFragment;
import com.example.PickBeforeGo.fragments.PromoProductCardFragment;

public class ProductScreenActivity extends AppCompatActivity {

    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_screen);
        ImageView btnBack = findViewById(R.id.btnBack);
        // set up top bar

        // set up fragments
        Fragment productCardFragment = new InStockProductCardFragment();
        Fragment availabilityFragment = new InStockAvailabilityFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_productCard, productCardFragment, "Product_Card");
        fragmentTransaction.add(R.id.fragment_availability, availabilityFragment, "Availability");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductScreenActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // retrieve data from database
        boolean inStock = true;
        boolean onPromo = true;

        // replace fragments (if necessary)
        if (onPromo) {;
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            productCardFragment = new PromoProductCardFragment();
            fragmentTransaction.replace(R.id.fragment_productCard, productCardFragment);
            fragmentTransaction.commit();
        }

        if (!inStock) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            productCardFragment = new NoStockProductCardFragment();
            availabilityFragment = new NoStockAvailabilityFragment();
            fragmentTransaction.replace(R.id.fragment_productCard, productCardFragment);
            fragmentTransaction.replace(R.id.fragment_availability, availabilityFragment);
            fragmentTransaction.commit();
        }



    }
}