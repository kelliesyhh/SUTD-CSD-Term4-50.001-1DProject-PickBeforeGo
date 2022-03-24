package com.example.PickBeforeGo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo.adapters.ProductRVAdapter;
import com.example.PickBeforeGo.components.Product;

import java.util.ArrayList;

public class CatalogueFragment extends Fragment {
    private final int position;
    private ArrayList<Product> productArrayList;

    public CatalogueFragment(int position){
        this.position = position;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_catalogue,null);

        ArrayList<Product> productArrayList = new ArrayList<Product>();

        RecyclerView productRV = rootView.findViewById(R.id.product_rv);

        //All Tab
        if (position == 0){

            productArrayList.add(new Product.ProductBuilder().setProductName("White Bread").setDescription("Gardenia").setWeight("500g").build());
            productArrayList.add(new Product.ProductBuilder().setProductName("White Bread").setDescription("Gardenia").setWeight("500g").build());
            productArrayList.add(new Product.ProductBuilder().setProductName("White Bread").setDescription("Gardenia").setWeight("500g").build());
            productArrayList.add(new Product.ProductBuilder().setProductName("White Bread").setDescription("Gardenia").setWeight("500g").build());
            productArrayList.add(new Product.ProductBuilder().setProductName("White Bread").setDescription("Gardenia").setWeight("500g").build());
        }

        // Favourites Tab
        else if (position == 1){
            //get data from database and populate catalogue here
            productArrayList.add(new Product.ProductBuilder().setProductName("White Bread").setDescription("Gardenia").setWeight("500g").build());
            productArrayList.add(new Product.ProductBuilder().setProductName("White Bread").setDescription("Gardenia").setWeight("500g").build());
            productArrayList.add(new Product.ProductBuilder().setProductName("White Bread").setDescription("Gardenia").setWeight("500g").build());
            productArrayList.add(new Product.ProductBuilder().setProductName("White Bread").setDescription("Gardenia").setWeight("500g").build());
            productArrayList.add(new Product.ProductBuilder().setProductName("White Bread").setDescription("Gardenia").setWeight("500g").build());


        }

        ProductRVAdapter productRVAdapter = new ProductRVAdapter(getActivity(), productArrayList);

        int numOfColumns = 2;
        productRV.setLayoutManager(new GridLayoutManager(getActivity(), numOfColumns));

        productRV.setAdapter(productRVAdapter);

        // Inflate the layout for this fragment
        return rootView;
    }
}
