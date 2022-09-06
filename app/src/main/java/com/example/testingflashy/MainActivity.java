package com.example.testingflashy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Parcel;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.testingflashy.TestClasses.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Vars for the sample tests
    private List<Test> userTests;
    private Test testingTest;

    //Vars for the date textview
    private TextView dateView;
    private String date;

    // Vars for making sample test button
    private Button test1;
    private String testButtonTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Has all the test for the user
        userTests = new ArrayList<>();

        // Sample data
        testingTest = new Test("Example Test", "3/23/22", "3:00pm");
        userTests.add(testingTest);
        ///////////////////

        // Makes it so that the current data is able to be displayed on the home page
        dateView = findViewById(R.id.currentDate);
        date = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format((new Date()));
        dateView.setText(date);

        // Getting the users tests and adding them to the total tests they have
        test1 = (Button) findViewById(R.id.testOne);
        test1.setVisibility(View.VISIBLE);
        test1.setEnabled(true);
        testButtonTitle = testingTest.getTitle() + "|| " + testingTest.getDate() + " || " + testingTest.getTime();
        test1.setText(testButtonTitle);

        test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTestPage();
            }
        });
    }

    public void openTestPage(){
        Intent intent = new Intent(this, TestPage.class);
        intent.putExtra("Test", testingTest);
        startActivity(intent);
    }
}