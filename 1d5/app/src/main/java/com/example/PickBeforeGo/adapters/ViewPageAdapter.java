package com.example.PickBeforeGo.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.PickBeforeGo.fragments.TabFragment;

public class ViewPageAdapter extends FragmentStateAdapter {

    public ViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

    //actually need Bundle for what sia
//        Bundle info = new Bundle();

        if(position == 0){
            return new TabFragment(0);
        }
        if (position == 1) {
            return new TabFragment(1);
        }
        return new TabFragment(0);
    }
    @Override
    public int getItemCount() {
        return 2;
    } //number of tabs directly linked to MainActivity.java tabTitles
}
