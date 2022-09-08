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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DeckPage extends AppCompatActivity {

    // Going back button
    private ImageButton backButton;

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

        // Sets all the views and titles
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

        // SAMPLE DATA DELETE LATER
        Options text1 = new Options("Los Angeles", false);
        Options text2 = new Options("Washington DC", true);
        Options text3 = new Options("New York NY", false);
        Options text4 = new Options("Naples FL", false  );

        cardList.add(new Question("What is the capital of the United States?", text1, text2, text3, text4));

        cardNames = new ArrayList<String>();
        cardNames.add(cardList.get(0).getQuestion());
        ////////////////////////////

        // Sets the list of names to the list view on the page
        ArrayAdapter<String> adpt;
        adpt = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, cardNames);
        deckL.setAdapter(adpt);

    }

}