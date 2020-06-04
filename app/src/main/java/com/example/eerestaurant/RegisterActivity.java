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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    //Declaring mydbhelper
    DatabaseHelper myDb;

    //declaring edit text fields
    EditText registerUsername, registerEmail, registerPassword, registerConformPassword;

    //declaring buttons
    Button registerButton, loginPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        myDb = new DatabaseHelper(this);

        //initialising edit textfields
        registerUsername = (EditText)findViewById(R.id.register_username);
        registerEmail = (EditText)findViewById(R.id.register_email);
        registerPassword = (EditText)findViewById(R.id.register_password);
        registerConformPassword = (EditText)findViewById(R.id.register_confirmpassword);

        //initialising buttons
        registerButton = (Button)findViewById(R.id.button_register);
        loginPage = (Button)findViewById(R.id.button_login);

        //adding onclick activities
        registerButton.setOnClickListener(this);
        loginPage.setOnClickListener(this);

    }

    //onclick method
    @Override
    public void onClick(View v) {

        //using a switch to get the click from the user
        switch (v.getId()){
            case R.id.button_register:

                //calling the addUser method and passing the details filled as parameters
                AddUser(registerEmail.getText().toString(),
                        registerUsername.getText().toString(),
                        registerPassword.getText().toString(),
                        registerConformPassword.getText().toString());break;

            case R.id.button_login:
                //taking the user to the login page
                startActivity(new Intent(this, LoginActivity.class));
        }
    }

    //method to add the user to the db
    public void AddUser(String email, String username,
                        String password, String confirmPassword){

        //checking if the password and the confirm password match
        if(!password.equals(confirmPassword)){
            Toast. makeText(getApplicationContext(),
                    "The passwords do not match",Toast. LENGTH_LONG).show();
        }
        //if they do ...
        else {

            //calling check if exists method ...
            if(!checkIfExist(email, username)) {

                //if the user does not exist ... the data is inserted in the db
                boolean result = myDb.insertData(email, username, password);

                //checking if the data was inserted successfully and notifying the user whether the details were saved or not
                if(result){
                    Toast. makeText(getApplicationContext(),
                            "User Registered, You can login",Toast. LENGTH_LONG).show();
                }
                else{
                    Toast. makeText(getApplicationContext(),
                            "Unable to register user",Toast. LENGTH_LONG).show();
                }
            }

            //if the account already exists, the user is notified with a toast
            else {
                Toast. makeText(getApplicationContext(),
                        "Details already exist",Toast. LENGTH_LONG).show();
            }
        }
    }

    //method to check if the user already exists
    public boolean checkIfExist(String email, String username){

        //getting all the users from the db and putting them in a cursor
        Cursor cursor = myDb.getAllUsers();

        //checking if the cursor is null
        if(cursor.getCount() == 0){
            //no users found in db
            return false;
        }

        //checking whether the user already exists, by comparing the email and the username
        boolean found = false;
        while (cursor.moveToNext()){
            if (email.equals(cursor.getString(1))
                    || username.equals(cursor.getString(2))){
                found = true;
                break;
            }
        }
        return found;
    }


}
