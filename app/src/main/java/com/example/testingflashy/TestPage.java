package com.example.testingflashy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.testingflashy.TestClasses.Deck;
import com.example.testingflashy.TestClasses.Test;

import java.util.ArrayList;
import java.util.List;

public class TestPage extends AppCompatActivity {

    // Going back button
    private ImageButton backButton;

    // Test information
    private Test currentTest;
    private TextView titleView;
    private TextView dateView;
    private TextView timeView;

    //Deck vars to help the listview
    private List<Deck> testsDecks;
    private List<String> deckNames;

    // List View
    private ListView testL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_page);

        // Gets which test that the user has clicked on
        currentTest = (Test)getIntent().getSerializableExtra("TEST");
        // Sets the title date and time at the top to the test the user clicked on
        titleView = findViewById(R.id.testName);
        titleView.setText(currentTest.getTitle());
        dateView = findViewById(R.id.testDate);
        dateView.setText(currentTest.getDate());
        timeView = findViewById(R.id.testTime);
        timeView.setText(currentTest.getTime());

        // Create the list of decks and copy over the decklist
        deckNames = new ArrayList<String>();
        testsDecks = currentTest.getDeckList();

        // SAMPLE DATA DELETE AFTER USE
        testsDecks.add(new Deck("Unit 1"));
        testsDecks.add(new Deck("Unit 2"));
        //////////////////////////////

        // Gets all the names of the decks and puts them into a list
        for (Deck d : testsDecks){
            deckNames.add(d.getName());
        }

        // sets the list view var to the list view
        testL = (ListView) findViewById(R.id.testPageList);

        // Makes an adapter to set the deck names to the list view
        ArrayAdapter<String> adapt;
        adapt = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, deckNames);
        testL.setAdapter(adapt);

        // Sets the image button to take people back to home
        backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> backToHome());
    }

    // This sends the user back to the home page
    public void backToHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}