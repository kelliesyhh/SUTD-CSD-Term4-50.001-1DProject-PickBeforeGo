package com.example.PickBeforeGo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.PickBeforeGo.MainActivity;
import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo.adapters.ViewPageAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TabFragment extends Fragment {
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



    MainActivity mainActivity = (MainActivity) getActivity();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_catalogue,null);
        SearchView searchText = rootView.findViewById(R.id.searchBar);

        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Bundle result = new Bundle();
                result.putString("searchQueryKey", newText);
                requireActivity().getSupportFragmentManager().setFragmentResult("requestTextKey", result);

                return false;
            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }

}