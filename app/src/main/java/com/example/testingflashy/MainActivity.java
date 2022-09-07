package com.example.testingflashy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

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

    private Test testingTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Has all the test for the user
        // Vars for the sample tests
        List<Test> userTests = new ArrayList<>();

        // Sample data
        testingTest = new Test("Example Test", "3/23/22", "3:00pm");
        userTests.add(testingTest);
        ///////////////////

        // Makes it so that the current data is able to be displayed on the home page
        //Vars for the date textview
        TextView dateView = findViewById(R.id.currentDate);
        String date = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format((new Date()));
        dateView.setText(date);

        // Getting the users tests and adding them to the total tests they have
        // Vars for making sample test button
        Button test1 = findViewById(R.id.testOne);
        test1.setVisibility(View.VISIBLE);
        test1.setEnabled(true);
        String testButtonTitle = testingTest.getTitle() + "|| " + testingTest.getDate() + " || " + testingTest.getTime();
        test1.setText(testButtonTitle);

        test1.setOnClickListener(view -> openTestPage());
    }

    public void openTestPage(){
        Intent intent = new Intent(this, TestPage.class);
        intent.putExtra("Test", testingTest);
        startActivity(intent);
    }
}