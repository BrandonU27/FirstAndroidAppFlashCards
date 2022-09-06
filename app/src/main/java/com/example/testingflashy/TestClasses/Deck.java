package com.example.testingflashy.TestClasses;

import java.util.LinkedList;
import java.util.List;

public class Deck {
    final List<Question> cardDeck = new LinkedList<>();

    void AddCard(Question _question){
        cardDeck.add(_question);
    }
}
