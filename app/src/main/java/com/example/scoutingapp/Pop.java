package com.example.scoutingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View;

public class Pop extends Activity {
    //initialize all the variables I want to export
    String scoutName = "";
    int matchNumber = 0;
    int robotNumber = 0;
    String startingPosition = "";
    boolean showedUp = false;
    protected void onCreate(Bundle savedInstanceState) {// when the pop up is created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);// set the content of the pop up to the pop up window xml layout file
        Spinner dropDownMenu = findViewById(R.id.robotPostitionDropDown);// create object connected to the drop down menu


        //set popup size
        DisplayMetrics displaySize = new DisplayMetrics();// create object that can store window width and height
        getWindowManager().getDefaultDisplay().getMetrics(displaySize); // get the display width and height and assign it to the displaySize variable

        int width = displaySize.widthPixels; //create variable width with value equal to the amount of pixels in width
        int height = displaySize.heightPixels; // create variable height with value equal to the amount of pixels in height
        getWindow().setLayout((int)(width * .8), (int)(height *.8));//set the layout height and width
        String[] dropDownOptions = new String[]{// set the drop down menu options
                "green", "red", "white", "blue", "purple", "orange"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dropDownOptions);// set the drop down menu to options to the list of strings we created earlier
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownMenu.setAdapter(adapter);// set the drop down menu options

    }

    public void exitMenu(View view) {
        //intaialize all the items I will pull data from
        EditText scoutTextBox = findViewById(R.id.ScoutNameTextBox);
        EditText robotTextBox = findViewById(R.id.robotTextBox);
        EditText matchTextBox = findViewById(R.id.matchTextBox);
        Spinner dropDownMenu = findViewById(R.id.robotPostitionDropDown);
        CheckBox checkBox = findViewById(R.id.showedUpCheckBox);
        scoutName = scoutTextBox.getText().toString();// get the scouts name
        try {// if there is nothing in the robot number text box set it to 0
            robotNumber = Integer.parseInt(robotTextBox.getText().toString());// get the robots number
        } catch (Exception e) {
            robotNumber = 0;
        }
        try {// if there is nothing in the  number text box set it to 0
            matchNumber = Integer.parseInt(matchTextBox.getText().toString());// get the match number
        } catch (Exception e) {
            matchNumber = 0;
        }
        startingPosition = dropDownMenu.getSelectedItem().toString();// get the starting position
        showedUp = checkBox.isChecked();// get if the showed up check box is checked

        //put all the data we just saved into intents so we can send it back to the main page
        getIntent().putExtra("scout name", scoutName);
        getIntent().putExtra("robot number", robotNumber);
        getIntent().putExtra("match number", matchNumber);
        getIntent().putExtra("starting position", startingPosition);
        getIntent().putExtra("showed up", showedUp);
        setResult(RESULT_OK, getIntent());
        finish();
        //navigateUpTo(new Intent(getBaseContext(), MainActivity.class));
    }
}
