package com.example.PickBeforeGo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.PickBeforeGo.MainActivity;
import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo.activities.ProductScreenActivity;
import com.example.PickBeforeGo.adapters.ProductRVAdapter;
import com.example.PickBeforeGo.components.Product;
import com.example.PickBeforeGo.helper.ProductAttributes;

import java.util.ArrayList;

public class FavouriteFragment extends Fragment {
    ProductRVAdapter.ClickListener clickListener;
    ArrayList<Product> filteredProductsArrayList;
    ProductRVAdapter productRVAdapter;
    ArrayList<Product> productArrayList = new ArrayList<Product>();
    RecyclerView productRV;


    @Override
    public void onStart() {
        super.onStart();
        MainActivity mainActivity = (MainActivity) getActivity();
        productArrayList = mainActivity.getFavouriteProducts();
        productRVAdapter = new ProductRVAdapter(getActivity(), productArrayList, clickListener);
        setRecyclerView(productRV, productArrayList, clickListener);
        productRVAdapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_catalogue,null);
        productRV = rootView.findViewById(R.id.product_rv);
        SearchView searchView = rootView.findViewById(R.id.searchBar);
        MainActivity mainActivity = (MainActivity) getActivity();

//        productArrayList = mainActivity.getFavouriteProducts();

        clickListener = new ProductRVAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, String productName, String imageUrl, String description, String productID, String productPrice, Boolean isFavourite, Boolean inStock, Boolean isPromo, Double discountPercent, String restock_time) {
                Intent intent = new Intent(getActivity(), ProductScreenActivity.class);

                intent.putExtra(ProductAttributes.NAME, productName);
                intent.putExtra(ProductAttributes.IMAGE_URL, imageUrl);
                intent.putExtra(ProductAttributes.DESCRIPTION, description);
                intent.putExtra(ProductAttributes.PRODUCT_ID, productID);
                intent.putExtra(ProductAttributes.PRICE, productPrice);
                intent.putExtra(ProductAttributes.FAVOURITE, isFavourite);
                intent.putExtra(ProductAttributes.STOCK, inStock);
                intent.putExtra(ProductAttributes.IS_PROMO, isPromo);
                intent.putExtra(ProductAttributes.DISCOUNT, (int) Math.round(discountPercent));
                intent.putExtra(ProductAttributes.RESTOCK_TIME, restock_time);
                startActivity(intent);
            }
        };

        productRVAdapter = new ProductRVAdapter(getActivity(), productArrayList, clickListener);
        setRecyclerView(productRV, productArrayList, clickListener);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Product> productArrayList = new ArrayList<Product>();
                productArrayList = mainActivity.getFavouriteProducts();
                filteredProductsArrayList = filterProducts(productArrayList, newText);
                setRecyclerView(productRV, filteredProductsArrayList, clickListener);
                return false;
            }
        });

        productRVAdapter.notifyDataSetChanged();

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
