package com.example.testingflashy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.testingflashy.TestClasses.Question;

public class CardViewPage extends AppCompatActivity {

    // gets the views for the front of the card and the back
    private TextView frontCard;
    private TextView backCard;

    // gets the strings for the question and answer of the card that was clicked
    private String question;
    private String answer;

    Question currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view_page);

        // gets the views from layout
        frontCard = findViewById(R.id.front_card);
        backCard = findViewById(R.id.back_card);

        // sees which card the user clicked on
        currentQuestion = (Question) getIntent().getSerializableExtra("CARD");
        question = currentQuestion.getQuestion();
        answer = currentQuestion.getAnswer();

        // sets the texts of the front and back
        frontCard.setText(question);
        backCard.setText(answer);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}