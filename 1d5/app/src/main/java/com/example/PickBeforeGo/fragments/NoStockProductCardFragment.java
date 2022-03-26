package com.example.PickBeforeGo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.PickBeforeGo.R;

public class NoStockProductCardFragment extends Fragment {

    // declaration of parameter arguments
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String IMAGE_URL = "image_url";
    private static final String FAVOURITE = "favourite";

    private String name;
    private String price;
    private String imageURL;
    private boolean favourite;

    public NoStockProductCardFragment() {
        // Required empty public constructor
    }

    public static NoStockProductCardFragment newInstance(String name, String price, String image_url, boolean favourite) {
        NoStockProductCardFragment fragment = new NoStockProductCardFragment();
        Bundle args = new Bundle();
        args.putString(NAME, name);
        args.putString(PRICE, price);
        args.putString(IMAGE_URL, image_url);
        args.putBoolean(FAVOURITE, favourite);
        fragment.setArguments(args);
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_inner_product_card_no_stock, container, false);
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