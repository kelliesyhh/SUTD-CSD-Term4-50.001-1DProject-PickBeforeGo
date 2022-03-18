package com.example.PickBeforeGo.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.PickBeforeGo.fragment.AllFragment;
import com.example.PickBeforeGo.fragment.FavouriteFragment;

public class ViewPageAdapter extends FragmentStateAdapter {

    public ViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        Bundle info = new Bundle();

        switch (position){
            case 0:
                AllFragment allFragment = new AllFragment();
                info.putInt("currentPage",position++);
                allFragment.setArguments(info);
                return allFragment;
            case 1:
                FavouriteFragment favouriteFragment = new FavouriteFragment();
                info.putInt("currentPage",position++);
                favouriteFragment.setArguments(info);
                return favouriteFragment;
            default:
                return new AllFragment();
        }
    }
    @Override
    public int getItemCount() {
        return 2;
    }
}
