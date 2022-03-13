package com.example.local_1d;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActionBar;
import android.os.Bundle;

public class ProductScreen extends AppCompatActivity {

    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_screen);

        // set up top bar

        // set up fragments
        Fragment availability = new Availability();
        Fragment productCard = new ProductCard();
        FragmentManager manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_productCard, productCard, "Product_Card");
        transaction.add(R.id.fragment_availability, availability, "Availability");
        transaction.addToBackStack(null);
        transaction.commit();
    }
}