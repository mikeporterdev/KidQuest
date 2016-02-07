package com.michael.kidquest;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.michael.kidquest.model.Quest;

public class MainActivity extends AppCompatActivity implements QuestLogFragment.OnListFragmentInteractionListener {

    public RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Toolbar setup
         * */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Your Quests");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


        String[] mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items);
        RecyclerView mDrawerList = (RecyclerView) findViewById(R.id.left_drawer);
        mDrawerList.setHasFixedSize(true);

        NavBarListAdapter mAdapter = new NavBarListAdapter(mNavigationDrawerItemTitles, "Balthazaro", 18);
        mDrawerList.setAdapter(mAdapter);

        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(this);
        mDrawerList.setLayoutManager(mLayoutManager2);


        Fragment fragment = new QuestLogFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

    }

    private void addDrawerItems() {





/**
 mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

@Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
Fragment fragment = new QuestLogFragment();

FragmentManager fragmentManager = getFragmentManager();
fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
}
});

 */
    }

    @Override
    public void onListFragmentInteraction(Quest quest) {

    }
}
