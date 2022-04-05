package com.example.PickBeforeGo1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo.components.ProductAttributes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class NoStockProductCardFragment extends Fragment {
    private String product_id;
    private String name;
    private String price;
    private String image_url;
    private boolean favourite;

    public NoStockProductCardFragment() {
        // Required empty public constructor
    }

    public static NoStockProductCardFragment newInstance() {
        NoStockProductCardFragment fragment = new NoStockProductCardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ProductAttributes.NAME);
            price = getArguments().getString(ProductAttributes.PRICE);
            image_url = getArguments().getString(ProductAttributes.IMAGE_URL);
            favourite = getArguments().getBoolean(ProductAttributes.FAVOURITE);
            product_id = getArguments().getString(ProductAttributes.PRODUCT_ID);
            if (price.length() == 4) {
                price += "0";
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            name = getArguments().getString(ProductAttributes.NAME);
            price = getArguments().getString(ProductAttributes.PRICE);
            image_url = getArguments().getString(ProductAttributes.IMAGE_URL);
            favourite = getArguments().getBoolean(ProductAttributes.FAVOURITE);
            product_id = getArguments().getString(ProductAttributes.PRODUCT_ID);
            if (price.length() == 4) {
                price += "0";
            }
        }

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_inner_product_card_no_stock, container, false);

        // set the different things on the product card
        ImageView itemImage = rootView.findViewById(R.id.imgProduct);
        Picasso.get().load(image_url).placeholder(R.drawable.placeholder_product_pic).into(itemImage);

        TextView itemName = rootView.findViewById(R.id.txtProductName);
        itemName.setText(name);

        TextView itemPrice = rootView.findViewById(R.id.txtProductPrice);
        itemPrice.setText(price);

        Button btnFav = rootView.findViewById(R.id.btnFavourite);
        if (favourite) {
            btnFav.setBackgroundResource(R.drawable.ic_favourite_selected);
        }
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // toggle value of favourite: if favourite was originally true, then set it to false. if favourite was originally false, then set it to true.
                favourite = !favourite;
                // update database with new value of favourite
                addingToFavorite(product_id);
                if(favourite){
                    btnFav.setBackgroundResource(R.drawable.ic_favourite_selected);
                }
                else {
                    btnFav.setBackgroundResource(R.drawable.ic_favourite_unselected);
                }
            }
        });
        return rootView;
    }
    private void addingToFavorite(String productID) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Product_List");
        reference.child(productID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean favorite = (boolean) snapshot.child("isFavourite").getValue();
                System.out.println(favorite);
                if (favorite) {
                    reference.child(productID).child("isFavourite").setValue(false);

                } else if (!favorite) {
                    reference.child(productID).child("isFavourite").setValue(true);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }
}
