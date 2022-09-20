package com.example.testingflashy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.testingflashy.TestClasses.Deck;
import com.example.testingflashy.TestClasses.Question;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class StudyPage extends AppCompatActivity {

    private TextView questionView;
    private TextView questionCountView;
    private Button option1Button;
    private Button option2Button;
    private Button option3Button;

    private int currentQuestionNumber;

    private List<String> wrongAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_page);

        questionView = findViewById(R.id.QuestionView);
        questionCountView = findViewById(R.id.questionCountView);
        option1Button = findViewById(R.id.option1Button);
        option2Button = findViewById(R.id.option2Button);
        option3Button = findViewById(R.id.option3Button);

        wrongAnswers = new ArrayList<>();
        TestPage.studyDeck.randomize();

        currentQuestionNumber = 0;

        for (Question q : TestPage.studyDeck.getCardDeck()){
            wrongAnswers.add(q.getAnswer());
        }

        setPageQuestion();
    }

    public void setPageQuestion(){
        Question currentQuestion = TestPage.studyDeck.getFirstCard();
        currentQuestionNumber++;
        questionCountView.setText("Question: " + currentQuestionNumber + "/" + TestPage.studyDeck.getCardCount());

        questionView.setText(currentQuestion.getQuestion());

        option1Button.setText(currentQuestion.getAnswer());
        option2Button.setText(wrongAnswers.get(1));
        option3Button.setText(wrongAnswers.get(2));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}