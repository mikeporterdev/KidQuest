package com.michael.kidquest;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.model.DaoMaster;
import com.example.model.DaoSession;
import com.example.model.Difficulty;
import com.example.model.DifficultyDao;
import com.example.model.Quest;
import com.example.model.QuestDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "quest-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();

        insertSampleData(daoSession);

        QuestDao questDao = daoSession.getQuestDao();
        List<Quest> questList = questDao.loadAll();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_activity_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MainActivityListAdapter adapter = new MainActivityListAdapter(questList);
        recyclerView.setAdapter(adapter);
    }

    public void insertSampleData(DaoSession daoSession){
        Difficulty difficulty = new Difficulty();
        difficulty.setGold(40);
        difficulty.setExperience(400);
        difficulty.setDifficultyLevel("Easy");
        DifficultyDao difficultyDao = daoSession.getDifficultyDao();
        difficultyDao.insertOrReplace(difficulty);

        Quest quest = new Quest();
        quest.setTitle("Clean Your Room");
        quest.setDescription("Test Quest");
        quest.setCompleted(false);
        quest.setDifficulty(difficulty);
        daoSession.getQuestDao().insertOrReplace(quest);
    }
}
