package com.michael.kidquest.quest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.michael.kidquest.R;
import com.michael.kidquest.widget.TabActivity;
import com.michael.kidquest.greendao.custommodel.DifficultyLevel;
import com.michael.kidquest.greendao.model.Quest;
import com.michael.kidquest.services.QuestService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AddQuestActivity extends AppCompatActivity {
    private String TAG = "AddQuestActivity";
    private final int GET_PRESET_QUEST_REQUEST = 1;
    private Spinner spinner;
    private QuestService questService;
    private EditText editQuestName;
    private EditText editQuestDesc;
    private List<String> difficultyTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quest);

        questService = new QuestService(getApplicationContext());

        addDifficultiesToSpinner();
        addListenerOnSpinnerItemSelection();
        addListenerOnButton();

        TextView textView = (TextView) findViewById(R.id.presetQuestLink);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPresetQuestIntent = new Intent(getApplicationContext(), TabActivity.class);
                startActivityForResult(pickPresetQuestIntent, GET_PRESET_QUEST_REQUEST);
            }
        });
    }

    private void addDifficultiesToSpinner() {
        spinner = (Spinner) findViewById(R.id.addQuestDifficulty);

        List<DifficultyLevel> diffs = Arrays.asList(DifficultyLevel.values());

        difficultyTexts = new ArrayList<>();
        for (DifficultyLevel difficulty : diffs) {
            difficultyTexts.add(difficulty.getDifficultyLevel());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, difficultyTexts);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    private void addListenerOnSpinnerItemSelection() {
        spinner = (Spinner) findViewById(R.id.addQuestDifficulty);
    }

    private void addListenerOnButton() {
        spinner = (Spinner) findViewById(R.id.addQuestDifficulty);
        Button btnSubmit = (Button) findViewById(R.id.addQuestSubmit);
        editQuestName = (EditText) findViewById(R.id.questName);
        editQuestDesc = (EditText) findViewById(R.id.questDesc);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qName = editQuestName.getText().toString();
                String diff = String.valueOf(spinner.getSelectedItem());

                if (!qName.equals("")) {
                    Quest quest = new Quest();
                    quest.setDifficultyLevel(DifficultyLevel.fromString(diff));
                    quest.setTitle(editQuestName.getText().toString());
                    quest.setDescription(editQuestDesc.getText().toString());

                    questService.addQuest(quest);

                    Toast.makeText(AddQuestActivity.this, "Quest Added", Toast.LENGTH_SHORT).show();

                    setResult(2);
                    finish();
                } else {
                    editQuestName.setError("Quest Name is required");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == GET_PRESET_QUEST_REQUEST){
                //Set form selected in the preset quest request
                Quest quest = (Quest) data.getExtras().getSerializable("quest");

                if (quest != null) {
                    editQuestName.setText(quest.getTitle());

                    //Weird workflow for setting a spinnery by string value.
                    String compareString = quest.getDifficultyLevel().getDifficultyLevel();
                    for (int i = 0; i < spinner.getCount(); i++){
                        if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(compareString)){
                            spinner.setSelection(i);
                            break;
                        }
                    }
                }
            }
        }
    }
}
