package com.example.testingflashy;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

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

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity implements AddDialog.AddDialogListener, SettingDialog.SettingDialogListener {

    // All of my firebase data information



    // Study MODE
    // 0 = no study on
    // 1 = daily correct incorrect pile
    // 2 = Leitner system
    // 5 = archive mode
    public static int mode = 1;
    public static int beforeArch = 1;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

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

        ///////////////////////EXAMPLE DATA WILL DELETE FINAL BUILD
        sampleData();
        //////////////////////////////////////////////////////////

        // Adding the list as a var
        homeL = findViewById(R.id.pastList);
        // Calls method to update the list
        updateList();

        // Button that goes to the past tests
        // Button for going to past tests
        Button archiveButton = findViewById(R.id.archiveButton);
        archiveButton.setOnClickListener(view -> toArchivePage());

        //Opens the settings Dialog
        ImageButton settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(view -> toSettingPage());

        // Makes the add test dialog box open
        //First gets the id of the button and adds to var
        // Button for adding test
        Button addButton = findViewById(R.id.addButton);
        // Creates a click listener to see when it is clicked
        addButton.setOnClickListener(view -> addTestDialog());

        // Set item clicked event for selecting a test
        homeL.setOnItemClickListener((adapterView, view, i, l) -> {
            selected = userNames.get(i);
            selectedTest = i;
            openTestPage();
        });

        // notification builder
        // sends the user a notification on which test are needed to be studied for the day
       if(mode == 1){
           StringBuilder noteMessage = new StringBuilder();
           for(String s: userNames){
               noteMessage.append(s).append(", ");
           }

           if(VERSION.SDK_INT >= Build.VERSION_CODES.O){
               NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
               NotificationManager manager = getSystemService(NotificationManager.class);
               manager.createNotificationChannel(channel);
           }

           NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "My Notification");
           builder.setContentTitle("Test to Study Today: ");
           builder.setContentText(noteMessage.toString());
           builder.setSmallIcon(R.drawable.notif_icon);
           builder.setAutoCancel(true);

           NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
           managerCompat.notify(1, builder.build());
       }
    }

    // Makes it so that the user can call the dialog box class to show up
    // uses a custom layout that I have made for the dialog box
    public void addTestDialog(){
        AddDialog addDialog = new AddDialog();
        addDialog.show(getSupportFragmentManager(), "add dialog");
    }

    // Method that gets all the information from the dialog box and makes a test to apply it
    // overrides the method tha is in the add test dialog
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
    // it also passes on the data of which test it clicked and sends which get number it is
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
    // this only lets the user click if there are 3 tests that are passed
    // also sets the program to a custom mode so that the test page appears different for them
    public void toArchivePage(){
        if(pastTests.size() == 0){
            messagePopUp("You currently have no tests that pasted.\nAs time goes by you'll start to fill this page up.");
        }
        else {
            beforeArch = mode;
            mode = 5;
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
    // uses the custom settings dialog that I have made
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

        builder.setPositiveButton("ok", (dialogInterface, i) -> dialogInterface.cancel());
        builder.show();
    }

    // method to update the study mode based on the users dialog choice
    // this is used with the settings dialog
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


    // sample data used to test NOT IMPORTANT WILL DELETE ON RELEASE
    public void sampleData(){
        userTests.add(new Test("EXAMPLE TEST", "02/21/23", "5:00am"));
        userTests.get(0).addDeck(new Deck("SAMPLE DECK"));
        userTests.get(0).getDeckList().get(0).addCard(new Question("What is a flying animal?", "Bat"));
        userTests.get(0).getBox1().addCard(new Question("What is a flying animal?", "Bat"));
        userTests.get(0).getDeckList().get(0).addCard(new Question("What is a type of bear?", "Polar Bear"));
        userTests.get(0).getBox1().addCard(new Question("What is a type of bear?", "Polar Bear"));
        userTests.get(0).getDeckList().get(0).addCard(new Question("What is a bug?", "Ant"));
        userTests.get(0).getBox1().addCard(new Question("What is a bug?", "Ant"));
        userTests.get(0).getDeckList().get(0).addCard(new Question("What is a swimming animal?", "fish"));
        userTests.get(0).getBox1().addCard(new Question("What is a swimming animal?", "fish"));
        userTests.get(0).getDeckList().get(0).addCard(new Question("WORST ANIMAL?", "spider"));
        userTests.get(0).getBox1().addCard(new Question("WORST ANIMAL?", "spider"));
        userNames.add(userTests.get(0).getTitle());

        userTests.add(new Test("History", "11/02/22", "3:00pm"));
        userTests.get(1).addDeck(new Deck("WW1"));
        userTests.get(1).addDeck(new Deck("WW2"));
        userTests.get(1).addDeck(new Deck("Cold War"));
        userTests.get(1).getDeckList().get(0).addCard(new Question("Who was the main country for the East side fights?", "Germany, UK, France, USA"));
        userTests.get(1).getDeckList().get(0).addCard(new Question("What year was the last major year of the war?", "1945"));
        userNames.add(userTests.get(1).getTitle());

        userTests.add(new Test("Project and Portfolio", "9/22/22", "9:00pm"));
        userTests.get(2).getDeckList().add(new Deck("Empty Deck Test"));
        userNames.add(userTests.get(2).getTitle());

        pastTests.add(new Test("Chem Test", "01/22/22", "8:00am"));
    }
}