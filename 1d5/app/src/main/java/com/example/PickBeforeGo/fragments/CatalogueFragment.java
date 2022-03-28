package com.example.PickBeforeGo.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.PickBeforeGo.MainActivity;
import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo.adapters.ProductRVAdapter;
import com.example.PickBeforeGo.adapters.ViewPageAdapter;
import com.example.PickBeforeGo.components.Product;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class CatalogueFragment extends Fragment {
    private int position;
    private String searchQuery;
    ArrayList<Product> filteredProductsArrayList;

    public CatalogueFragment(){}

    public CatalogueFragment(int position){this.position = position;}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_catalogue,null);
        ArrayList<Product> productArrayList = new ArrayList<Product>();
        RecyclerView productRV = rootView.findViewById(R.id.product_rv);
        MainActivity mainActivity = (MainActivity) getActivity();
        ProductRVAdapter productRVAdapter = new ProductRVAdapter(getActivity(), productArrayList);

        //TODO change favourite status and update the below code for testing only

        //get the SearchView text result
        requireActivity().getSupportFragmentManager().setFragmentResultListener("requestTextKey", this, new FragmentResultListener() {

            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                searchQuery = bundle.getString("searchQueryKey");
                Integer tabPosition = bundle.getInt("tabPositionKey");
                System.out.println("onCreate of CatalogueFragment");
                System.out.println("result is " + searchQuery);

                //do the filtering everything text changes
                ArrayList<Product> productArrayList = new ArrayList<Product>();

                System.out.println("position is: " + tabPosition);
                //position does not get updated

                if (tabPosition == 0){
                    productArrayList = mainActivity.getAllProducts();
                    System.out.println(productArrayList.size());
                    System.out.println("searchQuery is: " + searchQuery);
                    filteredProductsArrayList = filterProducts(productArrayList, searchQuery);
                }

                else if (tabPosition == 1){
                    productArrayList = mainActivity.getFavouriteProducts();
                    filteredProductsArrayList = filterProducts(productArrayList, searchQuery);
                }

                //this part works
                setRecyclerView(productRV, filteredProductsArrayList);
                productRVAdapter.notifyDataSetChanged();

            }
        });

        //initialise initial products
        if (position == 0){
            productArrayList = mainActivity.getAllProducts();
        }
        else if (position == 1){
            productArrayList = mainActivity.getFavouriteProducts();
        }

        setRecyclerView(productRV, productArrayList);

        // Inflate the layout for this fragment
        return rootView;
    };


    private void setRecyclerView(RecyclerView productRV, ArrayList<Product> productArrayList){
        ProductRVAdapter productRVAdapter = new ProductRVAdapter(getActivity(), productArrayList);
        int numOfColumns = 2;
        productRV.setLayoutManager(new GridLayoutManager(getActivity(), numOfColumns));
        productRV.setAdapter(productRVAdapter);

        System.out.println("RV size is: " + productRVAdapter.getItemCount());
    }

    private ArrayList<Product> filterProducts(ArrayList<Product> productArrayList, String searchQuery){
        ArrayList<Product> filteredProductsArrayList = new ArrayList<Product>();
        if (!searchQuery.isEmpty()){
            for (Product product : productArrayList){
                System.out.println("searchQuery lowercase is: " + searchQuery.toLowerCase());
                System.out.println("product lowercase is : " + product.getProductName().toLowerCase());
                if (product.getProductName().toLowerCase().contains(searchQuery.toLowerCase())){
                    filteredProductsArrayList.add(product);
                }
            }
        }
        else { filteredProductsArrayList = productArrayList;}

        return filteredProductsArrayList;
    }
}
