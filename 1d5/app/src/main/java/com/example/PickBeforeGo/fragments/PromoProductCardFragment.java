package com.example.PickBeforeGo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.PickBeforeGo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PromoProductCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PromoProductCardFragment extends Fragment {

    public PromoProductCardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // TODO: if pass in info that there is no stock, use this return inflater.inflate(R.layout.fragment_product_card_no_stock, container, false);
        return inflater.inflate(R.layout.fragment_inner_product_card_promo, container, false);
    }
}