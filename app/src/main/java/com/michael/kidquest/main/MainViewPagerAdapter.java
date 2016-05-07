package com.michael.kidquest.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.michael.kidquest.quest.OpenQuestLogFragment;
import com.michael.kidquest.quest.PendingQuestLogFragment;
import com.michael.kidquest.reward.RewardFragment;

/**
 * Created by m_por on 07/05/2016.
 */
public class MainViewPagerAdapter extends FragmentStatePagerAdapter {
    private int numberOfTabs = 3;
    CharSequence titles[] = {"Open Quests", "Pending Quests", "Rewards"};
    private static final String TAG = "MainViewPagerAdapter";

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new OpenQuestLogFragment();
            case 1:
                return new PendingQuestLogFragment();
            case 2:
                return new RewardFragment();
            default:
                Log.e(TAG, "getItem: Tab handler not found");
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
