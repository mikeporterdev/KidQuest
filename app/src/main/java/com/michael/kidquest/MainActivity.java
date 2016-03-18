package com.michael.kidquest;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.michael.kidquest.character.CharacterScreenFragment;
import com.michael.kidquest.character.ParentSetup;
import com.michael.kidquest.greendao.model.Character;
import com.michael.kidquest.greendao.model.Quest;
import com.michael.kidquest.quest.AddQuestActivity;
import com.michael.kidquest.quest.OpenQuestLogFragment;
import com.michael.kidquest.quest.PendingQuestLogFragment;
import com.michael.kidquest.services.CharacterService;
import com.michael.kidquest.widget.NavBarListAdapter;

public class MainActivity extends AppCompatActivity implements OpenQuestLogFragment.OnListFragmentInteractionListener, PendingQuestLogFragment.OnListFragmentInteractionListener {
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private FragmentManager mFragmentManager;
    private CharacterService cService;

    private final static int ADD_QUEST_CODE = 2;
    private final static int SETUP_PARENT_CODE = 3;

    private boolean parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("kidquest", Context.MODE_PRIVATE);
        parent = sharedPreferences.getBoolean("isparent", false);

        cService = new CharacterService(this.getApplicationContext());

        initialFragmentSetup();
        childSidebarSetup();
        toolbarSetup();

    }

    private void initialFragmentSetup() {
        //Load initial fragment
        Fragment fragment;

        if (parent){
            fragment = new OpenQuestLogFragment();
        } else {
            fragment = new CharacterScreenFragment();
        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    private void childSidebarSetup() {
        RecyclerView navBarList = (RecyclerView) findViewById(R.id.left_drawer);
        navBarList.setHasFixedSize(true);

        String[] navBarLocationStrings = getResources().getStringArray(R.array.navigation_drawer_items);

        Character c = cService.getCharacter();

        NavBarListAdapter mAdapter = new NavBarListAdapter(navBarLocationStrings, c.getName(), c.getLevel());
        navBarList.setAdapter(mAdapter);
        navBarList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setOnItemClickListener(new NavBarListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final View view, int position) {
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        fragment = new CharacterScreenFragment();
                        mToolbar.setTitle("Your Character");
                        break;
                    case 1:
                        fragment = new OpenQuestLogFragment();
                        mToolbar.setTitle("Your Quests");
                        break;
                    case 2:
                        fragment = new PendingQuestLogFragment();
                        mToolbar.setTitle("Your Pending Quests");
                        break;
                    case 3:
                        cService.isCorrectPin(view, new DialogSingleButtonListener() {
                            @Override
                            public void onButtonClicked(DialogInterface dialog) {
                                Intent intent = new Intent(view.getContext(), AddQuestActivity.class);
                                startActivityForResult(intent, ADD_QUEST_CODE);
                            }
                        });
                        break;
                    case 4:
                        cService.isCorrectPin(view, new DialogSingleButtonListener() {
                            @Override
                            public void onButtonClicked(DialogInterface dialog) {
                                Intent intent = new Intent(view.getContext(), ParentSetup.class);
                                startActivityForResult(intent, SETUP_PARENT_CODE);
                            }
                        });
                        break;
                    default:
                        Toast.makeText(view.getContext(), "no fragment found, position: " + position, Toast.LENGTH_SHORT).show();
                }

                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    mDrawerLayout.closeDrawers();
                }
            }
        });
    }

    private void toolbarSetup() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("KidQuest");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    @Override
    public void onListFragmentInteraction(Quest quest) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_QUEST_CODE) {
            Fragment fragment = new OpenQuestLogFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            mDrawerLayout.closeDrawers();
        }
    }
}
