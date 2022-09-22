package com.example.testingflashy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.testingflashy.TestClasses.Question;
import com.example.testingflashy.TestClasses.Test;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CorrectWrongDeck extends AppCompatActivity {

    private TextView correctwrongTitle;
    private TextView correctwrongCreated;
    private TextView correctwrongCount;

    private Button addcorrectwrongButton;
    private ListView correctwrongL;

    private List<String> correctNames;
    private List<String> wrongNames;

    private int selectedTest;

    private Test currentTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correct_wrong_deck);

        selectedTest = (int) getIntent().getSerializableExtra("SELECTEDTEST");
        currentTest = MainActivity.userTests.get(selectedTest);

        correctwrongTitle = findViewById(R.id.correctWrongName);
        correctwrongCreated = findViewById(R.id.correctwrongCreatedOn);
        correctwrongCount = findViewById(R.id.correctWrongCardCount);

        addcorrectwrongButton = findViewById(R.id.addcorrectwrong);
        correctwrongL = findViewById(R.id.correctwrongPageView);

        correctNames = new ArrayList<>();
        wrongNames = new ArrayList<>();

        updatePage();
        addcorrectwrongButton.setOnClickListener(view -> toStudy() );
    }

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
        }else {
            correctwrongTitle.setText("Wrong Deck");
            correctwrongCreated.setText("Created On: " + currentTest.getWrongCards().getCreatedOn());
            correctwrongCount.setText("Cards: " + currentTest.getWrongCards().getCardCount());
            for (Question q : currentTest.getWrongCards().getCardDeck()) {
                wrongNames.add(q.getQuestion());
            }
            updateWrongList();
        }
    }

    public void updateCorrectList(){
        ArrayAdapter<String> adapt;
        adapt = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, correctNames);
        correctwrongL.setAdapter(adapt);
    }

    public void updateWrongList(){
        ArrayAdapter<String> adapt;
        adapt = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, wrongNames);
        correctwrongL.setAdapter(adapt);
    }

    public void messagePopUp(String _message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(true);
        builder.setTitle("Results");
        builder.setMessage(_message);

        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}