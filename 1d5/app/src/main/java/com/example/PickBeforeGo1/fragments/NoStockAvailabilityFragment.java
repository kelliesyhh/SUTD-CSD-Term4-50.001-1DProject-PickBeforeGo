package com.example.PickBeforeGo1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.PickBeforeGo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoStockAvailabilityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoStockAvailabilityFragment extends Fragment {

    // declaration of parameter arguments
    private static final String RESTOCK_TIMING = "restock_timing";

    private String restock_timing;

    public NoStockAvailabilityFragment() {
        // Required empty public constructor
    }


    public static NoStockAvailabilityFragment newInstance(String restock_timing) {
        NoStockAvailabilityFragment fragment = new NoStockAvailabilityFragment();
        Bundle args = new Bundle();
        args.putString(RESTOCK_TIMING, restock_timing);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            restock_timing = getArguments().getString(RESTOCK_TIMING);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_availability_no_stock, container, false);
    }
}