package com.example.testingflashy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.testingflashy.TestClasses.Deck;
import com.example.testingflashy.TestClasses.Question;
import com.example.testingflashy.TestClasses.Test;
import com.example.testingflashy.dialogclasses.AddDeckDialog;
import com.example.testingflashy.dialogclasses.AddDialog;

import java.util.ArrayList;
import java.util.List;

public class TestPage extends AppCompatActivity implements  AddDeckDialog.AddDeckDialogListener{

    // Study Button and add button
    private Button studyButton;
    private Button addButton;

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
    // Selected Item in list
    private String selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_page);

        // Gets which test that the user has clicked on
        currentTest = (Test)getIntent().getSerializableExtra("TEST");
        // Sets the title date and time at the top to the test the user clicked on
        createTitle();

        // Create the list of decks and copy over the decklist
        deckNames = new ArrayList<String>();
        testsDecks = currentTest.getDeckList();

        // Gets all the names of the decks and puts them into a list
        for (Deck d : testsDecks){
            deckNames.add(d.getName());
        }

        // sets the list view var to the list view
        testL = (ListView) findViewById(R.id.testPageList);
        // Calls for list to update
        updateList();

        // get the add deck button and call the dialog to open
        addButton = findViewById(R.id.addDeckButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddDeckDialog();
            }
        });

        // Makes the decks in list clickable for the user to click on
        testL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selected = deckNames.get(i);
                toDeckPage();
            }
        });
    }

    // Sends the user to the deck page
    public void toDeckPage(){
        Intent in = new Intent(this, DeckPage.class);
        for (Deck d: currentTest.getDeckList()){
            if(selected == d.getName()){in.putExtra("DECK", d);}
        }
        startActivity(in);
    }

    // Calls the add deck dialog to open
    public void openAddDeckDialog(){
        AddDeckDialog addDeckDialog = new AddDeckDialog();
        addDeckDialog.show(getSupportFragmentManager(), "add deck");
    }

    // Updates the list method
    public void updateList(){
        ArrayAdapter<String> adapt;
        adapt = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, deckNames);
        testL.setAdapter(adapt);
    }

    // Create page titles
    public void createTitle(){
        titleView = findViewById(R.id.testName);
        titleView.setText(currentTest.getTitle());
        dateView = findViewById(R.id.testDate);
        dateView.setText(currentTest.getDate());
        timeView = findViewById(R.id.testTime);
        timeView.setText(currentTest.getTime());
    }

    // Method from add deck dialog that makes a deck to be added into the list
    @Override
    public void makeDeck(String _title) {
        currentTest.addDeck(new Deck(_title));
        testsDecks = currentTest.getDeckList();
        deckNames.clear();
        for(Deck d: testsDecks){
            deckNames.add(d.getName());
        }
        updateList();
    }
}