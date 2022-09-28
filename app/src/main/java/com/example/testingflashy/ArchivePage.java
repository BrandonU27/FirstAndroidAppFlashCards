package com.example.testingflashy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.testingflashy.TestClasses.Question;
import com.example.testingflashy.TestClasses.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArchivePage extends AppCompatActivity {

    private ListView archiveL;
    private List<String> pastNames;

    private String selected;
    private int selectedTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive_page);

        archiveL = findViewById(R.id.pastList);
        pastNames = new ArrayList<>();

        updateList();

        archiveL.setOnItemClickListener(((adapterView, view, i, l) -> {
            selected = pastNames.get(i);
            selectedTest = i;
            openTestPage();
        }));
    }

    public void updateList(){

        for (Test t : MainActivity.pastTests){
            pastNames.add(t.getTitle());
        }

        ArrayAdapter<String> adapt;
        adapt = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, pastNames);
        archiveL.setAdapter(adapt);
    }

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.mode = 0;
        finish();
    }
}