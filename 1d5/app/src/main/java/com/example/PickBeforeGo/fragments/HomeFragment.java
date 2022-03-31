package com.example.PickBeforeGo.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.PickBeforeGo.MainActivity;
import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo.activities.ProductScreenActivity;
import com.example.PickBeforeGo.components.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
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

    private BottomNavigationView bottom_bar;

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ArrayList productList = new ArrayList<Product>();
        ArrayList promoList = new ArrayList<Product>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference();
        dbRef.child("Product_List").addListenerForSingleValueEvent(new ValueEventListener() {
              @Override
              public void onDataChange(DataSnapshot dataSnapshot) {
                  productList.clear();
                  for (DataSnapshot child : dataSnapshot.getChildren()) {
                      Product product = child.getValue(Product.class);
                      if (product.getIsPromo()){
                          promoList.add(product);
                      }
                      productList.add(product);
                  }
                  int l = promoList.size();
                  addBox(view,inflater,l,promoList);
              }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                    Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }
          });
        return view;
    }

    public void addBox(View view, LayoutInflater inflater, int length, ArrayList<Product> productlist){
//         promotion
        ConstraintLayout home_layout = view.findViewById(R.id.main_home);
        LinearLayout promotion = home_layout.findViewById(R.id.promoLayout);
        for (int i=0; i<length; i++){
            Product product = productlist.get(i);
            View productLayout = inflater.inflate(R.layout.individual_product,null);
            ShapeableImageView prod_main = productLayout.findViewById(R.id.product_main_img);
            Picasso.get().load(product.getImageURL()).into(prod_main);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                int discountPercent = (int) Math.round(product.getDiscountPercent());
                TextView promodetail = productLayout.findViewById(R.id.promodetail);
                promodetail.setText("Promo "+ discountPercent + "%");
            }
            TextView name = productLayout.findViewById(R.id.nameTxt);
            name.setText(product.getProductName());
            int resID = View.generateViewId();
            productLayout.setId(resID);
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(380, 530);
            params.setMargins(50, 0, 20, 0);
            productLayout.setLayoutParams(params);
            productLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(),ProductScreenActivity.class);
                    intent.putExtra(PRICE, product.getPrice());
                    intent.putExtra(NAME, product.getProductName());
                    intent.putExtra(IMAGE_URL, product.getImageURL());
                    intent.putExtra(DESCRIPTION, product.getDescription());
                    intent.putExtra(FAVOURITE, product.getIsFavourite());
                    intent.putExtra(PRODUCT_ID, product.getProductID());
                    intent.putExtra(STOCK, product.getInStock());
                    intent.putExtra(PROMOTION, product.getIsPromo());
                    intent.putExtra(DISCOUNT, (int) Math.round(product.getDiscountPercent()));
                    intent.putExtra(RESTOCK_TIME, product.getNextRestockTime());
                    startActivity(intent);
                }
            });
            promotion.addView(productLayout);
        }
    }
}


