package com.example.testingflashy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.testingflashy.TestClasses.Deck;
import com.example.testingflashy.TestClasses.Question;
import com.example.testingflashy.TestClasses.Test;
import com.example.testingflashy.dialogclasses.AddDeckDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TestPage extends AppCompatActivity implements  AddDeckDialog.AddDeckDialogListener{

    // Test information
    public Test currentTest;

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

    // Study deck
    public static Deck studyDeck;
    private Button addButton;
    private Button studyButton;

    // Correct and wrong deck buttons
    private Button correctButton;
    private Button wrongButton;

    // percent view
    public static TextView percentView;
    public static ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_page);

        // gets the correct and wrong button ids
        correctButton = findViewById(R.id.correctDeckButton);
        wrongButton = findViewById(R.id.wrongDeckButton);

        // Gets selected test
        selectedTest = (int)getIntent().getSerializableExtra("SELECTEDTEST");

        // Gets which test that the user has clicked on
        currentTest = (Test)getIntent().getSerializableExtra("TEST");


        // Create Study deck
        studyDeck = new Deck("Study Deck");
        for (Deck d: currentTest.getDeckList()){
            for (Question q : d.getCardDeck()){
                studyDeck.addCard(q);
            }
        }

        //creates the add deck button and the study button
        addButton = findViewById(R.id.addDeckButton);
        studyButton = findViewById(R.id.studyButton);

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
        // Study Button and add button
        addButton.setOnClickListener(view -> openAddDeckDialog());

        // Makes the decks in list clickable for the user to click on
        testL.setOnItemClickListener((adapterView, view, i, l) -> {
            selected = deckNames.get(i);
            selectedDeck = i;
            toDeckPage();
        });

        // Makes study button to take user to study
        studyButton.setOnClickListener(view -> toStudyPage());

        correctButton.setOnClickListener(view -> toCorrect());
        wrongButton.setOnClickListener(view -> toWrong());
    }
    // To correct deck page
    public void toCorrect(){
        Intent intent = new Intent(this, CorrectWrongDeck.class);
        intent.putExtra("WHICH", "Correct");
        intent.putExtra("SELECTEDTEST", selectedTest);
        startActivity(intent);
    }

    // to wrong deck page
    public void toWrong(){
        Intent intent = new Intent(this, CorrectWrongDeck.class);
        intent.putExtra("WHICH", "Wrong");
        intent.putExtra("SELECTEDTEST", selectedTest);
        startActivity(intent);
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

    // Takes to study page
    public void toStudyPage(){
        if(studyDeck.getCardCount() < 3){
            messagePopUp("Sorry you need more then 3 cards to go into study mode.\nTry adding more cards.");
        }
        else {
            Intent intent = new Intent(this, StudyPage.class);
            intent.putExtra("SELECTEDTEST", selectedTest);
            intent.putExtra("WHICH", "Study");
            startActivity(intent);
        }
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
    public void createTitle() {
        TextView titleView = findViewById(R.id.testName);
        titleView.setText(currentTest.getTitle());
        TextView dateView = findViewById(R.id.testDate);
        dateView.setText(currentTest.getDate());
        TextView timeView = findViewById(R.id.testTime);
        timeView.setText(currentTest.getTime());
        percentView = findViewById(R.id.percentTestView);
        bar = findViewById(R.id.progressBar);

        if(Objects.equals(0, studyDeck.getCardCount())){
            percentView.setText("100% Done");
            bar.setProgress(100);
        }else{
            getProgress();
        }
    }

    // Gets the progress of the test
    public void getProgress(){
        float point = 100/studyDeck.getCardCount();
        percentView.setText(point * currentTest.getCorrectCards().getCardCount() + "% Done");
        bar.setProgress((int) point * currentTest.getCorrectCards().getCardCount());
    }

    //Which study
    public void whichStudy(){
        if(MainActivity.mode == 0){
            correctButton.setVisibility(View.INVISIBLE);
            wrongButton.setVisibility(View.INVISIBLE);
            percentView.setVisibility(View.INVISIBLE);
            bar.setVisibility(View.INVISIBLE);
            addButton.setVisibility(View.VISIBLE);
            addButton.setClickable(true);
            studyButton.setVisibility(View.VISIBLE);
            studyButton.setClickable(true);

        }
        if(MainActivity.mode == 1){
            correctButton.setVisibility(View.VISIBLE);
            wrongButton.setVisibility(View.VISIBLE);
            percentView.setVisibility(View.VISIBLE);
            bar.setVisibility(View.VISIBLE);
            addButton.setVisibility(View.VISIBLE);
            addButton.setClickable(true);
            studyButton.setVisibility(View.VISIBLE);
            studyButton.setClickable(true);
        }
        if(MainActivity.mode == 5){
            correctButton.setVisibility(View.INVISIBLE);
            wrongButton.setVisibility(View.INVISIBLE);
            percentView.setVisibility(View.INVISIBLE);
            bar.setVisibility(View.INVISIBLE);
            addButton.setVisibility(View.INVISIBLE);
            addButton.setClickable(false);

        }
    }

    // Makes a message pop up for users
    public void messagePopUp(String _message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(true);
        builder.setTitle("Results");
        builder.setMessage(_message);

        builder.setPositiveButton("ok", (dialogInterface, i) -> dialogInterface.cancel());
        builder.show();
    }

    // Method from add deck dialog that makes a deck to be added into the list
    @SuppressLint("NewApi")
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