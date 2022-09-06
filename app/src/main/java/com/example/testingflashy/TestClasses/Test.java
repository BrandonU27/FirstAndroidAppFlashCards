package com.example.testingflashy.TestClasses;

import java.util.LinkedList;
import java.util.List;

public class Test {
    final List<Deck> deckList = new LinkedList<>();

    void AddDeck(Deck _deck){
        deckList.add(_deck);
    }
}
