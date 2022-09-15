package com.example.testingflashy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.testingflashy.TestClasses.Question;

public class CardViewPage extends AppCompatActivity {

    private TextView frontCard;
    private TextView backCard;

    private String question;
    private String answer;

    Question currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view_page);

        frontCard = findViewById(R.id.front_card);
        backCard = findViewById(R.id.back_card);

        currentQuestion = (Question) getIntent().getSerializableExtra("CARD");
        question = currentQuestion.getQuestion();
        answer = currentQuestion.getAnswer();

        frontCard.setText(question);
        backCard.setText(answer);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}