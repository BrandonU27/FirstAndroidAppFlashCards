package com.example.testingflashy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.testingflashy.TestClasses.Question;

public class CardViewPage extends AppCompatActivity {

    Question currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view_page);
        getSupportActionBar().hide();

        // gets the views from layout
        // gets the views for the front of the card and the back
        TextView frontCard = findViewById(R.id.front_card);
        TextView backCard = findViewById(R.id.back_card);

        // sees which card the user clicked on
        currentQuestion = (Question) getIntent().getSerializableExtra("CARD");
        // gets the strings for the question and answer of the card that was clicked
        String question = currentQuestion.getQuestion();
        String answer = currentQuestion.getAnswer();

        // sets the texts of the front and back
        frontCard.setText("Question: \n" + question);
        backCard.setText("Answer: \n" + answer);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}