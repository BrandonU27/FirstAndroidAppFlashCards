package com.example.testingflashy.TestClasses;

import java.util.LinkedList;
import java.util.List;

public class Test {
    final List<Deck> deckList = new LinkedList<>();
    final String date;
    final String time;

    Test(String _date, String _time){
        date = _date;
        time = _time;
    }

    void AddDeck(Deck _deck){
        deckList.add(_deck);
    }
}
