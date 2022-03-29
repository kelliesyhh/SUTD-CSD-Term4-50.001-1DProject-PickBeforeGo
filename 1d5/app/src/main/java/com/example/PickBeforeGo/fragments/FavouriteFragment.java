package com.example.PickBeforeGo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.PickBeforeGo.MainActivity;
import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo.adapters.ProductRVAdapter;
import com.example.PickBeforeGo.components.Product;

import java.util.ArrayList;

public class FavouriteFragment extends Fragment {
    private String searchQuery;
    ArrayList<Product> filteredProductsArrayList;
    ProductRVAdapter productRVAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_catalogue,null);
        ArrayList<Product> productArrayList = new ArrayList<Product>();
        RecyclerView productRV = rootView.findViewById(R.id.product_rv);
        MainActivity mainActivity = (MainActivity) getActivity();

        productArrayList = mainActivity.getFavouriteProducts();
        productRVAdapter = new ProductRVAdapter(getActivity(), productArrayList);
        setRecyclerView(productRV, productArrayList);

        requireActivity().getSupportFragmentManager().setFragmentResultListener("requestTextKey", this, new FragmentResultListener() {

            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                searchQuery = bundle.getString("searchQueryKey");

                ArrayList<Product> productArrayList = new ArrayList<Product>();


                productArrayList = mainActivity.getFavouriteProducts();
                filteredProductsArrayList = filterProducts(productArrayList, searchQuery);

                setRecyclerView(productRV, filteredProductsArrayList);
            }
        });

        productRVAdapter.notifyDataSetChanged();

        return rootView;
    }

    private void setRecyclerView(RecyclerView productRV, ArrayList<Product> productArrayList){
        ProductRVAdapter productRVAdapter = new ProductRVAdapter(getActivity(), productArrayList);
        int numOfColumns = 2;
        productRV.setLayoutManager(new GridLayoutManager(getActivity(), numOfColumns));
        productRV.setAdapter(productRVAdapter);
    }

    private ArrayList<Product> filterProducts(ArrayList<Product> productArrayList, String searchQuery){
        ArrayList<Product> filteredProductsArrayList = new ArrayList<Product>();
        if (!searchQuery.isEmpty()){
            for (Product product : productArrayList){
                if (product.getProductName().toLowerCase().contains(searchQuery.toLowerCase())){
                    filteredProductsArrayList.add(product);
                }
            }
        }
        else { filteredProductsArrayList = productArrayList;}

        return filteredProductsArrayList;
    }
}
