package com.example.testingflashy.TestClasses;

import java.util.LinkedList;
import java.util.List;

public class Test {
    private List<Deck> deckList = new LinkedList<>();
    private String title;
    private String date;
    private String time;

    public Test(String _title, String _date, String _time){
        title = _title;
        date = _date;
        time = _time;
    }

    void AddDeck(Deck _deck){
        deckList.add(_deck);
    }
}
