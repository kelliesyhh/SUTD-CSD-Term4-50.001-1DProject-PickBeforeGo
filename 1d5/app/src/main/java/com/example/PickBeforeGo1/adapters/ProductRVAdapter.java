package com.example.PickBeforeGo1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo1.components.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductRVAdapter extends RecyclerView.Adapter<ProductRVAdapter.Viewholder> {

    private Context context;
    private ArrayList<Product> productArrayList;
    private final ClickListener clickListener;

    //constructor
    public ProductRVAdapter(@NonNull Context context, ArrayList<Product> productArrayList, ClickListener clickListener) {
        this.context = context;
        this.productArrayList = productArrayList;
        this.clickListener = clickListener;
    }

    //filter results base on search
    public void filterProducts (ArrayList<Product> filterproducts) {

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductRVAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //to inflate layout for each item of recycler view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card,parent,false);
        Viewholder viewholder = new Viewholder(view);
        viewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewholder.getAdapterPosition();
                if (clickListener != null) {
                    clickListener.onItemClick(position, viewholder.productName, viewholder.imageUrl, viewholder.productDescription);
                }
            }
        });
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRVAdapter.Viewholder holder, int position) {
        //set data to textview, imageview of each card layout
        Product product = productArrayList.get(position);
        holder.productName = product.getProductName();
        holder.productWeight = product.getWeight();
        holder.productDescription = product.getDescription();
        holder.imageUrl = product.getImageURL();
        Picasso.get().load(holder.imageUrl).placeholder(R.drawable.placeholder_product_pic).into(holder.imgProduct);
        holder.txtProductName.setText(holder.productName);
        holder.txtProductWeight.setText(holder.productWeight);
    }
    
    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        private ImageView imgProduct;
        private TextView txtProductName, txtProductWeight;
        private String productName, productWeight, imageUrl, productDescription;

        public Viewholder(@NonNull View itemView){
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtProductWeight = itemView.findViewById(R.id.txtProductWeight);
        }
    }

    public interface ClickListener {
        void onItemClick(int position, String productName, String imageUrl, String description);
    }
}