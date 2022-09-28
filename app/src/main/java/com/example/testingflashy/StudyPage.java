package com.example.testingflashy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testingflashy.TestClasses.Deck;
import com.example.testingflashy.TestClasses.Question;
import com.example.testingflashy.TestClasses.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class StudyPage extends AppCompatActivity {

    // Makes a deck for the study session
    private Deck sessionDeck;

    // Gets all of the text views and the buttons
    private TextView questionView;
    private TextView questionCountView;
    private Button option1Button;
    private Button option2Button;
    private Button option3Button;

    // to be used to see if the user clicked the correct answer
    private int correctAnswer;

    // used to display which question the user is on
    private int currentQuestionNumber;

    // gets a list of answer so the user has wrong answer
    private List<String> wrongAnswers;

    // Var for the current question
    Question currentQuestion;

    // gets the current selected test
    private int selectedTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_page);

        // gets the selected test from last page
        selectedTest = (int) getIntent().getSerializableExtra("SELECTEDTEST");

        // makes the session deck and fills it with all the cards to be used
        sessionDeck = new Deck("Session Deck");
        if(Objects.equals(getIntent().getSerializableExtra("WHICH"), "Study")) {
            // Clears their correct and incorrect cards
            MainActivity.userTests.get(selectedTest).getCorrectCards().clear();
            MainActivity.userTests.get(selectedTest).getWrongCards().clear();
            for (Question q : TestPage.studyDeck.getCardDeck()) {
                sessionDeck.addCard(q);
            }
        }
        if(Objects.equals(getIntent().getSerializableExtra("WHICH"), "Wrong")){
            // Clears the wrong list only
            MainActivity.userTests.get(selectedTest).getWrongCards().clear();
            Deck copyWrongDeck = (Deck) getIntent().getSerializableExtra("WRONGDECK");
            for (Question q : copyWrongDeck.getCardDeck()){
                sessionDeck.addCard(q);
            }
        }

        // gets all the views from the layout
        questionView = findViewById(R.id.QuestionView);
        questionCountView = findViewById(R.id.questionCountView);
        option1Button = findViewById(R.id.option1Button);
        option2Button = findViewById(R.id.option2Button);
        option3Button = findViewById(R.id.option3Button);

        // gets all the wrong answers
        wrongAnswers = new ArrayList<>();
        // shuffles the session deck
        sessionDeck.randomize();

        // sets the current question number for when the test is created
        currentQuestionNumber = 0;
        for (Question q : sessionDeck.getCardDeck()){
            wrongAnswers.add(q.getAnswer());
        }

        // gets the first question loaded
        setPageQuestion();

        // checks if the user clicks on them
        option1Button.setOnClickListener(view -> checkAnswer1());
        option2Button.setOnClickListener(view -> checkAnswer2());
        option3Button.setOnClickListener(view -> checkAnswer3());
    }

    // Method to set the page up for the current question
    public void setPageQuestion(){
        // Used in setting the wrong answers
        String tempWrong = wronganswerAdd();

        // updates the current question number
        currentQuestionNumber++;
        // checks to see if its the end of the test
        if(currentQuestionNumber <= sessionDeck.getCardCount()) {

            //gets the first question ready and displayed
            currentQuestion = sessionDeck.getFirstCard();
            sessionDeck.removeFirstCard();

            if(Objects.equals((String)getIntent().getSerializableExtra("WHICH"), "Study")) {
                questionCountView.setText("Question: " + currentQuestionNumber + "/" + TestPage.studyDeck.getCardCount());
            }else{
                Deck copyWrongDeck = (Deck) getIntent().getSerializableExtra("WRONGDECK");
                questionCountView.setText("Question: " + currentQuestionNumber + "/" + copyWrongDeck.getCardCount());
            }

            questionView.setText(currentQuestion.getQuestion());

            Random random = new Random();
            correctAnswer = random.nextInt(3);
            correctAnswer++;

            // depending on the random selection of where the answer will be this filled the other options with wrong answers
            // if on button already has one wrong answer it wont let there be two
            switch (correctAnswer) {
                case 1:
                    option1Button.setText(currentQuestion.getAnswer());
                    while(tempWrong == option1Button.getText()){tempWrong = wronganswerAdd();}
                    option2Button.setText(tempWrong);
                    while(tempWrong == option1Button.getText()|| tempWrong == option2Button.getText()){tempWrong = wronganswerAdd();}
                    option3Button.setText(tempWrong);
                    break;
                case 2:
                    while(Objects.equals(tempWrong, currentQuestion.getAnswer())){tempWrong = wronganswerAdd();}
                    option1Button.setText(tempWrong);
                    option2Button.setText(currentQuestion.getAnswer());
                    while(tempWrong == option2Button.getText() || tempWrong == option1Button.getText()){tempWrong = wronganswerAdd();}
                    option3Button.setText(tempWrong);
                    break;
                case 3:
                    while(Objects.equals(tempWrong, currentQuestion.getAnswer())){tempWrong = wronganswerAdd();}
                    option1Button.setText(tempWrong);
                    while(Objects.equals(tempWrong, currentQuestion.getAnswer()) || tempWrong == option1Button.getText()){tempWrong = wronganswerAdd();}
                    option2Button.setText(tempWrong);
                    option3Button.setText(currentQuestion.getAnswer());
                    break;
            }
        }else{
            // if the user has reached the end of the test the page will tell the user its done
            questionView.setText("FINISHED\nYou can now hit the back button to go back to the test page.");
            option1Button.setVisibility(View.INVISIBLE);
            option2Button.setVisibility(View.INVISIBLE);
            option3Button.setVisibility(View.INVISIBLE);

            // update the percent of the test
            updatePercentage();
        }
    }

    public void updatePercentage(){
        float point = 100/ TestPage.studyDeck.getCardCount();
        TestPage.percentView.setText((float) point * MainActivity.userTests.get(selectedTest).getCorrectCards().getCardCount() + "% Done");

        TestPage.bar.setProgress((int) point * MainActivity.userTests.get(selectedTest).getCorrectCards().getCardCount());
    }

    // Gets a random wrong answer to be added to the test
    public String wronganswerAdd(){
        Collections.shuffle(wrongAnswers);
        return wrongAnswers.get(0);
    }

    // checks if this is the answer button
    public void checkAnswer1(){
        if(correctAnswer == 1){
            messagePopUp("You rock that was correct");
            MainActivity.userTests.get(selectedTest).getCorrectCards().addCard(currentQuestion);
        }else{
            messagePopUp("Sorry that wasn't it");
            MainActivity.userTests.get(selectedTest).getWrongCards().addCard(currentQuestion);
        }
        setPageQuestion();
    }

    // checks if this is the answer button
    public void checkAnswer2(){
        if(correctAnswer == 2){
            messagePopUp("Correct answer Super Star");
            MainActivity.userTests.get(selectedTest).getCorrectCards().addCard(currentQuestion);
        }else{
            messagePopUp("You need to practice a little more on this question");
            MainActivity.userTests.get(selectedTest).getWrongCards().addCard(currentQuestion);
        }
        setPageQuestion();
    }

    // checks if this is the answer button
    public void checkAnswer3(){
        if(correctAnswer == 3){
            messagePopUp("CORRECT amazing");
            MainActivity.userTests.get(selectedTest).getCorrectCards().addCard(currentQuestion);
        }else{
            messagePopUp("Incorrect study a little more");
            MainActivity.userTests.get(selectedTest).getWrongCards().addCard(currentQuestion);
        }
        setPageQuestion();
    }

    // Makes a message for the user to customize on what to display
    public void messagePopUp(String _message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(true);
        builder.setTitle("Results");
        builder.setMessage(_message);

        builder.setPositiveButton("ok", (dialogInterface, i) -> dialogInterface.cancel());
        builder.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}