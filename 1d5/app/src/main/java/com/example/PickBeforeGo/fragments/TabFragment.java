package com.example.PickBeforeGo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.PickBeforeGo.MainActivity;
import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo.adapters.ViewPageAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabFragment extends Fragment {
    private String tabTitles[] = new String[]{"All", "Favourites"};
    MainActivity mainActivity = (MainActivity) getActivity();
    Integer tabPosition = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab,null);
        TabLayout tabLayout = rootView.findViewById(R.id.tabs);
        ViewPager2 viewPager2 = rootView.findViewById(R.id.viewPager);
        SearchView searchText = rootView.findViewById(R.id.searchBar);

        ViewPageAdapter adapter = new ViewPageAdapter(getActivity());
        viewPager2.setAdapter(adapter);

        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Bundle result = new Bundle();
                result.putString("searchQueryKey", newText);
                result.putInt("tabPositionKey", tabPosition);
                requireActivity().getSupportFragmentManager().setFragmentResult("requestTextKey", result);

                return false;
            }
        });

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy(){
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabTitles[position]);
            }
        }).attach();

        // Inflate the layout for this fragment
        return rootView;
    }

}