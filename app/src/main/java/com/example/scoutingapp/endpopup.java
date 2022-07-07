package com.example.scoutingapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class endpopup extends Activity {

    public Map<String, Object> getMap(Object o) {
        Map<String, Object> result = new HashMap<String, Object>();
        Field[] declaredFields = o.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            try {
                result.put(field.getName(), field.get(o));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    //ListView labels = findViewById(R.id.LabelsListView);
    MatchScore match = null;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postgame);
        DisplayMetrics displaySize = new DisplayMetrics();// create object that can store window width and height
        getWindowManager().getDefaultDisplay().getMetrics(displaySize); // get the display width and height and assign it to the displaySize variable

        //--------------------------------------------------------------------------

        ListView labels = findViewById(R.id.LabelsListView);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {// get the variables from the other activitys
            match = (MatchScore) getIntent().getSerializableExtra("data");
        }

        ArrayList<String> list = new ArrayList<String>();// create a list called list to store adapter veiw data enterys
        Map<String, Object> a = getMap(match);
        for (Map.Entry<String, Object> entry : a.entrySet()) {// for every entry in dictionatry map
            String key = entry.getKey();//get the key
            Object value = entry.getValue();//get the value
            //Log.v("blah", key + ": " + value.toString()); // log the key and the value
            list.add(key + ": " + value);
        }
        EditText textBox = findViewById(R.id.CommentsTextBox);
        textBox.setText(match.comments);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list);
        labels.setAdapter(adapter);

    }

    public void onConfirm(View view){
        EditText textBox = findViewById(R.id.CommentsTextBox);
        match.comments = textBox.getText().toString();
        Toast.makeText(this, "someone else can figure out exporting to csv",Toast.LENGTH_SHORT).show();
    }
    public void onCancel(View view){
        EditText textBox = findViewById(R.id.CommentsTextBox);
        match.comments = textBox.getText().toString();
        setResult(RESULT_OK, getIntent());
        finish();
    }// close pop up

}
