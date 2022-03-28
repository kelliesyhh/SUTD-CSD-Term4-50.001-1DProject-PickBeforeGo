package com.example.PickBeforeGo.fragments;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import com.squareup.picasso.Picasso;

public class InStockProductCardFragment extends Fragment {

    // declaration of parameter arguments
    private static final String PRODUCT_ID = "product_id";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String IMAGE_URL = "image_url";
    private static final String FAVOURITE = "favourite";

    private String product_id;
    private String name;
    private String price;
    private String image_url;
    private boolean favourite;

    public InStockProductCardFragment() {
        // Required empty public constructor
    }


    public static InStockProductCardFragment newInstance() {
        InStockProductCardFragment fragment = new InStockProductCardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(NAME);
            price = getArguments().getString(PRICE);
            image_url = getArguments().getString(IMAGE_URL);
            favourite = getArguments().getBoolean(FAVOURITE);
            product_id = getArguments().getString(PRODUCT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_inner_product_card_in_stock, container, false);

        // set the different things on the product card
        ImageView itemImage = rootView.findViewById(R.id.imgProduct);
        Picasso.get().load(image_url).into(itemImage);

        TextView itemName = rootView.findViewById(R.id.txtProductName);
        itemName.setText(name);

        TextView itemPrice = rootView.findViewById(R.id.txtProductPrice);
        itemPrice.setText(price);

        Button btnFav = rootView.findViewById(R.id.btnFavourite);
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // toggle value of favourite: if favourite was originally true, then set it to false. if favourite was originally false, then set it to true.
                favourite = !favourite;
                // update database with new value of favourite. commenting out cuz doesn't work
                // TODO: update database
                // addingToFavorite(product_id);
            }
        });
        return rootView;
    }
//    private void addingToFavorite(String productID) {
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Products");
//        reference.child(productID).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                boolean favorite = (boolean) snapshot.child("favourite").getValue();
//                System.out.println(favorite);
//                if (favorite) {
//                    reference.child(productID).child("favourite").setValue(false);
//
//                } else if (!favorite) {
//                    reference.child(productID).child("favourite").setValue(true);
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                throw error.toException();
//            }
//        });
//    }
//}
}
