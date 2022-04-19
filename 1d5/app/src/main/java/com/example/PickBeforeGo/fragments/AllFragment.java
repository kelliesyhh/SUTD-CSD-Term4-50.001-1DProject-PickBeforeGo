package com.example.PickBeforeGo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.PickBeforeGo.MainActivity;
import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo.activities.AdminFormActivity;
import com.example.PickBeforeGo.activities.ProductScreenActivity;
import com.example.PickBeforeGo.adapters.ProductRVAdapter;
import com.example.PickBeforeGo.components.Product;
import com.example.PickBeforeGo.components.ProductAttributes;
import com.example.PickBeforeGo.helper.Container;
import com.example.PickBeforeGo.helper.UserHelperClass;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AllFragment extends Fragment {
    private static final String NAME = "name";
    private static final String IMAGE_URL = "image_url";
    private static final String DESCRIPTION = "description";
    private static final String PRODUCT_ID = "product_id";
    private static final String PRICE = "price";
    private static final String FAVOURITE = "favourite";
    private static final String STOCK = "in_stock";
    private static final String PROMOTION = "promotion";
    private static final String DISCOUNT = "discount";
    private static final String RESTOCK_TIME = "restock_time";

    ProductRVAdapter.ClickListener clickListener;
    ArrayList<Product> filteredProductsArrayList;
    ProductRVAdapter productRVAdapter;
    ArrayList<Product> productArrayList = new ArrayList<Product>();
    RecyclerView productRV;

    @Override
    public void onStart() {
        super.onStart();
        MainActivity mainActivity = (MainActivity) getActivity();
        productArrayList = mainActivity.getAllProducts();
        productRVAdapter = new ProductRVAdapter(getActivity(), productArrayList, clickListener);
        setRecyclerView(productRV, productArrayList, clickListener);
        productRVAdapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_catalogue,null);
        SearchView searchView = rootView.findViewById(R.id.searchBar);
        productRV = rootView.findViewById(R.id.product_rv);
        FloatingActionButton adminFloatingButton = rootView.findViewById(R.id.admin_fab);
        MainActivity mainActivity = (MainActivity) getActivity();

        clickListener = new ProductRVAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, String productName, String imageUrl, String description, String productID, String productPrice, Boolean isFavourite, Boolean inStock, Boolean isPromo, Double discountPercent, String restock_time) {
                Intent intent = new Intent(getActivity(), ProductScreenActivity.class);
                System.out.println("item click all frag " + productPrice);
                intent.putExtra(NAME, productName);
                intent.putExtra(IMAGE_URL, imageUrl);
                intent.putExtra(DESCRIPTION, description);
                intent.putExtra(PRODUCT_ID, productID);
                intent.putExtra(PRICE, productPrice);
                intent.putExtra(FAVOURITE, isFavourite);
                intent.putExtra(STOCK, inStock);
                intent.putExtra(PROMOTION, isPromo);
                intent.putExtra(DISCOUNT, (int) Math.round(discountPercent));
                intent.putExtra(RESTOCK_TIME, restock_time);
                startActivity(intent);
            }
        };
        productRVAdapter = new ProductRVAdapter(getActivity(), productArrayList, clickListener);
        setRecyclerView(productRV, productArrayList, clickListener);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Product> productArrayList = new ArrayList<Product>();
                productArrayList = mainActivity.getAllProducts();
                filteredProductsArrayList = filterProducts(productArrayList, newText);
                setRecyclerView(productRV, filteredProductsArrayList, clickListener);

                return false;
            }
        });

        productRVAdapter.notifyDataSetChanged();

        //TODO: get isAdmin property from other screens/activity

        final Container<Boolean> isAdmin = new Container(false);
        FirebaseFirestore db;
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        String userid = fAuth.getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Users").document(userid);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserHelperClass user = documentSnapshot.toObject(UserHelperClass.class);
                isAdmin.set(user.getIs_admin());
                if (isAdmin.get() == Boolean.TRUE) {
                    adminFloatingButton.setVisibility(View.VISIBLE);
                    adminFloatingButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // TODO: add in intent putExtras if needed, to populate the various fields for editing
                            Intent intent = new Intent(getActivity(), AdminFormActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
        return rootView;
    }

    private void setRecyclerView(RecyclerView productRV, ArrayList<Product> productArrayList, ProductRVAdapter.ClickListener clickListener){
        ProductRVAdapter productRVAdapter = new ProductRVAdapter(getActivity(), productArrayList, clickListener);
        int numOfColumns = 2;
        productRV.setLayoutManager(new GridLayoutManager(getActivity(), numOfColumns));
        productRV.setAdapter(productRVAdapter);
    }

    private ArrayList<Product> filterProducts(ArrayList<Product> productArrayList, String searchQuery){
        ArrayList<Product> filteredProductsArrayList = new ArrayList<Product>();
        if (!searchQuery.isEmpty()){
            for (Product product : productArrayList){
                if (product.getProductName()!= null && product.getProductName().toLowerCase().contains(searchQuery.toLowerCase())){
                    filteredProductsArrayList.add(product);
                }
            }
        }
        else { filteredProductsArrayList = productArrayList;}

        return filteredProductsArrayList;
    }
}
