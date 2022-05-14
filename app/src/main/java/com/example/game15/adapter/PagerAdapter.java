package com.example.game15.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.game15.fragments.Easy;
import com.example.game15.fragments.Expert;
import com.example.game15.fragments.Hard;
import com.example.game15.fragments.Medium;
import com.example.game15.fragments.Normal;

public class PagerAdapter extends FragmentStateAdapter {
    private final Easy easyScreen = new Easy();
    private final Normal normalScreen = new Normal();
    private final Medium mediumScreen = new Medium();
    private final Hard hardScreen = new Hard();
    private final Expert expertScreen = new Expert();

    public PagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0)
            return easyScreen;
        else if (position == 1)
            return normalScreen;
        else if (position == 2)
            return mediumScreen;
        else if (position == 3)
            return hardScreen;
        else return expertScreen;
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
