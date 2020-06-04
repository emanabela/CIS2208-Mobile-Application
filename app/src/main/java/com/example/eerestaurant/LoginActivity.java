package com.example.eerestaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    //Declaring mydbhelper
    DatabaseHelper myDb;

    //declaring edittext
    EditText loginEmail, loginPassword;

    //declaring buttons
    Button loginButton, registerPage;

    //shared preferences declaration
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    private String loggedinUsername, loggedinEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialising db
        myDb = new DatabaseHelper(this);

        //initialising edittext
        loginEmail = (EditText)findViewById(R.id.login_email);
        loginPassword = (EditText)findViewById(R.id.login_password);

        //initialising buttons
        loginButton = (Button)findViewById(R.id.button_login);
        registerPage = (Button)findViewById(R.id.button_register);

        //setting on click listener
        loginButton.setOnClickListener(this);
        registerPage.setOnClickListener(this);
    }

    //onclick method
    @Override
    public void onClick(View v) {
        //switch to get which button was pressed
        switch (v.getId()){
            case R.id.button_login:
                //calling the login method
                Login(loginEmail.getText().toString(),
                        loginPassword.getText().toString());break;

            case R.id.button_register:
                //going to the register activity
                startActivity(new Intent(this, RegisterActivity.class));
        }
    }

    //method to log in the user/admin
    public void Login(String email, String password){

        //getting all of the users from the db and putting them into a cursor
        Cursor cursor = myDb.getAllUsers();
        if(cursor.getCount() == 0){
            //no users found in db and notifying the user that no users exist
            Toast. makeText(getApplicationContext(),
                    "No users registered",Toast. LENGTH_LONG).show();
        }

        //creating boolean to check whether or not the account was found (initially set to false)
        boolean found = false;

        //looping through the cursor
        while (cursor.moveToNext()){
            //checking if the email and the password are the same as found in the db
             if(email.equals(cursor.getString(1))
             && password.equals(cursor.getString(3))){

                 //if they are the same, the boolean found is set to true
                 found = true;

                 //calling the method to save the user
                 saveUser(email, cursor.getString(2));
                 break;
             }
        }

        //notifying the user whether the login was successful or not by using toast and by checking the state of the if statement
        if (found){
            Toast. makeText(getApplicationContext(),
                    "Log in Successful",Toast. LENGTH_LONG).show();

            //going to the main activity
            startActivity(new Intent(this, MainActivity.class));
        }
        else {
            Toast. makeText(getApplicationContext(),
                    "Log in not Successful, try again",
                    Toast. LENGTH_LONG).show();

            //clearing the loginPassword editText
            loginPassword.setText("");
        }
    }

    //saving the user in the Shared Preferences
    public void saveUser(String email, String username){
        SharedPreferences sharedPreferences = getSharedPreferences(
                SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(EMAIL, email);
        editor.putString(USERNAME, username);

        editor.apply();
    }
}
