package com.example.PickBeforeGo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo.adapters.ProductRVAdapter;
import com.example.PickBeforeGo.items.Product;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavouriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouriteFragment extends Fragment {

    private RecyclerView productRV;
    private ArrayList<Product> productArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favourite,null);

        ArrayList<Product> productArrayList = new ArrayList<Product>();

        productRV = rootView.findViewById(R.id.product_rv);

        //get data from database and populate catalogue here
        productArrayList.add(new Product("White Bread", "500g", "Gardenia", R.drawable.milk_soap));
        productArrayList.add(new Product("Tissue", "light", "Kleenex", R.drawable.milk_soap));
        productArrayList.add(new Product("Soap", "200g", "Meril", R.drawable.milk_soap));


        ProductRVAdapter productRVAdapter = new ProductRVAdapter(getActivity(), productArrayList);

        int numOfColumns = 2;
        productRV.setLayoutManager(new GridLayoutManager(getActivity(), numOfColumns));

        productRV.setAdapter(productRVAdapter);

        // Inflate the layout for this fragment
        return rootView;
    }
}