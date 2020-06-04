package com.example.eerestaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //declaring buttons
    Button viewBook, viewMenu, viewAbout, viewViewBookings, viewAddItemstoMenu;

    //Declaring mydbhelper
    DatabaseHelper myDb;

    //declaring ImageButtons
    ImageButton imageButtonFaceook, imageButtonInstagram, imageButtonTwitter;

    //shared preferences declaration
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    private String loggedinUsername, loggedinEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting the title of the Toolbar
        Objects.requireNonNull(getSupportActionBar()).setTitle("E & E Restaurant");

        //initialising mydb
        myDb = new DatabaseHelper(this);

        //getting buttons
        viewBook = (Button)findViewById(R.id.button_book);
        viewMenu = (Button)findViewById(R.id.button_viewmenu);
        viewAbout = (Button)findViewById(R.id.button_about);
        viewViewBookings = (Button)findViewById(R.id.button_view_bookings);
        viewAddItemstoMenu = (Button)findViewById(R.id.button_addItemtoMenu);

        //initialising imagebuttons
        imageButtonFaceook = (ImageButton)findViewById(R.id.imageButton_facebook);
        imageButtonInstagram = (ImageButton)findViewById(R.id.imageButton_instagram);
        imageButtonTwitter = (ImageButton)findViewById(R.id.imageButton_twitter);

        //onclick
        viewBook.setOnClickListener(this);
        viewMenu.setOnClickListener(this);
        viewAbout.setOnClickListener(this);
        viewViewBookings.setOnClickListener(this);
        viewAddItemstoMenu.setOnClickListener(this);
        imageButtonFaceook.setOnClickListener(this);
        imageButtonInstagram.setOnClickListener(this);
        imageButtonTwitter.setOnClickListener(this);

        //getting the saved user
        getUser();

        //checking the role of the signed in user, to change the buttons which are showing
        //checking if the signed in user is an admin, to show the add items to menu and the viewbookings buttons
        if (loggedinUsername.equals("admin")){
            viewViewBookings.setVisibility(View.VISIBLE);
            viewBook.setVisibility(View.GONE);

            viewAddItemstoMenu.setVisibility(View.VISIBLE);
            viewMenu.setVisibility(View.GONE);
        }

        //else, meaning the user is a normal user. Here, I am showing the Book a Table button and the View Menu button
        else {
            viewViewBookings.setVisibility(View.GONE);
            viewBook.setVisibility(View.VISIBLE);

            viewAddItemstoMenu.setVisibility(View.GONE);
            viewMenu.setVisibility(View.VISIBLE);
        }
    }

    //Creating Account Manager Dropdown
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //creating a menu inflater
        MenuInflater inflater = getMenuInflater();

        //checking if the the user is signed in or not
        //if the user is not signed in:
        if(loggedinUsername.equals("")) {
            inflater.inflate(R.menu.account_menu, menu);
        }
        else{
            //the user is signed in
            //checking if he/she is an admin:
            if (!loggedinUsername.equals("admin")){
                inflater.inflate(R.menu.user_logout_menu, menu);
            }

            //else, he is a normal user
            else {
                inflater.inflate(R.menu.logout_menu, menu);
            }
        }
        return true;
    }

    //on select for account manager dropdown
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Login_button:
                //going to the loginActivity page
                startActivity(new Intent(this,
                        LoginActivity.class)); break;

            case R.id.Register_button:
                //going to the RegisterActivity page
                startActivity(new Intent(this,
                        RegisterActivity.class)); break;

            case R.id.logout_button:
                //calling the logout method
                Logout();
                //refreshing the Main Activity page
                startActivity(new Intent(this,
                        MainActivity.class)); break;

            case R.id.view_your_reservations_button:
                //takes to user to the page UserViewReservationsActivity, were the user can view his own reservations
                startActivity(new Intent(this,
                        UserViewReservationsActivity.class)); break;

        }
        return super.onOptionsItemSelected(item);
    }

    //on select for on screen buttons
    @Override
    public void onClick(View v) {

        //case to go through the onclicks
        switch (v.getId()){
            case R.id.button_book:
                startActivity(new Intent(this,
                        BookActivity.class)); break;

            case R.id.button_about:
                startActivity(new Intent(this,
                        AboutActivity.class)); break;

            case R.id.button_viewmenu:
                startActivity(new Intent(this,
                        MenuActivity.class)); break;

            case R.id.button_view_bookings:
                //add activity to view the bookings
                startActivity(new Intent(this,
                        ViewBookingsActivity.class)); break;

            case R.id.button_addItemtoMenu:
                //add activity to view the bookings
                startActivity(new Intent(this,
                        AddMenuActivity.class)); break;

            case R.id.imageButton_facebook:
                //add code to go to facebook.com
                String facebookUrl = "https://www.facebook.com/";
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                facebookIntent.setData(Uri.parse(facebookUrl));
                startActivity(facebookIntent);break;

            case R.id.imageButton_instagram:
                //add code to go to instagram.com
                String instagramUrl = "https://www.instagram.com/";
                Intent instagramIntent = new Intent(Intent.ACTION_VIEW);
                instagramIntent.setData(Uri.parse(instagramUrl));
                startActivity(instagramIntent);break;

            case R.id.imageButton_twitter:
                //add code to go to twitter.com
                String twitterUrl = "https://www.twitter.com/";
                Intent twitterIntent = new Intent(Intent.ACTION_VIEW);
                twitterIntent.setData(Uri.parse(twitterUrl));
                startActivity(twitterIntent);break;

        }
    }

    //method to get the user from the shared preferences, if is already signed in
    public void getUser(){
        SharedPreferences sharedPreferences = getSharedPreferences(
                SHARED_PREFS, MODE_PRIVATE);

        loggedinUsername = sharedPreferences.getString(USERNAME, "");
        loggedinEmail = sharedPreferences.getString(EMAIL, "");
    }

    //log out method, which removed the user's details from the shared preferences
    public void Logout(){
        SharedPreferences sharedPreferences = getSharedPreferences(
                SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(EMAIL, "");
        editor.putString(USERNAME, "");

        editor.apply();
    }
}
