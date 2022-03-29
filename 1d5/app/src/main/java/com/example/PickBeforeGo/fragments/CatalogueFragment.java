package com.example.PickBeforeGo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.PickBeforeGo.MainActivity;
import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo.activities.ProductScreenActivity;
import com.example.PickBeforeGo.adapters.ProductRVAdapter;
import com.example.PickBeforeGo.components.Product;
import com.example.PickBeforeGo.helper.GetProduct_helper;

import java.util.ArrayList;

public class CatalogueFragment extends Fragment {
    private final int position;
    private ArrayList<Product> productArrayList = new ArrayList<Product>();

    private static final String NAME = "name";
    private static final String IMAGE_URL = "image_url";
    private static final String DESCRIPTION = "description";

    public CatalogueFragment(int position){
        this.position = position;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_catalogue,null);

        ArrayList<Product> productArrayList = new ArrayList<Product>();

        RecyclerView productRV = rootView.findViewById(R.id.product_rv);

        //TODO to get All products and can do sorting uh
        MainActivity main_activity = (MainActivity) getActivity();


        //All Tab
        if (position == 0){
//            assert main_activity != null;
            productArrayList = main_activity.getAllProducts();
        }

        // Favourites Tab
        else if (position == 1){
            productArrayList = main_activity.getFavouriteProducts();
        }

        // onclick for RV
        ProductRVAdapter.ClickListener clickListener = new ProductRVAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, String productName, String imageUrl, String description) {
                Intent intent = new Intent(getActivity(), ProductScreenActivity.class);
                intent.putExtra(NAME, productName);
                intent.putExtra(IMAGE_URL, imageUrl);
                intent.putExtra(DESCRIPTION, description);
                startActivity(intent);
            }
        };

        ProductRVAdapter productRVAdapter = new ProductRVAdapter(getActivity(), productArrayList, clickListener);

        int numOfColumns = 2;
        productRV.setLayoutManager(new GridLayoutManager(getActivity(), numOfColumns));

        productRV.setAdapter(productRVAdapter);

        // Inflate the layout for this fragment
        return rootView;
    }
}
