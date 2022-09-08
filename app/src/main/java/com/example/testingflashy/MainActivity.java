package com.example.testingflashy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.testingflashy.TestClasses.Deck;
import com.example.testingflashy.TestClasses.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ListView homeL;
    private List<Test> userTests;
    private List<String> userNames;
    private String selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Makes the lists that keeps track of the users tests
        The names list is used for the list view to make it look better
         */
        userTests = new ArrayList<>();
        userNames = new ArrayList<>();

        // Sample data
        userTests.add(new Test("Testing", "10.3.22", "3:00am"));
        userTests.add(new Test("English HW", "10.30.22", "2:00pm"));
        userTests.add(new Test("Math Alg", "10.13.22", "3:30pm"));

        for (Test data: userTests){
            userNames.add(data.getTitle());
        }
        ///////////////////

        // Makes it so that the current data is able to be displayed on the home page
        //Vars for the date textview
        TextView dateView = findViewById(R.id.currentDate);
        String date = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format((new Date()));
        dateView.setText(date);

        // Adding the list as a var
        homeL = (ListView)findViewById(R.id.homeList);

        // Creates an adapter
        ArrayAdapter<String> adapt;
        adapt = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, userNames);
        homeL.setAdapter(adapt);

        // Set itemclicked event
        homeL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selected = userNames.get(i);
                openTestPage();
            }
        });
    }

    public void openTestPage(){
        Intent intent = new Intent(this, TestPage.class);
        for (Test t : userTests){
            if(selected == t.getTitle()){
                intent.putExtra("TEST", t);
            }
        }
        startActivity(intent);
    }

}