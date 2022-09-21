package com.example.testingflashy.TestClasses;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.io.Serializable;

public class Test implements Serializable{
    private List<Deck> deckList;

    private Deck correctCards;
    private Deck wrongCards;

    private String title;
    private String date;
    private String time;

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
    public void addFrontDeck(Deck _deck){deckList.add(0, _deck);}
    public void addCorrect(Question _question){correctCards.addCard(_question);}
    public void addWrong(Question _wrong){wrongCards.addCard(_wrong);}

    public String getTitle(){return title;}
    public String getDate(){return date;}
    public String getTime(){return time;}
    public List<Deck> getDeckList(){return deckList;}

    public Deck getCorrectCards(){return correctCards;}
    public Deck getWrongCards(){return wrongCards;}

    public int getDeckNumbers(){return deckList.size();}
}
