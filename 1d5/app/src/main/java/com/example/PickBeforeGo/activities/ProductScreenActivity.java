package com.example.PickBeforeGo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.PickBeforeGo.MainActivity;
import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo.components.Product;
import com.example.PickBeforeGo.fragments.InStockAvailabilityFragment;
import com.example.PickBeforeGo.fragments.InStockProductCardFragment;
import com.example.PickBeforeGo.fragments.NoStockAvailabilityFragment;
import com.example.PickBeforeGo.fragments.NoStockProductCardFragment;
import com.example.PickBeforeGo.fragments.PromoProductCardFragment;
import com.example.PickBeforeGo.helper.GetProductHelper;

import java.util.ArrayList;

public class ProductScreenActivity extends AppCompatActivity {

    private static final String PRODUCT_ID = "product_id";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String IMAGE_URL = "image_url";
    private static final String FAVOURITE = "favourite";
    private static final String DESCRIPTION = "description";
    private static final String PROMOTION = "promotion";

    private String product_id;
    private String name;
    private String price;
    private String image_url;
    private boolean favourite;
    private String description;

    FragmentTransaction fragmentTransaction;
    private MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_screen);
        ImageView btnBack = findViewById(R.id.btnBack);
        TextView txtDescription = findViewById(R.id.txtDescriptionFull);
        ArrayList<Product> productArrayList = new ArrayList<Product>();


        // get arguments from previous intent
        Bundle args = getIntent().getExtras();
        name = args.getString(NAME);
        image_url = args.getString(IMAGE_URL);
        description = args.getString(DESCRIPTION);
        product_id = args.getString(PRODUCT_ID);

        // TODO get price and fav from database
//        productArrayList = mainActivity.getAllProducts();
//        int product_index = productArrayList.indexOf(product_id);
//        price = "$" + productArrayList.get(product_index).getPrice();
//        favourite = productArrayList.get(product_index).getIsFavorite();
        price = "$5.95";
        favourite = false;

        // set up fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        Fragment productCardFragment = new InStockProductCardFragment();
        fragmentTransaction.add(R.id.fragment_productCard, productCardFragment, "Product_Card");
        Bundle productCardArgs = new Bundle();
        productCardArgs.putString(NAME, name);
        productCardArgs.putString(PRICE, price);
        productCardArgs.putString(IMAGE_URL, image_url);
        productCardArgs.putBoolean(FAVOURITE, favourite);
        productCardArgs.putString(PRODUCT_ID, product_id);
        productCardArgs.putString(DESCRIPTION, description);
        productCardFragment.setArguments(productCardArgs);

        Fragment availabilityFragment = new InStockAvailabilityFragment();
        fragmentTransaction.add(R.id.fragment_availability, availabilityFragment, "Availability");

        // TODO: retrieve inStock, onPromo, promotion amount from previous page's intent / from database based on product id
        // TODO: retrieve restock timing from database
        // inStock corresponds to 'stock' in db
        // onPromo corresponds to 'discount' in db
        // discPercentage corresponds to 'DiscountPercent' in db

        boolean inStock = true;
        boolean onPromo = false;
        int discPercentage = 20;
        String restockTiming = "Next Restock Time - 10:00 28 Feb 2021";
        String promotion = "Promo " + discPercentage + "%";

        // replace fragments (if necessary) based on boolean values
        if (onPromo) {;
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();

            productCardFragment = new PromoProductCardFragment();
            productCardArgs = new Bundle();
            productCardArgs.putString(NAME, name);
            productCardArgs.putString(PRICE, price);
            productCardArgs.putString(IMAGE_URL, image_url);
            productCardArgs.putBoolean(FAVOURITE, favourite);
            productCardArgs.putString(PRODUCT_ID, product_id);
            productCardArgs.putString(PROMOTION, promotion);
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
            availabilityArgs.putString("restock_timing", restockTiming);
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
    }
}