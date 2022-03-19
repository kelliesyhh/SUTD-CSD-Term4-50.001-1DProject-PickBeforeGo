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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabFragment extends Fragment {

    private final int position;
    private RecyclerView productRV;
    private ArrayList<Product> productArrayList;

    public TabFragment(int position){
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab,null);

        ArrayList<Product> productArrayList = new ArrayList<Product>();

        productRV = rootView.findViewById(R.id.product_rv);

        //All Tab
        if (position == 0){
            //get data from database and populate catalogue here
            productArrayList.add(new Product("White Bread", "500g", "Gardenia", R.drawable.milk_soap));
            productArrayList.add(new Product("Tissue", "light", "Kleenex", R.drawable.tissue));
            productArrayList.add(new Product("Soap", "200g", "Meril", R.drawable.milk_soap));
            productArrayList.add(new Product("BOY", "TALL", "HUMAN", R.drawable.bread));
            productArrayList.add(new Product("BOY", "TALL", "HUMAN", R.drawable.bread));
            productArrayList.add(new Product("BOY", "TALL", "HUMAN", R.drawable.bread));
            productArrayList.add(new Product("BOY", "TALL", "HUMAN", R.drawable.bread));
        }
        // Favourites Tab
        else if (position == 1){
            //get data from database and populate catalogue here
            productArrayList.add(new Product("White Bread", "500g", "Gardenia", R.drawable.milk_soap));
            productArrayList.add(new Product("Tissue", "light", "Kleenex", R.drawable.milk_soap));
            productArrayList.add(new Product("Soap", "200g", "Meril", R.drawable.milk_soap));
        }

        ProductRVAdapter productRVAdapter = new ProductRVAdapter(getActivity(), productArrayList);

        int numOfColumns = 2;
        productRV.setLayoutManager(new GridLayoutManager(getActivity(), numOfColumns));

        productRV.setAdapter(productRVAdapter);

        // Inflate the layout for this fragment
        return rootView;
    }
}