package com.example.testingflashy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.testingflashy.TestClasses.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Has all the test for the user
        List<Test> userTests = new ArrayList<>();

        // Sample data
        Test testingTest = new Test("Example Test", "3/23/22", "3:00pm");
        ///////////////////

        // Makes it so that the current data is able to be displayed on the home page
        TextView dateView = findViewById(R.id.currentDate);
        String date = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format((new Date()));
        dateView.setText(date);


    }
}