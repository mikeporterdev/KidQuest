package com.michael.kidquest.widget;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.michael.kidquest.server.PresetQuestFragment;

/**
 * Created by m_por on 16/02/2016.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter{
    private static final String TAG = "ViewPagerAdapter";
    CharSequence titles[]={"Staff Pick", "Trending"};
    int numberOfTabs = 2;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        Fragment fragment = new PresetQuestFragment();
        switch (position){
            case 0:
                bundle.putString("URL", "quests/staff_pick");
                fragment.setArguments(bundle);
                return fragment;
            case 1:
                bundle.putString("URL", "quests/trending");
                fragment.setArguments(bundle);
                return fragment;
            default:
                Log.e(TAG, "Tab handler not found");
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
