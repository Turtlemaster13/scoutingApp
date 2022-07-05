package com.example.scoutingapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

public class endpopup extends Activity {
    String value = "";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.endpopup);
        DisplayMetrics displaySize = new DisplayMetrics();// create object that can store window width and height
        getWindowManager().getDefaultDisplay().getMetrics(displaySize); // get the display width and height and assign it to the displaySize variable

        //--------------------------------------------------------------------------
        ConstraintLayout constraintLayout = findViewById(R.id.ConstraintLayout);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("text");
        }
        TextView textbox = findViewById(R.id.textView11);
        textbox.setText(value);
        EditText newToDo =  new EditText(endpopup.this);
        newToDo.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        newToDo.setHint("enter new to do");
        constraintLayout.addView(newToDo);
    }

    public void onConfirm(View view){
        Toast.makeText(this, "someone else can figure out exporting to csv",Toast.LENGTH_SHORT).show();
    }
    public void onCancel(View view){
        finish();
    }// close pop up
}
