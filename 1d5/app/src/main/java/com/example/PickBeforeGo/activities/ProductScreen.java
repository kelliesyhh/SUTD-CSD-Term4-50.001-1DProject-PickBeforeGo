package com.example.PickBeforeGo.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo.fragment.Availability;
import com.example.PickBeforeGo.fragment.ProductCard;

public class ProductScreen extends AppCompatActivity {

    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_screen);
        ImageView imageButton = findViewById(R.id.imageButton);
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
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}