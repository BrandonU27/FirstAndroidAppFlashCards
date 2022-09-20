package com.example.testingflashy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.testingflashy.TestClasses.Deck;
import com.example.testingflashy.TestClasses.Question;

import java.util.Random;

public class StudyPage extends AppCompatActivity {

    private TextView questionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_page);

        questionView = findViewById(R.id.QuestionView);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}