package com.michael.kidquest;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by Michael Porter on 05/02/16.
 */
public class CustomOnItemSelectedListener implements android.widget.AdapterView.OnItemSelectedListener {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(parent.getContext(), "OnSlectedLstnr " +
                        parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
