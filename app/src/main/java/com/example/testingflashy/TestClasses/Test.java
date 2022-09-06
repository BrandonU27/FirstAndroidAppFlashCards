package com.example.testingflashy.TestClasses;

import java.util.LinkedList;
import java.util.List;

public class Test {
    private List<Deck> deckList = new LinkedList<>();
    private String date;
    private String time;

    void ChangedDate(String _date){
        date = _date;
    }

    void ChangeTime(String _time){
        time = _time;
    }

    void AddDeck(Deck _deck){
        deckList.add(_deck);
    }
}
