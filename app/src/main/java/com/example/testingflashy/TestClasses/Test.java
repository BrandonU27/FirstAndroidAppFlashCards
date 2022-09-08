package com.example.testingflashy.TestClasses;

import java.util.LinkedList;
import java.util.List;
import java.io.Serializable;

public class Test implements Serializable{
    private List<Deck> deckList = new LinkedList<>();
    private String title;
    private String date;
    private String time;

    public Test(String _title, String _date, String _time){
        title = _title;
        date = _date;
        time = _time;
    }

    public void addDeck(Deck _deck){
        deckList.add(_deck);
    }

    public String getTitle(){return title;}
    public String getDate(){return date;}
    public String getTime(){return time;}
    public List<Deck> getDeckList(){return deckList;}

}
