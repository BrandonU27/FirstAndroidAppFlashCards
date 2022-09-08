package com.example.testingflashy.TestClasses;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class Deck {

    private String name;
    private String createdOn;
    private List<Question> cardDeck = new LinkedList<>();

    public Deck(String _name){
        name = _name;
        createdOn = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format((new Date()));
    }

    void AddCard(Question _question){
        cardDeck.add(_question);
    }

    public String getName(){return name;}
    public String getCreatedOn(){return createdOn;}
}
