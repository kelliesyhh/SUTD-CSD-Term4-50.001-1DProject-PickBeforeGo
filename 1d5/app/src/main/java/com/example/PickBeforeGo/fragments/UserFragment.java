package com.example.PickBeforeGo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo.activities.AdminFormActivity;
import com.example.PickBeforeGo.adapters.UserRVAdapter;
import com.example.PickBeforeGo.components.Userpg;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {
    private ArrayList<Userpg> userslist;
    private RecyclerView recyclerView;
    private static final String TAG = "Main activity";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View UserView = inflater.inflate(R.layout.fragment_user, container, false);
        userslist = new ArrayList<>();
        recyclerView = UserView.findViewById(R.id.userslist);
        setUserInfo();
        setAdapter();

        return UserView;
    }

    private void setUserInfo(){
        userslist.add(new Userpg("Admin"));
        userslist.add(new Userpg("Points"));
        userslist.add(new Userpg("Wallet"));
        userslist.add(new Userpg("Faces"));
        userslist.add(new Userpg("Guide"));
        userslist.add(new Userpg("Settings"));
        userslist.add(new Userpg("Feedback"));
        userslist.add(new Userpg("Privacy Policy"));
        userslist.add(new Userpg("Terms & Conditions"));
    }


    private void setAdapter(){
        UserRVAdapter adapter = new UserRVAdapter(userslist, (UserRVAdapter.OnNoteListener) getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

//    public void onNoteClick(int position) {
//        Log.d(TAG, "onNoteClick: clicked."+ position);
//        if (position == 0){
//            Intent intent = new Intent(getActivity(), AdminFormActivity.class);
//            startActivity(intent);
//        }
//    }
}