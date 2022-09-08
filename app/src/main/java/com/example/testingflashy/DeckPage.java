package com.example.testingflashy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.testingflashy.TestClasses.Deck;
import com.example.testingflashy.TestClasses.Test;

import org.w3c.dom.Text;

public class DeckPage extends AppCompatActivity {

    // Going back button
    private ImageButton backButton;

    // Gets the deck currently clicked on
    private Deck currentDeck;

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

        // Gets the deck that the user clicked on and puts it in var
        currentDeck = (Deck)getIntent().getSerializableExtra("DECK");

        // Sets all the views and titles
        // title
        title = (TextView)findViewById(R.id.deckName);
        title.setText(currentDeck.getName());
        //created on
        createdOn = (TextView)findViewById(R.id.deckCreatedOn);
        createdOn.setText(currentDeck.getCreatedOn());
        // card cont
        cardCount = (TextView)findViewById(R.id.deckCardCount);
        intCardCount = currentDeck.getCardCount();
        stringCardCount = Integer.toString(intCardCount);
        cardCount.setText(stringCardCount);

        // Gets the back button and makes it clickable to send back to test page
        backButton = (ImageButton) findViewById(R.id.deckPageBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toTestPage();
            }
        });
    }

    // Takes the user back to the test page
    public void toTestPage(){
        Intent in = new Intent(this, TestPage.class);
        in.putExtra("TEST", (Test)getIntent().getSerializableExtra("TEST"));
        startActivity(in);
    }


}