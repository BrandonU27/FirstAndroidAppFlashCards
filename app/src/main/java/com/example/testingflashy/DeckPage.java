package com.example.testingflashy;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.testingflashy.TestClasses.Deck;
import com.example.testingflashy.TestClasses.Question;
import com.example.testingflashy.dialogclasses.AddCardDialog;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DeckPage extends AppCompatActivity implements AddCardDialog.AddCardDialogListener {

    // List View
    private ListView deckL;

    // Gets the deck currently clicked on
    private Deck currentDeck;
    private List<Question> cardList;
    // Gets all the card names
    private List<String> cardNames;

    // Keeps track on which card the user selected
    private String select;
    // Gets the main selected numbers
    private int selectedTest;
    private int selectedDeck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_page);
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Gets the numbers passed in
        selectedTest = (int)getIntent().getSerializableExtra("SELECTEDTEST");
        selectedDeck = (int)getIntent().getSerializableExtra("SELECTEDECK");

        // Gets the list view
        deckL = findViewById(R.id.deckPageView);
        cardNames = new ArrayList<>();

        // Gets the deck that the user clicked on and puts it in var
        currentDeck = (Deck)getIntent().getSerializableExtra("DECK");
        cardList = currentDeck.getCardDeck();

        // gets all the cards and puts them into the list view
        for(Question q: cardList){
            cardNames.add(q.getQuestion());
        }
        updateList();

        // Calls for the add to set the title
        SetTitles();

        // gets the add button
        // button for adding card
        Button addCard = findViewById(R.id.addCardButton);
        addCard.setOnClickListener(view -> showAddCardDialog());

        // Makes the cards in list clickable for the suer to click on
        deckL.setOnItemClickListener((adapterView, view, i, l) -> {
            select = cardNames.get(i);
            toCardView();
        });
    }

    // Sets all the views and titles
    public void SetTitles(){
        // title
        // Gets the textview from the xml page
        TextView title = findViewById(R.id.deckName);
        title.setText(currentDeck.getName());
        //created on
        TextView createdOn = findViewById(R.id.deckCreatedOn);
        createdOn.setText("Created On: " + currentDeck.getCreatedOn());
        // card cont
        TextView cardCount = findViewById(R.id.deckCardCount);
        // Just for the card count
        int intCardCount = currentDeck.getCardCount();
        String stringCardCount = Integer.toString(intCardCount);
        cardCount.setText("Cards: " + stringCardCount);

    }

    // updates the list view
    public void updateList(){
        ArrayAdapter<String> adapt;
        adapt = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, cardNames);
        deckL.setAdapter(adapt);
    }

    // opens the add card dialog
    public void showAddCardDialog(){
        AddCardDialog addCardDialog = new AddCardDialog();
        addCardDialog.show(getSupportFragmentManager(), "add card");
    }

    // Takes user to the card view page
    public void toCardView(){
        Intent intent = new Intent(this, CardViewPage.class);
        for (Question q: cardList){
            if(Objects.equals(select, q.getQuestion())){
                intent.putExtra("CARD", q);
            }
        }
        startActivity(intent);
    }

    // this is a method that lets the user make a card based on what they entered in the popup
    // then this will add a card to some decks used in the program
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void makeCard(String _question, String _answer) {
        currentDeck.addCard(new Question(_question, _answer));
        TestPage.studyDeck.addCard(new Question(_question, _answer));
        MainActivity.userTests.get(selectedTest)
                .getDeckList().get(selectedDeck)
                .addCard(new Question(_question,_answer));
        MainActivity.userTests.get(selectedTest)
                        .getBox1().addCard(new Question(_question, _answer));
        TestPage.testsDecks.get(selectedDeck)
                .addCard(new Question(_question, _answer));
        cardList = currentDeck.getCardDeck();
        cardNames.clear();
        for (Question q: cardList){
            cardNames.add(q.getQuestion());
        }
        updateList();
        SetTitles();
    }

    // ends the page when the user clicks the back button
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}