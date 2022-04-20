package com.example.PickBeforeGo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo.components.ProductAttributes;

public class NoStockAvailabilityFragment extends Fragment {

    private String nextRestockTiming;
    TextView txtStock;

    public NoStockAvailabilityFragment() {
        // Required empty public constructor
    }


    public static NoStockAvailabilityFragment newInstance(String restock_timing) {
        NoStockAvailabilityFragment fragment = new NoStockAvailabilityFragment();
        Bundle args = new Bundle();
        args.putString(ProductAttributes.NEXT_RESTOCK_TIMING, restock_timing);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nextRestockTiming = getArguments().getString(ProductAttributes.NEXT_RESTOCK_TIMING);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_availability_no_stock, container, false);
        txtStock = rootView.findViewById(R.id.txtStock);
        txtStock.setText("Next Restock Timing: " + nextRestockTiming);
        return rootView;
    }
}