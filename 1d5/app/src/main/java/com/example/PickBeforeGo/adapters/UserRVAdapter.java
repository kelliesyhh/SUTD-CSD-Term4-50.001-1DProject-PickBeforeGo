package com.example.PickBeforeGo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo.components.Userpg;

import java.util.ArrayList;

public class UserRVAdapter extends RecyclerView.Adapter<UserRVAdapter.MyViewHolder>{
    private ArrayList<Userpg> userslist;
    private OnNoteListener mOnNoteListener;

    public UserRVAdapter(ArrayList<Userpg> userslist, OnNoteListener onNoteListener){
        this.userslist = userslist;
        this.mOnNoteListener = onNoteListener;

    }

    public interface OnNoteListener {
        void onNoteClick(int position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items_user, parent, false);
        return new MyViewHolder(itemView, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String name = userslist.get(position).getUsername();
        holder.nametxt.setText(name);
    }

    @Override
    public int getItemCount() {

        return userslist.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nametxt;
        OnNoteListener onNoteListener;


        public MyViewHolder(final View view, OnNoteListener onNoteListener){
            super(view);
            nametxt = view.findViewById(R.id.textView);

            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            onNoteListener.onNoteClick(getAdapterPosition());
        }


    }

}
