package com.example.eerestaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

public class UserViewReservationsActivity extends AppCompatActivity {

    //initialising mydbhelper
    DatabaseHelper myDb;

    //shared prefs vairalbes
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    private String loggedinUsername, loggedinEmail;

    //getting adapter
    private YourReservationsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_reservations);

        //declaring mydp
        myDb = new DatabaseHelper(this);

        //calling getUser method
        getUser();

        //getting the recyclerView
        RecyclerView recyclerView = findViewById(R.id.list_of_your_bookings);

        //setting the Layout Manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //declaring the Adapter
        mAdapter = new YourReservationsAdapter(this, getAllReservations());

        //setting the adapter
        recyclerView.setAdapter(mAdapter);
    }

    // method to get all of the Reservations
    @org.jetbrains.annotations.Nullable
    private Cursor getAllReservations(){

        //passing the data from the db to the cursor (passing the signed in user as a parameter)
        Cursor cursor =  myDb.getUsersReservations(loggedinUsername);

        //checking if the cursor is null. If it is, the method returns the cursor
        if(cursor.getCount() == 0){
            Toast.makeText(getApplicationContext(),
                    "Could not load",
                    Toast.LENGTH_LONG).show();
            return null;
        }

        //else, the method returns the cursor
        else {
            return cursor;
        }
    }

    //method to get the signed in user from the shared prefferences
    public void getUser(){
        SharedPreferences sharedPreferences = getSharedPreferences(
                SHARED_PREFS, MODE_PRIVATE);

        loggedinUsername = sharedPreferences.getString(USERNAME, "");
        loggedinEmail = sharedPreferences.getString(EMAIL, "");
    }
}
