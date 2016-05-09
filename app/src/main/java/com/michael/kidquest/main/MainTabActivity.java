package com.michael.kidquest.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.michael.kidquest.DialogSingleButtonListener;
import com.michael.kidquest.R;
import com.michael.kidquest.character.ParentSetup;
import com.michael.kidquest.gcm.RegistrationIntentService;
import com.michael.kidquest.greendao.model.Character;
import com.michael.kidquest.quest.AddQuestActivity;
import com.michael.kidquest.reward.AddRewardActivity;
import com.michael.kidquest.server.ServerRestClient;
import com.michael.kidquest.services.CharacterService;
import com.michael.kidquest.widget.NavBarListAdapter;
import com.michael.kidquest.widget.SlidingTabLayout;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by m_por on 07/05/2016.
 */
public class MainTabActivity extends AppCompatActivity {
    private static final String TAG = "MainTabActivity";
    private ViewPager pager;
    private DrawerLayout mDrawerLayout;
    private MainViewPagerAdapter adapter;
    private SlidingTabLayout layout;
    int numberOfTabs = 3;

    private CharacterService cService;
    private final static int ADD_QUEST_CODE = 1;
    private final static int ADD_REWARD_CODE = 2;
    private NavBarListAdapter mAdapter;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean isReceiverRegistered;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_activity);

        cService = new CharacterService(this.getApplicationContext());

        toolbarSetup();

        setUpCharacter();
        setUpTabView();
        setUpFab();
        setUpNotifications();
    }



    private void setUpTabView() {
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

            }
        });

        layout = (SlidingTabLayout) findViewById(R.id.tabs);
        assert layout != null;
        layout.setDistributeEvenly();

        layout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer(){

            @Override
            public int getIndicatorColor(int position) {
                return ContextCompat.getColor(pager.getContext(), R.color.tabsScrollColor);
            }
        });

        layout.setViewPager(pager);
    }

    private void setUpFab(){
        FloatingActionMenu fam = (FloatingActionMenu) findViewById(R.id.menu);
        assert fam != null;
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

    private void toolbarSetup() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        assert mToolbar != null;
        mToolbar.setTitle("KidQuest");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mDrawerLayout = (DrawerLayout) findViewById(R.id.tab_drawer_layout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                mAdapter.update();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    private void sidebarSetup(Character character){
        RecyclerView navBarList = (RecyclerView) findViewById(R.id.left_drawer);
        assert navBarList != null;
        navBarList.setHasFixedSize(true);
        String[] navBarLocationStrings = getResources().getStringArray(R.array.navigation_drawer_items);

        mAdapter = new NavBarListAdapter(navBarLocationStrings, character);
        navBarList.setAdapter(mAdapter);
        navBarList.setLayoutManager(new LinearLayoutManager(this));

        mAdapter.setOnItemClickListener(new NavBarListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final View view, int position) {
                switch (position) {
                    case 1:
                        cService.isCorrectPin(view, new DialogSingleButtonListener() {
                            @Override
                            public void onButtonClicked(DialogInterface dialog) {
                                Intent intent = new Intent(view.getContext(), ParentSetup.class);
                                startActivity(intent);
                            }
                        });
                        break;
                    case 2:
                        cService.signOut();
                        finish();
                        break;
                }


                mDrawerLayout.closeDrawers();

            }
        });
    }

    private void setUpCharacter() {
        ServerRestClient serverRestClient = new ServerRestClient(cService.getToken());

        serverRestClient.get("users/" + cService.getServerId() + "/", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Gson gson = new GsonBuilder().create();
                Character character = gson.fromJson(response.toString(), Character.class);
                sidebarSetup(character);
            }
        });
    }

    private void setUpNotifications() {
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean("SENT_TOKEN_TO_SERVER", false);
            }
        };

        registerReceiver();

        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }

    private void registerReceiver() {
        if (!isReceiverRegistered){
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter("registrationComplete"));
            isReceiverRegistered = true;
        }
    }
}
