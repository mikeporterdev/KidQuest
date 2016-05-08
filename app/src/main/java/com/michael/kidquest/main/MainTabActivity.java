package com.michael.kidquest.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.michael.kidquest.DialogSingleButtonListener;
import com.michael.kidquest.R;
import com.michael.kidquest.quest.AddQuestActivity;
import com.michael.kidquest.reward.AddRewardActivity;
import com.michael.kidquest.services.CharacterService;
import com.michael.kidquest.widget.SlidingTabLayout;

/**
 * Created by m_por on 07/05/2016.
 */
public class MainTabActivity extends AppCompatActivity {
    private static final String TAG = "MainTabActivity";
    private Toolbar toolbar;
    private ViewPager pager;
    MainViewPagerAdapter adapter;
    SlidingTabLayout layout;
    int numberOfTabs = 3;

    private CharacterService cService;
    private final static int ADD_QUEST_CODE = 1;
    private final static int ADD_REWARD_CODE = 2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_activity);

        cService = new CharacterService(this.getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new MainViewPagerAdapter(getSupportFragmentManager());

        pager = (ViewPager) findViewById(R.id.pager);

        assert pager != null;
        pager.setAdapter(adapter);

        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.i(TAG, "onPageSelected: ");

                Fragment f = adapter.getItem(position);
                f.onResume();
                //OpenQuestLogFragment fragment = (OpenQuestLogFragment) adapter.getItem(0);
                //fragment.update();
                //PendingQuestLogFragment pendingFragment = (PendingQuestLogFragment) adapter.getItem(1);
                //pendingFragment.update(getApplicationContext());

            }
        });



        layout = (SlidingTabLayout) findViewById(R.id.tabs);
        assert layout != null;
        layout.setDistributeEvenly(true);

        layout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer(){

            @Override
            public int getIndicatorColor(int position) {
                return ContextCompat.getColor(pager.getContext(), R.color.tabsScrollColor);
            }
        });

        layout.setViewPager(pager);

        setUpFab();


    }

    private void setUpFab(){
        FloatingActionMenu fam = (FloatingActionMenu) findViewById(R.id.menu);
        fam.setClosedOnTouchOutside(true);

        FloatingActionButton questFab = (FloatingActionButton) findViewById(R.id.menu_item_add_quest);
        assert questFab != null;
        questFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                cService.isCorrectPin(v, new DialogSingleButtonListener() {
                    @Override
                    public void onButtonClicked(DialogInterface dialog) {
                        Intent intent = new Intent(v.getContext(), AddQuestActivity.class);
                        startActivityForResult(intent, ADD_QUEST_CODE);
                    }
                });
            }
        });

        FloatingActionButton rewardFab = (FloatingActionButton) findViewById(R.id.menu_item_add_reward);
        assert rewardFab != null;
        rewardFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                cService.isCorrectPin(view, new DialogSingleButtonListener() {
                    @Override
                    public void onButtonClicked(DialogInterface dialog) {
                        Intent intent = new Intent(view.getContext(), AddRewardActivity.class);
                        startActivityForResult(intent, ADD_REWARD_CODE);
                    }
                });
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_QUEST_CODE){
            pager.setAdapter(adapter);
            pager.setCurrentItem(0);
        } else if (requestCode == ADD_REWARD_CODE){
            pager.setAdapter(adapter);
            pager.setCurrentItem(2);
        }
        FloatingActionMenu fam = (FloatingActionMenu) findViewById(R.id.menu);
        assert fam != null;
        fam.toggle(true);
        fam.close(true);
    }

}
