package com.example.testingflashy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.testingflashy.TestClasses.Deck;
import com.example.testingflashy.TestClasses.Question;
import com.example.testingflashy.dialogclasses.AddCardDialog;


import java.util.ArrayList;
import java.util.List;

public class DeckPage extends AppCompatActivity implements AddCardDialog.AddCardDialogListener {

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

    // Keeps track on which card the user selected
    private String select;

    // button for adding card
    private Button addCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_page);

        // Gets the list view
        deckL = findViewById(R.id.deckPageView);
        cardNames = new ArrayList<String>();

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
        addCard = findViewById(R.id.addCardButton);
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

    // updates the list view
    public void updateList(){
        ArrayAdapter<String> adapt;
        adapt = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, cardNames);
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
            if(select == q.getQuestion()){
                intent.putExtra("CARD", q);
            }
        }
        startActivity(intent);
    }

    @Override
    public void makeCard(String _question, String _answer) {
        currentDeck.addCard(new Question(_question, _answer));
        cardList = currentDeck.getCardDeck();
        cardNames.clear();
        for (Question q: cardList){
            cardNames.add(q.getQuestion());
        }
        updateList();
    }
}