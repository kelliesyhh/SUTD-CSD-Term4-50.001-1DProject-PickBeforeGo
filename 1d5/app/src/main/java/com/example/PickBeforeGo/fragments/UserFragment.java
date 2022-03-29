package com.example.PickBeforeGo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo.adapters.UserRVAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {
    private String[] userslist;
    private RecyclerView recyclerView;
    private static final String TAG = "user and admin fragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View UserView = inflater.inflate(R.layout.fragment_user, container, false);
        recyclerView = UserView.findViewById(R.id.userslist);
        setUserInfo();
        // this is to set the Recyclerview for the user/admin page
        UserRVAdapter adapter = new UserRVAdapter(userslist, (UserRVAdapter.OnNoteListener) getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        return UserView;
    }

    private void setUserInfo(){
        userslist = new String[]{"Admin", "Points", "Wallet", "Faces", "Guide", "Settings", "Feedback", "Privacy Policy", "Terms & Conditions"};

    }
}