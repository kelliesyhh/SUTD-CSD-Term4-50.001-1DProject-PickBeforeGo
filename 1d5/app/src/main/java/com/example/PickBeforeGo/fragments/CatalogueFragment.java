package com.example.PickBeforeGo.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.PickBeforeGo.MainActivity;
import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo.adapters.ProductRVAdapter;
import com.example.PickBeforeGo.components.Product;

import java.util.ArrayList;

public class CatalogueFragment extends Fragment {
    private final int position;
    private ArrayList<Product> productArrayList = new ArrayList<Product>();

    public CatalogueFragment(int position){
        this.position = position;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_catalogue,null);

        ArrayList<Product> productArrayList = new ArrayList<Product>();

        RecyclerView productRV = rootView.findViewById(R.id.product_rv);

        MainActivity mainActivity = (MainActivity) getActivity();

        //TODO change favourite status and update the below code for testing only
        if (mainActivity.getFavouriteProducts().isEmpty()) {
            mainActivity.getAllProducts().get(0).setIsFavourite(true);
            mainActivity.getFavouriteProducts().add(mainActivity.getAllProducts().get(0));
            System.out.println(mainActivity.getAllProducts().get(0).getIsFavorite());
            System.out.println(mainActivity.getFavouriteProducts().size());
        }


        //All Tab
        if (position == 0){
            productArrayList = mainActivity.getAllProducts();
        }

        // Favourites Tab
        else if (position == 1){
            productArrayList = mainActivity.getFavouriteProducts();
        }

        ProductRVAdapter productRVAdapter = new ProductRVAdapter(getActivity(), productArrayList);

        int numOfColumns = 2;
        productRV.setLayoutManager(new GridLayoutManager(getActivity(), numOfColumns));

        productRV.setAdapter(productRVAdapter);

        // Inflate the layout for this fragment
        return rootView;
    }
}
