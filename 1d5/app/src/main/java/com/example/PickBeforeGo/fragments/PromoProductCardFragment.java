package com.example.PickBeforeGo.fragments;

import android.os.Bundle;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
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
import com.example.PickBeforeGo.helper.PromotionHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PromoProductCardFragment extends Fragment {
    private String product_id;
    private String name;
    private String originalPrice;
    private String discountedPrice;
    private String image_url;
    private boolean favourite;
    private int discountPercent;

    public PromoProductCardFragment() {
        // Required empty public constructor
    }


    public static PromoProductCardFragment newInstance() {
        PromoProductCardFragment fragment = new PromoProductCardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ProductAttributes.NAME);
            originalPrice = getArguments().getString(ProductAttributes.PRICE);
            image_url = getArguments().getString(ProductAttributes.IMAGE_URL);
            favourite = getArguments().getBoolean(ProductAttributes.FAVOURITE);
            product_id = getArguments().getString(ProductAttributes.PRODUCT_ID);
            discountPercent = getArguments().getInt(ProductAttributes.DISCOUNT);
            PromotionHelper promoHelper = new PromotionHelper(originalPrice.substring(1), String.valueOf(discountPercent) + "%");
            discountedPrice = "$" + promoHelper.promoChange();
            if (originalPrice.length() == 4) {
                originalPrice += "0";
            }
            if (discountedPrice.length() == 4) {
                discountedPrice += "0";
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            name = getArguments().getString(ProductAttributes.NAME);
            originalPrice = getArguments().getString(ProductAttributes.PRICE);
            image_url = getArguments().getString(ProductAttributes.IMAGE_URL);
            favourite = getArguments().getBoolean(ProductAttributes.FAVOURITE);
            product_id = getArguments().getString(ProductAttributes.PRODUCT_ID);
            discountPercent = getArguments().getInt(ProductAttributes.DISCOUNT);
            PromotionHelper promoHelper = new PromotionHelper(originalPrice.substring(1), String.valueOf(discountPercent) + "%");
            discountedPrice = "$" + promoHelper.promoChange();
            if (originalPrice.length() == 4) {
                originalPrice += "0";
            }
            if (discountedPrice.length() == 4) {
                discountedPrice += "0";
            }

        }

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_inner_product_card_promo, container, false);

        // set the different things on the product card
        ImageView itemImage = rootView.findViewById(R.id.imgProduct);
        Picasso.get().load(image_url).placeholder(R.drawable.placeholder_product_pic).into(itemImage);

        TextView itemName = rootView.findViewById(R.id.txtProductName);
        itemName.setText(name);

        TextView itemPrice = rootView.findViewById(R.id.txtProductPrice);
        String price = originalPrice + " " + discountedPrice;
        itemPrice.setText(price, TextView.BufferType.SPANNABLE);
        Spannable spannable = (Spannable) itemPrice.getText();
        spannable.setSpan(new StrikethroughSpan(), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        TextView itemPromotion = rootView.findViewById(R.id.txtProductPromo);
        String promotion = "Promo " + String.valueOf(discountPercent) + "%";
        itemPromotion.setText(promotion);

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