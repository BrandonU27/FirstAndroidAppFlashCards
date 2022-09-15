package com.example.testingflashy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.testingflashy.TestClasses.Deck;
import com.example.testingflashy.TestClasses.Test;
import com.example.testingflashy.dialogclasses.AddDeckDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TestPage extends AppCompatActivity implements  AddDeckDialog.AddDeckDialogListener{

    // Study Button and add button
    private Button studyButton;
    private Button addButton;

    // Test information
    public Test currentTest;
    private TextView titleView;
    private TextView dateView;
    private TextView timeView;

    //Deck vars to help the listview
    public static List<Deck> testsDecks;
    private List<String> deckNames;

    // Tracks which deck is selected
    private int selectedDeck;
    // Gets the selected test number
    private int selectedTest;

    // List View
    private ListView testL;
    // Selected Item in list
    private String selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_page);

        // Gets selected test
        selectedTest = (int)getIntent().getSerializableExtra("SELECTEDTEST");

        // Gets which test that the user has clicked on
        currentTest = (Test)getIntent().getSerializableExtra("TEST");
        // Sets the title date and time at the top to the test the user clicked on
        createTitle();

        //checks which mode
        whichStudy();

        // Create the list of decks and copy over the deck list
        deckNames = new ArrayList<>();
        testsDecks = currentTest.getDeckList();

        // Gets all the names of the decks and puts them into a list
        for (Deck d : testsDecks){
            deckNames.add(d.getName());
        }

        // sets the list view var to the list view
        testL = findViewById(R.id.testPageList);
        // Calls for list to update
        updateList();

        // get the add deck button and call the dialog to open
        addButton = findViewById(R.id.addDeckButton);
        addButton.setOnClickListener(view -> openAddDeckDialog());

        // Makes the decks in list clickable for the user to click on
        testL.setOnItemClickListener((adapterView, view, i, l) -> {
            selected = deckNames.get(i);
            selectedDeck = i;
            toDeckPage();
        });



    }

    // Sends the user to the deck page
    public void toDeckPage(){
        Intent in = new Intent(this, DeckPage.class);
        for (Deck d: currentTest.getDeckList()){
            if(Objects.equals(selected, d.getName()))
            {
                in.putExtra("DECK", d);
                in.putExtra("SELECTEDTEST", selectedTest);
                in.putExtra("SELECTEDECK", selectedDeck);
            }
        }
        in.putExtra("SELECTED", selected);
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
        adapt = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, deckNames);
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

    //Which study
    public void whichStudy(){
        if(MainActivity.mode == 0){
            // WORK ON NO STUDY MODE

            ///////////////////////
        }
        if(MainActivity.mode == 1){
            for (Deck t: currentTest.getDeckList()){
                if(Objects.equals(t.getName(), "Correct"))
                    return;
            }
            currentTest.addFrontDeck(new Deck("Correct"));
            currentTest.addFrontDeck(new Deck("Wrong"));
            MainActivity.userTests.get(selectedTest)
                    .addFrontDeck(new Deck("Correct"));
            MainActivity.userTests.get(selectedTest)
                    .addFrontDeck(new Deck("Wrong"));
        }
    }

    // Method from add deck dialog that makes a deck to be added into the list
    @Override
    public void makeDeck(String _title) {
        currentTest.addDeck(new Deck(_title));
        MainActivity.userTests.get(selectedTest)
                .addDeck(new Deck(_title));
        testsDecks = currentTest.getDeckList();
        deckNames.clear();
        for(Deck d: testsDecks){
            deckNames.add(d.getName());
        }
        updateList();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}