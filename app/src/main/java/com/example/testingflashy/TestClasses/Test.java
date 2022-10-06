package com.example.testingflashy.TestClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Test implements Serializable{
    private final List<Deck> deckList;

    private final Deck correctCards;
    private final Deck wrongCards;

    private final Deck Box1;
    private final Deck Box2;
    private final Deck Box3;

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

        Box1 = new Deck("Box1");
        Box2 = new Deck("Box2");
        Box3 = new Deck("Box3");
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

    public Deck getBox1(){return Box1;}
    public Deck getBox2(){return Box2;}
    public Deck getBox3(){return Box3;}
}
