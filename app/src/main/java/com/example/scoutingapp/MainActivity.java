package com.example.scoutingapp;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    // all variables that need to be exported at the end of the match are in the MatchScore Class
    MatchScore match = new MatchScore();
    //___________________________________________________________________
    // other variables
    boolean teleOpp = false; //what mode is the button in false = auto, true = teleop


    //other stuff
    // stop watch stuff ik kanda jank
    boolean timerOn = false;// make a boolean that tells me if the chronometer is running
    long pauseOffset = 0;


    //initialize score buttons
    //(anything prefixed with a capital B will be a button
    Button BupperScore;
    Button BupperMiss;
    Button BlowerScore;
    Button BlowerMiss;

    // initialize foul buttons
    Button BfoulButton;
    Button BtechFoulButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Button textPopup = findViewById(R.id.PreMatch);
        // initalize buttons then set their text
        BupperScore = findViewById(R.id.UpperHubScore); BupperScore.setText(String.valueOf(match.autoUpperHit));//
        BupperMiss = findViewById(R.id.UpperHubMiss); BupperMiss.setText(String.valueOf(match.autoUpperMissed));
        BlowerScore = findViewById(R.id.LowerHubScore); BlowerScore.setText(String.valueOf(match.autoLowerHit));
        BlowerMiss = findViewById(R.id.LowerHubMiss); BlowerMiss.setText(String.valueOf(match.autoLowerMissed));

        //set the text of the foul buttons
        BfoulButton = findViewById(R.id.Foul); BfoulButton.setText(String.valueOf(match.totalFouls));
        BtechFoulButton = findViewById(R.id.TechFoul); BtechFoulButton.setText(String.valueOf(match.totalTechFouls));

        //set up the starting position drop down menu
        Spinner climbPositionDropdown =  findViewById(R.id.ClimbPosition);
        String[] dropDownOptions = new String[]{// set the drop down menu options
                "Low", "Middle", "High", "Traversal"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, dropDownOptions);// set the drop down menu to options to the list of strings we created earlier
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        climbPositionDropdown.setAdapter(adapter);// set the drop down menu options
    }

    public void preMatchPopup(View view){// create a pop up window

        Intent intent = new Intent(MainActivity.this,Pop.class);
        intent.putExtra("data", match);
        startActivityForResult(intent,1);//start the pop up window
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {// when an activity ends
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {// grab the correct signal
            TextView robotNumberTextBox = findViewById(R.id.RobotTitleText);// grab a text box to display what robot you are veiwing
            if (resultCode == RESULT_OK){///grab all the values from the text box
                match = (MatchScore) data.getSerializableExtra("data");
                robotNumberTextBox.setText("Team: " + match.robotNumber);// display what robot you are watching
            }
        }
    }

    public void onTaxiClick(View view){
        CheckBox taxiBox = findViewById(R.id.TaxiCheckBox);// get the taxi check box
        match.taxi = taxiBox.isChecked();//set taxi variable to if the taxi check box has been clicked
    }
    public void onTeleToggleFlipped (View view){
        Switch teleSwitch = findViewById(R.id.TeleSwitch);// get the teleop switch
        CheckBox taxiBox = findViewById(R.id.TaxiCheckBox);// get the taxi check
        if (teleSwitch.isChecked()) {
            taxiBox.setAlpha(.5f);
            taxiBox.setClickable(false);//make the text box clickable
            teleOpp = true;
            BlowerScore.setText(String.valueOf(match.teleLowerHit));
            BlowerMiss.setText(String.valueOf(match.teleLowerMissed));
            BupperScore.setText(String.valueOf(match.teleUpperHit));
            BupperMiss.setText(String.valueOf(match.teleUpperMissed));
        } else if (!teleSwitch.isChecked()){
            taxiBox.setAlpha(1f);
            taxiBox.setClickable(true);//make the text box clickable
            teleOpp = false;
            BlowerScore.setText(String.valueOf(match.autoLowerHit));
            BlowerMiss.setText(String.valueOf(match.autoLowerMissed));
            BupperScore.setText(String.valueOf(match.autoUpperHit));
            BupperMiss.setText(String.valueOf(match.autoUpperMissed));
        }

    }
    // scoring buttons
    public void onScoreButtonPressed(View view){///  basicly update and add to the text of the buttons and scores// bro this function ligit 80 lines long// speget code
        if (!teleOpp){
            switch (view.getId()){
                case R.id.LowerHubScore:
                    match.autoLowerHit += 1;
                    BlowerScore.setText(String.valueOf(match.autoLowerHit));
                    break;
                case R.id.LowerHubMiss:
                    match.autoLowerMissed += 1;
                    BlowerMiss.setText(String.valueOf(match.autoLowerMissed));
                    break;
                case R.id.UpperHubScore:
                    match.autoUpperHit += 1;
                    BupperScore.setText(String.valueOf(match.autoUpperHit));
                    break;
                case R.id.UpperHubMiss:
                    match.autoUpperMissed += 1;
                    BupperMiss.setText(String.valueOf(match.autoUpperMissed));
                    break;
                case R.id.SubLowerHubScore:///subtract------------------------------------------------------
                    match.autoLowerHit -= 1;
                    BlowerScore.setText(String.valueOf(match.autoLowerHit));
                    break;
                case R.id.SubLowerHubMiss:
                    match.autoLowerMissed -= 1;
                    BlowerMiss.setText(String.valueOf(match.autoLowerMissed));
                    break;
                case R.id.SubUpperHubScore:
                    match.autoUpperHit -= 1;
                    BupperScore.setText(String.valueOf(match.autoUpperHit));
                    break;
                case R.id.SubUpperHubMiss:
                    match.autoUpperMissed -= 1;
                    BupperMiss.setText(String.valueOf(match.autoUpperMissed));
                    break;
            }//-----------------------------------------------------------------------------------------------
        } else if (teleOpp){
            switch (view.getId()){
                case R.id.LowerHubScore:
                    match.teleLowerHit += 1;
                    BlowerScore.setText(String.valueOf(match.teleLowerHit));
                    break;
                case R.id.LowerHubMiss:
                    match.teleLowerMissed += 1;
                    BlowerMiss.setText(String.valueOf(match.teleLowerMissed));
                    break;
                case R.id.UpperHubScore:
                    match.teleUpperHit += 1;
                    BupperScore.setText(String.valueOf(match.teleUpperHit));
                    break;
                case R.id.UpperHubMiss:
                    match.teleUpperMissed += 1;
                    BupperMiss.setText(String.valueOf(match.teleUpperMissed));
                    break;
                case R.id.SubLowerHubScore:////subtract
                    match.teleLowerHit -= 1;
                    BlowerScore.setText(String.valueOf(match.teleLowerHit));
                    break;
                case R.id.SubLowerHubMiss:
                    match.teleLowerMissed -= 1;
                    BlowerMiss.setText(String.valueOf(match.teleLowerMissed));
                    break;
                case R.id.SubUpperHubScore:
                    match.teleUpperHit -= 1;
                    BupperScore.setText(String.valueOf(match.teleUpperHit));
                    break;
                case R.id.SubUpperHubMiss:
                    match.teleUpperMissed -= 1;
                    BupperMiss.setText(String.valueOf(match.teleUpperMissed));
                    break;
                }
            }
        }//when you press any buttons that have to do with score it is handled in this function
    public void onFoulButtonPressed(View view){
        switch(view.getId()){// switch statement to get the buttons and add to the foul total variables then update the text of the button
            case R.id.Foul://when foul button is pressed
                match.totalFouls += 1;
                BfoulButton.setText(String.valueOf(match.totalFouls));
                break;
            case R.id.TechFoul://when the tech foul button is pressed
                match.totalTechFouls += 1;
                BtechFoulButton.setText(String.valueOf(match.totalTechFouls));
                break;
            case R.id.FoulSubtract:
                match.totalFouls -= 1;
                BfoulButton.setText(String.valueOf(match.totalFouls));
                break;
            case R.id.TechFoulSubtract:
                match.totalTechFouls -= 1;
                BtechFoulButton.setText(String.valueOf(match.totalTechFouls));
                break;
        }
    }// when you press any of the foul buttons it is dealt with here

    public void stopWatch(View view){
        Chronometer chronometer = findViewById(R.id.Chronometer);// get the actual timer item
        Button BtimerAction = findViewById(R.id.StopWatchStart);// get the button that will start or stop the chronometer
        if(timerOn){//stop
            timerOn = false;
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase(); // set the timer offset so we actually save the time when we pause the timer
            chronometer.stop();
            BtimerAction.setText("start");
        } else{//start
            timerOn = true;
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);// save the time the chronometer has been running
            chronometer.start();
            BtimerAction.setText("stop");

        }
        long elapsedTime = (int) (SystemClock.elapsedRealtime() - chronometer.getBase()); //convert the chronometer base into seconds
        match.climbTime = (int) (elapsedTime / 1000);//convert from milliseconds to seconds
        //System.out.println(climbTime); //debug stuff lol
    }

    public void stopWatchReset(View view){// reset the timer
        Chronometer chronometer = findViewById(R.id.Chronometer);// get the actual timer item
        Button BtimerAction = findViewById(R.id.StopWatchStart);
        pauseOffset = 0;
        timerOn = false;
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
        BtimerAction.setText("start");
        match.climbTime = 0;
    }

    public void finish(View view){
        CheckBox disconectBox = findViewById(R.id.DisconectCheckbox);
        CheckBox damageBox = findViewById(R.id.DamageCheckbox);
        match.disconects = disconectBox.isChecked(); match.damage = damageBox.isChecked(); // get damage and disconects
        Spinner climbPositionDropdown = findViewById(R.id.ClimbPosition);
        match.climbEndingPosition = String.valueOf(climbPositionDropdown.getSelectedItem());



        Intent intent = new Intent(MainActivity.this,endpopup.class);
        intent.putExtra("data", match);
        startActivityForResult(intent,1);//start the pop up window
    }
}
