package com.michael.kidquest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.michael.kidquest.server.StaffPickQuestFragment;
import com.michael.kidquest.server.TrendingQuestFragment;

/**
 * Created by m_por on 16/02/2016.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter{
    CharSequence titles[]={"Staff Pick", "Trending"};
    int numberOfTabs = 2;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new StaffPickQuestFragment();
            case 1:
                return new TrendingQuestFragment();
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
