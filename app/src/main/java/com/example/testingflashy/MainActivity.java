package com.example.testingflashy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.testingflashy.TestClasses.Deck;
import com.example.testingflashy.TestClasses.Options;
import com.example.testingflashy.TestClasses.Question;
import com.example.testingflashy.TestClasses.Test;
import com.example.testingflashy.dialogclasses.AddDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AddDialog.AddDialogListener {

    // All these variables are used to make the list view work properly
    private ListView homeL;
    // Holds the data for the test adn the test names
    private List<Test> userTests;
    private List<String> userNames;
    // Tells which one the user selected
    private String selected;

    // Button for adding test
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Makes the lists that keeps track of the users tests
        The names list is used for the list view to make it look better
         */
        userTests = new ArrayList<>();
        userNames = new ArrayList<>();

        // Makes it so that the current data is able to be displayed on the home page
        //Vars for the date textview
        TextView dateView = findViewById(R.id.currentDate);
        String date = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format((new Date()));
        dateView.setText(date);

        ////// SAMPLE DATA
        userTests.add(new Test("EXAMPLE", "12/12/12", "3:00pm"));
        userTests.get(0).addDeck(new Deck("TEST"));
        for (Test t : userTests) {
            userNames.add(t.getTitle());
        }
        ////////////////////////

        // Adding the list as a var
        homeL = (ListView)findViewById(R.id.homeList);

        // Calls method to update the list
        updateList();

        // Makes the add test dialog box open
        //First gets the id of the button and adds to var
        addButton = findViewById(R.id.addButton);
        // Creates a click listener to see when it is clicked
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTestDialog();
            }
        });

        // Set item clicked event for selecting a test
        homeL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selected = userNames.get(i);
                openTestPage();
            }
        });
    }

    // Makes it so that the user can call the dialog box class to show up
    public void addTestDialog(){
        AddDialog addDialog = new AddDialog();
        addDialog.show(getSupportFragmentManager(), "add dialog");
    }

    // Method that gets all the information from the dialog box and makes a test to apply it
    @Override
    public void makeTest(String _title, String _date, String _time){
        userTests.add(new Test(_title,_date,_time));
        userNames.clear();
        for (Test t: userTests){
            userNames.add(t.getTitle());
        }
        updateList();
    }

    // Method for when the user clicks on the selected test it opens up to the test's page
    public void openTestPage(){
        Intent intent = new Intent(this, TestPage.class);
        for (Test t : userTests){
            if(selected == t.getTitle()){
                intent.putExtra("TEST", t);
            }
        }
        startActivity(intent);
    }

    // Makes an adaptor for the list and then adds the userNames to the list to update it
    public void updateList(){
        ArrayAdapter<String> adapt;
        adapt = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, userNames);
        homeL.setAdapter(adapt);
    }



}