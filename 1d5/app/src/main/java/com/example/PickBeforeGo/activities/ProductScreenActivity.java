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

import com.example.PickBeforeGo.MainActivity;
import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo.helper.Container;
import com.example.PickBeforeGo.helper.UserHelperClass;
import com.example.PickBeforeGo.components.Product;
import com.example.PickBeforeGo.components.ProductAttributes;
import com.example.PickBeforeGo.fragments.InStockAvailabilityFragment;
import com.example.PickBeforeGo.fragments.InStockProductCardFragment;
import com.example.PickBeforeGo.fragments.NoStockAvailabilityFragment;
import com.example.PickBeforeGo.fragments.NoStockProductCardFragment;
import com.example.PickBeforeGo.fragments.PromoProductCardFragment;
import com.example.PickBeforeGo.components.ProductAttributes;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class ProductScreenActivity extends AppCompatActivity {
    private static final String PRODUCT_ID = "product_id";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String IMAGE_URL = "image_url";
    private static final String FAVOURITE = "favourite";
    private static final String DESCRIPTION = "description";
    private static final String STOCK = "in_stock";
    private static final String PROMOTION = "promotion";
    private static final String DISCOUNT = "discount";
    private static final String RESTOCK_TIME = "restock_time";
    private static final String PROMOTION_FULL = "promotion_text";

    private String product_id;
    private String name;
    private String price;
    private String image_url;
    private Boolean favourite, inStock, isPromo;
    private String description;
    private int discountPercent;
    private String restockTime;
    private String TAG = "TAG";

    FragmentTransaction fragmentTransaction;

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
        name = args.getString(NAME);
        image_url = args.getString(IMAGE_URL);
        description = args.getString(DESCRIPTION);
        product_id = args.getString(PRODUCT_ID);
        price = "$" + args.getString(PRICE);
        favourite = args.getBoolean(FAVOURITE);

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

        inStock = args.getBoolean(STOCK);
        isPromo = args.getBoolean(PROMOTION);
        discountPercent = args.getInt(DISCOUNT);
        restockTime = args.getString(RESTOCK_TIME);

        // replace fragments (if necessary) based on boolean values
        if (isPromo) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();

            productCardFragment = new PromoProductCardFragment();
            productCardArgs = new Bundle();
            productCardArgs.putString(NAME, name);
            productCardArgs.putString(PRICE, price);
            productCardArgs.putString(IMAGE_URL, image_url);
            productCardArgs.putBoolean(FAVOURITE, favourite);
            productCardArgs.putString(PRODUCT_ID, product_id);
            productCardArgs.putInt(DISCOUNT, discountPercent);
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
            availabilityArgs.putString(RESTOCK_TIME, restockTime);
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
                Log.i(TAG, "Is null" + Boolean.toString(user == null));
                Log.i(TAG, "Is admin" + user.getIs_admin());
                Log.i(TAG, "Is admin" + user.getUsername());
                isAdmin.set(user.getIs_admin());
                Log.i(TAG, "Is admin in container" + Boolean.toString(isAdmin.get()));
                if (isAdmin.get() == Boolean.TRUE) {
                    Log.i(TAG, "Setting edit btn " + Boolean.toString(isAdmin.get()));
                    btnAdminEditProductDetails.setVisibility(View.VISIBLE);
                    btnAdminEditProductDetails.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // TODO: add in intent putExtras if needed, to populate the various fields for editing
                            Intent intentAdmin = new Intent(ProductScreenActivity.this, AdminFormActivity.class);

                            // Filling intents
                            intentAdmin.putExtra(ProductAttributes.PRODUCT_ID, product_id);
                            intentAdmin.putExtra(ProductAttributes.NAME, name);
                            intentAdmin.putExtra(ProductAttributes.DESCRIPTION,description);
                            intentAdmin.putExtra(ProductAttributes.PRICE, price);
                            intentAdmin.putExtra(ProductAttributes.IS_PROMO, isPromo);
                            intentAdmin.putExtra(ProductAttributes.STOCK, inStock);
                            intentAdmin.putExtra(ProductAttributes.DISCOUNT, discountPercent);
                            intentAdmin.putExtra(ProductAttributes.IS_NEW, false);
                            intentAdmin.putExtra(ProductAttributes.IMAGE_URL, image_url);
                            startActivity(intentAdmin);


                            // dembird testing zone
                            System.out.println("name is: " + name);
                            System.out.println("price is: " + price);
                            System.out.println("promotion is: " + discountPercent + "%");
                            System.out.println("product id: " + product_id);
                            System.out.println("is promo: " + isPromo);
                            System.out.println("is inStock?" + inStock);


                        }
                    });
                }
            }
        });
    }
}
