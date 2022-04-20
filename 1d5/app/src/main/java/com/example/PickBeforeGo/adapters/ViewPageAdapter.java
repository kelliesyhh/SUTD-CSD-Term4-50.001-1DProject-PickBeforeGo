package com.example.PickBeforeGo.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.PickBeforeGo.fragments.AllFragment;
import com.example.PickBeforeGo.fragments.FavouriteFragment;

public class ViewPageAdapter extends FragmentStateAdapter {

    public ViewPageAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 0) {return new AllFragment();
        }

        else if (position == 1) {return new FavouriteFragment();}


        return new AllFragment();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return 2;
    } //number of tabs directly linked to MainActivity.java tabTitles
}
