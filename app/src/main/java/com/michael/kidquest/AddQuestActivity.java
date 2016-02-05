package com.michael.kidquest;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.model.DaoMaster;
import com.example.model.DaoSession;
import com.example.model.Difficulty;

import java.util.ArrayList;
import java.util.List;

public class AddQuestActivity extends AppCompatActivity {
    private Spinner spinner;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quest);

        addDifficultiesToSpinner();
        addListenerOnSpinnerItemSelection();
        addListenerOnButton();
    }

    public void addDifficultiesToSpinner(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "quest-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();

        List<Difficulty> difficulties = daoSession.getDifficultyDao().loadAll();
        spinner = (Spinner) findViewById(R.id.addQuestDifficulty);

        List<String> difficultyTexts = new ArrayList<String>();
        for (Difficulty difficulty: difficulties){
            difficultyTexts.add(difficulty.getDifficultyLevel());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, difficultyTexts);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    public void addListenerOnSpinnerItemSelection(){
        spinner = (Spinner) findViewById(R.id.addQuestDifficulty);
        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    public void addListenerOnButton(){
        spinner = (Spinner) findViewById(R.id.addQuestDifficulty);
        btnSubmit = (Button) findViewById(R.id.addQuestSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddQuestActivity.this, "you submitted some shit: " + String.valueOf(spinner.getSelectedItem()), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
