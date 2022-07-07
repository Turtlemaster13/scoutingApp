package com.example.scoutingapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View;

public class Pop extends Activity {
    //initialize all the variables I want to export

    MatchScore match = null;
    protected void onCreate(Bundle savedInstanceState) {// when the pop up is created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pregame);// set the content of the pop up to the pop up window xml layout file



        //get the data from the other activitys
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            match = (MatchScore) getIntent().getSerializableExtra("data");
        }//----------------------

        //set popup size
        DisplayMetrics displaySize = new DisplayMetrics();// create object that can store window width and height
        getWindowManager().getDefaultDisplay().getMetrics(displaySize); // get the display width and height and assign it to the displaySize variable
        //int width = displaySize.widthPixels; //create variable width with value equal to the amount of pixels in width
        //int height = displaySize.heightPixels; // create variable height with value equal to the amount of pixels in height
        //getWindow().setLayout((int)(width * .8), (int)(height *.8));//set the layout height and width

        //----------------------
        //create the dropdown window
        Spinner dropDownMenu = findViewById(R.id.robotPostitionDropDown);// create object connected to the drop down menu
        String[] dropDownOptions = new String[]{// set the drop down menu options
                "no show", "green", "red", "white", "blue", "purple", "orange"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, dropDownOptions);// set the drop down menu to options to the list of strings we created earlier
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownMenu.setAdapter(adapter);// set the drop down menu options
        if (match.startingPosition != null) {// if match.starting position contrains a value
            int spinnerPosition = adapter.getPosition(match.startingPosition);// set the drop down window selected to be equil to that
            dropDownMenu.setSelection(spinnerPosition);
        }
        EditText scoutTextBox = findViewById(R.id.ScoutNameTextBox);
        EditText robotTextBox = findViewById(R.id.robotTextBox);
        EditText matchTextBox = findViewById(R.id.matchTextBox);
        //set all of the text boxes to their value only if they already have a value for example before closing and opening the window would reset everything
        if (match.scoutName != null){// if scout name is not empty
            scoutTextBox.setText(match.scoutName);// set the scout name text box to scout name
        }
        if (match.robotNumber != 0){
            robotTextBox.setText(String.valueOf(match.robotNumber));
        }
        if (match.matchNumber != 0){
            matchTextBox.setText(String.valueOf(match.matchNumber));
        }

    }

    public void exitMenu(View view) {
        //intaialize all the items I will pull data from
        EditText scoutTextBox = findViewById(R.id.ScoutNameTextBox);
        EditText robotTextBox = findViewById(R.id.robotTextBox);
        EditText matchTextBox = findViewById(R.id.matchTextBox);
        Spinner dropDownMenu = findViewById(R.id.robotPostitionDropDown);
        //CheckBox checkBox = findViewById(R.id.showedUpCheckBox);
        match.scoutName = scoutTextBox.getText().toString();// get the scouts name
        try {// if there is nothing in the robot number text box set it to 0
            match.robotNumber = Integer.parseInt(robotTextBox.getText().toString());// get the robots number
        } catch (Exception e) {
            match.robotNumber = 0;
        }
        try {// if there is nothing in the  number text box set it to 0
            match.matchNumber = Integer.parseInt(matchTextBox.getText().toString());// get the match number
        } catch (Exception e) {
            match.matchNumber = 0;
        }
        match.startingPosition = dropDownMenu.getSelectedItem().toString();// get the starting position
        if (match.startingPosition == "no show"){
            match.showedUp = false;
        } else{
            match.showedUp = true;
        }

        getIntent().putExtra("data",match);
        setResult(RESULT_OK, getIntent());
        finish();
        //navigateUpTo(new Intent(getBaseContext(), MainActivity.class));
    }
}
