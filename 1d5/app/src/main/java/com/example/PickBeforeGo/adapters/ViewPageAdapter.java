package com.example.PickBeforeGo.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.PickBeforeGo.fragments.CatalogueFragment;
import com.example.PickBeforeGo.fragments.TabFragment;

public class ViewPageAdapter extends FragmentStateAdapter {

    public ViewPageAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

//        CatalogueFragment catalogueFragment = new CatalogueFragment();
//        Bundle args = new Bundle();
//
//        args.putInt(String.valueOf(CatalogueFragment.class), position + 1);
//        catalogueFragment.setArguments(args);
//
//        return catalogueFragment;

        if(position == 0){
            return new CatalogueFragment(0);
        }
        if (position == 1) {
            return new CatalogueFragment(1);
        }
        return new CatalogueFragment(0);
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
