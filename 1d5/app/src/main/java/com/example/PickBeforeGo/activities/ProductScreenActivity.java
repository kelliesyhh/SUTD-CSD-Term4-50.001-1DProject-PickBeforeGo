package com.example.PickBeforeGo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo.components.ProductAttributes;
import com.example.PickBeforeGo.fragments.InStockAvailabilityFragment;
import com.example.PickBeforeGo.fragments.InStockProductCardFragment;
import com.example.PickBeforeGo.fragments.NoStockAvailabilityFragment;
import com.example.PickBeforeGo.fragments.NoStockProductCardFragment;
import com.example.PickBeforeGo.fragments.PromoProductCardFragment;
import com.example.PickBeforeGo.helper.Container;
import com.example.PickBeforeGo.helper.UserHelperClass;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProductScreenActivity extends AppCompatActivity {
    private String productId;
    private String name;
    private String price;
    private String imageUrl;
    private Boolean favourite, inStock, isPromo;
    private String description;
    private int discountPercent;
    private String restockTime;
    private final String TAG = "indiv_product_screen";
    private final String DEBUG = "debug_product_screen";

    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_screen);
        ImageView btnBack = findViewById(R.id.btnBack);
        TextView txtDescription = findViewById(R.id.txtDescriptionFull);
        Button btnAdminEditProductDetails = findViewById(R.id.btnAdminEditProductDetails);

        // get arguments from previous intent
        Bundle args = getIntent().getExtras();
        name = args.getString(ProductAttributes.NAME);
        imageUrl = args.getString(ProductAttributes.IMAGE_URL);
        description = args.getString(ProductAttributes.DESCRIPTION);
        productId = args.getString(ProductAttributes.PRODUCT_ID);
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
        productCardArgs.putString(ProductAttributes.IMAGE_URL, imageUrl);
        productCardArgs.putBoolean(ProductAttributes.FAVOURITE, favourite);
        productCardArgs.putString(ProductAttributes.PRODUCT_ID, productId);
        productCardArgs.putString(ProductAttributes.DESCRIPTION, description);
        productCardFragment.setArguments(productCardArgs);

        Fragment availabilityFragment = new InStockAvailabilityFragment();
        fragmentTransaction.add(R.id.fragment_availability, availabilityFragment, "Availability");

        inStock = args.getBoolean(ProductAttributes.STOCK);
        isPromo = args.getBoolean(ProductAttributes.IS_PROMO);
        discountPercent = args.getInt(ProductAttributes.DISCOUNT);
        restockTime = args.getString(ProductAttributes.NEXT_RESTOCK_TIMING);

        // replace fragments (if necessary) based on boolean values
        if (isPromo) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();

            productCardFragment = new PromoProductCardFragment();
            productCardArgs = new Bundle();
            productCardArgs.putString(ProductAttributes.NAME, name);
            productCardArgs.putString(ProductAttributes.PRICE, price);
            productCardArgs.putString(ProductAttributes.IMAGE_URL, imageUrl);
            productCardArgs.putBoolean(ProductAttributes.FAVOURITE, favourite);
            productCardArgs.putString(ProductAttributes.PRODUCT_ID, productId);
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
            availabilityArgs.putString(ProductAttributes.NEXT_RESTOCK_TIMING, restockTime);
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

        //TODO: get isAdmin property from other screens/activity
        final Container<Boolean> isAdmin = new Container(false);
        FirebaseFirestore db;
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        String userid = fAuth.getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();
        Log.i(TAG, "userid is " + userid);
        DocumentReference docRef = db.collection("Users").document(userid);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserHelperClass user = documentSnapshot.toObject(UserHelperClass.class);
                Log.i(TAG, "Is null" + (user == null));
                Log.i(TAG, "Is admin" + user.getIs_admin());
                Log.i(TAG, "Is admin" + user.getUsername());
                isAdmin.set(user.getIs_admin());
                Log.i(TAG, "Is admin in container" + isAdmin.get());
                if (isAdmin.get() == Boolean.TRUE) {
                    Log.i(TAG, "Setting edit btn " + isAdmin.get());
                    btnAdminEditProductDetails.setVisibility(View.VISIBLE);
                    btnAdminEditProductDetails.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intentAdmin = new Intent(ProductScreenActivity.this, AdminFormActivity.class);

                            // Filling intents
                            intentAdmin.putExtra(ProductAttributes.PRODUCT_ID, productId);
                            intentAdmin.putExtra(ProductAttributes.NAME, name);
                            intentAdmin.putExtra(ProductAttributes.DESCRIPTION,description);
                            intentAdmin.putExtra(ProductAttributes.PRICE, price);
                            intentAdmin.putExtra(ProductAttributes.IS_PROMO, isPromo);
                            intentAdmin.putExtra(ProductAttributes.STOCK, inStock);
                            intentAdmin.putExtra(ProductAttributes.DISCOUNT, discountPercent);
                            intentAdmin.putExtra(ProductAttributes.IS_NEW, false);
                            intentAdmin.putExtra(ProductAttributes.IMAGE_URL, imageUrl);
                            startActivity(intentAdmin);

                            Log.i(DEBUG, "name is: " + name);
                            Log.i(DEBUG, "price is: " + price);
                            Log.i(DEBUG, "promotion is: " + discountPercent + "%");
                            Log.i(DEBUG, "product id: " + productId);
                            Log.i(DEBUG, "is promo: " + isPromo);
                            Log.i(DEBUG, "is inStock?" + inStock);
                        }
                    });
                }
            }
        });
    }
}
