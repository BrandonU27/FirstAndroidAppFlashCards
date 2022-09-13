package com.example.testingflashy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.testingflashy.TestClasses.Deck;
import com.example.testingflashy.TestClasses.Options;
import com.example.testingflashy.TestClasses.Question;
import com.example.testingflashy.TestClasses.Test;
import com.example.testingflashy.dialogclasses.AddDeckDialog;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DeckPage extends AppCompatActivity{

    // List View
    private ListView deckL;

    // Gets the deck currently clicked on
    private Deck currentDeck;
    private List<Question> cardList;
    // Gets all the card names
    private List<String> cardNames;

    // Gets the textview from the xml page
    private TextView title;
    private TextView createdOn;
    private TextView cardCount;

    // Just for the card count
    private int intCardCount;
    private String stringCardCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_page);

        // Gets the list view
        deckL = findViewById(R.id.deckPageView);

        // Gets the deck that the user clicked on and puts it in var
        currentDeck = (Deck)getIntent().getSerializableExtra("DECK");
        cardList = currentDeck.getCardDeck();

        /*
        for (Question q: cardList){
            cardNames.add(q.getQuestion());
        }
        ArrayAdapter<String> adapt;
        adapt = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, cardNames);
        deckL.setAdapter(adapt);
        */
        // Sets all the views and titles
        SetTitles();
    }

    // Sets all the views and titles
    public void SetTitles(){
        // title
        title = findViewById(R.id.deckName);
        title.setText(currentDeck.getName());
        //created on
        createdOn = findViewById(R.id.deckCreatedOn);
        createdOn.setText("Created On: " + currentDeck.getCreatedOn());
        // card cont
        cardCount = findViewById(R.id.deckCardCount);
        intCardCount = currentDeck.getCardCount();
        stringCardCount = Integer.toString(intCardCount);
        cardCount.setText("Cards: " + stringCardCount);
    }

}