package com.example.scoutingapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class endpopup extends Activity {
    String value = "";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.endpopup);
        DisplayMetrics displaySize = new DisplayMetrics();// create object that can store window width and height
        getWindowManager().getDefaultDisplay().getMetrics(displaySize); // get the display width and height and assign it to the displaySize variable

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("text");
        }
        TextView textbox = findViewById(R.id.textView11);
        textbox.setText(value);
    }

    public void onConfirm(View view){
        Toast.makeText(this, "someone else can figure out exporting to csv",Toast.LENGTH_SHORT).show();
    }
}
