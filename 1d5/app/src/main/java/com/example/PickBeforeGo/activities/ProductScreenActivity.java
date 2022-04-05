package com.example.PickBeforeGo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.PickBeforeGo.MainActivity;
import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo.components.Product;
import com.example.PickBeforeGo.components.ProductAttributes;
import com.example.PickBeforeGo.fragments.InStockAvailabilityFragment;
import com.example.PickBeforeGo.fragments.InStockProductCardFragment;
import com.example.PickBeforeGo.fragments.NoStockAvailabilityFragment;
import com.example.PickBeforeGo.fragments.NoStockProductCardFragment;
import com.example.PickBeforeGo.fragments.PromoProductCardFragment;

import com.example.PickBeforeGo.components.ProductAttributes;


import java.util.ArrayList;

public class ProductScreenActivity extends AppCompatActivity {
    private String product_id;
    private String name;
    private String price;
    private String image_url;
    private Boolean favourite, inStock, isPromo;
    private String description;
    private int discountPercent;
    private String restockTime;

    FragmentTransaction fragmentTransaction;
    private MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_screen);
        ImageView btnBack = findViewById(R.id.btnBack);
        TextView txtDescription = findViewById(R.id.txtDescriptionFull);
        ArrayList<Product> productArrayList = new ArrayList<Product>();
        Button btnAdminEditProductDetails = findViewById(R.id.btnAdminEditProductDetails);

        // get arguments from previous intent
        Bundle args = getIntent().getExtras();
        name = args.getString(ProductAttributes.NAME);
        image_url = args.getString(ProductAttributes.IMAGE_URL);
        description = args.getString(ProductAttributes.DESCRIPTION);
        product_id = args.getString(ProductAttributes.PRODUCT_ID);
        price = "$" + args.getString(ProductAttributes.PRICE);
        favourite = args.getBoolean(ProductAttributes.FAVOURITE);

        // set up fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        Fragment productCardFragment = new InStockProductCardFragment();
        fragmentTransaction.add(R.id.fragment_productCard, productCardFragment, "Product_Card");
        Bundle productCardArgs = new Bundle();
        productCardArgs.putString(ProductAttributes.NAME, name);
        productCardArgs.putString(ProductAttributes.PRICE, price);
        productCardArgs.putString(ProductAttributes.IMAGE_URL, image_url);
        productCardArgs.putBoolean(ProductAttributes.FAVOURITE, favourite);
        productCardArgs.putString(ProductAttributes.PRODUCT_ID, product_id);
        productCardArgs.putString(ProductAttributes.DESCRIPTION, description);
        productCardFragment.setArguments(productCardArgs);

        Fragment availabilityFragment = new InStockAvailabilityFragment();
        fragmentTransaction.add(R.id.fragment_availability, availabilityFragment, "Availability");

        inStock = args.getBoolean(ProductAttributes.STOCK);
        isPromo = args.getBoolean(ProductAttributes.IS_PROMO);
        discountPercent = args.getInt(ProductAttributes.DISCOUNT);
        restockTime = args.getString(ProductAttributes.RESTOCK_TIME);

        // replace fragments (if necessary) based on boolean values
        if (isPromo) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();

            productCardFragment = new PromoProductCardFragment();
            productCardArgs = new Bundle();
            productCardArgs.putString(ProductAttributes.NAME, name);
            productCardArgs.putString(ProductAttributes.PRICE, price);
            productCardArgs.putString(ProductAttributes.IMAGE_URL, image_url);
            productCardArgs.putBoolean(ProductAttributes.FAVOURITE, favourite);
            productCardArgs.putString(ProductAttributes.PRODUCT_ID, product_id);
            productCardArgs.putInt(ProductAttributes.DISCOUNT, discountPercent);
            productCardFragment.setArguments(productCardArgs);

            fragmentTransaction.replace(R.id.fragment_productCard, productCardFragment);
            fragmentTransaction.add(R.id.fragment_availability, availabilityFragment, "Availability");
        }

        if (!inStock) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();

            productCardFragment = new NoStockProductCardFragment();
            productCardFragment.setArguments(productCardArgs);

            availabilityFragment = new NoStockAvailabilityFragment();
            Bundle availabilityArgs = new Bundle();
            availabilityArgs.putString(ProductAttributes.RESTOCK_TIME, restockTime);
            availabilityFragment.setArguments(availabilityArgs);

            fragmentTransaction.replace(R.id.fragment_productCard, productCardFragment);
            fragmentTransaction.replace(R.id.fragment_availability, availabilityFragment);
        }

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        // set up topbar back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // set up product description below availability
        txtDescription.setText("Product Description: " + description);

        // TODO: get isAdmin property from other screens
        boolean isAdmin = true;
        if (isAdmin) {
            btnAdminEditProductDetails.setVisibility(View.VISIBLE);
            btnAdminEditProductDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: add in intent putExtras if needed, to populate the various fields for editing
                    Intent intentAdmin = new Intent(ProductScreenActivity.this, AdminFormActivity.class);

                    // Filling intents
                    intentAdmin.putExtra(ProductAttributes.PRODUCT_ID, product_id);
                    intentAdmin.putExtra(ProductAttributes.NAME, name);
                    intentAdmin.putExtra(ProductAttributes.PRICE, price);
                    intentAdmin.putExtra(ProductAttributes.IS_PROMO, isPromo);
                    intentAdmin.putExtra(ProductAttributes.STOCK, inStock);
                    intentAdmin.putExtra(ProductAttributes.DISCOUNT, discountPercent);
                    intentAdmin.putExtra(ProductAttributes.IS_NEW, false);
                    intentAdmin.putExtra(ProductAttributes.IMAGE_URL, image_url);
                    startActivity(intentAdmin);



                    // dembird testing zone
                    System.out.println("name is: " + name);
                    System.out.println("price is: "  + price);
                    System.out.println("promotion is: " + discountPercent+"%");
                    System.out.println("product id: " + product_id);
                    System.out.println("is promo: " + isPromo);
                    System.out.println("is inStock?" + inStock);


                }
            });
        }
    }
}