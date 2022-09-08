package com.example.testingflashy.TestClasses;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class Deck implements Serializable {

    private String name;
    private String createdOn;
    private int cardCount;
    private List<Question> cardDeck;

    public Deck(String _name){
        name = _name;
        createdOn = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format((new Date()));
        cardDeck = new ArrayList<>();
        cardCount = 0;
    }

    public void addCard(Question _question){
        cardDeck.add(_question);
        cardCount++;
    }

    public String getName(){return name;}
    public String getCreatedOn(){return createdOn;}
    public int getCardCount(){return cardCount;}
    public List<Question> getCardDeck(){return cardDeck;}
}
