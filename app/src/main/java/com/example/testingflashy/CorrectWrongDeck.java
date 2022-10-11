package com.example.testingflashy;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testingflashy.TestClasses.Question;
import com.example.testingflashy.TestClasses.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CorrectWrongDeck extends AppCompatActivity {

    // makes vars for the title of the page the date it was created on an also the count of cards they currently have
    // correct or wrong
    private TextView correctwrongTitle;
    private TextView correctwrongCreated;
    private TextView correctwrongCount;

    // makes vars for the list and the add button
    private Button addcorrectwrongButton;
    private ListView correctwrongL;

    // makes vars  of the lists that will be used
    // all the boxes on the test page goes to this page
    // also the list of correct and wrong cards goes this way
    // also chooses which on to use based on where you came from
    private List<String> correctNames;
    private List<String> wrongNames;
    private List<String> Box1Names;
    private List<String> Box2Names;
    private List<String> Box3Names;

    // var for the get number
    private int selectedTest;

    // var for the current test
    private Test currentTest;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correct_wrong_deck);
        Objects.requireNonNull(getSupportActionBar()).hide();

        // gets the information passed from the past page and
        // puts them in variables
        selectedTest = (int) getIntent().getSerializableExtra("SELECTEDTEST");
        currentTest = MainActivity.userTests.get(selectedTest);

        // sets the views to the layout views
        correctwrongTitle = findViewById(R.id.correctWrongName);
        correctwrongCreated = findViewById(R.id.correctwrongCreatedOn);
        correctwrongCount = findViewById(R.id.correctWrongCardCount);

        // sets the list and button to vars
        addcorrectwrongButton = findViewById(R.id.addcorrectwrong);
        correctwrongL = findViewById(R.id.correctwrongPageView);

        // makes different lists for the user for the boxes and the correct and wrong
        correctNames = new ArrayList<>();
        wrongNames = new ArrayList<>();
        Box1Names = new ArrayList<>();
        Box2Names = new ArrayList<>();
        Box3Names = new ArrayList<>();

        // calls the method that updates the page
        // sets the title and which cards are used
        updatePage();
    }

    // sets it so that there is a method that sends the user to the study page
    // ONLY if its on the correct and wrong method
    // passes on the selected deck number, and the wrong deck, and the mode its in
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void toStudy(){
        if(currentTest.getWrongCards().getCardCount() < 3){
            messagePopUp("Sorry but you didn't get enough wrong to study them.\nYou need at least 3.");
        }else{
            Intent intent = new Intent(this, StudyPage.class);
            intent.putExtra("SELECTEDTEST", getIntent().getSerializableExtra("SELECTEDTEST"));
            intent.putExtra("WRONGDECK", MainActivity.userTests.get(selectedTest).getWrongCards());
            intent.putExtra("WHICH", "Wrong");
            startActivity(intent);
            finish();
        }
    }

    // takes the user to the study page for box 1
    // passes in the selected test number, box deck of cards and which mode the user is in
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void toStudyBox1(){
        if(currentTest.getBox1().getCardCount() < 3){
            messagePopUp("Sorry but you didn't get enough wrong to study them.\nYou need at least 3.");
        }else{
            Intent intent = new Intent(this, StudyPage.class);
            intent.putExtra("SELECTEDTEST", getIntent().getSerializableExtra("SELECTEDTEST"));
            intent.putExtra("WRONGDECK", MainActivity.userTests.get(selectedTest).getBox1());
            intent.putExtra("WHICH", "1");
            startActivity(intent);
            finish();
        }
    }

    // takes the user to the study page for box 2
    // passes in the selected test number, the box 2 deck, and the mode the user is in
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void toStudyBoxBox2(){
        if(currentTest.getBox2().getCardCount() < 3){
            messagePopUp("Sorry but you didn't get enough wrong to study them.\nYou need at least 3.");
        }else{
            Intent intent = new Intent(this, StudyPage.class);
            intent.putExtra("SELECTEDTEST", getIntent().getSerializableExtra("SELECTEDTEST"));
            intent.putExtra("WRONGDECK", MainActivity.userTests.get(selectedTest).getBox2());
            intent.putExtra("WHICH", "2");
            startActivity(intent);
            finish();
        }
    }

    // takes the user to the study page for box 3
    // passes in the selected test number, the box 3 deck, and the mode the user is in
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void toStudyBox3(){
        if(currentTest.getBox2().getCardCount() < 3){
            messagePopUp("Sorry but you didn't get enough wrong to study them.\nYou need at least 3.");
        }else{
            Intent intent = new Intent(this, StudyPage.class);
            intent.putExtra("SELECTEDTEST", getIntent().getSerializableExtra("SELECTEDTEST"));
            intent.putExtra("WRONGDECK", MainActivity.userTests.get(selectedTest).getBox3());
            intent.putExtra("WHICH", "3");
            startActivity(intent);
            finish();
        }
    }

    // updates the information of the page.
    // this will depend on which mode the user is in
    //  this changes the buttons layout, the title of the page and date they where created
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updatePage(){
        String mode = (String) getIntent().getSerializableExtra("WHICH");
        if(Objects.equals(mode, "Correct")){
            correctwrongTitle.setText("Correct Deck");
            correctwrongCreated.setText("Created On: " + currentTest.getCorrectCards().getCreatedOn());
            correctwrongCount.setText("Cards: " + currentTest.getCorrectCards().getCardCount());
            for (Question q: currentTest.getCorrectCards().getCardDeck()){
                correctNames.add(q.getQuestion());
            }
            addcorrectwrongButton.setVisibility(View.INVISIBLE);
            addcorrectwrongButton.setClickable(false);
            updateCorrectList();
        }
        if(Objects.equals(mode, "Wrong")){
            correctwrongTitle.setText("Wrong Deck");
            correctwrongCreated.setText("Created On: " + currentTest.getWrongCards().getCreatedOn());
            correctwrongCount.setText("Cards: " + currentTest.getWrongCards().getCardCount());
            for (Question q : currentTest.getWrongCards().getCardDeck()) {
                wrongNames.add(q.getQuestion());
            }
            addcorrectwrongButton.setOnClickListener(view -> toStudy());
            updateWrongList();
        }
        if(Objects.equals(mode, "1")){
            correctwrongTitle.setText("Box 1");
            correctwrongCreated.setText("Created On: " + currentTest.getBox1().getCreatedOn());
            correctwrongCount.setText("Cards: " + currentTest.getBox1().getCardCount());
            for(Question q : currentTest.getBox1().getCardDeck()){
                Box1Names.add(q.getQuestion());
            }
            addcorrectwrongButton.setOnClickListener(view -> toStudyBox1());
            updateBoxList();
        }
        if(Objects.equals(mode, "2")){
            correctwrongTitle.setText("Box 2");
            correctwrongCreated.setText("Created On: " + currentTest.getBox2().getCreatedOn());
            correctwrongCount.setText("Cards: " + currentTest.getBox2().getCardCount());
            for(Question q : currentTest.getBox2().getCardDeck()){
                Box2Names.add(q.getQuestion());
            }
            addcorrectwrongButton.setOnClickListener(view -> toStudyBoxBox2());
            updateBox2List();
        }
        if(Objects.equals(mode, "3")){
            correctwrongTitle.setText("Box 3");
            correctwrongCreated.setText("Created On: " + currentTest.getBox3().getCreatedOn());
            correctwrongCount.setText("Cards: " + currentTest.getBox3().getCardCount());
            for(Question q : currentTest.getBox3().getCardDeck()){
                Box3Names.add(q.getQuestion());
            }
            addcorrectwrongButton.setOnClickListener(view -> toStudyBox3());
            updateBox3List();
        }
    }

    // if user is in correct deck updates the list on the screen to correct
    public void updateCorrectList(){
        ArrayAdapter<String> adapt;
        adapt = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, correctNames);
        correctwrongL.setAdapter(adapt);
    }

    // if user is in wrong deck updates the list on the screen to wrong
    public void updateWrongList(){
        ArrayAdapter<String> adapt;
        adapt = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, wrongNames);
        correctwrongL.setAdapter(adapt);
    }

    // if user is in the first box updates the list on the screen to box one list
    public void updateBoxList(){
        ArrayAdapter<String> adapt;
        adapt = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, Box1Names);
        correctwrongL.setAdapter(adapt);
    }

    // if user is in the second box it updates the list on the screen to box two list
    public void updateBox2List(){
        ArrayAdapter<String> adapt;
        adapt = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, Box2Names);
        correctwrongL.setAdapter(adapt);
    }

    // if use is in the third box it updates the list on the screen to box three list
    public void updateBox3List() {
        ArrayAdapter<String> adapt;
        adapt = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, Box3Names);
        correctwrongL.setAdapter(adapt);
    }

    // method that creates a pop up method for the user to use and edit the text in it
    public void messagePopUp(String _message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(true);
        builder.setTitle("Results");
        builder.setMessage(_message);

        builder.setPositiveButton("ok", (dialogInterface, i) -> dialogInterface.cancel());
        builder.show();
    }

    // when the user goes back it ends the page
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}