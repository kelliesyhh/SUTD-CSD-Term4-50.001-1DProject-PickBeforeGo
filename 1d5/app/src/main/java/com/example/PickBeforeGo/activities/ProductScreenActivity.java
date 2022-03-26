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

    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String IMAGE_URL = "image_url";
    private static final String FAVOURITE = "favourite";
    private static final String DESCRIPTION = "description";
    private static final String PROMOTION = "promotion";

    private String name;
    private String price;
    private String image_url;
    private boolean favourite;
    private String description;


    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_screen);
        ImageView btnBack = findViewById(R.id.btnBack);

        // TODO: get name, imageURL, price, favourite from previous page's intent to use as fragment arguments
        // name corresponds to 'pname' in db
        // price corresponds to 'price' in db (need to add '$')
        // image_url corresponds to 'image' in db
        // favourite corresponds to 'favourite' in db
        // description corresponds to 'description' in db

        name = "Meiji Fresh Milk - Regular";
        price = "$5.95";
        image_url = "https://firebasestorage.googleapis.com/v0/b/pickbeforegop.appspot.com/o/Product%20Images%2Facc%3D1%3Bdoc%3Dencoded%3DzLuAgyLP5ExYVDAUvRGTWYl%2Bc%2Ffz06hHcWDZs9OwwlHY5XaOrtuoNls%3DMar%2026%2C%20202202%3A19%3A36%20AM.jpg?alt=media&token=0e70df68-5ac1-4cae-a876-9c8df9a35d9c";
        favourite = false;
        description = "500g | Brand: Gardenia";

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
        productCardArgs.putString(DESCRIPTION, description);
        productCardFragment.setArguments(productCardArgs);

        Fragment availabilityFragment = new InStockAvailabilityFragment();
        fragmentTransaction.add(R.id.fragment_availability, availabilityFragment, "Availability");

        //TODO: retrieve inStock, onPromo, promotion amount, restock timing from database
        // inStock corresponds to 'stock' in db
        // onPromo corresponds to 'discount' in db
        // discPercentage corresponds to 'DiscountPercent' in db

        boolean inStock = true;
        boolean onPromo = true;
        int discPercentage = 0;
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
                Intent intent = new Intent(ProductScreenActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}