package com.example.testingflashy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.testingflashy.TestClasses.Deck;
import com.example.testingflashy.TestClasses.Question;
import com.example.testingflashy.TestClasses.Test;
import com.example.testingflashy.dialogclasses.AddDialog;
import com.example.testingflashy.dialogclasses.SettingDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements AddDialog.AddDialogListener, SettingDialog.SettingDialogListener {

    // Study MODE
    // 0 = no study on
    // 1 = daily correct incorrect pile
    // 2 = ?????????
    public static int mode = 0;

    // All these variables are used to make the list view work properly
    private ListView homeL;

    // Holds the data for the test adn the test names
    public static List<Test> userTests;
    private List<String> userNames;

    // list of all the past test that are past date
    public static List<Test> pastTests;

    // Tells which one the user selected
    private String selected;
    private int selectedTest;

    // Button for adding test
    private Button addButton;
    private ImageButton settingsButton;
    // Button for going to past tests
    private Button archiveButton;

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
        pastTests = new ArrayList<>();

        // Makes it so that the current data is able to be displayed on the home page
        //Vars for the date textview
        TextView dateView = findViewById(R.id.currentDate);
        String date = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format((new Date()));
        dateView.setText(date);

        ///////////////////////EXAMPLE DATA WILL DELTE FINAL BUILD
        userTests.add(new Test("EXAMPLE TEST", "02/21/23", "5:00am"));
        userTests.get(0).addDeck(new Deck("SAMPLE DECK"));
        userTests.get(0).getDeckList().get(0).addCard(new Question("What is a flying animal?", "Bat"));
        userTests.get(0).getDeckList().get(0).addCard(new Question("What is a type of bear?", "Polar Bear"));
        userTests.get(0).getDeckList().get(0).addCard(new Question("What is a bug?", "Ant"));
        userNames.add(userTests.get(0).getTitle());
        //////////////////////////////////////////////////////////

        // Adding the list as a var
        homeL = findViewById(R.id.homeList);
        // Calls method to update the list
        updateList();

        // Button that goes to the past tests
        archiveButton = findViewById(R.id.archiveButton);
        archiveButton.setOnClickListener(view -> toArchivePage());

        //Opens the settings Dialog
        settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(view -> toSettingPage());

        // Makes the add test dialog box open
        //First gets the id of the button and adds to var
        addButton = findViewById(R.id.addButton);
        // Creates a click listener to see when it is clicked
        addButton.setOnClickListener(view -> addTestDialog());

        // Set item clicked event for selecting a test
        homeL.setOnItemClickListener((adapterView, view, i, l) -> {
            selected = userNames.get(i);
            selectedTest = i;
            openTestPage();
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
            if(Objects.equals(selected, t.getTitle())){
                intent.putExtra("TEST", t);
                intent.putExtra("SELECTEDTEST", selectedTest);
            }
        }
        startActivity(intent);
    }

    // opens the page with past tests
    public void toArchivePage(){
        if(pastTests.size() == 0){
            messagePopUp("You currently have no tests that pasted.\nAs time goes by you'll start to fill this page up.");
        }
        else {
            Intent intent = new Intent(this, ArchivePage.class);
            startActivity(intent);
        }
    }

    // Makes an adaptor for the list and then adds the userNames to the list to update it
    public void updateList(){
        ArrayAdapter<String> adapt;
        adapt = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, userNames);
        homeL.setAdapter(adapt);
    }

    // Opens the settings button
    public void toSettingPage(){
        SettingDialog settingDialog = new SettingDialog();
        settingDialog.show(getSupportFragmentManager(), "setting dialog");
    }

    // lets the user make a pop up message based on what they enter
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

    // method to update the study mode based on the users dialog choice
    @Override
    public void studyChange(Boolean _op1, Boolean _op2, Boolean _op3) {
        if(_op1){
            mode = 0;
        }
        if(_op2){
            mode = 1;
        }
        if(_op3){
            mode = 2;
        }
    }
}