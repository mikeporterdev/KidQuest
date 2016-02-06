package com.michael.kidquest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.michael.kidquest.model.DaoSession;
import com.michael.kidquest.model.Quest;
import com.michael.kidquest.model.QuestDao;

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
                Intent intent = new Intent(MainActivity.this, AddQuestActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        showQuests();

    }

    private void showQuests() {
        DaoSession daoSession = ((KidQuestApplication) getApplicationContext()).getDaoSession();

        QuestDao questDao = daoSession.getQuestDao();
        List<Quest> questList = questDao.loadAll();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_activity_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MainActivityListAdapter adapter = new MainActivityListAdapter(questList);
        recyclerView.setAdapter(adapter);
    }

    public void addQuest(View view){
        Intent intent = new Intent(this, AddQuestActivity.class);
    //    EditText editText = (EditText) findViewById(R.id.add_quest);
    }

    @Override
    public void onResume(){
        super.onResume();

        showQuests();
    }
}
