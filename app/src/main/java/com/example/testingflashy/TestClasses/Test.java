package com.example.testingflashy.TestClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Test implements Serializable{
    private final List<Deck> deckList;

    private final Deck correctCards;
    private final Deck wrongCards;

    private final String title;
    private final String date;
    private final String time;

    public Test(String _title, String _date, String _time){
        title = _title;
        date = _date;
        time = _time;
        deckList = new ArrayList<>();

        correctCards = new Deck("Correct Cards");
        wrongCards = new Deck("Wrong Cards");
    }

    public void addDeck(Deck _deck){
        deckList.add(_deck);
    }

    public String getTitle(){return title;}
    public String getDate(){return date;}
    public String getTime(){return time;}
    public List<Deck> getDeckList(){return deckList;}

    public Deck getCorrectCards(){return correctCards;}
    public Deck getWrongCards(){return wrongCards;}

}
