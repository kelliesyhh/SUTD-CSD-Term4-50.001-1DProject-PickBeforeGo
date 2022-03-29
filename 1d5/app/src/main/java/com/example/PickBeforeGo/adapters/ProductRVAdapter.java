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
                    clickListener.onItemClick(position);
                }
            }
        });
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRVAdapter.Viewholder holder, int position) {
        //set data to textview, imageview of each card layout
        Product product = productArrayList.get(position);
        Picasso.get().load(product.getImageURL()).placeholder(R.drawable.placeholder_product_pic).into(holder.productImg);
        holder.productName.setText(product.getProductName());
        holder.productWeight.setText(product.getWeight());
    }
    
    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        private ImageView productImg;
        private TextView productName, productWeight;

        public Viewholder(@NonNull View itemView){
            super(itemView);
            productImg = itemView.findViewById(R.id.imgProduct);
            productName = itemView.findViewById(R.id.txtProductName);
            productWeight = itemView.findViewById(R.id.txtProductWeight);
        }
    }

    public interface ClickListener {
        void onItemClick(int position);
    }
}