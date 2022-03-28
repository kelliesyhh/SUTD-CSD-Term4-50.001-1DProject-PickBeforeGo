package com.example.PickBeforeGo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo.components.Product;

import java.util.ArrayList;

public class ProductRVAdapter extends RecyclerView.Adapter<ProductRVAdapter.Viewholder> {

    private Context context;
    private ArrayList<Product> productArrayList;

    //constructor
    public ProductRVAdapter(@NonNull Context context, ArrayList<Product> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
    }

    @NonNull
    @Override
    public ProductRVAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //to inflate layout for each item of recycler view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRVAdapter.Viewholder holder, int position) {
        //set data to textview, imageview of each card layout
        Product product = productArrayList.get(position);
//        holder.productImg.setImageResource(product.getImageURL());
        holder.product.setText(product.getProductName());
        holder.brand.setText(product.getDescription());
        holder.weight.setText(product.getWeight());
    }
    
    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        private ImageView productImg;
        private TextView product, brand, weight;

        public Viewholder(@NonNull View itemView){
            super(itemView);
            productImg = itemView.findViewById(R.id.product_img);
            product = itemView.findViewById(R.id.name_text);
            brand = itemView.findViewById(R.id.brand_text);
            weight = itemView.findViewById(R.id.weight_text);
        }
    }
}