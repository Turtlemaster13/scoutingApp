package com.example.scoutingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
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
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    //export variables to csv________________________________
    // initialize all the variables that will be exported
    String scoutName = "";
    int robotNumber = 0;
    int matchNumber = 0;
    String startingPosition = "";
    boolean showedUp = false;
    boolean taxi = false;
    //scoring
        //auto
    int autoLowerMissed = 0;
    int autoUpperMissed = 0;
    int autoLowerHit = 0;
    int autoUpperHit = 0;
        //teleop
    int teleLowerMissed = 0;
    int teleUpperMissed = 0;
    int teleLowerHit = 0;
    int teleUpperHit = 0;
        //fouls
    int totalFouls = 0;
    int totalTechFouls = 0;
        //damage/disconects
    boolean damage = false;
    boolean disconects = false;


    int climbTime;
    String climbEndingPosition;
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
        BupperScore = findViewById(R.id.UpperHubScore); BupperScore.setText(String.valueOf(autoUpperHit));//
        BupperMiss = findViewById(R.id.UpperHubMiss); BupperMiss.setText(String.valueOf(autoUpperMissed));
        BlowerScore = findViewById(R.id.LowerHubScore); BlowerScore.setText(String.valueOf(autoLowerHit));
        BlowerMiss = findViewById(R.id.LowerHubMiss); BlowerMiss.setText(String.valueOf(autoLowerMissed));

        //set the text of the foul buttons
        BfoulButton = findViewById(R.id.Foul); BfoulButton.setText(String.valueOf(totalFouls));
        BtechFoulButton = findViewById(R.id.TechFoul); BtechFoulButton.setText(String.valueOf(totalTechFouls));

        //set up the starting position drop down menu
        Spinner climbPositionDropdown =  findViewById(R.id.ClimbPosition);
        String[] dropDownOptions = new String[]{// set the drop down menu options
                "Low", "Middle", "High", "Traversal"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dropDownOptions);// set the drop down menu to options to the list of strings we created earlier
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        climbPositionDropdown.setAdapter(adapter);// set the drop down menu options
    }

    public void preMatchPopup(View view){// create a pop up window

        Intent intent = new Intent(MainActivity.this,Pop.class);
        startActivityForResult(intent,1);//start the pop up window
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {// when an activity ends
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {// grab the correct signal
            TextView debug = findViewById(R.id.RobotTitleText);// grab a text box to display what robot you are veiwing
            if (resultCode == RESULT_OK){///grab all the values from the text box
                scoutName = data.getStringExtra("scout name");
                robotNumber = data.getIntExtra("robot number", 0);
                matchNumber = data.getIntExtra("match number", 0);
                startingPosition = data.getStringExtra("starting position");
                showedUp = data.getBooleanExtra("showed up",false);
                debug.setText("Team: " + robotNumber);// display what robot you are watching
            }
        }
    }

    public void onTaxiClick(View view){
        CheckBox taxiBox = findViewById(R.id.TaxiCheckBox);// get the taxi check box
        taxi = taxiBox.isChecked();//set taxi variable to if the taxi check box has been clicked
    }
    public void onTeleToggleFlipped (View view){
        Switch teleSwitch = findViewById(R.id.TeleSwitch);// get the teleop switch
        CheckBox taxiBox = findViewById(R.id.TaxiCheckBox);// get the taxi check
        if (teleSwitch.isChecked()) {
            taxiBox.setAlpha(.5f);
            taxiBox.setClickable(false);//make the text box clickable
            teleOpp = true;
            BlowerScore.setText(String.valueOf(teleLowerHit));
            BlowerMiss.setText(String.valueOf(teleLowerMissed));
            BupperScore.setText(String.valueOf(teleUpperHit));
            BupperMiss.setText(String.valueOf(teleUpperMissed));
        } else if (!teleSwitch.isChecked()){
            taxiBox.setAlpha(1f);
            taxiBox.setClickable(true);//make the text box clickable
            teleOpp = false;
            BlowerScore.setText(String.valueOf(autoLowerHit));
            BlowerMiss.setText(String.valueOf(autoLowerMissed));
            BupperScore.setText(String.valueOf(autoUpperHit));
            BupperMiss.setText(String.valueOf(autoUpperMissed));
        }

    }
    // scoring buttons
    public void onScoreButtonPressed(View view){///  basicly update and add to the text of the buttons and scores// bro this function ligit 80 lines long// speget code
        if (!teleOpp){
            switch (view.getId()){
                case R.id.LowerHubScore:
                    autoLowerHit += 1;
                    BlowerScore.setText(String.valueOf(autoLowerHit));
                    break;
                case R.id.LowerHubMiss:
                    autoLowerMissed += 1;
                    BlowerMiss.setText(String.valueOf(autoLowerMissed));
                    break;
                case R.id.UpperHubScore:
                    autoUpperHit += 1;
                    BupperScore.setText(String.valueOf(autoUpperHit));
                    break;
                case R.id.UpperHubMiss:
                    autoUpperMissed += 1;
                    BupperMiss.setText(String.valueOf(autoUpperMissed));
                    break;
                case R.id.SubLowerHubScore:///subtract------------------------------------------------------
                    autoLowerHit -= 1;
                    BlowerScore.setText(String.valueOf(autoLowerHit));
                    break;
                case R.id.SubLowerHubMiss:
                    autoLowerMissed -= 1;
                    BlowerMiss.setText(String.valueOf(autoLowerMissed));
                    break;
                case R.id.SubUpperHubScore:
                    autoUpperHit -= 1;
                    BupperScore.setText(String.valueOf(autoUpperHit));
                    break;
                case R.id.SubUpperHubMiss:
                    autoUpperMissed -= 1;
                    BupperMiss.setText(String.valueOf(autoUpperMissed));
                    break;
            }//-----------------------------------------------------------------------------------------------
        } else if (teleOpp){
            switch (view.getId()){
                case R.id.LowerHubScore:
                    teleLowerHit += 1;
                    BlowerScore.setText(String.valueOf(teleLowerHit));
                    break;
                case R.id.LowerHubMiss:
                    teleLowerMissed += 1;
                    BlowerMiss.setText(String.valueOf(teleLowerMissed));
                    break;
                case R.id.UpperHubScore:
                    teleUpperHit += 1;
                    BupperScore.setText(String.valueOf(teleUpperHit));
                    break;
                case R.id.UpperHubMiss:
                    teleUpperMissed += 1;
                    BupperMiss.setText(String.valueOf(teleUpperMissed));
                    break;
                case R.id.SubLowerHubScore:////subtract
                    teleLowerHit -= 1;
                    BlowerScore.setText(String.valueOf(teleLowerHit));
                    break;
                case R.id.SubLowerHubMiss:
                    teleLowerMissed -= 1;
                    BlowerMiss.setText(String.valueOf(teleLowerMissed));
                    break;
                case R.id.SubUpperHubScore:
                    teleUpperHit -= 1;
                    BupperScore.setText(String.valueOf(teleUpperHit));
                    break;
                case R.id.SubUpperHubMiss:
                    teleUpperMissed -= 1;
                    BupperMiss.setText(String.valueOf(teleUpperMissed));
                    break;
                }
            }
        }//when you press any buttons that have to do with score it is handled in this function
    public void onFoulButtonPressed(View view){
        switch(view.getId()){// switch statement to get the buttons and add to the foul total variables then update the text of the button
            case R.id.Foul://when foul button is pressed
                totalFouls += 1;
                BfoulButton.setText(String.valueOf(totalFouls));
                break;
            case R.id.TechFoul://when the tech foul button is pressed
                totalTechFouls += 1;
                BtechFoulButton.setText(String.valueOf(totalTechFouls));
                break;
            case R.id.FoulSubtract:
                totalFouls -= 1;
                BfoulButton.setText(String.valueOf(totalFouls));
                break;
            case R.id.TechFoulSubtract:
                totalTechFouls -= 1;
                BtechFoulButton.setText(String.valueOf(totalTechFouls));
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
        climbTime = (int) (elapsedTime / 1000);//convert from milliseconds to seconds
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
        climbTime = 0;
    }

    public void finish(View view){
        CheckBox disconectBox = findViewById(R.id.DisconectCheckbox);
        CheckBox damageBox = findViewById(R.id.DamageCheckbox);
        disconects = disconectBox.isChecked(); damage = damageBox.isChecked(); // get damage and disconects
        Spinner climbPositionDropdown = findViewById(R.id.ClimbPosition);
        climbEndingPosition = String.valueOf(climbPositionDropdown.getSelectedItem());


        String text = "scout name : " + scoutName + "\n" +"robot number: " + robotNumber + "\n" + "match number" + matchNumber + "\n" +"starting position: " + startingPosition + "\n" +"showed up: " + showedUp + "\n" +
                "shots missed in auto for lower hub: " + autoLowerMissed + "\n" +"shots missed in auto for upper hub: "+ autoUpperMissed + "\n" +"shots scored in auto for lower hub: "+ autoLowerHit + "\n" +"shots scored in auto for upper hub: "+ autoUpperHit + "\n" +
                "shots missed in teleop for lower hub: " + teleLowerMissed + "\n" +"shots missed in teleop for upper hub: "+ teleUpperMissed + "\n" +"shots scored in teleop for lower hub: "+ teleLowerHit + "\n" +"shots scored in teleop for upper hub: "+ teleUpperHit + "\n" +
                "total fouls: " + totalFouls + "\n" +"total tech fouls: "+ totalTechFouls + "\n" +"damage: " + damage + "\n" +"disconnects: " + disconects + "\n" + "climb time: " + climbTime;

        Intent intent = new Intent(MainActivity.this,endpopup.class);
        intent.putExtra("text", text);
        startActivityForResult(intent,1);//start the pop up window
    }
}
