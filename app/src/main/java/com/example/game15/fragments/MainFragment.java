package com.example.game15.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.game15.R;
import com.example.game15.adapter.PagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainFragment extends Fragment {

    private TabLayout tabLayout;
    private PagerAdapter adapter;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager2 pager = view.findViewById(R.id.viewPager2);
        adapter = new PagerAdapter(getParentFragmentManager(), getLifecycle());

//        view.findViewById(R.id.finish_record).setOnClickListener(v -> {
//            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_playFragment);
//        });
        pager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, pager,
                (tab, position) -> {
                    if (position == 0) tab.setText("3x3");
                    else if (position == 1) tab.setText("4x4");
                    else if (position == 2) tab.setText("5x5");
                    else if (position == 3) tab.setText("6x6");
                    else tab.setText("7x7");
                }).attach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}