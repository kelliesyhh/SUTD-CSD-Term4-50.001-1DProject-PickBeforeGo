package com.example.PickBeforeGo.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;

import com.example.PickBeforeGo.R;

public class InStockProductCardFragment extends Fragment {

    // declaration of parameter arguments
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String IMAGE_URL = "image_url";
    private static final String FAVOURITE = "favourite";

    private String name;
    private String price;
    private String imageURL;
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
            imageURL = getArguments().getString(IMAGE_URL);
            favourite = getArguments().getBoolean(FAVOURITE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getArguments() != null) {
            name = getArguments().getString(NAME);
            price = getArguments().getString(PRICE);
            imageURL = getArguments().getString(IMAGE_URL);
            favourite = getArguments().getBoolean(FAVOURITE);
        }

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_inner_product_card_in_stock, container, false);
        TextView itemName = rootView.findViewById(R.id.txtName);
        TextView itemPrice = rootView.findViewById(R.id.txtPrice);
        itemName.setText(name);
        itemPrice.setText(price);
        Button btnFav = rootView.findViewById(R.id.btnFavourite);
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // toggle value of favourite: if favourite was originally true, then set it to false. if favourite was originally false, then set it to true.
                favourite = !favourite;
                // TODO: update database with the updated value of favourite
            }
        });
        return rootView;
    }
}