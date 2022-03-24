package com.example.PickBeforeGo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.PickBeforeGo.activities.AdminFormActivity;
import com.example.PickBeforeGo.activities.ProductScreenActivity;
import com.example.PickBeforeGo.adapters.UserRVAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements  UserRVAdapter.OnNoteListener{
    private Toolbar topbar;
    private BottomNavigationView bottom_bar;
    private static final String TAG = "Main activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //set up the bottom navigation bar
        bottom_bar = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this,R.id.my_nav);
        NavigationUI.setupWithNavController(bottom_bar,navController);

    }

    public void onNoteClick(int position) {
        Log.d(TAG, "onNoteClick: clicked."+ position);
        if (position == 0){
            Intent intent = new Intent(MainActivity.this, AdminFormActivity.class);
            startActivity(intent);
        }
    }
}