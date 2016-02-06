package com.michael.kidquest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.michael.kidquest.custommodel.DifficultyLevel;
import com.michael.kidquest.model.DaoSession;
import com.michael.kidquest.model.Quest;

import java.util.ArrayList;
import java.util.Arrays;
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
        DaoSession daoSession = ((KidQuestApplication) getApplicationContext()).getDaoSession();

        spinner = (Spinner) findViewById(R.id.addQuestDifficulty);

        List<DifficultyLevel> diffs = Arrays.asList(DifficultyLevel.values());


        List<String> difficultyTexts = new ArrayList<String>();
        for (DifficultyLevel difficulty: diffs){
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
        final EditText editQuestName = (EditText) findViewById(R.id.questName);
        final EditText editQuestDesc = (EditText) findViewById(R.id.questDesc);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaoSession daoSession = ((KidQuestApplication) getApplicationContext()).getDaoSession();

                String diff = String.valueOf(spinner.getSelectedItem());

                Quest quest = new Quest();
                quest.setDifficultyLevel(DifficultyLevel.fromString(diff));
                quest.setTitle(editQuestName.getText().toString());
                quest.setDescription(editQuestDesc.getText().toString());
                quest.setCompleted(false);

                daoSession.getQuestDao().insertOrReplace(quest);

                Toast.makeText(AddQuestActivity.this, "Quest Added", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }
}
