package com.example.testingflashy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.testingflashy.TestClasses.Deck;
import com.example.testingflashy.TestClasses.Options;
import com.example.testingflashy.TestClasses.Question;
import com.example.testingflashy.TestClasses.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // All these variables are used to make the list view work properly
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

        Test sampleTes = new Test("Calculus Chapter 1", "12/31/2022", "12:00 pm");
        Deck sampleDec = new Deck("Unit 1");

        sampleTes.addDeck(sampleDec);
        userTests.add(sampleTes);

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

        // Makes the add test dialog box open
        //First gets the id of the button and adds to var
        Button addTestButton = findViewById(R.id.addButton);
        // Creates a click listener to see when it is clicked
        addTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTestDialog();
            }
        });

        // Set item clicked event
        homeL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selected = userNames.get(i);
                openTestPage();
            }
        });
    }

    public void addTestDialog(){

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