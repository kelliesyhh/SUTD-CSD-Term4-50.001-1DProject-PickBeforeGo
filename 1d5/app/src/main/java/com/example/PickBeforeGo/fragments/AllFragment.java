package com.example.PickBeforeGo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.example.PickBeforeGo.activities.ProductScreenActivity;
import com.example.PickBeforeGo.adapters.ProductRVAdapter;
import com.example.PickBeforeGo.components.Product;

import java.util.ArrayList;

public class AllFragment extends Fragment {
    private static final String NAME = "name";
    private static final String IMAGE_URL = "image_url";
    private static final String DESCRIPTION = "description";
    private static final String PRODUCT_ID = "product_id";
    private String searchQuery;
    ProductRVAdapter.ClickListener clickListener;
    ArrayList<Product> filteredProductsArrayList;
    ProductRVAdapter productRVAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_catalogue,null);
        ArrayList<Product> productArrayList = new ArrayList<Product>();
        RecyclerView productRV = rootView.findViewById(R.id.product_rv);
        MainActivity mainActivity = (MainActivity) getActivity();

        productArrayList = mainActivity.getAllProducts();

        // onclick for RV
        clickListener = new ProductRVAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, String productName, String imageUrl, String description, String productID) {
                Intent intent = new Intent(getActivity(), ProductScreenActivity.class);
                intent.putExtra(NAME, productName);
                intent.putExtra(IMAGE_URL, imageUrl);
                intent.putExtra(DESCRIPTION, description);
                intent.putExtra(PRODUCT_ID, productID);
                startActivity(intent);
            }
        };


        productRVAdapter = new ProductRVAdapter(getActivity(), productArrayList, clickListener);
        setRecyclerView(productRV, productArrayList, clickListener);

        requireActivity().getSupportFragmentManager().setFragmentResultListener("requestTextKey", this, new FragmentResultListener() {

            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                searchQuery = bundle.getString("searchQueryKey");

                ArrayList<Product> productArrayList = new ArrayList<Product>();


                productArrayList = mainActivity.getAllProducts();
                filteredProductsArrayList = filterProducts(productArrayList, searchQuery);

                setRecyclerView(productRV, filteredProductsArrayList, clickListener);
            }
        });

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                productRVAdapter.notifyDataSetChanged();
            }
        });

        return rootView;
    }

    private void setRecyclerView(RecyclerView productRV, ArrayList<Product> productArrayList, ProductRVAdapter.ClickListener clickListener){
        ProductRVAdapter productRVAdapter = new ProductRVAdapter(getActivity(), productArrayList, clickListener);
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
