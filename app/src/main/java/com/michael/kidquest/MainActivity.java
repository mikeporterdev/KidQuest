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
import android.view.View;
import android.widget.Toast;

import com.michael.kidquest.greendao.model.Quest;
import com.michael.kidquest.quest.OpenQuestLogFragment;

public class MainActivity extends AppCompatActivity implements OpenQuestLogFragment.OnListFragmentInteractionListener, PendingQuestLogFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbarSetup();
        sidebarSetup();

        //Load initial fragment
        Fragment fragment = new OpenQuestLogFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

    }

    private void sidebarSetup() {
        RecyclerView navBarList = (RecyclerView) findViewById(R.id.left_drawer);
        navBarList.setHasFixedSize(true);

        String[] navBarLocationStrings = getResources().getStringArray(R.array.navigation_drawer_items);
        NavBarListAdapter mAdapter = new NavBarListAdapter(navBarLocationStrings, "Balthazaro", 18);
        navBarList.setAdapter(mAdapter);
        navBarList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setOnItemClickListener(new NavBarListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Fragment fragment = null;
                switch(position){
                    case 1: fragment = new OpenQuestLogFragment(); break;
                    case 2: fragment = new PendingQuestLogFragment(); break;
                    default: Toast.makeText(view.getContext(), "no fragment found, position: " + position, Toast.LENGTH_SHORT).show();
                }

                if (fragment != null){
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                }
            }
        });
    }

    private void toolbarSetup() {
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
    }

    private void addDrawerItems() {





/**
 mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

@Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
Fragment fragment = new OpenQuestLogFragment();

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
