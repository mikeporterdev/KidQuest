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
class MainViewPagerAdapter extends FragmentStatePagerAdapter {
    public static final String STATE_SUPERCLASS = "STATE_SUPERCLASS";
    private final CharSequence[] titles = {"Open Quests", "Pending Quests", "Rewards"};
    private static final String TAG = "MainViewPagerAdapter";

    private OpenQuestLogFragment openQuestLogFragment;
    private PendingQuestLogFragment pendingQuestLogFragment;

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                if (openQuestLogFragment == null){
                    openQuestLogFragment = new OpenQuestLogFragment();
                }
                openQuestLogFragment.setRetainInstance(true);
                return openQuestLogFragment;
            case 1:
                if (pendingQuestLogFragment == null){
                    pendingQuestLogFragment = new PendingQuestLogFragment();
                }
                pendingQuestLogFragment.setRetainInstance(true);
                return pendingQuestLogFragment;
            case 2:
                RewardFragment rewardFragment = new RewardFragment();
                rewardFragment.setRetainInstance(true);
                return rewardFragment;
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
        int numberOfTabs = 3;
        return numberOfTabs;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
