package com.michael.kidquest;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.michael.kidquest.greendao.model.Character;
import com.michael.kidquest.greendao.model.DaoSession;
import com.michael.kidquest.greendao.model.Quest;
import com.michael.kidquest.quest.AddQuestActivity;
import com.michael.kidquest.quest.OpenQuestLogFragment;
import com.michael.kidquest.quest.PendingQuestLogFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OpenQuestLogFragment.OnListFragmentInteractionListener, PendingQuestLogFragment.OnListFragmentInteractionListener {
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private FragmentManager mFragmentManager;

    private final static int ADD_QUEST_CODE = 2;
    private String mCharacterNameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaoSession daoSession = ((KidQuestApplication) this.getApplicationContext()).getDaoSession();

        //TODO: change this to not use a list
        List<Character> character = daoSession.getCharacterDao().loadAll();
        //daoSession.getCharacterDao().delete(character.get(0));
        if (character.size() == 0){
            firstTimeSetup();
        } else {
            initialFragmentSetup();
        }

        toolbarSetup();
        sidebarSetup();
    }

    private void initialFragmentSetup() {
        //Load initial fragment
        Fragment fragment = new CharacterScreenFragment();
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
                        Intent intent = new Intent(view.getContext(), AddQuestActivity.class);
                        startActivityForResult(intent, ADD_QUEST_CODE);
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
        mToolbar.setTitle("Your Quests");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    @Override
    public void onListFragmentInteraction(Quest quest) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_QUEST_CODE)
        {
            Fragment fragment = new OpenQuestLogFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            mDrawerLayout.closeDrawers();
        }
    }

    public void firstTimeSetup(){


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Character Name");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Character character = new Character();

                mCharacterNameInput = input.getText().toString();

                character.setName(mCharacterNameInput);
                character.setLevel(1);
                character.setParentPin("1066");

                DaoSession daoSession = ((KidQuestApplication) getApplicationContext()).getDaoSession();
                daoSession.getCharacterDao().insertOrReplace(character);

                initialFragmentSetup();
            }
        });

        builder.show();
    }
}
