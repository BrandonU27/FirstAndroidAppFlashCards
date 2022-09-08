package com.example.testingflashy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.testingflashy.TestClasses.Deck;
import com.example.testingflashy.TestClasses.Test;

import java.util.List;

public class TestPage extends AppCompatActivity {

    // Going back button
    private ImageButton backButton;

    // Test information
    private Test currentTest;
    private TextView titleView;
    private TextView dateView;
    private TextView timeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_page);

        // Gets which test that the user has clicked on
        currentTest = (Test)getIntent().getSerializableExtra("TEST");
        // Sets the title date and time at the top to the test the user clicked on
        titleView = (TextView)findViewById(R.id.testName);
        titleView.setText(currentTest.getTitle());
        dateView = (TextView)findViewById(R.id.testDate);
        dateView.setText(currentTest.getDate());
        timeView = (TextView)findViewById(R.id.testTime);
        timeView.setText(currentTest.getTime());

        // Sets the image button to take people back to home
        backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToHome();
            }
        });
    }

    // This sends the user back to the home page
    public void backToHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}