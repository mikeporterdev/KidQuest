package com.michael.kidquest.reward;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.michael.kidquest.R;
import com.michael.kidquest.greendao.model.Reward;
import com.michael.kidquest.services.RewardService;

/**
 * Created by m_por on 25/04/2016.
 */
public class AddRewardActivity extends AppCompatActivity {
    private String TAG = "AddRewardActivity";
    private EditText editRewardName;
    private EditText editRewardCost;
    private TextView txtCharacterGold;
    private RewardService rewardService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reward);

        rewardService = new RewardService(getApplicationContext());

        editRewardName = (EditText) findViewById(R.id.rewardName);
        editRewardCost = (EditText) findViewById(R.id.rewardCost);
        editRewardCost.setText("Test");
        Button btnSubmit = (Button) findViewById(R.id.addRewardSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateReward()){
                    Reward reward = new Reward();

                    reward.setName(editRewardName.getText().toString());
                    int cost = Integer.parseInt(editRewardCost.getText().toString());
                    reward.setCost(cost);

                    rewardService.addReward(reward);

                    Toast.makeText(AddRewardActivity.this, "Reward Added", Toast.LENGTH_SHORT).show();

                    finish();
                }
            }

            public boolean validateReward(){
                String name = editRewardName.getText().toString();
                boolean valid = true;

                if (name.equals("")){
                    editRewardName.setError("Reward must have a name");
                    valid = false;
                }

                String cost = editRewardCost.getText().toString();

                if (cost.equals("")){
                    editRewardCost.setError("Reward must have a cost");
                    valid = false;
                }

                try {
                    Integer.parseInt(cost);
                } catch(NumberFormatException e){
                    editRewardCost.setError("Reward cost must be a whole number");
                    valid = false;
                }

                return valid;
            }
        });
    }
}
