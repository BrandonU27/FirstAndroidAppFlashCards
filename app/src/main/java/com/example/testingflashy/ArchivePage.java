package com.example.testingflashy;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.testingflashy.TestClasses.Question;
import com.example.testingflashy.TestClasses.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArchivePage extends AppCompatActivity {

    // vars for the lsit and the names of the past tests
    private ListView archiveL;
    private List<String> pastNames;

    // vars for the selected test and the number that goes to that test
    private String selected;
    private int selectedTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive_page);

        // sets the list and makes a new list
        archiveL = findViewById(R.id.pastList);
        pastNames = new ArrayList<>();

        // updates the list that the user just made
        updateList();

        // makes a clicker listener so that when the user clicks on the test it takes the number
        // also uses method to open test
        archiveL.setOnItemClickListener(((adapterView, view, i, l) -> {
            selected = pastNames.get(i);
            selectedTest = i;
            openTestPage();
        }));
    }

    // method that updates the list to the current list of passed tests
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateList(){

        for (Test t : MainActivity.pastTests){
            pastNames.add(t.getTitle());
        }

        ArrayAdapter<String> adapt;
        adapt = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, pastNames);
        archiveL.setAdapter(adapt);
    }

    // opens the page of the test using the mode that is made just for the archive page
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void openTestPage(){
        Intent intent = new Intent(this, TestPage.class);
        for (Test t : MainActivity.pastTests){
            if(Objects.equals(selected, t.getTitle())){
                intent.putExtra("TEST", t);
                intent.putExtra("SELECTEDTEST", selectedTest);
            }
        }
        startActivity(intent);
    }

    // ends the page when the user presses back
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.mode = MainActivity.beforeArch;
        finish();
    }
}